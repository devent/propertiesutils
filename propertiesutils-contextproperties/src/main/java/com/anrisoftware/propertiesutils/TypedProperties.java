/*
 * Copyright 2012-2026 Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.propertiesutils;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.split;

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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Utility to return typed properties.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@SuppressWarnings("serial")
public class TypedProperties implements Serializable {

    private static final String DEFAULT_LIST_SEPARATOR_CHARS = " ,;";

    private final Properties properties;

    private final String listSepChars;

    public TypedProperties(Map<String, Object> properties) {
        this(properties, DEFAULT_LIST_SEPARATOR_CHARS);
    }

    public TypedProperties(Map<String, Object> properties, String listSepChars) {
        this(new Properties(), listSepChars);
        for (Entry<String, Object> entry : properties.entrySet()) {
            properties.put(entry.toString(), entry.toString());
        }
    }

    public TypedProperties(Properties properties) {
        this(properties, DEFAULT_LIST_SEPARATOR_CHARS);
    }

    public TypedProperties(Properties properties, String listSepChars) {
        this.properties = properties;
        this.listSepChars = listSepChars;
    }

    /**
     * Returns a {@link String} property.
     *
     * @param key the property key.
     *
     * @return the {@link String} from the property or {@code null} if no property
     *         with the key was found.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Returns a {@link String} property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link String}.
     *
     * @return the {@link String} from the property or the default value if no
     *         property with the key was found.
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Returns a boolean property.
     *
     * @param key the property key.
     *
     * @return the {@link Boolean} from the property or {@code null} if no property
     *         with the key was found.
     */
    public Boolean getBooleanProperty(String key) {
        String property = getProperty(key);
        return property == null ? null : parseBoolean(property);
    }

    /**
     * Returns a boolean property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link Boolean}.
     *
     * @return the {@link Boolean} from the property or the default value if no
     *         property with the key was found.
     */
    public Boolean getBooleanProperty(String key, Boolean defaultValue) {
        String property = getProperty(key, String.valueOf(defaultValue));
        return parseBoolean(property);
    }

    /**
     * Returns a number property.
     *
     * @param key the property key.
     *
     * @return the {@link Number} from the property or {@code null} if no property
     *         with the key was found.
     */
    public Number getNumberProperty(String key) {
        String property = getProperty(key);
        return property == null ? null : parseDouble(property);
    }

    /**
     * Returns a number property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link Number}.
     *
     * @return the {@link Number} from the property or the default value if no
     *         property with the key was found.
     *
     * @throws NumberFormatException if the string does not contain a parseble
     *                               {@code double}.
     */
    public Number getNumberProperty(String key, Number defaultValue) {
        String property = getProperty(key, String.valueOf(defaultValue));
        return parseDouble(property);
    }

    /**
     * Returns a character property.
     *
     * @param key the property key.
     *
     * @return the {@link Character} from the property or {@code null} if no
     *         property with the key was found.
     *
     * @throws IndexOutOfBoundsException if the property is an empty string.
     */
    public Character getCharProperty(String key) {
        String property = getProperty(key);
        return property == null ? null : property.charAt(0);
    }

    /**
     * Returns a character property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link Character}.
     *
     * @return the {@link Character} from the property or the default value if no
     *         property with the key was found.
     *
     * @throws IndexOutOfBoundsException if the property is an empty string.
     */
    public Character getCharProperty(String key, Character defaultValue) {
        String property = getProperty(key);
        return property == null ? defaultValue : property.charAt(0);
    }

    /**
     * Returns a character set property.
     *
     * @param key the property key.
     *
     * @return the {@link Charset} from the property or {@code null} if no property
     *         with the key was found.
     *
     * @throws UnsupportedCharsetException If no support for the named character set
     *                                     is available in this instance of the Java
     *                                     virtual machine.
     */
    public Charset getCharsetProperty(String key) {
        String property = getProperty(key);
        return property == null ? null : Charset.forName(property);
    }

    /**
     * Returns a character set property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link Charset}.
     *
     * @return the {@link Charset} from the property or the default value if no
     *         property with the key was found.
     *
     * @throws UnsupportedCharsetException If no support for the named character set
     *                                     is available in this instance of the Java
     *                                     virtual machine.
     */
    public Charset getCharsetProperty(String key, Charset defaultValue) {
        String property = getProperty(key, String.valueOf(defaultValue));
        return Charset.forName(property);
    }

    /**
     * Returns a URL property.
     *
     * @param key the property key.
     *
     * @return the {@link URL} from the property or {@code null} if no property with
     *         the key was found.
     *
     * @throws MalformedURLException if the property value is not a valid URL.
     * @throws URISyntaxException    if the property value is not a valid URL.
     */
    public URL getURLProperty(String key) throws MalformedURLException, URISyntaxException {
        String property = getProperty(key);
        return property == null ? null : new URI(property).toURL();
    }

    /**
     * Returns a URL property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link URL}.
     *
     * @return the {@link URL} from the property or the default value if no property
     *         with the key was found.
     *
     * @throws MalformedURLException if the property value is not a valid URL.
     * @throws URISyntaxException    if the property value is not a valid URL.
     */
    public URL getURLProperty(String key, URL defaultValue) throws MalformedURLException, URISyntaxException {
        String property = getProperty(key);
        return property == null ? defaultValue : new URI(property).toURL();
    }

    /**
     * Returns a URI property.
     *
     * @param key the property key.
     *
     * @return the {@link URI} from the property or {@code null} if no property with
     *         the key was found.
     *
     * @throws URISyntaxException if the property value is not a valid URI.
     */
    public URI getURIProperty(String key) throws URISyntaxException {
        String property = getProperty(key);
        return property == null ? null : new URI(property);
    }

    /**
     * Returns a URI property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link URI}.
     *
     * @return the {@link URI} from the property or the default value if no property
     *         with the key was found.
     *
     * @throws URISyntaxException if the property value is not a valid URI.
     */
    public URI getURIProperty(String key, URI defaultValue) throws URISyntaxException {
        String property = getProperty(key, String.valueOf(defaultValue));
        return new URI(property);
    }

    /**
     * Returns a File property.
     *
     * @param key the property key.
     *
     * @return the {@link File} from the property or {@code null} if no property
     *         with the key was found.
     */
    public File getFileProperty(String key) {
        String property = getProperty(key);
        return property == null ? null : new File(property);
    }

    /**
     * Returns a File property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link File}.
     *
     * @return the {@link File} from the property or the default value if no
     *         property with the key was found.
     */
    public File getFileProperty(String key, File defaultValue) {
        String property = getProperty(key);
        return property == null ? defaultValue : new File(property);
    }

    /**
     * Returns a property of the specified type.
     *
     * @param key    the property key.
     *
     * @param format the {@link Format} to parse the type.
     *
     * @param        <T> the type.
     *
     * @return the the property or {@code null} if no property with the key was
     *         found.
     *
     * @throws ParseException if the property cannot be parsed to the type.
     */
    public <T> T getTypedProperty(String key, Format format) throws ParseException {
        return getTypedProperty(key, format, null);
    }

    /**
     * Returns a property of the specified type.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default value.
     *
     * @param format       the {@link Format} to parse the type.
     *
     * @param              <T> the type.
     *
     * @return the the property or the default value if no property with the key was
     *         found.
     *
     * @throws ParseException if the property cannot be parsed to the type.
     */
    @SuppressWarnings("unchecked")
    public <T> T getTypedProperty(String key, Format format, T defaultValue) throws ParseException {
        String property = getProperty(key, String.valueOf(defaultValue));
        return property == null ? null : (T) format.parseObject(property);
    }

    /**
     * Returns a list property.
     *
     * @param key    the property key.
     *
     * @param format the {@link Format} to parse the key values.
     *
     * @param        <T> the type.
     *
     * @return the {@link List} from the property or an empty list if no property
     *         with the key was found.
     *
     * @throws ParseException if there was an error to parse a key value.
     */
    public <T> List<T> getTypedListProperty(String key, Format format) throws ParseException {
        return getTypedListProperty(key, format, listSepChars);
    }

    /**
     * Returns a list property.
     *
     * @param key            the property key.
     *
     * @param format         the {@link Format} to parse the key values.
     *
     * @param separatorChars the characters used as the delimiters, {@code null}
     *                       splits on whitespace.
     *
     * @param                <T> the type.
     *
     * @return the {@link List} from the property or an empty list if no property
     *         with the key was found.
     *
     * @throws ParseException if there was an error to parse a key value.
     */
    public <T> List<T> getTypedListProperty(String key, Format format, String separatorChars) throws ParseException {
        List<T> list = new ArrayList<>();
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
     * Returns a list property.
     *
     * @param key          the property key.
     *
     * @param format       the {@link Format} to parse the key values.
     *
     * @param defaultValue the default {@link List}.
     *
     * @param              <T> the type.
     *
     * @return the {@link List} from the property or the default value if no
     *         property with the key was found.
     *
     * @throws ParseException if there was an error to parse a key value.
     */
    public <T> List<T> getTypedListProperty(String key, Format format, List<T> defaultValue) throws ParseException {
        return getTypedListProperty(key, format, defaultValue, listSepChars);
    }

    /**
     * Returns a list property.
     *
     * @param key            the property key.
     *
     * @param format         the {@link Format} to parse the key values.
     *
     * @param defaultValue   the default {@link List}.
     *
     * @param separatorChars the characters used as the delimiters, {@code null}
     *                       splits on whitespace.
     *
     * @param                <T> the type.
     *
     * @return the {@link List} from the property or the default value if no
     *         property with the key was found.
     *
     * @throws ParseException if there was an error to parse a key value.
     */
    public <T> List<T> getTypedListProperty(String key, Format format, List<T> defaultValue, String separatorChars)
            throws ParseException {
        List<T> list = new ArrayList<>();
        String property = getProperty(key, join(defaultValue, ","));
        for (String value : split(property, listSepChars)) {
            addParsedObject(list, format, value);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private <T> void addParsedObject(List<T> list, Format format, String value) throws ParseException {
        list.add((T) format.parseObject(value));
    }

    /**
     * Returns a list property.
     *
     * @param key          the property key.
     *
     * @param stringToType the {@link StringToType} that parses the string to the
     *                     type.
     *
     * @param              <T> the type.
     *
     * @return the {@link List} from the property or an empty list if no property
     *         with the key was found.
     *
     * @throws ParseException if there was an error to parse a key value.
     */
    public <T> List<T> getTypedListProperty(String key, StringToType<T> stringToType) throws ParseException {
        return getTypedListProperty(key, stringToType, listSepChars);
    }

    /**
     * Returns a typed list property.
     *
     * @param key            the property key.
     *
     * @param stringToType   the {@link StringToType} that parses the string to the
     *                       type.
     *
     * @param separatorChars the characters used as the delimiters, {@code null}
     *                       splits on whitespace.
     *
     * @param                <T> the type.
     *
     * @return the {@link List} from the property or an empty list if no property
     *         with the key was found.
     *
     * @throws ParseException if there was an error to parse a key value.
     */
    public <T> List<T> getTypedListProperty(String key, StringToType<T> stringToType, String separatorChars)
            throws ParseException {
        List<T> list = new ArrayList<>();
        String property = getProperty(key);
        if (property == null) {
            return list;
        }
        for (String value : split(property, separatorChars)) {
            list.add(stringToType.stringToType(value));
        }
        return list;
    }

    /**
     * Returns a typed list property.
     *
     * @param key            the property key.
     *
     * @param stringToType   the {@link StringToType} that parses the string to the
     *                       type.
     *
     * @param defaultValue   the default {@link List}.
     *
     * @param separatorChars the characters used as the delimiters, {@code null}
     *                       splits on whitespace.
     *
     * @param                <T> the type.
     *
     * @return the {@link List} from the property or the default value if no
     *         property with the key was found.
     *
     * @throws ParseException if there was an error to parse a key value.
     */
    public <T> List<T> getTypedListProperty(String key, StringToType<T> stringToType, List<T> defaultValue,
            String separatorChars) throws ParseException {
        List<T> list = new ArrayList<>();
        String property = getProperty(key, join(defaultValue, ","));
        for (String value : split(property, listSepChars)) {
            list.add(stringToType.stringToType(value));
        }
        return list;
    }

    /**
     * Returns a list property.
     *
     * @param key the property key.
     *
     * @return the {@link List} from the property or an empty list if no property
     *         with the key was found.
     */
    public List<String> getListProperty(String key) {
        return getListProperty(key, listSepChars);
    }

    /**
     * Returns a list property.
     *
     * @param key            the property key.
     *
     * @param separatorChars the characters used as the delimiters, {@code null}
     *                       splits on whitespace.
     *
     * @return the {@link List} from the property or an empty list if no property
     *         with the key was found.
     */
    public List<String> getListProperty(String key, String separatorChars) {
        String property = getProperty(key);
        return property == null ? new ArrayList<>() : asList(split(property, separatorChars));
    }

    /**
     * Returns a list property.
     *
     * @param key          the property key.
     *
     * @param defaultValue the default {@link List}.
     *
     * @return the {@link List} from the property or the default value if no
     *         property with the key was found.
     */
    public List<String> getListProperty(String key, List<String> defaultValue) {
        return getListProperty(key, defaultValue, listSepChars);
    }

    /**
     * Returns a list property.
     *
     * @param key            the property key.
     *
     * @param defaultValue   the default {@link List}.
     *
     * @param separatorChars the characters used as the delimiters, {@code null}
     *                       splits on whitespace.
     *
     * @return the {@link List} from the property or the default value if no
     *         property with the key was found.
     */
    public List<String> getListProperty(String key, List<String> defaultValue, String separatorChars) {
        String property = getProperty(key, join(defaultValue, ","));
        return Arrays.asList(split(property, separatorChars));
    }

}
