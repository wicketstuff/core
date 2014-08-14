var map = map_${componentId};
var view = map.getView();
var layers = 'NULL';

if('${layers}' != 'NULL') {
  layers = '${layers}'.split(',');
}

var points = []
map.getLayers().forEach(function (layer) {

  if(typeof layer.getSource().forEachFeature === 'function') {
    console.log(layer.get('id'));
    if(layers == 'NULL' || layers.indexOf(layer.get('id')) > -1) {
      layer.getSource().forEachFeature(function(feature) {
         points.push(feature.getGeometry().getCoordinates());
      });
    }
  }
});

console.log(points);

var extent = ol.extent.boundingExtent(points);
if('${buffer}' != 'NULL') {
    extent = ol.extent.buffer(extent, parseFloat('${buffer}'));
}

map.getView().fitExtent(extent, map.getSize());