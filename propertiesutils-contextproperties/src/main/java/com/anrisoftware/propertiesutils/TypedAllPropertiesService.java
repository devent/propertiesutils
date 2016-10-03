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

import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.google.inject.Guice;

/**
 * Provides {@link TypedAllProperties}.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.1
 */
@Component
@Service(TypedAllPropertiesService.class)
public class TypedAllPropertiesService implements TypedAllPropertiesFactory {

    @Inject
    private TypedAllPropertiesFactory factory;

    @Override
    public TypedAllProperties create(Map<String, Object> properties,
            String listSepChars) {
        return factory.create(properties, listSepChars);
    }

    @Override
    public TypedAllProperties create(Map<String, Object> properties) {
        return factory.create(properties);
    }

    @Override
    public TypedAllProperties create(Properties properties,
            String listSepChars) {
        return factory.create(properties, listSepChars);
    }

    @Override
    public TypedAllProperties create(Properties properties) {
        return factory.create(properties);
    }

    @Activate
    protected void start() {
        Guice.createInjector(new PropertiesUtilsModule()).injectMembers(this);
    }
}
