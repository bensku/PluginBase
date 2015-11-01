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

public class CmdRemoveTemp extends UserCommand
{
	public CmdRemoveTemp(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "remove";
		this.name = "temp";
		this.requiredArgs.add("permission");
		this.description = "Removed a temp permission";
		this.permission = Permission.USER_REMOVE_PERMISSION;
	}

	@Override
	public void perform()
	{
		String permission = args[0];
		if (! user.hasTempPermission(permission))
		{
			sendpMessage("User &6{0} &6does not have temp permission ''&6{1}&6.''", user.getName(), permission);
			return;
		}

		user.removeTempPermission(permission);
		user.updatePermissions(true);

		sendpMessage("Temp permission ''&6{0}&6'' removed from user &6{1}&6''s permissions.", permission, user.getName());
	}
}