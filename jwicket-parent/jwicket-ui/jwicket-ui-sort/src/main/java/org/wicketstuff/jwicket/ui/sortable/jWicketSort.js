/**
 * jwicket functions for ui-sort
 *
 * @version 1.2
 */

(function($) {

	jQuery.jWicketDetectPositionAfterSort = function(ui, sortableId) {
		var newPosition = 0;
		var movedItemId = ui.item.attr('id');
		var items = $('#' + sortableId).sortable('toArray');

		for (var i=0; i<items.length; i++) {
		   if (movedItemId == items[i]) {
		      newPosition = i + 1;
		      break;
		   }
		}

		return newPosition;
	};


	jQuery.handleSortStop = function(wicketCallbackUrl, ui, sortableId) {
		var url = wicketCallbackUrl +
			'&newPosition='+jQuery.jWicketDetectPositionAfterSort(ui,sortableId)+
			'&draggedItemId='+ui.item.attr('id') +
			'&jWicketEvent=stop';
		wicketAjaxGet(url);
	}


	jQuery.handleReceive = function(wicketCallbackUrl, ui, sortableId) {
		var url = wicketCallbackUrl +
			'&newPosition='+jQuery.jWicketDetectPositionAfterSort(ui,sortableId)+
			'&draggedItemId='+ui.item.attr('id') +
			'&otherSortableId='+ui.sender.attr('id') +
			'&jWicketEvent=receive';
		wicketAjaxGet(url);
	}


	jQuery.handleRemove = function(wicketCallbackUrl, ui, sortableId) {
		var url = wicketCallbackUrl +
			'&draggedItemId='+ui.item.attr('id') +
			'&jWicketEvent=remove';
		wicketAjaxGet(url);
	}
	
	
})(jQuery);
