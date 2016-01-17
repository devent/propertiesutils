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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.thoughtworks.xstream.XStream;

/**
 * Properties with a specified context returning byte array data properties.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.14
 */
@SuppressWarnings("serial")
public class ByteContextProperties extends ContextProperties {

    /**
     * Sets the context and the properties.
     *
     * @param context
     *            an {@link Object} that is used as the context.
     *
     * @param parentProperties
     *            the {@link Properties} that are returned.
     */
    public ByteContextProperties(Class<?> context, Properties parentProperties) {
        super(context, parentProperties);
    }

    /**
     * Sets the context and the properties.
     *
     * @param context
     *            an {@link Object} that is used as the context.
     *
     * @param parentProperties
     *            the {@link Properties} that are returned.
     */
    public ByteContextProperties(Object context, Properties parentProperties) {
        super(context, parentProperties);
    }

    /**
     * Sets the context and the properties.
     *
     * @param context
     *            the context.
     *
     * @param parentProperties
     *            the {@link Properties} that are returned.
     */
    public ByteContextProperties(String context, Properties parentProperties) {
        super(context, parentProperties);
    }

    /**
     * Returns a data from a ROT13 property string.
     *
     * @param key
     *            the property key.
     *
     * @return the {@link Byte} array data.
     */
    public byte[] getDataProperty(String key) {
        String property = getProperty(key);
        byte[] data = (byte[]) new XStream().fromXML(property);
        return data;
    }

    /**
     * Returns a data from a ROT13 property string as a stream.
     *
     * @param key
     *            the property key.
     *
     * @return the {@link InputStream} data.
     */
    public InputStream getDataPropertyStream(String key) {
        String property = getProperty(key);
        byte[] data = (byte[]) new XStream().fromXML(property);
        return new ByteArrayInputStream(data);
    }

}
