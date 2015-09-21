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

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.extendedalpha.blockphysics.SlideOTron;
import org.extendedalpha.core.EAUtilsException;
import org.extendedalpha.core.EAValidate;
import org.extendedalpha.core.commands.AbstractCommand;

public class PowerCommand extends AbstractCommand {

    public PowerCommand() {
        super("blockphysics power", 1, 1);
        setPermissionNode("blockphysics.commands.power");
        setUsage("/<command> power <power-level>");
    }

    @Override
    public boolean execute(Plugin plugin, CommandSender sender, String[] args) {
        notFromConsole(sender);

        Player player = (Player) sender;
        SlideOTron wand = SlideOTron.getWand(player);
        EAValidate.notNull(wand, "You are not holding a Slide-O-Tron wand");

        try {
            int power = Integer.parseInt(args[0]);
            wand.setPower(power);
            player.setItemInHand(wand.toItemStack(player.getItemInHand().getAmount()));
        } catch (NumberFormatException e) {
            throw new EAUtilsException("Invalid numeric quantity: " + args[0]);
        }

        return true;
    }

}
