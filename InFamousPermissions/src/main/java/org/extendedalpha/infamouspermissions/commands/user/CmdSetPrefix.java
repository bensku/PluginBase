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
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * @author ExileDev
 */

public class CmdSetPrefix extends UserCommand
{
	public CmdSetPrefix(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "set";
		this.name = "prefix";
		this.requiredArgs.add("prefix");
		this.description = "Set a user's prefix";
		this.permission = Permission.USER_SET_PREFIX;
	}

	@Override
	public void perform()
	{
		String prefix = FormatUtil.join(" ", args);
		prefix = prefix.replaceAll("\"", "");

		if (prefix.equalsIgnoreCase("null") || prefix.equalsIgnoreCase("remove"))
		{
			user.resetPrefix();
			sendpMessage("&6{0} &6prefix has been reset.", user.describeTo(sender, true));
			return;
		}

		user.setPrefix(prefix);

		sendpMessage("&6{0} &6prefix is now \"&r{1}&6\"", user.describeTo(sender, true), prefix);
	}
}