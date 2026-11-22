---
title: GMap3
category: js
description: "Google Maps v3 as a Wicket component: markers, overlays and events."
artifactId: wicketstuff-gmap3
tags: maps google
---

The GMap3 project offers a component to use Google Maps v3 within Wicket applications.

## History

Even though the code is based on the old GMap2 module, those components are not compatible!

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-gmap3</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Examples

### A simple map

```html
<html>
    <body>
        <div wicket:id="map">Map</div>
    </body>
</html>
```

```java
public class SimplePage extends WebPage
{

    public SimplePage()
    {
        GMap map = new GMap("map", "your-api-key");
        map.setStreetViewControlEnabled(false);
        map.setScaleControlEnabled(true);
        map.setScrollWheelZoomEnabled(true);
        map.setCenter(new GLatLng(52.47649, 13.228573));
        add(map);
    }
}
```

Google Maps requires an API key; pass it to the `GMap` constructor (or use a `GMapHeaderContributor`).

The `setXXXEnabled` methods modify the behavior and/or the appearance of the map. More setters of this kind are:

* `setDoubleClickZoomEnabled`
* `setScrollWheelZoomEnabled`
* `setDraggingEnabled`
* `setMapTypeControlEnabled`
* `setStreetViewControlEnabled`
* `setZoomControlEnabled`

The names should be self explainable.

### Adding markers with each click on the map

This example demonstrates how to add a marker to each position you click on. The HTML file stays the same.

```java
public class HomePage extends WebPage
{
    public HomePage()
    {
        final GMap map = new GMap("map", "your-api-key");
        add(map);
        map.add(new ClickListener()
        {
            @Override
            protected void onClick(AjaxRequestTarget target, GLatLng latLng)
            {
                if (latLng != null)
                {
                    map.addOverlay(new GMarker(new GMarkerOptions(map, latLng)));
                }
            }
        });
    }
}
```

Make sure that you add your map to the page before you apply any listeners!

A `GMarker` is only one overlay among others. Also available:

* `GGroundOverlay`
* `GInfoWindow` (since version 3 you can have more than one InfoWindow)
* `GPolygon`
* `GPolyline`

For more examples see the examples project. It covers nearly all methods provided by this component.

## License

Just like Wicket itself, GMap3 is licensed under Apache 2.0.

The [`gmap3-examples`](https://github.com/wicketstuff/core/tree/wicket-11.x/gmap3-parent/gmap3-examples) module contains a runnable example application.

Source: [`gmap3-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/gmap3-parent)
