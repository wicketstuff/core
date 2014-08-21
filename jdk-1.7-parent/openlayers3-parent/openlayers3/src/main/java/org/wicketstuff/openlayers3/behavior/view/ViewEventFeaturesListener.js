${callbackFunctionName} = function(view, features) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
           return {'view': view, 'features': features,};
         }]});
};

map_${componentId}.getView().on('propertychange', function(event) {

  var view = null

  if(event.key == 'center' || event.key == 'resolution') {
    view = event.target.getProperties();

    if('${projection}' != 'NULL') {

         extent = ol.extent.applyTransform(map_${componentId}.getView().calculateExtent(map_${componentId}.getSize()),
                                           ol.proj.getTransform(map_${componentId}.getView().getProjection(),
                                                                '${projection}'));

         view['transformedExtent'] = extent;
         view['transformedProjection'] = '${projection}';
      }
  }

  var features = [];

  ${layerId}.getSource().forEachFeatureInExtent(map_${componentId}.getView().calculateExtent(map_${componentId}.getSize()),
    function(feature) {

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

      features.push(values);
  });

  ${callbackFunctionName}(JSON.stringify(view), JSON.stringify(features));
});
