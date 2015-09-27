/*
 * This file is part of BossbarLib
 *
 * Copyright (C) 2013-2015 ExtendedAlpha, ExileDev
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

package org.extendedalpha.core.handler;

import org.extendedalpha.core.common.NMS;
import org.extendedalpha.core.types.BossbarWither;
import org.extendedalpha.core.types.CraftWitherBossbar;
import org.extendedalpha.core.types.WitherBossbar;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class WitherBossbarHandler implements Listener, BossbarHandler {

    static {
        NMS.registerCustomEntity("WitherBoss", BossbarWither.class, 64);
    }

    private final Map<UUID, WitherBossbar> spawnedWithers = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        clearBossbar(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        clearBossbar(event.getPlayer());
    }

    private WitherBossbar newBossbar() {
        return newBossbar(ChatColor.BOLD + "", 1f);
    }

    private WitherBossbar newBossbar(String message, float percentage) {
        return new CraftWitherBossbar(message, null).setMessage(message).setPercentage(percentage);
    }

    public void clearBossbar(Player player) {
        WitherBossbar bossbar = spawnedWithers.get(player.getUniqueId());
        spawnedWithers.remove(player.getUniqueId());
        if (bossbar == null || !bossbar.isSpawned() || bossbar.getDestroyPacket() == null) {
            return;
        }
        NMS.sendPacket(player, bossbar.getDestroyPacket());
    }

    public WitherBossbar getBossbar(Player player) {
        WitherBossbar bossbar = spawnedWithers.get(player.getUniqueId());
        if (bossbar == null) {
            bossbar = newBossbar();
            spawnedWithers.put(player.getUniqueId(), bossbar);
        }
        return bossbar;
    }

    public boolean hasBossbar(Player player) {
        return spawnedWithers.containsKey(player.getUniqueId());
    }

    public void updateBossbar(Player player) {
        WitherBossbar bossbar = spawnedWithers.get(player.getUniqueId());
        if (bossbar == null) {
            return;
        }
        if (!bossbar.isSpawned()) {
            bossbar.setSpawned(true);
            bossbar.setSpawnLocation(player.getLocation().add(player.getEyeLocation().getDirection().multiply(20)));
            NMS.sendPacket(player, bossbar.getSpawnPacket());
        }
        NMS.sendPacket(player, bossbar.getMetaPacket(bossbar.getWatcher()));
        NMS.sendPacket(player, bossbar.getTeleportPacket(player.getLocation().add(player.getEyeLocation().getDirection().multiply(20))));
    }

}
