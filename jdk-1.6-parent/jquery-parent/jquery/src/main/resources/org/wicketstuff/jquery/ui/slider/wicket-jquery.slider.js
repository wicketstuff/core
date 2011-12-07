/**
 * Returns the HTML id of the slider handle that triggered this event
 * @param {Event} e normalized by JQuery event object
 */
function getHandleId(e, ui){

    return jQuery(e.target).attr('id');
}

/**
 * Returns the new value of the slider handle which triggered this event
 *
 * @param {Event} e normalized by JQuery event object
 * @param {Object} ui an object with context information about the last change in the slider handle(s) position
 */
function getValue(e, ui){
    return ui.value;
}
