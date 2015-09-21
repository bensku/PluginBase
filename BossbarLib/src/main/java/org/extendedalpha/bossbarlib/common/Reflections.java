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

package org.extendedalpha.bossbarlib.common;

import java.lang.reflect.Field;

/**
 * An utility class that simplifies reflection in Bukkit plugins.
 *
 * @author ExileDev
 */
public final class Reflections {

    private Reflections() {
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param target    the target type
     * @param name      the name of the field, or NULL to ignore
     * @param fieldType a compatible field type
     * @return the field accessor
     */
    public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
        return getField(target, name, fieldType, 0);
    }

    // Common method
    private static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
        for (final Field field : target.getDeclaredFields()) {
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);

                // A function for retrieving a specific field value
                return new FieldAccessor<T>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public T get(Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public boolean hasField(Object target) {
                        // target instanceof DeclaringClass
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }

        // Search in parent classes
        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), name, fieldType, index);
        }
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }

    /**
     * An interface for retrieving the field content.
     *
     * @param <T> field type
     */
    public interface FieldAccessor<T> {

        /**
         * Retrieve the content of a field.
         *
         * @param target the target object, or NULL for a static field
         * @return the value of the field
         */
        T get(Object target);

        /**
         * Set the content of a field.
         *
         * @param target the target object, or NULL for a static field
         * @param value  the new value of the field
         */
        void set(Object target, Object value);

        /**
         * Determine if the given object has this field.
         *
         * @param target the object to test
         * @return TRUE if it does, FALSE otherwise
         */
        boolean hasField(Object target);

    }

}