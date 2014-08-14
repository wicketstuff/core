dataLoadedHandler_${dataLoaderId} = function(source) {
    Wicket.Ajax.post(
        {'u': '${callbackUrl}',
         'dep': [function() {
             return {}
         }]});
};
