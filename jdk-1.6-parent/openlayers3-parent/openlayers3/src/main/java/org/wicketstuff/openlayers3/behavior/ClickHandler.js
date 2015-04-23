var clickHandler_${componentId}_${clickHandlerId} = function(coordinate) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return {'coordinate': coordinate}
         }]});
};

window.org_wicketstuff_openlayers3['map_${componentId}'].on('singleclick', function(event) {
    var coordinateRaw = event.coordinate;

    var coordinateHdms = coordinateRaw;
    if('${projection}' != 'NULL') {
        coordinateHdms = ol.proj.transform(
          coordinateRaw, window.org_wicketstuff_openlayers3['map_${componentId}'].getView().getProjection(), '${projection}');
    }

   clickHandler_${componentId}_${clickHandlerId}(coordinateHdms);
});
