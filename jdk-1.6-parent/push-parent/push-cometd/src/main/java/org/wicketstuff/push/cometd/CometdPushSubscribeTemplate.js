history.navigationMode = 'compatible'; // Opera onUnload support

(function () {
  var subscribe = function() {
    try {
      if (!Wicket.Push.cometd.isDisconnected()) {
        subscription=Wicket.Push.cometd.subscribe('/${COMETD_CHANNEL_ID}', onEventFor${COMETD_CHANNEL_ID});
        Wicket.Event.add(window, "unload", function(event) { Wicket.Push.cometd.unsubscribe(subscription);});
        return;
      }
    } catch (x) { /* ignore */ }
   setTimeout(subscribe, 100);
  };
  subscribe();
})();