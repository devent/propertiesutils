package com.anrisoftware.propertiesutils;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.startsWith;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	private final String context;

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
		super(parentProperties);
		this.context = context.getPackage().getName();
	}

	@Override
	public String getProperty(String key) {
		return super.getProperty(keyWithContext(key));
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return super.getProperty(keyWithContext(key));
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
	 * Returns a list property.
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
		List<T> list = new ArrayList<T>();
		String property = getProperty(key);
		if (property == null) {
			return list;
		}
		for (String value : split(property, LIST_SEPARATOR_CHARS)) {
			addParsedObject(list, format, value);
		}
		return list;
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
	 * @return the {@link List} from the property or the default list if no
	 *         property with the key was found.
	 * 
	 * @throws ParseException
	 *             if there was an error to parse a key value.
	 */
	public <T> List<T> getTypedListProperty(String key, Format format,
			List<T> defaultValue) throws ParseException {
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
	 * Returns a list property.
	 * 
	 * @param key
	 *            the property key.
	 * 
	 * @return the {@link List} from the property or an empty list if no
	 *         property with the key was found.
	 */
	public List<String> getListProperty(String key) {
		String property = getProperty(key);
		return property == null ? new ArrayList<String>() : asList(split(
				property, LIST_SEPARATOR_CHARS));
	}

	/**
	 * Returns a list property.
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
		String property = getProperty(key, join(defaultValue, ","));
		return Arrays.asList(split(property, LIST_SEPARATOR_CHARS));
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
