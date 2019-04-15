if (!Wicket.Push){

  function _setHeaders(xhr, headers){
    if (headers){
      for (var headerName in headers){
          if (headerName.toLowerCase() === 'content-type') continue;
          xhr.setRequestHeader(headerName, headers[headerName]);
      }
    }
  }

  Wicket.Push = {};
  Wicket.Push.Request = function(){
    that = new Wicket.Ajax.Request();
    that.post = function (packet){
      this.transport = Wicket.Ajax.getTransport();  
      this.log("POST", packet.url);
      Wicket.Ajax.invokePreCallHandlers();
      var t = this.transport;
      if (t != null){
        t.open("POST", packet.url, packet.sync !== true);
        t.onreadystatechange = this.stateChangeCallback.bind(this);
        t.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        _setHeaders(t, packet.headers);
        t.send(packet.body);
        return true;
      } else {
        this.failure();
        return false;
      }
    }
    return that;
  }

  Wicket.Push.LongPollingTransport = function(){
    var _super = new org.cometd.LongPollingTransport();
    var that = org.cometd.Transport.derive(_super);
    that.xhrSend = function(packet) {
      var req = new Wicket.Push.Request();
      req.initialize(packet.url, packet.onSuccess, false, false, packet.onError);
      req.post(packet);
    }
    return that;
  }

}

if (!Wicket.Push.cometd){
  Wicket.Push.cometd = new org.cometd.CometD();

  if (window.WebSocket) {
    Wicket.Push.cometd.websocketEnabled = true
    Wicket.Push.cometd.registerTransport('websocket', new org.cometd.WebSocketTransport());
  }

  Wicket.Push.cometd.registerTransport('long-polling', new Wicket.Push.LongPollingTransport());

  //TODO: Unsupported
  //Wicket.Push.cometd.registerTransport('callback-polling', new Wicket.Push.CallbackPollingTransport());

  Wicket.Push.cometd.configure({
    url: ( ( ${isServerWebSocketTransport} && window.WebSocket ) ? 
    		(window.location.protocol == 'https:' ? 'wss://' : 'ws://') + window.location.host
    		: ''
    	 ) + '${cometdServletPath}',
    logLevel: '${logLevel}'
  });

  Wicket.Event.add(window, "domready", function(){
    Wicket.Push.cometd.handshake();
  });
}
