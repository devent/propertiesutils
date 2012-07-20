package com.anrisoftware.propertiesutils;

import static com.google.common.io.Resources.getResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
		Properties properties = new Properties();
		Reader reader = new InputStreamReader(resource.openStream(), charset);
		properties.load(reader);
		return new ContextProperties(context, properties);
	}
}
