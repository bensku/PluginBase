/*******************************************************************************
 * This file is part of Skyblock.
 *
 *     Skyblock is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Skyblock is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Skyblock.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.extendedalpha.skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.scheduler.BukkitTask;

import org.extendedalpha.skyblock.Skyblock;
import org.extendedalpha.skyblock.Settings;

/**
 * @author ExileDev
 * 
 */
public class LavaCheck implements Listener {
    BukkitTask task;
    private final Skyblock plugin;

    public LavaCheck(Skyblock aSkyBlock) {
	plugin = aSkyBlock;
    }

    /**
     * Removes stone generated by lava pouring onto water
     * 
     * @param e
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCleanstoneGen(BlockFromToEvent e) {
	// Only do this in Skyblock world
	if (!e.getBlock().getWorld().getName().equalsIgnoreCase(Settings.worldName)) {
	    return;
	}
	// Do nothing if a new island is being created
	if (plugin.isNewIsland())
	    return;
	final Block to = e.getToBlock();
	// plugin.getLogger().info("From material is " + from.toString());
	// plugin.getLogger().info("To material is " + to.getType().toString());
	// plugin.getLogger().info("---------------------------------");
	if (Settings.acidDamage > 0) {
	    final Material prev = to.getType();
	    // plugin.getLogger().info("To material was " +
	    // to.getType().toString());
	    plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
		@Override
		public void run() {
		    // plugin.getLogger().info("To material is after 1 tick " +
		    // to.getType().toString());
		    if ((prev.equals(Material.WATER) || prev.equals(Material.STATIONARY_WATER)) && to.getType().equals(Material.STONE)) {
			to.setType(prev);
			to.getWorld().playSound(to.getLocation(), Sound.FIZZ, 1F, 1F);
		    }
		}
	    });
	}
    }

    // Failed attempts - remember the pain
    // Not this event
    /*
     * @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
     * public void onStone(BlockFormEvent e) {
     * plugin.getLogger().info(e.getEventName());
     * }
     */
    /*
     * @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
     * public void onStone(BlockPhysicsEvent e) {
     * plugin.getLogger().info(e.getEventName());
     * plugin.getLogger().info("DEBUG: block physics " +
     * e.getBlock().getType());
     * plugin.getLogger().info("DEBUG: block physics changed " +
     * e.getChangedType());
     * plugin.getLogger().info("---------------------------------");
     * if (e.getChangedType().equals(Material.WATER) ||
     * e.getChangedType().equals(Material.STATIONARY_WATER)
     * && e.getBlock().getType().equals(Material.STONE)) {
     * e.getBlock().setType(Material.WATER);
     * e.getBlock().getWorld().playSound(e.getBlock().getLocation(), Sound.FIZZ,
     * 1F, 1F);
     * }
     * }
     */
    /*
     * @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
     * public void onStone(BlockSpreadEvent e) {
     * plugin.getLogger().info(e.getEventName());
     * }
     */
    /*
     * if ((from.equals(Material.STATIONARY_WATER) &&
     * to.getType().equals(Material.STONE))
     * || (from.equals(Material.STATIONARY_LAVA) &&
     * to.getType().equals(Material.STATIONARY_WATER))
     * || (from.equals(Material.LAVA) &&
     * to.getType().equals(Material.STATIONARY_WATER))) {
     * // plugin.getLogger().info("from sw to st cancelled");
     * // to.setType(Material.FIRE);
     * to.setType(Material.STATIONARY_WATER);
     * e.getBlock().getWorld().playSound(e.getBlock().getLocation(), Sound.FIZZ,
     * 1F, 1F);
     * e.setCancelled(true);
     * //return;
     * }
     * // Get the from block
     * Block fromBlock = to.getRelative(oppositeFace(e.getFace()));
     * plugin.getLogger().info("DEBUG: From block " + fromBlock.getType() +
     * " location " + fromBlock.getX() + "," + fromBlock.getZ());
     * plugin.getLogger().info("To material before " + to.getType().toString());
     * final Material prev = to.getType();
     * plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
     * @Override
     * public void run() {
     * plugin.getLogger().info("To material is after 1 tick " +
     * to.getType().toString());
     * if ((prev.equals(Material.WATER) ||
     * prev.equals(Material.STATIONARY_WATER)) &&
     * to.getType().equals(Material.STONE)) {
     * to.setType(prev);
     * to.getWorld().playSound(to.getLocation(), Sound.FIZZ, 1F, 1F);
     * }
     * }});
     * if ((from.equals(Material.STATIONARY_WATER) ||
     * from.equals(Material.WATER))) {
     * // Look around the from block
     * for (BlockFace bf: BlockFace.values()) {
     * switch (bf) {
     * case DOWN:
     * case EAST:
     * case NORTH:
     * case NORTH_EAST:
     * case NORTH_WEST:
     * case SOUTH:
     * case SOUTH_EAST:
     * case SOUTH_WEST:
     * case UP:
     * case WEST:
     * Block adjacent = fromBlock.getRelative(bf);
     * if (adjacent.getType().equals(Material.STONE)) {
     * adjacent.setType(Material.AIR);
     * adjacent.getWorld().playSound(e.getBlock().getLocation(), Sound.FIZZ, 1F,
     * 1F);
     * plugin.getLogger().info("DEBUG: Melting block " + adjacent.getType() +
     * " location " + adjacent.getX() + "," + adjacent.getZ());
     * }
     * break;
     * default:
     * break;
     * }
     * }
     * }
     */
    /*
     * private BlockFace oppositeFace(BlockFace face) {
     * switch (face) {
     * case DOWN:
     * return BlockFace.UP;
     * case EAST:
     * return BlockFace.WEST;
     * case EAST_NORTH_EAST:
     * return BlockFace.WEST_SOUTH_WEST;
     * case EAST_SOUTH_EAST:
     * return BlockFace.WEST_NORTH_WEST;
     * case NORTH:
     * return BlockFace.SOUTH;
     * case NORTH_EAST:
     * return BlockFace.SOUTH_WEST;
     * case NORTH_NORTH_EAST:
     * return BlockFace.SOUTH_SOUTH_WEST;
     * case NORTH_NORTH_WEST:
     * return BlockFace.SOUTH_SOUTH_EAST;
     * case NORTH_WEST:
     * return BlockFace.SOUTH_EAST;
     * case SELF:
     * return BlockFace.SELF;
     * case SOUTH:
     * return BlockFace.NORTH;
     * case SOUTH_EAST:
     * return BlockFace.NORTH_WEST;
     * case SOUTH_SOUTH_EAST:
     * return BlockFace.NORTH_NORTH_WEST;
     * case SOUTH_SOUTH_WEST:
     * return BlockFace.NORTH_NORTH_EAST;
     * case SOUTH_WEST:
     * return BlockFace.NORTH_EAST;
     * case UP:
     * return BlockFace.DOWN;
     * case WEST:
     * return BlockFace.EAST;
     * case WEST_NORTH_WEST:
     * return BlockFace.EAST_SOUTH_EAST;
     * case WEST_SOUTH_WEST:
     * return BlockFace.EAST_NORTH_EAST;
     * default:
     * return BlockFace.SELF;
     * }
     * }
     */
}