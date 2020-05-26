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

package com.anrisoftware.propertiesutils;

import java.text.ParseException;

/**
 * Parses the string value to the type.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.14
 */
public interface StringToType<T> {

    /**
     * Parses the string value to the type.
     *
     * @param value
     *            the {@link String} value.
     *
     * @return the parses type.
     *
     * @throws ParseException
     *             if there was an error parsing the value.
     */
    T stringToType(String value) throws ParseException;

}
