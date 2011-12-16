/**
 * EidoGo -- Web-based SGF Editor
 * Copyright (c) 2007, Justin Kramer <jkkramer@gmail.com>
 * Code licensed under AGPLv3:
 * http://www.fsf.org/licensing/licenses/agpl-3.0.html
 *
 * Supplements to core language objects (Array, Function)
 */
Array.prototype.contains = function(needle) {
    if (Array.prototype.indexOf)
        return this.indexOf(needle) != -1;
    for (var i in this)
        if (this[i] == needle)
            return true;
    return false;
}
Array.prototype.setLength = function(len, val) {
    val = typeof val != "undefined" ? val : null;
    for (var i = 0; i < len; i++) {
        this[i] = val;
    }
    return this;
}
Array.prototype.addDimension = function(len, val) {
    val = typeof val != "undefined" ? val : null;
    var thisLen = this.length; // minor optimization
    for (var i = 0; i < thisLen; i++) {
        this[i] = [].setLength(len, val);
    }
    return this;
}
Array.prototype.first = function() {
    return this[0];
}
Array.prototype.last = function() {
    return this[this.length-1];
}
Array.prototype.copy = function() {
    var copy = [];
    var len = this.length; // minor optimization
    for (var i = 0; i < len; i++) {
        if (this[i] instanceof Array) {
            copy[i] = this[i].copy();
        } else {
            copy[i] = this[i];
        }
    }
    return copy;
}


if (!Array.prototype.map)
{
  Array.prototype.map = function(fun /*, thisp*/)
  {
    var len = this.length;
    if (typeof fun != "function")
      throw new TypeError();

    var res = new Array(len);
    var thisp = arguments[1];
    for (var i = 0; i < len; i++)
    {
      if (i in this)
        res[i] = fun.call(thisp, this[i], i, this);
    }

    return res;
  };
}

if (!Array.prototype.filter)
{
  Array.prototype.filter = function(fun /*, thisp*/)
  {
    var len = this.length;
    if (typeof fun != "function")
      throw new TypeError();

    var res = new Array();
    var thisp = arguments[1];
    for (var i = 0; i < len; i++)
    {
      if (i in this)
      {
        var val = this[i]; // in case fun mutates this
        if (fun.call(thisp, val, i, this))
          res.push(val);
      }
    }

    return res;
  };
}

if (!Array.prototype.forEach)
{
  Array.prototype.forEach = function(fun /*, thisp*/)
  {
    var len = this.length;
    if (typeof fun != "function")
      throw new TypeError();

    var thisp = arguments[1];
    for (var i = 0; i < len; i++)
    {
      if (i in this)
        fun.call(thisp, this[i], i, this);
    }
  };
}

if (!Array.prototype.every)
{
  Array.prototype.every = function(fun /*, thisp*/)
  {
    var len = this.length;
    if (typeof fun != "function")
      throw new TypeError();

    var thisp = arguments[1];
    for (var i = 0; i < len; i++)
    {
      if (i in this &&
          !fun.call(thisp, this[i], i, this))
        return false;
    }

    return true;
  };
}

if (!Array.prototype.some)
{
  Array.prototype.some = function(fun /*, thisp*/)
  {
    var len = this.length;
    if (typeof fun != "function")
      throw new TypeError();

    var thisp = arguments[1];
    for (var i = 0; i < len; i++)
    {
      if (i in this &&
          fun.call(thisp, this[i], i, this))
        return true;
    }

    return false;
  };
}

Array.from = function(it) {
    var arr = [];
    for (var i = 0; i < it.length; i++) {
        arr[i] = it[i];
    }
    return arr;
}

Function.prototype.bind = function($thisObj) {
    var $method = this;
    var $args = Array.from(arguments).slice(1);
    return function() {
        return $method.apply($thisObj, $args.concat(Array.from(arguments)));
    }
}