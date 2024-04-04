if (window.addEventListener) {
	window.addEventListener('pageshow', function(event) {
	    if (event.persisted) {
	        window.location.reload(); 
	    }
	});
}