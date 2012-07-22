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

import static com.google.common.io.Resources.getResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
public class ContextPropertiesFactory {

	private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

	private final String context;

	private final Properties defaultProperties;

	/**
	 * Sets the specified context object.
	 * 
	 * @param context
	 *            the context {@link Object}.
	 */
	public ContextPropertiesFactory(Object context) {
		this(context.getClass(), null);
	}

	/**
	 * Sets the specified context object.
	 * 
	 * @param context
	 *            the context {@link Object}.
	 * 
	 * @param defaultProperties
	 *            the default {@link Properties}.
	 * 
	 * @since 1.1
	 */
	public ContextPropertiesFactory(Object context, Properties defaultProperties) {
		this(context.getClass(), defaultProperties);
	}

	/**
	 * Sets the specified context class.
	 * 
	 * @param context
	 *            the context {@link Class}.
	 */
	public ContextPropertiesFactory(Class<?> context) {
		this(context.getPackage().getName(), null);
	}

	/**
	 * Sets the specified context class.
	 * 
	 * @param context
	 *            the context {@link Class}.
	 * 
	 * @param defaultProperties
	 *            the default {@link Properties}.
	 * 
	 * @since 1.1
	 */
	public ContextPropertiesFactory(Class<?> context,
			Properties defaultProperties) {
		this(context.getPackage().getName(), defaultProperties);
	}

	/**
	 * Sets the specified context.
	 * 
	 * @param context
	 *            the context.
	 */
	public ContextPropertiesFactory(String context) {
		this(context, null);
	}

	/**
	 * Sets the specified context.
	 * 
	 * @param context
	 *            the context.
	 * 
	 * @param defaultProperties
	 *            the default {@link Properties}.
	 * 
	 * @since 1.1
	 */
	public ContextPropertiesFactory(String context, Properties defaultProperties) {
		this.context = context;
		this.defaultProperties = defaultProperties;
	}

	/**
	 * Returns the system properties.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @since 1.1
	 */
	public ContextProperties getSystemProperties() {
		return new ContextProperties(context, System.getProperties());
	}

	/**
	 * Loads the properties from a resource with a default character set.
	 * 
	 * @param resourceName
	 *            the resource name.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 */
	public ContextProperties fromResource(String resourceName)
			throws IOException {
		return fromResource(null, resourceName, DEFAULT_CHARSET);
	}

	/**
	 * Loads the properties from a resource with a specified character set.
	 * 
	 * @param resourceContext
	 *            the context {@link Class} for the resource.
	 * 
	 * @param resourceName
	 *            the resource name.
	 * 
	 * @param charset
	 *            the {@link Charset} of the resource.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 */
	public ContextProperties fromResource(Class<?> resourceContext,
			String resourceName) throws IOException {
		return fromResource(resourceContext, resourceName, DEFAULT_CHARSET);
	}

	/**
	 * Loads the properties from a resource with a specified character set.
	 * 
	 * @param resourceContext
	 *            the context {@link Class} for the resource.
	 * 
	 * @param resourceName
	 *            the resource name.
	 * 
	 * @param charset
	 *            the {@link Charset} of the resource.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws IOException
	 *             if there was an error loading the resource.
	 */
	public ContextProperties fromResource(Class<?> resourceContext,
			String resourceName, Charset charset) throws IOException {
		URL url;
		if (resourceContext != null) {
			url = getResource(resourceContext, resourceName);
		} else {
			url = getResource(resourceName);
		}
		return fromResource(url, charset);
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
	 * @param resource
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
	public ContextProperties fromResource(URL resource, Charset charset)
			throws IOException {
		Properties properties = new Properties(defaultProperties);
		Reader reader = new InputStreamReader(resource.openStream(), charset);
		properties.load(reader);
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
	 * @param resource
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
	public ContextProperties fromResource(File resource, Charset charset)
			throws IOException {
		Properties properties = new Properties(defaultProperties);
		Reader reader = new InputStreamReader(new FileInputStream(resource),
				charset);
		properties.load(reader);
		return new ContextProperties(context, properties);
	}
}
