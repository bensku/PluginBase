package org.extendedalpha.skyblock.events;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import org.extendedalpha.skyblock.Skyblock;

/**
 * This event is fired when an island level is calculated
 * 
 * @author ExileDev
 * 
 */
public class IslandLevelEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final UUID player;
    private int level;

    /**
     * @param player
     * @param teamLeader
     */
    public IslandLevelEvent(Skyblock plugin, UUID player, int level) {
	this.player = player;
	this.level = level;
    }

    /**
     * @return the player
     */
    public UUID getPlayer() {
        return player;
    }
   
    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    @Override
    public HandlerList getHandlers() {
	return handlers;
    }

    public static HandlerList getHandlerList() {
	return handlers;
    }
}
