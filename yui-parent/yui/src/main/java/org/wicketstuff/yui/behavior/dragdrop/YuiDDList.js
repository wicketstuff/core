(function(){

    var Y = YAHOO;
    Y.namespace('Wicket.DDList');
    
    var Dom = Y.util.Dom;
    var Event = Y.util.Event;
    var DDM = Y.util.DragDropMgr;
    
    // ////////////////////////////////////////////////////////////////////////////
    // custom drag and drop implementation
    // ////////////////////////////////////////////////////////////////////////////
    
    var DDList = function(id, sGroup, config, callbackWicket){
    
        DDList.superclass.constructor.call(this, id, sGroup, config);
        
        this.logger = this.logger || YAHOO;
        var el = this.getDragEl();
        Dom.setStyle(el, "opacity", 0.67); // The proxy is slightly transparent
        this.goingUp = false;
        this.lastY = 0;
        this.callbackWicket = callbackWicket;
        this.idx = 0;
        this.doi = 0;
    };
    
    Y.extend(DDList, YAHOO.util.DDProxy, {
    
        startDrag: function(x, y){
            this.logger.log(this.id + " startDrag");
            
            // make the proxy look like the source element
            var dragEl = this.getDragEl();
            var clickEl = this.getEl();
            Dom.setStyle(clickEl, "visibility", "hidden");
            
            dragEl.innerHTML = clickEl.innerHTML;
            
            Dom.setStyle(dragEl, "color", Dom.getStyle(clickEl, "color"));
            Dom.setStyle(dragEl, "backgroundColor", Dom.getStyle(clickEl, "backgroundColor"));
            Dom.setStyle(dragEl, "border", "2px solid gray");
        },
        
        endDrag: function(e){
        
            var srcEl = this.getEl();
            var proxy = this.getDragEl();
            
            // Show the proxy element and animate it to the src element's location
            Dom.setStyle(proxy, "visibility", "");
            var a = new YAHOO.util.Motion(proxy, {
                points: {
                    to: Dom.getXY(srcEl)
                }
            }, 0.2, Y.util.Easing.easeOut)
            var proxyid = proxy.id;
            var thisid = this.id;
            
            // Hide the proxy and show the source element when finished with the
            // animation
            a.onComplete.subscribe(function(){
                Dom.setStyle(proxyid, "visibility", "hidden");
                Dom.setStyle(thisid, "visibility", "");
            });
            a.animate();
            this.callbackWicket();
        },
        
        onDragDrop: function(e, id){
        
            // If there is one drop interaction, the li was dropped either on the
            // list, or it was dropped on the current location of the source
            // element.
            if (DDM.interactionInfo.drop.length === 1) {
            
                // The position of the cursor at the time of the drop
                // (YAHOO.util.Point)
                var pt = DDM.interactionInfo.point;
                
                // The region occupied by the source element at the time of the drop
                var region = DDM.interactionInfo.sourceRegion;
                
                // Check to see if we are over the source element's location. We
                // will append to the bottom of the list once we are sure it was a
                // drop in the negative space (the area of the list without any list
                // items)
                
                if (!region.intersect(pt)) {
                    var destEl = Dom.get(id);
                    var destDD = DDM.getDDById(id);
                    destEl.appendChild(this.getEl());
                    destDD.isEmpty = false;
                    DDM.refreshCache();
                    // this is an empty target
                    this.doi = destEl.id;
                }
            }
        },
        
        onDrag: function(e){
        
            // Keep track of the direction of the drag for use during onDragOver
            var y = Event.getPageY(e);
            
            if (y < this.lastY) {
                this.goingUp = true;
            }
            else 
                if (y > this.lastY) {
                    this.goingUp = false;
                }
            
            this.lastY = y;
        },
        
        onDragOver: function(e, id){
        
            var srcEl = this.getEl();
            var destEl = Dom.get(id);
            
            // We are only concerned with list items, we ignore the dragover
            // notifications for the list.
            if (destEl.nodeName.toLowerCase() == "li") {
                var orig_p = srcEl.parentNode;
                var p = destEl.parentNode;
                
                if (this.goingUp) {
                    p.insertBefore(srcEl, destEl); // insert above
                }
                else {
                    p.insertBefore(srcEl, destEl.nextSibling); // insert
                    // below
                }
                
                var lis = Dom.getChildren(p);
                this.idx = Array.indexOf(lis, srcEl);
                this.doi = destEl.id;
                DDM.refreshCache();
            }
        }
    });
    
    Y.Wicket.DDList = DDList;
})();

YAHOO.register("wicket_yui_ddlist", YAHOO.Wicket.DDList, {
    version: "2.7.0",
    build: "1"
});
