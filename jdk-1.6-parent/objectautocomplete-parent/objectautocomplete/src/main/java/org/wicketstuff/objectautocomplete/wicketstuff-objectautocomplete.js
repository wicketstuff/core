if (typeof(Wicketstuff) == "undefined") {
	var Wicketstuff = { };
}

Wicketstuff.ObjectAutoComplete=function(elementId, objectElementId, callbackUrl, cfg) {

  return new Wicketstuff.DropDownList(elementId,updateChoices,updateValue,cfg);

  // ===============================================================================

  function updateChoices(dropDown,elementId) {
    var value = Wicket.DOM.get(elementId).value;
//    var request = new Wicket.Ajax.get(
//            callbackUrl + "&q=" + (encodeURIComponent ? encodeURIComponent(value) : escape(value)),
//            doUpdateChoices, false, true, false, "wicket-autocomplete|d");
      var url = callbackUrl + "&q=" + (encodeURIComponent ? encodeURIComponent(value) : escape(value));
     Wicket.Ajax.get({ 'u': url, 'wr': false, 'fh' : false, 'ch' : "wicket-autocomplete|d", 'sh' : [doUpdateChoices] });

    // Callback method
    function doUpdateChoices(data, textStatus, jqXHR, attrs) {

        console.log("E: " + elementId + " - " + data);
        // check if the input hasn't been cleared in the meanwhile
      var input = Wicket.DOM.get(elementId);
      if (!cfg.showListOnEmptyInput && (input.value == null || input.value == "")) {
        dropDown.hideDropDown();
        return;
      }

      dropDown.setSelectablesFromHtml(data);

      window.setTimeout(function() {
        var input = Wicket.DOM.get(elementId);
        if (!cfg.showListOnEmptyInput && (input.value == null || input.value == "")) {
          dropDown.hideDropDown();
        }
      }, 100);

    }
  }

  function updateValue(dropDown,elementId,selectedElement) {
    var objElement = Wicket.DOM.get(objectElementId);
    var textElement = Wicket.DOM.get(elementId);
    var selected = dropDown.getSelectedElement();
    var attr = selected.attributes['textvalue'];
    var idAttr = selected.attributes['idvalue'];
    var value;
    if (attr == undefined) {
      value = selected.innerHTML;
    } else {
      value = attr.value;
    }
    objElement.value = idAttr.value;
    textElement.value = value.replace(/<[^>]+>/g,"");
  }
}