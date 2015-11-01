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
import org.extendedalpha.infamouspermissions.types.ServerGroup;

/**
 * @author ExileDev
 */

public class CmdSetGroup extends UserCommand
{
	public CmdSetGroup(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "set";
		this.name = "group";
		this.requiredArgs.add("group");
		this.description = "Set a user's group";
		this.permission = Permission.USER_SET_GROUP;
	}

	@Override
	public void perform()
	{
		Group group = getGroup(0, world, true);
		if (group == null)
			return;

		if (group instanceof ServerGroup)
		{
			err("A user''s primary group cannot be a Server Group!");
			return;
		}

		user.setGroup(group);
		user.updatePermissions(true);

		sendpMessage("User &6{0} &6moved to group &6{1} &6in world &6{2}&6.", user.getName(), group.getName(), world.getName());
	}
}