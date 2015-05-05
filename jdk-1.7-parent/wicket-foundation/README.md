#wicket-foundation

Integrates [Apache Wicket](http://wicket.apache.org/) 7.x and [Zurb Foundation](http://foundation.zurb.com/) 5.x

<br>

## Build status and test coverage:

![Build status](https://travis-ci.org/iluwatar/wicket-foundation.svg?branch=master)
&nbsp;&nbsp;&nbsp;
[![Coverage Status](https://coveralls.io/repos/iluwatar/wicket-foundation/badge.png?branch=master)](https://coveralls.io/r/iluwatar/wicket-foundation?branch=master)

<br>

## Usage

Wicket-Foundation is available in the Sonatype Nexus. Add the following dependency to the project pom.xml:

```xml
<dependency>
    <groupId>com.iluwatar</groupId>
    <artifactId>wicket-foundation-core</artifactId>
    <version>0.3.0</version>
</dependency>
```

<br>

## Screenshot from the sample application (shows project progress status):

![alt text](https://github.com/iluwatar/wicket-foundation/blob/master/catalog.jpg "Samples catalog")

<br>

## Build instructions

```
mvn clean install
cd wicket-foundation-samples
mvn jetty:run
```
Now open browser and navigate to http://localhost:8080 to see the sample application.

<br>

## License

Wicket-foundation is distributed under the terms of the Apache Software Foundation
license, version 2.0. The text is included in the file LICENSE.md in the root
of the project.

<br>

## Semantic versioning

Wicket-foundation uses [semantic versioning](http://semver.org/). However, for versions 0.x while the fundamental operations are still under development the API breaks can and will happen.

<br>

## Changelog

v0.3.0
* Added FoundationSplitButton.
* Added FoundationRevealModal.
* Updated to Wicket 7.0.0-M5
* Updated to Foundation 5.5.1

v0.2.1
* Redesign of Breadcrumbs component.
* Improved Javadocs for all components.
* Checked naming of all classes.
* Checked visibility of all classes.

v0.2.0
* Added Progress Bar.
* Added Side Nav.
* Added Sub Nav.

v0.1.1
* Added tests. Minor implementation changes to Icon Bar.

v0.1.0
* Foundation icons added.

v0.0.2
* Added basic Javadoc to important classes.
* Removed Guava dependency.
* Removed Jetty dependency from wicket-foundation-core.
 
v0.0.1
* Initial release with Visibility, Grid, Block Grid, Top Bar, Icon Bar, Breadcrumbs, Buttons, Button Groups, Dropdown Buttons, Type, Inline Lists, Labels, Alerts, Panels, Tooltips, Dropdowns.
