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

package org.extendedalpha.eapermissions.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.extendedalpha.eapermissions.EAPermissions;

import org.bukkit.configuration.MemorySection;

/**
 * @author exiledev
 */

public class WorldGroup extends Group
{
	private boolean defaultGroup;

	public WorldGroup(EAPermissions plugin, String name, String world)
	{
		super(plugin, name, world);
	}

	public WorldGroup(EAPermissions plugin, String name, String world, MemorySection section)
	{
		this(plugin, name, world);
		this.loadFromDisk(section);
	}

	// ---- I/O

	@Override
	public void loadFromDisk(MemorySection section)
	{
		super.loadFromDisk(section);
		this.defaultGroup = section.getBoolean("default", false);
		this.parents = section.getStringList("parents");
	}

	public void loadParentGroups()
	{
		for (String parent : parents)
		{
			Group group = plugin.getPermissionHandler().getGroupRaw(worldName, parent);
			if (group != null)
				parentGroups.add(group);
			else
				plugin.getLogHandler().log(Level.WARNING, "Could not find parent group \"{0}\" for group {1}", parent, name);
		}
	}

	@Override
	public boolean shouldBeSaved()
	{
		return true;
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> ret = new LinkedHashMap<String, Object>();

		ret.put("default", defaultGroup);
		ret.put("permissions", permissionNodes);
		ret.put("parents", parents);

		if (! timestamps.isEmpty())
			ret.put("timestamps", timestamps);
		if (! options.isEmpty())
			ret.put("options", options);

		return ret;
	}

	// ---- Getters

	@Override
	public List<String> getAllPermissionNodes()
	{
		List<String> ret = new ArrayList<String>();

		// Add parent nodes
		ret.addAll(getParentNodes());

		// Add group nodes
		ret.addAll(getPermissionNodes());

		return ret;
	}

	private final List<String> getParentNodes()
	{
		List<String> ret = new ArrayList<String>();

		// Add all nodes from parent groups
		if (parents != null)
		{
			for (Group parent : parentGroups)
				ret.addAll(parent.getAllPermissionNodes());
		}

		return ret;
	}

	private final Map<String, Boolean> getParentPermissions()
	{
		Map<String, Boolean> ret = new LinkedHashMap<String, Boolean>();

		if (parents != null)
		{
			for (Group parent : parentGroups)
				ret.putAll(parent.getPermissions());
		}

		return ret;
	}

	@Override
	public List<String> sortPermissions()
	{
		Map<String, Boolean> permissions = new LinkedHashMap<String, Boolean>();

		// Add parent permissions first
		permissions.putAll(getParentPermissions());

		// Add group-specific nodes last
		List<String> groupNodes = getPermissionNodes();
		groupNodes = getAllChildren(groupNodes);
		groupNodes = getMatchingNodes(groupNodes);

		for (String groupNode : groupNodes)
		{
			boolean value = ! groupNode.startsWith("-");
			permissions.put(value ? groupNode : groupNode.substring(1), value);
		}

		List<String> ret = new ArrayList<String>();

		for (Entry<String, Boolean> entry : permissions.entrySet())
		{
			String node = entry.getKey();
			boolean value = entry.getValue();
			ret.add(value ? node : "-" + node);
		}

		// Sort and return
		return sort(ret);
	}

	// ---- Parent Groups

	@Override
	public boolean hasParentGroup()
	{
		return parents.size() > 0;
	}

	@Override
	public boolean hasParentGroup(Group parent)
	{
		return hasParentGroup() && parentGroups.contains(parent);
	}

	@Override
	public void addParentGroup(Group parent)
	{
		parentGroups.add(parent);
		parents.add(parent.getName());
	}

	@Override
	public void removeParentGroup(Group parent)
	{
		parentGroups.remove(parent);
		parents.remove(parent.getName());
	}

	/**
	 * @deprecated For conversion use ONLY
	 */
	@Deprecated
	public void setParentGroups(List<String> parents)
	{
		this.parents = parents;
	}

	// ---- Default

	public boolean isDefaultGroup()
	{
		return defaultGroup;
	}

	public void setIsDefaultGroup(boolean def)
	{
		this.defaultGroup = def;
	}

	// ---- Utility

	@Override
	public void updatePermissions(boolean force)
	{
		updatePermissions(force, true);
	}

	@Override
	public void updatePermissions(boolean force, boolean users)
	{
		if (! permissions.isEmpty() && ! force)
			return;

		// Update our permissions
		updatePermissionMap();

		// Update child groups
		for (Group group : plugin.getPermissionHandler().getGroups(worldName))
		{
			if (group.getParentGroups() != null && group.getParentGroups().contains(this))
				group.updatePermissions(force, users);
		}

		if (! users)
			return;

		// Update users with this group
		for (User user : plugin.getPermissionHandler().getUsers(worldName))
		{
			if (user.getGroup().equals(this) || user.getSubGroups().contains(this))
				user.updatePermissions(force);
		}
	}

	// ---- Generic Methods

	@Override
	public String toString()
	{
		return "WorldGroup[name=" + name + ", world=" + worldName + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof WorldGroup)
		{
			WorldGroup that = (WorldGroup) obj;
			return this.name.equals(that.name) && this.worldName.equals(that.worldName);
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 89;
		hash *= 1 + name.hashCode();
		hash *= 1 + worldName.hashCode();
		return hash;
	}
}