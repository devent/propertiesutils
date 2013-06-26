/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of propertiesutils-swingproperties.
 *
 * propertiesutils-swingproperties is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * propertiesutils-swingproperties is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with propertiesutils-swingproperties. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.propertiesutils;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.Properties;

import com.anrisoftware.globalpom.format.point.PointFormat;

/**
 * Properties with a specified context returning AWT properties.
 * 
 * <ul>
 * <li>{@link Color}</li>
 * <li>{@link Point2D}</li>
 * </ul>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.7
 */
@SuppressWarnings("serial")
public class AwtContextProperties extends ContextProperties {

	/**
	 * Sets the context and the properties.
	 * 
	 * @param context
	 *            an {@link Object} that is used as the context.
	 * 
	 * @param parentProperties
	 *            the {@link Properties} that are returned.
	 */
	public AwtContextProperties(Class<?> context, Properties parentProperties) {
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
	public AwtContextProperties(Object context, Properties parentProperties) {
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
	public AwtContextProperties(String context, Properties parentProperties) {
		super(context, parentProperties);
	}

	/**
	 * Returns a color property using the format defined in
	 * {@link Color#decode(String)}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link Color} from the property or {@code null} if no
	 *         property with the key was found.
	 */
	public Color getColorProperty(String key) {
		String property = getProperty(key);
		return Color.decode(property);
	}

	/**
	 * Returns a color property using the format defined in
	 * {@link Color#decode(String)}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link Color}.
	 * 
	 * @return the {@link Color} from the property or the default color if no
	 *         property with the key was found.
	 */
	public Color getColorProperty(String key, Color defaultValue) {
		String property = getProperty(key);
		return property == null ? null : Color.decode(property);
	}

	/**
	 * Returns a point property using the format: {@code (x, y)}. The
	 * parenthesis are optional.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link Point2D} from the property or {@code null} if no
	 *         property with the key was found.
	 * 
	 * @throws ParseException
	 *             if the property cannot be parsed to a point.
	 */
	public Point2D getPointProperty(String key) throws ParseException {
		return getTypedProperty(key, new PointFormat());
	}

	/**
	 * Returns a point property using the format: {@code (x, y)}. The
	 * parenthesis are optional.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param point
	 *            the {@link Point2D} that should be parsed. If {@code null} a
	 *            {@link Point2D#Double} is used.
	 * 
	 * @return the {@link Point2D} from the property or {@code null} if no
	 *         property with the key was found.
	 * 
	 * @throws ParseException
	 *             if the property cannot be parsed to a point.
	 */
	public Point2D getPointProperty(String key, Point2D point)
			throws ParseException {
		String property = getProperty(key);
		return new PointFormat().parse(property, point);
	}

	/**
	 * Returns a point property using the format: {@code (x, y)}. The
	 * parenthesis are optional.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link Point2D}.
	 * 
	 * @throws ParseException
	 *             if the property cannot be parsed to a point.
	 * 
	 * @return the {@link Point2D} from the property or the default point if no
	 *         property with the key was found.
	 */
	public Point2D getPointPropertyWithDefault(String key, Point2D defaultValue)
			throws ParseException {
		return getTypedProperty(key, new PointFormat(), defaultValue);
	}

}
