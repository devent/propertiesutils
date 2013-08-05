/*
 * Copyright 2012-2013 Erwin Müller <erwin.mueller@deventm.org>
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

import static java.lang.String.format;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Properties;

import com.google.inject.Provider;
import com.google.inject.ProvisionException;

/**
 * Provides the context properties.
 * <p>
 * The properties are loaded from the specified properties file. This is indened
 * to inject the properties in a class:
 * 
 * <pre>
 * &#064;Singleton
 * public class FooPropertiesProvider extends AbstractContextPropertiesProvider {
 * 
 * 	private static final URL RESOURCE = FooPropertiesProvider.class
 * 			.getResource(&quot;foo.properties&quot;);
 * 
 * 	FooPropertiesProvider() {
 * 		super(Foo.class, RESOURCE);
 * 	}
 * }
 * 
 * public class Foo {
 * 
 * 	&#064;Inject
 * 	Foo(FooPropertiesProvider p) {
 * 		this.property = p.get().getProperty(&quot;foo&quot;);
 * 	}
 * }
 * </pre>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class AbstractContextPropertiesProvider implements
		Provider<ContextProperties>, Serializable {

	private static final String LOAD_MESSAGE = "Could not load application properties from '%s'.";

	private final Class<?> context;

	private final URL resource;

	private ContextProperties properties;

	private Properties defaultProperties;

	/**
	 * Sets the properties context and the properties resource.
	 * 
	 * @param context
	 *            the properties context {@link Class}.
	 * 
	 * @param resource
	 *            the properties resource {@link URL}.
	 */
	protected AbstractContextPropertiesProvider(Class<?> context, URL resource) {
		this.context = context;
		this.resource = resource;
	}

	/**
	 * Sets the default properties.
	 * 
	 * @param p
	 *            the default {@link Properties}.
	 * 
	 * @see ContextPropertiesFactory#withDefaultProperties(Properties)
	 */
	public void setDefaultProperties(Properties p) {
		this.defaultProperties = p;
	}

	/**
	 * @see #getProperties()
	 */
	@Override
	public ContextProperties get() {
		return getProperties();
	}

	/**
	 * Loads and returns the context properties from the resource.
	 * 
	 * @return the {@link ContextProperties}.
	 * 
	 * @throws ProvisionException
	 *             if there was an error loading the properties.
	 */
	public ContextProperties getProperties() {
		if (properties == null) {
			loadProperties();
		}
		return properties;
	}

	private synchronized void loadProperties() {
		try {
			ContextPropertiesFactory p = new ContextPropertiesFactory(context)
					.withProperties(System.getProperties());
			if (defaultProperties != null) {
				p.withDefaultProperties(defaultProperties);
			}
			properties = p.fromResource(resource);
		} catch (IOException e) {
			throw new ProvisionException(format(LOAD_MESSAGE, resource), e);
		}
	}

}
