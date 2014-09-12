/**
 * Adds a value as get argument to the url
 * 
 * 
 * Example:
 * 
 * var url = '${wicket:ajaxCallbackUrl()}';
 * Wicket.Ajax.applyGetParameter(url,{param:value});
 */
Wicket.Ajax.applyGetParameters = function(url, options) {
	return url + "&" + $.param(options);
}

/**
 * Wraps the url into a Wicket.Ajax.get-Call
 * 
 * Example:
 * 
 * var url = '${wicket:ajaxCallbackUrl()}'; Wicket.Ajax.wrapget(url);
 * 
 */
Wicket.Ajax.wrapget = function(url) {
	Wicket.Ajax.get({
		'u' : url 
	});
}

/**
 * Wraps the url into a Wicket.Ajax.post-call
 * 
 * Example:
 * 
 * var url = '${wicket:ajaxCallbackUrl()}'; Wicket.Ajax.wrappost(url);
 * 
 */
Wicket.Ajax.wrappost = function(url, options) {
	Wicket.Ajax.post({
		'u' :  url ,
		ep : options
	});
}
