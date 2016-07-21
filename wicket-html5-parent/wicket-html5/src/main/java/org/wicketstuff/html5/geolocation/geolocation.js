if (typeof(Wicket.Html5) == 'undefined') {
	Wicket.Html5 = {};
}

Wicket.Html5.geo_${componentId} = '${callbackUrl}';

if (!!navigator.geolocation){
// this solution was inspired by http://stackoverflow.com/questions/4736057/geolocation-feedback-while-accepting-the-request    
var succeed = function(location) {
    navigator.geolocation.received = true;
    sendGeo(location.coords.latitude, location.coords.longitude);
};
var failed = function(errorObj) { 
    navigator.geolocation.received = true;
    sendError(errorObj);
};

var timedout = function() {
    if (!navigator.geolocation.received)
        sendTimeout();
}
    // Extend geolocation object
    navigator.geolocation.retrievePermission = function retrievePermission(succeed,failed) {
        this.received = false;              // reference for timeout callback
        setTimeout(timedout,${timeout});
        this.getCurrentPosition.apply(this,arguments);  // actual request
    }

    // New location request with timeout callback
    navigator.geolocation.retrievePermission(succeed,failed,{},{});

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

function sendError(errorObj){
    Wicket.Ajax.get(
		{
			'u': Wicket.Html5.geo_${componentId},
			'dep': [
				function(){
					return {'code': errorObj.code, 'message': errorObj.message}
				}
			]
		}
	);
}

function sendTimeout(){
    Wicket.Ajax.get(
		{
			'u': Wicket.Html5.geo_${componentId},
			'dep': [
				function(){
					return {'timeout': 'timeout'}
				}
			]
		}
	);
}