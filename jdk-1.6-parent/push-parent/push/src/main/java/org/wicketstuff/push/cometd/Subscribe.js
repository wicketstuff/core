(function () {
  var subscribe = function () {
    try {
      if (!Wicket.Push.cometd.isDisconnected()) {
        Wicket.Push.cometd.subscribe('/${channelId}', ${partialSubscriber});
        return;
      }
    } catch (x) { }
    setTimeout(subscribe, 100);
  };
  subscribe();
})();