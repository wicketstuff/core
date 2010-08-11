if (typeof(Wicketstuff) == "undefined") {
	var Wicketstuff = { };
}

Wicketstuff.ObjectAutoComplete=function(elementId, objectElementId, callbackUrl, cfg) {

  return new Wicketstuff.DropDownList(elementId,updateChoices,updateValue,cfg);

  // ===============================================================================

  function updateChoices(dropDown,elementId) {
    var value = wicketGet(elementId).value;
    var request = new Wicket.Ajax.Request(
            callbackUrl + "&q=" + (encodeURIComponent ? encodeURIComponent(value) : escape(value)),
            doUpdateChoices, false, true, false, "wicket-autocomplete|d");
    request.get();

    // Callback method
    function doUpdateChoices(resp) {
      // check if the input hasn't been cleared in the meanwhile
      var input = wicketGet(elementId);
      if (!cfg.showListOnEmptyInput && (input.value == null || input.value == "")) {
        dropDown.hideDropDown();
        return;
      }

      dropDown.setSelectablesFromHtml(resp);

      window.setTimeout(function() {
        var input = wicketGet(elementId);
        if (!cfg.showListOnEmptyInput && (input.value == null || input.value == "")) {
          dropDown.hideDropDown();
        }
      }, 100);
      Wicket.Ajax.invokePostCallHandlers();
    }
  }

  function updateValue(dropDown,elementId,selectedElement) {
    var objElement = wicketGet(objectElementId);
    var textElement = wicketGet(elementId);
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