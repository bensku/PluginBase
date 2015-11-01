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

package org.extendedalpha.infamouspermissions.commands;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Permission;
import org.extendedalpha.infamouspermissions.types.User;

import org.bukkit.entity.Player;

/**
 * @author ExileDev
 */

public class CmdNick extends InFamousPermissionsCommand
{
	public CmdNick(InFamousPermissions plugin)
	{
		super(plugin);
		this.name = "nick";
		this.optionalArgs.add("user");
		this.requiredArgs.add("nick");
		this.description = "Set a player''s nickname";
		this.permission = Permission.CMD_NICK;
	}

	@Override
	public void perform()
	{
		if (args.length == 1)
		{
			User user = getUser();
			if (user == null)
				return;

			String nick = args[0];
			if (nick.equalsIgnoreCase("off") || nick.equalsIgnoreCase("null"))
			{
				user.setOption("name", null);
				sendpMessage("You have removed your nickname.");
				return;
			}

			user.setOption("name", args[0]);
			sendpMessage("You have set your nickname to \"&r{0}&6\"", args[0]);
		}
		else
		{
			User user = getUser(0);
			if (user == null)
				return;

			String nick = args[1];
			if (nick.equalsIgnoreCase("off") || nick.equalsIgnoreCase("null"))
			{
				user.setOption("name", null);

				Player target = user.getPlayer();
				if (target != null && ! hasArgument("--silent"))
					sendpMessage(target, "Your nickname has been removed.");

				sendpMessage("You have removed &6{0}&6''s nickname.", user.getName());
				return;
			}

			user.setOption("name", args[1]);

			Player target = user.getPlayer();
			if (target != null && ! hasArgument("--silent"))
				sendpMessage(target, "Your nickname is now \"&r{0}&6\"", args[1]);

			sendpMessage("You have set &6{0}&6''s nickname to \"&r{1}&6\"", user.getName(), user.getDisplayName());
		}
	}
}