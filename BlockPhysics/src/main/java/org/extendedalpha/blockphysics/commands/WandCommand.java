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

import org.extendedalpha.blockphysics.SlideOTron;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.extendedalpha.core.EAValidate;
import org.extendedalpha.core.MiscUtil;
import org.extendedalpha.core.commands.AbstractCommand;

public class WandCommand extends AbstractCommand {

    public WandCommand() {
        super("blockphysics wand", 0, 1);
        setPermissionNode("blockphysics.commands.wand");
        setUsage("/<command> wand [<player-name>]");
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean execute(Plugin plugin, CommandSender sender, String[] args) {
        Player player;
        if (args.length > 0) {
            String playerName = args[0];
            player = Bukkit.getPlayer(playerName);
            EAValidate.notNull(player, "Player " + playerName + " is not online.");
        } else {
            notFromConsole(sender);
            player = (Player) sender;
        }

        player.getInventory().addItem(SlideOTron.makeWand());
        player.updateInventory();

        if (!sender.getName().equals(player.getName())) {
            MiscUtil.statusMessage(sender, "Gave a Slide-O-Tron\u2122 to " + player.getName());
            String from = sender instanceof ConsoleCommandSender ? "the Powers-That-Be" : sender.getName();
            MiscUtil.alertMessage(player, "You received a Slide-O-Tron\u2122 from " + from + ".  Use with care.");
        } else {
            MiscUtil.statusMessage(sender, "Gave yourself a Slide-O-Tron\u2122");
        }
        return true;
    }

}
