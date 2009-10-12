/*!
* jquery.fixedHeaderTable. The jQuery fixedHeaderTable plugin
*
* Copyright (c) 2009 Mark Malek
* http://fixedheadertable.mmalek.com
*
* Licensed under MIT
* http://www.opensource.org/licenses/mit-license.php
*
* Launch  : October 2009
* Version : 0.1.1 beta
* Released: TBA
*/
(function($)
{

	$.fn.fixedHeaderTable = function(options) {
		var defaults = {
			loader: false,
			footer: false,
			colBorder: true,
			cloneHeaderToFooter: false,
			autoResize: false
		};
		
		var options = $.extend(defaults, options); // get the defaults or any user set options
		
		return this.each(function() {
			var obj = $(this); // the jQuery object the user calls fixedHeaderTable on
			
			buildTable(obj,options);
			
			if(options.autoResize == true) {
				$(window).resize( function() {
					if (table.resizeTable) {
						// if a timeOut is active cancel it because the browser is still being resized
						clearTimeout(table.resizeTable);
					}
				
					// setTimeout is used for resizing because some browsers will call resize() while the browser is still being resized which causes performance issues.
					// if the browser hasn't been resized for 200ms then resize the table
					table.resizeTable = setTimeout(function() {
					
						buildTable(obj,options);
						
					}, 200);
				});
			}
		});
	};
	
	var table = function() {
		this.resizeTable;
	}
	
	function buildTable(obj,options) {
		var objClass = obj.attr('class');
			
		var hasTable = obj.find("table").size() > 0; // returns true if there is a table
		var hasTHead = obj.find("thead").size() > 0; // returns true if there is a thead
		var hasTBody = obj.find("tbody").size() > 0; // returns true if there is a tbody
			
		if(hasTable && hasTHead && hasTBody) {
			var parentDivWidth = obj.width()-5; // get the width of the parent DIV
			var parentDivHeight = obj.height()-5; // get the height of the parent DIV
			var tableBodyWidth = parentDivWidth; // width of the div surrounding the tbody (overflow:auto)
			
			obj.css('position', 'relative'); // set the jQuery object the user passsed in to position:relative (just incase they did not set it in their stylesheet)
			
			if (obj.find('.fht_parent').size() == 0) {
				// if returns false then the plugin has not been used on this jQuery object
				obj.find('table').wrap('<div class="fht_parent"><div class="fht_table_body"></div></div>');
			}
			obj.find('.fht_parent').css('width', parentDivWidth+'px'); // set the width of the parent div
			obj.find('.fht_table_body').css('width', parentDivWidth+'px'); // set the width of the main table body (where the data will be displayed)
			
			var tableWidthNoScroll = parentDivWidth; // this is the width of the table with no scrollbar (used for the fixed header)
			
			obj.find('.fht_parent .fht_table_body table').addClass('fht_orig_table'); // add a class to identify the orignal table later on
			
			if(options.loader) {
				obj.find('.fht_parent').prepend('<div class="fht_loader"></div>');
				obj.find('.fht_loader').css({'width':parentDivWidth+'px', 'height': parentDivHeight+'px'});
			}
			
			obj.attr('id', obj.attr('class'));
			var tableWidthScroll = parentDivWidth;
			
			/*if (obj.find('.fht_parent .fht_orig_table').height() < obj.find('.fht_parent .fht_table_body').height()) {
				obj.find('.fht_parent table').css('width', tableWidthNoScroll+'px');
			}*/
			
			if ($.browser.msie == true) {
				// if IE subtract an additional 2px from the table to compensate for the scrollbar.  Allows the outside border of the table to be flush with the outside border of the fixed header
				// if IE subtract 15px to compensate for the scrollbar
				tableWidthScroll = tableWidthScroll - 20; // default for IE
			}
			else if (jQuery.browser.safari == true) {
				// if Safari subtract 14px to compensate for the scrollbar
				tableWidthScroll = tableWidthScroll - 16; // default for Safari
			}
			else {
				// if everything else subtract 15px to compensate for the scrollbar
				tableWidthScroll = tableWidthScroll - 19; // default for everyone else
			}

			obj.find('table.fht_orig_table').css({'width': tableWidthScroll+'px'}); // set the width of the table minus the scrollbar
			obj.find('table tbody tr:even td').addClass('even');
			obj.find('table tbody tr:odd td').addClass('odd');

			if (obj.find('table tbody tr td div.tableData').size() > 0 == false) {
				obj.find('table tbody tr td').wrapInner('<div class="tableData"><p class="tableData"></p></div>');
			}
			else {
				obj.find('table tbody tr td div.tableData').css('width','auto');
			}
			
			obj.find('table.fht_orig_table thead tr').css('display', '');
			
			if (obj.find('table thead tr th div.tableHeader').size() > 0 == false) {
				obj.find('table thead th').wrapInner('<div class="tableHeader"><p class="tableHeader"></p></div>');
			}
			else {
				obj.find('div.tableHeader').css('width', 'auto');
			}
			
			if (options.colBorder) {
				obj.find('.fht_parent table tr td:not(:last-child)').addClass('borderRight');
				obj.find('.fht_parent table tr th:not(:last-child)').addClass('borderRight');
			}
			
			obj.find('.fht_fixed_header_table_parent').remove();
			
			var html = "";
			html += "<div class='fht_fixed_header_table_parent'>"; // wraps around the entire fixed header
			html += "<!--[if IE]><div class='fht_top_right_header'></div><![endif]-->"; // adds a rounded corner to the top right of the header
			html += "<!--[if IE]><div class='fht_top_left_header'></div><![endif]-->"; // adds a rounded corner to the top left of the header
			html += "<div class='fht_fixed_header_table_border'>"; // creates the border for the header
			html += "<table class='fht_fixed_header_table'>"; // holds the thead that is cloned from the original table body
			html += "</table></div></div>"; // close all open div's and table tags
			
			obj.find('.fht_parent').prepend(html);
			
			obj.find('.fht_fixed_header_table_border').css('width', tableWidthScroll + 'px');
			
			obj.find('.fht_fixed_header_table_parent').css('width', parentDivWidth+'px');
			obj.find('table.fht_fixed_header_table').empty();
			
			obj.find('.fht_parent table thead').clone().prependTo('.' + objClass + ' .fht_fixed_header_table');
			
			obj.find('table.fht_fixed_header_table').css({'width': tableWidthScroll+'px'});

			var x = 0;
			var widthHidden = new Array();
			obj.find('.fht_parent table.fht_orig_table th').each(function() {
				if($(this).hasClass('th'+x) == false) {
					$(this).addClass('th'+x); // used to identify which column we are looking at
				}
				
				widthHidden[x] = $(this).width();
				x++;
			});
			
			var i = 0;
			var width = new Array();
			obj.find('.fht_parent table.fht_fixed_header_table th').each(function() {
				if($(this).hasClass('th'+i) == false) {
					$(this).addClass('th'+i);
				}
				width[i] = widthHidden[i];
				i++;
			});
			
			if(obj.find('table.fht_orig_table tbody tr td:first-child').hasClass('firstCell') == false) {
				obj.find('table.fht_orig_table tbody tr td:first-child').addClass('firstCell');
			}
			
			/*
			if (options.colBorder) {
				obj.find('.fht_parent table tr td:not(:last-child)').addClass('borderRight');
				obj.find('.fht_parent table tr th:not(:last-child)').addClass('borderRight');
			}*/
			
			var thCount = 0;
			var thWidth;
			var tdWidth;
			obj.find('table.fht_orig_table tbody tr:first td').each(function() {
				
				if ($(this).hasClass('firstCell')) {
					thCount = 0;
				}
				
				thWidth = width[thCount];
				tdWidth = $(this).width();
		
				$(this).children('div.tableData').css('width',thWidth+'px');
				obj.find('.fht_parent table.fht_fixed_header_table th.th'+thCount+' div.tableHeader').css('width', thWidth+'px');
				
				thCount++;
			});
			
			var footerHeight = 0;
			
			if (options.footer && !options.cloneHeaderToFooter) {
				if (!options.footerId) {
						// notify the developer they wanted a footer and didn't provide content
				}
				else {
					var footerId = options.footerId;
					if (obj.find('.fht_fixed_footer_border').size() == 1) {
						var footerContent = obj.find('.fht_fixed_footer_border').html();
					}
					else {
						$('#'+footerId).appendTo('.fht_parent');
						
						var footerContent = obj.find('#'+footerId).html();
					}
						obj.find('#'+footerId).empty();
						obj.find('#'+footerId).prepend('<div class="fht_cloned_footer"><!--[if IE 6]><div class="fht_bottom_left_header"></div><div class="fht_bottom_right_header"></div><![endif]--><div class="fht_fixed_footer_border"></div></div>');
						obj.find('.fht_fixed_footer_border').html(footerContent);
						obj.find('.fht_cloned_footer').css('width', obj.find('.fht_fixed_header_table_parent').width()+'px');
						obj.find('#'+footerId).css({'height': obj.find('#'+footerId).height() + 'px', 'width': obj.find('.fht_fixed_header_table_parent').width()+'px'});
						footerHeight = obj.find('#'+footerId).height();
				}
			}
			else if (options.footer && options.cloneHeaderToFooter) {
				obj.find('.fht_parent .fht_cloned_footer').remove()
				
				var html = "";
				html += "<div class='fht_cloned_footer'>"; // wraps around the entire fixed header
				html += "<!--[if IE]><div class='fht_bottom_right_header'></div><![endif]-->"; // adds a rounded corner to the top right of the header
				html += "<!--[if IE]><div class='fht_bottom_left_header'></div><![endif]-->"; // adds a rounded corner to the top left of the header
				html += "<div class='fht_fixed_footer_border'>"; // creates the border for the header
				html += "</div></div>"; // close all open div's and table tags
	
				obj.find('.fht_parent').append(html);

				obj.find('.fht_parent .fht_fixed_header_table_parent .fht_fixed_header_table_border table').clone().prependTo('.' + objClass + ' .fht_cloned_footer .fht_fixed_footer_border');
				obj.find('.fht_cloned_footer').css({'width': obj.find('.fht_parent .fht_fixed_header_table_parent').width()+'px', 'height': (obj.find('.fht_parent .fht_fixed_header_table_parent').height()-1)+'px'});
	
				footerHeight = obj.find('.fht_cloned_footer').height();
			}
			
			var headerHeight = obj.find('.fht_parent .fht_fixed_header_table_parent').height();
			var scrollDivHeight = parentDivHeight - footerHeight - headerHeight;
	
			obj.find('.fht_table_body').css({'width': tableBodyWidth+'px','height': scrollDivHeight+'px'}); // set the height of the main table body (where the data will be displayed) this also determines how much of the data is visible before a scroll bar is needed
			
			
			obj.find('table.fht_orig_table thead tr').css('display', 'none'); // hide the table body's header
	
			if(options.loader) {
				obj.find('.fht_loader').css('display', 'none');
			}
			
			obj.find('.fht_table_body').scroll(function() {
				obj.find('.fht_fixed_header_table_border').css('margin-left',(-this.scrollLeft)+'px');
				if (options.footer && options.cloneHeaderToFooter) {
					obj.find('.fht_fixed_footer_border').css('margin-left',(-this.scrollLeft)+'px');
				}
			});
		}
		else {
			$('body').css('background', '#f00');
			// build a dialog window that indicates an error in implementation
		}
	}	
})(jQuery);