package com.anrisoftware.propertiesutils;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the swing properties factory.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class SwingPropertiesModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(SwingProperties.class,
				SwingProperties.class).build(SwingPropertiesFactory.class));
	}

}
