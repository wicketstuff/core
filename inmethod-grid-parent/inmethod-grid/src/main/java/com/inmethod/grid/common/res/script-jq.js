/**
 * Copyright (c) 2007 Matej Knopp, InMethod s.r.o.
 * All rights reserved.
 */

if (typeof(InMethod) === "undefined") {
	InMethod = { };
}


(function() {	
	
    var empty = function() { };

    var genIdCounter = 0;

    /**
     * YAHOO event cleanups the listeners only on page unload. However, if the page lives long
     * enough the elements removed from document that have listener attached cause IE GC not free the
     * memory. So we manually register each element with listener and then periodically check 
     * whether the element is still in document. If it's not the element's listeners are removed.
     */
    var elementsWithListeners = new Array();

    var addListener = function(element, event, fn, obj, override) {

            // attach event
            $(element).on(event, obj, fn);

            if (element !== document && element !== window) {
                    elementsWithListeners.push(element);
            }
    };

    // temporary array of elements being processed during purge
    var beingPurged = null;

    // count of purged elements (debug)
    var purgedCount = 0;

    var purgeDebug = false;

    // periodically called to initiate the purging process
    var purgeInactiveListeners = function() {

            // if purge is in progress don't do anything
            if (typeof beingPurged !== 'undefined' && beingPurged !== null) {
                    return;
            }

            // the the elements
            beingPurged = elementsWithListeners;
            elementsWithListeners = new Array();

            if (purgeDebug)
                    Wicket.Log.info("Purge begin");

            purgedCount = 0;

            // start the process
            purge();
    };

    var purge = function() 
    {
	if (typeof beingPurged !== 'undefined' && beingPurged !== null) {
		var done = 0;
		
		// it is necessary to limit amount of items being purged in one go otherwise
		// IE will complain about script being slow
		var max = 50;
		
		var a = beingPurged;
		for (var i = 0; i < a.length && done < max; ++i)
		{
			var e = a[i];
			if (e !== null)
			{
				++done;
				if ($(e).length === 0) {
					// remove all events.
					$(e).off();
					++purgedCount;
				} 
                                // Solves Memory leak
                                else if(!jQuery.contains(document, e)){
                                    $(e).off();
                                    $(e).remove();
                                    ++purgedCount;
				} else {
					// element is still in document, return it
					elementsWithListeners.push(e);
				}
				a[i] = null;
			}			
		}
		
		
		if (i === a.length)
		{
			// we are done with purging
			beingPurged = null;
			
			if (purgeDebug)
				Wicket.Log.info("Purge End; purged: " + purgedCount + ", total: " + elementsWithListeners.length);
		}		
		else
		{
			// not yet done, continue after 50ms
			window.setTimeout(purge, 50);
		}
	}
    };

window.setInterval(purgeInactiveListeners, 10000); 

$(window).on("unload", function() { elementsWithListeners = null; });

var getElementId = function(element) {
	if (typeof(element.getAttribute("id")) === "string" && element.getAttribute("id").length > 0) {
		return element.getAttribute("id");
	} else {
		var id = "imxt-generated-id-" + (++genIdCounter);
		element.setAttribute("id", id);
		return id;
	}
};

InMethod.Drag = Wicket.Class.create();

InMethod.Drag.prototype = {	
	
	initialize: function(element, onDragBegin, onDragEnd, onDrag, thisRef) {
						
		this.elementId = getElementId(element);		
		this.onDragBegin = onDragBegin || empty;
		this.onDrag = onDrag || empty; 
		this.onDragEnd = onDragEnd || empty;
		this.thisRef = thisRef || new Object();
		this.noTree = null;
		
		addListener(element, "mousedown", this.onMouseDown, this, true);						
		element.imDrag = this;
	},
	
	onMouseDown: function(e) {
		if (typeof(e.ignore) === "undefined") {				
			e.stopPropagation();			
			e.data.lastMouseX = e.clientX;
			e.data.lastMouseY = e.clientY;				
			
			addListener(document, "selectstart", e.data.onSelectStart);							
			addListener(document, "mousemove", e.data.onMouseMove, e.data, true);
			addListener(document, "mouseup", e.data.onMouseUp, e.data, true);	
			
			Wicket.bind(e.data.onDragBegin,e.data.thisRef)(Wicket.$(e.data.elementId), e);
		}
		
		return false;
	},
	
	onMouseMove: function(e) {
		e.stopPropagation();
		
		// this happens sometimes in Safari 
		if (e.clientX < 0 || e.clientY < 0) {
			return;
		}		

		var deltaX = e.clientX - e.data.lastMouseX;
		var deltaY = e.clientY - e.data.lastMouseY;
				
		var res = Wicket.bind(e.data.onDrag,e.data.thisRef)(Wicket.$(e.data.elementId), deltaX, deltaY, e);
			
		if (typeof res === 'undefined' || res === null){
                    res = [0, 0];
                }
			
		e.data.lastMouseX = e.clientX + res[0];
		e.data.lastMouseY = e.clientY + res[1];
						
		return false;
	},
	
	onMouseUp: function(e) {
		e.stopPropagation();
		
		// cleanup
		$(document).off("selectstart", e.data.onSelectStart);
		$(document).off("mousemove", e.data.onMouseMove);
		$(document).off("mouseup", e.data.onMouseUp);
		
		Wicket.bind(e.data.onDragEnd,e.data.thisRef)(Wicket.$(e.data.elementId), e);
		
		return false;
	},
	
	onSelectStart: function(e) {
		return false;
	},
	
	cleanUp: function() {
		$(Wicket.$(this.elementId)).off("mousedown");
		this.element = null;
		this.onDragBegin = null;
		this.onDrag = null;
		this.onDragEnd = null;
		this.thisRef = null;
	}	
};


var colSpanRegex = /imxt-colspan-([\d]+)/;

var nextSibling = function(element) {
	return $(element).next(element.tagName)[0];
};

var prevSibling = function(element) {
	return $(element).prev(element.tagName)[0];
};

var hasClass = function(e, c) {
	return $(e).hasClass(c);
};

var addClass = function(e, c) {
	$(e).addClass(c);
};

var removeClass = function(e, c) {
	$(e).removeClass(c);
};

var contains = function(array, value) {
	for (var i = 0; i < array.length; ++i) {
		if (array[i] == value) {
			return true;
		}
	}
	return false;
};

var isEmpty = function(string) {
	return !(typeof string === 'string' && string.length > 0);
}


var getChildren = function(parent, tagName) {
	if ($.isArray(tagName)) {
		return $(parent).children(tagName.toString());
	} else {
		return $(parent).children(tagName);
	}	
};

var arrayEq = function(a1, a2) {
	if (!$.isArray(a1) || !$.isArray(a2)) {
		return false;
	} 
	if (a1 == a2) {
		return true;
	}
	if (a1.length != a2.length) {
		return false;
	}
	for (var i = 0; i < a1.length; ++i) {
		if (a1[i] != a2[i]) {
			return false;
		}
	}
	return true;
};

var getFirstChild = function(parent, tagName) {
	for (var i = 0; i < parent.childNodes.length; ++i) {
		var c = parent.childNodes[i];
		if (c && c.tagName == tagName) {
			return c;
		}
	}
	return null;
}

InMethod.XTable = Wicket.Class.create();

InMethod.XTable.prototype = {
	
	initialize: function(id, columnsData, columnsStateCallback) {			
		this.id = id;	
		this.initColumns(columnsData);				
		
		this.attachEventHandlers();
		this.prevColumnWidths = null;					
		
		this.updateScrollTop = this.lastScrollTop;
		this.updateScrollLeft = this.lastScrollLeft;	

		//this.updateColumnWidths();		
		this.update();		
		
		this.columnsStateCallback = columnsStateCallback;
		
		this.updateSelectCheckBoxes();				
		
		this.initCells(this.getBodyTable());
		
	},
	
	initColumns: function(columnsData) {
		var r = this.getColumnsRow();
		var ths = getChildren(r, "TH");
		for (var i = 0; i < columnsData.length && i < ths.length; ++i) {
			var c = columnsData[i];
			var th = ths[i];
			th.imxtMinSize = c.minSize;
			th.imxtMaxSize = c.maxSize;
			th.imxtId = c.id;
			th.imxtResizable = c.resizable;
			th.imxtReorderable = c.reorderable;
		}
	},
	
	/**
	 * Returns the element that contains the XTable
	 */
	getTopContainer: function() {		
		return Wicket.$(this.id);
	},
	
	/**
	 * Returns the table that contains header (part that doesn't scroll vertically, e.g. columns)
	 */
	getHeadTable: function() {
		return this.getElement("table", "imxt-head");
	},

	/**
	 * Returns the table that contains the body (part that scrolls vertically)
	 */	
	getBodyTable: function() {
		return this.getElement("table", "imxt-body");
	},	
	
	/**
	 * Returns the elements that have specified tagName and css class.
	 * Optionally root parameter can narrow the searching scope.
         * @param tagName
         * @param className
         * @param root
	 */ 
	getElements: function(tagName, className, root) {				
		if (typeof(root) === "undefined")
			root = this.getTopContainer();
		
		if (typeof className === 'undefined' || className === null) {
			return $(root).find(tagName);
		} else {
			var selector = tagName + '.' + className;
			return $(root).find(selector);
		}		
	},
	
	/**
	 * Mapping between cacheId and real Id. We need caching because retrieving the elements
	 * based on the CSS class name is relative expensive. So once we retrieve en element 
	 * (single component, not a group of components), we set it's id to generated id that
	 * can be used to retrieve the element quickly. If element has already id set, we use
	 * idCache to map between elementId (real Id) and the generatedId (cacheId).
	 */
	idCache: new Object(),

	/**
	 * Constructs cache id unique for this XTable.
         * @param suffix
	 */
	getCacheId: function(suffix) {
		return "imxt-" + this.id + "-" + suffix;
	},

	/**
	 * Returns element with specified id. If the element can't be found based on the id,
	 * it calls getElementFunc to find the element and then either sets element id or
	 * put a mapping to real id to idCache.
         * @param id
         * @param {function} getElementFunc
	 */
	getElementCached: function(id, getElementFunc) {
		var actualId = this.idCache[id] || id;
		
		var e = Wicket.$(actualId);
				
		if (typeof e === 'undefined' || e === null) {
		
			e = getElementFunc();
			actualId = e.getAttribute("id");
									
			
			if (!isEmpty(actualId)) {
				this.idCache[id] = actualId;
			} else {
				e.setAttribute("id", id);
			}	
		}
		
		return e;
	},	
	
	/**
	 * Returns element in this XTable with specified tagName and className,
	 * optionally narrowing the search scope with the root parameter.
         * @param tagName
         * @param className
         * @param root
	 */	
	getElement: function(tagName, className, root) {
		var id = this.getCacheId(className);
		return this.getElementCached(id, Wicket.bind(function() {
			return this.getElements(tagName, className, root)[0];
		},this));						
	},
	
	/**
	 * Returns array of the TR elements inside the head table.
	 */
	getHeadRows: function() {
		var id = this.getCacheId("head-first-row");
		var firstRow = this.getElementCached(id, Wicket.bind(function() {
			var head = this.getHeadTable();
			return head.getElementsByTagName("tr")[0];
		},this));
		
		return getChildren(firstRow.parentNode, "TR");		
	},
	
	/**
	 * Returns array of the TR elements inside the body table.
	 */
	getBodyRows: function() {
		var id = this.getCacheId("body-first-row");
		var firstRow = this.getElementCached(id, Wicket.bind(function() {
			var body = this.getBodyTable();
			return body.getElementsByTagName("tr")[0];
		},this));
		
		return getChildren(firstRow.parentNode, "TR");
	},
	
	/**
	 * Returns the TR element that contains column headers.
	 */
	getColumnsRow: function() {
		return this.getElement("tr", "imxt-columns", this.getHeadTable());
	},
	
	updateSelectCheckBoxes: function() {
		var head = this.getElements("input", "imxt-select", this.getHeadTable());		
		
		if (head.length > 0) {
			var body = this.getElements("input", "imxt-select", this.getBodyTable());
									
			var selected = body.length > 0;
			for (var i = 0; i < body.length; ++i) {
				if (body[i].checked != true) {					
					selected = false;
					break;
				}
			}
									
			for (var i = 0; i < head.length; ++i) {
				head[i].checked = selected;
			}
		}
	},
	
	/**
	 * Returns array of column width strings.
	 * E.g. ["100px", "300px", "250px"];
	 */
	getColumnWidths: function() {
		var row = this.getColumnsRow();

		var ths = getChildren(row, "TH");
		var res = new Array();
		for (var i = 0; i < ths.length; ++i) {
			res.push(ths[i].offsetWidth);
		}
		return res;
	},
	
	/**
	 * Updates the widths of columns in table body according to the headers 
	 */
	updateColumnWidths: function() {				
		
		var widths = this.getColumnWidths();		
								
		var scroll;
		 		
		if (arrayEq(this.prevColumnWidths, widths) == false) {		

			var header = this.getColumnsRow(); 				
			var rows = this.getBodyRows();
										
			if (rows.length > 0) {
				var first = rows[0];
				var ths = getChildren(header, "TH");
				var tds = getChildren(first, "TD");
				
				for (var i = 0; i < ths.length - 2; ++i) {
					var h = ths[i];
					var r = tds[i];
			
					var width = widths[i] + "px";							
			
					r.style.width = width;
				}
			}
			
			this.prevColumnWidths = widths;
		} 
	},
	
	/**
	 * Updates the table state
         * @param force
	 */
	updateInternal: function(force) {
 		var topContainer = this.getTopContainer();
		
		if (topContainer == null) {
			// the table was probably removed
			return false;
		}

		var head = this.getHeadTable();
		var body = this.getBodyTable();
		
  		var headContainer1 = this.getElement("div", "imxt-head-container1");
  		var headContainer2 = this.getElement("div", "imxt-head-container2");
  		var bodyContainer1 = this.getElement("div", "imxt-body-container1");

		bodyContainer1.style.width = topContainer.offsetWidth + "px";
	
		var padding = (bodyContainer1.offsetWidth - bodyContainer1.clientWidth);

		// count new header width
		var newWidth = (body.offsetWidth + padding);
		
		if (padding > 0) {
			// compensate for scrollbar
			var e = this.getElement("th", "imxt-padding-right", head);		
			e.style.width = padding + "px";			
		}
 			 		 			 		
 		this.updateColumnWidths();
 		
		this.updatePreservedScrollOffsets(); 				

 		
 		// scroll the header if body is scrolled
		var scroll = bodyContainer1.scrollLeft;
		
		if (headContainer2.scrollLeft != scroll)
			headContainer2.scrollLeft = scroll; 							

		// update ok
		return true; 
	},
	
	/**
	 * After (ajax) refresh this updates the scroll offsets to match previous offset, so that the scrolling
	 * position remains same after component update.
	 */
	updatePreservedScrollOffsets: function() {		
	 	if ($.isNumeric(this.updateScrollTop) ||
	 			$.isNumeric(this.updateScrollLeft)) {
 		    
 		    var bodyContainer1 = this.getElement("div", "imxt-body-container1");
 		    
 			bodyContainer1.style.visibility="hidden"; 			
 			
 			if ($.isNumeric(this.updateScrollLeft)) {
 				bodyContainer1.scrollLeft = this.lastScrollLeft;
 				this.updateScrollLeft = null;
 			}
 			
 			if ($.isNumeric(this.updateScrollTop)) { 			
 				bodyContainer1.scrollTop = this.updateScrollTop;
 				this.updateScrollTop = null;
 			}
 			
 			bodyContainer1.style.visibility="visible";
 			
 			bodyContainer1.imxtOldOffsetWidth = null; 			 			
 		}
	},
	
	/**
	 * Updates the XTable. This is called during resizing, after column drag and also periodically.
         * @param force
	 */
	update: function(force) {
		if (this.dragging === true) {
			return true;
		} else {
			return this.updateInternal(force);
		}
	},

	getColumnMinSize: function(column) {
		var res = column.imxtMinSize;
		return $.isNumeric(res) && res > 0 ? res : 20;
	},
	
	getColumnMaxSize: function(column) {
		var res = column.imxtMaxSize;
		return $.isNumeric(res) && res > 0 ? res : -1;
	},

	getResizeProxy: function() {
		var e = Wicket.$("imxt-resize-proxy");
		if (typeof e === 'undefined' || e === null) {
			e = document.createElement("div");
			e.setAttribute("id", "imxt-resize-proxy");
			document.body.appendChild(e);
		}
		return e;
	},
	
	showResizeProxy: function(column) {
		var headContainer = this.getElement("div", "imxt-head-container");
		var bodyContainer = this.getElement("div", "imxt-body-container1");
		var columnPos = $(column).offset();
		var headContainerPos = $(headContainer).offset();
		var bodyContainerPos = $(bodyContainer).offset();
		
		var proxy = this.getResizeProxy();
		proxy.style.display = "";
		proxy.style.height = (bodyContainer.offsetHeight + bodyContainerPos.top - headContainerPos.top) + "px";
		
		var top = headContainerPos.top;
		proxy.style.top = top + "px";		
		proxy.style.left = (columnPos.left + column.imxtWidth - proxy.offsetWidth) + "px"; 			
	},
	
	hideResizeProxy: function(column) {
		var proxy = this.getResizeProxy();
		proxy.style.display="none";
	},

	/**
	 * Called when column resizing handle dragging started.  
         * @param handle
	 */
	handleDragBegin: function(handle) {
		
            Wicket.Focus.lastFocusId = null;

            this.dragging = true;		

            var column = handle;
            // find the column itself      			
            do {
                    column = column.parentNode; 				
            } while (column.tagName.toLowerCase() !== "th");

            // add the css dragging class 	      	      	      
            addClass(column, "imxt-dragging");
            addClass(this.getColumnsRow(), "imxt-dragging");

            column.min = this.getColumnMinSize(column);
                    column.max = this.getColumnMaxSize(column);	      

            column.imxtWidth = parseInt(column.style.width, 10);
            this.showResizeProxy(column);   				
	},
	
	/**
	 * Called when column resizing handle dragging ended
         * @param handle
	 */
	handleDragEnd: function(handle) {
		this.dragging = false;
		
		this.hideResizeProxy();
		
		var column = handle;
		do {
      		column = column.parentNode; 				
      	} while (column.tagName.toLowerCase() !== "th");

		column.style.width = column.imxtWidth + "px";
		
		// remove the css classes
		removeClass(column, "imxt-dragging");
		removeClass(this.getColumnsRow(), "imxt-dragging");
		
		// fix for IE quirks mode (to force recalculating of table layout)
		this.getElement("div", "imxt-body-container1").imxtOldOffsetWidth = null;
		
		this.updateInternal();
		this.submitColumnState();
	},
	
	/**
	 * Called during a column handle dragging
         * @param handle
         * @param dX
         * @param dY
	 */
	handleDrag: function(handle, dX, dY) {
            var column = handle;
            do {
      		column = column.parentNode; 				
            } while (column.tagName.toLowerCase() !== "th");      							      		
      	
            var current = column.imxtWidth;
            var newWidth = current + dX;
            var delta = 0;

            if (column.min != -1 && newWidth < column.min) {
                    delta = column.min - newWidth;
                    newWidth = column.min;
            } else if (column.max != -1 && newWidth > column.max) {		
                    delta = column.max - newWidth;
                    newWidth = column.max;
            }
      		      	
            column.imxtWidth = newWidth;
            this.showResizeProxy(column);

            return [delta, 0];		      			
	},
	
	handleDoubleClick: function(event) {
		var table = event.data;		
		var column = event.target;
		do {
      		column = column.parentNode; 				
      	} while (column.tagName.toLowerCase() !== "th");      	
      	
      	var min = table.getColumnMinSize(column);
      	
      	column.style.width = min + "px";
      	table.updateInternal();
		table.submitColumnState();
	},
	
	/**
	 * Returns the array of TH elements from the column row
	 */
	getColumns: function() {
		var columnsRow = this.getColumnsRow();
		return getChildren(columnsRow, "TH");
	},
	
	/**
	 * Returns array of handle links inside the columns row
	 */
	getHandles: function() {
		var columns = this.getColumnsRow();
		var handles = this.getElements("a", "imxt-handle", columns);
		return handles;
	},
	
	/**
	 * Calback invoked when the body is scrolled.
         * @param event
	 */
	onScroll: function(event) {
		var bodyContainer = event.data.getElement("div", "imxt-body-container1");
		// only update on horizontal scrolling
		if (bodyContainer.scrollLeft != bodyContainer.imxtPrevScrollLeft) {
			event.data.update();
			bodyContainer.imxtPrevScrollLeft = bodyContainer.scrollLeft;
		}
		
		event.data.lastScrollLeft = bodyContainer.scrollLeft;
		event.data.lastScrollTop = bodyContainer.scrollTop;		
	},
			
	/**
	 * Attach the various event handler to elements in this XTable.
	 */
	attachEventHandlers: function() {
		
		// 1 scroll handler
		var bodyContainer = this.getElement("div", "imxt-body-container1");				
						
		if (bodyContainer.imxtAttached != true) {
			addListener(bodyContainer, "scroll", this.onScroll, this, true);			
			bodyContainer.imxtAttached = true;
		}		
		
				
		// 2 column resize handlers
		var handles = this.getHandles();
						
		for (var i = 0; i < handles.length; ++i) {
		
			var h = handles[i];						
			
			if (h.imxtAttached != true) {				
				new InMethod.Drag(h, this.handleDragBegin, this.handleDragEnd, 
				                     this.handleDrag, this);
				                     
				addListener(h, "dblclick", this.handleDoubleClick, this); 
				                     				
				h.imxtAttached = true;
			}
		}
			
		// 3 columns reorder handlers		
		var columns = getChildren(this.getColumnsRow(), "TH");
		
		for (var i = 0; i < columns.length; ++i) {
			var c = columns[i];			
			if (!hasClass(c, "imxt-padding") && c.imxtReorderable == true && c.imxtAttached != true) {
				new InMethod.Drag(c, this.columnDragBegin, this.columnDragEnd, this.columnDrag, this);								
				c.imxtAttached = true;
			}
		}
	},
	
	/**
	 * Called when user starts to drag a column (reorder)
         * @param column
         * @param event
	 */
	columnDragBegin: function(column, event) {
		Wicket.Focus.lastFocusId = null;
				
		addClass(column, "imxt-dragging");
		this.dragging = true;
		this.initDragProxy(event, column);
		
		this.columnDragBeginX = event.pageX;
		this.columnDragBeginY = event.pageY;
		this.columnDragBeginTime = new Date().getTime();
	},
	
	/**
	 * Called after user finished dragging a column (reorder)
         * @param column
	 */
	columnDragEnd: function(column) {		
	
		this.cachedColumns = null;
	
		// targetColumn is array [column, position] where position is 0 or 1
		var target = this.targetColumn;
		
		var submitState = false;
		
		if ($.isArray(target)) {
		
			// find the delta - how many columns right or left has the source
			// column been moved 		
			var delta = 0;
			var c = column;
			
			// try moving left
			do {
				c = prevSibling(c);
				--delta;
			} while (typeof c !== 'undefined' && c !== null && c != target[0]);
		
			// if the column hasn't been found in left siblings, try moving right
			if (typeof c === 'undefined' || c === null) {
			 	c = column;
				delta = 0;
				do {
					c = nextSibling(c);
					++delta;
				} while (typeof c !== 'undefined' && c !== null && c != target[0]);
				--delta;
			}
			
			// if we found target column, do the actual reordering
			if (typeof c !== 'undefined' && c !== null) {
				delta += target[1];
				this.moveColumn(column, delta);
				submitState = true;
			}
		}	
	
		this.dragging = false;
		removeClass(column, "imxt-dragging");
	
		this.hideArrows();
		this.hideDragProxy();
		
		if (submitState) {
		 	this.submitColumnState();
		}
						
	},
	
	/**
	 * Initializes the drag proxy. If the drag proxy doesn't exist yet, creates it. 
	 * This method doesn't make the proxy visible. Proxy is made visible in updateDragProxy.
         * @param ev
         * @param column
	 */
	initDragProxy: function(ev, column) {	
		var p = Wicket.$("imxt-drag-proxy");
		if (typeof p === 'undefined' || p === null) {
			p = document.createElement("div");
			document.body.appendChild(p);
			p.setAttribute("id", "imxt-drag-proxy");
			p.innerHTML = "<div id='imxt-drag-proxy1'></div>";			
		}
		var p1 = Wicket.$("imxt-drag-proxy1");
		var columnPos = $(column).offset();
		var mousePos = [ev.pageX, ev.pageY];
		
		// get the initial proxy position relative to mouse cursor
		ev.data.dragProxyDX = -column.offsetWidth / 2; //columnPos[0] - mousePos[0];
		ev.data.dragProxyDY = -column.offsetHeight / 2; //columnPos[1] - mousePos[1];
		
		// initialize dimensions
		p.style.width = column.offsetWidth + "px";
		p1.style.height = (column.offsetHeight - 3) + "px";
		
		p.style.display = "none";
		this.setPosition(p, [columnPos.left,columnPos.top]);
	},
	
	/**
	 * Moves the drag proxy to mouse cursor position.
         * @param ev
	 */
	updateDragProxy: function(ev) {
		var p = Wicket.$("imxt-drag-proxy");
		p.style.display = "";
			
		this.setPosition(p, [ev.pageX + ev.data.dragProxyDX, ev.pageY + ev.data.dragProxyDY]);				
	},
	
	/**
	 * Hides the drag proxy.
	 */
	hideDragProxy: function() {
		var p = Wicket.$("imxt-drag-proxy");
		p.style.display = "none";
	},
	
	/**
	 * Returns the arrow element with specified id (should be either 'imxt-arrow-down' or 'imxt-arrow-up').
	 * If the arrow element doesn't exist, creates it.
         * @param id
	 */
	getArrow: function(id) {
		var a = Wicket.$(id);
		if (typeof a === 'undefined' || a === null) {
			a = document.createElement("a");
			a.setAttribute("id", id);
			document.body.appendChild(a);
		}
		return a;
	},
	
	setPosition: function(a, xy) {
		$(a).css({left: xy[0], top: xy[1]});
	},
	
	/**
	 * Shows arrows (both up and down) at the specified position)
         * @param column
         * @param pos
	 */
	showArrows: function(column, pos) {
		var a1 = this.getArrow("imxt-arrow-down");
		var a2 = this.getArrow("imxt-arrow-up");
		
		a1.style.display = "";
		a2.style.display = "";
		
		var xy = $(column).offset();
		var x = xy.left + (pos * column.offsetWidth);
		var y = xy.top;
		
		this.setPosition(a1, [x - 7, y - 14]);
		this.setPosition(a2, [x - 7, y - 2 + column.offsetHeight]);
		
		// mark the target column - used in columnDragEnd()
		this.targetColumn = [column, pos];
	},
	
	/**
	 * Hides the arrows
	 */
	hideArrows: function() {
		var a1 = this.getArrow("imxt-arrow-down");
		var a2 = this.getArrow("imxt-arrow-up");
		a1.style.display = "none";
		a2.style.display = "none";
		this.targetColumn = null;
	},
	
	columnAndPrevSiblingsNotReorderable: function(column) {
		while (typeof column !== 'undefined' && column !== null) {
			if (column.imxtReorderable == true) {
				return false;
			}
			column = prevSibling(column);
		}
		return true;
	},
	
	columnAndNextSiblingsNotReorderable: function(column) {
		while (typeof column !== 'undefined' && column !== null) {
			if (column.imxtReorderable == true) {
				return false;
			}
			column = nextSibling(column);
		}
		return true;
	},
	
	/**
	 * Invoked when user is dragging a column (reorder).
         * @param column
         * @param dX
         * @param dY
         * @param event
	 */
	columnDrag: function(column, dX, dY, event) {										
					
		var columns = this.cachedColumns;
		if (typeof(columns) === "undefined" || columns === null) {
			columns = getChildren(this.getColumnsRow(), "TH");
			this.cachedColumns = columns;
		}
			
		var x = event.pageX;
		var y = event.pageY;
		
		if (Math.abs(this.columnDragBeginY - y) < 10 &&
		    Math.abs(this.columnDragBeginX - x) < 15 &&
		    new Date().getTime() - this.columnDragBeginTime < 1000) {
		    return;
		}
		
		// find the column on cursor position
		var c = null;
		for (var i = 0; i < columns.length; ++i) {
			cx = columns[i];
			var pos = $(cx).offset();
			
			if (x >= pos.left && x < pos.left + cx.offsetWidth) {
			    c = cx;
			    break;
			}   
		}		
				
		// if the column is a column other than the column being dragged
		if (typeof c !== 'undefined' && c != column && c !== null) {					
			// find if the cursor is in left or right half of column
			var c_x = $(cx).offset().left;
			var x = event.pageX - c_x;
			var w = c.offsetWidth;
			var pos = x < w / 2 ? 0 : 1;				
	
			if ((pos == 0 && c == nextSibling(column)) ||
			    (pos == 0 && this.columnAndPrevSiblingsNotReorderable(c)) ||
			    (pos == 1 && !hasClass(nextSibling(c), "imxt-reorderable") && this.columnAndPrevSiblingsNotReorderable(c)) || 			    
			    (pos == 1 && c == prevSibling(column)) ||			    
			    (pos == 1 && this.columnAndNextSiblingsNotReorderable(c)) ||
			    (pos == 0 && !hasClass(prevSibling(c), "imxt-reorderable") && this.columnAndNextSiblingsNotReorderable(c)) ||
			    (pos == 1 && hasClass(c, "imxt-padding"))) {
			    // the source column can't be dragged here				
				this.hideArrows();			    
			} else {		
				// this is a valid destination				
				this.showArrows(c, pos);						
			}
		} else {
			this.hideArrows();
		}
		
		// move drag proxy to follow the cursor 
		this.updateDragProxy(event);
	},
	
	getOriginalColSpan: function(column) {
		var res = column.className.match(colSpanRegex);
		if (res) {			
			return parseInt(res[1], 10);
		} else {
			return 1;
		}
	},
	
	/**
	 * Reorders the column according to the delta (positive or negative number)
         * @param column
         * @param delta
	 */
	moveColumn: function(column, delta) {
		while (column.tagName.toLowerCase() !== "th") {
			column = column.parentNode;
		}
				
		// bug in opera - we need to hide both tables during reordering, 
		// otherwise the tables will not be refreshed afterwards
		var bodyContainer1;
		var scroll;
								
		var header = this.getColumnsRow();		
		var ths = getChildren(header, "TH");
		
		// get the source column index
		var index;
		for (var i = 0; i < ths.length; ++i) {
			if (ths[i] == column) {
				index = i;
				break;
			}
		} 		
		
		var other = ths[i + delta];		
						
		var fixSpans = Wicket.bind(function(cells) {
			var hide = 0;
			
			for (var i = 0; i < cells.length; ++i) {
				var cell = cells[i];
				var left = cells.length - i - 1;				
				if (hide > 0) {
					cell.setAttribute("colSpan", 1);
					cell.style.display = "none";
					--hide;
				} else {
					var span = this.getOriginalColSpan(cell);
					if (span > left) {
						span = left;
					}
					hide = span - 1;
					cell.style.display = "";
					cell.setAttribute("colSpan", span > 0 ? span : 1);
				}
			}
			
		}, this);
						
		// helper function that reorders columns in the given rows
		var updateRows = Wicket.bind(function(rows, fixSpans) {			
			for (var j = 0; j < rows.length; ++j) {
				var row = rows[j];
				var tds = getChildren(row, ["TD", "TH"]);
				
				var current = tds[i];
				
				if (typeof(current) === "undefined") {
					continue;
				}
				
				var index = i + delta;
				if (delta > 0) {
					++index;
				}
				
				other = tds[index];		
				
				row.insertBefore(current, other);
												
				if ($.isFunction(fixSpans)) {
					fixSpans(getChildren(row, ["TD", "TH"]));					
				}
			}
		}, this);
		
		// update rows in both tables
		updateRows(this.getHeadRows());
		updateRows(this.getBodyRows(), fixSpans);
				
		// we might have changed the layout
		this.update(true);
		
	},
		
	/**
	 * Returns the state of columns (order and widths) represented as string.
	 */
	getColumnState: function() {
		var r = this.getColumnsRow();
		var ths = getChildren(r, "TH");
		var state = "";		
		for (var i = 0; i < ths.length - 1; ++i) {
			var th = ths[i];
			state += th.imxtId;
			state += ",";
			var width = th.style.width;
			if (width.match(/[\d]+px/))
				state += parseInt(th.style.width, 10);
			else
				state += "-1";
			state += ";";
		};
		return state;
	},
	
	submitColumnState: function() {
		this.columnsStateCallback(this.getColumnState());
	},
	
	/**
	 * Needs to be called when a row was added or updated.
         * @param rowElement
	 */
	rowUpdated: function(rowElement) {
		this.updateSelectCheckBoxes();
		this.initCells(rowElement);
	},
	
	getCellId: function(cell) {
		if (typeof(cell.imxtId) !== "string") {
			var index = 0;
			var c = cell;
			while ((c = prevSibling(c)) != null) {
				++index;				
			}
			
			var row = this.getColumnsRow();
			var headerCells = getChildren(row, "TH");
			
			var headerCell = headerCells[index];
			
			if (typeof(headerCell.imxtId) === "string") {
				cell.imxtId = headerCell.imxtId;
			} else {
				cell.imxtId = "";
			}
			
		} 
		return cell.imxtId;
	},
	
	initCellsEventHandler: function(event) {
        // Needed because when jquery handles cell event, returns cell contents instead
        // of td cell.
        var cell = $(event.target).closest('td.imxt-cell')[0];
        var table = event.data;
        cell.parentNode.imxtClickedColumn = table.getCellId(cell);
	},
	
	initCells: function(container) {
		
		var elements = this.getElements("td", "imxt-cell", container);
				
		for (var i = 0; i < elements.length; ++i) {
			var cell = elements[i];
			if (cell.imxtInitialized != true) {
				//var addListener = function(element, event, fn, obj, override) {				
				addListener(cell, "click", this.initCellsEventHandler, this, false);
				cell.imxtInitialized = true;
			}
		}
			
	}
};

InMethod.XTable.canSelectRow = function(event) {
	var e = Wicket.Event.fix(event);
	var element = e.target ? e.target : e.srcElement;
	
	while (typeof element !== 'undefined' && element !== null && element != document.documentElement) {
		var tn = element.tagName.toLowerCase();
		if (tn === "a" || tn === "input" || tn === "select" || tn === "button") {
			return false;
		}
		element = element.parentNode;
	}
	
	return true;
};

InMethod.XTableManager = Wicket.Class.create();

InMethod.XTableManager.prototype = {

	current: new Object(),

	initialize: function() {
		window.setInterval(Wicket.bind(this.update, this), 100);
	},
	
	update: function() {						
		for (var property in this.current) {
			var table = this.current[property];
			if (typeof table !== 'undefined' && table !== null) {
				var res = table.update();
				if (res != true) {
					this.current[property] = null;
				}
			}
		};
	},
	
	updateTreeColumns: function() {
		for (var property in this.current) {
			var table = this.current[property];
			if (typeof table !== 'undefined' && table !== null) {
				table.updateTreeColumns();
			}
		};
	},
	
	register: function(id, columnsData, columnsStateCallback) {
		var existing = this.current[id];
		if (typeof existing === 'undefined' || existing === null) {
                    existing = new InMethod.XTable(id, columnsData, columnsStateCallback);
                    this.current[id] = existing;
		} else {
                    // update existing
                    existing.initialize(id, columnsData, columnsStateCallback);
		}
	},
	
	
	updateRow: function(id, row) {
		var table = this.current[id];
		if (typeof table !== 'undefined' && table !== null) {
			table.rowUpdated(row);
		}
	}
};

InMethod.XTableManager.instance = new InMethod.XTableManager(); 

InMethod.setCursorPos = function(elm, begin, end) {
    if (typeof elm.selectionStart !== "undefined" && typeof elm.selectionEnd !== "undefined")  {
        elm.setSelectionRange (begin, end);
        elm.focus ();
    } else if (document.selection && document.selection.createRange) {
        var range = elm.createTextRange ();
        range.move ("character", begin);
        range.moveEnd ("character", end - begin);
        range.select ();
    }
};

function findParent(node, tagName)  {
    return $(node).parents(tagName);
}

onKeyEvent = function(element, event) {
	
	var e = Wicket.Event.fix(event)
	var key = event.keyCode;
	
	if (key == 13 || key == 27) {					
		
		var row = element;
		
		do {
			row = findParent(row, "TR");		
		} while (typeof row !== 'undefined' && row !== null 
                        && !hasClass(findParent(row, "TABLE"), "imxt-body"));
		
		if (typeof row !== 'undefined' && row !== null) {
		
			var elements;
			if (key == 13) {
                                elements = $(row).find("a.imxt-edit-submit");
			} else {
                                elements = $(row).find("a.imxt-edit-cancel");
			}
			
			if (typeof elements !== 'undefined' && elements !== null && elements.length > 0) {
				$(elements[0]).click();
			}		
		}		
	}	
};


InMethod.editKeyUp = function(element, event) {
		
	onKeyEvent(element, event);
	
	var e = Wicket.Event.fix(event)
	var key = event.keyCode;
	
	if (key == 13 || key == 27) {
		return false;
	} else {
		return true;
	}		
};

InMethod.editKeyPress = function(element, event) {
	
	var e = Wicket.Event.fix(event)
	var key = event.keyCode;
	
	if (key == 13 || key == 27) {
		return false;
	} else {
		return true;
	}	
};
})();
