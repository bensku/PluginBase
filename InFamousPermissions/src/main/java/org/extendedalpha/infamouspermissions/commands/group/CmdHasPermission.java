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

public class CmdHasPermission extends GroupCommand
{
	public CmdHasPermission(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "has";
		this.name = "permission";
		this.requiredArgs.add("permission");
		this.description = "Checks if a group has a permission";
		this.permission = Permission.GROUP_HAS_PERMISSION;
	}

	@Override
	public void perform()
	{
		String permission = args[0];
		if (group.hasPermission(permission))
		{
			sendpMessage("Group &6{0} &6has access to permission &6{1}&6.", group.getName(), permission);

			String node = group.getMatchingPermission(permission);
			if (node != null && ! node.equalsIgnoreCase(permission))
			{
				sendpMessage("Matching node: &6{0}", node);
			}
		}
		else
		{
			sendpMessage("Group &6{0} &6does not have access to permission &6{1}&6.", group.getName(), permission);

			String node = group.getMatchingPermission(permission);
			if (node != null)
				sendpMessage("Negated, node: &6{0}", node);
		}
	}
}