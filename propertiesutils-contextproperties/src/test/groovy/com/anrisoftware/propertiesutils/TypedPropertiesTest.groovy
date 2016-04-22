/*
 * Copyright 2012-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import org.junit.Test

/**
 * @see TypedProperties
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@CompileStatic
@Slf4j
class TypedPropertiesTest {

    @Test
    void "get properties"() {
        def testCases = [
            [
                input: 'string_foo = some string foo',
                expected: { TypedProperties properties ->
                    assert properties.getProperty('string_foo') == 'some string foo'
                },
            ],
            [
                input: 'int_foo = 12',
                expected: { TypedProperties properties ->
                    assert properties.getNumberProperty('int_foo').intValue() == 12
                },
            ],
            [
                input: 'double_foo = 12.0',
                expected: { TypedProperties properties ->
                    assert properties.getNumberProperty('int_foo').doubleValue() == 12.0
                },
            ],
        ]
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}. case: {}', k, test
            def parentProperties = new Properties()
            parentProperties.load new StringReader(test.input as String)
            def properties = new TypedProperties(parentProperties)
        }
    }
}
