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

package org.extendedalpha.infamouspermissions.vault;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Group;
import org.extendedalpha.infamouspermissions.types.ServerGroup;
import org.extendedalpha.infamouspermissions.types.User;
import org.extendedalpha.infamouspermissions.types.WorldGroup;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author ExileDev
 */

public class InFamousPermissionsVault extends Permission
{
	private final String name;
	private final InFamousPermissions plugin;

	public InFamousPermissionsVault(InFamousPermissions plugin)
	{
		this.plugin = plugin;
		this.name = plugin.getName();
	}

	@Override
	public String[] getGroups()
	{
		Set<String> ret = new HashSet<String>();

		Map<String, Map<String, WorldGroup>> groupMaps = plugin.getPermissionHandler().getWorldGroups();
		for (Entry<String, Map<String, WorldGroup>> entry : groupMaps.entrySet())
		{
			ret.addAll(entry.getValue().keySet());
		}

		Map<String, ServerGroup> serverGroups = plugin.getPermissionHandler().getServerGroups();
		ret.addAll(serverGroups.keySet());

		return ret.toArray(new String[0]);
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String[] getPlayerGroups(String world, String player)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return null;

		return user.getGroups().toArray(new String[0]);
	}

	@Override
	public String getPrimaryGroup(String world, String player)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return "";

		Group group = user.getGroup();
		if (group == null)
			return "";

		return group.getName();
	}

	@Override
	public String[] getPlayerGroups(String world, OfflinePlayer player)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return null;

		return user.getGroups().toArray(new String[0]);
	}

	@Override
	public String getPrimaryGroup(String world, OfflinePlayer player)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return "";

		Group group = user.getGroup();
		if (group == null)
			return "";

		return group.getName();
	}

	@Override
	public boolean groupAdd(String world, String groupName, String permission)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return false;

		group.addPermission(permission);
		group.updatePermissions(true);
		return true;
	}

	@Override
	public boolean groupHas(String world, String groupName, String permission)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return false;

		return group.hasPermission(permission);
	}

	@Override
	public boolean groupRemove(String world, String groupName, String permission)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return false;

		group.removePermission(permission);
		group.updatePermissions(true);
		return true;
	}

	@Override
	public boolean hasGroupSupport()
	{
		return true;
	}

	@Override
	public boolean hasSuperPermsCompat()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return plugin.isEnabled();
	}

	@Override
	public boolean playerAdd(String world, String player, String permission)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		user.addPermission(permission);
		user.updatePermissions(true);
		return true;
	}

	@Override
	public boolean playerAddGroup(String world, String player, String groupName)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return false;

		user.addSubGroup(group);
		user.updatePermissions(true);
		return true;
	}

	@Override
	public boolean playerHas(String world, String player, String permission)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		return user.hasPermission(permission);
	}

	@Override
	public boolean playerHas(Player player, String permission)
	{
		User user = plugin.getPermissionHandler().getUser(player);
		if (user == null)
			return false;

		return user.hasPermission(permission);
	}

	@Override
	public boolean playerInGroup(String world, String player, String group)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		return user.isGroupApplicable(group);
	}

	@Override
	public boolean playerRemove(String world, String player, String permission)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		user.removePermission(permission);
		user.updatePermissions(true);
		return true;
	}

	@Override
	public boolean playerRemoveGroup(String world, String player, String group)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		if (! user.isInSubGroup(group))
			return false;

		user.removeSubGroup(group);
		user.updatePermissions(true);
		return true;
	}

	@Override
	public boolean playerAdd(String world, OfflinePlayer player, String permission)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		user.addPermission(permission);
		user.updatePermissions(true);
		return true;
	}

	@Override
	public boolean playerAddGroup(String world, OfflinePlayer player, String groupName)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return false;

		user.addSubGroup(group);
		user.updatePermissions(true);
		return true;
	}

	@Override
	public boolean playerHas(String world, OfflinePlayer player, String permission)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		return user.hasPermission(permission);
	}

	@Override
	public boolean playerInGroup(String world, OfflinePlayer player, String group)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		return user.isInGroup(group);
	}

	@Override
	public boolean playerRemove(String world, OfflinePlayer player, String permission)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		user.removePermission(permission);
		user.updatePermissions(true);
		return true;
	}

	@Override
	public boolean playerRemoveGroup(String world, OfflinePlayer player, String group)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return false;

		if (! user.isInSubGroup(group))
			return false;

		user.removeSubGroup(group);
		user.updatePermissions(true);
		return true;
	}
}