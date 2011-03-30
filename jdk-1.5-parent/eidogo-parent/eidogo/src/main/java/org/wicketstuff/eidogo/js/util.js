/**
 * EidoGo -- Web-based SGF Editor
 * Copyright (c) 2007, Justin Kramer <jkkramer@gmail.com>
 * Code licensed under AGPLv3:
 * http://www.fsf.org/licensing/licenses/agpl-3.0.html
 *
 * General-purpose utility functions.
 */
 
(function() {

// browser detection    
var ua = navigator.userAgent.toLowerCase();
var uav = (ua.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [])[1];
eidogo.browser = {ua: ua, ver: uav, ie: /msie/.test(ua) && !/opera/.test(ua),
    moz: /mozilla/.test(ua) && !/(compatible|webkit)/.test(ua),
    safari3: /webkit/.test(ua) && parseInt(uav, 10) >= 420};

eidogo.util = {

    byId: function(id) {
        return document.getElementById(id);
    },
    
    makeQueryString: function(params) {
        var qs = "";
        if (params && typeof params == "object") {
            var pairs = [];
            for (var key in params) {
                if (params[key] && params[key].constructor == Array) {
                    for (var i = 0; i < params[key].length; i++) {
                        pairs.push(encodeURIComponent(key) + "=" +
                            encodeURIComponent(params[key]));
                    }
                } else {
                    pairs.push(encodeURIComponent(key) + "=" +
                        encodeURIComponent(params[key]));
                }
            }
            qs = pairs.join("&").replace(/%20/g, "+");
        }
        return qs;
    },
    
    // Adapted from jQuery
    ajax: function(method, url, params, successFn, failureFn, scope, timeout) {
        method = method.toUpperCase();
        var xhr = window.ActiveXObject ?
            new ActiveXObject("Microsoft.XMLHTTP") :
            new XMLHttpRequest();
        var qs = (params && typeof params == "object" ?
            eidogo.util.makeQueryString(params) : null);
        if (qs && method == "GET" ) {
            url += (url.match(/\?/) ? "&" : "?") + qs;
            qs = null;
        }
        xhr.open(method, url, true);
        if (qs) {
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        }
        var requestDone = false;
        var isSafari = /webkit/.test(navigator.userAgent.toLowerCase());
        function httpSuccess(r) {
            try {
                return !r.status && location.protocol == "file:" ||
                (r.status >= 200 && r.status < 300) || r.status == 304 ||
                isSafari && r.status == undefined;
            } catch(e) {}
            return false;
        };
        function handleReadyState(isTimeout) {
            if (!requestDone && xhr && (xhr.readyState == 4 || isTimeout == "timeout")) {
                requestDone = true;
                if (ival) {
                    clearInterval(ival);
                    ival = null;
                }
                var status = isTimeout == "timeout" && "timeout" ||
                    !httpSuccess(xhr) && "error" ||
                    "success";
                if (status == "success")
                    successFn.call(scope, xhr);
                else
                    failureFn.call(scope);
                xhr = null;
            }
        }
        var ival = setInterval(handleReadyState, 13); 
        if (timeout)
            setTimeout(function() {
                if (xhr) {
                    xhr.abort();
                    if(!requestDone)
                        handleReadyState("timeout");
                }
            }, timeout);
        xhr.send(qs);
        return xhr;
    },
    
    // written by Dean Edwards, 2005
    // with input from Tino Zijdel, Matthias Miller, Diego Perini
    // http://dean.edwards.name/weblog/2005/10/add-event/
    // modified for use with EidoGo
    addEventHelper: function(element, type, handler) {
        if (element.addEventListener) {
            element.addEventListener(type, handler, false);
        } else {
            if (!eidogo.util.addEventId) eidogo.util.addEventId = 1;
            // assign each event handler a unique ID
            if (!handler.$$guid) handler.$$guid = eidogo.util.addEventId++;
            // create a hash table of event types for the element
            if (!element.events) element.events = {};
            // create a hash table of event handlers for each element/event pair
            var handlers = element.events[type];
            if (!handlers) {
                handlers = element.events[type] = {};
                // store the existing event handler (if there is one)
                if (element["on" + type]) {
                    handlers[0] = element["on" + type];
                }
            }
            // store the event handler in the hash table
            handlers[handler.$$guid] = handler;
            // assign a global event handler to do all the work
            element["on" + type] = eidogo.util.handleEvent;
        }
    },

    handleEvent: function(event) {
        var returnValue = true;
        // grab the event object (IE uses a global event object)
        event = event || ((this.ownerDocument || this.document || this).parentWindow || window).event;
        // get a reference to the hash table of event handlers
        var handlers = this.events[event.type];
        // execute each event handler
        for (var i in handlers) {
            this.$$handleEvent = handlers[i];
            if (this.$$handleEvent(event) === false) {
                returnValue = false;
            }
        }
        return returnValue;
    },
    
    addEvent: function(el, eventType, handler, arg, override) {
        if (!el) return;
        if (override) {
            handler = handler.bind(arg);
        } else if (arg) {
            // use a closure to pass an extra argument
            var oldHandler = handler;
            handler = function(e) {
                oldHandler(e, arg);
            }
        }
        eidogo.util.addEventHelper(el, eventType, handler);
    },
    
    onClick: function(el, handler, scope) {
        eidogo.util.addEvent(el, "click", handler, scope, true);
    },
    
    getElClickXY: function(e, el, noScroll) {
        // for IE
        if(!e.pageX) {
            e.pageX = e.clientX + (document.documentElement.scrollLeft ||
                document.body.scrollLeft);
            e.pageY = e.clientY + (document.documentElement.scrollTop ||
                document.body.scrollTop);
        }
        var elXY = eidogo.util.getElXY(el, noScroll);
        return [e.pageX - elXY[0], e.pageY - elXY[1]];
    },
    
    stopEvent: function(e) {
        if (!e) return;
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        if (e.preventDefault) {
            e.preventDefault();
        } else {
            e.returnValue = false;
        }
    },
    
    getTarget: function(ev) {
        var t = ev.target || ev.srcElement;
        return (t && t.nodeName && t.nodeName.toUpperCase() == "#TEXT") ?
            t.parentNode : t;
    },
    
    addClass: function(el, cls) {
        if (!cls) return;
        var ca = cls.split(/\s+/);
        for (var i = 0; i < ca.length; i++) {
            if (!eidogo.util.hasClass(el, ca[i]))
                el.className += (el.className ? " " : "") + ca[i];
        }
    },

    removeClass: function(el, cls) {
        var ca = el.className.split(/\s+/);
        var nc = [];
        for (var i = 0; i < ca.length; i++) {
            if (ca[i] != cls)
                nc.push(ca[i]);
        }
        el.className = nc.join(" ");
    },

    hasClass: function(el, cls) {
        var ca = el.className.split(/\s+/);
        for (var i = 0; i < ca.length; i++) {
            if (ca[i] == cls)
                return true;
        }
        return false;
    },
    
    show: function(el, display) {
        display = display || "block";
        if (typeof el == "string") {
            el = eidogo.util.byId(el);
        }
        if (!el) return;
        el.style.display = display;
    },
    
    hide: function(el) {
        if (typeof el == "string") {
            el = eidogo.util.byId(el);
        }
        if (!el) return;
        el.style.display = "none";
    },
    
    getElXY: function(el, noScroll) {
        // TODO: improve caching?
        // if (el._x && el._y) return [el._x, el._y];
        var node = el, elX = 0, elY = 0, parent = el.parentNode, sx = 0, sy = 0;
        while (node) {
            elX += node.offsetLeft;
            elY += node.offsetTop;
            node = node.offsetParent ? node.offsetParent : null;
        }
        while (!noScroll && parent && parent.tagName && !/^body|html$/i.test(parent.tagName)) {
            sx += parent.scrollLeft;
            sy += parent.scrollTop;
            elX -= parent.scrollLeft;
            elY -= parent.scrollTop;
            parent = parent.parentNode;
        }
        // el._x = elX;
        // el._y = elY;
        return [elX, elY, sx, sy];
    },
    
    getElX: function(el) {
        return this.getElXY(el)[0];
    },
    
    getElY: function(el) {
        return this.getElXY(el)[1];
    },
    
    addStyleSheet: function(href) {
        if (document.createStyleSheet) {
            document.createStyleSheet(href);
        } else {
            var link = document.createElement('link');
            link.rel = 'stylesheet';
            link.type = 'text/css';
            link.href = href;
            document.getElementsByTagName("head")[0].appendChild(link);
        }
    },
    
    getPlayerPath: function() {
        var scripts = document.getElementsByTagName('script');
        var scriptPath;
        var script;
        for (var i = 0; script = scripts[i]; i++) {
            if (/(all\.compressed\.js|eidogo\.js)/.test(script.src)) {
                scriptPath = script.src.replace(/\/js\/[^\/]+$/, "");
            }
        }
        return scriptPath;
    },
    
    numProperties: function(obj) {
        var count = 0;
        for (var i in obj) count++;
        return count;
    }
    
};

})();