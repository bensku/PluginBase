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

package org.extendedalpha.eapermissions.vault;

import org.extendedalpha.core.util.NumberUtil;
import org.extendedalpha.core.util.Util;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Group;
import org.extendedalpha.eapermissions.types.User;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.OfflinePlayer;

/**
 * @author exiledev
 */

public class EAChatVault extends Chat
{
	private final String name;
	private final EAPermissions plugin;

	public EAChatVault(EAPermissions plugin, Permission perms)
	{
		super(perms);
		this.plugin = plugin;
		this.name = plugin.getName() + " - Chat";
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public boolean isEnabled()
	{
		return plugin.isEnabled();
	}

	@Override
	public String getPlayerPrefix(String world, String player)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return "";

		return user.getPrefix();
	}

	@Override
	public void setPlayerPrefix(String world, String player, String prefix)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return;

		user.setPrefix(prefix);
	}

	@Override
	public String getPlayerSuffix(String world, String player)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return "";

		return user.getSuffix();
	}

	@Override
	public void setPlayerSuffix(String world, String player, String suffix)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return;

		user.setSuffix(suffix);
	}

	@Override
	public String getPlayerPrefix(String world, OfflinePlayer player)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return "";

		return user.getPrefix();
	}

	@Override
	public void setPlayerPrefix(String world, OfflinePlayer player, String prefix)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return;

		user.setPrefix(prefix);
	}

	@Override
	public String getPlayerSuffix(String world, OfflinePlayer player)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return "";

		return user.getSuffix();
	}

	@Override
	public void setPlayerSuffix(String world, OfflinePlayer player, String suffix)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return;

		user.setSuffix(suffix);
	}

	@Override
	public String getGroupPrefix(String world, String groupName)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return "";

		return group.getPrefix();
	}

	@Override
	public void setGroupPrefix(String world, String groupName, String prefix)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return;

		group.setPrefix(prefix);
	}

	@Override
	public String getGroupSuffix(String world, String groupName)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return "";

		return group.getSuffix();
	}

	@Override
	public void setGroupSuffix(String world, String groupName, String suffix)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return;

		group.setSuffix(suffix);
	}

	// ---- Options

	private Object getPlayerInfo(String world, String player, String node, Object def)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return def;

		if (user.hasOption(node))
			return user.getOption(node);

		return def;
	}

	private void setPlayerInfo(String world, String player, String node, Object val)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return;

		user.setOption(node, val);
	}

	private Object getPlayerInfo(String world, OfflinePlayer player, String node, Object def)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return def;

		if (user.hasOption(node))
			return user.getOption(node);

		return def;
	}

	private void setPlayerInfo(String world, OfflinePlayer player, String node, Object val)
	{
		User user = plugin.getPermissionHandler().getUser(world, player);
		if (user == null)
			return;

		user.setOption(node, val);
	}

	private Object getGroupInfo(String world, String groupName, String node, Object def)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return def;

		if (group.hasOption(node))
			return group.getOption(node);

		return def;
	}

	private void setGroupInfo(String world, String groupName, String node, Object val)
	{
		Group group = plugin.getPermissionHandler().getGroup(world, groupName);
		if (group == null)
			return;

		group.setOption(node, val);
	}

	@Override
	public int getPlayerInfoInteger(String world, String player, String node, int defaultValue)
	{
		return NumberUtil.toInt(getPlayerInfo(world, player, node, defaultValue));
	}

	@Override
	public void setPlayerInfoInteger(String world, String player, String node, int value)
	{
		setPlayerInfo(world, player, node, value);
	}

	@Override
	public int getPlayerInfoInteger(String world, OfflinePlayer player, String node, int defaultValue)
	{
		return NumberUtil.toInt(getPlayerInfo(world, player, node, defaultValue));
	}

	@Override
	public void setPlayerInfoInteger(String world, OfflinePlayer player, String node, int value)
	{
		setPlayerInfo(world, player, node, value);
	}

	@Override
	public int getGroupInfoInteger(String world, String groupName, String node, int defaultValue)
	{
		return NumberUtil.toInt(getGroupInfo(world, groupName, node, defaultValue));
	}

	@Override
	public void setGroupInfoInteger(String world, String group, String node, int value)
	{
		setGroupInfo(world, group, node, value);
	}

	@Override
	public double getPlayerInfoDouble(String world, String player, String node, double defaultValue)
	{
		return NumberUtil.toDouble(getPlayerInfo(world, player, node, defaultValue));
	}

	@Override
	public void setPlayerInfoDouble(String world, String player, String node, double value)
	{
		setPlayerInfo(world, player, node, value);
	}

	@Override
	public double getPlayerInfoDouble(String world, OfflinePlayer player, String node, double defaultValue)
	{
		return NumberUtil.toDouble(getPlayerInfo(world, player, node, defaultValue));
	}

	@Override
	public void setPlayerInfoDouble(String world, OfflinePlayer player, String node, double value)
	{
		setPlayerInfo(world, player, node, value);
	}

	@Override
	public double getGroupInfoDouble(String world, String group, String node, double defaultValue)
	{
		return NumberUtil.toDouble(getGroupInfo(world, group, node, defaultValue));
	}

	@Override
	public void setGroupInfoDouble(String world, String group, String node, double value)
	{
		setGroupInfo(world, group, node, value);
	}

	@Override
	public boolean getPlayerInfoBoolean(String world, String player, String node, boolean defaultValue)
	{
		return Util.toBoolean(getPlayerInfo(world, player, node, defaultValue));
	}

	@Override
	public void setPlayerInfoBoolean(String world, String player, String node, boolean value)
	{
		setPlayerInfo(world, player, node, value);
	}

	@Override
	public boolean getPlayerInfoBoolean(String world, OfflinePlayer player, String node, boolean defaultValue)
	{
		return Util.toBoolean(getPlayerInfo(world, player, node, defaultValue));
	}

	@Override
	public void setPlayerInfoBoolean(String world, OfflinePlayer player, String node, boolean value)
	{
		setPlayerInfo(world, player, node, value);
	}

	@Override
	public boolean getGroupInfoBoolean(String world, String group, String node, boolean defaultValue)
	{
		return Util.toBoolean(getGroupInfo(world, group, node, defaultValue));
	}

	@Override
	public void setGroupInfoBoolean(String world, String group, String node, boolean value)
	{
		setGroupInfo(world, group, node, value);
	}

	@Override
	public String getPlayerInfoString(String world, String player, String node, String defaultValue)
	{
		return getPlayerInfo(world, player, node, defaultValue).toString();
	}

	@Override
	public void setPlayerInfoString(String world, String player, String node, String value)
	{
		setPlayerInfo(world, player, node, value);
	}

	@Override
	public String getPlayerInfoString(String world, OfflinePlayer player, String node, String defaultValue)
	{
		return getPlayerInfo(world, player, node, defaultValue).toString();
	}

	@Override
	public void setPlayerInfoString(String world, OfflinePlayer player, String node, String value)
	{
		setPlayerInfo(world, player, node, value);
	}

	@Override
	public String getGroupInfoString(String world, String group, String node, String defaultValue)
	{
		return getGroupInfo(world, group, node, defaultValue).toString();
	}

	@Override
	public void setGroupInfoString(String world, String group, String node, String value)
	{
		setGroupInfo(world, group, node, value);
	}
}