/**
 * Liquid Canvas jQuery Plugin 
 * 
 * Version 0.3
 *
 * Steffen Rusitschka  http://www.ruzee.com  MIT licensed
 */
(function($) {
  var canvasElements = [];
  var pollCounter = 0;
  var plugins = {};

  function Area(canvas) {
    var stack = [];
    
    $.extend(this, {
      width: canvas.width, height: canvas.height, ctx: canvas.getContext("2d"),
      
      save: function() {
        this.ctx.save();
        stack.push({ width: this.width, height: this.height });
      },
      
      restore: function() {
        this.ctx.restore();
        $.extend(this, stack.pop());
      }
    });
  }
  
  var Plugin = (function() {
    var shrink = function(area, steps) {
      area.ctx.translate(steps, steps);
      area.width -= 2 * steps;
      area.height -= 2 * steps;
    };
    return {
      action:{ paint:function(){} },  // provide a NOP "plugin"
      shrink: shrink,
      defaultShrink: shrink,
      setAction: function(action) { this.action = action; }
    };
  })();
  
  function newPlugin(hash, opts) {
    return $.extend({}, Plugin, hash, { opts: opts, savedOpts: opts });
  }
  
  function pluginFromPlugins(plugins) {
    return newPlugin({
      paint: function(area) {
        area.save();
        this.action.opts = $.extend(true, this.action.savedOpts);
        $.each(plugins, function() { this.paint(area); });
        area.restore();
      },
      
      setAction: function(action) {
        this.action = action; // should call super if it existed ...
        $.each(plugins, function() { this.action = action; });
      }
    });
  }
  var pluginFromApplications = pluginFromPlugins; // it just does the same ...
  
  function pluginFromName(name, opts) {
    var plugin = plugins[name];
    if (!plugin) throw "Unknown plugin: " + name;
    opts = $.extend({}, plugin.defaultOpts || {}, opts);
    return newPlugin(plugin, opts);
  }
  
  function parse(s) {
    s += " ";
    var index = 0;
    
    function err(m) { msg = m + " at " + index + ": ..." + s.substring(index) + "\nin " + s; alert(msg); throw msg; }
    function cur() { return s.charAt(index); }
    function next() { if (index > s.length) throw("Unexpected end"); return s.charAt(index + 1) }
    function eat() { return s.charAt(index++); }
    function skipWhite() { while (/\s/.exec(cur())) eat(); }
    function check(c) {
      skipWhite(); 
      for (var i=0; i<c.length; ++i) {
        if (cur() != c.charAt(i)) err("Expected '" + c.charAt(i) + "' found '" + cur() + "'"); 
        eat();
      }
    }

    //var parseApplications; // forward reference
    
    function parseWord() {
      skipWhite();
      for (var word = []; /\w/.exec(cur()); word.push(eat()));
      return word.join("");
    }
    
    function parseNumber() {
      skipWhite();
      for (var n = []; /\d/.exec(cur()); n.push(eat()));
      return parseInt(n.join(""));
    }
    
    function parseString() {
      skipWhite();
      var s = [], start = cur();
      if (/[^\'\"]/.exec(start)) { err("String expected") }
      eat();
      while (cur() != start) { if (cur() == "\\") s.eat(); s.push(eat()); }
      check(start);
      return s.join("");
    }
    
    // Yeah, strange thing - this does the CSS value like parsing
    function parseValue() {
      skipWhite();
      for (var s = []; /[^;}]/.exec(cur()); s.push(eat()));
      return s.join("");
    }
    
    function parseLiteral() {
      skipWhite();
      if (/\d/.exec(cur())) return parseNumber();
      if (/['"]/.exec(cur())) return parseString();
      return parseValue();
    }
    
    function parseOpts() {
      check("{");
      skipWhite();
      var opts = {};
      while (cur() != "}") {
        var key = parseWord();
        check(":");
        opts[key] = parseLiteral();
        skipWhite();
        if (cur() == "}") break;
        check(";");
      }
      check("}");
      return opts;
    }
    
    function parsePlugin() {
      var name = parseWord();
      skipWhite();
      opts = cur() == "{" ? parseOpts() : {};
      return pluginFromName(name, opts);
    }
    
    function parsePlugins() {
      check("[");
      skipWhite();
      var plugins = [];
      while (cur() != "]") {
        plugins.push(parsePlugin());
        skipWhite();
      }
      check("]");
      return pluginFromPlugins(plugins);
    }

    function parseActors() {
      skipWhite();
      return cur() == "[" ? parsePlugins() : parsePlugin();
    }
    
    function parseAction() {
      var action;
      skipWhite();
      if (cur() == "(") {
        eat();
        action = parseApplications();
        check(")");
      } else {
        action = parsePlugin();
      }
      return action;
    }
    
    function parseApplication() {
      var actors = parseActors();
      check("=>");
      var action = parseAction();
      actors.setAction(action);
      return actors;
    }
    
    function parseApplications() {
      var applications = [];
      while (true) {
        applications.push(parseApplication());
        skipWhite();
        if (cur() != ",") break;
        check(",");
      }
      return pluginFromApplications(applications);
    }
    
    return parseApplications();
  }

  function checkResize(container, force) {
    var $container = $(container);
    var data = $container.data('liquid-canvas');
    if (!data) return;
    var canvas = data.canvas;
    var $canvas = $(canvas);
    var w = $container.outerWidth();
    var h = $container.outerHeight();
    
    if (force || 
        canvas.width != w || canvas.height != h ||
        canvas.offsetTop != container.offsetTop || canvas.offsetLeft != container.offsetLeft) {
      pollCounter = 100;
      $canvas.css({ left: container.offsetLeft + "px", top: container.offsetTop + "px" });
      canvas.width = w;
      canvas.height = h;
      var area = new Area(canvas);
      area.save();
      data.paint(area);
      area.restore();
    }
  }

  function checkAllResize(force) {
    $.each(canvasElements, function() { checkResize(this, force); });
  }

  function poll(){
    checkAllResize();
    pollCounter--;
    if (pollCounter < 0) {
      pollCounter = 0;
      setTimeout(poll, 1000);
    } else {
      setTimeout(poll, 1000 / 60);
    }
  }

  jQuery.fn.extend({
    liquidCanvas: function(func) {
      this.each(function() {
        var canvas;
        if (window.G_vmlCanvasManager) {
          $(this).before('<div width="0" height="0" style="position:absolute; top:0px; left:0px;"></div>');
          canvas = G_vmlCanvasManager.initElement($(this).prev("div").get(0));
        } else {
          $(this).before('<canvas width="0" height="0" style="position:absolute; top:0px; left:0px;"></canvas>');
          canvas = $(this).prev("canvas").get(0);
        }
        
        var paint;
        if ($.isFunction(func)) {
          paint = func;
        } else {
          var plugin = parse(func)
          paint = function(area) { plugin.paint(area); };
        }
        
        $(this).data("liquid-canvas", {
          "canvas": canvas,
          "paint": paint
        });
        $(this).css({ background: "transparent" });
        if ($(this).css("position") != "absolute") $(this).css({ position: "relative" });
        
        canvasElements.push(this);
        checkResize(this, true);
      });
    }
  });
  
  jQuery.extend({
    registerLiquidCanvasPlugin: function(plugin) {
      plugins[plugin.name] = $.extend({}, Plugin, plugin);
    }
  });
  
  $(document).ready(checkAllResize);
  poll();
})(jQuery);
