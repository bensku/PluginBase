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

package org.extendedalpha.pluginbase.general.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InvUtils {
    public static boolean fits(Inventory inv, ItemStack item) {
        Inventory inv2 = inv.getSize() > 9 ? Bukkit.createInventory((InventoryHolder)null, (int)inv.getSize()) : Bukkit.createInventory((InventoryHolder)null, (InventoryType)inv.getType());
        for (int i = 0; i < inv.getContents().length; ++i) {
            inv2.setItem(i, inv.getContents()[i]);
        }
        return inv2.addItem(new ItemStack[]{item}).isEmpty();
    }

    public static boolean fits(Inventory inv, ItemStack item, int slot) {
        if (inv.getContents()[slot] == null) {
            return true;
        }
        ItemStack clone = inv.getContents()[slot].clone();
        int fits = clone.getType().getMaxStackSize() - inv.getContents()[slot].getAmount();
        if (clone.getType() == item.getType() && clone.getDurability() == item.getDurability()) {
            if (clone.getItemMeta().toString().equalsIgnoreCase(item.getItemMeta().toString())) {
                if (fits >= item.getAmount()) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public static ItemStack decreaseItem(ItemStack item, int amount) {
        ItemStack clone = item.clone();
        if (amount >= clone.getAmount()) {
            return null;
        }
        clone.setAmount(clone.getAmount() - amount);
        return clone;
    }

    public static void removeItem(Inventory inv, ItemStack item, int Amount) {
        ItemStack[] contents = inv.getContents();
        block0 : for (int i = 0; i < Amount; ++i) {
            for (int j = 0; j < contents.length; ++j) {
                if (contents[j] == null || contents[j].getType() != item.getType() || contents[j].getDurability() != item.getDurability()) continue;
                if (contents[j].getAmount() > 1) {
                    contents[j].setAmount(contents[j].getAmount() - 1);
                    continue block0;
                }
                inv.removeItem(new ItemStack[]{contents[j]});
                continue block0;
            }
        }
    }
}

