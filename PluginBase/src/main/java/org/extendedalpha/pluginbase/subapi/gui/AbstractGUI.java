package org.extendedalpha.pluginbase.subapi.gui;

import org.extendedalpha.pluginbase.subapi.SubPlugin;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;
import org.extendedalpha.pluginbase.subapi.util.NumberUtil;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

/**
 * @author ExileDev
 */

public abstract class AbstractGUI
{
	protected final Player player;
	protected final SubPlugin plugin;

	public AbstractGUI(SubPlugin plugin, Player player)
	{
		Validate.notNull(plugin, "plugin cannot be null");
		Validate.notNull(player, "player cannot be null!");
		this.player = player;
		this.plugin = plugin;
	}

	protected final void setup()
	{
		// Size checks
		int size = NumberUtil.roundUp(getSize(), 9);
		Validate.isTrue(size > 0, "Inventory size must not be negative!");
		Validate.isTrue(size <= 54, "Inventory size is too large! (" + size + " > 54)");

		// Validate title
		String title = getTitle();
		Validate.notNull(title, "Inventory title cannot be null!");
		title = FormatUtil.format(getTitle());
		if (title.length() > 32)
		{
			title = title.substring(0, 31);
			title = title + "\u2026";
		}

		Inventory inventory = Bukkit.createInventory(player, size, title);
		stock(inventory);

		player.openInventory(inventory);
	}

	public final Player getPlayer()
	{
		return player;
	}

	// ---- Required Methods

	public abstract int getSize();

	public abstract String getTitle();

	public abstract void stock(Inventory inventory);

	// ---- Messaging

	protected final void err(String msg, Object... args)
	{
		sendMessage("&cError: &4" + FormatUtil.format(msg, args));
	}

	protected final void sendpMessage(String message, Object... objects)
	{
		sendMessage(plugin.getPrefix() + message, objects);
	}

	protected final void sendMessage(String message, Object... objects)
	{
		player.sendMessage(ChatColor.YELLOW + FormatUtil.format(message, objects));
	}

	// ---- Events

	public void onInventoryClick(InventoryClickEvent event) { }

	public void onInventoryClose(InventoryCloseEvent event) { }
}
