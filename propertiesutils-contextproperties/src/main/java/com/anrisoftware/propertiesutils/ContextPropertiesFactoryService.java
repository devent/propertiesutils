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

package com.anrisoftware.propertiesutils;

import org.osgi.service.component.annotations.Component;

/**
 * Provides {@link ContextPropertiesFactory}.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@Component(service = ContextPropertiesFactoryService.class)
public class ContextPropertiesFactoryService {

    public ContextPropertiesFactory create(Object context) {
        return new ContextPropertiesFactory(context);
    }

    public ContextPropertiesFactory create(Class<?> context) {
        return new ContextPropertiesFactory(context);
    }

    public ContextPropertiesFactory create(String context) {
        return new ContextPropertiesFactory(context);
    }

}
