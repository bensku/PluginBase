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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extendedalpha.pluginbase.general.math.Calculator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ChestMenu {
    boolean clickable;
    String title;
    Inventory inv;
    List<ItemStack> items;
    Map<Integer, MenuClickHandler> handlers;
    MenuOpeningHandler open;
    MenuCloseHandler close;

    public ChestMenu(String title) {
        this.title = ChatColor.translateAlternateColorCodes((char)'&', (String)title);
        this.clickable = false;
        this.items = new ArrayList<ItemStack>();
        this.handlers = new HashMap<Integer, MenuClickHandler>();
        this.open = new MenuOpeningHandler(){

            @Override
            public void onOpen(Player p) {
            }
        };
        this.close = new MenuCloseHandler(){

            @Override
            public void onClose(Player p) {
            }
        };
    }

    public ChestMenu setPlayerInventoryClickable(boolean clickable) {
        this.clickable = clickable;
        return this;
    }

    public boolean isPlayerInventoryClickable() {
        return this.clickable;
    }

    public ChestMenu addItem(int slot, ItemStack item) {
        int size = this.items.size();
        if (size > slot) {
            this.items.set(slot, item);
        } else {
            for (int i = 0; i < slot - size; ++i) {
                this.items.add(null);
            }
            this.items.add(item);
        }
        return this;
    }

    public ChestMenu addItem(int slot, ItemStack item, MenuClickHandler clickHandler) {
        this.addItem(slot, item);
        this.addMenuClickHandler(slot, clickHandler);
        return this;
    }

    public ItemStack getItemInSlot(int slot) {
        this.setup();
        return this.inv.getItem(slot);
    }

    public ChestMenu addMenuClickHandler(int slot, MenuClickHandler handler) {
        this.handlers.put(slot, handler);
        return this;
    }

    public ChestMenu addMenuOpeningHandler(MenuOpeningHandler handler) {
        this.open = handler;
        return this;
    }

    public ChestMenu addMenuCloseHandler(MenuCloseHandler handler) {
        this.close = handler;
        return this;
    }

    @Deprecated
    public ChestMenu build() {
        return this;
    }

    public ItemStack[] getContents() {
        this.setup();
        return this.inv.getContents();
    }

    private void setup() {
        if (this.inv != null) {
            return;
        }
        this.inv = Bukkit.createInventory((InventoryHolder)null, (int)(Calculator.formToLine(this.items.size()) * 9), (String)this.title);
        for (int i = 0; i < this.items.size(); ++i) {
            this.inv.setItem(i, this.items.get(i));
        }
    }

    public void reset(boolean update) {
        if (update) {
            this.inv.clear();
        } else {
            this.inv = Bukkit.createInventory((InventoryHolder)null, (int)(Calculator.formToLine(this.items.size()) * 9), (String)this.title);
        }
        for (int i = 0; i < this.items.size(); ++i) {
            this.inv.setItem(i, this.items.get(i));
        }
    }

    public void replaceExistingItem(int slot, ItemStack item) {
        this.setup();
        this.inv.setItem(slot, item);
    }

    public /* varargs */ void open(Player ... players) {
        this.setup();
        for (Player p : players) {
            p.openInventory(this.inv);
            if (this.open == null) continue;
            this.open.onOpen(p);
        }
    }

    public MenuClickHandler getMenuClickHandler(int slot) {
        return this.handlers.get(slot);
    }

    public MenuCloseHandler getMenuCloseHandler() {
        return this.close;
    }

    public Inventory toInventory() {
        return this.inv;
    }

    public static interface MenuClickHandler {
        public boolean onClick(Player var1, int var2, ItemStack var3, ClickAction var4);
    }

    public static interface MenuCloseHandler {
        public void onClose(Player var1);
    }

    public static interface MenuOpeningHandler {
        public void onOpen(Player var1);
    }

}

