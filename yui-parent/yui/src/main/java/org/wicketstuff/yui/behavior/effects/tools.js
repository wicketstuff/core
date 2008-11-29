/**
* @fileoverview
* <p>General Tools.</p>
* <p>Now contains a modified version of Douglas Crockford's json.js that doesn't
* mess with the DOM's prototype methods
* http://www.json.org/js.html</p>
* @author Dav Glass <dav.glass@yahoo.com>
* @version 1.0
* @requires YAHOO
* @requires YAHOO.util.Dom
* @requires YAHOO.util.Event
*
* @constructor
* @class General Tools.
*/
YAHOO.Tools = function() {
    keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    /**
    * Moved all regexes to the top level object to cache them.
    * @type Object
    */
    regExs = {
        quotes: /\x22/g,
        startspace: /^\s+/g,
        endspace: /\s+$/g,
        striptags: /<\/?[^>]+>/gi,
        hasbr: /<br/i,
        hasp: /<p>/i,
        rbr: /<br>/gi,
        rbr2: /<br\/>/gi,
        rendp: /<\/p>/gi,
        rp: /<p>/gi,
        base64: /[^A-Za-z0-9\+\/\=]/g,
        syntaxCheck: /^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/
    }

    jsonCodes = {
        '\b': '\\b',
        '\t': '\\t',
        '\n': '\\n',
        '\f': '\\f',
        '\r': '\\r',
        '"' : '\\"',
        '\\': '\\\\'
    }
    return {
        version: '1.0'
    }
}();
/**
* This normalizes getting the height of an element in IE
* @param {String/HTMLElement} elm The element to get the height of
* @returns The Height in pixels
* @type String
*/
YAHOO.Tools.getHeight = function(elm) {
    var elm = $(elm);
    var h = $D.getStyle(elm, 'height');
    if (h == 'auto') {
        elm.style.zoom = 1;
        h = elm.clientHeight + 'px';
    }
    return h;
}
/**
* Get the XY coords required to place the element at the center of the screen
* @param {String/HTMLElement} elm The element to place at the center of the screen
* @returns The XY coords required to place the element at the center of the screen
* @type Array
*/
YAHOO.Tools.getCenter = function(elm) {
    var elm = $(elm);
    var cX = Math.round(($D.getViewportWidth() - parseInt($D.getStyle(elm, 'width'))) / 2);
    var cY = Math.round(($D.getViewportHeight() - parseInt(this.getHeight(elm))) / 2);
    return [cX, cY];
}

/**
* Converts a text string into a DOM object
* @param {String} txt String to convert
* @returns A string to a textNode
*/
YAHOO.Tools.makeTextObject = function(txt) {
    return document.createTextNode(txt);
}
/**
* Takes an Array of DOM objects and appends them as a child to the main Element
* @param {Array} arr Array of elements to append to elm.
* @param {HTMLElement/String} elm A reference or ID to the main Element that the children will be appended to
*/
YAHOO.Tools.makeChildren = function(arr, elm) {
    var elm = $(elm);
    for (var i in arr) {
        _val = arr[i];
        if (typeof _val == 'string') {
            _val = this.makeTxtObject(_val);
        }
        elm.appendChild(_val);
    }
}
/**
* Converts a standard CSS string to a Javascriptable Camel Case variable name
* @param {String} str The CSS string to convert to camel case Javascript String
* Example:<br>
* background-color<br>
* backgroundColor<br><br>
* list-style-type<br>
* listStyleType
*/
YAHOO.Tools.styleToCamel = function(str) {
    var _tmp = str.split('-');
    var _new_style = _tmp[0];
    for (var i = 1; i < _tmp.length; i++) {
        _new_style += _tmp[i].substring(0, 1).toUpperCase() + _tmp[i].substring(1, _tmp[i].length); 
    }
    return _new_style;
}
/**
* Removes " from a given string
* @param {String} str The string to remove quotes from
*/
YAHOO.Tools.removeQuotes = function(str) {
    var checkText = new String(str);
    return String(checkText.replace(regExs.quotes, ''));
}
/**
* Trims starting and trailing white space from a string.
* @param {String} str The string to trim
*/
YAHOO.Tools.trim = function(str) {
    return str.replace(regExs.startspace, '').replace(regExs.endspace, '');
}
/**
* Removes all HTML tags from a string.
* @param {String} str The string to remove HTML from
*/
YAHOO.Tools.stripTags = function(str) {
    return str.replace(regExs.striptags, '');
}
/**
* Returns True/False if it finds BR' or P's
* @param {String} str The string to search
*/
YAHOO.Tools.hasBRs = function(str) {
    return str.match(regExs.hasbr) || str.match(regExs.hasp);
}
/**
* Converts BR's and P's to Plain Text Line Feeds
* @param {String} str The string to search
*/
YAHOO.Tools.convertBRs2NLs = function(str) {
    return str.replace(regExs.rbr, "\n").replace(regExs.rbr2, "\n").replace(regExs.rendp, "\n").replace(regExs.rp, "");
}
/**
* Repeats a string n number of times
* @param {String} str The string to repeat
* @param {Integer} repeat Number of times to repeat it
* @returns Repeated string
* @type String
*/
YAHOO.Tools.stringRepeat = function(str, repeat) {
    return new Array(repeat + 1).join(str);
}
/**
* Reverses a string
* @param {String} str The string to reverse
* @returns Reversed string
* @type String
*/
YAHOO.Tools.stringReverse = function(str) {
    var new_str = '';
    for (i = 0; i < str.length; i++) {
        new_str = new_str + str.charAt((str.length -1) -i);
    }
    return new_str;
}
/**
* printf function written in Javascript<br>
* <pre>var test = "You are viewing messages {0} - {1} out of {2}";
* YAHOO.Tools.printf(test, '5', '25', '500');</pre><br>
* This will return a string like:<br>
* "You are view messages 5 - 25 out of 500"<br>
* Patched provided by: Peter Foti <foti-1@comcast.net><br>
* @param {String} string
* @returns Parsed String
* @type String
*/
YAHOO.Tools.printf = function() {
    var num = arguments.length;
    var oStr = arguments[0];
    
    for (var i = 1; i < num; i++) {
        var pattern = "\\{" + (i-1) + "\\}";
        var re = new RegExp(pattern, "g");
        oStr = oStr.replace(re, arguments[i]);
    }
    return oStr;
}
/**
* Trims starting and trailing white space from a string.
* @param {HTMLElement/Array/String} el Single element, array of elements or id string to apply the style string to
* @param {String} str The CSS string to apply to the elements
* Example:
* color: black; text-decoration: none; background-color: yellow;
*/
YAHOO.Tools.setStyleString = function(el, str) {
    var _tmp = str.split(';');
    for (x in _tmp) {
        if (x) {
            __tmp = YAHOO.Tools.trim(_tmp[x]);
            __tmp = _tmp[x].split(':');
            if (__tmp[0] && __tmp[1]) {
                var _attr = YAHOO.Tools.trim(__tmp[0]);
                var _val = YAHOO.Tools.trim(__tmp[1]);
                if (_attr && _val) {
                    if (_attr.indexOf('-') != -1) {
                        _attr = YAHOO.Tools.styleToCamel(_attr);
                    }
                    $D.setStyle(el, _attr, _val);
                }
            }
        }
    }
}
/**
* Gets the currently selected text
* @param {Object} _document Optional. Reference to the document object
* @param {Object} _window Optional. Reference to the window object
* Both parameters are optional, but if you give one you need to give both.<br>
* The reason for the parameters is if you are dealing with an iFrame or FrameSet,
* you need to specify the document and the window of the frame you want to get the selection for
*/
YAHOO.Tools.getSelection = function(_document, _window) {
    if (!_document) { _document = document; }
    if (!_window) { _window = window; }
    if (_document.selection) {
		return _document.selection;
    }
	return _window.getSelection();
}
/**
* Remove the element from the document.
* @param {HTMLElement/Array/String} el Single element, array of elements or id string to remove from the document
* This function needs to be extended to remove all of the child elements & their listeners.
*/
YAHOO.Tools.removeElement = function(el) {
    if (!(el instanceof Array)) {
        el = new Array($(el));
    }
    for (var i = 0; i < el.length; i++) {
        if (el[i].parentNode) {
            el[i].parentNode.removeChild(el);
        }
    }
}
/**
* Set a cookie.
* @param {String} name The name of the cookie to be set
* @param {String} value The value of the cookie
* @param {String} expires A valid Javascript Date object
* @param {String} path The path of the cookie (Deaults to /)
* @param {String} domain The domain to attach the cookie to
* @param {Booleen} secure Booleen True or False
*/
YAHOO.Tools.setCookie = function(name, value, expires, path, domain, secure) {
     var argv = arguments;
     var argc = arguments.length;
     var expires = (argc > 2) ? argv[2] : null;
     var path = (argc > 3) ? argv[3] : '/';
     var domain = (argc > 4) ? argv[4] : null;
     var secure = (argc > 5) ? argv[5] : false;
     document.cookie = name + "=" + escape (value) +
       ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
       ((path == null) ? "" : ("; path=" + path)) +
       ((domain == null) ? "" : ("; domain=" + domain)) +
       ((secure == true) ? "; secure" : "");
}

/**
* Get the value of a cookie.
* @param {String} name The name of the cookie to get
*/
YAHOO.Tools.getCookie = function(name) {
    var dc = document.cookie;
    var prefix = name + '=';
    var begin = dc.indexOf('; ' + prefix);
    if (begin == -1) {
        begin = dc.indexOf(prefix);
        if (begin != 0) return null;
    } else {
        begin += 2;
    }
    var end = document.cookie.indexOf(';', begin); 
    if (end == -1) {
        end = dc.length;
    }
    return unescape(dc.substring(begin + prefix.length, end));
}
/**
* Delete a cookie
* @param {String} name The name of the cookie to delete.
*/
YAHOO.Tools.deleteCookie = function(name, path, domain) {
    if (getCookie(name)) {
        document.cookie = name + '=' + ((path) ? '; path=' + path : '') + ((domain) ? '; domain=' + domain : '') + '; expires=Thu, 01-Jan-70 00:00:01 GMT';
    }
}
/**
* Object based Browser Engine Detection<br>
* The returned object will look like:<br>
* <pre>
*   obj {
*       ua: 'Full UserAgent String'
*       opera: boolean
*       safari: boolean
*       gecko: boolean
*       msie: boolean
*       version: string
*   }
* </pre>
* @return Browser Information Object
* @type Object
*/
YAHOO.Tools.getBrowserEngine = function() {
    var opera = ((window.opera && window.opera.version) ? true : false);
    var safari = ((navigator.vendor && navigator.vendor.indexOf('Apple') != -1) ? true : false);
    var gecko = ((document.getElementById && !document.all && !opera && !safari) ? true : false);
    var msie = ((window.ActiveXObject) ? true : false);
    var version = false;
    if (msie) {
        /**
        * This checks for the maxHeight style property.
        * I.E. 7 has this
        */
        if (typeof document.body.style.maxHeight != "undefined") {
            version = '7';
        } else {
            /**
            * Fall back to 6 (might need to find a 5.5 object too...).
            */
            version = '6';
        }
    }
    if (opera) {
        /**
        * The window.opera object has a method called version();
        * Here we only grab the first 2 parts of the dotted string to get 9.01,  9.02, etc..
        */
        var tmp_version = window.opera.version().split('.');
        version = tmp_version[0] + '.' + tmp_version[1];
    }
    if (gecko) {
        /**
        * FireFox 2 has a function called registerContentHandler();
        */
        if (navigator.registerContentHandler) {
            version = '2';
        } else {
            version = '1.5';
        }
        /**
        * This should catch all pre Firefox 1.5 browsers
        */
        if ((navigator.vendorSub) && !version) {
            version = navigator.vendorSub;
        }
    }
    if (safari) {
        try {
            /**
            * Safari 1.3+ supports the console method
            */
            if (console) {
                /**
                * Safari 2+ supports the onmousewheel event
                */
                if ((window.onmousewheel !== 'undefined') && (window.onmousewheel === null)) {
                    version = '2';
                } else {
                    version = '1.3';
                }
            }
        } catch (e) {
            /**
            * Safari 1.2 does not support the console method
            */
            version = '1.2';
        }
    }
    /**
    * Return the Browser Object
    * @type Object
    */
    var browsers = {
        ua: navigator.userAgent,
        opera: opera,
        safari: safari,
        gecko: gecko,
        msie: msie,
        version: version
    }
    return browsers;
}
/**
* User Agent Based Browser Detection<br>
* This function uses the userAgent string to get the browsers information.<br>
* The returned object will look like:<br>
* <pre>
*   obj {
*       ua: 'Full UserAgent String'
*       opera: boolean
*       safari: boolean
*       firefox: boolean
*       mozilla: boolean
*       msie: boolean
*       mac: boolean
*       win: boolean
*       unix: boolean
*       version: string
*       flash: version string
*   }
* </pre><br>
* @return Browser Information Object
* @type Object
*/
YAHOO.Tools.getBrowserAgent = function() {
    var ua = navigator.userAgent.toLowerCase();
    var opera = ((ua.indexOf('opera') != -1) ? true : false);
    var safari = ((ua.indexOf('safari') != -1) ? true : false);
    var firefox = ((ua.indexOf('firefox') != -1) ? true : false);
    var msie = ((ua.indexOf('msie') != -1) ? true : false);
    var mac = ((ua.indexOf('mac') != -1) ? true : false);
    var unix = ((ua.indexOf('x11') != -1) ? true : false);
    var win = ((mac || unix) ? false : true);
    var version = false;
    var mozilla = false;
    //var flash = this.checkFlash();
    if (!firefox && !safari && (ua.indexOf('gecko') != -1)) {
        mozilla = true;
        var _tmp = ua.split('/');
        version = _tmp[_tmp.length - 1].split(' ')[0];
    }
    if (firefox) {
        var _tmp = ua.split('/');
        version = _tmp[_tmp.length - 1].split(' ')[0];
    }
    if (msie) {
        version = ua.substring((ua.indexOf('msie ') + 5)).split(';')[0];
    }
    if (safari) {
        /**
        * Safari doesn't report a string, have to use getBrowserEngine to get it
        */
        version = this.getBrowserEngine().version;
    }
    if (opera) {
        version = ua.substring((ua.indexOf('opera/') + 6)).split(' ')[0];
    }

    /**
    * Return the Browser Object
    * @type Object
    */
    var browsers = {
        ua: navigator.userAgent,
        opera: opera,
        safari: safari,
        firefox: firefox,
        mozilla: mozilla,
        msie: msie,
        mac: mac,
        win: win,
        unix: unix,
        version: version//,
        //flash: flash
    }
    return browsers;
}
/**
* Check if Flash is enabled and return the version number
* @return Version number or false on error
* @type String
*/
YAHOO.Tools.checkFlash = function() {
    var br = this.getBrowserEngine();
    if (br.msie) {
        try {
            // version will be set for 7.X or greater players
            var axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
            var versionStr = axo.GetVariable("$version");
            var tempArray = versionStr.split(" ");  // ["WIN", "2,0,0,11"]
            var tempString = tempArray[1];           // "2,0,0,11"
            var versionArray = tempString.split(",");  // ['2', '0', '0', '11']
            var flash = versionArray[0];
        } catch (e) {
        }
    } else {
        var flashObj = null;
        var tokens, len, curr_tok;
        if (navigator.mimeTypes && navigator.mimeTypes['application/x-shockwave-flash']) {
            flashObj = navigator.mimeTypes['application/x-shockwave-flash'].enabledPlugin;
        }
        if (flashObj == null) {
            flash = false;
        } else {
            tokens = navigator.plugins['Shockwave Flash'].description.split(' ');
            len = tokens.length;
            while(len--) {
                curr_tok = tokens[len];
                if(!isNaN(parseInt(curr_tok))) {
                    hasVersion = curr_tok;
                    flash = hasVersion;
                    break;
                }
            }
        }
    }
    return flash;
}
/**
* Set Mass Attributes on an Element
* @param {Object} attrObj Object containing the attributes to set.
* @param {HTMLElement/String} elm The element you want to apply the attribute to
* Supports adding listeners and setting style from a CSS style string.<br>
*/
YAHOO.Tools.setAttr = function(attrsObj, elm) {
    if (typeof elm == 'string') {
        elm = $(elm);
    }
    for (var i in attrsObj) {
        switch (i.toLowerCase()) {
            case 'listener':
               if (attrsObj[i] instanceof Array) {
                   var ev = attrsObj[i][0];
                   var func = attrsObj[i][1];
                   var base = attrsObj[i][2];
                   var scope = attrsObj[i][3];
                   $E.addListener(elm, ev, func, base, scope);
               }
               break;
           case 'classname':
           case 'class':
               elm.className = attrsObj[i];
               break;
           case 'style':
               YAHOO.Tools.setStyleString(elm, attrsObj[i]);
               break;
           default:
               elm.setAttribute(i, attrsObj[i]);
               break;
       }
   }
}
/**
* Usage:<br>
* <pre><code>
* div = YAHOO.util.Dom.create('div', 'Single DIV. This is some test text.', {
*           className:'test1',
*           style:'font-size: 20px'
*       }
* );
* test1.appendChild(div);
* <br><br>- or -<br><br>
* div = YAHOO.util.Dom.create('div', {className:'test2',style:'font-size:11px'}, 
*        [YAHOO.util.Dom.create('p', {
*            style:'border: 1px solid red; color: blue',
*            listener: ['click', test]
*           },
*           'This is a P inside of a DIV both styled.')
*       ]
*);
*    test2.appendChild(div);
*
* </code></pre>
* @param {String} tagName Tag name to create
* @param {Object} attrs Element attributes in object notation
* @param {Array} children Array of children to append to the created element
* @param {String} txt Text string to insert into the created element
* @returns A reference to the newly created element
* @type HTMLReference
*/
YAHOO.Tools.create = function(tagName) {
    tagName = tagName.toLowerCase();
    elm = document.createElement(tagName);
    var txt = false;
    var attrsObj = false;

    if (!elm) { return false; }
    
    for (var i = 1; i < arguments.length; i++) {
        txt = arguments[i];
        if (typeof txt == 'string') {
            _txt = YAHOO.Tools.makeTextObject(txt);
            elm.appendChild(_txt);
        } else if (txt instanceof Array) {
            YAHOO.Tools.makeChildren(txt, elm);
        } else if (typeof txt == 'object') {
            //_makeStyleObject(txt, elm);
            YAHOO.Tools.setAttr(txt, elm);
        }
    }
    return elm;
}
/**
* Inserts an HTML Element after another in the DOM Tree.
* @param    {HTMLElement}   elm The element to insert
* @param    {HTMLElement}    curNode The element to insert it before
*/
YAHOO.Tools.insertAfter = function(elm, curNode) {
    if (curNode.nextSibling) {
        curNode.parentNode.insertBefore(elm, curNode.nextSibling);
    } else {
        curNode.parentNode.appendChild(elm);
    }
}
/**
* Validates that the value passed is in the Array passed.
* @param    {Array}   arr The Array to search (haystack)
* @param    {String}    val The value to search for (needle)
* @returns True if the value is found
* @type Boolean
*/
YAHOO.Tools.inArray = function(arr, val) {
    if (arr instanceof Array) {
        for (var i = (arr.length -1); i >= 0; i--) {
            if (arr[i] === val) {
                return true;
            }
        }
    }
    return false;
}


/**
* Validates that the value passed in is a boolean.
* @param    {Object}    str The value to validate
* @return true, if the value is valid
* @type Boolean
*/
YAHOO.Tools.checkBoolean = function(str) {
    return ((typeof str == 'boolean') ? true : false);
}

/**
* Validates that the value passed in is a number.
* @param    {Object}    str The value to validate
* @return true, if the value is valid
* @type Boolean
*/
YAHOO.Tools.checkNumber = function(str) {
    return ((isNaN(str)) ? false : true);
}

/**
* Divide your desired pixel width by 13 to find em width. Multiply that value by 0.9759 for IE via *width.
* @param    {Integer}   size The pixel size to convert to em.
* @return Object of sizes (2) {msie: size, other: size }
* @type Object
*/
YAHOO.Tools.PixelToEm = function(size) {
    var data = {};
    var sSize = (size / 13);
    data.other = (Math.round(sSize * 100) / 100);
    data.msie = (Math.round((sSize * 0.9759) * 100) / 100);
    return data;
}

/**
* Return a string of CSS statements for this pixel size in ems
* @param    {Integer}   size The pixel size to convert to em.
* @param    {String}    prop The property to apply the style to.
* @return String of CSS style statements (width:46.15em;*width:45.04em;min-width:600px;)
* @type String
*/
YAHOO.Tools.PixelToEmStyle = function(size, prop) {
    var data = '';
    var prop = ((prop) ? prop.toLowerCase() : 'width');
    var sSize = (size / 13);
    data += prop + ':' + (Math.round(sSize * 100) / 100) + 'em;';
    data += '*' + prop + ':' + (Math.round((sSize * 0.9759) * 100) / 100) + 'em;';
    if ((prop == 'width') || (prop == 'height')) {
        data += 'min-' + prop + ':' + size + 'px;';
    }
    return data;
}

/**
* Base64 Encodes a string
* @param    {String}    str The string to base64 encode.
* @return Base64 Encoded String
* @type String
*/
YAHOO.Tools.base64Encode = function(str) {
    var data = "";
    var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
    var i = 0;

    do {
        chr1 = str.charCodeAt(i++);
        chr2 = str.charCodeAt(i++);
        chr3 = str.charCodeAt(i++);

        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;

        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }

        data = data + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
    } while (i < str.length);
   
    return data;
}
/**
* Base64 Dncodes a string
* @param    {String}    str The base64 encoded string to decode.
* @return The decoded String
* @type String
*/
YAHOO.Tools.base64Decode = function(str) {
    var data = "";
    var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
    var i = 0;

    // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
    str = str.replace(regExs.base64, "");

    do {
        enc1 = keyStr.indexOf(str.charAt(i++));
        enc2 = keyStr.indexOf(str.charAt(i++));
        enc3 = keyStr.indexOf(str.charAt(i++));
        enc4 = keyStr.indexOf(str.charAt(i++));

        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;

        data = data + String.fromCharCode(chr1);

        if (enc3 != 64) {
            data = data + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
            data = data + String.fromCharCode(chr3);
        }
    } while (i < str.length);

    return data;
}

/**
* Parses a Query String, if one is not provided, it will look in location.href<br>
* NOTE: This function will also handle test[] vars and convert them to an array inside of the return object.<br>
* This now supports #hash vars, it will return it in the object as Obj.hash
* @param    {String}    str The string to parse as a query string
* @return An object of the parts of the parsed query string
* @type Object
*/
YAHOO.Tools.getQueryString = function(str) {
    var qstr = {};
    if (!str) {
        var str = location.href.split('?');
        if (str.length != 2) {
            str = ['', location.href];
        }
    } else {
        var str = ['', str];
    }
    if (str[1].match('#')) {
        var _tmp = str[1].split('#');
        qstr.hash = _tmp[1];
        str[1] = _tmp[0];
    }
    if (str[1]) {
        str = str[1].split('&');
        if (str.length) {
            for (var i = 0; i < str.length; i++) {
                var part = str[i].split('=');
                if (part[0].indexOf('[') != -1) {
                    if (part[0].indexOf('[]') != -1) {
                        //Array
                        var arr = part[0].substring(0, part[0].length - 2);
                        if (!qstr[arr]) {
                            qstr[arr] = [];
                        }
                        qstr[arr][qstr[arr].length] = part[1];
                    } else {
                        //Object
                        var arr = part[0].substring(0, part[0].indexOf('['));
                        var data = part[0].substring((part[0].indexOf('[') + 1), part[0].indexOf(']'));
                        if (!qstr[arr]) {
                            qstr[arr] = {};
                        }
                        //Object
                        qstr[arr][data] = part[1];
                    }
                } else {
                    qstr[part[0]] = part[1];
                }
            }
        }
    }
    return qstr;
}
/**
* Parses a Query String Var<br>
* NOTE: This function will also handle test[] vars and convert them to an array inside of the return object.
* @param    {String}    str The var to get from the query string
* @return The value of the var in the querystring.
* @type String/Array
*/
YAHOO.Tools.getQueryStringVar = function(str) {
    var qs = this.getQueryString();
    if (qs[str]) {
        return qs[str];
    } else {
        return false;
    }
}


/**
* Function to pad a date with a beginning 0 so 1 becomes 01, 2 becomes 02, etc..
* @param {String} n The string to pad
* @returns Zero padded string
* @type String
*/
YAHOO.Tools.padDate = function(n) {
    return n < 10 ? '0' + n : n;
}

/**
* Converts a string to a JSON string
* @param {String} str Converts a string to a JSON string
* @returns JSON Encoded string
* @type String
*/
YAHOO.Tools.encodeStr = function(str) {
    if (/["\\\x00-\x1f]/.test(str)) {
        return '"' + str.replace(/([\x00-\x1f\\"])/g, function(a, b) {
            var c = jsonCodes[b];
            if(c) {
                return c;
            }
            c = b.charCodeAt();
            return '\\u00' +
                Math.floor(c / 16).toString(16) +
                (c % 16).toString(16);
        }) + '"';
    }
    return '"' + str + '"';
}
/**
* Converts an Array to a JSON string
* @param {Array} arr Converts an Array to a JSON string
* @returns JSON encoded string
* @type String
*/
YAHOO.Tools.encodeArr = function(arr) {
    var a = ['['], b, i, l = arr.length, v;
        for (i = 0; i < l; i += 1) {
            v = arr[i];
            switch (typeof v) {
                case 'undefined':
                case 'function':
                case 'unknown':
                    break;
                default:
                    if (b) {
                        a.push(',');
                    }
                    a.push(v === null ? "null" : YAHOO.Tools.JSONEncode(v));
                    b = true;
            }
        }
        a.push(']');
        return a.join('');
}
/**
* Converts a Date object to a JSON string
* @param {Object} d Converts a Date object to a JSON string
* @returns JSON encoded Date string
* @type String
*/
YAHOO.Tools.encodeDate = function(d) {
    return '"' + d.getFullYear() + '-' + YAHOO.Tools.padDate(d.getMonth() + 1) + '-' + YAHOO.Tools.padDate(d.getDate()) + 'T' + YAHOO.Tools.padDate(d.getHours()) + ':' + YAHOO.Tools.padDate(d.getMinutes()) + ':' + YAHOO.Tools.padDate(d.getSeconds()) + '"';
}

/**
* Fixes the JSON date format
* @param {String} dateStr JSON encoded date string (YYYY-MM-DDTHH:MM:SS)
* @returns Date Object
* @type Object
*/
YAHOO.Tools.fixJSONDate = function(dateStr) {
    var tmp = dateStr.split('T');
    var fixedDate = dateStr;
    if (tmp.length == 2) {
        var tmpDate = tmp[0].split('-');
        if (tmpDate.length == 3) {
            fixedDate = new Date(tmpDate[0], (tmpDate[1] - 1), tmpDate[2]);
            var tmpTime = tmp[1].split(':');
            if (tmpTime.length == 3) {
                fixedDate.setHours(tmpTime[0], tmpTime[1], tmpTime[2]);
            }
        }
    }
    return fixedDate;
}

/**
* Encode a Javascript Object/Array into a JSON string
* @param {String/Object/Array} o Converts the object to a JSON string
* @returns JSON String
* @type String
*/
YAHOO.Tools.JSONEncode = function(o) {
    if ((typeof o == 'undefined') || (o === null)) {
        return 'null';
    } else if (o instanceof Array) {
        return YAHOO.Tools.encodeArr(o);
    } else if (o instanceof Date) {
        return YAHOO.Tools.encodeDate(o);
    } else if (typeof o == 'string') {
        return YAHOO.Tools.encodeStr(o);
    } else if (typeof o == 'number') {
        return isFinite(o) ? String(o) : "null";
    } else if (typeof o == 'boolean') {
        return String(o);
    } else {
        var a = ['{'], b, i, v;
        for (var i in o) {
            //if (o.hasOwnProperty(i)) {
                v = o[i];
                switch (typeof v) {
                    case 'undefined':
                    case 'function':
                    case 'unknown':
                        break;
                    default:
                        if (b) {
                            a.push(',');
                        }
                        a.push(YAHOO.Tools.JSONEncode(i), ':', ((v === null) ? "null" : YAHOO.Tools.JSONEncode(v)));
                        b = true;
                }
            //}
        }
        a.push('}');
        return a.join('');
    }
}
/**
* Converts/evals a JSON string into a native Javascript object
* @param {String} json Converts the JSON string back into the native object
* @param {Booleen} autoDate Try to autofix date objects 
* @returns eval'd object
* @type Object/Array/String
*/
YAHOO.Tools.JSONParse = function(json, autoDate) {
    var autoDate = ((autoDate) ? true : false);
    try {
        if (regExs.syntaxCheck.test(json)) {
            var j = eval('(' + json + ')');
            if (autoDate) {
                function walk(k, v) {
                    if (v && typeof v === 'object') {
                        for (var i in v) {
                            if (v.hasOwnProperty(i)) {
                                v[i] = walk(i, v[i]);
                            }
                        }
                    }
                    if (k.toLowerCase().indexOf('date') >= 0) {
                        return YAHOO.Tools.fixJSONDate(v);
                    } else {
                        return v;
                    }
                }
                return walk('', j);
            } else {
                return j;
            }
        }
    } catch(e) {
        console.log(e);
    }
    throw new SyntaxError("parseJSON");
}


/*
* Try to catch the developers that use the wrong case 8-)
*/
YAHOO.tools = YAHOO.Tools;
YAHOO.TOOLS = YAHOO.Tools;
YAHOO.util.Dom.create = YAHOO.Tools.create;
/*
* Smaller Code
*/

$A = YAHOO.util.Anim;
$E = YAHOO.util.Event;
$D = YAHOO.util.Dom;
$T = YAHOO.Tools;
$ = YAHOO.util.Dom.get;
$$ = YAHOO.util.Dom.getElementsByClassName;
