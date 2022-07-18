[![Build Status](https://jenkins.anrisoftware.com/job/com.anrisoftware.propertiesutils-propertiesutils/job/main/badge/icon)](https://jenkins.anrisoftware.com/job/com.anrisoftware.propertiesutils-propertiesutils)
[![Gate](https://sonarcloud.io/api/project_badges/measure?project=devent_propertiesutils&metric=alert_status)](https://sonarcloud.io/project/overview?id=devent_propertiesutils)
[![Apache License, Version 2.0](https://project.anrisoftware.com/attachments/download/217/apache2.0-small.gif)](http://www.apache.org/licenses/LICENSE-2.0)
© 2012-2022 Erwin Müller

Description
===========

Loads properties from a properties file. The properties keys are
prefixed with the context, if not already present. The context is an
object package name. For example, if the context object is the class
ContextProperties then the context is
"com.anrisoftware.propertiesutils". If then a property with the name
"foo" is requested, then the property
"com.anrisoftware.propertiesutils.foo" is looked up. Defines additional
methods to return different types of properties, like boolean, number,
list.

The ContextPropertiesFactory class defines various methods to load a
properties resource from the class path. Furthermore, the abstract
provider AbstractContextPropertiesProvider can be used to inject loaded
properties into a class. See the Javadoc of those classes for examples.

Links
=====

-   [Generated
    Site](https://javadoc.anrisoftware.com/com.anrisoftware.propertiesutils/propertiesutils/4.6.3/index.html)
-   [Download
    (Central)](https://search.maven.org/search?q=g:com.anrisoftware.propertiesutils)
-   [Source
    code](https://gitea.anrisoftware.com/com.anrisoftware.propertiesutils/propertiesutils)
-   [Source code (Github)](https://github.com/devent/propertiesutils)
-   [Project
    Home](https://project.anrisoftware.com/projects/propertiesutils)
-   [Project
    Roadmap](https://project.anrisoftware.com/projects/propertiesutils/roadmap)
-   [Project
    Issues](https://project.anrisoftware.com/projects/propertiesutils/issues)
-   [Jenkins](https://jenkins.anrisoftware.com/job/com.anrisoftware.propertiesutils-propertiesutils)

Example Properties File
=======================

    com.anrisoftware.propertiesutils.testString = Foo
    com.anrisoftware.propertiesutils.testWithReplacements = Foo ${foo}
    com.anrisoftware.propertiesutils.testWithReplacementsSystem = Foo ${os.name}

License
=======

Copyright ©2012 - 2022 Erwin Müller. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");\
you may not use this file except in compliance with the License.\
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software\
distributed under the License is distributed on an "AS IS" BASIS,\
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied.\
See the License for the specific language governing permissions and\
limitations under the License.
