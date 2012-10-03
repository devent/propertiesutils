/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of propertiesutils-swingproperties-groovy.
 *
 * propertiesutils-swingproperties-groovy is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * propertiesutils-swingproperties-groovy is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with propertiesutils-swingproperties-groovy. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.propertiesutils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Provides the Groovy Evaluating properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class GroovyEvaluatingModule extends AbstractModule {

	private static final URL resource = GroovyEvaluatingModule.class
			.getResource("/groovy_evaluating.properties");

	@Override
	protected void configure() {
	}

	@Provides
	@Singleton
	@Named("groovy-evaluating-properties")
	Properties getProperties() throws IOException {
		return new ContextPropertiesFactory(GroovyEvaluating.class)
				.withProperties(System.getProperties()).fromResource(resource);
	}
}
