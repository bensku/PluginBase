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

package org.extendedalpha.pluginbase.general.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerManager {
    public static void reset(Player p) {
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20.0);
        p.setFoodLevel(20);
        PlayerManager.clearEffects(p);
        p.setExp(0.0f);
        p.setLevel(0);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        PlayerInventory.update(p);
    }

    public static void clearEffects(Player p) {
        for (PotionEffect e : p.getActivePotionEffects()) {
            p.removePotionEffect(e.getType());
        }
    }

    public static void loseHunger(Player p, int amount) {
        if (p.getGameMode() != GameMode.CREATIVE) {
            int starve = p.getFoodLevel() - amount;
            if (starve < 0) {
                starve = 0;
            }
            p.setFoodLevel(starve);
        }
    }
}

