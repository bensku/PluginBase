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

package org.extendedalpha.core.common;

import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Map;

public final class NMS {

    private NMS() {
    }

    public static void sendPacket(Player player, Packet... packets) {
        if (!(player instanceof CraftPlayer)) {
            player = player.getPlayer();
            if (!(player instanceof CraftPlayer)) {
                throw new IllegalArgumentException();
            }
        }
        for (Packet packet : packets) ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void registerCustomEntity(String entityName, Class<?> entityClass, int entityId) {
        Reflections.getField(EntityTypes.class, "c", Map.class).get(null).put(entityName, entityClass);
        Reflections.getField(EntityTypes.class, "d", Map.class).get(null).put(entityClass, entityName);
        Reflections.getField(EntityTypes.class, "e", Map.class).get(null).put(entityId, entityClass);
        Reflections.getField(EntityTypes.class, "f", Map.class).get(null).put(entityClass, entityId);
        Reflections.getField(EntityTypes.class, "g", Map.class).get(null).put(entityName, entityId);
    }

}
