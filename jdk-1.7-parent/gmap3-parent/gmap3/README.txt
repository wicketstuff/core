wicketstuff-gmap3 provides a component to integrate Google Maps v3 into your
Wicket 6 application.

If you are familiar with the wicket-contrib-gmap2 project (https://github.com/wicketstuff/core/tree/master/jdk-1.6-parent/gmap2-parent)
you should have little to no problems to use this component since the code 
is based mainly on the wicket-contrib-gmap2 project.

There are a few differences though:
- Since Google Maps v3 does not need any API-key anymore the usage of such a
key is at the moment not supported.
- Since version 3 you can have more than one InfoWindow
- The handling which controls should be shown on the map has changed a bit
(in version 2 you could use GSmallMapControl, etc. Now you have to use the methods
setXXXControlEnabled in the GMap class).

There are plenty of examples showing how to use this component. Have a look at them.
