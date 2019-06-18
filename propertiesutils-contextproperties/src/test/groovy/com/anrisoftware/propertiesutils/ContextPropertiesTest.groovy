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

import java.text.Format
import java.text.NumberFormat
import java.util.stream.Stream

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import groovy.util.logging.Slf4j

/**
 * @see ContextProperties
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@Slf4j
class ContextPropertiesTest extends AbstractContextPropertiesTest {

    static def getPropertyProvider() {
        Stream.of of('test.foo = some string foo', 'some string foo')
    }

    @ParameterizedTest
    @MethodSource("getPropertyProvider")
    void getPropertyTest(String input, def expected) {
        def properties = new ContextProperties('test', createParentProperties(input))
        assert properties.getProperty('foo') == expected
    }

    static def getNumberPropertyTest_intProvider() {
        Stream.of of('test.foo = 12', 12)
    }

    @ParameterizedTest
    @MethodSource("getNumberPropertyTest_intProvider")
    void getNumberPropertyTest_int(String input, def expected) {
        def properties = new ContextProperties('test', createParentProperties(input))
        assert properties.getNumberProperty('foo').intValue() == expected
    }
    
    static def getNumberPropertyTest_doubleProvider() {
        Stream.of of('test.foo = 12.0', 12.0)
    }

    @ParameterizedTest
    @MethodSource("getNumberPropertyTest_doubleProvider")
    void getNumberPropertyTest_double(String input, def expected) {
        def properties = new ContextProperties('test', createParentProperties(input))
        assert properties.getNumberProperty('foo').doubleValue() == expected
    }

    static def getTypedListPropertyData_key_format_separatorChars() {
        Stream.of of('test.foo = 12,13,14', "foo", NumberFormat.getNumberInstance(), ",", [12l, 13l, 14l])
    }

    @ParameterizedTest
    @MethodSource("getTypedListPropertyData_key_format_separatorChars")
    void getTypedListPropertyTest_key_format_separatorChars(String input, String key, Format format, String separatorChars, def expected) {
        def properties = new ContextProperties('test', createParentProperties(input))
        assertIterableEquals expected, properties.getTypedListProperty(key, format, separatorChars)
    }

    static def getTypedListPropertyData_key_format_defaultValue_separatorChars() {
        Stream.of of('', "foo", NumberFormat.getNumberInstance(), [12l, 13l, 14l], ",", [12l, 13l, 14l]),
        of('test.foo = 12,13,14', "foo", NumberFormat.getNumberInstance(), [], ",", [12l, 13l, 14l])
    }

    @ParameterizedTest
    @MethodSource("getTypedListPropertyData_key_format_defaultValue_separatorChars")
    void getTypedListPropertyTest_key_format_defaultValue_separatorChars(String input, String key, Format format, List defaultValue, String separatorChars, def expected) {
        def properties = new ContextProperties('test', createParentProperties(input))
        assertIterableEquals expected, properties.getTypedListProperty(key, format, defaultValue, separatorChars)
    }

    static final STRING_TO_INT = [stringToType: { String v -> Integer.valueOf(v) }] as StringToType
    
    static def getTypedListPropertyData_key_stringToType_separatorChars() {
        Stream.of of('test.foo = 12,13,14', "foo", STRING_TO_INT, ",", [12, 13, 14])
    }

    @ParameterizedTest
    @MethodSource("getTypedListPropertyData_key_stringToType_separatorChars")
    void getTypedListPropertyTest_key_stringToType_separatorChars(String input, String key, StringToType stringToType, String separatorChars, def expected) {
        def properties = new ContextProperties('test', createParentProperties(input))
        assertIterableEquals expected, properties.getTypedListProperty(key, stringToType, separatorChars)
    }

    static def getTypedListPropertyData_key_stringToType_defaultValue_separatorChars() {
        Stream.of of('', "foo", STRING_TO_INT, [12, 13, 14], ",", [12, 13, 14]),
        of('test.foo = 12,13,14', "foo", STRING_TO_INT, [], ",", [12, 13, 14])
    }

    @ParameterizedTest
    @MethodSource("getTypedListPropertyData_key_stringToType_defaultValue_separatorChars")
    void getTypedListPropertyTest_key_stringToType_separatorChars(String input, String key, StringToType stringToType, List defaultValue, String separatorChars, def expected) {
        def properties = new ContextProperties('test', createParentProperties(input))
        assertIterableEquals expected, properties.getTypedListProperty(key, stringToType, defaultValue, separatorChars)
    }

    @Test
    void "equals on context"() {
        def propertiesA = new ContextProperties('test', createParentProperties(''))
        def propertiesB = new ContextProperties('test', createParentProperties(''))
        assertTrue propertiesA.equals(propertiesB)
    }

    static def getHashCodeData() {
        Stream.of of('', "test", "foo", 3579771),
        of('test.foo = 12,13,14', "test", "foo", 3579771),
        of('foo.bar = 12,13,14', "foo", "bar", 124847)
    }

    @ParameterizedTest
    @MethodSource("getHashCodeData")
    void "hashcode test"(String input, String context, String key, def expected) {
        def properties = new ContextProperties(context, createParentProperties(input))
        assertEquals expected, properties.hashCode()
    }

}
