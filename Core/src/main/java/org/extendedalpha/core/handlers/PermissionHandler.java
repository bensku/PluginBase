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

package org.extendedalpha.core.handlers;

import org.extendedalpha.core.EAPermissionPlugin;
import org.extendedalpha.core.types.IPermission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles permissions.
 *
 * @author exiledev
 */

public class PermissionHandler
{
	private final String prefix;
	public PermissionHandler(String prefix)
	{
		this.prefix = prefix.toLowerCase() + ".";
	}

	public PermissionHandler(EAPermissionPlugin plugin)
	{
		this(plugin.getName());
	}

	/**
	 * Whether or not a {@link CommandSender} has a permission.
	 *
	 * @param sender Sender to check.
	 * @param permission Permission.
	 * @return True if they have the permission, false if not
	 */
	public final boolean hasPermission(CommandSender sender, IPermission permission)
	{
		return permission == null || hasPermission(sender, getPermissionString(permission));
	}

	/**
	 * Whether or not a {@link CommandSender} has a permission.
	 *
	 * @param sender Sender to check.
	 * @param permission Permission.
	 * @return True if they have the permission, false if not
	 */
	public final boolean hasPermission(CommandSender sender, String permission)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			return player.hasPermission(permission) || player.isOp();
		}

		return true;
	}

	/**
	 * Gets the complete permission string for a given {@link IPermission}.
	 *
	 * @param permission - Permission to get the node for.
	 * @return The complete permission string
	 */
	public final String getPermissionString(IPermission permission)
	{
		return prefix + permission.getNode().toLowerCase();
	}
}
