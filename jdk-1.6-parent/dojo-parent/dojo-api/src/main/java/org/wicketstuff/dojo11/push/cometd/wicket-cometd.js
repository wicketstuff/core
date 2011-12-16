
if (typeof(WicketDojo) == "undefined")
	WicketDojo = { };

if (typeof(WicketDojo.Cometd) == "undefined")
	WicketDojo.Cometd = { };

WicketDojo.Cometd._initCallbacks = new Array()
	
WicketDojo.Cometd.init = dojo.hitch(WicketDojo.Cometd, function(path) {
	console.debug("init cometd", path)
	if (dojox.cometd.state() == dojox.cometd.DISCONNECTED) {
		dojox.cometd.init(path)
	}
	
	if (this._initCallbacks.length > 0) {
		console.log("start cometd init batch with " + this._initCallbacks.length + " callbacks")
		dojox.cometd.startBatch()
		while (this._initCallbacks.length > 0) {
			this._initCallbacks.shift()()
		}
		dojox.cometd.endBatch()
		console.log("end cometd init batch")
	}
})

WicketDojo.Cometd.addOnInit = dojo.hitch(WicketDojo.Cometd, function(callback) {
	if (callback) this._initCallbacks.push(callback)
})

WicketDojo.Cometd._eval = function(message) {
	//console.debug("eval", message)
	evilscript = message.data['evalscript']
	if (evilscript)	eval(evilscript);
	return false;
}

WicketDojo.Cometd._callback = function(message, url) {
	//console.debug("callback", message, url)
	if (message.data.proxy == "true"){
		var addToUrl = "" 
		for (prop in message.data){
			addToUrl = addToUrl + "&" + prop + "=" + message.data[prop];
		}
		
		var wcall=wicketAjaxGet(url + addToUrl, function() { }, function() { });
	}
}