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

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.Test

import com.thoughtworks.xstream.XStream

import groovy.transform.CompileStatic

/**
 * @see ByteContextProperties
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.14
 */
@CompileStatic
class ByteContextPropertiesTest {

    static URL RESOURCE_URL = ByteContextPropertiesTest.class.getResource("/byte_data.properties")

    @Test
    void "XStream data"() {
        byte[] data = "abc".getBytes()
        String xml = new XStream().toXML data
        assert xml == "<byte-array>YWJj</byte-array>"
    }

    @Test
    void "data property"() {
        def p = new ContextPropertiesFactory(ByteContextPropertiesTest).fromResource(RESOURCE_URL)
        def byteProperties = new ByteContextProperties(ByteContextPropertiesTest, p)
        def data = byteProperties.getDataProperty "data"
        assert new String(data) == "abc"
    }
}
