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

package org.extendedalpha.bossbarlib;

import org.extendedalpha.bossbarlib.handler.BossbarHandler;
import org.extendedalpha.bossbarlib.handler.WitherBossbarHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;

public final class BossbarLib extends JavaPlugin {

    private static Plugin instance;
    private static BossbarHandler handler;

    public static Plugin getPluginInstance() {
        return instance;
    }

    public static void setPluginInstance(Plugin instance) {
        if (BossbarLib.instance != null) return;
        BossbarLib.instance = instance;
        setHandler(new WitherBossbarHandler());
    }

    public static BossbarHandler getHandler() {
        return handler;
    }

    public static void setHandler(BossbarHandler handler) {
        BossbarLib.handler = handler;
    }

    @Override
    public void onEnable() {
        setPluginInstance(this);
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().warning("Couldn't submit metrics stats: " + e.getMessage());
        }
    }
}

