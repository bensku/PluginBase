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

import java.util.Arrays;

import org.extendedalpha.core.util.FormatUtil;
import org.extendedalpha.core.util.NumberUtil;
import org.extendedalpha.core.util.Util;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Permission;

/**
 * @author exiledev
 */

public class CmdSetOption extends GroupCommand
{
	public CmdSetOption(EAPermissions plugin)
	{
		super(plugin);
		this.action = "set";
		this.name = "option";
		this.requiredArgs.add("option");
		this.requiredArgs.add("value");
		this.description = "Set an option for a group";
		this.permission = Permission.GROUP_SET_OPTION;
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

		group.setOption(key, val);

		if (val == null)
			sendpMessage("Option ''&b{0}&e'' removed from group &b{1}&e.", key, group.getName());
		else
			sendpMessage("Option ''&b{0}&e'' set to ''&b{1}&e'' for group &b{2}&e.", key, val, group.getName());
	}
}