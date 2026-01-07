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

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.Format;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
@SuppressWarnings("serial")
public class ContextProperties extends Properties {

    private static final String REPLACEMENT_PATTERN = "\\$\\{%s\\}";

    private final String context;

    private final HashMap<String, Serializable> replacements;

    private final TypedProperties typedProperties;

    /**
     * Sets the context and the properties.
     *
     * @param context          an {@link Object} that is used as the context.
     *
     * @param parentProperties the {@link Properties} that are returned.
     */
    public ContextProperties(Object context, Properties parentProperties) {
        this(context.getClass(), parentProperties);
    }

    /**
     * Sets the context and the properties.
     *
     * @param context          an {@link Class} that is used as the context.
     *
     * @param parentProperties the {@link Properties} that are returned.
     */
    public ContextProperties(Class<?> context, Properties parentProperties) {
        this(context.getPackage().getName(), parentProperties);
    }

    /**
     * Sets the context and the properties.
     *
     * @param context          the context.
     *
     * @param parentProperties the {@link Properties} that are returned.
     */
    public ContextProperties(String context, Properties parentProperties) {
        super(parentProperties);
        this.typedProperties = new TypedProperties(this);
        this.context = context;
        this.replacements = new HashMap<>();
    }

    /**
     * Adds the replacements from the specified map.
     * <p>
     * Each of the replacement entries is applied to the property. The replacement
     * is of format <code>{$key}</code>.
     * <p>
     * It is possible to add the system properties directly as in
     * {@code p.withReplacements(System.getProperties())}.
     *
     * @param map the {@link Map} that contains the replacements as
     *            {@code <key>: <value>}.
     *
     * @return this {@link ContextProperties}.
     *
     * @throws NullPointerException if the specified map is {@code null}.
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
     * Each of the replacement entries is applied to the property. The replacement
     * is of format <code>${key}</code>.
     *
     * @param key     the replacement key.
     *
     * @param replace the {@link Serializable} object as the replacement.
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
     * Adds the system properties as replacements.
     *
     * @return this {@link ContextProperties}.
     *
     * @since 2.1
     */
    public ContextProperties withSystemReplacements() {
        Enumeration<?> names = System.getProperties().propertyNames();
        while (names.hasMoreElements()) {
            String key = (String) names.nextElement();
            replacements.put(key, System.getProperty(key));
        }
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
            String replace = Matcher.quoteReplacement(entry.getValue().toString());
            String key = entry.getKey();
            Pattern pattern = Pattern.compile(format(REPLACEMENT_PATTERN, key));
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                value = matcher.replaceAll(replace);
            }
        }
        return value;
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

    public Boolean getBooleanProperty(String key) {
        return typedProperties.getBooleanProperty(key);
    }

    public Boolean getBooleanProperty(String key, Boolean defaultValue) {
        return typedProperties.getBooleanProperty(key, defaultValue);
    }

    public Number getNumberProperty(String key) {
        return typedProperties.getNumberProperty(key);
    }

    public Number getNumberProperty(String key, Number defaultValue) {
        return typedProperties.getNumberProperty(key, defaultValue);
    }

    public Character getCharProperty(String key) {
        return typedProperties.getCharProperty(key);
    }

    public Character getCharProperty(String key, Character defaultValue) {
        return typedProperties.getCharProperty(key, defaultValue);
    }

    public Charset getCharsetProperty(String key) {
        return typedProperties.getCharsetProperty(key);
    }

    public Charset getCharsetProperty(String key, Charset defaultValue) {
        return typedProperties.getCharsetProperty(key, defaultValue);
    }

    public URL getURLProperty(String key) throws MalformedURLException {
        return typedProperties.getURLProperty(key);
    }

    public URL getURLProperty(String key, URL defaultValue) throws MalformedURLException {
        return typedProperties.getURLProperty(key, defaultValue);
    }

    public URI getURIProperty(String key) throws URISyntaxException {
        return typedProperties.getURIProperty(key);
    }

    public URI getURIProperty(String key, URI defaultValue) throws URISyntaxException {
        return typedProperties.getURIProperty(key, defaultValue);
    }

    public File getFileProperty(String key) {
        return typedProperties.getFileProperty(key);
    }

    public File getFileProperty(String key, File defaultValue) {
        return typedProperties.getFileProperty(key, defaultValue);
    }

    public <T> T getTypedProperty(String key, Format format) throws ParseException {
        return typedProperties.getTypedProperty(key, format);
    }

    public <T> T getTypedProperty(String key, Format format, T defaultValue) throws ParseException {
        return typedProperties.getTypedProperty(key, format, defaultValue);
    }

    public <T> List<T> getTypedListProperty(String key, Format format) throws ParseException {
        return typedProperties.getTypedListProperty(key, format);
    }

    public <T> List<T> getTypedListProperty(String key, Format format, String separatorChars) throws ParseException {
        return typedProperties.getTypedListProperty(key, format, separatorChars);
    }

    public <T> List<T> getTypedListProperty(String key, Format format, List<T> defaultValue) throws ParseException {
        return typedProperties.getTypedListProperty(key, format, defaultValue);
    }

    public <T> List<T> getTypedListProperty(String key, Format format, List<T> defaultValue, String separatorChars)
            throws ParseException {
        return typedProperties.getTypedListProperty(key, format, defaultValue, separatorChars);
    }

    public <T> List<T> getTypedListProperty(String key, StringToType<T> stringToType) throws ParseException {
        return typedProperties.getTypedListProperty(key, stringToType);
    }

    public <T> List<T> getTypedListProperty(String key, StringToType<T> stringToType, String separatorChars)
            throws ParseException {
        return typedProperties.getTypedListProperty(key, stringToType, separatorChars);
    }

    public <T> List<T> getTypedListProperty(String key, StringToType<T> stringToType, List<T> defaultValue,
            String separatorChars) throws ParseException {
        return typedProperties.getTypedListProperty(key, stringToType, defaultValue, separatorChars);
    }

    public List<String> getListProperty(String key) {
        return typedProperties.getListProperty(key);
    }

    public List<String> getListProperty(String key, String separatorChars) {
        return typedProperties.getListProperty(key, separatorChars);
    }

    public List<String> getListProperty(String key, List<String> defaultValue) {
        return typedProperties.getListProperty(key, defaultValue);
    }

    public List<String> getListProperty(String key, List<String> defaultValue, String separatorChars) {
        return typedProperties.getListProperty(key, defaultValue, separatorChars);
    }

    @Override
    public synchronized boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ContextProperties rhs = (ContextProperties) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(context, rhs.getContext()).isEquals();
    }

    @Override
    public synchronized int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(context).toHashCode();
    }

    @Override
    public synchronized String toString() {
        return new ToStringBuilder(this).append("context", context).appendSuper(super.toString()).toString();
    }
}
