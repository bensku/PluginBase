package org.extendedalpha.pluginbase.subapi.reflection;

import org.extendedalpha.pluginbase.subapi.exception.ReflectionException;

/**
 * @author ExileDev
 */

// TODO: Keep up to date with MC versions.
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
