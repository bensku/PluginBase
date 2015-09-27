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

package org.extendedalpha.core.general.math;

import java.text.DecimalFormat;
import org.bukkit.Location;

public class Calculator {
    public static int formToLine(int i) {
        int lines = 1;
        if (i > 9) {
            ++lines;
        }
        if (i > 18) {
            ++lines;
        }
        if (i > 27) {
            ++lines;
        }
        if (i > 36) {
            ++lines;
        }
        if (i > 45) {
            ++lines;
        }
        if (i > 54) {
            ++lines;
        }
        return lines;
    }

    public static Location centerPosition(Location l) {
        double x = l.getX();
        double z = l.getZ();
        String[] rawX = String.valueOf(x).split(".");
        String[] rawZ = String.valueOf(z).split(".");
        String newX = String.valueOf(rawX[0]) + ".5";
        String newZ = String.valueOf(rawZ[0]) + ".5";
        l.setX(Double.parseDouble(newX));
        l.setZ(Double.parseDouble(newZ));
        return l;
    }

    @Deprecated
    public static double fixDouble(double amount, int digits) {
        if (digits == 0) {
            return (int)amount;
        }
        StringBuilder format = new StringBuilder("##");
        for (int i = 0; i < digits; ++i) {
            if (i == 0) {
                format.append(".");
            }
            format.append("#");
        }
        return Double.valueOf(new DecimalFormat(format.toString()).format(amount).replace((CharSequence)",", (CharSequence)"."));
    }

    @Deprecated
    public static double fixDouble(double amount) {
        return Calculator.fixDouble(amount, 2);
    }
}

