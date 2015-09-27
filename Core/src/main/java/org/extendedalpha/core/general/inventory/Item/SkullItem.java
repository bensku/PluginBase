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
import org.bukkit.inventory.meta.SkullMeta;

public class SkullItem
extends ItemStack {
    String owner;

    public SkullItem(String name, String owner) {
        super(new ItemStack(Material.SKULL_ITEM));
        this.setDurability((short)3);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ((SkullMeta)im).setOwner(owner);
        this.setItemMeta(im);
        this.owner = owner;
    }

    public SkullItem(String owner) {
        super(new ItemStack(Material.SKULL_ITEM));
        this.setDurability((short) 3);
        ItemMeta im = this.getItemMeta();
        ((SkullMeta)im).setOwner(owner);
        this.setItemMeta(im);
        this.owner = owner;
    }

    public /* varargs */ SkullItem(String name, String owner, String ... lore) {
        super(new ItemStack(Material.SKULL_ITEM));
        this.setDurability((short)3);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ArrayList<String> lines = new ArrayList<String>();
        for (String line : lore) {
            lines.add(ChatColor.translateAlternateColorCodes((char)'&', (String)line));
        }
        im.setLore(lines);
        ((SkullMeta)im).setOwner(owner);
        this.setItemMeta(im);
        this.owner = owner;
    }

    public String getOwner() {
        return this.owner;
    }
}

