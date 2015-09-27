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

package org.extendedalpha.core.general.player;

import org.extendedalpha.core.general.inventory.InvUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInventory {
    public static void consumeItemInHand(Player p) {
        if (p.getGameMode() != GameMode.CREATIVE) {
            ItemStack item = p.getItemInHand().clone();
            item.setAmount(item.getAmount() - 1);
            p.setItemInHand(item.getAmount() > 0 ? item : null);
        }
    }

    public static void removeItemIgnoringMetaAndDamage(Player p, Material Item, int Amount) {
        ItemStack[] contents = p.getInventory().getContents();
        block0 : for (int i = 0; i < Amount; ++i) {
            for (int j = 0; j < contents.length; ++j) {
                if (contents[j] == null || contents[j].getType() != Item) continue;
                if (contents[j].getAmount() > 1) {
                    contents[j].setAmount(contents[j].getAmount() - 1);
                    continue block0;
                }
                p.getInventory().removeItem(new ItemStack[]{contents[j]});
                continue block0;
            }
        }
    }

    public static void removeItemIgnoringMeta(Player p, Material Item, int Amount) {
        ItemStack[] contents = p.getInventory().getContents();
        block0 : for (int i = 0; i < Amount; ++i) {
            for (int j = 0; j < contents.length; ++j) {
                if (contents[j] == null || contents[j].getType() != Item || contents[j].getDurability() != 0) continue;
                if (contents[j].getAmount() > 1) {
                    contents[j].setAmount(contents[j].getAmount() - 1);
                    continue block0;
                }
                p.getInventory().removeItem(new ItemStack[]{contents[j]});
                continue block0;
            }
        }
    }

    public static void removeItemIgnoringMeta(Player p, Material Item, int Amount, int durability) {
        ItemStack[] contents = p.getInventory().getContents();
        block0 : for (int i = 0; i < Amount; ++i) {
            for (int j = 0; j < contents.length; ++j) {
                if (contents[j] == null || contents[j].getType() != Item || contents[j].getDurability() != durability) continue;
                if (contents[j].getAmount() > 1) {
                    contents[j].setAmount(contents[j].getAmount() - 1);
                    continue block0;
                }
                p.getInventory().removeItem(new ItemStack[]{contents[j]});
                continue block0;
            }
        }
    }

    public static void removeItem(Player p, ItemStack item, int Amount) {
        ItemStack[] contents = p.getInventory().getContents();
        block0 : for (int i = 0; i < Amount; ++i) {
            for (int j = 0; j < contents.length; ++j) {
                if (contents[j] == null || contents[j].getType() != item.getType() || contents[j].getDurability() != item.getDurability()) continue;
                if (contents[j].getAmount() > 1) {
                    contents[j].setAmount(contents[j].getAmount() - 1);
                    continue block0;
                }
                p.getInventory().removeItem(new ItemStack[]{contents[j]});
                continue block0;
            }
        }
    }

    public static void update(Player p) {
        p.updateInventory();
    }

    public static void damageItemInHand(Player p) {
        if (p.getGameMode() != GameMode.CREATIVE) {
            ItemStack item = p.getItemInHand().clone();
            item.setDurability((short)(item.getDurability() + 1));
            p.setItemInHand(item.getDurability() < item.getType().getMaxDurability() ? item : null);
        }
    }

    public static void giveItem(Player p, ItemStack item) {
        if (InvUtils.fits((Inventory)p.getInventory(), item)) {
            p.getInventory().addItem(new ItemStack[]{item});
        } else {
            p.getWorld().dropItemNaturally(p.getLocation(), item);
        }
    }
}

