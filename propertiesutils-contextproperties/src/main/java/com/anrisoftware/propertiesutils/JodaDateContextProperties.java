/*
 * Copyright 2012-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import java.util.Properties;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/**
 * Properties with a specified context returning data and time properties.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 4.5.1
 */
@SuppressWarnings("serial")
public class JodaDateContextProperties extends ContextProperties {

    private final JodaDateProperties dateProperties;

    /**
     * Sets the context and the properties.
     *
     * @param context          an {@link Class} that is used as the context.
     *
     * @param parentProperties the {@link Properties} that are returned.
     */
    public JodaDateContextProperties(Class<?> context, Properties parentProperties) {
        super(context, parentProperties);
        this.dateProperties = new JodaDateProperties(this);
    }

    /**
     * Sets the context and the properties.
     *
     * @param context          an {@link Object} that is used as the context.
     *
     * @param parentProperties the {@link Properties} that are returned.
     */
    public JodaDateContextProperties(Object context, Properties parentProperties) {
        super(context, parentProperties);
        this.dateProperties = new JodaDateProperties(this);
    }

    /**
     * Sets the context and the properties.
     *
     * @param context          the context.
     *
     * @param parentProperties the {@link Properties} that are returned.
     */
    public JodaDateContextProperties(String context, Properties parentProperties) {
        super(context, parentProperties);
        this.dateProperties = new JodaDateProperties(this);
    }

    /**
     * Returns a time period property using the format defined in
     * {@link ISOPeriodFormat#standard()}.
     *
     * @param key the property key.
     *
     * @return the {@link Period}.
     */
    public Period getPeriodProperty(String key) {
        return dateProperties.getPeriodProperty(key);
    }

    /**
     * Returns a time period property using the format defined in
     * {@link ISOPeriodFormat#standard()}.
     *
     * @param key       the property key.
     *
     * @param formatter the {@link PeriodFormatter} that parses the period property.
     *
     * @return the {@link Period}.
     */
    public Period getPeriodProperty(String key, PeriodFormatter formatter) {
        return dateProperties.getPeriodProperty(key, formatter);
    }

    /**
     * Returns a time duration property using the format defined in
     * {@link ISOPeriodFormat#standard()}.
     *
     * @param key the property key.
     *
     * @return the {@link Duration}.
     */
    public Duration getDurationProperty(String key) {
        return dateProperties.getDurationProperty(key);
    }

    /**
     * Returns a time duration property using the format defined in
     * {@link ISOPeriodFormat#standard()}.
     *
     * @param key       the property key.
     *
     * @param formatter the {@link PeriodFormatter} that parses the duration
     *                  property.
     *
     * @return the {@link Duration}.
     */
    public Duration getDurationProperty(String key, PeriodFormatter formatter) {
        return dateProperties.getDurationProperty(key, formatter);
    }

}
