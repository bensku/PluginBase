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

package org.extendedalpha.infamouspermissions.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.extendedalpha.pluginbase.subapi.types.IPermission;

/**
 * @author ExileDev
 */

@Getter
@AllArgsConstructor
public enum Permission implements IPermission
{
	// Permission Management
	USER_ADD_SUBGROUP("user.add.subgroup"),
	USER_ADD_PERMISSION("user.add.permission"),
	USER_VIEW_INFO("user.view.info"),
	USER_HAS_GROUP("user.has.group"),
	USER_HAS_OPTION("user.has.option"),
	USER_HAS_PERMISSION("user.has.permission"),
	USER_LIST_PERMISSIONS("user.list.permissions"),
	USER_REMOVE_SUBGROUP("user.remove.subgroup"),
	USER_REMOVE_PERMISSION("user.remove.permission"),
	USER_SET_GROUP("user.set.group"),
	USER_SET_OPTION("user.set.option"),
	USER_SET_PREFIX("user.set.prefix"),
	USER_SET_SUFFIX("user.set.suffix"),
	USER_RESET("user.reset"),

	GROUP_ADD_PERMISSION("group.add.permission"),
	GROUP_VIEW_INFO("group.view.info"),
	GROUP_CREATE("group.create"),
	GROUP_HAS_OPTION("group.has.option"),
	GROUP_HAS_PERMISSION("group.has.permission"),
	GROUP_LIST("group.list"),
	GROUP_LIST_PERMISSIONS("group.list.permissions"),
	GROUP_LIST_USERS("group.list.users"),
	GROUP_REMOVE_PERMISSION("group.remove.permission"),
	GROUP_SET_OPTION("group.set.option"),
	GROUP_SET_PREFIX("group.set.prefix"),

	// Chat
	CHAT_COLOR("chat.color"),
	CHAT_FORMATTING("chat.formatting"),
	CHAT_RAINBOW("chat.rainbow"),

	// Prefixes
	CMD_PREFIX("cmd.prefix"),
	CMD_PREFIX_RESET("cmd.prefix.reset"),
	CMD_PREFIX_RESET_OTHERS("cmd.prefix.reset.others"),

	// Suffixes
	CMD_SUFFIX("cmd.suffix"),
	CMD_SUFFIX_RESET("cmd.suffix.reset"),
	CMD_SUFFIX_RESET_OTHERS("cmd.suffix.reset.others"),

	// Other Commands
	CMD_NICK("cmd.nick"),
	CMD_REALNAME("cmd.realname"),
	CMD_RELOAD("cmd.reload"),
	CMD_SAVE("cmd.save"),
	CMD_VERSION("cmd.version");

	private final String node;
}