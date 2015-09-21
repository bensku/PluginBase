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

import org.extendedalpha.bossbarlib.common.Maths;
import org.bukkit.Location;

public abstract class WitherBossbar implements Bossbar {

    protected static final float MAX_HEALTH = 300;

    protected boolean spawned;
    protected Location spawnLocation;

    protected String name;
    protected float health;

    public WitherBossbar(String message, Location spawnLocation) {
        this.spawnLocation = spawnLocation;
        this.name = message;
        this.health = MAX_HEALTH;
    }

    @Override
    public String getMessage() {
        return name;
    }

    @Override
    public WitherBossbar setMessage(String message) {
        this.name = message;
        return this;
    }

    @Override
    public float getPercentage() {
        return health / MAX_HEALTH;
    }

    @Override
    public WitherBossbar setPercentage(float percentage) {
        percentage = Maths.clamp(percentage, 0f, 1f);
        health = percentage * MAX_HEALTH;
        return this;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

}
