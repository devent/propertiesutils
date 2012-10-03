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

import java.util.Map;

import javax.script.ScriptException;

/**
 * Evaluating the script and return the result.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface Evaluating {

	/**
	 * Evaluate the specified script with the variables.
	 * 
	 * @param script
	 *            the script to evaluate.
	 * 
	 * @param variables
	 *            the {@link Map} of variables that are made available in the
	 *            script.
	 * 
	 * @return the result of the script.
	 * 
	 * @throws ScriptException
	 *             if there was an error evaluating the script.
	 */
	<T> T evaluate(String script, Map<String, Object> variables)
			throws ScriptException;
}
