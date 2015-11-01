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

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Permission;

/**
 * @author ExileDev
 */

public class CmdHasOption extends UserCommand
{
	public CmdHasOption(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "has";
		this.name = "option";
		this.requiredArgs.add("option");
		this.description = "Check if a user has an option";
		this.permission = Permission.USER_HAS_OPTION;
	}

	@Override
	public void perform()
	{
		String key = args[0];
		if (user.hasOption(key))
		{
			sendpMessage("User &6{0} &6has option ''&6{1}&6''", user.getName(), key);
			sendpMessage("Value: &6{0}", user.getOption(key));
		}
		else
		{
			sendpMessage("User &6{0} &6does not have option ''&6{1}&6''", user.getName(), key);
		}
	}
}