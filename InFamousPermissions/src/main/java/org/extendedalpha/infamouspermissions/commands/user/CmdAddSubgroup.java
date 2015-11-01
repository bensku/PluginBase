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
import org.extendedalpha.infamouspermissions.types.Group;
import org.extendedalpha.infamouspermissions.types.Permission;

/**
 * @author ExileDev
 */

public class CmdAddSubgroup extends UserCommand
{
	public CmdAddSubgroup(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "add";
		this.name = "subgroup";
		this.aliases.add("sub");
		this.requiredArgs.add("group");
		this.description = "Give a subgroup to a user";
		this.permission = Permission.USER_ADD_SUBGROUP;
	}

	@Override
	public void perform()
	{
		Group group = getGroup(0, world, true);
		if (group == null)
			return;

		if (user.isInGroup(group.getName()) || user.isInSubGroup(group.getName()))
		{
			sendpMessage("Group &6{0} &6is already available to user {1}.", group.getName(), user.getName());
			return;
		}

		user.addSubGroup(group);
		user.updatePermissions(true);

		sendpMessage("Group &6{0} &6added to user &6{1}&6''s permissions.", group.getName(), user.getName());
	}
}