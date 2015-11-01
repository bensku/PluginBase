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

public class CmdPrefixReset extends InFamousPermissionsCommand
{
	public CmdPrefixReset(InFamousPermissions plugin)
	{
		super(plugin);
		this.name = "prefixreset";
		this.aliases.add("prefixr");
		this.aliases.add("prer");
		this.optionalArgs.add("player");
		this.description = "Reset your prefix";
		this.permission = Permission.CMD_PREFIX_RESET;
	}

	@Override
	public void perform()
	{
		if (args.length == 0)
		{
			User user = getUser(true);
			if (user == null)
				return;

			user.resetPrefix();

			sendpMessage("Your prefix has been reset!");
		}
		else if (args.length == 1)
		{
			User user = getUser(0, getWorld(), true);
			if (user == null)
				return;

			// Permission check
			if (! sender.getName().equals(user.getName()) && ! hasPermission(Permission.CMD_PREFIX_RESET_OTHERS))
			{
				err("You do not have permission to perform this command!");
				return;
			}

			user.resetPrefix();

			sendpMessage("&6You have reset {0}''s prefix!", user.getName());

			Player target = user.getPlayer();
			if (target != null && ! hasArgument("--silent"))
				sendpMessage(target, "&6Your prefix has been reset!");
		}
	}
}