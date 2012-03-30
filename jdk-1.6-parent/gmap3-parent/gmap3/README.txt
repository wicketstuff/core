wicket-[contrib]-gmap3, the only right Google Maps implementation for wicket. 

maven is not supported now!
But  "mvn install -DperformRelease=true" works for me

Target is to migrate wicket-contrib-gmap2  from the deprecated api v2 to v3.

project status: 50%

 - Simple map works
 - Simple Markers
 - InfoWindow works
 - ClientId 
 - Asynchronous init of map works

TODOs:
    - add Findbungs and [notnull|nullable] annotations
    - remove obsoleted documentations (i think the most)
    - remove deprecated code
