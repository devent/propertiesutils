/*
 * Copyright 2012-2015 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic

import org.junit.Test

import com.thoughtworks.xstream.XStream

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
