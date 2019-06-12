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

package com.anrisoftware.propertiesutils

import static org.junit.jupiter.api.Assertions.*
import static org.junit.jupiter.params.provider.Arguments.of

import java.util.stream.Stream

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import groovy.util.logging.Slf4j

/**
 * @see TypedProperties
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@Slf4j
class TypedPropertiesTest {

    static def getPropertyDataString() {
        Stream.of of('string_foo = some string foo', 'some string foo')
    }

    @ParameterizedTest
    @MethodSource("getPropertyDataString")
    void "getProperty"(String input, String expected) {
        def parentProperties = new Properties()
        parentProperties.load new StringReader(input)
        def properties = new TypedProperties(parentProperties)
        assert properties.getProperty('string_foo') == expected
    }

    static def getPropertyDataInt() {
        Stream.of of('int_foo = 12', 12)
    }

    @ParameterizedTest
    @MethodSource("getPropertyDataInt")
    void "getNumberProperty-int"(String input, int expected) {
        def parentProperties = new Properties()
        parentProperties.load new StringReader(input)
        def properties = new TypedProperties(parentProperties)
        assert properties.getNumberProperty('int_foo').intValue() == expected
    }

    static def getPropertyDataDouble() {
        Stream.of of('double_foo = 12.0', (double)12.0)
    }

    @ParameterizedTest
    @MethodSource("getPropertyDataDouble")
    void "getNumberProperty-double"(String input, double expected) {
        def parentProperties = new Properties()
        parentProperties.load new StringReader(input)
        def properties = new TypedProperties(parentProperties)
        assert properties.getNumberProperty('double_foo').doubleValue() == expected
    }

    static def getListPropertyData() {
        Stream.of of('list_foo = foo,bar,baz', ['foo', 'bar', 'baz'], ','),
        of('list_foo = foo;bar;baz', ['foo', 'bar', 'baz'], ';')
    }

    @ParameterizedTest
    @MethodSource("getListPropertyData")
    void "getListProperty-separatorChars"(String input, List expected, def separatorChars) {
        def parentProperties = new Properties()
        parentProperties.load new StringReader(input)
        def properties = new TypedProperties(parentProperties)
        def output = properties.getListProperty('list_foo')
        assertIterableEquals expected, output
    }
}
