/*
 * Copyright 2012-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import java.util.Properties;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/**
 * Properties with a specified context returning data and time properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@SuppressWarnings("serial")
public class DateContextProperties extends ContextProperties {

	/**
	 * Sets the context and the properties.
	 * 
	 * @param context
	 *            an {@link Object} that is used as the context.
	 * 
	 * @param parentProperties
	 *            the {@link Properties} that are returned.
	 */
	public DateContextProperties(Class<?> context, Properties parentProperties) {
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
	public DateContextProperties(Object context, Properties parentProperties) {
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
	public DateContextProperties(String context, Properties parentProperties) {
		super(context, parentProperties);
	}

	/**
	 * Returns a time period property using the format defined in
	 * {@link ISOPeriodFormat#standard()}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link Period}.
	 */
	public Period getPeriodProperty(String key) {
		String property = getProperty(key);
		return new Period(property);
	}

	/**
	 * Returns a time period property using the format defined in
	 * {@link ISOPeriodFormat#standard()}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param formatter
	 *            the {@link PeriodFormatter} that parses the period property.
	 * 
	 * @return the {@link Period}.
	 */
	public Period getPeriodProperty(String key, PeriodFormatter formatter) {
		String property = getProperty(key);
		return formatter.parsePeriod(property);
	}

	/**
	 * Returns a time duration property using the format defined in
	 * {@link ISOPeriodFormat#standard()}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link Duration}.
	 */
	public Duration getDurationProperty(String key) {
		String property = getProperty(key);
		return new Period(property).toStandardDuration();
	}

	/**
	 * Returns a time duration property using the format defined in
	 * {@link ISODurationFormat#standard()}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param formatter
	 *            the {@link PeriodFormatter} that parses the duration property.
	 * 
	 * @return the {@link Duration}.
	 */
	public Duration getDurationProperty(String key, PeriodFormatter formatter) {
		String property = getProperty(key);
		return formatter.parsePeriod(property).toStandardDuration();
	}

}
