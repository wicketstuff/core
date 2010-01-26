function onEventFor${behaviorMarkupId}(message){
	if (message.data.proxy = "true"){
		var addToUrl = "" 
		var doRoundTrip = "true";
		for (prop in message.data){
		    // do not make a roundtrip to server if the property is named evalscript
			if(prop == "evalscript") {
			    doRoundTrip = "false";			   
			    eval(message.data[prop]);
			} else {
			    // otherwise add each property to url
			    addToUrl = addToUrl + "&" + prop + "=" + message.data[prop];
			}
		}
		
		if (doRoundTrip) {
		    var wcall=wicketAjaxGet('${url}' + addToUrl, function() { }, function() { });
		}

	}
}
