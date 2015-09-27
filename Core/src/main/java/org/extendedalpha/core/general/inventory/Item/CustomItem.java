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
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class CustomItem
extends ItemStack {
    public CustomItem(ItemStack item) {
        super(item);
    }

    public CustomItem(Material type, String name, int durability, List<String> lore) {
        super(new ItemStack(type));
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        im.setLore(lore);
        this.setItemMeta(im);
        this.setDurability((short)durability);
    }

    public CustomItem(Material type, String name, int durability, String[] lore) {
        super(new ItemStack(type));
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ArrayList<String> lines = new ArrayList<String>();
        for (String line : lore) {
            lines.add(ChatColor.translateAlternateColorCodes((char)'&', (String)line));
        }
        im.setLore(lines);
        this.setItemMeta(im);
        this.setDurability((short)durability);
    }

    public CustomItem(Material type, String name, int durability, String[] lore, String[] enchantments) {
        super(new ItemStack(type));
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ArrayList<String> lines = new ArrayList<String>();
        for (String line : lore) {
            lines.add(ChatColor.translateAlternateColorCodes((char)'&', (String)line));
        }
        im.setLore(lines);
        this.setItemMeta(im);
        this.setDurability((short)durability);
        for (String ench : enchantments) {
            this.addUnsafeEnchantment(Enchantment.getByName((String)ench.split("-")[0]), Integer.parseInt(ench.split("-")[1]));
        }
    }

    public CustomItem(ItemStack item, String[] enchantments) {
        super(item);
        for (String ench : enchantments) {
            this.addUnsafeEnchantment(Enchantment.getByName((String)ench.split("-")[0]), Integer.parseInt(ench.split("-")[1]));
        }
    }

    public CustomItem(Material type, String name, String[] enchantments, int durability) {
        super(new ItemStack(type));
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        this.setItemMeta(im);
        this.setDurability((short)durability);
        for (String ench : enchantments) {
            this.addUnsafeEnchantment(Enchantment.getByName((String)ench.split("-")[0]), Integer.parseInt(ench.split("-")[1]));
        }
    }

    public CustomItem(Material type, String name, int durability) {
        super(new ItemStack(type));
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        this.setItemMeta(im);
        this.setDurability((short)durability);
    }

    public CustomItem(ItemStack item, String name) {
        super(item);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        this.setItemMeta(im);
    }

    public /* varargs */ CustomItem(ItemStack item, String name, String ... lore) {
        super(item);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ArrayList<String> lines = new ArrayList<String>();
        for (String line : lore) {
            lines.add(ChatColor.translateAlternateColorCodes((char)'&', (String)line));
        }
        im.setLore(lines);
        this.setItemMeta(im);
    }

    public CustomItem(Material type, int durability) {
        super(new ItemStack(type));
        this.setDurability((short)durability);
    }

    public CustomItem(Material type, int durability, int amount) {
        super(new ItemStack(type, amount));
        this.setDurability((short)durability);
    }

    public CustomItem(ItemStack item, int amount) {
        super(item.clone());
        this.setAmount(amount);
    }

    public CustomItem(Material type, String name, int durability, int amount, List<String> lore) {
        super(new ItemStack(type, amount));
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        im.setLore(lore);
        this.setItemMeta(im);
        this.setDurability((short)durability);
    }

    public /* varargs */ CustomItem(MaterialData data, String name, String ... lore) {
        super(data.toItemStack(1));
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ArrayList<String> lines = new ArrayList<String>();
        for (String line : lore) {
            lines.add(ChatColor.translateAlternateColorCodes((char)'&', (String)line));
        }
        im.setLore(lines);
        this.setItemMeta(im);
    }

    public CustomItem(MaterialData data, int amount) {
        super(data.toItemStack(amount));
    }

    public List<String> getLore() {
        if (!this.hasItemMeta()) {
            return new ArrayList<String>();
        }
        if (!this.getItemMeta().hasLore()) {
            return new ArrayList<String>();
        }
        return this.getItemMeta().getLore();
    }

    public String getDisplayName() {
        if (!this.hasItemMeta()) {
            return "";
        }
        if (!this.getItemMeta().hasDisplayName()) {
            return "";
        }
        return this.getItemMeta().getDisplayName();
    }

    public boolean hasEnchantment(Enchantment enchantment) {
        return this.getEnchantments().containsKey((Object)enchantment);
    }

    public int getEnchantmentLevel(Enchantment enchantment) {
        return this.hasEnchantment(enchantment) ? this.getEnchantmentLevel(enchantment) : 0;
    }

    public static void addEnchantmentGlow(ItemStack item) {
    }
}

