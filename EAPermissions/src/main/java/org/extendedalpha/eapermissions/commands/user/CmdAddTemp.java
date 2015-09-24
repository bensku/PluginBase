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

package org.extendedalpha.eapermissions.commands.user;

import org.extendedalpha.core.exception.BadTimeException;
import org.extendedalpha.core.util.TimeUtil;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Permission;

/**
 * @author exiledev
 */

public class CmdAddTemp extends UserCommand
{
	public CmdAddTemp(EAPermissions plugin)
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
			sendpMessage("User &b{0} &ealready has this permission.", user.getName());
			sendpMessage("Node: &b{0}", user.getMatchingPermission(permission));
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

		sendpMessage("Permission ''&b{0}&e'' added to user &b{1}&e''s permissions for &b{2}&e.", permission, user.getName(),
				TimeUtil.formatTime(time));
	}
}