/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.propertiesutils

import org.junit.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.common.io.Resources

/**
 * Test the context properties factory.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.2
 */
class ContextPropertiesFactoryTest extends TestUtils {

	static URL RESOURCE_URL = Resources.getResource("test.properties")

	@Test
	void "object context from URL resource"() {
		def properties = new ContextPropertiesFactory(this).
						fromResource(RESOURCE_URL)
		assertStringContent properties.getProperty("testString"), "Foo"
	}

	@Test
	void "object context from URL resource with defaults"() {
		def defaults = new Properties()
		defaults.put("com.anrisoftware.propertiesutils.default_key", "Bar")

		def properties = new ContextPropertiesFactory(this).
						withDefaultProperties(defaults).
						fromResource(RESOURCE_URL)
		assertStringContent properties.getProperty("testString"), "Foo"
		assertStringContent properties.getProperty("default_key"), "Bar"
	}

	@Test
	void "object context from URL resource with overrides"() {
		def defaults = new Properties()
		defaults.put("com.anrisoftware.propertiesutils.testString", "Bar")

		def properties = new ContextPropertiesFactory(this).
						withProperties(defaults).
						fromResource(RESOURCE_URL)
		assertStringContent properties.getProperty("testString"), "Bar"
	}

	@Test
	void "object context from URL resource with replacements"() {
		def properties = new ContextPropertiesFactory(this).
						fromResource(RESOURCE_URL).
						withReplacement("foo", "aaa")
		assertStringContent properties.getProperty("testWithReplacements"), "Foo aaa"
	}

	@Test
	void "object context from URL resource with replacements system properties"() {
		def os = System.getProperty("os.name")
		def properties = new ContextPropertiesFactory(this).
						fromResource(RESOURCE_URL).
						withReplacements(System.getProperties())
		assertStringContent properties.getProperty("testWithReplacementsSystem"),
						"Foo $os"
	}
}
