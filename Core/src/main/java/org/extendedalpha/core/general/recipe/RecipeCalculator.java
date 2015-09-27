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

package org.extendedalpha.core.general.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeCalculator {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static List<ItemStack> getIngredients(Recipe recipe) {
        ArrayList<ItemStack> ingredients = new ArrayList<ItemStack>();
        if (recipe instanceof ShapedRecipe) {
            String[] shape;
            ShapedRecipe sr = (ShapedRecipe)recipe;
            for (String row : shape = sr.getShape()) {
                for (int i = 0; i < row.length(); ++i) {
                    ItemStack stack = (ItemStack)sr.getIngredientMap().get(Character.valueOf(row.charAt(i)));
                    for (ItemStack ing : ingredients) {
                        int mss = ing.getType().getMaxStackSize();
                        if (!ing.isSimilar(stack) || ing.getAmount() >= mss) continue;
                        int canAdd = mss - ing.getAmount();
                        int add = Math.min(canAdd, stack.getAmount());
                        ing.setAmount(ing.getAmount() + add);
                        int remaining = stack.getAmount() - add;
                        if (remaining >= 1) {
                            stack.setAmount(remaining);
                            continue;
                        }
                        stack = null;
                        break;
                    }
                    if (stack == null || stack.getAmount() <= 0) continue;
                    ingredients.add(stack);
                }
            }
            return ingredients;
        } else {
            if (!(recipe instanceof ShapelessRecipe)) return ingredients;
            for (ItemStack i : ((ShapelessRecipe)recipe).getIngredientList()) {
                for (ItemStack ing : ingredients) {
                    int mss = ing.getType().getMaxStackSize();
                    if (!ing.isSimilar(i) || ing.getAmount() >= mss) continue;
                    int canAdd = mss - ing.getAmount();
                    ing.setAmount(ing.getAmount() + Math.min(canAdd, i.getAmount()));
                    int remaining = i.getAmount() - Math.min(canAdd, i.getAmount());
                    if (remaining < 1) break;
                    i.setAmount(remaining);
                }
                if (i.getAmount() <= 0) continue;
                ingredients.add(i);
            }
        }
        return ingredients;
    }

    public static ItemStack getSmeltedOutput(Material type) {
        ItemStack result = null;
        Iterator iter = Bukkit.recipeIterator();
        while (iter.hasNext()) {
            Recipe recipe = (Recipe)iter.next();
            if (!(recipe instanceof FurnaceRecipe)) continue;
            if (((FurnaceRecipe)recipe).getInput().getType() != type) continue;
            result = recipe.getResult();
            break;
        }
        return result;
    }
}

