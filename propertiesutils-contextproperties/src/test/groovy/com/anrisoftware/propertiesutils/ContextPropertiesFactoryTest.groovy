/*
 * Copyright 2012-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.propertiesutils

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.Test

/**
 * Test the context properties factory.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.2
 */
class ContextPropertiesFactoryTest {

    static URL RESOURCE_URL = ContextPropertiesFactoryTest.class.getResource("/test.properties")

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
    void "object context from URL resource with replacements with backslash"() {
        def properties = new ContextPropertiesFactory(this).
                fromResource(RESOURCE_URL).
                withReplacement("foo", "C:\\Users\\user\\Documents\\foo")
        def result = properties.getProperty("testWithReplacements")
        assertStringContent result, "Foo C:\\Users\\user\\Documents\\foo"
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

    /**
     * @see https://project.anrisoftware.com/issues/4376
     */
    @Test
    void "object context from URL resource with replacements system properties for Windows Backslash"() {
        def dir = "C:\\Users\\erwin\\AppData\\Local\\Temp\\"
        def p = new Properties()
        p.setProperty("java.io.tmpdir", dir)
        def properties = new ContextPropertiesFactory(this).
                fromResource(RESOURCE_URL).
                withReplacements(p)
        assertStringContent properties.getProperty("testWithReplacementsWindows"),
                "Foo $dir"
    }
}
