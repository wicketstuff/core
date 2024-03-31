/*
 *	Wicketeer StyleSwitcher, version 1.0
 *  (c) 2007 Tauren Mills
 *	
 *	Inspired by Paul Sowden's A List Apart article "Altenative Style"
 *	http://alistapart.com/stories/alternate/
 *
 */

// Create the package namespace com.wicketeer.components
if (!com) {
	var com = {};
}
if (!com.wicketeer) {	
	com.wicketeer = {};
}
if (!com.wicketeer.components) {
	com.wicketeer.components = {};
}

// Create StyleSwitcher 
com.wicketeer.components.StyleSwitcher = function() {

	// private properties and functions
	var COOKIE_KEY = 'styleswitcher_style';
	var cookie = '';
	var DEFAULT_STYLESHEET = 'small';

	// public properties and functions
	var _public = {

		// sets the active stylesheet to the stylesheet with the title
		setActiveStyleSheet: function(title) {
			var i, a, main;
			for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
				if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
					a.disabled = true;
					if(a.getAttribute("title") == title) a.disabled = false;
				}
			}
		},
		
		// gets the title of the active stylesheet
		getActiveStyleSheet: function() {
			var i, a;
			for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
				if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title") && !a.disabled) return a.getAttribute("title");
			}
			return null;
		},

		// gets the default stylesheet preference		
		getPreferredStyleSheet: function() {
			return (DEFAULT_STYLESHEET);
		},

		// creates a cookie to remember the specified stylesheet
		createCookie: function(value,days) {
			if (days) {
				var date = new Date();
				date.setTime(date.getTime()+(days*24*60*60*1000));
				var expires = "; expires="+date.toGMTString();
			}
			else expires = "";
			document.cookie = COOKIE_KEY+"="+value+expires+"; path=/";
		},
		
		// reads the title of the stylesheet previously stored in a cookie
		readCookie: function() {
			var nameEQ = COOKIE_KEY + "=";
			var ca = document.cookie.split(';');
			for(var i=0;i < ca.length;i++) {
				var c = ca[i];
				while (c.charAt(0)==' ') c = c.substring(1,c.length);
				if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
			}
			return null;
		},
		
		// initialize StyleSwitcher - set active stylesheet
		initialize: function() {
			cookie = this.readCookie();
			var title = cookie ? cookie : this.getPreferredStyleSheet();
			if (title == 'null') {
				title = this.getPreferredStyleSheet();
			}
			this.setActiveStyleSheet(title);
		},

		// add an event to window.onload without overriding other events already set
		addLoadEvent: function(func) {
			var oldonload = window.onload;
			if (typeof window.onload != 'function') {
				window.onload = func;
			} else {
				window.onload = function() {
					if (oldonload) {
						oldonload();
					}
					func();
				};
			}
		},
		
		// add an event to window.onunload without overriding other events already set
		addUnloadEvent: function(func) {
			var oldonunload = window.onunload;
			if (typeof window.onunload != 'function') {
				window.onunload = func;
			} else {
				window.onunload = function() {
					if (oldonunload) {
						oldonunload();
					}
					func();
				};
			}
		}

	};

	return _public;

}();

// Create simpler name to access object instead of using full package path
var StyleSwitcher = com.wicketeer.components.StyleSwitcher;

// Add events to window.load and window.unload
StyleSwitcher.addLoadEvent(function() {
			var cookie = StyleSwitcher.readCookie();
			var title = cookie ? cookie : StyleSwitcher.getPreferredStyleSheet();
			StyleSwitcher.setActiveStyleSheet(title);
});		
StyleSwitcher.addUnloadEvent(function () {
			var title = StyleSwitcher.getActiveStyleSheet();
			StyleSwitcher.createCookie(title, 365);
});

// Initialize 
StyleSwitcher.initialize();

