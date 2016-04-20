# propertiesutils-contextproperties

## Description

Loads properties from a properties file. The properties keys are prefixed
with the context, if not already present. The context is an object package
name. For example, if the context object is the class ContextProperties then
the context is "com.anrisoftware.propertiesutils". If then a property with
the name "foo" is requested, then the
property "com.anrisoftware.propertiesutils.foo" is looked up.
Defines additional methods to return different types of properties, like
boolean, number, list.

The ContextPropertiesFactory class defines various methods to load a
properties resource from the class path. Furthermore, the abstract provider
AbstractContextPropertiesProvider can be used to inject loaded properties
into a class. See the Javadoc of those classes for examples.

### Example Properties File

```
com.anrisoftware.propertiesutils.testString = Foo
com.anrisoftware.propertiesutils.testWithReplacements = Foo ${foo}
com.anrisoftware.propertiesutils.testWithReplacementsSystem = Foo ${os.name}
```

## Maven

```
<dependency>
    <groupId>com.anrisoftware.propertiesutils</groupId>
    <artifactId>propertiesutils-contextproperties</artifactId>
    <version>2.2</version>
</dependency>
```

# SCM

* [Main Repository](https://anrisoftware.com/projects/projects/propertiesutils/repository)
* `git@anrisoftware.com:propertiesutils.git`
* [Github Mirror Repository](https://github.com/devent/propertiesutils)
* `git@anrisoftware.com:propertiesutils.git`

# License

Copyright 2012-2016 Erwin Müller <erwin.mueller@deventm.org>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
