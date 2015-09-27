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

package org.extendedalpha.core.general.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extendedalpha.core.general.math.Calculator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@Deprecated
public class Menu {
    Inventory inv;
    List<ItemStack> contents;
    Map<Integer, ItemStack> map;
    String name;

    public Menu(String name, List<ItemStack> items) {
        this.name = name;
        this.inv = Bukkit.createInventory((InventoryHolder)null, (int)(Calculator.formToLine(items.size()) * 9), (String)ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        this.contents = items;
        this.map = new HashMap<Integer, ItemStack>();
        for (int i = 0; i < this.inv.getSize(); ++i) {
            if (items.size() - 1 < i) {
                this.inv.setItem(i, new ItemStack(Material.AIR));
                this.map.put(i, new ItemStack(Material.AIR));
                continue;
            }
            this.inv.setItem(i, items.get(i));
            this.map.put(i, items.get(i));
        }
    }

    public Menu(String name, ItemStack[] items) {
        this.name = name;
        this.inv = Bukkit.createInventory((InventoryHolder)null, (int)(Calculator.formToLine(items.length) * 9), (String)ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (ItemStack item : items) {
            list.add(item);
        }
        this.contents = list;
        this.map = new HashMap<Integer, ItemStack>();
        for (int i = 0; i < this.inv.getSize(); ++i) {
            if (items.length - 1 < i) {
                this.inv.setItem(i, new ItemStack(Material.AIR));
                this.map.put(i, new ItemStack(Material.AIR));
                continue;
            }
            this.inv.setItem(i, items[i]);
            this.map.put(i, items[i]);
        }
    }

    public Menu(String name, int size) {
        this.name = name;
        this.inv = Bukkit.createInventory((InventoryHolder)null, (int)size, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)name));
        this.contents = new ArrayList<ItemStack>();
        this.map = new HashMap<Integer, ItemStack>();
        for (int i = 0; i < this.inv.getSize(); ++i) {
            this.inv.setItem(i, new ItemStack(Material.AIR));
            this.map.put(i, new ItemStack(Material.AIR));
        }
    }

    public void setSize(int size) {
        this.inv = Bukkit.createInventory((InventoryHolder)null, (int)size, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)this.name));
        for (int i = 0; i < size; ++i) {
            if (this.contents.size() - 1 >= i) continue;
            this.inv.setItem(i, new ItemStack(Material.AIR));
            this.map.put(i, new ItemStack(Material.AIR));
        }
    }

    public Inventory getInventory() {
        return this.inv;
    }

    public Map<Integer, ItemStack> toHashMap() {
        return this.map;
    }

    public List<ItemStack> getContents() {
        return this.contents;
    }

    public String getName() {
        return this.name;
    }


    public void openIndividually(Player p, List<ItemStack> items) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)null, (int)(Calculator.formToLine(items.size()) * 9), (String)ChatColor.translateAlternateColorCodes((char)'&', (String)this.name));
        for (int i = 0; i < items.size(); ++i) {
            inv.setItem(i, items.get(i));
        }
    }

    public void setItem(int slot, ItemStack item) {
        this.contents.set(slot, item);
        this.inv.setItem(slot, item);
        this.map.put(slot, item);
    }
}

