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

import static org.junit.jupiter.api.Assertions.*
import static org.junit.jupiter.params.provider.Arguments.of

import java.util.stream.Stream

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import groovy.util.logging.Slf4j

/**
 * @see MapEntryToString
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 4.5.2
 */
@Slf4j
class MapEntryToStringTest extends AbstractContextPropertiesTest {

    static mapEntryToString = new MapEntryToString()

    static Stream getArgsWithNull() {
        Stream.of of([test: 'test'], 'test', 'test'),
        of([test: null], 'test', null),
        of([test: 12], 'test', '12')
    }

    @ParameterizedTest
    @MethodSource("getArgsWithNull")
    void "toStringNullTest"(def args, def key, def expected) {
        def output = mapEntryToString.toStringNull(args, key)
        assert output == expected
    }

    static Stream getArgs() {
        Stream.of of([test: 'test'], 'test', 'test'),
        of([test: 12], 'test', '12')
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    void "toStringTest"(def args, def key, def expected) {
        def output = mapEntryToString.toString(args, key)
        assert output == expected
    }

    @Test
    void "toStringTest with null"() {
        assertThrows NullPointerException, {
            mapEntryToString.toString([test: null], 'test')
        }
    }
}
