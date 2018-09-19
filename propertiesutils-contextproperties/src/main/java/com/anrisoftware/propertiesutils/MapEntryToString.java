/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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

/*-
 * #%L
 * Properties Utilities :: Context Properties
 * %%
 * Copyright (C) 2012 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

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
     * @return the {@link String} or {@code null} if the argument doesn't
     *         exists.
     */
    public String toStringNull(Map<String, Object> args, String key) {
        Object value = args.get(key);
        if (value == null) {
            return null;
        }
        return toString(value, key);
    }

    /**
     * Converts the argument to a {@link String}.
     *
     * @throws NullPointerException
     *             if the argument is {@code null}.
     */
    public String toString(Map<String, Object> args, String key) {
        Object value = args.get(key);
        return toString(value, key);
    }

    /**
     * Converts the argument to a {@link String}.
     *
     * @return the {@link String} or {@code null} if the argument doesn't
     *         exists.
     */
    @SuppressWarnings("deprecation")
    public String toStringNull(Object arg, String name) {
        if (arg == null) {
            return null;
        }
        return ObjectUtils.toString(arg);
    }

    /**
     * Converts the argument to a {@link String}.
     *
     * @throws NullPointerException
     *             if the argument is {@code null}.
     */
    @SuppressWarnings("deprecation")
    public String toString(Object arg, String name) {
        notNull(arg, "arg");
        return ObjectUtils.toString(arg);
    }

}
