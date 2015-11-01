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

package org.extendedalpha.infamouspermissions.conversion;

import java.io.File;

import org.extendedalpha.infamouspermissions.InFamousPermissions;

/**
 * @author ExileDev
 */

public class ConversionHandler
{
	public static void convert(InFamousPermissions plugin)
	{
		// Obtain plugins folder
		File dataFolder = plugin.getDataFolder();
		dataFolder.mkdirs();

		File plugins = dataFolder.getParentFile();

		// Check for GroupManager
		File groupManager = new File(plugins, "GroupManager");
		if (groupManager.exists())
		{
			new GroupManagerConverter(groupManager, plugin).convert();
			return;
		}

		// Check for PEX
		File pex = new File(plugins, "PermissionsEx");
		if (pex.exists())
		{
			File perms = new File(pex, "permissions.yml");
			new PermissionsExConverter(perms, plugin).convert();
		}
	}
}