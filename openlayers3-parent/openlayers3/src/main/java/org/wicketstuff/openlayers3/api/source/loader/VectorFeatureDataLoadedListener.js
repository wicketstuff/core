dataLoadedHandler_${dataLoaderId} = function(source) {

    var layer = ${componentId};
    var source = layer.getSource();

    var features = [];
    source.getFeatures().forEach(function(feature) {

        var coordinateRaw = feature.getGeometry().getCoordinates();
        var coordinateHdms = coordinateRaw;

        var values = {};
        feature.getKeys().forEach(function(key) {
            if(key != 'geometry') {
                values[key] = feature.get(key);
            } else {
                values[key] = coordinateHdms;
            }
        });

        features.push(values);
    });

    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return{'features': JSON.stringify(features),}
         }]});
};