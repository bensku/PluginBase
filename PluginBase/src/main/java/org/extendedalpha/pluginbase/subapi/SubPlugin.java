
package org.extendedalpha.pluginbase.subapi;

import java.util.List;

import lombok.Getter;
import org.extendedalpha.pluginbase.subapi.commands.Command;
import org.extendedalpha.pluginbase.subapi.handlers.CommandHandler;
import org.extendedalpha.pluginbase.subapi.handlers.LogHandler;
import org.extendedalpha.pluginbase.subapi.handlers.PermissionHandler;
import org.extendedalpha.pluginbase.subapi.types.Reloadable;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main SubAPI class. Plugins utilizing this API should extend this class.
 *
 * @author ExileDev
 */

public abstract class SubPlugin extends JavaPlugin implements Reloadable
{
	protected @Getter
    PermissionHandler permissionHandler;
	protected @Getter
    CommandHandler commandHandler;
	protected @Getter LogHandler logHandler;

	/**
	 * Gets this plugin's prefix. Defaults to {@link ChatColor#YELLOW}.
	 * @return This plugin's prefix
	 */
	public String getPrefix()
	{
		return ChatColor.YELLOW.toString();
	}

	// I'd like to move to something like Optional, but it would cause some serious breakage.

	/**
	 * Gets any extra lines to be displayed in the help menu.
	 * @return Any extra lines, or null if none
	 */
	public List<String> getExtraHelp()
	{
		return null;
	}

	/**
	 * Gets this plugin's help command. Unless this is overriden, it will be
	 * null before commands are registered.
	 * @return Custom help command, or the default if unapplicable.
	 */
	public Command getHelpCommand()
	{
		return commandHandler.getCommand("help");
	}

	/**
	 * Gets this plugin's default command if applicable. The default command is
	 * run if the first argument is not a valid command. This has no effect when
	 * prefixed commands are not used.
	 * @return The default command, or null if unapplicable.
	 */
	public Command getDefaultCommand()
	{
		return null;
	}

	/**
	 * Exposes this plugin's ClassLoader.
	 * @see JavaPlugin#getClassLoader()
	 */
	public ClassLoader classLoader()
	{
		return super.getClassLoader();
	}

	@Override
	public void reload()
	{
		
	}
}