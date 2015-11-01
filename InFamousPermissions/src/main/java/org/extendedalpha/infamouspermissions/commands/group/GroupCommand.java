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

package org.extendedalpha.infamouspermissions.commands.group;

import java.util.logging.Level;

import lombok.Getter;
import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.commands.InFamousPermissionsCommand;
import org.extendedalpha.infamouspermissions.types.Group;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;
import org.extendedalpha.pluginbase.subapi.util.Util;

/**
 * @author ExileDev
 */

public abstract class GroupCommand extends InFamousPermissionsCommand
{
	protected Group group;
	protected World world;
	protected @Getter String action;

	public GroupCommand(InFamousPermissions plugin)
	{
		super(plugin);
		this.requiredArgs.add("group");
	}

	public void execute(CommandSender sender, Group group, World world, String[] args)
	{
		this.sender = sender;
		this.group = group;
		this.world = world;
		this.args = args;

		// Prevent commands being run on command blocks, if applicable
		if (sender instanceof BlockCommandSender && ! plugin.getConfig().getBoolean("allowCommandBlocks"))
		{
			Block block = ((BlockCommandSender) sender).getBlock();
			plugin.getLogHandler().log(Level.WARNING, "InFamousPermissions commands cannot be used from command blocks!");
			plugin.getLogHandler().log(Level.WARNING, "Location: {0}, {1}, {2} ({3})", block.getX(), block.getY(), block.getZ(),
					block.getWorld().getName());
			return;
		}

		if (sender instanceof Player)
			player = (Player) sender;

		if (mustBePlayer && ! isPlayer())
		{
			err("You must be a player to execute this command!");
			return;
		}

		if (requiredArgs.size() - 1 > args.length)
		{
			invalidArgs();
			return;
		}

		if (! hasPermission())
		{
			err("You do not have permission to perform this command!");
			plugin.getLogHandler().log(Level.WARNING, sender.getName() + " was denied access to a command!");
			return;
		}

		try
		{
			perform();
		}
		catch (Throwable e)
		{
			err("Error executing command: &c{0}&c: &c{1}", e.getClass().getName(), e.getLocalizedMessage());
			plugin.getLogHandler().log(Level.WARNING, Util.getUsefulStack(e, "executing command " + name));
		}
	}

	@Override
	public String getUsageTemplate(boolean displayHelp)
	{
		StringBuilder ret = new StringBuilder();
		ret.append(String.format("&6/%s &6group &7<group> &6", plugin.getCommandHandler().getCommandPrefix()));

		if (! action.isEmpty())
			ret.append(String.format("%s ", action));

		ret.append(name);

		for (String s : requiredArgs.subList(1, requiredArgs.size()))
			ret.append(String.format(" &7<%s>", s));

		for (String s : optionalArgs)
			ret.append(String.format(" &7[%s]", s));

		if (displayHelp)
			ret.append("&6" + description);

		return FormatUtil.format(ret.toString());
	}
}