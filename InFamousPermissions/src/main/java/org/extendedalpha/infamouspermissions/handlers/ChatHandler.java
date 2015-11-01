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

package org.extendedalpha.infamouspermissions.handlers;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.types.Permission;
import org.extendedalpha.infamouspermissions.types.User;

import org.bukkit.entity.Player;
import org.extendedalpha.pluginbase.subapi.types.Reloadable;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * @author ExileDev
 */

public class ChatHandler implements Reloadable
{
	private boolean formatChat;
	private String chatFormat;

	private final InFamousPermissions plugin;
	public ChatHandler(InFamousPermissions plugin)
	{
		this.plugin = plugin;
		this.reload();
	}

	/**
	 * Parses a given chat message, replacing defined variables.
	 *
	 * @param player Player chatting
	 * @param message Message sent
	 * @return Formatted chat message
	 */
	public final String parseChatMessage(Player player, String message)
	{
		if (! formatChat)
			return message;

		User user = plugin.getPermissionHandler().getUser(player);
		if (user == null)
			return message;

		// Replace Variables
		String format = getChatFormat(user)
				.replace("{prefix}", user.getPrefix())
				.replace("{name}", user.getDisplayName())
				.replace("{suffix}", user.getSuffix())
				.replace("{world}", player.getWorld().getName());

		// Chat color
		if (user.hasOption("chatColor"))
			format = format.replace(":", user.getOption("chatColor") + ":");

		// Escape pesky % characters
		message = message.replace("%", "%%");

		// Disallow fancy chat formatting if they don't have permission
		if (! plugin.getPermissionHandler().hasPermission(player, Permission.CHAT_COLOR))
			message = message.replaceAll("(&([a-fA-F0-9]))", "");
		if (! plugin.getPermissionHandler().hasPermission(player, Permission.CHAT_FORMATTING))
			message = message.replaceAll("(&([kKl-oL-OrR]))", "");
		if (! plugin.getPermissionHandler().hasPermission(player, Permission.CHAT_RAINBOW))
			message = message.replaceAll("(&([zZ]))", "");

		// Insert message, format colors
		return FormatUtil.replaceColors(format.replace("{message}", message));
	}

	// Personal chat format #EasterEgg
	private String getChatFormat(User user)
	{
		if (user.hasOption("chatFormat"))
			return (String) user.getOption("chatFormat");

		return chatFormat;
	}

	@Override
	public void reload()
	{
		this.formatChat = plugin.getConfig().getBoolean("formatChat", true);
		this.chatFormat = plugin.getConfig().getString("chatFormat");
	}
}