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

public class DoubleHandler {
    public static String getFancyDouble(double d) {
        DecimalFormat format = new DecimalFormat("##.##");
        double d2 = d / 1.0E15;
        if (d2 > 1.0) {
            return String.valueOf(format.format(d2).replace((CharSequence)",", (CharSequence)".")) + "Q";
        }
        d2 = d / 1.0E12;
        if (d2 > 1.0) {
            return String.valueOf(format.format(d2).replace((CharSequence)",", (CharSequence)".")) + "T";
        }
        d2 = d / 1.0E9;
        if (d2 > 1.0) {
            return String.valueOf(format.format(d2).replace((CharSequence)",", (CharSequence)".")) + "B";
        }
        d2 = d / 1000000.0;
        if (d2 > 1.0) {
            return String.valueOf(format.format(d2).replace((CharSequence)",", (CharSequence)".")) + "M";
        }
        d2 = d / 1000.0;
        if (d2 > 1.0) {
            return String.valueOf(format.format(d2).replace((CharSequence)",", (CharSequence)".")) + "K";
        }
        return format.format(d).replace((CharSequence)",", (CharSequence)".");
    }

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

    public static double fixDouble(double amount) {
        return DoubleHandler.fixDouble(amount, 2);
    }
}

