/**
* Cornerz 0.1 - Bullet Proof Corners
* Jonah Fox (jonah@parkerfox.co.uk) 2008
* 
* Usage: $('.myclass').curve(options)
* options is a hash with the following parameters. Bracketed is the default
*   radius (10)
*   borderWidth (read from BorderTopWidth or 0)
*   background ("white"). Note that this is not calculated from the HTML as it is expensive
*   borderColor (read from BorderTopColor)
*   corners ("tl br tr bl"). Specify which borders
*/

(function($){
  
  $.fn.cornerz = function(options){

    function canvasCorner(t,l, r,bw,bc,bg){
	    var sa,ea,cw,sx,sy,x,y, p = 1.57, css="position:absolute;"
	    if(t) 
		    {sa=-p; sy=r; y=0; css+="top:-"+bw+"px;";  }
	    else 
		    {sa=p; sy=0; y=r; css+="bottom:-"+bw+"px;"; }
	    if(l) 
		    {ea=p*2; sx=r; x=0;	css+="left:-"+bw+"px;"}
	    else 
		    {ea=0; sx=0; x=r; css+="right:-"+bw+"px;";	}
		
	    var canvas=$("<canvas width="+r+"px height="+ r +"px style='" + css+"' ></canvas>")
	    var ctx=canvas[0].getContext('2d')
	    ctx.beginPath();
	    ctx.lineWidth=bw*2;	
	    ctx.arc(sx,sy,r,sa,ea,!(t^l));
	    ctx.strokeStyle=bc
	    ctx.stroke()
	    ctx.lineWidth = 0
	    ctx.lineTo(x,y)
	    ctx.fillStyle=bg
	    ctx.fill()
	    return canvas
    }

    function canvasCorners(t, corners, r, bw,bc,bg) {
	    var $$ = $(t)
        $.each(corners.split(" "), function() {
	      $$.append(canvasCorner(this[0]=="t",this[1]=="l", r,bw,bc,bg).css("z-index",0))//canvasCorners returns a DOM element
	    })
    }

    function vmlCurve(r,b,c,m,ml,mt, right_fix) {
        var l = m-ml-right_fix
        var t = m-mt
        return "<v:arc filled='False' strokeweight='"+b+"px' strokecolor='"+c+"' startangle='0' endangle='361' style=' top:" + t +"px;left: "+ l + ";width:" + r+ "px; height:" + r+ "px' />"
    }
    
    var cornerzIECache = {}
    function vmlCorners(corners, r, bw, bc, bg) {
      var key = corners+ r+ "_" +bw+ bc+ bg
      cornerzIECache[key] = cornerzIECache[key] || _vmlCorners(corners, r, bw, bc, bg)
      return cornerzIECache[key]
    }
    
    function vmlCorners(t, corners, r, bw, bc, bg, w) {
        var inl = $(t).css("display")
      var h ="<div style='display: "+ inl +" ;text-align: right; padding-right: " + (r-bw-2) + "px;'>"
      $.each($.trim(corners).split(" "), function() {
        var css,ml=1,mt=1,right_fix=0
        if(this.charAt(0)=="t") {
          css="top:-"+bw+"px;"
        }
        else {
          css= "bottom:-"+bw+"px;"
          mt=r
        }
        if(this.charAt(1)=="l")
          css+="left:-"+bw+"px;"
        else {
          css +=" margin-right:-"+(bw-r)+"px; " // odd width gives wrong margin?
           ml=r
           right_fix = 0
        }
        //css+="background:red;"
        h+="<div style='"+css+"; position: absolute; overflow:hidden; width:"+ r +"px; height: " + r + "px;'>"
        h+="<div style='text-align:left'>"
        h+= "<v:group  style='width:10px;height:10px;position:absolute;' coordsize='10,10' >"
        h+= vmlCurve(r*3,r+bw,bg, -r/2,ml,mt,right_fix) 
        if(bw>0)
          h+= vmlCurve(r*2-bw,bw,bc, bw/2,ml,mt,right_fix)
        h+="</v:group>"
        h+="</div>"
        h+= "</div>" 
      })
    h+="</div>"
      t.innerHTML += h//need to use innerHTML rather than jQuery
    }

    var settings = {
      corners : "tl tr bl br",
      radius : 10,
      background: "white",
      borderWidth: 0
    }
        
    $.extend(settings, options || {});
    
    if($.browser.msie ) {
        //document.namespaces.add("v", "urn:schemas-microsoft-com:vml");
        if(document.namespaces['v'] == null) { 
        //      var stl = document.createStyleSheet(); 
          //    stl.addRule("v\\:*", "behavior: url(#default#VML);"); 
            document.namespaces.add("v", "urn:schemas-microsoft-com:vml"); 
        var ss = document.createStyleSheet();
        ss.cssText = "v\\:*{behavior:url(#default#VML);}"
          

         }
    }
    
    return this.each(function() {
      var $$ = $(this)
      var r = settings.radius*1.0
      var bw = (settings.borderWidth || parseInt($$.css("borderTopWidth")) || 0)*1.0
      var bg = settings.background
      var bc = settings.borderColor
      bc = bc || ( bw > 0 ? $$.css("borderTopColor") : bg)
      
      var cs = settings.corners
      if($.browser.msie) 
        vmlCorners(this, cs,r,bw,bc,bg, $(this).width() )     
      else  
        canvasCorners(this, cs,r,bw,bc,bg)
        
      if(this.style.position != "absolute")
        this.style.position = "relative"
        this.style.zoom = 1 // give it a layout in IE
      })  
  
  }
})(jQuery);
