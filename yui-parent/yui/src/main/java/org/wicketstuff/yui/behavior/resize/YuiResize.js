if (typeof (Wicket) == "undefined")
	Wicket = {};
if (typeof (Wicket.yui) == "undefined")
	Wicket.yui = {};

( function() {

	var Dom = YAHOO.util.Dom;
	var Event = YAHOO.util.Event;

	Wicket.yui.Resize = function(el, attr, startResize, resize) {
		Wicket.yui.Resize.superclass.constructor.call(this, el, attr);
		this.on('startResize', startResize, el, true);
		this.on('resize', resize, el, true);
	};

	YAHOO.extend(Wicket.yui.Resize, YAHOO.util.Resize);

})();
