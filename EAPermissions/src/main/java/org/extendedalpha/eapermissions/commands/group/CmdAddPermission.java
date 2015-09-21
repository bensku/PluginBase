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

public class CmdAddPermission extends GroupCommand
{
	public CmdAddPermission(EAPermissions plugin)
	{
		super(plugin);
		this.action = "add";
		this.name = "permission";
		this.aliases.add("perm");
		this.requiredArgs.add("permission");
		this.description = "Give a group a permission";
		this.permission = Permission.GROUP_ADD_PERMISSION;
	}

	@Override
	public void perform()
	{
		String node = args[0];
		if (node.contains("**"))
		{
			err("Permission \"&c{0}&4\" contains invalid characters: &cdouble star&4!", permission);
			return;
		}

		boolean negative = node.startsWith("-");
		String permission = negative ? node.substring(1) : node;

		if (group.hasPermission(permission) && ! negative)
		{
			sendpMessage("Group &b{0} &ealready has access to this permission.", group.getName());
			String matchingPerm = group.getMatchingPermission(permission);
			if (matchingPerm != null)
				sendpMessage("Node: &b{0}", group.getMatchingPermission(permission));
			return;
		}

		if (group.hasPermissionNode(node))
		{
			sendpMessage("Group &b{0} &ealready has this node.", group.getName());
			return;
		}

		group.addPermission(node);
		group.updatePermissions(true, true);

		sendpMessage("Permission ''&b{0}&e'' added to group &b{1}&e''s permissions.", node, group.getName());
	}
}