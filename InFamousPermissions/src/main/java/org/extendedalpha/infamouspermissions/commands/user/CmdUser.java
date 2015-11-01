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

package org.extendedalpha.infamouspermissions.commands.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.commands.InFamousPermissionsCommand;
import org.extendedalpha.infamouspermissions.types.Permission;
import org.extendedalpha.infamouspermissions.types.User;

import org.bukkit.World;
import org.extendedalpha.pluginbase.subapi.types.StringJoiner;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * @author ExileDev
 */

public class CmdUser extends InFamousPermissionsCommand
{
	private List<UserCommand> subCommands;

	public CmdUser(InFamousPermissions plugin)
	{
		super(plugin);
		this.name = "user";
		this.requiredArgs.add("user");
		this.optionalArgs.add("action");
		this.optionalArgs.add("args");
		this.optionalArgs.add("world");
		this.description = "Modify a user's permissions";
		this.hasSubCommands = true;
		this.usesPrefix = true;

		this.registerSubCommands();
	}

	private final void registerSubCommands()
	{
		this.subCommands = new ArrayList<UserCommand>();

		subCommands.add(new CmdAddPermission(plugin));
		subCommands.add(new CmdAddSubgroup(plugin));
		subCommands.add(new CmdAddTemp(plugin));
		subCommands.add(new CmdHasGroup(plugin));
		subCommands.add(new CmdHasOption(plugin));
		subCommands.add(new CmdHasPermission(plugin));
		subCommands.add(new CmdListPermissions(plugin));
		subCommands.add(new CmdRemovePermission(plugin));
		subCommands.add(new CmdRemoveSubgroup(plugin));
		subCommands.add(new CmdRemoveTemp(plugin));
		subCommands.add(new CmdReset(plugin));
		subCommands.add(new CmdSetGroup(plugin));
		subCommands.add(new CmdSetOption(plugin));
		subCommands.add(new CmdSetPrefix(plugin));
		subCommands.add(new CmdSetSuffix(plugin));
	}

	@Override
	public void perform()
	{
		World world = getWorld();
		User user = getUser(0, world, true);
		if (user == null)
			return;

		if (args.length == 1)
		{
			printUserInfo(user);
			return;
		}

		String action = "";
		String name = "";
		List<String> argsList = new ArrayList<>();

		if (args.length == 2)
		{
			action = "";
			name = args[1];
			for (int i = 2; i < args.length; i++)
				argsList.add(args[i]);
		}
		else
		{
			action = args[1];
			name = args[2];
			for (int i = 3; i < args.length; i++)
				argsList.add(args[i]);
		}

		for (UserCommand command : subCommands)
		{
			if (command.getAction().equalsIgnoreCase(action))
			{
				if (command.getName().equalsIgnoreCase(name) || command.getAliases().contains(name.toLowerCase()))
				{
					command.execute(sender, user, world, argsList.toArray(new String[0]));
					return;
				}
			}
		}

		err("Invalid arguments! Try &c/infamousperms help&c!");
	}

	@Override
	public List<UserCommand> getSubCommands()
	{
		return subCommands;
	}

	private final void printUserInfo(User user)
	{
		if (! hasPermission(Permission.USER_VIEW_INFO))
		{
			err("You do not have permission to perform this command!");
			return;
		}

		sendMessage("&7====[ &6{0} &7]====", user.getName());
		sendMessage("&6Group&6: {0}", user.getGroupName());

		List<String> subGroups = user.getSubGroupNames();
		if (subGroups.size() > 0)
		{
			sendMessage("&6Sub Groups&6: {0}", FormatUtil.join("&6, &6", subGroups.toArray(new String[0])));
		}

		List<String> permissions = user.getPermissionNodes();
		if (permissions.size() > 0)
		{
			sendMessage("&6Permissions&6: {0}", new StringJoiner("&6, &6").appendAll(permissions.toArray(new String[0])));
		}

		Map<String, Object> options = user.getOptions();
		if (options.size() > 0)
		{
			sendMessage("&6Options&6:");
			for (Entry<String, Object> entry : options.entrySet())
			{
				sendMessage("  &6{0}&6: \"&f{1}&6\"", entry.getKey(), entry.getValue());
			}
		}
	}
}