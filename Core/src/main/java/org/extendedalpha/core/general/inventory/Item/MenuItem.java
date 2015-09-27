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

package org.extendedalpha.core.general.inventory.Item;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItem
extends ItemStack {
    public MenuItem(Material type, String name, int amount, int durability, String action) {
        super(type, amount);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add((Object)ChatColor.GREEN + "> Click to " + action);
        im.setLore(lore);
        this.setItemMeta(im);
        this.setDurability((short)durability);
    }

    public MenuItem(Material type, String name, int durability, String action) {
        super(type);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add((Object)ChatColor.GREEN + "> Click to " + action);
        im.setLore(lore);
        this.setItemMeta(im);
        this.setDurability((short)durability);
    }
}

