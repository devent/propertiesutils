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

import static org.junit.jupiter.params.provider.Arguments.of

import java.util.stream.Stream

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

}
