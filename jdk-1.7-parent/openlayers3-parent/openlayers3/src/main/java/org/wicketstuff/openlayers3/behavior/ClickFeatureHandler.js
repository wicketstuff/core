var clickFeatureHandler_${componentId}_${clickHandlerId} = function(id, coordinate, properties) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return {'id': id, 'coordinate': coordinate, 'properties': properties}
         }]});
};

map_${componentId}.on('click', function(event) {
   var feature = map_${componentId}.forEachFeatureAtPixel(event.pixel,
     function(feature, layer) {
       return feature;
     });

    if(feature) {
        var coordinateRaw = feature.getGeometry().getCoordinates();
        var coordinateHdms = coordinateRaw;

        if('${projection}' != 'NULL') {
            coordinateHdms = ol.proj.transform(
              coordinateRaw, map_${componentId}.getView().getProjection(), '${projection}');
        }

        var values = {};
        feature.getKeys().forEach(function(key) {
            if(key != 'geometry') {
                values[key] = feature.get(key);
            } else {
                values[key] = coordinateHdms;
            }
        });

      clickFeatureHandler_${componentId}_${clickHandlerId}(feature.get('id'), coordinateHdms, JSON.stringify(values));
    } else {

        var coordinateRaw = event.coordinate;
        var coordinateHdms = coordinateRaw;

        if('${projection}' != 'NULL') {
            coordinateHdms = ol.proj.transform(
              coordinateRaw, map_${componentId}.getView().getProjection(), '${projection}');
        }
      clickFeatureHandler_${componentId}_${clickHandlerId}(null, coordinateHdms);
    };
});