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

package org.extendedalpha.pluginbase.general.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockAdjacents {
    public static Block[] getAdjacentBlocks(Block b) {
        return new Block[]{b.getRelative(BlockFace.UP), b.getRelative(BlockFace.DOWN), b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST)};
    }

    public static boolean hasAdjacentMaterial(Block b, Material m) {
        for (Block block : BlockAdjacents.getAdjacentBlocks(b)) {
            if (block.getType() != m) continue;
            return true;
        }
        return false;
    }

    public static boolean hasMaterialOnBothSides(Block b, Material m) {
        if (b.getRelative(BlockFace.NORTH).getType() == m && b.getRelative(BlockFace.SOUTH).getType() == m) {
            return true;
        }
        if (b.getRelative(BlockFace.EAST).getType() == m && b.getRelative(BlockFace.WEST).getType() == m) {
            return true;
        }
        return false;
    }

    public static boolean hasMaterialOnAllSides(Block b, Material m) {
        Block[] adjacents = BlockAdjacents.getAdjacentBlocks(b);
        if (adjacents[2].getType() == m && adjacents[3].getType() == m && adjacents[4].getType() == m && adjacents[5].getType() == m) {
            return true;
        }
        return false;
    }

    public static boolean isMaterial(Block b, Material m) {
        return m == null ? true : m == b.getType();
    }

    public static boolean hasMaterialOnSide(Block b, Material m) {
        if (m == null) {
            return true;
        }
        Block[] adjacents = BlockAdjacents.getAdjacentBlocks(b);
        if (adjacents[2].getType() == m || adjacents[3].getType() == m || adjacents[4].getType() == m || adjacents[5].getType() == m) {
            return true;
        }
        return false;
    }

    public static boolean hasMaterialOnTop(Block b, Material m) {
        if (b.getRelative(BlockFace.UP).getType() == m) {
            return true;
        }
        return false;
    }
}

