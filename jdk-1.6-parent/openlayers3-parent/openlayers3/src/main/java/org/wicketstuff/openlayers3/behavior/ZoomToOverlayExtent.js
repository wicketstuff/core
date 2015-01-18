var map = window.org_wicketstuff_openlayers3['map_${componentId}'];
var view = map.getView();

var points = []
map.getOverlays().forEach(function (o) {
    if(!(typeof o.getPosition() === 'undefined')) {
      points.push(o.getPosition());
    }
});

var extent = ol.extent.boundingExtent(points);

if('${buffer}' != 'NULL') {
  extent = ol.extent.buffer(extent, parseFloat('${buffer}'));
}

map.getView().fitExtent(extent, map.getSize());
