/*
 * This file is part of ExtendedAlpha-Core
 *
 * Copyright (C) 2013-2015 ExtendedAlpha
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

package org.extendedalpha.pluginbase.general.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MenuHelper
implements Listener {
    public static Map<UUID, ChatHandler> map = new HashMap<UUID, ChatHandler>();

    @EventHandler(priority=EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!map.containsKey(p.getUniqueId())) {
            return;
        }
        map.get(p.getUniqueId()).onChat(p, e.getMessage());
        e.setCancelled(true);
        map.remove(p.getUniqueId());
    }

    public static void awaitChatInput(Player p, ChatHandler handler) {
        map.put(p.getUniqueId(), handler);
    }

    public static interface ChatHandler {
        public boolean onChat(Player var1, String var2);
    }

}

