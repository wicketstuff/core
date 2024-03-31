function serializeWidgetLocations() {
	var items = [];
	$("ul.column").each(function() {
		var columnId = $(this).attr("id");
		var column = columnId.substring(7); // "column-".length
		$(this).children().each(function(i) {
			var widgetId = $(this).attr("id");
			var widget = widgetId.substring(7); // "widget-".length
			// create item object for current panel
			var item = {
				column : column,
				widget : widget,
				sortIndex : i
			};

			// push item object into items array
			items.push(item);
		});
	});
	// pass items variable to server to save state
	return JSON.stringify(items);
}
