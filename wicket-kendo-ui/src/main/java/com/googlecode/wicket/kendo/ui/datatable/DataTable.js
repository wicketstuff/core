
/**
 * Disables .k-state-disabled links, this is needed for 'edit' and 'destroy' buttons.
 */
function datatable_dataBound(e) {
	var $grid = e.sender;

//	$grid.tbody.find("a.k-state-disabled").each(function () {
//		$(this).attr("href", "javascript:;");
//      $(this).off("click");
//    });

	// workaround until finding a way to disable links
	$grid.tbody.find("a.k-state-disabled.k-grid-edit").each(function () {
        $(this).remove();
    });

	$grid.tbody.find("a.k-state-disabled.k-grid-delete").each(function () {
        $(this).remove();
    });
}

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
