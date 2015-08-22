(function(){
    var Y = YAHOO;
    Y.namespace('Wicket.Resize');
    var Event = Y.util.Event;
    
    var YUI_RESIZE = function(el, attr, startResize, resize){
        YUI_RESIZE.superclass.constructor.call(this, el, attr);
        this.on('startResize', startResize, el, true);
        this.on('resize', resize, el, true);
    };
    
    Y.extend(YUI_RESIZE, YAHOO.util.Resize);
    Y.Wicket.Resize = YUI_RESIZE;
})();

YAHOO.register("wicket_resize", YAHOO.Wicket.Resize, {
    version: "2.7.0",
    build: "1799"
});
