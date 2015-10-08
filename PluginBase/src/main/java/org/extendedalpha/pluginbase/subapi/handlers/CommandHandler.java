package org.extendedalpha.pluginbase.subapi.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.extendedalpha.pluginbase.subapi.SubPlugin;
import org.extendedalpha.pluginbase.subapi.chat.BaseComponent;
import org.extendedalpha.pluginbase.subapi.chat.ComponentBuilder;
import org.extendedalpha.pluginbase.subapi.commands.Command;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

/**
 * Handles commands. This supports both prefixed and non-prefixed commands.
 *
 * @author ExileDev
 */

public class CommandHandler implements CommandExecutor
{
	private String commandPrefix;
	private List<Command> registeredPrefixedCommands;
	private List<Command> registeredCommands;

	private final SubPlugin plugin;
	public CommandHandler(SubPlugin plugin)
	{
		this.plugin = plugin;
		this.registeredCommands = new ArrayList<>();
	}

	/**
	 * Registers a non-prefixed {@link Command}.
	 *
	 * @param command Non-prefixed {@link Command} to register.
	 */
	public void registerCommand(Command command)
	{
		Validate.notNull(command, "command cannot be null!");
		PluginCommand pluginCommand = plugin.getCommand(command.getName());
		if (pluginCommand != null)
		{
			pluginCommand.setExecutor(command);
			registeredCommands.add(command);
		}
		else
		{
			plugin.getLogHandler().log(Level.WARNING, "Entry for command {0} is missing in plugin.yml", command.getName());
		}
	}

	/**
	 * Registers a prefixed {@link Command}. The commandPrefix must be set for
	 * this method to work.
	 *
	 * @param command Prefixed {@link Command} to register.
	 */
	public void registerPrefixedCommand(Command command)
	{
		Validate.notNull(command, "command cannot be null!");
		if (commandPrefix != null)
			registeredPrefixedCommands.add(command);
	}

	/**
	 * Gets a {@link List} of all registered non-prefixed commands.
	 *
	 * @return The list
	 */
	public List<Command> getRegisteredCommands()
	{
		return registeredCommands;
	}

	/**
	 * Gets a {@link List} of all registered prefixed commands.
	 *
	 * @return The list
	 */
	public List<Command> getRegisteredPrefixedCommands()
	{
		return registeredPrefixedCommands;
	}

	/**
	 * Gets the command prefix.
	 *
	 * @return The prefix, or null if not used
	 */
	public String getCommandPrefix()
	{
		return commandPrefix;
	}

	/**
	 * Sets the command prefix. This method must be called before any prefixed
	 * commands are registered.
	 *
	 * @param commandPrefix Command prefix
	 */
	public void setCommandPrefix(String commandPrefix)
	{
		Validate.notEmpty(commandPrefix, "prefix cannot be null or empty!");
		this.commandPrefix = commandPrefix;
		this.registeredPrefixedCommands = new ArrayList<>();

		plugin.getCommand(commandPrefix).setExecutor(this);
	}

	/**
	 * Whether or not the command prefix is used.
	 *
	 * @return True if the command prefix is used, false if not
	 */
	public boolean usesCommandPrefix()
	{
		return commandPrefix != null;
	}

	/**
	 * Gets a {@link Command} by name.
	 *
	 * @param name Command name
	 * @return Command, or null if not found
	 */
	public final Command getCommand(String name)
	{
		Validate.notNull(name, "name cannot be null!");
		for (Command command : registeredPrefixedCommands)
		{
			if (name.equalsIgnoreCase(command.getName()) || command.getAliases().contains(name.toLowerCase()))
				return command;
		}

		for (Command command : registeredCommands)
		{
			if (name.equalsIgnoreCase(command.getName()) || command.getAliases().contains(name.toLowerCase()))
				return command;
		}

		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args)
	{
		if (args.length > 0)
		{
			String name = args[0];
			String[] originalArgs = args;
			args = Arrays.copyOfRange(args, 1, args.length);

			Command command = getCommand(name);
			if (command != null)
			{
				command.execute(sender, args);
				return true;
			}

			Command def = plugin.getDefaultCommand();
			if (def != null)
			{
				def.execute(sender, originalArgs);
				return true;
			}

			if (sender instanceof Player)
			{
				List<BaseComponent[]> templates = getHelpCommand().getFancyUsageTemplate();
				String error = FormatUtil.format("&cError: &4Unknown command \"&c{0}&4\". Try ", name);
				new ComponentBuilder(error).addAll(templates.get(0)).send(sender);
			}
			else
			{
				List<String> templates = getHelpCommand().getUsageTemplate(false);
				sender.sendMessage(FormatUtil.format("&cError: &4Unknown command \"&c{0}&4\". Try {1}", name, templates.get(0)));
			}
		}
		else
		{
			getHelpCommand().execute(sender, args);
		}

		return true;
	}

	public final Command getHelpCommand()
	{
		if (plugin.getHelpCommand() != null)
			return plugin.getHelpCommand();

		return getCommand("help");
	}
}