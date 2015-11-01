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
import org.extendedalpha.pluginbase.subapi.exception.BadTimeException;
import org.extendedalpha.pluginbase.subapi.util.TimeUtil;

/**
 * @author ExileDev
 */

public class CmdAddTemp extends UserCommand
{
	public CmdAddTemp(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "add";
		this.name = "temp";
		this.requiredArgs.add("permission");
		this.requiredArgs.add("time");
		this.description = "Temporarily add a permission";
		this.permission = Permission.USER_ADD_PERMISSION;
	}

	@Override
	public void perform()
	{
		String permission = args[0];
		if (user.hasPermission(permission))
		{
			sendpMessage("User &6{0} &6already has this permission.", user.getName());
			sendpMessage("Node: &6{0}", user.getMatchingPermission(permission));
			return;
		}

		long time;

		try
		{
			time = TimeUtil.parseTime(args[1]);
			if (time <= 0)
				throw new BadTimeException("Time must be greater than 0");
		}
		catch (BadTimeException ex)
		{
			err("Failed to parse time \"{0}\": {1}", args[1], ex);
			return;
		}

		user.addTempPermission(permission, System.currentTimeMillis() + time);
		user.updatePermissions(true);

		sendpMessage("Permission ''&6{0}&6'' added to user &6{1}&6''s permissions for &6{2}&6.", permission, user.getName(),
				TimeUtil.formatTime(time));
	}
}