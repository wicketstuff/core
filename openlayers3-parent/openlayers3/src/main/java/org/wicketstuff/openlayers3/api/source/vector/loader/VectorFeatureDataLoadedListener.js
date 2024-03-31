dataLoadedHandler_${dataLoaderId} = function(source) {

    var layer = ${componentId};
    var source = layer.getSource();

    var features = [];
    source.getFeatures().forEach(function(feature) {

        var coordinateRaw = feature.getGeometry().getCoordinates();
        var coordinateHdms = coordinateRaw;

        var valuesOut = {};
        feature.getKeys().forEach(function(key) {
            if(key != 'geometry') {
                valuesOut[key] = feature.get(key);
            } else {
                valuesOut[key] = coordinateHdms;
            }
        });

        features.push(valuesOut);
    });

    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return{'features': JSON.stringify(features),}
         }]});
};