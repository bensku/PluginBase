/*
 * This file is part of ExtendedAlpha-Core
 *
 * Copyright (C) 2013-2015 ExtendedAlpha
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

package org.extendedalpha.pluginbase.general.block;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreaker {
    public static void breakBlock(Player p, Block b) {
        BlockBreakEvent event = new BlockBreakEvent(b, p);
        Bukkit.getServer().getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            event.setCancelled(true);
            for (ItemStack drop : b.getDrops()) {
                b.getWorld().dropItem(b.getLocation(), drop);
            }
            b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, (Object)b.getType());
            b.setType(Material.AIR);
        }
    }

    public static void nullify(Block b) {
        b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, (Object)b.getType());
        b.setType(Material.AIR);
    }

    public static void breakBlock(Player p, Block b, List<ItemStack> drops, boolean check) {
        boolean allowed = true;
        if (check) {
            BlockBreakEvent event = new BlockBreakEvent(b, p);
            Bukkit.getServer().getPluginManager().callEvent((Event)event);
            boolean bl = allowed = !event.isCancelled();
        }
        if (allowed) {
            for (ItemStack drop : drops) {
                b.getWorld().dropItemNaturally(b.getLocation(), drop);
            }
            if (check) {
                b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, (Object)b.getType());
            }
            b.setType(Material.AIR);
        }
    }
}

