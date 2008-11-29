
/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */

/**
 * @class a YAHOO.util.DDProxy implementation that swaps positions with the 
 * target when dropped
 *
 * @extends YAHOO.util.DDProxy
 * @constructor
 * @param {String} id the id of the linked element
 * @param {String} sGroup the group of related DragDrop items
 */
YAHOO.example.DDSwap = function(id, sGroup, config) {
    this.swapInit(id, sGroup, config);
};

YAHOO.extend(YAHOO.example.DDSwap, YAHOO.util.DDProxy);

YAHOO.example.DDSwap.prototype.swapInit = function(id, sGroup, config) {
    if (!id) { return; }

    this.init(id, sGroup, config);
    this.initFrame();
    this.logger = this.logger || YAHOO;

    /**
     * css style to use when items are not being hovered over.
     */
    this.offClass = "testSquare";

    /**
     * css style to use when hovered over
     */
    this.onClass = "testSquareOn";

    /**
     * cache of the elements we have changed the style so we can restore it
     * later
     */
    this.els = [];

};

YAHOO.example.DDSwap.prototype.onDragDrop = function(e, id) {
    var dd = YAHOO.util.DDM.getDDById(id);
    this.swap(this.getEl(), dd.getEl());
    this.resetConstraints();
    dd.resetConstraints();
};

YAHOO.example.DDSwap.prototype.swap = function(el1, el2) {
    this.logger.log(this.id + " onDragDrop swap");

    // Swap out the position of the two objects.  This only works for absolutely
    // positioned elements.  See for an implementation that 
    // works for relatively positioned elements
    var s1 = el1.style;
    var s2 = el2.style;

    var l = s1.left;
    var t = s1.top;

    s1.left = s2.left;
    s1.top = s2.top;

    s2.left = l;
    s2.top = t;
};

YAHOO.example.DDSwap.prototype.onDragEnter = function(e, id) {
    this.logger.log(this.id + " dragEnter " + id);

    // store a ref so we can restore the style later
    this.els[id] = true;

    // set the mouseover style
    var el = YAHOO.util.DDM.getElement(id);
    if (el.className != this.onClass) {
        el.className = this.onClass;
    }
};

YAHOO.example.DDSwap.prototype.onDragOut = function(e, id) {
    this.logger.log(this.id + " dragOut " + id);

    // restore the style
    YAHOO.util.DDM.getElement(id).className = this.offClass;
};

YAHOO.example.DDSwap.prototype.endDrag = function(e) {
    this.logger.log(this.id + " endDrag");
    this.resetStyles();

    /*
        var el = this.getDragEl();
    el.style.visibility = ""; // show the element first
    var position = [100, 100];
    var duration = 0.4;
    var oAnim = new YAHOO.util.Motion( 
           el, { points: { to: position } }, duration, YAHOO.util.Easing.easeOut );

    oAnim.onComplete.subscribe( function() { el.style.visibility = "hidden" } );
*/


};

YAHOO.example.DDSwap.prototype.resetStyles = function() {
    // restore all element styles
    for (var i in this.els) {
        var el = YAHOO.util.DDM.getElement(i);
        if (el) { el.className = this.offClass; }
    }
};

YAHOO.example.DDSwap.prototype.onDrag = function(e) { };

YAHOO.example.DDSwap.prototype.onDragOver = function(e) { };


//-------------------------------------------------------------------------
// Intersect mode
//-------------------------------------------------------------------------



YAHOO.example.DDSwap_i = function(id, sGroup) {
    this.swapInit(id, sGroup);
};

YAHOO.example.DDSwap_i.prototype = new YAHOO.example.DDSwap();

YAHOO.example.DDSwap_i.prototype.onDragDrop = function(e, dds) {
    // this.logger.log(this.id + " onDragDrop swap");
    var dd = YAHOO.util.DDM.getBestMatch(dds);
    this.swap(this.getEl(), dd.getEl());

    this.resetConstraints();
    dd.resetConstraints();
};

YAHOO.example.DDSwap_i.prototype.onDragEnter = function(e, dds) { 
  // this.logger.log(this.id + " dragEnter " + id);
};

YAHOO.example.DDSwap_i.prototype.onDragOver = function(e, dds) {
 
    this.resetStyles();

    var dd = YAHOO.util.DDM.getBestMatch(dds);

    this.els[dd.id] = true;

    // set the mouseover style
    var el = dd.getEl();
    if (el.className != this.onClass) {
        el.className = this.onClass;
    }
 
};

YAHOO.example.DDSwap_i.prototype.onDragOut = function(e, dds) {
    // this.logger.log(this.id + " dragOut " + id);

    // restore the style
    for (var i=0; i<dds.length; ++i) {
        dds[i].getEl().className = this.offClass;
    }
};

