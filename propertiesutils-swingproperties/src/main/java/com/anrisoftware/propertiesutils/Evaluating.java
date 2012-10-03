package com.anrisoftware.propertiesutils;

import java.util.Map;

/**
 * Evaluating the script and return the result.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface Evaluating {

	<T> T evaluate(String script, Map<String, Object> variables);
}
