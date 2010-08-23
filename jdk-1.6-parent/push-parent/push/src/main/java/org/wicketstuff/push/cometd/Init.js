if (!Wicket.Push) {
  
  //Skip javascript json implementation if native browser version is available.
  if (!JSON) {
  /* json2.js 
   * 2008-01-17
   * Public Domain
   * No warranty expressed or implied. Use at your own risk.
   * See http://www.JSON.org/js.html
  */
  if(!this.JSON){JSON=function(){function f(n){return n<10?'0'+n:n;}
  Date.prototype.toJSON=function(){return this.getUTCFullYear()+'-'+
  f(this.getUTCMonth()+1)+'-'+
  f(this.getUTCDate())+'T'+
  f(this.getUTCHours())+':'+
  f(this.getUTCMinutes())+':'+
  f(this.getUTCSeconds())+'Z';};var m={'\b':'\\b','\t':'\\t','\n':'\\n','\f':'\\f','\r':'\\r','"':'\\"','\\':'\\\\'};function stringify(value,whitelist){var a,i,k,l,r=/["\\\x00-\x1f\x7f-\x9f]/g,v;switch(typeof value){case'string':return r.test(value)?'"'+value.replace(r,function(a){var c=m[a];if(c){return c;}
  c=a.charCodeAt();return'\\u00'+Math.floor(c/16).toString(16)+
  (c%16).toString(16);})+'"':'"'+value+'"';case'number':return isFinite(value)?String(value):'null';case'boolean':case'null':return String(value);case'object':if(!value){return'null';}
  if(typeof value.toJSON==='function'){return stringify(value.toJSON());}
  a=[];if(typeof value.length==='number'&&!(value.propertyIsEnumerable('length'))){l=value.length;for(i=0;i<l;i+=1){a.push(stringify(value[i],whitelist)||'null');}
  return'['+a.join(',')+']';}
  if(whitelist){l=whitelist.length;for(i=0;i<l;i+=1){k=whitelist[i];if(typeof k==='string'){v=stringify(value[k],whitelist);if(v){a.push(stringify(k)+':'+v);}}}}else{for(k in value){if(typeof k==='string'){v=stringify(value[k],whitelist);if(v){a.push(stringify(k)+':'+v);}}}}
  return'{'+a.join(',')+'}';}}
  return{stringify:stringify,parse:function(text,filter){var j;function walk(k,v){var i,n;if(v&&typeof v==='object'){for(i in v){if(Object.prototype.hasOwnProperty.apply(v,[i])){n=walk(i,v[i]);if(n!==undefined){v[i]=n;}}}}
  return filter(k,v);}
  if(/^[\],:{}\s]*$/.test(text.replace(/\\./g,'@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,']').replace(/(?:^|:|,)(?:\s*\[)+/g,''))){j=eval('('+text+')');return typeof filter==='function'?walk('',j):j;}
  throw new SyntaxError('parseJSON');}};}();}
  }
  
  org.cometd.JSON.toJSON = JSON.stringify;
  org.cometd.JSON.fromJSON = JSON.parse;
  
  function _setHeaders(xhr, headers) {
    if (headers) {
      for (var headerName in headers) {
        if (headerName.toLowerCase() === 'content-type') {
          continue;
        }
        xhr.setRequestHeader(headerName, headers[headerName]);
      }
    }
  }
  
  
  Wicket.Push = {};
  Wicket.Push.Request = function () {
    that = new Wicket.Ajax.Request();
    that.post = function (packet) {
      this.transport = Wicket.Ajax.getTransport();  
      
      this.log("POST", packet.url);
      
      Wicket.Ajax.invokePreCallHandlers();
      
      var t = this.transport;
      if (t != null) {
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
    };
    return that;
  };
  
  Wicket.Push.LongPollingTransport = function () {
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

if (!Wicket.Push.cometd) {
  Wicket.Push.cometd = new org.cometd.Cometd();
  if (window.WebSocket) {
    Wicket.Push.cometd.registerTransport('websocket', new org.cometd.WebSocketTransport());
  }
  Wicket.Push.cometd.registerTransport('long-polling', new Wicket.Push.LongPollingTransport());
  //TODO: Unsopported
  //Wicket.Push.cometd.registerTransport('callback-polling', new Wicket.Push.CallbackPollingTransport());
  
  Wicket.Push.cometd.configure({
    url : '${cometdServletPath}',
    logLevel : '${logLevel}'
  });
  
  Wicket.Event.addDomReadyEvent(function () {
    Wicket.Push.cometd.handshake();
  });
}
