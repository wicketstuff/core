(function($) {

  $.registerLiquidCanvasPlugin({
    name: "rect",
    paint: function(area) {
      area.ctx.beginPath();
      area.ctx.rect(0, 0, area.width, area.height);
      area.ctx.closePath();
      if (this.action) this.action.paint(area);  // for chaining
    }
  });
  
  $.registerLiquidCanvasPlugin({
    name: "roundedRect",
    defaultOpts: { radius:20 },
    paint: function(area) {
      var ctx = area.ctx;
      var opts = this.opts;
      ctx.beginPath();
      ctx.moveTo(0, opts.radius);
      ctx.lineTo(0, area.height - opts.radius);
      ctx.quadraticCurveTo(0, area.height, opts.radius, area.height);
      ctx.lineTo(area.width - opts.radius, area.height);
      ctx.quadraticCurveTo(area.width, area.height, area.width, area.height - opts.radius);
      ctx.lineTo(area.width, opts.radius);
      ctx.quadraticCurveTo(area.width, 0, area.width - opts.radius, 0);
      ctx.lineTo(opts.radius, 0);
      ctx.quadraticCurveTo(0, 0, 0, opts.radius);
      ctx.closePath();
      if (this.action) this.action.paint(area);  // for chaining
    },
    shrink: function(area, steps) {
      this.defaultShrink(area, steps);
      this.opts.radius -= steps;
    }
  });
  
  // This is a Liquid Canvas Plugin
  $.registerLiquidCanvasPlugin({
    name: "fill",
    defaultOpts: { color:"#aaa" },
    paint: function(area) {
      area.ctx.fillStyle = this.opts.color;
      this.action.paint(area);
      area.ctx.fill();
    }
  });

  $.registerLiquidCanvasPlugin({  // hmmmmmmm, no rotation? no width??? ah patterns!
    name: "image",
    defaultOpts: { url:"http://www.ruzee.com/files/liquid-canvas-image.png" },
    paint: function(area) {
      var image = new Image();
      image.src = this.opts.url;
      image.onload = function() { 
        area.ctx.drawImage(this, 0, 0); 
      };
    }
  });

  // This is a Liquid Canvas Plugin
  $.registerLiquidCanvasPlugin({
    name: "gradient",
    defaultOpts: { from: "#fff", to:"#666" },
    paint: function(area) {
      var grad = area.ctx.createLinearGradient(0, 0, 0, area.height);
      grad.addColorStop(0, this.opts.from);
      grad.addColorStop(1, this.opts.to);
      area.ctx.fillStyle = grad;
      this.action.paint(area);
      area.ctx.fill();
    }
  });
  // End of Liquid Canvas Plugin
  
  $.registerLiquidCanvasPlugin({
    name: "shadow",
    defaultOpts: { width:3, color:'#000', shift:2 },
    paint: function(area) {
      var sw = this.opts.width;
      
      area.ctx.fillStyle = this.opts.color; 
      area.ctx.globalAlpha = 1.0 / sw;
      for (var s = 0; s < sw; ++s) {
        this.action.paint(area);
        area.ctx.fill();
        this.action.shrink(area, 1);
      }
      area.ctx.globalAlpha = 1;
      area.ctx.translate(0, -this.opts.shift);
    }
  });

  $.registerLiquidCanvasPlugin({
    name: "border",
    defaultOpts: { color:'#8f4', width:3 },
    paint: function(area) {
      var bw = this.opts.width;
      area.ctx.strokeStyle = this.opts.color;
      area.ctx.lineWidth = bw;
      this.action.shrink(area, bw / 2);
      this.action.paint(area);
      area.ctx.stroke();
      this.action.shrink(area, bw / 2);
    }
  });

})(jQuery);
