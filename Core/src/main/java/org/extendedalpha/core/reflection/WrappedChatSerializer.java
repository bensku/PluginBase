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

import java.lang.reflect.Method;

import org.extendedalpha.core.exception.ReflectionException;

/**
 * @author exiledev
 */

// TODO: Keep up to date with MC versions. 1.8.8
public class WrappedChatSerializer extends AbstractWrapper
{
	private static final String CLASS_NAME = "ChatSerializer";
	private static final String[] ALIASES = { "IChatBaseComponent$ChatSerializer" };

	private final Method serialize;
	public WrappedChatSerializer() throws ReflectionException
	{
		try
		{
			this.nmsClass = Reflection.getMinecraftClass(CLASS_NAME, ALIASES);
			this.constructor = null;
			this.nmsHandle = null;

			this.serialize = Reflection.getMethod(nmsClass, "a", String.class);
		}
		catch (Throwable ex)
		{
			throw new ReflectionException("Constructing chat serializer", ex);
		}
	}

	public final Object serialize(String json) throws ReflectionException
	{
		return invokeMethod(serialize, json);
	}
}
