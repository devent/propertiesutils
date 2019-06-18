<a href="https://jenkins.anrisoftware.com/job/propertiesutils-public/job/master">![Build Status](https://jenkins.anrisoftware.com/job/propertiesutils-public/job/master/badge/icon "Build Status")</a> <a href="https://sonar.anrisoftware.com/dashboard?id=com.anrisoftware.propertiesutils%3Apropertiesutils">![Quality Gate](https://sonar.anrisoftware.com/api/badges/gate?key=com.anrisoftware.propertiesutils%3Apropertiesutils "Quality Gate")</a> <a href="http://www.apache.org/licenses/LICENSE-2.0">![Apache License, Version 2.0](https://project.anrisoftware.com/attachments/download/217/apache2.0-small.gif "Apache License, Version 2.0")</a> © 2012-2019 Erwin Müller

Description
===========

Adds Groovy support to compile Groovy sources and test sources as an OSGi bundle

Loads properties from a properties file. The properties keys are prefixed with the context, if not already present. The context is an object package name. For example, if the context object is the class ContextProperties then the context is “com.anrisoftware.propertiesutils”. If then a property with the name “foo” is requested, then the property “com.anrisoftware.propertiesutils.foo” is looked up. Defines additional methods to return different types of properties, like boolean, number, list.

The ContextPropertiesFactory class defines various methods to load a properties resource from the class path. Furthermore, the abstract provider AbstractContextPropertiesProvider can be used to inject loaded properties into a class. See the Javadoc of those classes for examples.

Example Properties File
-----------------------

    com.anrisoftware.propertiesutils.testString = Foo
    com.anrisoftware.propertiesutils.testWithReplacements = Foo ${foo}
    com.anrisoftware.propertiesutils.testWithReplacementsSystem = Foo ${os.name}

Links
=====

-   [Generated Site](https://javadoc.anrisoftware.com/com.anrisoftware.propertiesutils/propertiesutils/4.5.1-SNAPSHOT/)
-   [Download (Central)](https://search.maven.org/artifact/com.anrisoftware.propertiesutils/propertiesutils/4.5.1-SNAPSHOT/pom)
-   [Source code](https://gitea.anrisoftware.com/anrisoftware.com/propertiesutils.git)
-   [Source code (Github)](https://github.com/devent/propertiesutils)
-   [Project Home](https://project.anrisoftware.com/projects/propertiesutils)
-   [Project Roadmap](https://project.anrisoftware.com/projects/propertiesutils/roadmap)
-   [Project Issues](https://project.anrisoftware.com/projects/propertiesutils/issues)
-   [Jenkins](https://jenkins.anrisoftware.com/job/propertiesutils-public)
-   [SonarQube](https://sonar.anrisoftware.com/dashboard?id=com.anrisoftware.propertiesutils%3Apropertiesutils)

License
=======

Copyright ©2012 - 2019 [Advanced Natural Research Institute](https://anrisoftware.com/). All rights reserved.

Licensed under the Apache License, Version 2.0 (the “License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an “AS IS” BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

