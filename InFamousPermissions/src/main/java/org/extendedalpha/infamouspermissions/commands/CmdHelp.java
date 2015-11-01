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

package org.extendedalpha.infamouspermissions.commands;

import java.util.ArrayList;
import java.util.List;

import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.pluginbase.subapi.chat.BaseComponent;
import org.extendedalpha.pluginbase.subapi.chat.ChatUtil;
import org.extendedalpha.pluginbase.subapi.chat.TextComponent;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * Generic help command.
 *
 * @author ExileDev
 */

public class CmdHelp extends InFamousPermissionsCommand
{
	private int linesPerPage, pageArgIndex;

	public CmdHelp(InFamousPermissions plugin)
	{
		super(plugin);
		this.name = "help";
		this.optionalArgs.add("page");
		this.description = "Shows " + plugin.getName() + " help";
		this.linesPerPage = 6;

		this.usesPrefix = true;
	}

	@Override
	public void perform()
	{
		int index = 1;
		if (args.length > pageArgIndex)
		{
			try
			{
				index = Integer.parseInt(args[pageArgIndex]);
				if (index < 1 || index > getPageCount())
					throw new IndexOutOfBoundsException();
			}
			catch (NumberFormatException ex)
			{
				err("&c{0} &cis not a number.", args[0]);
				return;
			}
			catch (IndexOutOfBoundsException ex)
			{
				err("&cThere is no page with the index &c{0}&c.", args[0]);
				return;
			}
		}

		for (BaseComponent[] components : getPage(index))
			ChatUtil.sendMessage(sender, components);
	}

	public int getPageCount()
	{
		return (getListSize() + linesPerPage - 1) / linesPerPage;
	}

	public int getListSize()
	{
		return buildHelpMenu().size();
	}

	private List<BaseComponent[]> getPage(int index)
	{
		List<BaseComponent[]> lines = new ArrayList<>();

		lines.addAll(getHeader(index));
		lines.addAll(getLines((index - 1) * linesPerPage, index * linesPerPage));

		return lines;
	}

	private List<BaseComponent[]> getHeader(int index)
	{
		List<String> header = new ArrayList<>();

		header.add(FormatUtil.format("&7====[ &6{0} Commands &7(&6{1}&7/&6{2}&7) ]====", plugin.getName(), index, getPageCount()));
		header.add(FormatUtil.format("&6Key: &7<required> [optional]"));

		return TextComponent.fromLegacyList(header);
	}

	private List<BaseComponent[]> getLines(int startIndex, int endIndex)
	{
		List<BaseComponent[]> lines = new ArrayList<>();

		for (int i = startIndex; i < endIndex && i < getListSize(); i++)
		{
			lines.add(buildHelpMenu().get(i));
		}

		return lines;
	}

	private final List<BaseComponent[]> buildHelpMenu()
	{
		List<BaseComponent[]> ret = new ArrayList<>();

		for (InFamousPermissionsCommand cmd : plugin.getCommandHandler().getRegisteredPrefixedCommands())
		{
			if (cmd.hasSubCommands())
				ret.addAll(cmd.getFancySubCommandHelp(true));
			else
				ret.add(cmd.getFancyUsageTemplate(true));
		}

		for (InFamousPermissionsCommand cmd : plugin.getCommandHandler().getRegisteredCommands())
		{
			if (cmd.hasSubCommands())
				ret.addAll(cmd.getFancySubCommandHelp(true));
			else
				ret.add(cmd.getFancyUsageTemplate(true));
		}

		return ret;
	}
}