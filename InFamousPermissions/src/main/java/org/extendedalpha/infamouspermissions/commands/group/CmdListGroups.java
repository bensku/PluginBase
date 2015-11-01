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

package org.extendedalpha.infamouspermissions.commands.group;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.commands.InFamousPermissionsCommand;
import org.extendedalpha.infamouspermissions.types.Permission;
import org.extendedalpha.infamouspermissions.types.WorldGroup;
import org.extendedalpha.pluginbase.subapi.types.StringJoiner;

/**
 * @author ExileDev
 */

public class CmdListGroups extends InFamousPermissionsCommand
{
	public CmdListGroups(InFamousPermissions plugin)
	{
		super(plugin);
		this.name = "listgroups";
		this.description = "List available groups";
		this.permission = Permission.GROUP_LIST;
		this.usesPrefix = true;
	}

	@Override
	public void perform()
	{
		StringJoiner joiner = new StringJoiner("&6, &6");
		
		sendMessage("&7[ &6Server Groups &7]");

		Set<String> serverGroups = plugin.getPermissionHandler().getServerGroups().keySet();
		if (! serverGroups.isEmpty())
			sendMessage("  &6{0}", joiner.appendAll(serverGroups));
		else
			sendMessage("  &6None");

		sendMessage("&7[ &6World Groups &7]");
		for (Entry<String, Map<String, WorldGroup>> entry : plugin.getPermissionHandler().getWorldGroups().entrySet())
		{
			Map<String, WorldGroup> groups = entry.getValue();
			if (! groups.isEmpty())
				sendMessage("  &6{0}&6: {1}", entry.getKey(), joiner.newString().appendAll(groups.keySet()));
		}
	}
}