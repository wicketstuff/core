/**
 * Removes elements related to non 'popupVisible' columns
 */
function datatable_edit(e) {
	var columns = e.sender.columns;
	var $container = jQuery(e.container);

	jQuery(columns).each(function(index, column) { 
			if (column.hasOwnProperty('popupVisible') && !column.popupVisible) {
				$container.find('label[for=' + column.field + ']').parent().remove();
				$container.find('div[data-container-for=' + column.field + ']').remove();
			}
	});
}
