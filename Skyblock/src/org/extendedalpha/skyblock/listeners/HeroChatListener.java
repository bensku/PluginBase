package org.extendedalpha.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.dthielke.herochat.ChannelChatEvent;
import org.extendedalpha.skyblock.Skyblock;

public class HeroChatListener implements Listener {
    private Skyblock plugin;
    /**
     * @param plugin
     */
    public HeroChatListener(Skyblock plugin) {
	this.plugin = plugin;
	plugin.getLogger().info("Herochat registered");
    }
    
    /**
     * Handle Herochat events if they exist
     * @param event
     */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerHeroChat(final ChannelChatEvent event) {
	try {
	    int level = plugin.getChatListener().getPlayerLevel(event.getSender().getPlayer().getUniqueId());
	    event.setFormat(event.getFormat().replace("{ISLAND_LEVEL}", String.valueOf(level)));
	} catch (Exception e) {
	    // Do nothing
	}
    }

}
