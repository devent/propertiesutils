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
