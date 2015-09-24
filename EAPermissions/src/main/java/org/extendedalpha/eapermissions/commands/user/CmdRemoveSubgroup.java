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

public class CmdRemoveSubgroup extends UserCommand
{
	public CmdRemoveSubgroup(EAPermissions plugin)
	{
		super(plugin);
		this.action = "remove";
		this.name = "subgroup";
		this.aliases.add("sub");
		this.requiredArgs.add("group");
		this.description = "Remove a subgroup from a user";
		this.permission = Permission.USER_REMOVE_SUBGROUP;
	}

	@Override
	public void perform()
	{
		String group = args[0];
		if (! user.isInSubGroup(group))
		{
			sendpMessage("User &b{0} &eis not in sub group &b{1}&e.", user.getName(), group);
			return;
		}

		user.removeSubGroup(group);
		user.updatePermissions(true);

		sendpMessage("Sub group &b{0} &eremoved from user &b{1}&e''s permissions.", group, user.getName());
	}
}