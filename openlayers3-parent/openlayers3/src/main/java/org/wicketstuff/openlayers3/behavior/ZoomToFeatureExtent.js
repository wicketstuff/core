var map = window.org_wicketstuff_openlayers3['map_${componentId}'];
var view = map.getView();
var layers = 'NULL';

if("${layers}" != 'NULL') {
  layers = "${layers}".split(',');
}

var points = []
map.getLayers().forEach(function (layer) {

  if(typeof layer.getSource().forEachFeature === 'function') {

    if(layers == 'NULL' || layers.indexOf(layer.get('id')) > -1) {
      layer.getSource().forEachFeature(function(feature) {
         points.push(feature.getGeometry().getCoordinates());
      });
    }
  }
});

var extent = ol.extent.boundingExtent(points);
if('${buffer}' != 'NULL') {
    extent = ol.extent.buffer(extent, parseFloat('${buffer}'));
}

map.getView().fitExtent(extent, map.getSize());
