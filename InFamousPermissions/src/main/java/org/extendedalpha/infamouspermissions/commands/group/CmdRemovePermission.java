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

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Permission;

/**
 * @author ExileDev
 */

public class CmdRemovePermission extends GroupCommand
{
	public CmdRemovePermission(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "remove";
		this.name = "permission";
		this.aliases.add("perm");
		this.requiredArgs.add("permission");
		this.description = "Remove a permission from a group";
		this.permission = Permission.GROUP_REMOVE_PERMISSION;
	}

	@Override
	public void perform()
	{
		String permission = args[0];
		if (! group.hasPermissionNode(permission))
		{
			sendpMessage("Group &6{0} &6does not have permission ''&6{1}&6.''", group.getName(), permission);
			return;
		}

		group.removePermission(permission);
		group.updatePermissions(true, true);

		sendpMessage("Permission ''&6{0}&6'' removed from group &6{1}&6''s permissions.", permission, group.getName());
	}
}