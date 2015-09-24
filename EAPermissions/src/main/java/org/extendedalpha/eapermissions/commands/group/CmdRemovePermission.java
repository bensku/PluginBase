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

package org.extendedalpha.eapermissions.commands.group;

import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Permission;

/**
 * @author exiledev
 */

public class CmdRemovePermission extends GroupCommand
{
	public CmdRemovePermission(EAPermissions plugin)
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
			sendpMessage("Group &b{0} &edoes not have permission ''&b{1}&e.''", group.getName(), permission);
			return;
		}

		group.removePermission(permission);
		group.updatePermissions(true, true);

		sendpMessage("Permission ''&b{0}&e'' removed from group &b{1}&e''s permissions.", permission, group.getName());
	}
}