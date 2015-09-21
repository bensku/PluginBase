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

package org.extendedalpha.core;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.extendedalpha.core.commands.Command;
import org.extendedalpha.core.handlers.CommandHandler;
import org.extendedalpha.core.handlers.LogHandler;
import org.extendedalpha.core.handlers.PermissionHandler;
import org.extendedalpha.core.types.LazyLocation;
import org.extendedalpha.core.types.Reloadable;
import org.extendedalpha.core.types.SimpleVector;

import java.util.List;

public abstract class EAPermissionPlugin extends JavaPlugin implements Reloadable {
    protected @Getter
    PermissionHandler permissionHandler;
    protected @Getter
    CommandHandler commandHandler;
    protected @Getter
    LogHandler logHandler;

    /**
     * Gets this plugin's prefix. Defaults to {@link ChatColor#YELLOW}.
     * @return This plugin's prefix
     */
    public String getPrefix()
    {
        return ChatColor.YELLOW.toString();
    }

    // TODO: Returning null, especially with API methods, is generally a bad idea
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