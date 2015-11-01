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

package org.extendedalpha.infamouspermissions.commands;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Permission;

/**
 * @author ExileDev
 */

public class CmdReload extends InFamousPermissionsCommand
{
	public CmdReload(InFamousPermissions plugin)
	{
		super(plugin);
		this.name = "reload";
		this.aliases.add("rl");
		this.optionalArgs.add("type");
		this.description = "reload InFamousPermissions";
		this.permission = Permission.CMD_RELOAD;
		this.mustBePlayer = false;
		this.usesPrefix = true;
	}

	@Override
	public void perform()
	{
		long start = System.currentTimeMillis();

		if (args.length > 0 && args[0].equalsIgnoreCase("config"))
		{
			plugin.getLogHandler().log("Reloading InFamousPermissions config...");
			sendpMessage("&6Reloading &6InFamousPermissions &6config...");

			plugin.reloadConfig();
			plugin.getChatHandler().reload();
		}
		else
		{
			plugin.getLogHandler().log("Reloading InFamousPermissions...");
			sendpMessage("&6Reloading &6InFamousPermissions&6...");

			plugin.reload();
		}

		sendpMessage("&aReload complete. Took {0} ms.", System.currentTimeMillis() - start);
		plugin.getLogHandler().log("Reload complete. Took {0} ms.", System.currentTimeMillis() - start);
	}
}