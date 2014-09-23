if (typeof(Wicketstuff) == "undefined") {
	var Wicketstuff = { };
}

Wicketstuff.ObjectAutoComplete=function(elementId, objectElementId, callbackUrl, cfg) {

  return new Wicketstuff.DropDownList(elementId, updateChoices, updateValue, cfg);

  // ===============================================================================

  function updateChoices(dropDown,elementId) {
    var value = Wicket.DOM.get(elementId).value;

    var url = callbackUrl + "&q=" + (encodeURIComponent ? encodeURIComponent(value) : escape(value));
    Wicket.Ajax.get({
        u: url,
        wr: false,
        dt: 'text',
        ch : "wicket-autocomplete|d",
        sh : [doUpdateChoices]
    });

    function doUpdateChoices(attrs, jqXHR, data, textStatus) {

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

  function updateValue(dropDown, elementId, selected) {
    var objElement = Wicket.DOM.get(objectElementId);
    var textElement = Wicket.DOM.get(elementId);
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
    jQuery(textElement).trigger('objectchange');
  }
}