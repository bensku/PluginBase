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
import org.extendedalpha.pluginbase.subapi.util.TimeUtil;

/**
 * @author ExileDev
 */

public class CmdAddTemp extends GroupCommand
{
	public CmdAddTemp(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "add";
		this.name = "temp";
		this.requiredArgs.add("permission");
		this.requiredArgs.add("time");
		this.description = "Temporarily add a permission";
		this.permission = Permission.GROUP_ADD_PERMISSION;
	}

	@Override
	public void perform()
	{
		String permission = args[0];
		if (group.hasPermission(permission))
		{
			sendpMessage("Group &6{0} &6already has this permission.", group.getName());
			sendpMessage("Node: &6{0}", group.getMatchingPermission(permission));
			return;
		}

		long time;

		try
		{
			time = TimeUtil.parseTime(args[1]);
			if (time <= 0)
				throw new Exception();
		}
		catch (Throwable ex)
		{
			err("Please specify a valid time!");
			return;
		}

		group.addTempPermission(permission, System.currentTimeMillis() + time);
		group.updatePermissions(true, true);

		sendpMessage("Permission ''&6{0}&6'' added to group &6{1}&6''s permissions for &6{2}&6.", permission, group.getName(),
				TimeUtil.formatTime(time));
	}
}