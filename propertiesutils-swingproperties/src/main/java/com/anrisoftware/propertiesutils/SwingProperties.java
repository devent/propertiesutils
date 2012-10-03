/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of propertiesutils.
 *
 * propertiesutils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * propertiesutils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with propertiesutils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.propertiesutils;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.script.ScriptException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Swing properties.
 * <p>
 * Defines additional methods to return different types of swing styles, i.e.
 * borders, colors, fonts.
 * </p>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SwingProperties extends ContextProperties {

	private final Evaluating evaluating;

	private SwingPropertiesLogger log;

	/**
	 * Sets the context and the properties.
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
	 */
	@AssistedInject
	SwingProperties(@Assisted Class<?> context,
			@Assisted Properties parentProperties,
			@Assisted Evaluating evaluating) {
		this(context.getPackage().getName(), parentProperties, evaluating);
	}

	/**
	 * Sets the context and the properties.
	 * 
	 * @param context
	 *            the context {@link Object}.
	 * 
	 * @param parentProperties
	 *            the parent {@link Properties}.
	 * 
	 * @param evaluating
	 *            the {@link Evaluating} to evaluate the script.
	 */
	@AssistedInject
	SwingProperties(@Assisted Object context,
			@Assisted Properties parentProperties,
			@Assisted Evaluating evaluating) {
		this(context.getClass(), parentProperties, evaluating);
	}

	/**
	 * Sets the context and the properties.
	 * 
	 * @param context
	 *            the custom context.
	 * 
	 * @param parentProperties
	 *            the parent {@link Properties}.
	 * 
	 * @param evaluating
	 *            the {@link Evaluating} to evaluate the script.
	 */
	@AssistedInject
	SwingProperties(@Assisted String context,
			@Assisted Properties parentProperties,
			@Assisted Evaluating evaluating) {
		super(context, parentProperties);
		this.evaluating = evaluating;
	}

	/**
	 * Injects the logger.
	 * 
	 * @param logger
	 *            the {@link SwingPropertiesLogger}.
	 */
	@Inject
	void setSwingPropertiesLogger(SwingPropertiesLogger logger) {
		log = logger;
	}

	/**
	 * Returns the styled component with the specified property key.
	 * <p>
	 * The evaluated script will receive the variable {@code it} that is the
	 * instance of the specified component. The script can then style the
	 * component and return the same component after it is finished.
	 * <p>
	 * Example {@code JLabel} component with a properties file:
	 * 
	 * <pre>
	 * JLabel label = new JLabel();
	 * label = getStyledComponent(label, &quot;label&quot;);
	 * </pre>
	 * 
	 * <pre>
	 * # properties file
	 * context.package.label = \n\
	 * 		it.setFont(new Font("Dialog", Font.BOLD, 12)); \n\
	 * 		it.setForderground(Color.RED);  \n\
	 * 		return it
	 * </pre>
	 * 
	 * @param component
	 *            the {@link Component} to be styled.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the styled {@link Component}.
	 * 
	 * @throws NullPointerException
	 *             if the specified component is {@code null}; if the evaluated
	 *             script returns a null value.
	 * 
	 * @throws IllegalStateException
	 *             if there was an error evaluating the script.
	 */
	public Component getStyledComponent(Component component, String key) {
		log.checkComponent(this, component);
		String script = getProperty(key);
		component = evaluate(script, component, new HashMap<String, Object>());
		log.checkResult(this, component, key);
		return component;
	}

	/**
	 * Returns the styled component with the specified property key.
	 * <p>
	 * The evaluated script will receive the variable {@code it} that is the
	 * instance of the specified component. The script can then style the
	 * component and return the same component after it is finished.
	 * <p>
	 * In addition the specified variables are passed on to the script and can
	 * be used.
	 * <p>
	 * Example {@code JPanel} component with a properties file:
	 * 
	 * <pre>
	 * JPanel panel = new JPanel();
	 * JLabel label = new JLabel();
	 * JTextField textField = new JTextField();
	 * 
	 * variables = new HashMap();
	 * variables.put(&quot;layout&quot;, layout);
	 * variables.put(&quot;textLabel&quot;, label);
	 * variables.put(&quot;textField&quot;, textField);
	 * panel = getStyledComponent(panel, &quot;panel&quot;, variables);
	 * </pre>
	 * 
	 * <pre>
	 * # properties file
	 * context.package.panel = \n\
	 * 		double[] col = ... \n\
	 * 		double[] row = ... \n\
	 * 		TableLayout layout = new TableLayout(col, row); \n\
	 * 		it.setLayout(layout); \n\
	 * 		it.add(textLabel, "0, 0");  \n\
	 * 		it.add(textField, "1, 0");  \n\
	 * 		return it
	 * </pre>
	 * 
	 * @param component
	 *            the {@link Component} to be styled.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param variables
	 *            the {@link Map} with additional variables.
	 * 
	 * @return the styled {@link Component}.
	 * 
	 * @throws NullPointerException
	 *             if the specified component is {@code null}; if the evaluated
	 *             script returns a null value.
	 * 
	 * @throws IllegalStateException
	 *             if there was an error evaluating the script.
	 */
	public Component getStyledComponent(Component component, String key,
			Map<String, Object> variables) {
		String script = getProperty(key);
		evaluate(script, component, variables);
		return component;
	}

	private <T> T evaluate(String script, Component component,
			Map<String, Object> variables) {
		log.checkScript(this, script);
		variables = new HashMap<String, Object>(variables);
		variables.put("it", component);
		log.evaluatingScript(script, variables);
		try {
			T result = evaluating.<T> evaluate(script, variables);
			return result;
		} catch (ScriptException e) {
			throw log.errorEvaluatingScript(this, e);
		}
	}

	@Override
	public synchronized String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
				.toString();
	}
}
