/*
 * Copyright 2012-2026 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import java.util.Map;
import java.util.Properties;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/**
 * Extends the utility to return typed properties for Joda-Time typed.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 4.5.1
 */
@SuppressWarnings("serial")
public class JodaDateProperties extends TypedProperties {

    public JodaDateProperties(Map<String, Object> properties, String listSepChars) {
        super(properties, listSepChars);
    }

    public JodaDateProperties(Map<String, Object> properties) {
        super(properties);
    }

    public JodaDateProperties(Properties properties, String listSepChars) {
        super(properties, listSepChars);
    }

    public JodaDateProperties(Properties properties) {
        super(properties);
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
        String property = getProperty(key);
        if (property == null) {
            return null;
        } else {
            return new Period(property);
        }
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
        String property = getProperty(key);
        if (property == null) {
            return null;
        } else {
            return formatter.parsePeriod(property);
        }
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
        String property = getProperty(key);
        if (property == null) {
            return null;
        } else {
            return new Period(property).toStandardDuration();
        }
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
        String property = getProperty(key);
        if (property == null) {
            return null;
        } else {
            return formatter.parsePeriod(property).toStandardDuration();
        }
    }

}
