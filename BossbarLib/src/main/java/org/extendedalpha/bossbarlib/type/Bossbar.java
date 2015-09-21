/*
 * This file is part of BossbarLib
 *
 * Copyright (C) 2013-2015 ExtendedAlpha, ExileDev
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

package org.extendedalpha.bossbarlib.type;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Location;

public interface Bossbar {

    /**
     * Returns the message.
     *
     * @return message
     */
    String getMessage();

    /**
     * Set the message.
     *
     * @param message message
     * @return this
     */
    Bossbar setMessage(String message);

    /**
     * Returns the amount of health in a percentage of [0~1]. 0 is the minimum value, while 1 is the maximum.
     *
     * @return percentage
     */
    float getPercentage();

    /**
     * Set the amount of health in a percentage of [0~1]. 0 is the minimum value, while 1 is the maximum.
     *
     * @param percentage percentage
     * @return this
     */
    Bossbar setPercentage(float percentage);

    /**
     * Returns the spawn packet. Only for internal purposes.
     *
     * @return packet
     */
    Packet getSpawnPacket();

    /**
     * Returns the destroy packet. Only for internal purposes.
     *
     * @return packet
     */
    Packet getDestroyPacket();

    /**
     * Returns the meta packet. Only for internal purposes.
     *
     * @param watcher data watcher
     * @return packet
     */
    Packet getMetaPacket(DataWatcher watcher);

    /**
     * Returns the teleport packet. Only for internal purposes.
     *
     * @param location location
     * @return packet
     */
    Packet getTeleportPacket(Location location);

    /**
     * Returns the data watcher. Only for internal purposes.
     *
     * @return packet
     */
    DataWatcher getWatcher();

}
