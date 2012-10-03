/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
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

import java.util.Properties;

/**
 * Factory to create swing properties from either the context class, context
 * object or a custom context.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface SwingPropertiesFactory {

	/**
	 * Creates the swing properties from the specified context class.
	 * 
	 * @param context
	 *            the context {@link Class}.
	 * 
	 * @param parentProperties
	 *            the parent {@link Properties}.
	 * 
	 * @param evaluating
	 *            the {@link Evaluating} to evaluate the script.
	 * 
	 * @return the {@link SwingProperties}.
	 */
	SwingProperties create(Class<?> context, Properties parentProperties,
			Evaluating evaluating);

	/**
	 * Creates the swing properties from the specified context class.
	 * 
	 * @param context
	 *            the context {@link Object}.
	 * 
	 * @param parentProperties
	 *            the parent {@link Properties}.
	 * 
	 * @param evaluating
	 *            the {@link Evaluating} to evaluate the script.
	 * 
	 * @return the {@link SwingProperties}.
	 */
	SwingProperties create(Object context, Properties parentProperties,
			Evaluating evaluating);

	/**
	 * Creates the swing properties from the specified context class.
	 * 
	 * @param context
	 *            the custom context.
	 * 
	 * @param parentProperties
	 *            the parent {@link Properties}.
	 * 
	 * @param evaluating
	 *            the {@link Evaluating} to evaluate the script.
	 * 
	 * @return the {@link SwingProperties}.
	 */
	SwingProperties create(String context, Properties parentProperties,
			Evaluating evaluating);
}
