/*
 * Copyright (C) 2014-2015  ExtendedAlpha
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

package org.extendedalpha.core.gui;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.extendedalpha.core.EAPermissionPlugin;

/**
 * @author exiledev
 */

public class GUIHandler implements Listener
{
	private final Map<String, AbstractGUI> open;

	public GUIHandler(EAPermissionPlugin plugin)
	{
		this.open = new HashMap<>();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Opens a given GUI for a given {@link Player}.
	 *
	 * @param player Player to open GUI for
	 * @param gui GUI to open
	 */
	public void open(Player player, AbstractGUI gui)
	{
		Validate.notNull(player, "player cannot be null!");
		Validate.notNull(gui, "gui cannot be null!");
		open.put(player.getName(), gui);
	}

	/**
	 * Opens a given GUI.
	 * 
	 * @param gui GUI to open
	 */
	public void open(AbstractGUI gui)
	{
		Validate.notNull(gui, "gui cannot be null!");
		open(gui.getPlayer(), gui);
	}

	// ---- Listeners

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event)
	{
		HumanEntity clicker = event.getWhoClicked();
		if (clicker instanceof Player)
		{
			Player player = (Player) clicker;
			if (open.containsKey(player.getName()))
			{
				AbstractGUI gui = open.get(player.getName());
				gui.onInventoryClick(event);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClose(InventoryCloseEvent event)
	{
		HumanEntity closer = event.getPlayer();
		if (closer instanceof Player)
		{
			Player player = (Player) closer;
			if (open.containsKey(player.getName()))
			{
				AbstractGUI gui = open.get(player.getName());
				gui.onInventoryClose(event);
				open.remove(player.getName());
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		if (open.containsKey(player.getName()))
		{
			player.closeInventory();
			open.remove(player.getName());
		}
	}
}
