/*
 * Copyright (C) 2014-2015  ExtendedAlpha
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.extendedalpha.blockphysics.handlers;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.extendedalpha.blockphysics.BlockPhysicsPlugin;
import org.extendedalpha.blockphysics.components.SlideManager;

public class SnowHandler {
    private final BlockPhysicsPlugin plugin;
    private boolean snowSmoothing;
    private boolean meltAway;
    private int meltLightLevel;
    private boolean wgChecks;

    public SnowHandler(BlockPhysicsPlugin plugin) {
        this.plugin = plugin;
    }

    public void setSnowSmoothing(boolean snowSmoothing) {
        this.snowSmoothing = snowSmoothing;
    }

    public void setMeltAway(boolean meltAway) {
        this.meltAway = meltAway;
    }

    public void setMeltLightLevel(int meltLightLevel) {
        this.meltLightLevel = meltLightLevel;
    }

    public void setWgChecks(boolean wgChecks) {
        this.wgChecks = wgChecks;
    }

    public void tick() {
        for (World w : Bukkit.getWorlds()) {
            int limit = w.hasStorm() ? plugin.getPerWorldConfig().getSnowFormChance(w) : plugin.getPerWorldConfig().getSnowMeltChance(w);
            if (limit <= 0) {
                continue;
            }
            limit = (256 * limit) / 100; // 256 blocks per chunk layer

            int formRate = plugin.getPerWorldConfig().getSnowFormRate(w);
            int meltRate = plugin.getPerWorldConfig().getSnowMeltRate(w);
            int modifier = w.hasStorm() ? formRate : -meltRate;

            new ChunkProcessor(w, modifier, limit).runTaskTimer(plugin, 0L, 1L);
        }
    }

    /**
     * Handle the case where a snow layer or snow block falling block lands (and sucessfully forms a
     * new block) on an existing snow layer.
     *
     * @param block the snow layer block being landed on
     * @param fb    the falling block, either a snow layer or a snow block
     */
    public void handleSnowAccumulation(final Block block, FallingBlock fb) {
        int fbThickness = fb.getMaterial() == Material.SNOW ? fb.getBlockData() + 1 : 8;
        final byte newThickness = (byte) (block.getData() + fbThickness);

        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (newThickness > 7) {
                    block.setTypeIdAndData(Material.SNOW_BLOCK.getId(), (byte) 0, true);
                    block.getRelative(BlockFace.UP).setTypeIdAndData(Material.SNOW.getId(), (byte) (newThickness - 8), true);
                } else if (newThickness == 7) {
                    block.setTypeIdAndData(Material.SNOW_BLOCK.getId(), (byte) 0, true);
                } else {
                    block.setData(newThickness, true);
                }
            }
        });
    }

    private void processOneChunk(World w, Chunk c, int modifier, int limit) {
        for (int i = 0; i < limit; i++) {
            int x = plugin.getRandom().nextInt(16);
            int z = plugin.getRandom().nextInt(16);
            Block block = w.getHighestBlockAt(c.getX() * 16 + x, c.getZ() * 16 + z);
            Block below = block.getRelative(BlockFace.DOWN);
            if (block.getTemperature() < 0.1 && (modifier > 0 || block.getLightLevel() > meltLightLevel)) {
                if (wgChecks) {
                    StateFlag flag = modifier > 0 ? DefaultFlag.SNOW_FALL : DefaultFlag.SNOW_MELT;
                    if (!WGBukkit.getRegionManager(w).getApplicableRegions(block.getLocation()).allows(flag)) {
                        continue;
                    }
                }
                if (block.getType() == Material.SNOW) {
                    if (snowSmoothing) {
                        for (BlockFace face : BlockPhysicsPlugin.horizontalFaces) {
                            Block neighbour = block.getRelative(face);
                            if (neighbour.getType() == Material.SNOW || neighbour.getType() == Material.AIR && SlideManager.isSolid(neighbour.getRelative(BlockFace.DOWN).getType())) {
                                int diff = getSnowThicknessDifference(block, neighbour);
                                if (modifier > 0 && diff > 0) {
                                    block = neighbour;
                                    break;
                                } else if (modifier < 0 && diff < 0) {
                                    block = neighbour;
                                    break;
                                }
                            }
                        }
                    }
                    int newData = block.getData() + modifier;
                    if (block.getType() == Material.AIR) {
                        block.setTypeIdAndData(Material.SNOW.getId(), (byte) 0, true);
                    } else if (newData >= 7) {
                        block.setTypeIdAndData(Material.SNOW_BLOCK.getId(), (byte) 0, true);
                        if (newData > 7) {
                            block.getRelative(BlockFace.UP).setTypeIdAndData(Material.SNOW.getId(), (byte) (newData - 7), true);
                        }
                    } else if (newData >= 0) {
                        block.setData((byte) newData);
                    } else {
                        if (meltAway || below.getType() == Material.SNOW_BLOCK) {
                            block.setTypeIdAndData(Material.AIR.getId(), (byte) 0, true);
                            if (below.getType() == Material.SNOW_BLOCK && newData < -1) {
                                below.setTypeIdAndData(Material.SNOW.getId(), (byte) (newData + 8), true);
                            }
                        } else {
                            block.setTypeIdAndData(Material.SNOW.getId(), (byte) 0, true);
                        }
                    }
                } else if (block.getType() == Material.SNOW_BLOCK && modifier < 0) {
                    block.setTypeIdAndData(Material.SNOW.getId(), (byte) (7 + modifier), true);
                } else if (block.getType() == Material.AIR && modifier < 0 && below.getType() == Material.SNOW_BLOCK) {
                    below.setTypeIdAndData(Material.SNOW.getId(), (byte) (7 + modifier), true);
                }
            }
        }
    }

    private int getSnowThicknessDifference(Block b1, Block b2) {
        return getSnowThickness(b1) - getSnowThickness(b2);
    }

    private int getSnowThickness(Block b) {
        switch (b.getType()) {
            case SNOW:
                return b.getData() + 1;
            case SNOW_BLOCK:
                return 8;
            default:
                return 0;
        }
    }

    private class ChunkProcessor extends BukkitRunnable {
        private static final int MAX_CHUNKS_PER_TICK = 30;
        private final World w;
        private final Chunk[] chunks;
        private final int modifier;
        private final int limit;
        private int idx;

        private ChunkProcessor(World w, int modifier, int limit) {
            this.w = w;
            this.modifier = modifier;
            this.limit = limit;
            chunks = w.getLoadedChunks();
            idx = 0;
        }

        @Override
        public void run() {
            int n = 0;
            while (idx < chunks.length) {
                if (chunks[idx].isLoaded()) {
                    processOneChunk(w, chunks[idx], modifier, limit);
                }
                idx++;
                if (++n >= MAX_CHUNKS_PER_TICK) {
                    break;
                }
            }
            if (idx >= chunks.length) {
                cancel();
            }
        }
    }
}
