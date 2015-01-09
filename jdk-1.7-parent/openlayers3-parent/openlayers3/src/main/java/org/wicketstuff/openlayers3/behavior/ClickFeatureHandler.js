var clickFeatureHandler_${componentId}_${clickHandlerId} = function(id, coordinate, properties) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return {'id': id, 'coordinate': coordinate, 'properties': properties}
         }]});
};

window.org_wicketstuff_openlayers3['map_${componentId}'].on('click', function(event) {

   var convertFeature = function(feature) {

     var coordinateRaw = feature.getGeometry().getCoordinates();
     var coordinateHdms = coordinateRaw;

     if('${projection}' != 'NULL') {
         coordinateHdms = ol.proj.transform(
           coordinateRaw, window.org_wicketstuff_openlayers3['map_${componentId}'].getView().getProjection(), '${projection}');
     }

     var values = {};
     feature.getKeys().forEach(function(key) {

         if(key == 'geometry') {
             values[key] = coordinateHdms;
         } else if(key == 'features') {

             var featuresOut = [];
             feature.get(key).forEach(function(featureThis) {
               featuresOut.push(convertFeature(featureThis));
             });

             values[key] = featuresOut;
         } else {
             values[key] = feature.get(key);
         }
     });

     return values;
   };

   var feature = window.org_wicketstuff_openlayers3['map_${componentId}'].forEachFeatureAtPixel(event.pixel,
     function(feature, layer) {
       return feature;
     });

    if(feature) {

      var values = convertFeature(feature);
      clickFeatureHandler_${componentId}_${clickHandlerId}(feature.get('id'), values["geometry"], JSON.stringify(values));
    } else {

        var coordinateRaw = event.coordinate;
        var coordinateHdms = coordinateRaw;

        if('${projection}' != 'NULL') {
            coordinateHdms = ol.proj.transform(
              coordinateRaw, window.org_wicketstuff_openlayers3['map_${componentId}'].getView().getProjection(), '${projection}');
        }
      clickFeatureHandler_${componentId}_${clickHandlerId}(null, coordinateHdms);
    };
});
