${callbackFunctionName} = function(view) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
           return {'view': view,};
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

  ${callbackFunctionName}(JSON.stringify(view));
});
