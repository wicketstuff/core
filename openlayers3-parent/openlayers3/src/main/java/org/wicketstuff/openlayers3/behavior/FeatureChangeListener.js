/*
 * Variable to hold our timer.
 */
window.org_wicketstuff_openlayers3['updateTimerChangeFeatureHandler_${componentId}_${changeHandlerId}'] = null;

/**
 * Posts our new data back to the server.
 *
 * @param id The unique identifier of the modified feature
 * @param coordinate The new coordinate for the modified feature
 * @param properties The properties of the modified feature
 */
var changeFeatureHandler_${componentId}_${changeHandlerId} = function(id, coordinate, properties) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return {'id': id, 'coordinate': coordinate, 'properties': properties}
         }]});
};

/**
 * Converts the geometry of the provided feature to the map's current
 * view's projection geometry.
 *
 * @param feature Feature to convert
 * @returns A feature with the new geometry
 */
var convertFeature = function(feature) {

    var coordinateRaw = feature.getGeometry().getCoordinates();
    var coordinateHdms = coordinateRaw;

    if('${projection}' != 'NULL') {
        coordinateHdms = ol.proj.transform(
            coordinateRaw, window.org_wicketstuff_openlayers3['map_${componentId}'].getView().getProjection(), '${projection}');
    }

    var valuesOut = {};
    feature.getKeys().forEach(function(key) {

        if(key == 'geometry') {
            valuesOut[key] = coordinateHdms;
        } else if(key == 'features') {

            var featuresOut = [];
            feature.get(key).forEach(function(featureThis) {
                featuresOut.push(convertFeature(featureThis));
            });

            valuesOut[key] = featuresOut;
        } else {
            valuesOut[key] = feature.get(key);
        }
    });

    return valuesOut;
};

/**
 * Restarts the timer if it exists, creates a new timer if it does
 * not. When the timer expires, the updated feature information will be
 * posted back to the server.
 *
 * @param timeoutThis Timeout to either reset or create
 * @returns A reference to the timeout
 */
var restartTimeout = function() {
    console.log("Starting timer...");
    clearTimeout(window.org_wicketstuff_openlayers3['updateTimerChangeFeatureHandler_${componentId}_${changeHandlerId}']);

   window.org_wicketstuff_openlayers3['updateTimerChangeFeatureHandler_${componentId}_${changeHandlerId}'] = window.setTimeout(function() {
       console.log("Firing timer!");
       clearTimeout( window.updateTimerChangeFeatureHandler_${componentId}_${changeHandlerId});
       var valuesOut = convertFeature(${featureId});
       changeFeatureHandler_${componentId}_${changeHandlerId}(${featureId}, valuesOut["geometry"], JSON.stringify(valuesOut));
    }, 500);
};

// register our change event handler on the feature
${featureId}.on('change', function(event) {
    console.log("Change event received: " + event);
    restartTimeout();
});
