$('#${component}').sortable({
    connectWith: '.column',
    handle: '.dragbox-header',
    forcePlaceholderSize: true,
    placeholder: 'placeholder',
    stop: function(event, ui) {
        ${stopBehavior}
    }
});