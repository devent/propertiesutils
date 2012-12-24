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
public class DateContextProperties extends ContextProperties {

	/**
	 * @version 1.5
	 */
	private static final long serialVersionUID = -3424187504752836390L;

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
