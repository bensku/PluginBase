package org.extendedalpha.pluginbase.subapi.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.extendedalpha.pluginbase.subapi.exception.ReflectionException;

import org.bukkit.entity.Player;

/**
 * @author ExileDev
 */

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
