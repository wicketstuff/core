function newUrlUtil(options) {

	var defaults = {
			fragmentIdentifierSuffix: '!',
			keyValueDelimiter: '='
		},
		options = $.extend(defaults, options),
		hashIdentifier = '#' + options.fragmentIdentifierSuffix,
		queryPrefix = '?',
		parameterPattern = new RegExp('([^&' + options.keyValueDelimiter + ']+)' + options.keyValueDelimiter + '?([^&]*)(?:&+|$)', 'g'),
		wicketAjaxCall = undefined;

	function getQueryParameters() {
		return getParameters(window.location.search, queryPrefix);
	}

	function getFragmentParameters() {
		return getParameters(window.location.hash, hashIdentifier);
	}

	function getParameters(urlPart, prefix) {
		var urlPartWithoutPrefix = (urlPart || prefix).substr(prefix.length), parameterMap = {};
		urlPartWithoutPrefix.replace(parameterPattern, function(match, key, value) {
			parameterMap[key] = value;
		});
		return parameterMap;
	}

	return {
		setFragment : function(name, value) {
			this.editedFragment = true;
			if(value) {
				window.location.hash = hashIdentifier.concat(name).concat(options.keyValueDelimiter).concat(value);
			} else {
				window.location.hash = hashIdentifier.concat(name);
			}
		},

		putFragmentParameter : function(name, value, keyValuePairDelimiter) {
			var fragmentParameters = getFragmentParameters(),
			hash = hashIdentifier;
			
			if(keyValuePairDelimiter) {
			  if(fragmentParameters[name]) {
				fragmentParameters[name] = fragmentParameters[name].concat(keyValuePairDelimiter).concat(value);
			  } else {
				fragmentParameters[name] = value;
			  }
			} else {
			  fragmentParameters[name] = value;
			}
			
			for (parameter in fragmentParameters) {
				var hashBegin = hash === hashIdentifier ? '' : '&';
				hash = hash.concat(hashBegin).concat(parameter).concat(options.keyValueDelimiter)
						.concat(fragmentParameters[parameter]);
			}
			
			this.editedFragment = true;
			window.location.hash = hash;
		},

		removeFragmentParameter : function(name) {
			var fragmentParameters = getFragmentParameters(),
				hash = hashIdentifier;

			for (parameter in fragmentParameters) {
				var hashBegin = hash === hashIdentifier ? '' : '&';
				if (parameter != name) {
					hash = hash.concat(hashBegin).concat(parameter).concat(options.keyValueDelimiter)
							.concat(fragmentParameters[parameter]);
				}
			}

			this.editedFragment = true;
			window.location.hash = hash === hashIdentifier ? '' : hash;
		},

		joinQueryAndFragment : function() {
			var queryParameters = getFragmentParameters(),
				fragmentParameters = getQueryParameters();

			for (parameter in fragmentParameters) {
				queryParameters[parameter] = fragmentParameters[parameter];
			}
			return queryParameters;
		},

		sendUrlParameters : function() {
			if (!this.sentParametersOnInitialPageLoad) {
				wicketAjaxCall();
				this.sentParametersOnInitialPageLoad = true;
			}
		},

		back : function() {
			if (UrlUtil.editedFragment) { // hashchange through set/add/removeFragmentParameter
				UrlUtil.editedFragment = false;
			} else { // hashchange through back button
				wicketAjaxCall();
			}
		},

		setWicketAjaxCall : function(ajaxCallFunction) {
			wicketAjaxCall = ajaxCallFunction;
		},

		sentParametersOnInitialPageLoad : false, // used to avoid executing the Wicket AJAX call infinitely
		editedFragment : false
	};
};

if (typeof exports != 'undefined') {
	exports.UrlUtil = newUrlUtil;
}
