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

import java.util.Arrays;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Permission;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;
import org.extendedalpha.pluginbase.subapi.util.NumberUtil;
import org.extendedalpha.pluginbase.subapi.util.Util;

/**
 * @author ExileDev
 */

public class CmdSetOption extends UserCommand
{
	public CmdSetOption(InFamousPermissions plugin)
	{
		super(plugin);
		this.action = "set";
		this.name = "option";
		this.requiredArgs.add("option");
		this.requiredArgs.add("value");
		this.description = "Set an option for a user";
		this.permission = Permission.USER_SET_OPTION;
	}

	@Override
	public void perform()
	{
		String key = args[0];
		Object val = null;

		String valStr = FormatUtil.join(" ", Arrays.copyOfRange(args, 1, args.length));
		valStr = valStr.replaceAll("\"", "");
		valStr = valStr.trim();

		if (valStr.contains("b:"))
			val = Util.toBoolean(valStr);
		else if (valStr.contains("i:"))
			val = NumberUtil.toInt(valStr);
		else if (valStr.contains("d:"))
			val = NumberUtil.toDouble(valStr);
		else if (valStr.equalsIgnoreCase("null") || valStr.isEmpty())
			val = null;
		else
			val = valStr;

		user.setOption(key, val);

		if (val == null)
			sendpMessage("Option ''&6{0}&6'' removed from user &6{1}&6.", key, user.getName());
		else
			sendpMessage("Option ''&6{0}&6'' set to ''&6{1}&6'' for user &6{2}&6.", key, val, user.getName());
	}
}