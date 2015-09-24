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
import org.extendedalpha.eapermissions.types.Group;
import org.extendedalpha.eapermissions.types.Permission;

/**
 * @author exiledev
 */

public class CmdCreateGroup extends EAPermissionsCommand
{
	public CmdCreateGroup(EAPermissions plugin)
	{
		super(plugin);
		this.name = "create";
		this.requiredArgs.add("group");
		this.description = "Creates a group";
		this.permission = Permission.GROUP_CREATE;
		this.usesPrefix = true;
	}

	@Override
	public void perform()
	{
		String name = args[0];
		boolean server = name.startsWith("s:");
		name = server ? name.substring(2) : name;
		
		if (! name.matches("^[a-zA-Z_0-9]+$"))
		{
			err("Name contains invalid characters!");
			return;
		}

		Group group;
		if (server) group = plugin.getPermissionHandler().createServerGroup(name);
		else group = plugin.getPermissionHandler().createWorldGroup(name, getWorld());

		if (group == null)
		{
			err("Failed to create group! Contact an administrator!");
			return;
		}

		// TODO: Some sort of creation wizard?zO

		sendpMessage("Group &b{0} &esuccessfully created!", group.getName());
	}
}