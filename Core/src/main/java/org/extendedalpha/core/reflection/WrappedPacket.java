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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.extendedalpha.core.exception.ReflectionException;

import org.bukkit.entity.Player;

/**
 * @author exiledev
 */

// TODO: Keep up to date with MC versions. 1.8.7
public abstract class WrappedPacket extends AbstractWrapper implements Packet
{
	@Override
	public final void send(Player player) throws ReflectionException
	{
		try
		{
			Object nmsPlayer = Reflection.getHandle(player);
			Field playerConnectionField = Reflection.getField(nmsPlayer.getClass(), "playerConnection");
			Object playerConnection = playerConnectionField.get(nmsPlayer);
			Method sendPacket = Reflection.getMethod(playerConnection.getClass(), "sendPacket");
			sendPacket.invoke(playerConnection, nmsHandle);
		}
		catch (Throwable ex)
		{
			throw new ReflectionException(String.format("Sending packet to %s", player.getName()), ex);
		}
	}
}
