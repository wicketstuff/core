/**
 * Ajax History Manager 
 * Based on: http://www.zachleat.com/Projects/history/history.zip
 * From blog: http://www.zachleat.com/web/2008/08/21/onhashchange-without-setinterval/
 */
var HistoryManager = {
	
	currentHistoryItem : '',

	getHistoryItem: function () {
		Wicket.Log.error('getHI: ' + this.currentHistoryItem);
		return this.currentHistoryItem;
	},

	setHistoryItem: function(item) {
		this.currentHistoryItem = item;
		Wicket.Log.error('setHI: ' + this.currentHistoryItem);
	},

	/**
	 * Appends new history entry into the hidden iframe if it is not already there
	 * 
	 * @param {Object} newHash
	 */
	addHistoryEntry: function (newHash)
	{
		var that = this;
		
		/*
		 *	In Safari, changing the src/hash inside of an iframe doesn't
		 * 	create a new history entry.  This will need to work to use
		 *	this approach.
		 *	
		 *	See: https://bugs.webkit.org/show_bug.cgi?id=9166  
		 */
		var iframe = jQuery('#historyIframe').contents();
		if(jQuery('a:contains('+newHash+')').length === 0) {
			jQuery('body', iframe).append('<div></div><a name="' + newHash + '"></a>');
		}
	
		function changeHash(newHash)
		{
			jQuery('#historyIframe').get(0).contentWindow.document.location.hash = '#' + newHash;
			that.setHistoryItem(newHash);
		}

		/*
		 *  The following code will trigger scrolling in the hidden iframe
		 *  With this flag we disable temporary the back button notification 
		 */
		jQuery('#historyIframe').get(0).contentWindow.window.isBackButton = false;
	
		if(jQuery.browser.opera) { // Opera wasn't recognizing new <div> and <a> without a timeout.
			window.setTimeout(function()
			{
				changeHash(newHash);
			}, 100);
		} else {
			changeHash(newHash);
		}
		
		/*
		 * Restore the hidden iframe's flag
		 */
		window.setTimeout(function()
			{
				jQuery('#historyIframe').get(0).contentWindow.window.isBackButton = true;
			}, 200);
	}
}