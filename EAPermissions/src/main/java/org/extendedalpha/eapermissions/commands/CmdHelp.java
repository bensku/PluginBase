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

package org.extendedalpha.eapermissions.commands;

import java.util.ArrayList;
import java.util.List;

import org.extendedalpha.core.chat.BaseComponent;
import org.extendedalpha.core.chat.ChatUtil;
import org.extendedalpha.core.chat.TextComponent;
import org.extendedalpha.core.util.FormatUtil;
import org.extendedalpha.eapermissions.EAPermissions;

/**
 * Generic help command.
 *
 * @author exiledev
 */

public class CmdHelp extends EAPermissionsCommand
{
	private int linesPerPage, pageArgIndex;

	public CmdHelp(EAPermissions plugin)
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
				err("&c{0} &4is not a number.", args[0]);
				return;
			}
			catch (IndexOutOfBoundsException ex)
			{
				err("&4There is no page with the index &c{0}&4.", args[0]);
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

		BaseComponent[] footer = getFooter();
		if (footer != null)
			lines.add(footer);

		return lines;
	}

	private List<BaseComponent[]> getHeader(int index)
	{
		List<String> header = new ArrayList<>();

		header.add(FormatUtil.format("&3====[ &e{0} Commands &3(&e{1}&3/&e{2}&3) ]====", plugin.getName(), index, getPageCount()));
		header.add(FormatUtil.format("&eKey: &3<required> [optional]"));

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

	public BaseComponent[] getFooter()
	{
		return TextComponent.fromLegacyText(FormatUtil.format("&eHover to see command information. Click to insert into chat."));
	}

	private final List<BaseComponent[]> buildHelpMenu()
	{
		List<BaseComponent[]> ret = new ArrayList<>();

		for (EAPermissionsCommand cmd : plugin.getCommandHandler().getRegisteredPrefixedCommands())
		{
			if (cmd.hasSubCommands())
				ret.addAll(cmd.getFancySubCommandHelp(true));
			else
				ret.add(cmd.getFancyUsageTemplate(true));
		}

		for (EAPermissionsCommand cmd : plugin.getCommandHandler().getRegisteredCommands())
		{
			if (cmd.hasSubCommands())
				ret.addAll(cmd.getFancySubCommandHelp(true));
			else
				ret.add(cmd.getFancyUsageTemplate(true));
		}

		return ret;
	}
}