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

package org.extendedalpha.infamouspermissions.commands.group;

import java.util.ArrayList;
import java.util.List;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Group;
import org.extendedalpha.infamouspermissions.types.Permission;
import org.extendedalpha.infamouspermissions.types.User;
import org.apache.commons.lang.WordUtils;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * @author ExileDev
 */

public class CmdListUsers extends GroupCommand
{
	public CmdListUsers(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "list";
		this.name = "users";
		this.description = "List users in a group";
		this.permission = Permission.GROUP_LIST_USERS;
	}

	@Override
	public void perform()
	{
		// Default group check
		if (group.equals(plugin.getPermissionHandler().getDefaultGroup(world)))
		{
			err("You cannot display users in the default group!");
			return;
		}

		// Store important references
		final Group group = this.group;
		final World world = this.world;
		final CommandSender sender = this.sender;

		sendpMessage("Building list for group: {0}", group.getName());
		plugin.getLogHandler().debug("Calculating users in group {0} in async task", group.getName());

		// Calculate async
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				// Build users list
				final List<User> users = new ArrayList<User>();
				for (User user : plugin.getPermissionHandler().getAllUsers(world))
				{
					if (user.getGroupName() == null)
						continue;

					if (user.getGroupName().equals(group.getName()) || user.getSubGroupNames().contains(group.getName()))
						users.add(user);
				}

				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						plugin.getLogHandler().debug("Returning to sync");

						if (users.isEmpty())
						{
							sender.sendMessage(FormatUtil.format("&cError: &cNo users found in this group!"));
							return;
						}

						sender.sendMessage(FormatUtil.format("&7====[ &6{0} &7]====", WordUtils.capitalize(group.getName())));

						for (User user : users)
						{
							sender.sendMessage(FormatUtil.format("&6 - &6{0}", user.getLastKnownBy()));
						}
					}
				}.runTask(plugin);
			}
		}.runTaskAsynchronously(plugin);
	
	}
}