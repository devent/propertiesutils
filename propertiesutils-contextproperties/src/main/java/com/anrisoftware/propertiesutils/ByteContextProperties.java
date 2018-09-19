/*-
 * #%L
 * Properties Utilities :: Context Properties
 * %%
 * Copyright (C) 2012 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.anrisoftware.propertiesutils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Properties with a specified context returning byte array data properties.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.14
 */
@SuppressWarnings("serial")
public class ByteContextProperties extends ContextProperties {

    private final ByteProperties byteProperties;

    /**
     * Sets the context and the properties.
     *
     * @param context
     *            an {@link Class} that is used as the context.
     *
     * @param parentProperties
     *            the {@link Properties} that are returned.
     */
    public ByteContextProperties(Class<?> context, Properties parentProperties) {
        super(context, parentProperties);
        this.byteProperties = new ByteProperties(this);
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
        this.byteProperties = new ByteProperties(this);
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
        this.byteProperties = new ByteProperties(this);
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
        return byteProperties.getDataProperty(key);
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
        return byteProperties.getDataPropertyStream(key);
    }

}
