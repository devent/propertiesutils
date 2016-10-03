/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Utility to return typed properties.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@SuppressWarnings("serial")
public class TypedAllProperties extends TypedProperties {

    private final DateProperties dateProperties;

    @AssistedInject
    TypedAllProperties(@Assisted Map<String, Object> properties,
            @Assisted String listSepChars) {
        super(properties, listSepChars);
        this.dateProperties = new DateProperties(properties, listSepChars);
    }

    @AssistedInject
    TypedAllProperties(@Assisted Map<String, Object> properties) {
        super(properties);
        this.dateProperties = new DateProperties(properties);
    }

    @AssistedInject
    TypedAllProperties(@Assisted Properties properties,
            @Assisted String listSepChars) {
        super(properties, listSepChars);
        this.dateProperties = new DateProperties(properties, listSepChars);
    }

    @AssistedInject
    TypedAllProperties(@Assisted Properties properties) {
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
