package org.extendedalpha.pluginbase.subapi.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.extendedalpha.pluginbase.subapi.SubPlugin;
import org.extendedalpha.pluginbase.subapi.chat.BaseComponent;
import org.extendedalpha.pluginbase.subapi.chat.ChatUtil;
import org.extendedalpha.pluginbase.subapi.chat.TextComponent;
import org.extendedalpha.pluginbase.subapi.types.CommandVisibility;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;
import org.extendedalpha.pluginbase.subapi.util.Util;

/**
 * Generic help command.
 *
 * @author ExileDev
 */

public class CmdHelp extends Command
{
	private static final int linesPerPage = 6;
	private static final int pageArgIndex = 0;

	private String header;
	private String footer;

	public CmdHelp(SubPlugin plugin)
	{
		super(plugin);
		this.name = "help";
		this.addOptionalArg("page");
		this.description = "Shows " + plugin.getName() + " help";
		this.visibility = CommandVisibility.ALL;
		this.usesPrefix = true;

		this.header = "&7====[ &6{0} Commands &7(&6{1}&7/&6{2}&7) ]====";
		this.footer = "";
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
				{
					err("&4There is no page with the index &c{0}&4.", index);
					return;
				}
			}
			catch (NumberFormatException ex)
			{
				err("&c{0} &4is not a number.", args[0]);
				return;
			}
		}

		try
		{
			// Attempt to send fancy help
			for (BaseComponent[] components : getPage(index))
				ChatUtil.sendMessageRaw(sender, components);
		}
		catch (Throwable ex)
		{
			// Fallback to legacy help
			for (String line : getLegacyPage(index))
				sendMessage(line);

			plugin.getLogHandler().debug(Level.WARNING, Util.getUsefulStack(ex, "sending help to " + sender.getName()));
		}
	}

	public String getHeader()
	{
		return header;
	}

	public void setHeader(String header)
	{
		this.header = header;
	}

	public int getPageCount()
	{
		return (getListSize() + linesPerPage - 1) / linesPerPage;
	}

	public int getListSize()
	{
		return getHelpMenu().size();
	}

	public List<BaseComponent[]> getPage(int index)
	{
		List<BaseComponent[]> lines = new ArrayList<>();

		lines.addAll(getHeader(index));
		lines.addAll(getLines((index - 1) * linesPerPage, index * linesPerPage));

		BaseComponent[] footer = getFooter();
		if (footer != null)
			lines.add(footer);

		return lines;
	}

	public List<String> getLegacyPage(int index)
	{
		List<String> lines = new ArrayList<>();

		lines.addAll(getLegacyHeader(index));
		lines.addAll(getLegacyLines((index - 1) * linesPerPage, index * linesPerPage));

		if (! footer.isEmpty())
			lines.add(footer);

		return lines;
	}

	public List<BaseComponent[]> getHeader(int index)
	{
		return TextComponent.fromLegacyList(getLegacyHeader(index));
	}

	public List<String> getLegacyHeader(int index)
	{
		List<String> ret = new ArrayList<>();

		ret.add(FormatUtil.format(header, plugin.getName(), index, getPageCount()));

		List<String> extraHelp = plugin.getExtraHelp();
		if (extraHelp != null)
		{
			for (String extra : extraHelp)
				ret.add(FormatUtil.format(extra));
		}

		ret.add(FormatUtil.format("&6Key: &7<required> [optional]"));
		return ret;
	}

	public List<BaseComponent[]> getLines(int startIndex, int endIndex)
	{
		List<BaseComponent[]> helpMenu = getHelpMenu();
		List<BaseComponent[]> lines = new ArrayList<>();

		for (int i = startIndex; i < endIndex && i < getListSize(); i++)
		{
			lines.add(helpMenu.get(i));
		}

		return lines;
	}

	public List<String> getLegacyLines(int startIndex, int endIndex)
	{
		List<String> helpMenu = getLegacyHelpMenu();
		List<String> lines = new ArrayList<>();

		for (int i = startIndex; i < endIndex && i < getListSize(); i++)
		{
			lines.add(helpMenu.get(i));
		}

		return lines;
	}

	public BaseComponent[] getFooter()
	{
		return TextComponent.fromLegacyText(FormatUtil.format("&eHover to see command information. Click to insert into chat."));
	}

	private final List<BaseComponent[]> getHelpMenu()
	{
		List<BaseComponent[]> ret = new ArrayList<>();

		for (Command cmd : plugin.getCommandHandler().getRegisteredPrefixedCommands())
		{
			if (cmd.isVisibleTo(sender))
			{
				ret.addAll(cmd.getFancyUsageTemplate(true));

				if (cmd.hasSubCommands())
					ret.addAll(cmd.getFancySubCommandHelp(true));
			}
		}

		for (Command cmd : plugin.getCommandHandler().getRegisteredCommands())
		{
			if (cmd.isVisibleTo(sender))
			{
				ret.addAll(cmd.getFancyUsageTemplate(true));

				if (cmd.hasSubCommands())
					ret.addAll(cmd.getFancySubCommandHelp(true));
			}
		}

		return ret;
	}

	private final List<String> getLegacyHelpMenu()
	{
		List<String> ret = new ArrayList<>();

		for (Command cmd : plugin.getCommandHandler().getRegisteredPrefixedCommands())
		{
			if (cmd.isVisibleTo(sender))
			{
				ret.addAll(cmd.getUsageTemplate(true));

				if (cmd.hasSubCommands())
					ret.addAll(cmd.getSubCommandHelp(true));
			}
		}

		for (Command cmd : plugin.getCommandHandler().getRegisteredCommands())
		{
			if (cmd.isVisibleTo(sender))
			{
				ret.addAll(cmd.getUsageTemplate(true));

				if (cmd.hasSubCommands())
					ret.addAll(cmd.getSubCommandHelp(true));
			}
		}

		return ret;
	}
}
