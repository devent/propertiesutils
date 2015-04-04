/*
 * Copyright 2012-2015 Erwin Müller <erwin.mueller@deventm.org>
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

import static java.lang.System.getProperties;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Factory to create a new context properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ContextPropertiesFactory implements Serializable {

	private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

	private final String context;

	private Properties defaultProperties;

	private Properties parentProperties;

	/**
	 * Sets the specified context object.
	 * 
	 * @param context
	 *            the context {@link Object}.
	 */
	public ContextPropertiesFactory(Object context) {
		this(context.getClass());
	}

	/**
	 * Sets the specified context class.
	 * 
	 * @param context
	 *            the context {@link Class}.
	 */
	public ContextPropertiesFactory(Class<?> context) {
		this(context.getPackage().getName());
	}

	/**
	 * Sets the specified context.
	 * 
	 * @param context
	 *            the context.
	 */
	public ContextPropertiesFactory(String context) {
		this.context = context;
		this.defaultProperties = new Properties();
		this.parentProperties = new Properties();
	}

	/**
	 * Sets the default properties for the context. The default properties are
	 * used if the context does not contain a specified key.
	 * <p>
	 * The method is to use in a fluent API style:
	 * 
	 * <pre>
	 * ContextProperties p = new ContextPropertiesFactory(context)
	 * 		.withDefaultProperties(properties).fromResource(resource);
	 * </pre>
	 * 
	 * @param properties
	 *            the default {@link Properties}.
	 * 
	 * @return this {@link ContextPropertiesFactory}.
	 * 
	 * @since 1.2
	 */
	public ContextPropertiesFactory withDefaultProperties(Properties properties) {
		notNull(properties, "The default properties cannot be null.");
		defaultProperties = properties;
		return this;
	}

	/**
	 * Sets the default properties for the context. The default properties are
	 * used if the context does not contain a specified key.
	 * <p>
	 * The method is to use in a fluent API style:
	 * 
	 * <pre>
	 * ContextProperties p = new ContextPropertiesFactory(context)
	 * 		.withDefaultProperties(propertiesUrl).fromResource(resource);
	 * </pre>
	 * 
	 * @param resource
	 *            the resource {@link URL} of the default properties.
	 * 
	 * @return this {@link ContextPropertiesFactory}.
	 * 
	 * @throws IOException
	 *             if there was an I/O exception while loading the properties
	 *             from the resource URL.
	 * 
	 * @since 1.4
	 */
	public ContextPropertiesFactory withDefaultProperties(URL resource)
			throws IOException {
		defaultProperties = new Properties();
		defaultProperties.load(resource.openStream());
		return this;
	}

	/**
	 * Sets the properties for the context. The properties can override loaded
	 * properties keys.
	 * <p>
	 * The method is to use in a fluent API style:
	 * 
	 * <pre>
	 * // loads the properties from the resource but lets the user overwrite
	 * // the properties with the system properties:
	 * ContextProperties p = new ContextPropertiesFactory(context).withProperties(
	 * 		System.getProperties()).fromResource(resource);
	 * </pre>
	 * 
	 * @param properties
	 *            the default {@link Properties}.
	 * 
	 * @return this {@link ContextPropertiesFactory}.
	 * 
	 * @since 1.2
	 */
	public ContextPropertiesFactory withProperties(Properties properties) {
		notNull(properties, "The parent properties cannot be null.");
		parentProperties = properties;
		return this;
	}

	/**
	 * Loads the properties from a resource with a default character set.
	 * 
	 * @param resource
	 *            the resource {@link URL}.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 */
	public ContextProperties fromResource(URL resource) throws IOException {
		return fromResource(resource, DEFAULT_CHARSET);
	}

	/**
	 * Loads the properties from a resource with a specified character set.
	 * 
	 * @param url
	 *            the resource {@link URL}.
	 * 
	 * @param charset
	 *            the {@link Charset} of the resource.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 */
	public ContextProperties fromResource(URL url, Charset charset)
			throws IOException {
		InputStream resource = new BufferedInputStream(url.openStream());
		Properties properties = loadProperties(resource, charset);
		return new ContextProperties(context, properties);
	}

	/**
	 * Loads the properties from a resource with a default character set.
	 * 
	 * @param resource
	 *            the resource {@link URI}.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 */
	public ContextProperties fromResource(URI resource) throws IOException {
		return fromResource(resource, DEFAULT_CHARSET);
	}

	/**
	 * Loads the properties from a resource with a specified character set.
	 * 
	 * @param resource
	 *            the resource {@link URI}.
	 * 
	 * @param charset
	 *            the {@link Charset} of the resource.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 * 
	 * @since 1.1
	 */
	public ContextProperties fromResource(URI resource, Charset charset)
			throws IOException {
		return fromResource(new File(resource), charset);
	}

	/**
	 * Loads the properties from a resource with a default character set.
	 * 
	 * @param resource
	 *            the resource {@link File}.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 */
	public ContextProperties fromResource(File resource) throws IOException {
		return fromResource(resource, DEFAULT_CHARSET);
	}

	/**
	 * Loads the properties from a resource with a specified character set.
	 * 
	 * @param file
	 *            the resource {@link File}.
	 * 
	 * @param charset
	 *            the {@link Charset} of the resource.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 * 
	 * @since 1.1
	 */
	public ContextProperties fromResource(File file, Charset charset)
			throws IOException {
		FileInputStream resource = new FileInputStream(file);
		Properties properties = loadProperties(resource, charset);
		return new ContextProperties(context, properties);
	}

	/**
	 * Loads the properties from user specified resource with the default
	 * character set.
	 * <p>
	 * The user can specify a location for the user specific properties in the
	 * system properties:
	 * <ul>
	 * <li>{@code <context>.<fileKey>}</li>
	 * <li>{@code <context>.<urlKey>}</li>
	 * </ul>
	 * If the file key property or the URL key property are set then the
	 * properties are loaded from the specified location. If the user have not
	 * set any of the properties then the properties are loaded from the
	 * specified resource URL.
	 * 
	 * 
	 * @param resource
	 *            the resource {@link URL} if the user is not specifying a
	 *            custom properties resource.
	 * 
	 * @param fileKey
	 *            the key for the user specific properties file resource.
	 * 
	 * @param urlKey
	 *            the key for the user specific properties URL resource.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 * 
	 * @since 1.4
	 */
	public ContextProperties fromUserResources(URL resource, String fileKey,
			String urlKey) throws IOException {
		URL userResource = getUserResourceURL(fileKey, urlKey);
		return fromResource(userResource == null ? resource : userResource);
	}

	private URL getUserResourceURL(String fileKey, String urlKey)
			throws MalformedURLException {
		ContextProperties properties;
		properties = new ContextProperties(context, getProperties());
		File file = properties.getFileProperty(fileKey, null);
		URL url = properties.getURLProperty(urlKey, null);
		if (file != null) {
			url = file.toURI().toURL();
		}
		return url;
	}

	private Properties loadProperties(InputStream resource, Charset charset)
			throws FileNotFoundException, IOException {
		Properties resourceP = new Properties(defaultProperties);
		Reader reader = new InputStreamReader(resource, charset);
		resourceP.load(reader);
		Properties parentP = new Properties(resourceP);
		parentP.putAll(parentProperties);
		return parentP;
	}

	/**
	 * Returning the context properties without loading any other resources.
	 * <p>
	 * Useful if we already loaded default properties from shared resource.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @since 1.5
	 */
	public ContextProperties fromDefaults() {
		Properties resourceP = new Properties(defaultProperties);
		Properties parentP = new Properties(resourceP);
		parentP.putAll(parentProperties);
		return new ContextProperties(context, parentP);
	}

}
