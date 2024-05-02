function loadAjaxPropertyColumn(data) {

	var field = '${field}';
	var key = data[field];
	var imageUrl = '${imageUrl}';
	var callbackUrl = '${callbackUrl}';

	jQuery.ajax({
		url : callbackUrl,
		type : "GET",
		data: { id: key },
		success : function(response) {
			jQuery("#lazy_" + key).html(response);
		},
		error: function (xhr, error) {
			console.error(error);
		}
	});

	return "<div id='lazy_" + key + "' data-container-for='" + field + "'><img alt='Loading...' src='" + imageUrl + "'/></div>"
}
