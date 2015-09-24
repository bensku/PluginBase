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

/**
 * @author exiledev
 */

public class CmdCleanUp extends EAPermissionsCommand
{
	public CmdCleanUp(EAPermissions plugin)
	{
		super(plugin);
		this.name = "cleanup";
		this.aliases.add("unload");
		this.description = "Unload offline users to save memory";
		this.permission = Permission.CMD_SAVE;
		this.usesPrefix = true;
	}

	@Override
	public void perform()
	{
		long start = System.currentTimeMillis();

		plugin.getLogHandler().log("Unloading offline users...");
		sendpMessage("&eUnloading offline users...");

		plugin.getPermissionHandler().cleanupUsers(0);
		System.gc();

		sendpMessage("&aUnload complete. Took {0} ms.", System.currentTimeMillis() - start);
		plugin.getLogHandler().log("Unload complete. Took {0} ms.", System.currentTimeMillis() - start);
	}
}