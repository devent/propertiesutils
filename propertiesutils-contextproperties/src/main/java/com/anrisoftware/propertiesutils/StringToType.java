/*
 * Copyright 2012-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of propertiesutils-contextproperties.
 *
 * propertiesutils-contextproperties is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * propertiesutils-contextproperties is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with propertiesutils-contextproperties. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.propertiesutils;

import java.text.ParseException;

/**
 * Parses the string value to the type.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.14
 */
public interface StringToType<T> {

    /**
     * Parses the string value to the type.
     *
     * @param value
     *            the {@link String} value.
     *
     * @return the parses type.
     *
     * @throws ParseException
     *             if there was an error parsing the value.
     */
    T stringToType(String value) throws ParseException;

}
