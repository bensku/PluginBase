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

public class CmdHasOption extends GroupCommand
{
	public CmdHasOption(EAPermissions plugin)
	{
		super(plugin);
		this.action = "has";
		this.name = "option";
		this.requiredArgs.add("option");
		this.description = "Check if a group has an option";
		this.permission = Permission.GROUP_HAS_OPTION;
	}

	@Override
	public void perform()
	{
		String key = args[0];
		if (group.hasOption(key))
		{
			sendpMessage("Group &b{0} &ehas option ''&b{1}&e''", group.getName(), key);
			sendpMessage("Value: &b{0}", group.getOption(key));
		}
		else
		{
			sendpMessage("Group &b{0} &edoes not have option ''&b{1}&e''", group.getName(), key);
		}
	}
}