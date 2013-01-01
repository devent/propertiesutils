/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of propertiesutils-contextproperties.
 *
 * propertiesutils-contextproperties is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * propertiesutils-contextproperties is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with propertiesutils-contextproperties. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.propertiesutils;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * Properties with a specified context.
 * </p>
 * <p>
 * The properties keys are prefixed with the context, if not already present.
 * The context is an object package name. For example, if the context object is
 * {@link ContextProperties} then the context is
 * {@code "com.anrisoftware.propertiesutils"}. If then a property with the name
 * {@code foo} is requested, then the property
 * {@code "com.anrisoftware.propertiesutils.foo"} is looked up.
 * </p>
 * <p>
 * Defines additional methods to return different types of properties, like
 * boolean, number, list.
 * </p>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class ContextProperties extends Properties {

	private static final String LIST_SEPARATOR_CHARS = " ,;";

	private static final long serialVersionUID = 3495658613155578555L;

	private static final String REPLACEMENT_PATTERN = "${%s}";

	private final String context;

	private final HashMap<String, Serializable> replacements;

	/**
	 * Sets the context and the properties.
	 * 
	 * @param context
	 *            an {@link Object} that is used as the context.
	 * 
	 * @param parentProperties
	 *            the {@link Properties} that are returned.
	 */
	public ContextProperties(Object context, Properties parentProperties) {
		this(context.getClass(), parentProperties);
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
	public ContextProperties(Class<?> context, Properties parentProperties) {
		this(context.getPackage().getName(), parentProperties);
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
	public ContextProperties(String context, Properties parentProperties) {
		super(parentProperties);
		this.context = context;
		this.replacements = new HashMap<String, Serializable>();
	}

	/**
	 * Adds the replacements from the specified map.
	 * <p>
	 * Each of the replacement entries is applied to the property. The
	 * replacement is of format <code>{$key}</code>.
	 * <p>
	 * It is possible to add the system properties directly as in
	 * {@code p.withReplacements(System.getProperties())}.
	 * 
	 * @param map
	 *            the {@link Map} that contains the replacements as
	 *            {@code <key>: <value>}.
	 * 
	 * @return this {@link ContextProperties}.
	 * 
	 * @throws NullPointerException
	 *             if the specified map is {@code null}.
	 * 
	 * @since 1.3
	 */
	public ContextProperties withReplacements(Map<?, ?> map) {
		notNull(map);
		Serializable replace;
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			if (entry.getValue() instanceof Serializable) {
				replace = (Serializable) entry.getValue();
			} else {
				replace = entry.getValue().toString();
			}
			this.replacements.put(key, replace);
		}
		return this;
	}

	/**
	 * Adds the replacement with the specified key.
	 * <p>
	 * Each of the replacement entries is applied to the property. The
	 * replacement is of format <code>{$key}</code>.
	 * 
	 * @param key
	 *            the replacement key.
	 * 
	 * @param replace
	 *            the {@link Serializable} object as the replacement.
	 * 
	 * @return this {@link ContextProperties}.
	 * 
	 * @since 1.3
	 */
	public ContextProperties withReplacement(String key, Serializable replace) {
		replacements.put(key, replace);
		return this;
	}

	/**
	 * Returns the context of this properties.
	 * 
	 * @return the context.
	 * 
	 * @since 1.5
	 */
	public String getContext() {
		return context;
	}

	@Override
	public String getProperty(String key) {
		String value = super.getProperty(keyWithContext(key));
		return applyReplacements(value);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		String value = super.getProperty(keyWithContext(key), defaultValue);
		return applyReplacements(value);
	}

	private String applyReplacements(String value) {
		if (value == null) {
			return value;
		}
		for (Map.Entry<String, Serializable> entry : replacements.entrySet()) {
			String replace = entry.getValue().toString();
			String key = entry.getKey();
			value = replace(value, format(REPLACEMENT_PATTERN, key), replace);
		}
		return value;
	}

	/**
	 * Returns a string property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link String}.
	 * 
	 * @return the {@link String} from the property or the default string if no
	 *         property with the key was found.
	 */
	public String getStringProperty(String key, String defaultValue) {
		return getProperty(key, defaultValue);
	}

	/**
	 * Returns a boolean property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link Boolean} from the property or {@code null} if no
	 *         property with the key was found.
	 */
	public Boolean getBooleanProperty(String key) {
		String property = getProperty(key);
		return property == null ? null : parseBoolean(property);
	}

	/**
	 * Returns a boolean property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link Boolean}.
	 * 
	 * @return the {@link Boolean} from the property or the default boolean if
	 *         no property with the key was found.
	 */
	public Boolean getBooleanProperty(String key, Boolean defaultValue) {
		String property = getProperty(key, String.valueOf(defaultValue));
		return parseBoolean(property);
	}

	/**
	 * Returns a number property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link Number} from the property or {@code null} if no
	 *         property with the key was found.
	 */
	public Number getNumberProperty(String key) {
		String property = getProperty(key);
		return property == null ? null : parseDouble(property);
	}

	/**
	 * Returns a number property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link Number}.
	 * 
	 * @return the {@link Number} from the property or the default number if no
	 *         property with the key was found.
	 * 
	 * @throws NumberFormatException
	 *             if the string does not contain a parseble {@code double}.
	 */
	public Number getNumberProperty(String key, Number defaultValue) {
		String property = getProperty(key, String.valueOf(defaultValue));
		return parseDouble(property);
	}

	/**
	 * Returns a character property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link Character} from the property or {@code null} if no
	 *         property with the key was found.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the property is an empty string.
	 */
	public Character getCharProperty(String key) {
		String property = getProperty(key);
		return property == null ? null : property.charAt(0);
	}

	/**
	 * Returns a character property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link Character}.
	 * 
	 * @return the {@link Character} from the property or the default value if
	 *         no property with the key was found.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the property is an empty string.
	 */
	public Character getCharProperty(String key, Character defaultValue) {
		String property = getProperty(key);
		return property == null ? defaultValue : property.charAt(0);
	}

	/**
	 * Returns a character set property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link Charset} from the property or {@code null} if no
	 *         property with the key was found.
	 * 
	 * @throws UnsupportedCharsetException
	 *             If no support for the named character set is available in
	 *             this instance of the Java virtual machine.
	 */
	public Charset getCharsetProperty(String key) {
		String property = getProperty(key);
		return property == null ? null : Charset.forName(property);
	}

	/**
	 * Returns a character set property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link Charset}.
	 * 
	 * @return the {@link Charset} from the property or the default character
	 *         set if no property with the key was found.
	 * 
	 * @throws UnsupportedCharsetException
	 *             If no support for the named character set is available in
	 *             this instance of the Java virtual machine.
	 */
	public Charset getCharsetProperty(String key, Charset defaultValue) {
		String property = getProperty(key, String.valueOf(defaultValue));
		return Charset.forName(property);
	}

	/**
	 * Returns a URL property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link URL} from the property or {@code null} if no property
	 *         with the key was found.
	 * 
	 * @throws MalformedURLException
	 *             if the property value is not a valid URL.
	 * 
	 * @since 1.1
	 */
	public URL getURLProperty(String key) throws MalformedURLException {
		String property = getProperty(key);
		return property == null ? null : new URL(property);
	}

	/**
	 * Returns a URL property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link URL}.
	 * 
	 * @return the {@link URL} from the property or the default URL if no
	 *         property with the key was found.
	 * 
	 * @throws MalformedURLException
	 *             if the property value is not a valid URL.
	 * 
	 * @since 1.1
	 */
	public URL getURLProperty(String key, URL defaultValue)
			throws MalformedURLException {
		String property = getProperty(key);
		return property == null ? defaultValue : new URL(property);
	}

	/**
	 * Returns a URI property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link URI} from the property or {@code null} if no property
	 *         with the key was found.
	 * 
	 * @throws URISyntaxException
	 *             if the property value is not a valid URI.
	 * 
	 * @since 1.1
	 */
	public URI getURIProperty(String key) throws URISyntaxException {
		String property = getProperty(key);
		return property == null ? null : new URI(property);
	}

	/**
	 * Returns a URI property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link URI}.
	 * 
	 * @return the {@link URI} from the property or the default URI if no
	 *         property with the key was found.
	 * 
	 * @throws URISyntaxException
	 *             if the property value is not a valid URI.
	 * 
	 * @since 1.1
	 */
	public URI getURIProperty(String key, URI defaultValue)
			throws URISyntaxException {
		String property = getProperty(key, String.valueOf(defaultValue));
		return new URI(property);
	}

	/**
	 * Returns a File property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link File} from the property or {@code null} if no property
	 *         with the key was found.
	 * 
	 * @since 1.1
	 */
	public File getFileProperty(String key) {
		String property = getProperty(key);
		return property == null ? null : new File(property);
	}

	/**
	 * Returns a File property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link File}.
	 * 
	 * @return the {@link File} from the property or the default File if no
	 *         property with the key was found.
	 * 
	 * @since 1.1
	 */
	public File getFileProperty(String key, File defaultValue) {
		String property = getProperty(key);
		return property == null ? defaultValue : new File(property);
	}

	/**
	 * Returns a property of the specified type.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param format
	 *            the {@link Format} to parse the type.
	 * 
	 * @return the the property or {@code null} if no property with the key was
	 *         found.
	 * 
	 * @throws ParseException
	 *             if the property cannot be parsed to the type.
	 */
	public <T> T getTypedProperty(String key, Format format)
			throws ParseException {
		return getTypedProperty(key, format, null);
	}

	/**
	 * Returns a property of the specified type.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default value.
	 * 
	 * @param format
	 *            the {@link Format} to parse the type.
	 * 
	 * @return the the property or {@code null} if no property with the key was
	 *         found.
	 * 
	 * @throws ParseException
	 *             if the property cannot be parsed to the type.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getTypedProperty(String key, Format format, T defaultValue)
			throws ParseException {
		String property = getProperty(key, String.valueOf(defaultValue));
		return property == null ? null : (T) format.parseObject(property);
	}

	/**
	 * Returns a list property. The separator characters are
	 * {@value #LIST_SEPARATOR_CHARS}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param format
	 *            the {@link Format} to parse the key values.
	 * 
	 * @return the {@link List} from the property or an empty list if no
	 *         property with the key was found.
	 * 
	 * @throws ParseException
	 *             if there was an error to parse a key value.
	 */
	public <T> List<T> getTypedListProperty(String key, Format format)
			throws ParseException {
		return getTypedListProperty(key, format, LIST_SEPARATOR_CHARS);
	}

	/**
	 * Returns a list property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param format
	 *            the {@link Format} to parse the key values.
	 * 
	 * @param separatorChars
	 *            the characters used as the delimiters, {@code null} splits on
	 *            whitespace.
	 * 
	 * @return the {@link List} from the property or an empty list if no
	 *         property with the key was found.
	 * 
	 * @throws ParseException
	 *             if there was an error to parse a key value.
	 * 
	 * @since 1.4
	 */
	public <T> List<T> getTypedListProperty(String key, Format format,
			String separatorChars) throws ParseException {
		List<T> list = new ArrayList<T>();
		String property = getProperty(key);
		if (property == null) {
			return list;
		}
		for (String value : split(property, separatorChars)) {
			addParsedObject(list, format, value);
		}
		return list;
	}

	/**
	 * Returns a list property. The separator characters are
	 * {@value #LIST_SEPARATOR_CHARS}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param format
	 *            the {@link Format} to parse the key values.
	 * 
	 * @param defaultValue
	 *            the default {@link List}.
	 * 
	 * @return the {@link List} from the property or the default list if no
	 *         property with the key was found.
	 * 
	 * @throws ParseException
	 *             if there was an error to parse a key value.
	 */
	public <T> List<T> getTypedListProperty(String key, Format format,
			List<T> defaultValue) throws ParseException {
		return getTypedListProperty(key, format, defaultValue,
				LIST_SEPARATOR_CHARS);
	}

	/**
	 * Returns a list property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param format
	 *            the {@link Format} to parse the key values.
	 * 
	 * @param defaultValue
	 *            the default {@link List}.
	 * 
	 * @param separatorChars
	 *            the characters used as the delimiters, {@code null} splits on
	 *            whitespace.
	 * 
	 * @return the {@link List} from the property or the default list if no
	 *         property with the key was found.
	 * 
	 * @throws ParseException
	 *             if there was an error to parse a key value.
	 * 
	 * @since 1.4
	 */
	public <T> List<T> getTypedListProperty(String key, Format format,
			List<T> defaultValue, String separatorChars) throws ParseException {
		List<T> list = new ArrayList<T>();
		String property = getProperty(key, join(defaultValue, ","));
		for (String value : split(property, LIST_SEPARATOR_CHARS)) {
			addParsedObject(list, format, value);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private <T> void addParsedObject(List<T> list, Format format, String value)
			throws ParseException {
		list.add((T) format.parseObject(value));
	}

	/**
	 * Returns a list property. The separator characters are
	 * {@value #LIST_SEPARATOR_CHARS}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link List} from the property or an empty list if no
	 *         property with the key was found.
	 */
	public List<String> getListProperty(String key) {
		return getListProperty(key, LIST_SEPARATOR_CHARS);
	}

	/**
	 * Returns a list property. The separator characters are
	 * {@value #LIST_SEPARATOR_CHARS}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param separatorChars
	 *            the characters used as the delimiters, {@code null} splits on
	 *            whitespace.
	 * 
	 * @return the {@link List} from the property or an empty list if no
	 *         property with the key was found.
	 * 
	 * @since 1.4
	 */
	public List<String> getListProperty(String key, String separatorChars) {
		String property = getProperty(key);
		return property == null ? new ArrayList<String>() : asList(split(
				property, separatorChars));
	}

	/**
	 * Returns a list property. The separator characters are
	 * {@value #LIST_SEPARATOR_CHARS}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link List}.
	 * 
	 * @return the {@link List} from the property or the default list if no
	 *         property with the key was found.
	 */
	public List<String> getListProperty(String key, List<String> defaultValue) {
		return getListProperty(key, defaultValue, LIST_SEPARATOR_CHARS);
	}

	/**
	 * Returns a list property. The separator characters are
	 * {@value #LIST_SEPARATOR_CHARS}.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @param defaultValue
	 *            the default {@link List}.
	 * 
	 * @param separatorChars
	 *            the characters used as the delimiters, {@code null} splits on
	 *            whitespace.
	 * 
	 * @return the {@link List} from the property or the default list if no
	 *         property with the key was found.
	 * 
	 * @since 1.4
	 */
	public List<String> getListProperty(String key, List<String> defaultValue,
			String separatorChars) {
		String property = getProperty(key, join(defaultValue, ","));
		return Arrays.asList(split(property, separatorChars));
	}

	@Override
	public synchronized Object get(Object key) {
		return getProperty(String.valueOf(key));
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		key = keyWithContext(String.valueOf(key));
		return super.put(key, value);
	}

	private String keyWithContext(String key) {
		if (!startsWith(key, context)) {
			key = String.format("%s.%s", context, key);
		}
		return key;
	}

}
