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

package org.extendedalpha.eapermissions.commands;

import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Permission;
import org.extendedalpha.eapermissions.types.User;

/**
 * @author exiledev
 */

public class CmdNick extends EAPermissionsCommand
{
	public CmdNick(EAPermissions plugin)
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
			sendpMessage("You have set your nickname to \"&r{0}&e\"", args[0]);
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
				sendpMessage(player, "Your nickname has been removed.");
				sendpMessage("You have removed &b{0}&e''s nickname.", user.getName());
				return;
			}

			user.setOption("name", args[1]);
			sendpMessage(player, "Your nickname is now \"&r{0}&e\"", args[1]);
			sendpMessage("You have set &b{0}&e''s nickname to \"&r{0}&e\"", user.getName(), args[1]);
		}
	}
}