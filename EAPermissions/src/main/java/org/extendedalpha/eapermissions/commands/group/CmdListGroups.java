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

package org.extendedalpha.eapermissions.commands.group;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.extendedalpha.core.types.StringJoiner;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.commands.EAPermissionsCommand;
import org.extendedalpha.eapermissions.types.Permission;
import org.extendedalpha.eapermissions.types.WorldGroup;

/**
 * @author exiledev
 */

public class CmdListGroups extends EAPermissionsCommand
{
	public CmdListGroups(EAPermissions plugin)
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
		StringJoiner joiner = new StringJoiner("&b, &e");
		
		sendMessage("&3[ &eServer Groups &3]");

		Set<String> serverGroups = plugin.getPermissionHandler().getServerGroups().keySet();
		if (! serverGroups.isEmpty())
			sendMessage("  &e{0}", joiner.appendAll(serverGroups));
		else
			sendMessage("  &eNone");

		sendMessage("&3[ &eWorld Groups &3]");
		for (Entry<String, Map<String, WorldGroup>> entry : plugin.getPermissionHandler().getWorldGroups().entrySet())
		{
			Map<String, WorldGroup> groups = entry.getValue();
			if (! groups.isEmpty())
				sendMessage("  &b{0}&e: {1}", entry.getKey(), joiner.newString().appendAll(groups.keySet()));
		}
	}
}