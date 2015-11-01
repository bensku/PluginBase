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

package org.extendedalpha.infamouspermissions.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.extendedalpha.infamouspermissions.InFamousPermissions;

import org.bukkit.configuration.MemorySection;

/**
 * @author ExileDev
 */

public class ServerGroup extends Group
{
	public ServerGroup(InFamousPermissions plugin, String name)
	{
		super(plugin, name, null);
	}

	public ServerGroup(InFamousPermissions plugin, String name, MemorySection section)
	{
		this(plugin, name);
		this.loadFromDisk(section);
	}

	// ---- I/O

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> ret = new LinkedHashMap<>();

		ret.put("permissions", permissionNodes);
		ret.put("options", options);

		return ret;
	}

	@Override
	public boolean shouldBeSaved()
	{
		return true;
	}

	// ---- Permissions

	@Override
	public List<String> sortPermissions()
	{
		List<String> groupPerms = getPermissionNodes();
		groupPerms = getAllChildren(groupPerms);
		groupPerms = getMatchingNodes(groupPerms);
		return sort(groupPerms);
	}

	// ---- Parent Groups (Server Groups cannot have parents)

	@Override
	public boolean hasParentGroup()
	{
		return false;
	}

	@Override
	public boolean hasParentGroup(Group parent)
	{
		return false;
	}

	@Override
	public void addParentGroup(Group parent)
	{
		throw new IllegalStateException("Server Groups cannot have parents!");
	}

	@Override
	public void removeParentGroup(Group parent)
	{
		throw new IllegalStateException("Server Groups cannot have parents!");
	}

	@Override
	public String findPrefix()
	{
		return options.containsKey("prefix") ? (String) options.get("prefix") : "";
	}

	// ---- Utility

	@Override
	public void updatePermissions(boolean force)
	{
		updatePermissions(force, true);
	}

	@Override
	public void updatePermissions(boolean force, boolean children)
	{
		if (! permissions.isEmpty() && ! force)
			return;

		// Update permission map
		updatePermissionMap();

		if (! children)
			return;

		// Update any world groups that inherit this group
		for (Group group : plugin.getPermissionHandler().getAllGroups())
		{
			if (group.getParentGroups() != null && group.getParentGroups().contains(this))
				group.updatePermissions(force, children);
		}
	}

	@Override
	public String toString()
	{
		return "ServerGroup[name=" + name + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ServerGroup)
		{
			ServerGroup that = (ServerGroup) obj;
			return this.name.equals(that.name);
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 88;
		hash *= 1 + name.hashCode();
		return hash;
	}
}