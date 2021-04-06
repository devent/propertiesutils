/**
 * Copyright © 2012 Erwin Müller (erwin.mueller@anrisoftware.com)
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
 * public class FooPropertiesProvider
 *         extends AbstractContextPropertiesProvider {
 *
 *     private static final URL RESOURCE = FooPropertiesProvider.class
 *             .getResource(&quot;foo.properties&quot;);
 *
 *     FooPropertiesProvider() {
 *         super(Foo.class, RESOURCE);
 *     }
 * }
 *
 * public class Foo {
 *
 *     &#064;Inject
 *     Foo(FooPropertiesProvider p) {
 *         this.property = p.get().getProperty(&quot;foo&quot;);
 *     }
 * }
 * </pre>
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class AbstractContextPropertiesProvider
        implements Provider<ContextProperties>, Serializable {

    private static final String LOAD_MESSAGE = "Could not load application properties from '%s'.";

    private final String context;

    private final URL resource;

    private ContextProperties properties;

    private Properties defaultProperties;

    /**
     * Sets the properties context and the properties resource.
     *
     * @param context
     *            the properties context {@link String}.
     *
     * @param res
     *            the properties resource {@link URL}.
     */
    protected AbstractContextPropertiesProvider(String context, URL res) {
        this.context = context;
        this.resource = res;
    }

    /**
     * Sets the properties context and the properties resource.
     *
     * @param context
     *            the properties context {@link Class}.
     *
     * @param res
     *            the properties resource {@link URL}.
     */
    protected AbstractContextPropertiesProvider(Class<?> context, URL res) {
        this(context.getPackage().getName(), res);
    }

    /**
     * Sets the properties context and the properties resource.
     *
     * @param context
     *            the properties context {@link Object}.
     *
     * @param res
     *            the properties resource {@link URL}.
     */
    protected AbstractContextPropertiesProvider(Object context, URL res) {
        this(context.getClass(), res);
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
