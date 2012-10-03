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
