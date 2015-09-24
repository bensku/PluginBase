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

import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Permission;

/**
 * @author exiledev
 */

public class CmdHasGroup extends UserCommand
{
	public CmdHasGroup(EAPermissions plugin)
	{
		super(plugin);
		this.action = "has";
		this.name = "group";
		this.requiredArgs.add("group");
		this.description = "Checks if a user is in a group";
		this.permission = Permission.USER_HAS_GROUP;
	}

	@Override
	public void perform()
	{
		String group = args[0];
		if (user.isInGroup(group))
		{
			sendpMessage("User &b{0} &eis in group &b{1}&e.", user.getName(), group);
		}
		else if (user.isInSubGroup(group))
		{
			sendpMessage("User &b{0} &ehas sub group {1}.", user.getName(), group);
		}
		else
		{
			sendpMessage("User &b{0} &eis not in group {1}.", user.getName(), group);
		}
	}
}