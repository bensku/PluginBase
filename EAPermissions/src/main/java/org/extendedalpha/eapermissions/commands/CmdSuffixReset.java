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

public class CmdSuffixReset extends EAPermissionsCommand
{
	public CmdSuffixReset(EAPermissions plugin)
	{
		super(plugin);
		this.name = "suffixreset";
		this.aliases.add("suffixr");
		this.aliases.add("sufr");
		this.optionalArgs.add("player");
		this.description = "Reset your suffix";
		this.permission = Permission.CMD_SUFFIX_RESET;
	}

	@Override
	public void perform()
	{
		if (args.length == 0)
		{
			User user = getUser(true);
			if (user == null)
				return;

			user.resetSuffix();

			sendpMessage("Your suffix has been reset!");
		}
		else if (args.length == 1)
		{
			User user = getUser(0, getWorld(), true);
			if (user == null)
				return;

			// Permission check
			if (! sender.getName().equals(user.getName()) && ! hasPermission(Permission.CMD_SUFFIX_RESET_OTHERS))
			{
				err("You do not have permission to perform this command!");
				return;
			}

			user.resetSuffix();

			sendpMessage("&eYou have reset {0}''s suffix!", user.getName());
			sendpMessage(user.getPlayer(), "&eYour suffix has been reset!");
		}
	}
}