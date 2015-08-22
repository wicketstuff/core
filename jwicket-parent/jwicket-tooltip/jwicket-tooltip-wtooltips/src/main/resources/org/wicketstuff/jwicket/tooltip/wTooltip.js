/* Wayfarer Tooltip
 * Version 1.0.3
 * Author Abel Mohler
 * URI: http://www.wayfarerweb.com/wtooltip.php
 * Released with the MIT License: http://www.wayfarerweb.com/mit.php
 */
(function($){ //jQuery.noConflict()compliant
    $.fn.wTooltip = function(o, callback){
        o = $.extend({ //defaults, can be overidden
            content: null, //string content for tooltip.
            ajax: null, //path to content for tooltip
            follow: true, //does tooltip follow the cursor?
            auto: true, //If false, tooltip won't automatically transition, it must be manually shown/hidden
            fadeIn: 0, //fade in, in milliseconds ("fast, "slow", etc may also be used)
            fadeOut: 0, //fade out, in milliseconds ("fast, "slow", etc may also be used)
            appendTip: document.body, //should probably not need to be overridden
            degrade: false, //if true, in IE6 tooltip will degrade to a title attribute message
            offsetY: 10, //offsetY and offsetX properties designate position from the cursor
            offsetX: 1,
            style: {},
            className: null, //to style the tooltip externally, pass a className or id
            id: null,
            callBefore: function(tooltip, node, settings){
            }, //called when mouse enters the area
            callAfter: function(tooltip, node, settings){
            }, //called when mouse leaves the area (same as "callback" option)
            clickAction: function(tooltip, node){
                $(tooltip).hide();
            }, //called when the element is clicked, with access to tooltip
            delay: 0, //delay (in milliseconds)before tooltip appears and callBefore executes
            timeout: 0, //delay (in milliseconds)before tooltip transitions away, and callAfter executes
            cloneable: false //UNWORKING.  Requires $().wClone plugin. If true, tooltip may be dragged and placed anywhere on the screen.
        }, o ||
        {});
        
        if (!o.style && typeof o.style != "object") {
            o.style = {};
            o.style.zIndex = "1000";
        }
        else {
            o.style = $.extend({ //the default style rules of the tooltip
                border: "1px solid gray",
                background: "#edeef0",
                color: "#000",
                padding: "10px",
                zIndex: "1000",
                textAlign: "left"
            }, o.style ||
            {});
        }
        
        if (typeof callback == "function") 
            o.callAfter = callback || o.callAfter;
        
        o.style.display = "none", o.style.position = "absolute"; //permanent defaults
        //private settings
        var title, timeout, timeout2, iId, over = {}, firstMove = true, hovered = false, maxed = false, tooltip = document.createElement('div'), ie6 = (typeof document.body.style.maxWidth == "undefined") ? true : false, talk = (typeof $.talk == "function" && typeof $.listen == "function") ? true : false;
        
        if (o.id) 
            tooltip.id = o.id;
        if (o.className) 
            tooltip.className = o.className;
        
        o.degrade = (o.degrade && ie6) ? true : false; //only degrades if also IE6
        for (var p in o.style)//apply styles to tooltip
             tooltip.style[p] = o.style[p];
        
        function fillTooltip(condition){
            if (condition) {
                if (o.degrade)//replace html characters for proper degradation to title attribute
                    $(tooltip).html(o.content.replace(/<\/?[^>]+>/gi, ''));
                else //otherwise just fill the tooltip with content
                     $(tooltip).html(o.content);
            }
        }
        
        if (o.ajax) { //if o.ajax is selected, this will fill and thus override o.content
            $.get(o.ajax, function(data){
                if (data) 
                    o.content = data;
                fillTooltip(o.content);
            });
        }
        
        function offConditions(that){
            function _offActions(that){
                if (title && !o.content) {
                    that.title = title;
                    title = null;
                }
            }
            function _execute(){
                if (!hovered && o.auto) {
                    clearInterval(iId);
                    if (o.fadeOut) {
                        $(tooltip).fadeOut(o.fadeOut, function(){
                            _offActions(that);
                        });
                    }
                    else {
                        _offActions(that);
                        tooltip.style.display = "none";
                    }
                }
                if (typeof o.callAfter == "function") 
                    o.callAfter(tooltip, that, o);
                if (talk) 
                    o = $.listen(o);
            }
            if (o.timeout > 0) {
                timeout2 = setTimeout(function(){
                    _execute();
                }, o.timeout);
            }
            else {
                _execute();
            }
        }
        
        $(tooltip).hover(function(){
            hovered = true;
        }, function(){
            hovered = false;
            offConditions(over);
        });
        
        //initialize
        if (talk) { //A "channel" for plugins to "talk" to each other, and callbacks to manipulate settings
            o.key = tooltip;
            o.plugin = "wTooltip";
            o.channel = "wayfarer";
            $.talk(o);
        }
        
        fillTooltip(o.content && !o.ajax);
        $(tooltip).appendTo(o.appendTip);
        
        return this.each(function(){ //returns the element chain
            this.onmouseover = function(ev){
                var that = this;
                clearTimeout(timeout2);
                if (this.title && !o.degrade && !o.content) {
                    title = this.title;
                    this.title = "";
                }
                if (o.content && o.degrade) 
                    this.title = tooltip.innerHTML;
                
                function _execute(){
                    if (typeof o.callBefore == "function") 
                        o.callBefore(tooltip, that, o);
                    if (talk) 
                        o = $.listen(o); //ping for new settings
                    if (o.auto) {
                        var display;
                        if (o.content) {
                            if (!o.degrade) 
                                display = "block";
                        }
                        else 
                            if (title && !o.degrade) {
                                $(tooltip).html(title);
                                display = "block";
                            }
                            else {
                                display = "none";
                            }
                        if (display == "block" && o.fadeIn) 
                            $(tooltip).fadeIn(o.fadeIn);
                        else 
                            tooltip.style.display = display;
                    }
                }
                
                if (o.delay > 0) {
                    timeout = setTimeout(function(){
                        _execute();
                    }, o.delay);
                }
                else {
                    _execute();
                }
            }
            
            this.onmousemove = function(ev){
                var e = (ev) ? ev : window.event, that = this;
                over = this; //tracks the event trigger in the plugin-global "over"
                if (o.follow || firstMove) {
                    var scrollY = $(window).scrollTop(), scrollX = $(window).scrollLeft(), top = e.clientY + scrollY + o.offsetY, left = e.clientX + scrollX + o.offsetX, outerH = $(o.appendTip).outerHeight(), innerH = $(o.appendTip).innerHeight(), maxLeft = $(window).width() + scrollX - $(tooltip).outerWidth(), maxTop = $(window).height() + scrollY - $(tooltip).outerHeight();
                    
                    top = (outerH > innerH) ? top - (outerH - innerH) : top; //if appended area (usually BODY) has a border on top, adjust
                    maxed = (top > maxTop || left > maxLeft) ? true : false;
                    
                    if (left - scrollX <= 0 && o.offsetX < 0) 
                        left = scrollX;
                    else 
                        if (left > maxLeft) 
                            left = maxLeft;
                    if (top - scrollY <= 0 && o.offsetY < 0) 
                        top = scrollY;
                    else 
                        if (top > maxTop) 
                            top = maxTop;
                    
                    tooltip.style.top = top + "px";
                    tooltip.style.left = left + "px";
                    firstMove = false;
                }
            }
            
            this.onmouseout = function(){
                clearTimeout(timeout);
                var that = this;
                firstMove = true;
                if (!o.follow || maxed || (o.offsetX < 0 && o.offsetY < 0)) {
                    setTimeout(function(){
                        iId = setInterval(function(){
                            offConditions(that)
                        }, 1)
                    }, 1);
                }
                else {
                    offConditions(this);
                }
            }
            
            if (typeof o.clickAction == "function") {
                this.onclick = function(){
                    o.clickAction(tooltip, this);
                }
            }
        });
    }
})(jQuery);
