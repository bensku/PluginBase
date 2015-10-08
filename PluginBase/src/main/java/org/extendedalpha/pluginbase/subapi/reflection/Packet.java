package org.extendedalpha.pluginbase.subapi.reflection;

import org.bukkit.entity.Player;

/**
 * Represents a packet that can be sent to a Player.
 * @author ExileDev
 */

public interface Packet
{
	/**
	 * Sends this packet to a given Player.
	 * @param player Player to send to
	 */
	public void send(Player player);
}