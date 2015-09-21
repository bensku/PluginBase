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

import org.extendedalpha.core.util.FormatUtil;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Permission;

/**
 * @author exiledev
 */

public class CmdSetSuffix extends UserCommand
{
	public CmdSetSuffix(EAPermissions plugin)
	{
		super(plugin);
		this.action = "set";
		this.name = "suffix";
		this.requiredArgs.add("suffix");
		this.description = "Set a user's suffix";
		this.permission = Permission.USER_SET_SUFFIX;
	}

	@Override
	public void perform()
	{
		String suffix = FormatUtil.join(" ", args);
		suffix = suffix.replaceAll("\"", "");

		if (suffix.equalsIgnoreCase("null") || suffix.equalsIgnoreCase("remove"))
		{
			user.resetPrefix();
			sendpMessage("&b{0} &esuffix has been reset.", user.describeTo(sender, true));
			return;
		}

		user.setSuffix(suffix);

		sendpMessage("&b{0} &esuffix is now \"&r{1}&e\"", user.describeTo(sender, true), suffix);
	}
}