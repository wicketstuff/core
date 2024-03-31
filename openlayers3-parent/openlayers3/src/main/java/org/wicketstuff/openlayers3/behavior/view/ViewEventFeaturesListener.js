${callbackFunctionName} = function(view, features) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
           return {'view': view, 'features': features,};
         }]});
};

window.org_wicketstuff_openlayers3['map_${componentId}'].getView().on('propertychange', function(event) {

  var view = null

  if(event.key == 'center' || event.key == 'resolution') {
    view = event.target.getProperties();

    if('${projection}' != 'NULL') {

         extent = ol.extent.applyTransform(window.org_wicketstuff_openlayers3['map_${componentId}'].getView().calculateExtent(map_${componentId}.getSize()),
                                           ol.proj.getTransform(window.org_wicketstuff_openlayers3['map_${componentId}'].getView().getProjection(),
                                                                '${projection}'));

         view['transformedExtent'] = extent;
         view['transformedProjection'] = '${projection}';
      }
  }

  var features = [];

  ${layerId}.getSource().forEachFeatureInExtent(window.org_wicketstuff_openlayers3['map_${componentId}'].getView().calculateExtent(window.org_wicketstuff_openlayers3['map_${componentId}'].getSize()),
    function(feature) {

      var coordinateRaw = feature.getGeometry().getCoordinates();
      var coordinateHdms = coordinateRaw;

      if('${projection}' != 'NULL') {
        coordinateHdms = ol.proj.transform(
        coordinateRaw, window.org_wicketstuff_openlayers3['map_${componentId}'].getView().getProjection(), '${projection}');
      }

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

  ${callbackFunctionName}(JSON.stringify(view), JSON.stringify(features));
});
