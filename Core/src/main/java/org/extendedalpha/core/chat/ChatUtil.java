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

package org.extendedalpha.core.chat;

import org.extendedalpha.core.exception.ReflectionException;
import org.extendedalpha.core.handlers.LogHandler;
import org.extendedalpha.core.reflection.Packet;
import org.extendedalpha.core.reflection.Reflection;
import org.extendedalpha.core.util.Util;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Util for dealing with JSON-based chat.
 *
 * @author exiledev
 */

public class ChatUtil
{
	private ChatUtil() { }

	/**
	 * Sends a message to a {@link CommandSender}. This method attempts to send
	 * a JSON chat message if the sender is a player. If message sending fails,
	 * a legacy message will be sent.
	 *
	 * @param sender CommandSender to send the message to
	 * @param message Message to send
	 */
	public static final void sendMessage(CommandSender sender, BaseComponent... message)
	{
		Validate.notNull(sender, "sender cannot be null!");

		if (sender instanceof Player)
		{
			try
			{
				sendMessageRaw(sender, message);
				return;
			}
			catch (Throwable ex)
			{
				LogHandler.globalDebug(Util.getUsefulStack(ex, "sending message {0} to {1}", ComponentSerializer.toString(message),
						sender.getName()));
			}
		}

		sender.sendMessage(BaseComponent.toLegacyText(message));
	}

	/**
	 * Sends a JSON chat message to a {@link CommandSender}. If message sending
	 * fails, a {@link ReflectionException} will be thrown.
	 *
	 * @param sender CommandSender to send the message to
	 * @param message Message to send
	 * @throws ReflectionException If sending fails
	 */
	public static final void sendMessageRaw(CommandSender sender, BaseComponent... message) throws ReflectionException
	{
		Validate.notNull(sender, "sender cannot be null!");

		if (sender instanceof Player)
		{
			Packet packet = Reflection.getChatPacket(message);
			packet.send((Player) sender);
		}
		else
		{
			throw new ReflectionException("JSON chat messages can only be sent to players.");
		}
	}
}
