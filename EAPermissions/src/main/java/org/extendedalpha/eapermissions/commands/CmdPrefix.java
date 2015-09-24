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

import org.extendedalpha.core.util.FormatUtil;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Permission;
import org.extendedalpha.eapermissions.types.User;

import org.bukkit.ChatColor;

/**
 * @author exiledev
 */

public class CmdPrefix extends EAPermissionsCommand
{
	public CmdPrefix(EAPermissions plugin)
	{
		super(plugin);
		this.name = "prefix";
		this.aliases.add("pre");
		this.requiredArgs.add("prefix");
		this.description = "Change your prefix";
		this.permission = Permission.CMD_PREFIX;
		this.mustBePlayer = true;
	}

	@Override
	public void perform()
	{
		User user = getUser(true);
		if (user == null)
			return;

		String prefix = FormatUtil.join(" ", args);
		prefix = prefix.replaceAll("\"", "");

		String argsCheck = ChatColor.stripColor(FormatUtil.replaceColors(prefix));
		argsCheck = argsCheck.replaceAll("\\[", "").replaceAll("\\]", "");

		// Perform and enforce args check
		int maxLength = plugin.getConfig().getInt("prefix.maxLength");
		if (argsCheck.length() > maxLength)
		{
			err("Your prefix is too long! (Max &c{0} &4characters)", maxLength);
			return;
		}

		// Trim the string
		prefix = prefix.trim();

		if (prefix.isEmpty())
		{
			sendpMessage("&eYour prefix has been reset!");
			user.resetPrefix();
			return;
		}

		// Apply configuration settings
		if (plugin.getConfig().getBoolean("prefix.forceReset"))
			prefix = prefix + "&r";
		if (plugin.getConfig().getBoolean("prefix.forceSpace"))
			prefix = prefix + " ";

		user.setPrefix(prefix);

		sendpMessage("&eYour prefix is now \"{0}&e\"", FormatUtil.replaceColors(prefix));
	}
}