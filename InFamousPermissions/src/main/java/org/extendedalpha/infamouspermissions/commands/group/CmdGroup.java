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
import java.util.Map;
import java.util.Map.Entry;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.commands.InFamousPermissionsCommand;
import org.extendedalpha.infamouspermissions.types.Group;
import org.extendedalpha.infamouspermissions.types.Permission;

import org.bukkit.World;
import org.extendedalpha.pluginbase.subapi.types.StringJoiner;

/**
 * @author ExileDev
 */

public class CmdGroup extends InFamousPermissionsCommand
{
	private List<GroupCommand> subCommands;

	public CmdGroup(InFamousPermissions plugin)
	{
		super(plugin);
		this.name = "group";
		this.requiredArgs.add("group");
		this.optionalArgs.add("action");
		this.optionalArgs.add("args");
		this.optionalArgs.add("world");
		this.description = "Modify a group's permissions";
		this.hasSubCommands = true;
		this.usesPrefix = true;

		this.registerSubCommands();
	}

	private final void registerSubCommands()
	{
		this.subCommands = new ArrayList<GroupCommand>();

		subCommands.add(new CmdAddPermission(plugin));
		subCommands.add(new CmdAddTemp(plugin));
		subCommands.add(new CmdHasOption(plugin));
		subCommands.add(new CmdHasPermission(plugin));
		subCommands.add(new CmdListUsers(plugin));
		subCommands.add(new CmdListPermissions(plugin));
		subCommands.add(new CmdRemovePermission(plugin));
		subCommands.add(new CmdRemoveTemp(plugin));
		subCommands.add(new CmdSetOption(plugin));
		subCommands.add(new CmdSetPrefix(plugin));
	}

	@Override
	public void perform()
	{
		World world = getWorld();
		Group group = getGroup(0, world, true);
		if (group == null)
			return;

		if (args.length == 1)
		{
			printGroupInfo(group);
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

		for (GroupCommand command : subCommands)
		{
			if (command.getAction().equalsIgnoreCase(action))
			{
				if (name.equalsIgnoreCase(command.getName()) || command.getAliases().contains(name.toLowerCase()))
				{
					command.execute(sender, group, world, argsList.toArray(new String[0]));
					return;
				}
			}
		}

		err("Invalid arguments! Try &c/infamousperms help&c!");
	}

	@Override
	public List<GroupCommand> getSubCommands()
	{
		return subCommands;
	}

	private final void printGroupInfo(Group group)
	{
		if (! hasPermission(Permission.GROUP_VIEW_INFO))
		{
			err("You do not have permission to perform this command!");
			return;
		}

		sendMessage("&7====[ &6{0} &7]====", group.getName());

		List<String> permissions = group.getPermissionNodes();
		if (permissions.size() > 0)
		{
			sendMessage("&6Permissions&6: {0}", new StringJoiner("&6, &6").appendAll(permissions.toArray(new String[0])));
		}

		List<Group> parents = group.getParentGroups();
		if (parents.size() > 0)
		{
			sendMessage("&6Parents&6:");
			for (Group parent : parents)
			{
				sendMessage("  &6- &6{0}", parent.getName());
			}
		}

		Map<String, Object> options = group.getOptions();
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