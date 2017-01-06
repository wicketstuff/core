function loadAjaxPropertyColumn(data) {

	var _id = data['${field}'];
	var imageUrl = '${imageUrl}';
	var callbackUrl = '${callbackUrl}';

	jQuery.ajax({
		url : callbackUrl,
		type : "GET",
		data: { id: _id },
		success : function(response) {
			jQuery("#lazy_" + _id).html(response);
		},
		error: function (xhr, error) {
			console.error(error);
		}
	});

	return "<div id='lazy_" + _id + "'><img alt='Loading...' src='" + imageUrl + "'/></div>"
}
