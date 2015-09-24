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

package org.extendedalpha.eapermissions.listeners;

import lombok.AllArgsConstructor;
import org.extendedalpha.core.util.FormatUtil;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.User;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author exiledev
 */

@AllArgsConstructor
public class PlayerListener implements Listener
{
	private final EAPermissions plugin;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		User user = plugin.getPermissionHandler().getUser(player);
		if (user == null)
		{
			player.sendMessage(plugin.getPrefix() + FormatUtil.format("Failed to get a user instance! Contact an administrator!"));
			return;
		}

		user.updateUniqueID(player);
		user.updatePermissions(player, true);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		User user = plugin.getPermissionHandler().getUser(player);
		if (user == null)
		{
			player.sendMessage(plugin.getPrefix() + FormatUtil.format("Failed to get a user instance! Contact an administrator!"));
			return;
		}

		user = plugin.getPermissionHandler().moveWorld(player, event.getFrom(), player.getWorld());

		boolean force = plugin.getConfig().getBoolean("forceUpdate.worldChange", false);
		user.updatePermissions(player, force);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		User user = plugin.getPermissionHandler().getUser(player);
		if (user == null)
		{
			player.sendMessage(plugin.getPrefix() + FormatUtil.format("Failed to get a user instance! Contact an administrator!"));
			return;
		}

		user.removeAttachment();
	}
}