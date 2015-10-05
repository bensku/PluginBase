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

package org.extendedalpha.pluginbase.general.recipe;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class RecipeManager {
    public static void removeRecipe(Material type) {
        Iterator recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            Recipe recipe = (Recipe)recipes.next();
            if (recipe == null || recipe.getResult().getType() != type) continue;
            recipes.remove();
        }
    }

    public static void removeVanillaRecipe(Material type) {
        Iterator recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            Recipe recipe = (Recipe)recipes.next();
            if (recipe == null || !recipe.getResult().isSimilar(new ItemStack(type))) continue;
            recipes.remove();
        }
    }

    public static void removeRecipe(Material type, short durability) {
        Iterator recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            Recipe recipe = (Recipe)recipes.next();
            if (recipe == null || recipe.getResult().getType() != type || recipe.getResult().getDurability() != durability) continue;
            recipes.remove();
        }
    }
}

