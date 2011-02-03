/*
 * File:        FixedColumns.js
 * Version:     1.0.2
 * Description: "Fix" columns on the left of a scrolling DataTable
 * Author:      Allan Jardine (www.sprymedia.co.uk)
 * Created:     Sat Sep 18 09:28:54 BST 2010
 * Language:    Javascript
 * License:     GPL v2 or BSD 3 point style
 * Project:     Just a little bit of fun - enjoy :-)
 * Contact:     www.sprymedia.co.uk/contact
 * 
 * Copyright 2010 Allan Jardine, all rights reserved.
 */

var FixedColumns = function ( oDT, oInit ) {
	/* Sanity check - you just know it will happen */
	if ( typeof this._fnConstruct != 'function' )
	{
		alert( "FixedColumns warning: FixedColumns must be initialised with the 'new' keyword." );
		return;
	}
	
	if ( typeof oInit == 'undefined' )
	{
		oInit = {};
	}
	
	/**
	 * @namespace Settings object which contains customisable information for FixedColumns instance
	 */
	this.s = {
		/** 
		 * DataTables settings objects
     *  @property dt
     *  @type     object
     *  @default  null
		 */
		"dt": oDT.fnSettings(),
		
		/** 
		 * Number of columns to fix in position
     *  @property columns
     *  @type     int
     *  @default  1
		 */
		"columns": 1
	};
	
	
	/**
	 * @namespace Common and useful DOM elements for the class instance
	 */
	this.dom = {
		/**
		 * DataTables scrolling element
		 *  @property scroller
		 *  @type     node
		 *  @default  null
		 */
		"scroller": null,
		
		/**
		 * DataTables header table
		 *  @property header
		 *  @type     node
		 *  @default  null
		 */
		"header": null,
		
		/**
		 * DataTables body table
		 *  @property body
		 *  @type     node
		 *  @default  null
		 */
		"body": null,
		
		/**
		 * DataTables footer table
		 *  @property footer
		 *  @type     node
		 *  @default  null
		 */
		"footer": null,
		
		/**
		 * @namespace Cloned table nodes
		 */
		"clone": {
			/**
			 * Cloned header table
			 *  @property header
			 *  @type     node
			 *  @default  null
			 */
			"header": null,
		
			/**
			 * Cloned body table
			 *  @property body
			 *  @type     node
			 *  @default  null
			 */
			"body": null,
		
			/**
			 * Cloned footer table
			 *  @property footer
			 *  @type     node
			 *  @default  null
			 */
			"footer": null
		}
	};
	
	/* Let's do it */
	this._fnConstruct( oInit );
};


FixedColumns.prototype = {
	/**
	 * Initialisation for FixedColumns
	 *  @method  _fnConstruct
	 *  @param   {Object} oInit User settings for initialisation
	 *  @returns void
	 */
	"_fnConstruct": function ( oInit )
	{
		var that = this;
		
		/* Sanity checking */
		if ( typeof this.s.dt.oInstance.fnVersionCheck != 'function' ||
		     this.s.dt.oInstance.fnVersionCheck( '1.7.0' ) !== true )
		{
			alert( "FixedColumns 2 required DataTables 1.7.0 or later. "+
				"Please upgrade your DataTables installation" );
			return;
		}
		
		if ( this.s.dt.oScroll.sX === "" )
		{
			this.s.dt.oInstance.oApi._fnLog( this.s.dt, 1, "FixedColumns is not needed (no "+
				"x-scrolling in DataTables enabled), so no action will be taken. Use 'FixedHeader' for "+
				"column fixing when scrolling is not enabled" );
			return;
		}
		
		if ( typeof oInit.columns != 'undefined' )
		{
			if ( oInit.columns < 1 )
			{
				this.s.dt.oInstance.oApi._fnLog( this.s.dt, 1, "FixedColumns is not needed (no "+
					"columns to be fixed), so no action will be taken" );
				return;
			}
			this.s.columns = oInit.columns;
		}
		
		/* Set up the DOM as we need it and cache nodes */
		this.dom.body = this.s.dt.nTable;
		this.dom.scroller = this.dom.body.parentNode;
		this.dom.scroller.style.position = "relative";
		
		this.dom.header = this.s.dt.nTHead.parentNode;
		this.dom.header.parentNode.parentNode.style.position = "relative";
		
		if ( this.s.dt.nTFoot )
		{
			this.dom.footer = this.s.dt.nTFoot.parentNode;
			this.dom.footer.parentNode.parentNode.style.position = "relative";
		}
		
		/* Event handlers */
		$(this.dom.scroller).scroll( function () {
			that._fnScroll.call( that );
		} );
		
		this.s.dt.aoDrawCallback.push( {
			"fn": function () {
				that._fnClone.call( that );
				that._fnScroll.call( that );
			},
			"sName": "FixedColumns"
		} );
		
		/* Get things right to start with */
		this._fnClone();
		this._fnScroll();
	},
	
	
	/**
	 * Clone the DataTable nodes and place them in the DOM (sized correctly)
	 *  @method  _fnClone
	 *  @returns void
	 *  @private
	 */
	"_fnClone": function ()
	{
		var
			that = this,
			iTableWidth = 0,
			aiCellWidth = [],
			i, iLen, jq,
			bRubbishOldIE = ($.browser.msie && ($.browser.version == "6.0" || $.browser.version == "7.0"));
		
		/* Grab the widths that we are going to need */
		for ( i=0, iLen=this.s.columns ; i<iLen ; i++ )
		{
			jq = $('thead th:eq('+i+')', this.dom.header);
			iTableWidth += jq.outerWidth();
			aiCellWidth.push( jq.width() );
		}
		
		/* Header */
		if ( this.dom.clone.header !== null )
		{
			this.dom.clone.header.parentNode.removeChild( this.dom.clone.header );
		}
		this.dom.clone.header = $(this.dom.header).clone(true)[0];
		this.dom.clone.header.className += " FixedColumns_Cloned";
		
		$('thead tr:eq(0)', this.dom.clone.header).each( function () {
			$('th:gt('+(that.s.columns-1)+')', this).remove();
		} );
		$('tr', this.dom.clone.header).height( $(that.dom.header).height() );
		
		$('thead tr:gt(0)', this.dom.clone.header).remove();
		
		$('thead th', this.dom.clone.header).each( function (i) {
			this.style.width = aiCellWidth[i]+"px";
		} );
		
		this.dom.clone.header.style.position = "absolute";
		this.dom.clone.header.style.top = "0px";
		this.dom.clone.header.style.left = "0px";
		this.dom.clone.header.style.width = iTableWidth+"px";
		this.dom.header.parentNode.appendChild( this.dom.clone.header );
		
		/* Body */
		/* Remove any heights which have been applied already and let the browser figure it out */
		$('tbody tr', that.dom.body).css('height', 'auto');
		
		if ( this.dom.clone.body !== null )
		{
			this.dom.clone.body.parentNode.removeChild( this.dom.clone.body );
			this.dom.clone.body = null;
		}
		
		if ( this.s.dt.aiDisplay.length > 0 )
		{
			this.dom.clone.body = $(this.dom.body).clone(true)[0];
			this.dom.clone.body.className += " FixedColumns_Cloned";
			if ( this.dom.clone.body.getAttribute('id') !== null )
			{
				this.dom.clone.body.removeAttribute('id');
			}
			
			$('thead tr:eq(0)', this.dom.clone.body).each( function () {
				$('th:gt('+(that.s.columns-1)+')', this).remove();
			} );
			
			$('thead tr:gt(0)', this.dom.clone.body).remove();
			
			var jqBoxHack = $('tbody tr:eq(0) td:eq(0)', that.dom.body);
			var iBoxHack = jqBoxHack.outerHeight() - jqBoxHack.height();
			
			/* Remove cells which are not needed and copy the height from the original table */
			$('tbody tr', this.dom.clone.body).each( function (k) {
				$('td:gt('+(that.s.columns-1)+')', this).remove();
				
				/* Can we use some kind of object detection here?! This is very nasty - damn browsers */
				if ( $.browser.mozilla || $.browser.opera )
				{
					$('td', this).height( $('tbody tr:eq('+k+')', that.dom.body).outerHeight() );
				}
				else
				{
					$('td', this).height( $('tbody tr:eq('+k+')', that.dom.body).outerHeight() - iBoxHack );
				}
				
				/* It's only really IE8 and Firefox which need this, but to simplify, lets apply to all.
				 * The reason it is needed at all is sub-pixel rounded, which is done differently in every
				 * browser... Except of course IE6 and IE7 - applying the height to them causes the cell
				 * size to grow, but they don't mess around with sub-pixels so we can do nothing.
				 */
				if ( !bRubbishOldIE )
				{
					$('tbody tr:eq('+k+')', that.dom.body).height( $('tbody tr:eq('+k+')', that.dom.body).outerHeight() );		
				}
			} );
			
			$('tfoot tr:eq(0)', this.dom.clone.body).each( function () {
				$('th:gt('+(that.s.columns-1)+')', this).remove();
			} );
			
			$('tfoot tr:gt(0)', this.dom.clone.body).remove();
			
			
			this.dom.clone.body.style.position = "absolute";
			this.dom.clone.body.style.top = "0px";
			this.dom.clone.body.style.left = "0px";
			this.dom.clone.body.style.width = iTableWidth+"px";
			this.dom.body.parentNode.appendChild( this.dom.clone.body );
		}
		
		/* Footer */
		if ( this.s.dt.nTFoot !== null )
		{
			if ( this.dom.clone.footer !== null )
			{
				this.dom.clone.footer.parentNode.removeChild( this.dom.clone.footer );
			}
			this.dom.clone.footer = $(this.dom.footer).clone(true)[0];
			this.dom.clone.footer.className += " FixedColumns_Cloned";
			
			$('tfoot tr:eq(0)', this.dom.clone.footer).each( function () {
				$('th:gt('+(that.s.columns-1)+')', this).remove();
				$(this).height( $(that.dom.footer).height() );
			} );
			$('tr', this.dom.clone.footer).height( $(that.dom.footer).height() );
			
			$('tfoot tr:gt(0)', this.dom.clone.footer).remove();
			
			$('tfoot th', this.dom.clone.footer).each( function (i) {
				this.style.width = aiCellWidth[i]+"px";
			} );
			
			this.dom.clone.footer.style.position = "absolute";
			this.dom.clone.footer.style.top = "0px";
			this.dom.clone.footer.style.left = "0px";
			this.dom.clone.footer.style.width = iTableWidth+"px";
			this.dom.footer.parentNode.appendChild( this.dom.clone.footer );
		}
	},
	
	
	/**
	 * Set the absolute position of the fixed column tables when scrolling the DataTable
	 *  @method  _fnScroll
	 *  @returns void
	 *  @private
	 */
	"_fnScroll": function ()
	{
		var iScrollLeft = $(this.dom.scroller).scrollLeft();
		
		this.dom.clone.header.style.left = iScrollLeft+"px";
		if ( this.dom.clone.body !== null )
		{
			this.dom.clone.body.style.left = iScrollLeft+"px";
		}
		if ( this.dom.footer )
		{
			this.dom.clone.footer.style.left = iScrollLeft+"px";
		}
	}
};
