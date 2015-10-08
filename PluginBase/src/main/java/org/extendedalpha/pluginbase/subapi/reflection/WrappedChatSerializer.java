package org.extendedalpha.pluginbase.subapi.reflection;

import java.lang.reflect.Method;

import org.extendedalpha.pluginbase.subapi.exception.ReflectionException;

/**
 * @author ExileDev
 */

// TODO: Keep up to date with MC versions.
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
