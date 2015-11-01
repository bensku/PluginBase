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

public class CmdHasGroup extends UserCommand
{
	public CmdHasGroup(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "has";
		this.name = "group";
		this.requiredArgs.add("group");
		this.description = "Checks if a user is in a group";
		this.permission = Permission.USER_HAS_GROUP;
	}

	@Override
	public void perform()
	{
		String group = args[0];
		if (user.isInGroup(group))
		{
			sendpMessage("User &6{0} &6is in group &6{1}&6.", user.getName(), group);
		}
		else if (user.isInSubGroup(group))
		{
			sendpMessage("User &6{0} &6has sub group {1}.", user.getName(), group);
		}
		else
		{
			sendpMessage("User &6{0} &6is not in group {1}.", user.getName(), group);
		}
	}
}