/*
 * Wicket Portlet Ajax support
 * 
 * @author Konstantinos Karavitis
 *
 * fix for https://github.com/wicketstuff/core/issues/588 issue
 */
;(function(Wicket) {
	Wicket.Ajax.Call.prototype.processAjaxResponse = function(data, textStatus, jqXHR, context) {
		if (jqXHR.readyState === 4) {
			// first try to get the redirect header
			var redirectUrl;
			try {
				redirectUrl = jqXHR.getResponseHeader('Ajax-Location');
			} catch (ignore) { // might happen in older mozilla
			}

			// the redirect header was set, go to new url
			if (typeof(redirectUrl) !== "undefined" && redirectUrl !== null && redirectUrl !== "") {
				this.success(context);
				context.isRedirecting = true;
				Wicket.Ajax.redirect(redirectUrl);
			}
			else {
				// no redirect, just regular response
				if (Wicket.Log.enabled()) {
					var responseAsText = jqXHR.responseText;
					Wicket.Log.info("Received ajax response (" + responseAsText.length + " characters)");
					Wicket.Log.info("\n" + responseAsText);
				}

				// invoke the loaded callback with an xml document
				return this.loadedCallback(data, context);
			}
		}
	};
})(Wicket);