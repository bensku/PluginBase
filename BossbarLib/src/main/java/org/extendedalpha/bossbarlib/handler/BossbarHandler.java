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

package org.extendedalpha.bossbarlib.handler;

import org.extendedalpha.bossbarlib.type.Bossbar;
import org.bukkit.entity.Player;

public interface BossbarHandler {

    /**
     * Returns the bossbar of a player. If the player does not have a bossbar, a new instance will be created and returned.
     *
     * @param player player
     * @return bossbar
     */
    Bossbar getBossbar(Player player);

    /**
     * Returns either the player already has a bossbar.
     *
     * @param player player
     * @return availability of bossbar
     */
    boolean hasBossbar(Player player);

    /**
     * Send the bossbar to the player. If the player does not have a bossbar, no action will be performed.
     * You do not need to call this method as the implementation automatically updates the bossbar when it is changed.
     *
     * @param player player
     */
    void updateBossbar(Player player);

    /**
     * Clear and remove the bossbar of the player.
     *
     * @param player player
     */
    void clearBossbar(Player player);

}
