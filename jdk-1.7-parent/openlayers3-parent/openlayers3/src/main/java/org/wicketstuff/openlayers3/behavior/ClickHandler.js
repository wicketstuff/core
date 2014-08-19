var clickHandler_${componentId}_${clickHandlerId} = function(coordinate) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return {'coordinate': coordinate}
         }]});
};

map_${componentId}.on('click', function(event) {
    var coordinateRaw = event.coordinate;

    var coordinateHdms = coordinateRaw;
    if('${projection}' != 'NULL') {
        coordinateHdms = ol.proj.transform(
          coordinateRaw, map_${componentId}.getView().getProjection(), '${projection}');
    }

   clickHandler_${componentId}_${clickHandlerId}(coordinateHdms);
});