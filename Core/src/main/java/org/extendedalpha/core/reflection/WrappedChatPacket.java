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

package org.extendedalpha.core.reflection;

import org.extendedalpha.core.exception.ReflectionException;

/**
 * @author exiledev
 */

// TODO: Keep up to date with MC versions. 1.8.7
public class WrappedChatPacket extends WrappedPacket
{
	private static final String CLASS_NAME = "PacketPlayOutChat";
	private static final Class<?> CHAT_COMPONENT = Reflection.getMinecraftClass("IChatBaseComponent");

	public WrappedChatPacket(Object chatComponent) throws ReflectionException
	{
		try
		{
			this.nmsClass = Reflection.getMinecraftClass(CLASS_NAME);
			this.constructor = nmsClass.getConstructor(CHAT_COMPONENT);
			this.nmsHandle = constructor.newInstance(chatComponent);
		}
		catch (Throwable ex)
		{
			throw new ReflectionException("Constructing wrapped chat packet", ex);
		}
	}
}
