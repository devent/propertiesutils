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

import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import java.awt.Component;
import java.util.Map;

import javax.script.ScriptException;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link SwingProperties}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class SwingPropertiesLogger extends AbstractLogger {

	/**
	 * Create logger for {@link SwingProperties}.
	 */
	SwingPropertiesLogger() {
		super(SwingProperties.class);
	}

	void checkScript(SwingProperties p, String script) {
		notEmpty(script,
				"The script for the swing property cannot be empty in %s.", p);
	}

	void evaluatingScript(String script, Map<String, Object> variables) {
		log.trace("Evaluating the script with the variables {}: ``{}''.",
				variables, script);
	}

	void checkResult(SwingProperties p, Object result, String key) {
		notNull(result,
				"The swing property script with the key ``%s'' cannot return null in %s.",
				key, p);
	}

	void checkComponent(SwingProperties p, Component component) {
		notNull(component,
				"The component for the swing property cannot be null in %s.", p);
	}

	IllegalStateException errorEvaluatingScript(SwingProperties p,
			ScriptException e) {
		IllegalStateException ex = new IllegalStateException(format(
				"Error evaluating the script in %s", p), e);
		log.error(ex.getLocalizedMessage());
		return ex;
	}
}
