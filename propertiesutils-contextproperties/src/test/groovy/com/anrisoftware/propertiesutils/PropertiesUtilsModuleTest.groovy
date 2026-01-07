/*
 * Copyright 2012-2026 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import org.junit.jupiter.api.Test

import com.google.inject.Guice

import groovy.util.logging.Slf4j

/**
 * @see MapEntryToString
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 4.5.2
 */
@Slf4j
class PropertiesUtilsModuleTest extends AbstractContextPropertiesTest {

    @Test
    void "create TypedAllPropertiesFactory"() {
        def instance = Guice.createInjector(new PropertiesUtilsModule()).getInstance(TypedAllPropertiesFactory.class)
        assertNotNull instance
    }
}
