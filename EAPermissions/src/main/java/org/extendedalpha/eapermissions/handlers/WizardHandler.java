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

package org.extendedalpha.eapermissions.handlers;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.extendedalpha.core.util.FormatUtil;
import org.extendedalpha.core.util.Util;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.types.Group;
import org.extendedalpha.eapermissions.types.WorldGroup;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

/**
 * @author exiledev
 */

@AllArgsConstructor
public class WizardHandler
{
	private final EAPermissions plugin;

	public final void createGroup(Player player, String world)
	{
		GroupCreationWizard wizard = new GroupCreationWizard(player, world);
		Conversation conversation = new ConversationFactory(plugin).withFirstPrompt(wizard).withPrefix(new Prefix())
				.withEscapeSequence("exit").addConversationAbandonedListener(wizard).buildConversation(player);
		conversation.begin();
	}

	public class Prefix implements ConversationPrefix
	{
		@Override
		public String getPrefix(ConversationContext context)
		{
			return plugin.getPrefix();
		}
	}

	public class GroupCreationWizard extends StringPrompt implements ConversationAbandonedListener
	{
		private int step;
		private String world;
		private Player player;
		private WorldGroup group;
		private List<String> flags;

		public GroupCreationWizard(Player player, String world)
		{
			this.step = 1;
			this.world = world;
			this.player = player;
			this.flags = new ArrayList<String>();
		}

		@Override
		public String getPromptText(ConversationContext context)
		{
			if (! flags.contains("started"))
			{
				flags.add("started");
				return FormatUtil.format("&eWelcome to the Group Creation Wizard! Type \"&bdone&e\" to exit at any time!");
			}

			switch (step)
			{
				case 1:
					return FormatUtil.format("&eWhat will be the name of the group?");
				case 2:
					return FormatUtil.format("&eWill the group be a default group?");
				case 3:
					if (! flags.contains("parentsStarted"))
						return FormatUtil.format("&eSpecify the group''s parents. Type \"&bdone&e\" when done!");
					else
						return FormatUtil.format("&eAdd some more parents. Type \"&bdone&e\" when done!");
				case 4:
					if (! flags.contains("permissionsStarted"))
						return FormatUtil.format("&eSpecify the group''s permissions. Type \"&bdone&e\" when done.");
					else
						return FormatUtil.format("&eAdd some more permissions. Type \"&bdone&e\" when done.");
				case 5:
					plugin.getPermissionHandler().addWorldGroup(world, group);
				default:
					return FormatUtil.format("&4Invalid step! Type \"&cexit&4\"!");

			}
		}

		@Override
		public Prompt acceptInput(ConversationContext context, String input)
		{
			switch (step)
			{
				case 1:
					if (! input.matches("^[a-zA-Z_0-9]+$"))
					{
						player.sendMessage(FormatUtil.format("&cError: &4Name contains invalid characters!"));
						return this;
					}

					group = new WorldGroup(plugin, input, world);
					break;
				case 2:
					group.setIsDefaultGroup(Util.toBoolean(input));
					break;
				case 3:
					flags.add("parentsStarted");
					if (input.equalsIgnoreCase("done"))
						break;

					Group parent = plugin.getPermissionHandler().getGroup(world, input);
					if (parent == null)
					{
						player.sendMessage(FormatUtil.format("&cError: &4Group \"&c{0}&4\" not found!", input));
						return this;
					}

					group.addParentGroup(parent);
					return this;
				case 4:
					flags.add("permissionsStarted");
					if (input.equalsIgnoreCase("done"))
						break;

					group.addPermission(input);
					return this;
				case 5:
					return Prompt.END_OF_CONVERSATION;
			}

			step++;
			return this;
		}

		@Override
		public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent)
		{
			this.step = 0;
			this.world = null;
			this.group = null;
			this.flags = null;
		}
	}
}