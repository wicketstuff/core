${callbackFunctionName} = function(view) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
           return {'view': view,};
         }]});
};

window.org_wicketstuff_openlayers3['map_${componentId}'].getView().on('propertychange', function(event) {

  var view = null

  if(event.key == 'center' || event.key == 'resolution') {
    view = event.target.getProperties();

    if('${projection}' != 'NULL') {

         extent = ol.extent.applyTransform(window.org_wicketstuff_openlayers3['map_${componentId}'].getView().calculateExtent(window.org_wicketstuff_openlayers3['map_${componentId}'].getSize()),
                                           ol.proj.getTransform(window.org_wicketstuff_openlayers3['map_${componentId}'].getView().getProjection(),
                                                                '${projection}'));

         view['transformedExtent'] = extent;
         view['transformedProjection'] = '${projection}';
      }
  }

  ${callbackFunctionName}(JSON.stringify(view));
});
