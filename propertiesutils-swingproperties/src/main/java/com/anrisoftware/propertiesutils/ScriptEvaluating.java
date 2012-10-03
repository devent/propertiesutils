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

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 * Evaluating the script with the Java Scripting (JSR 223).
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class ScriptEvaluating implements Evaluating {

	private static final String NO_SCRIPT_ENGINE = "Could not find script engine ``%s''.";

	private final ScriptEngine engine;

	/**
	 * Sets the engine name that should be used.
	 * 
	 * @param engineName
	 *            the name of the engine.
	 */
	public ScriptEvaluating(String engineName) {
		this.engine = new ScriptEngineManager().getEngineByName(engineName);
		notNull(engine, NO_SCRIPT_ENGINE, engineName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T evaluate(String script, Map<String, Object> variables)
			throws ScriptException {
		Bindings bindings = new SimpleBindings(variables);
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		return (T) engine.eval(script);
	}

}
