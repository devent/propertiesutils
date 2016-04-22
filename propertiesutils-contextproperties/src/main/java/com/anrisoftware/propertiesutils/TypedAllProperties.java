/*
 * Copyright 2012-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;

/**
 * Utility to return typed properties.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
public class TypedAllProperties extends TypedProperties {

    private final DateProperties dateProperties;

    public TypedAllProperties(Map<String, Object> properties,
            String listSepChars) {
        super(properties, listSepChars);
        this.dateProperties = new DateProperties(properties, listSepChars);
    }

    public TypedAllProperties(Map<String, Object> properties) {
        super(properties);
        this.dateProperties = new DateProperties(properties);
    }

    public TypedAllProperties(Properties properties, String listSepChars) {
        super(properties, listSepChars);
        this.dateProperties = new DateProperties(properties, listSepChars);
    }

    public TypedAllProperties(Properties properties) {
        super(properties);
        this.dateProperties = new DateProperties(properties);
    }

    public Period getPeriodProperty(String key) {
        return dateProperties.getPeriodProperty(key);
    }

    public Period getPeriodProperty(String key, PeriodFormatter formatter) {
        return dateProperties.getPeriodProperty(key, formatter);
    }

    public Duration getDurationProperty(String key) {
        return dateProperties.getDurationProperty(key);
    }

    public Duration getDurationProperty(String key, PeriodFormatter formatter) {
        return dateProperties.getDurationProperty(key, formatter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString())
                .toString();
    }
}
