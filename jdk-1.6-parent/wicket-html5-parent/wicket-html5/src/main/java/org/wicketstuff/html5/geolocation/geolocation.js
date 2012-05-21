if (typeof(Wicket.Html5) == 'undefined') {
	Wicket.Html5 = {};
}

Wicket.Html5.geo_${componentId} = '${callbackUrl}';

if (!!navigator.geolocation){
	navigator.geolocation.getCurrentPosition(function(location) {
		sendGeo(location.coords.latitude, location.coords.longitude);
	});
}

function sendGeo(geoLat, geoLong){
    Wicket.Ajax.get(
		{
			'u': Wicket.Html5.geo_${componentId},
			'dep': [
				function(){
					return {'lat': geoLat, 'long': geoLong}
				}
			]
		}
	);
}