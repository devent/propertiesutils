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

import static java.util.Arrays.asList;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.Properties;

import javax.swing.border.Border;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;

/**
 * Swing properties.
 * <p>
 * Defines additional methods to return different types of swing styles, i.e.
 * borders, colors, fonts.
 * </p>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.2
 */
@SuppressWarnings("serial")
public class SwingProperties extends ContextProperties {

	private final GroovyShell shell;

	/**
	 * Sets the context and the properties.
	 * 
	 * @param context
	 *            an {@link Object} that is used as the context.
	 * 
	 * @param parentProperties
	 *            the {@link Properties} that are returned.
	 */
	public SwingProperties(Class<?> context, Properties parentProperties) {
		this(context.getPackage().getName(), parentProperties);
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
	public SwingProperties(Object context, Properties parentProperties) {
		this(context.getClass(), parentProperties);
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
	public SwingProperties(String context, Properties parentProperties) {
		super(context, parentProperties);
		this.shell = createShell();
	}

	private GroovyShell createShell() {
		String[] staticStarImports = new String[] { "javax.swing.BorderFactory" };
		String[] staticImports = new String[] {};
		String[] starImports = new String[] {};
		String[] imports = new String[] { "java.awt.Color", "java.awt.Font",
				"javax.swing.border.BevelBorder",
				"javax.swing.border.CompoundBorder",
				"javax.swing.border.EmptyBorder",
				"javax.swing.border.EtchedBorder",
				"javax.swing.border.LineBorder",
				"javax.swing.border.SoftBevelBorder",
				"javax.swing.border.StrokeBorder", "java.awt.BorderLayout",
				"javax.swing.BoxLayout", "java.awt.CardLayout",
				"java.awt.FlowLayout", "java.awt.GridBagLayout",
				"java.awt.GridLayout", "javax.swing.GroupLayout",
				"javax.swing.OverlayLayout", "javax.swing.SpringLayout",
				"javax.swing.ViewportLayout",
				"info.clearthought.layout.TableLayout",
				"info.clearthought.layout.TableLayoutConstants" };

		CompilerConfiguration compiler = new CompilerConfiguration();
		ImportCustomizer importCustomizer = new ImportCustomizer();
		importCustomizer.addImports(imports);
		importCustomizer.addStarImports(starImports);
		importCustomizer.addStaticStars(staticStarImports);
		compiler.addCompilationCustomizers(importCustomizer);

		SecureASTCustomizer secure = new SecureASTCustomizer();
		secure.setClosuresAllowed(false);
		secure.setMethodDefinitionAllowed(false);
		secure.setImportsWhitelist(asList(imports));
		secure.setStarImportsWhitelist(asList(starImports));
		secure.setStaticImportsWhitelist(asList(staticImports));
		secure.setStaticStarImportsWhitelist(asList(staticStarImports));
		compiler.addCompilationCustomizers(secure);

		ClassLoader classLoader = getClass().getClassLoader();
		return new GroovyShell(classLoader, new Binding(), compiler);
	}

	public Border getBorderProperty(String key) {
		String script = getProperty(key);
		return evaluate(script);
	}

	public Border getBorderProperty(String key, Border defaultValue) {
		String script = getProperty(key);
		if (StringUtils.isEmpty(script)) {
			return defaultValue;
		} else {
			return evaluate(script);
		}
	}

	public Color getColorProperty(String key) {
		String script = getProperty(key);
		return evaluate(script);
	}

	public Color getColorProperty(String key, Color defaultValue) {
		String script = getProperty(key);
		if (StringUtils.isEmpty(script)) {
			return defaultValue;
		} else {
			return evaluate(script);
		}
	}

	public Font getFontProperty(String key) {
		String script = getProperty(key);
		return evaluate(script);
	}

	public Font getFontProperty(String key, Font defaultValue) {
		String script = getProperty(key);
		if (StringUtils.isEmpty(script)) {
			return defaultValue;
		} else {
			return evaluate(script);
		}
	}

	public LayoutManager getLayoutProperty(String key) {
		String script = getProperty(key);
		return evaluate(script);
	}

	public LayoutManager getLayoutProperty(String key,
			LayoutManager defaultValue) {
		String script = getProperty(key);
		if (StringUtils.isEmpty(script)) {
			return defaultValue;
		} else {
			return evaluate(script);
		}
	}

	private <T> T evaluate(String script) {
		@SuppressWarnings("unchecked")
		T result = (T) shell.evaluate(script);
		return result;
	}

}
