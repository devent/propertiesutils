/**
 * Copyright © 2012 Erwin Müller (erwin.mueller@anrisoftware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anrisoftware.propertiesutils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.thoughtworks.xstream.XStream;

/**
 * Extends the utility to return typed properties for byte-streams.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@SuppressWarnings("serial")
public class ByteProperties extends TypedProperties {

    public ByteProperties(Map<String, Object> properties, String listSepChars) {
        super(properties, listSepChars);
    }

    public ByteProperties(Map<String, Object> properties) {
        super(properties);
    }

    public ByteProperties(Properties properties, String listSepChars) {
        super(properties, listSepChars);
    }

    public ByteProperties(Properties properties) {
        super(properties);
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString())
                .toString();
    }
}
