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

import java.util.Map;
import java.util.Properties;

import org.osgi.service.component.annotations.Component;

/**
 * Provides {@link JodaDateProperties}.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 4.5.1
 */
@Component(service = JodaDatePropertiesService.class)
public class JodaDatePropertiesService {

    public JodaDateProperties create(Map<String, Object> properties,
            String listSepChars) {
        return new JodaDateProperties(properties, listSepChars);
    }

    public JodaDateProperties create(Map<String, Object> properties) {
        return new JodaDateProperties(properties);
    }

    public JodaDateProperties create(Properties properties, String listSepChars) {
        return new JodaDateProperties(properties, listSepChars);
    }

    public JodaDateProperties create(Properties properties) {
        return new JodaDateProperties(properties);
    }
}
