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

package org.extendedalpha.pluginbase.general;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Clock {
    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getFormattedTime() {
        return Clock.format(Clock.getCurrentDate());
    }

    public static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(date);
    }

    public static Date getFutureDate(int days, int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Clock.getCurrentDate());
        calendar.add(5, days);
        calendar.add(10, hours);
        calendar.add(12, minutes);
        return calendar.getTime();
    }
}

