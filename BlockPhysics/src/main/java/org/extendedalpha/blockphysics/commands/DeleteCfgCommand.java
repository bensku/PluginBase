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

package org.extendedalpha.blockphysics.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extendedalpha.blockphysics.BlockPhysicsPlugin;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.extendedalpha.pluginbase.ConfigurationManager;
import org.extendedalpha.pluginbase.EAUtilsException;
import org.extendedalpha.pluginbase.MiscUtil;
import org.extendedalpha.pluginbase.commands.AbstractCommand;

public class DeleteCfgCommand extends AbstractCommand {

    public DeleteCfgCommand() {
        super("blockphysics delete", 1, 1);
        setPermissionNode("blockphysics.commands.delete");
        setUsage("/<command> delete <config-key>");
    }

    private static final Pattern worldPat = Pattern.compile("^worlds\\.(.+?)\\.(.+)");

    @Override
    public boolean execute(Plugin plugin, CommandSender sender, String[] args) {
        String key = args[0];
        Matcher m = worldPat.matcher(key);
        if (!m.find() || m.groupCount() != 2) {
            throw new EAUtilsException("Only per-world (worlds.<world-name>.<key>) keys may be deleted");
        }
        ConfigurationManager configManager = ((BlockPhysicsPlugin) plugin).getConfigManager();
        configManager.set(key, (String) null);
        MiscUtil.statusMessage(sender, key + " has been deleted (default will be used)");
        return true;
    }
}
