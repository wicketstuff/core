if (typeof(Wicket) == 'undefined') {
	Wicket = {
		'Form' : {}
	};
}

// this function intentionally ignores image and submit inputs
Wicket.Form.serializeInput = function(input) {

	var type = input.type.toLowerCase();

    if ((type == "checkbox" || type == "radio") && input.checked) {
    	return Wicket.Form.encode(input.name) + "=" + Wicket.Form.encode(input.value) + "&";
	} else if (type == "text" || type == "password" || type == "hidden" || type == "textarea" || 
			type == "search" || type == "range" ) {
		return Wicket.Form.encode(input.name) + "=" + Wicket.Form.encode(input.value) + "&";
	} else {
		return "";
	}
}