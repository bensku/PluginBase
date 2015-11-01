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

package org.extendedalpha.infamouspermissions.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.commands.InFamousPermissionsCommand;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.extendedalpha.pluginbase.subapi.chat.ComponentBuilder;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * Handles commands. This supports both prefixed and non-prefixed commands.
 *
 */

public class CommandHandler implements CommandExecutor
{
	private String commandPrefix;
	private List<InFamousPermissionsCommand> registeredPrefixedCommands;
	private List<InFamousPermissionsCommand> registeredCommands;

	private final InFamousPermissions plugin;

	public CommandHandler(InFamousPermissions plugin)
	{
		this.plugin = plugin;
		this.registeredCommands = new ArrayList<>();
	}

	/**
	 * Registers a non-prefixed {@link InFamousPermissionsCommand}.
	 *
	 * @param command Non-prefixed {@link InFamousPermissionsCommand} to register.
	 */
	public void registerCommand(InFamousPermissionsCommand command)
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
	 * Registers a prefixed {@link InFamousPermissionsCommand}. The commandPrefix
	 * must be set for this method to work.
	 *
	 * @param command Prefixed {@link InFamousPermissionsCommand} to register.
	 */
	public void registerPrefixedCommand(InFamousPermissionsCommand command)
	{
		Validate.notNull(command, "command cannot be null!");
		if (commandPrefix != null)
			registeredPrefixedCommands.add(command);
	}

	/**
	 * @return A {@link List} of all registered non-prefixed commands.
	 */
	public List<InFamousPermissionsCommand> getRegisteredCommands()
	{
		return registeredCommands;
	}

	/**
	 * @return A {@link List} of all registered prefixed commands.
	 */
	public List<InFamousPermissionsCommand> getRegisteredPrefixedCommands()
	{
		return registeredPrefixedCommands;
	}

	/**
	 * @return The command prefix.
	 */
	public String getCommandPrefix()
	{
		return commandPrefix;
	}

	/**
	 * Sets the command prefix. This method must be called before any prefixed
	 * commands are registered.
	 *
	 * @param commandPrefix InFamousPermissionsCommand prefix
	 */
	public void setCommandPrefix(String commandPrefix)
	{
		Validate.notEmpty(commandPrefix, "prefix cannot be null or empty!");
		this.commandPrefix = commandPrefix;
		this.registeredPrefixedCommands = new ArrayList<InFamousPermissionsCommand>();

		plugin.getCommand(commandPrefix).setExecutor(this);
	}

	/**
	 * @return whether or not the command prefix is used.
	 */
	public boolean usesCommandPrefix()
	{
		return commandPrefix != null;
	}

	public final InFamousPermissionsCommand getCommand(String name)
	{
		Validate.notNull(name, "name cannot be null!");
		for (InFamousPermissionsCommand command : registeredPrefixedCommands)
		{
			if (name.equalsIgnoreCase(command.getName()) || command.getAliases().contains(name.toLowerCase()))
				return command;
		}

		for (InFamousPermissionsCommand command : registeredCommands)
		{
			if (name.equalsIgnoreCase(command.getName()) || command.getAliases().contains(name.toLowerCase()))
				return command;
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args)
	{
		if (args.length > 0)
		{
			String name = args[0];
			args = Arrays.copyOfRange(args, 1, args.length);

			InFamousPermissionsCommand command = getCommand(name);
			if (command != null)
			{
				command.execute(sender, args);
				return true;
			}

			new ComponentBuilder(FormatUtil.format("&cError: &cUnknown command \"&c{0}&c\". Try ", name))
				.addAll(getHelpCommand().getFancyUsageTemplate()).send(sender);
		}
		else
		{
			getHelpCommand().execute(sender, args);
		}

		return true;
	}

	private final InFamousPermissionsCommand getHelpCommand()
	{
		return getCommand("help");
	}
}