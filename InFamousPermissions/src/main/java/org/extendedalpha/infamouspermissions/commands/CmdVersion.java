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

public class CmdVersion extends InFamousPermissionsCommand
{
	public CmdVersion(InFamousPermissions plugin)
	{
		super(plugin);
		this.name = "version";
		this.aliases.add("v");
		this.description = "Displays version information";
		this.permission = Permission.CMD_VERSION;
		this.usesPrefix = true;
	}

	@Override
	public void perform()
	{
		sendMessage("&7====[ &6InFamousPermissions &7]====");
		sendMessage("&6Version&7: {0}", plugin.getDescription().getVersion());
		sendMessage("&6Author&7: ExtendedAlpha");
		sendMessage("&6Website&7: http://www.extendedalpha.org/");
	}
}