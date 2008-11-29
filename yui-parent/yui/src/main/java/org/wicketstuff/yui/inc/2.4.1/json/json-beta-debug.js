/*
Copyright (c) 2007, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.net/yui/license.txt
version: 2.4.1
*/
/**
 * Provides methods to parse JSON strings and convert objects to JSON strings.
 * @module json
 * @requires yahoo
 * @class YAHOO.lang.JSON
 * @static
 */
YAHOO.namespace('lang');
YAHOO.lang.JSON = {

    /**
     * Parse a JSON string, returning the native JavaScript representation.
     * Only minor modifications from http://www.json.org/json.js.
     * @param s {string} JSON string data
     * @param filter {function} (optional) function(k,v) passed each key value pair of object literals, allowing pruning or altering values
     * @return {MIXED} the native JavaScript representation of the JSON string
     * @throws SyntaxError
     * @method parse
     * @static
     * @public
     */
    parse : function (s,filter) {
        var j;

        function walk(k, v) {
            var i, n;
            if (v && typeof v === 'object') {
                for (i in v) {
                    if (YAHOO.lang.hasOwnProperty.apply(v, [i])) {
                        n = walk(i, v[i]);
                        if (n !== undefined) {
                            v[i] = n;
                        }
                    }
                }
            }
            return filter(k, v);
        }


// Parsing happens in three stages. In the first stage, we run the text against
// a regular expression which looks for non-JSON characters. We are especially
// concerned with '()' and 'new' because they can cause invocation, and '='
// because it can cause mutation. But just to be safe, we will reject all
// unexpected characters.

// We split the first stage into 4 regexp operations in order to work around
// crippling deficiencies in IE's and Safari's regexp engines. First we replace
// all backslash pairs with '@' (a non-JSON character). Second, we replace all
// simple value tokens with ']' characters. Third, we delete all open brackets
// that follow a colon or comma or that begin the text. Finally, we look to see
// that the remaining characters are only whitespace or ']' or ',' or ':' or '{'
// or '}'. If that is so, then the text is safe for eval.

        if (/^[\],:{}\s]*$/.test(s.replace(/\\./g, '@').
                replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(:?[eE][+\-]?\d+)?/g, ']').
                replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

// In the second stage we use the eval function to compile the text into a
// JavaScript structure. The '{' operator is subject to a syntactic ambiguity
// in JavaScript: it can begin a block or an object literal. We wrap the text
// in parens to eliminate the ambiguity.

            j = eval('(' + s + ')');

// In the optional third stage, we recursively walk the new structure, passing
// each name/value pair to a filter function for possible transformation.

            return typeof filter === 'function' ? walk('', j) : j;
        }

// If the text is not JSON parseable, then a SyntaxError is thrown.

        throw new SyntaxError('parseJSON');
    },


    /**
     * Converts an arbitrary value to a JSON string representation.
     * Cyclical object or array references are replaced with null.
     * If a whitelist is provided, only matching object keys will be included.
     * If a depth limit is provided, objects and arrays at that depth will
     * be stringified as empty.
     * @param o {MIXED} any arbitrary object to convert to JSON string
     * @param w {Array} (optional) whitelist of acceptable object keys to include
     * @param d {number} (optional) depth limit to recurse objects/arrays (practical minimum 1)
     * @return {string} JSON string representation of the input
     * @method stringify
     * @static
     * @public
     */
    stringify : function (o,w,d) {

        var l = YAHOO.lang,

            // Regex used to encode strings as safe JSON values
            str_re = /["\\\x00-\x1f]/g,

            // Character substitution map used by regex to prepare strings
            m = {
                '\b': '\\b',
                '\t': '\\t',
                '\n': '\\n',
                '\f': '\\f',
                '\r': '\\r',
                '"' : '\\"',
                '\\': '\\\\'
            },

            // Processing stack used to prevent cyclical references
            pstack  = [];


        /**
        * Encode odd characters.  Translated characters are cached.
        * @private
        */
        function _encodeChar(c) {
            if (!m[c]) {
                var a = c.charCodeAt();
                m[c] = '\\u00' + Math.floor(a / 16).toString(16) +
                                           (a % 16).toString(16);
            }
            return m[c];
        }

        /**
         * zero pad single digits in dates.
         * @private
         */
        function _zeroPad(v) {
            return v < 10 ? '0' + v : v;
        }

        /**
         * Wrap string values and object keys in double quotes after replacing
         * any odd characters.
         * @private
         */
        function _string(o) {
            return '"' + o.replace(str_re, _encodeChar) + '"';
        }
    
        /**
         * Worker function.  Fork behavior on data type and recurse objects and
         * arrays per the configured depth.
         * @private
         */
        function _stringify(o,w,d) {
            var t = typeof o,
                i,len,j, // array iteration
                k,v,     // object iteration
                vt,      // typeof v during iteration
                a;       // composition array for performance over string concat

            // String
            if (t === 'string') {
                return _string(o);
            }

            // native boolean and Boolean instance
            if (t === 'boolean' || o instanceof Boolean) {
                return String(o);
            }

            // native number and Number instance
            if (t === 'number' || o instanceof Number) {
                return isFinite(o) ? String(o) : 'null';
            }

            // Date
            if (o instanceof Date) {
                return ['"',         o.getUTCFullYear(),  '-',
                            _zeroPad(o.getUTCMonth() + 1),'-',
                            _zeroPad(o.getUTCDate()),     'T',
                            _zeroPad(o.getUTCHours()),    ':',
                            _zeroPad(o.getUTCMinutes()),  ':',
                            _zeroPad(o.getUTCSeconds()),  'Z"'].join('');
            }

            // Array
            if (l.isArray(o)) {
                // Check for cyclical references
                for (i = 0, len = pstack.length; i < len; ++i) {
                    if (pstack[i] === o) {
                        return 'null';
                    }
                }

                // Add the array to the processing stack
                pstack[pstack.length] = o;

                a = [];
                // Only recurse if we're above depth config
                if (d > 0) {
                    for (i = 0, len = o.length; i < len; ++i) {
                        a[i] = _stringify(o[i],w,d-1);
                    }
                }

                // remove the array from the stack
                pstack.pop();

                return '[' + a.join(',') + ']';
            }

            // Object
            if (t === 'object' && o) {
                // Check for cyclical references
                for (i = 0, len = pstack.length; i < len; ++i) {
                    if (pstack[i] === o) {
                        return 'null';
                    }
                }

                // Add the object to the  processing stack
                pstack[pstack.length] = o;

                a = [];
                // Only recurse if we're above depth config
                if (d > 0) {

                    // If whitelist provided, take only those keys
                    if (w) {
                        for (i = 0, j = 0, len = w.length; i < len; ++i) {
                            v = o[w[i]];
                            vt = typeof v;

                            // Omit invalid values
                            if (vt !== 'undefined' && vt !== 'function') {
                                a[j++] = _string(w[i]) + ':' + _stringify(v,w,d-1);
                            }
                        }

                    // Otherwise, take all valid object properties
                    // omitting the prototype chain properties
                    } else {
                        j = 0;
                        for (k in o) {
                            if (typeof k === 'string' && l.hasOwnProperty(o,k)) {
                                v = o[k];
                                vt = typeof v;
                                if (vt !== 'undefined' && vt !== 'function') {
                                    a[j++] = _string(k) + ':' + _stringify(v,w,d-1);
                                }
                            }
                        }
                    }
                }

                // Remove the object from processing stack
                pstack.pop();

                return '{' + a.join(',') + '}';
            }

            return 'null';
        }

        // process the input
        d = d >= 0 ? d : 1/0;  // Default depth to POSITIVE_INFINITY
        return _stringify(o,w,d);
    }
};
YAHOO.register("json", YAHOO.lang.JSON, {version: "2.4.1", build: "742"});
