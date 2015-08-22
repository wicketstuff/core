var onEventFor${COMETD_CHANNEL_ID}_isRunning = false;
var onEventFor${COMETD_CHANNEL_ID}_isScheduled = false;
function onEventFor${COMETD_CHANNEL_ID}(message) {
	if (message.data == "pollEvents") {
	    if (onEventFor${COMETD_CHANNEL_ID}_isRunning)
	    {
	    	onEventFor${COMETD_CHANNEL_ID}_isScheduled = true
	    }
	    else
		{
			onEventFor${COMETD_CHANNEL_ID}_isScheduled = false
			onEventFor${COMETD_CHANNEL_ID}_isRunning = true
		    onFinished = function() {
		    	onEventFor${COMETD_CHANNEL_ID}_isRunning = false
		    	if (onEventFor${COMETD_CHANNEL_ID}_isScheduled)
					onEventFor${COMETD_CHANNEL_ID}(message);
		    }
			wicketAjaxGet('${CALLBACK_URL}', onFinished, onFinished);
		}
	}
	else if (message.data.match("^javascript:")) {
		eval(message.data.substring(12));
	}
}
