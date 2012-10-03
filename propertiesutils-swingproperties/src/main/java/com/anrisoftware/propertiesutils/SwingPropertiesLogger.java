package com.anrisoftware.propertiesutils;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import java.awt.Component;
import java.util.Map;

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
}
