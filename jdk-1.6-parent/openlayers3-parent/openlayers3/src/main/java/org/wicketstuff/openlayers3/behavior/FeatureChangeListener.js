var changeFeatureHandler_${componentId}_${changeHandlerId} = function(id, coordinate, properties) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return {'id': id, 'coordinate': coordinate, 'properties': properties}
         }]});
};

${featureId}.on('change', function(event) {

    var convertFeature = function(feature) {

         var coordinateRaw = feature.getGeometry().getCoordinates();
         var coordinateHdms = coordinateRaw;

         if('${projection}' != 'NULL') {
             coordinateHdms = ol.proj.transform(
               coordinateRaw, map_${componentId}.getView().getProjection(), '${projection}');
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

    values = convertFeature(${featureId});
    changeFeatureHandler_${componentId}_${changeHandlerId}(${featureId}, values["geometry"], JSON.stringify(values));
});