function changeStyleOnOnMouseOver(markupId, mouseOverClass) {
	var element = document.getElementById(markupId);
	element.setAttribute('originalClass', element.getAttribute('class'));
	element.onmouseover = function(e) {
		element.className = mouseOverClass;
	};
	element.onmouseout = function(e) {
		element.className = element.getAttribute('originalClass');
	};
}