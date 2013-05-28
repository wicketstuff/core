WicketStuff Stateless
=====================================================

Sometimes it is good to be stateless. As the core of Wicket is focused on managing stateful behavior there is sometimes a lack of support for stateless pages. The goal of this package is to add a few components that provide more comprehensive stateless features for Apache Wicket.

These components currently include a ``StatelessLink`` and a ``StatelessAjaxFallbackLink``. They are backed by stateless behaviors: ``StatelessAjaxEventBehavior`` and ``StatelessAjaxFormComponentUpdatingBehavior``.


== Maven ==

This component is part of WicketStuff project and is regularly released at Maven Central repository. Simply add the following lines of configuration to our `pom.xml`:

````xml
<dependencies>
  <dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-stateless</artifactId>
    <version>6.8.0</version>
  </dependency>
</dependencies>
````