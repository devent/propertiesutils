/*
 * Copyright 2012-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Map;
import java.util.Objects;

/**
 * Converts a map entry to a {@link String}.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
public class MapEntryToString {

    /**
     * Converts the argument to a {@link String}.
     *
     * @param args the {@link Map} that should contain the key.
     *
     * @param key  the key.
     *
     * @return the {@link String} or {@code null} if the argument doesn't exists.
     */
    public String toStringNull(Map<String, Object> args, String key) {
        Object value = args.get(key);
        if (value == null) {
            return null;
        }
        return toStringNull(value);
    }

    /**
     * Converts the argument to a {@link String}.
     *
     * @param args the {@link Map} that should contain the key.
     *
     * @param key  the key.
     *
     * @throws NullPointerException if the argument is {@code null}.
     *
     * @return the {@link String}.
     */
    public String toString(Map<String, Object> args, String key) {
        Object value = args.get(key);
        return toString(value);
    }

    /**
     * Converts the argument to a {@link String}.
     *
     * @param arg the argument.
     *
     * @return the {@link String} or {@code null} if the argument was {@code null}.
     */
    public String toStringNull(Object arg) {
        if (arg == null) {
            return null;
        }
        return Objects.toString(arg);
    }

    /**
     * Converts the argument to a {@link String}.
     *
     * @param arg the argument.
     *
     * @throws NullPointerException if the argument is {@code null}.
     *
     * @return the {@link String}.
     */
    public String toString(Object arg) {
        notNull(arg, "arg");
        return Objects.toString(arg);
    }

}
