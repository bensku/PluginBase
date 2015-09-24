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

package org.extendedalpha.eapermissions.commands;

import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Permission;
import org.extendedalpha.eapermissions.types.User;

/**
 * @author exiledev
 */

public class CmdRealName extends EAPermissionsCommand
{
	public CmdRealName(EAPermissions plugin)
	{
		super(plugin);
		this.name = "realname";
		this.requiredArgs.add("player");
		this.description = "Get a player''s real name";
		this.permission = Permission.CMD_REALNAME;
	}

	@Override
	public void perform()
	{
		User user = getUser(0);
		if (user == null)
			return;

		sendpMessage("&r{0} &eis &b{1}&e.", user.getDisplayName(), user.getName());
	}
}