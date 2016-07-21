(function() {
if (typeof(Wicket) == "undefined")Wicket = { };
if (typeof(Wicket.yui) == "undefined")Wicket.yui = { };
        
Wicket.yui.SortableListApp = {
    init: function(type, obj, arguments) {
		new YAHOO.util.DDTarget(arguments.groupId);
        for (i=0;i<arguments.items.length;i++) {
			new Wicket.yui.SortableList(arguments.items[i], arguments.groupId, {}, arguments.callbackUrl);
        }
    }
};

Wicket.yui.SortableList = function(id, sGroup, config, callbackUrl) {

    Wicket.yui.SortableList.superclass.constructor.call(this, id, sGroup, config);
	this.callbackUrl = callbackUrl;
    this.logger = this.logger || YAHOO;
    var el = this.getDragEl();
    YAHOO.util.Dom.setStyle(el, "opacity", 0.67); // The proxy is slightly transparent

    this.goingUp = false;
    this.lastY = 0;
};

YAHOO.extend(Wicket.yui.SortableList, YAHOO.util.DDProxy, {

    startDrag: function(x, y) {
        this.logger.log(this.id + " startDrag");

        // make the proxy look like the source element
        var dragEl = this.getDragEl();
        var clickEl = this.getEl();

   	    YAHOO.util.Dom.setStyle(clickEl, "visibility", "hidden");

        dragEl.innerHTML = clickEl.innerHTML;

       	YAHOO.util.Dom.setStyle(dragEl, "color", YAHOO.util.Dom.getStyle(clickEl, "color"));
   	    YAHOO.util.Dom.setStyle(dragEl, "backgroundColor", YAHOO.util.Dom.getStyle(clickEl, "backgroundColor"));
        YAHOO.util.Dom.setStyle(dragEl, "border", "2px solid gray");
    },

    endDrag: function(e) {

        var srcEl = this.getEl();
        var proxy = this.getDragEl();
		
		var index = this.index;
		
		if(index != "undefined") {
			var callback = this.callbackUrl+'&did='+srcEl.id+'&dindex='+index;
			wicketAjaxGet(callback, function() { }.bind(this), function() { }.bind(this));
		}			

        // Show the proxy element and animate it to the src element's location
        YAHOO.util.Dom.setStyle(proxy, "visibility", "");
        var a = new YAHOO.util.Motion( 
            proxy, { 
                points: { 
                    to: YAHOO.util.Dom.getXY(srcEl)
                }
            }, 
            0.2, 
            YAHOO.util.Easing.easeOut 
        )
        var proxyid = proxy.id;
        var thisid = this.id;

        // Hide the proxy and show the source element when finished with the animation
        a.onComplete.subscribe(function() {
                YAHOO.util.Dom.setStyle(proxyid, "visibility", "hidden");
                YAHOO.util.Dom.setStyle(thisid, "visibility", "");
            });
        a.animate();
    },

    onDragDrop: function(e, id) {
        // If there is one drop interaction, the li was dropped either on the list,
        // or it was dropped on the current location of the source element.
        if (YAHOO.util.DragDropMgr.interactionInfo.drop.length === 1) {

            // The position of the cursor at the time of the drop (YAHOO.util.Point)
            var pt = YAHOO.util.DragDropMgr.interactionInfo.point; 

            // The region occupied by the source element at the time of the drop
            var region = YAHOO.util.DragDropMgr.interactionInfo.sourceRegion; 

            // Check to see if we are over the source element's location.  We will
            // append to the bottom of the list once we are sure it was a drop in
            // the negative space (the area of the list without any list items)
            if (!region.intersect(pt)) {
                var destEl = YAHOO.util.Dom.get(id);
				if(destEl.nodeName.toLowerCase() == "ul"){
	                var destDD = YAHOO.util.DragDropMgr.getDDById(id);
	                destEl.appendChild(this.getEl());
    	            destDD.isEmpty = false;
        	        YAHOO.util.DragDropMgr.refreshCache();
        	    }
            }
        }
    },

    onDrag: function(e) {

        // Keep track of the direction of the drag for use during onDragOver
        var y = YAHOO.util.Event.getPageY(e);

        if (y < this.lastY) {
            this.goingUp = true;
        } else if (y > this.lastY) {
            this.goingUp = false;
        }

        this.lastY = y;
    },

    onDragOver: function(e, id) {
        var srcEl = this.getEl();
        var destEl = YAHOO.util.Dom.get(id);

        // We are only concerned with list items, we ignore the dragover
        // notifications for the list.
        if (destEl.nodeName.toLowerCase() == "li") {
            var orig_p = srcEl.parentNode;
            var p = destEl.parentNode;

            if (this.goingUp) {
            	this.index = this.getIndexOf(p, destEl)-1;
                p.insertBefore(srcEl, destEl); // insert above
            } else {
            	this.index = this.getIndexOf(p, destEl)-1;
                p.insertBefore(srcEl, destEl.nextSibling); // insert below
            }

            YAHOO.util.DragDropMgr.refreshCache();
        } else {
			this.index = 0;
        }
    },
    
    getIndexOf: function(p, e) {
		var counter = 0;
		var n = p.firstChild;
		while( n != 'undefined') {
			if(n==e) {
				return counter;
			}
			n=n.nextSibling;
			if(n.tagName == e.tagName){
				counter++;
			}
		}
    	return 0;
    }
});
})();