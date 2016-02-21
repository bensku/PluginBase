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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.extendedalpha.pluginbase.misc.EAUtilsException;
import org.extendedalpha.pluginbase.commands.AbstractCommand;

import java.util.Set;


public class KaboomCommand extends AbstractCommand {

    public KaboomCommand() {
        super("blockphysics kaboom", 0, 1);
        setPermissionNode("blockphysics.commands.kaboom");
        setUsage("/<command> kaboom [<power-level>]");
    }

    @Override
    public boolean execute(Plugin plugin, CommandSender sender, String[] args) {
        notFromConsole(sender);

        float power;
        if (args.length == 0) {
            power = 4.0f;
        } else {
            try {
                power = Float.parseFloat(args[0]);
            } catch (NumberFormatException e) {
                throw new EAUtilsException("invalid numeric argument: " + args[0]);
            }
        }

        Player player = (Player) sender;
        Block b = player.getTargetBlock((Set<Material>)null, 140);
        player.getWorld().createExplosion(b.getLocation(), power);

        return true;
    }

}
