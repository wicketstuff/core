OpenLayers 3 Parent
==================

This project provides a set of components that may be used to add interactive maps to a Wicket application. These 
components wrap the [OpenLayers 3][0] Javascript library and provide the necessary glue to let you manage them entirely
in Wicket with Java code.

This library is currently in active development and not all of the OpenLayers 3 functionality has been implemented. 
Nonetheless, work continues on the library and if there's functionality you'd like that is missing, it may be added to
the project soon. Please feel free to submit pull requests as well, we're always looking for help!

Installation
------------

Installing the library is as easy as including the dependency in your project's POM file.

    <dependency>
      <groupId>org.wicketstuff</groupId>
      <artifactId>wicketstuff-openlayers3</artifactId>
      <version>6.0-SNAPSHOT</version>
    </dependency>
    
This will include the majority of the library's features. In addition, some components that leverage the 
[Wicket Bootstrap][1] library are also included in a seperate library. If you are already using Bootstrap in your 
application, these components make it easier to place location markers and popover panels in your maps.
    
    <dependency>
      <groupId>org.wicketstuff</groupId>
      <artifactId>wicketstuff-openlayers3-bootstrap</artifactId>
      <version>6.0-SNAPSHOT</version>
    </dependency>

Usage
-----

We've done our best to keep the API simple and straightforward. Where possible, the Java classes that you interact with
will have the same names as the Javascript object that they are wrapping. More information about [the OpenLayers3 API 
may be found on their website][2]. Below is the Java code from page a that displays a map of the world.

```java
add(new DefaultOpenLayersMap("map", Model.of(new Map(
  Arrays.<Layer>asList(new Tile("Global Imagery",
    new TileWms("http://maps.opengeo.org/geowebcache/service/wms",
      ImmutableMap.of("LAYERS", "bluemarble", "VERSION", "1.1.1")))),
  new View(new Coordinate(0, 0), 2)))));
```

In this example a new `DefaultOpenLayersMap` map component is created and it's passed a `Map` instance as it's model. 
The `DefaultOpenLayersMap` is our Wicket component and may be manipulated as such. The model for this object, `Map`,
is the wrapper around the OpenLayers 3 `ol.Map` instance. The goal is to make it easier to mentally map the function
onto the object, for instance, if you need to make your map invisible (a Wicket function) then you may invoke the 
`setVisibilityAllowed` method on the Wicket component, `DefaultOpenLayersMap`. 

```java
openLayersMap.setVisibilityAllowed(false);
```

If you want to add another vector layer to the map (an OpenLayers 3 function), then you will want to call the 
`getLayers` method on the model for your map, `Map`, as this object represents the matching OpenLayers object.

```java
openLayersMap.getModel().getObject().getLayers()
  .add(new Tile("Global Imagery",
    new TileWms("http://maps.opengeo.org/geowebcache/service/wms",
      ImmutableMap.of("LAYERS", "bluemarble", "VERSION", "1.1.1")));
```

More information may be found in the `openlayers3-examples` project. To get an idea of what these maps look like, you 
may build and run the application via Maven.

    cd jdk-1.6-parent/openlayers3-parent/openlayers3-examples/
    mvn jetty:run
    
The project will be built and launched inside a Jetty instance. You can take a look by visitng http://localhost:8080.

----
[0]: http://ol3js.org/ "OpenLayers 3 Project Home Page"
[1]: http://wb.agilecoders.de/demo/ "Wicket Bootstrap Project Home Page"
[2]: http://ol3js.org/en/master/apidoc/ "OpenLayers 3 API Reference"