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

import java.util.Map;
import java.util.Properties;

import org.osgi.service.component.annotations.Component;

/**
 * Provides {@link TypedProperties}.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@Component(service = TypedPropertiesService.class)
public class TypedPropertiesService {

    public TypedProperties create(Map<String, Object> properties,
            String listSepChars) {
        return new TypedProperties(properties, listSepChars);
    }

    public TypedProperties create(Map<String, Object> properties) {
        return new TypedProperties(properties);
    }

    public TypedProperties create(Properties properties, String listSepChars) {
        return new TypedProperties(properties, listSepChars);
    }

    public TypedProperties create(Properties properties) {
        return new TypedProperties(properties);
    }
}
