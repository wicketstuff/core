(function($) {
    $('#${componentId}').tagit({
        removeConfirmation: true,
        tagSource: function(search, showChoices) { 
            var that = this; 
            $.ajax({
                url: '${callbackUrl}', 
                data: search, 
                error: function() {
                    // console.log('err', arguments);
                }, 
                success: function(choices) {
                    showChoices(that._subtractArray(choices, that.assignedTags()));
                } 
             });
        }
   });
})(jQuery);