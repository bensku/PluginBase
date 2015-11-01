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

import java.util.List;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Group;
import org.extendedalpha.infamouspermissions.types.Permission;
import org.extendedalpha.pluginbase.subapi.types.StringJoiner;

/**
 * @author ExileDev
 */

public class CmdListPermissions extends GroupCommand
{
	public CmdListPermissions(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "list";
		this.name = "permissions";
		this.description = "List a group''s permissions";
		this.permission = Permission.GROUP_LIST_PERMISSIONS;
	}

	@Override
	public void perform()
	{
		sendMessage("&7====[ &6{0} &7]====", group.getName());

		List<String> permissions = group.getPermissionNodes();
		if (! permissions.isEmpty())
		{
			sendMessage("&6Permissions&6: {0}", new StringJoiner("&6, &6").appendAll(permissions.toArray(new String[0])));
		}
		else
		{
			sendMessage("No group-specific permissions.");
		}

		List<Group> parents = group.getParentGroups();
		if (! parents.isEmpty())
		{
			StringJoiner joiner = new StringJoiner("&6, &6");
			for (Group parent : parents)
				joiner.append(parent.getName());
	
			sendMessage("&6And all from&6: {0}", joiner.toString());
		}
	}
}