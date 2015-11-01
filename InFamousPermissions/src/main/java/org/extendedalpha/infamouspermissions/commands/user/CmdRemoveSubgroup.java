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

public class CmdRemoveSubgroup extends UserCommand
{
	public CmdRemoveSubgroup(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "remove";
		this.name = "subgroup";
		this.aliases.add("sub");
		this.requiredArgs.add("group");
		this.description = "Remove a subgroup from a user";
		this.permission = Permission.USER_REMOVE_SUBGROUP;
	}

	@Override
	public void perform()
	{
		String group = args[0];
		if (! user.isInSubGroup(group))
		{
			sendpMessage("User &6{0} &6is not in sub group &6{1}&6.", user.getName(), group);
			return;
		}

		user.removeSubGroup(group);
		user.updatePermissions(true);

		sendpMessage("Sub group &6{0} &6removed from user &6{1}&6''s permissions.", group, user.getName());
	}
}