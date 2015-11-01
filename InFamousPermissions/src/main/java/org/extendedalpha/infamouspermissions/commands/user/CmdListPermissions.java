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

import java.util.List;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Permission;
import org.extendedalpha.pluginbase.subapi.types.StringJoiner;

/**
 * @author ExileDev
 */

public class CmdListPermissions extends UserCommand
{
	public CmdListPermissions(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "list";
		this.name = "permissions";
		this.description = "List a user''s permissions";
		this.permission = Permission.USER_LIST_PERMISSIONS;
	}

	@Override
	public void perform()
	{
		sendMessage("&7====[ &6{0} &7]====", user.getName());

		List<String> permissions = user.getPermissionNodes();
		if (! permissions.isEmpty())
		{
			sendMessage("&6Permissions&6: {0}", new StringJoiner("&6, &6").appendAll(permissions));
		}
		else
		{
			sendMessage("No user-specific permissions.");
		}

		StringJoiner joiner = new StringJoiner("&6, &6");
		joiner.append(user.getGroupName());
		joiner.appendAll(user.getSubGroupNames());

		sendMessage("&6And all from&6: {0}", joiner.toString());
	}
}