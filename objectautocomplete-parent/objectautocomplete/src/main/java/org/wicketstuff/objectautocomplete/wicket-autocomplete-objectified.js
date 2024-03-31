/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Wicket Ajax Autocomplete
 *
 * @author Janne Hietam&auml;ki
 */

if (typeof(Wicket) == "undefined")
  Wicket = { };

Wicket.AutoCompleteSettings = {
  enterHidesWithNoSelection : false
};

Wicket.AutoComplete = function(elementId, callbackUrl, cfg) {
  var KEY_TAB = 9;
  var KEY_ENTER = 13;
  var KEY_ESC = 27;
  var KEY_LEFT = 37;
  var KEY_UP = 38;
  var KEY_RIGHT = 39;
  var KEY_DOWN = 40;
  var KEY_SHIFT = 16;
  var KEY_CTRL = 17;
  var KEY_ALT = 18;

  var selected = -1; 	// index of the currently selected item
  var mouseactive = 0;
  var visible = 0;		// is the list visible
  var hidingAutocomplete = 0;		// are we hiding the autocomplete list
  var selectables = [];

  // pointers of the browser events
  var objonkeydown;
  var objonblur;
  var objonkeyup;
  var objonkeypress;

  // remember this object in order to reference to it even in
  // callback function (closure)
  var acObject = this;

  function initialize() {
    var obj = Wicket.DOM.get(elementId);

    objonkeydown = obj.onkeydown;
    objonblur = obj.onblur;
    objonkeyup = obj.onkeyup;
    objonkeypress = obj.onkeypress;

    obj.onblur = function(event) {

      if (mouseactive == 1) {
        Wicket.$(elementId).focus();
        return Wicket.Event.stop(event);
      }
      hideAutoComplete();
      if (typeof objonblur == "function")objonblur();
    }

    obj.onkeydown = function(event) {
      switch (Wicket.Event.fix(event)) {
        case KEY_UP:
          if (selected > -1)selected--;
          if (selected == -1) {
            hideAutoComplete();
          } else {
            render();
          }
          if (Wicket.Browser.isSafari())return Wicket.Event.stop(event);
          break;
        case KEY_DOWN:
          if (selected < selectables.length - 1) {
            selected++;
          }
          if (visible == 0) {
            updateChoices();
          } else {
            render();
            showAutoComplete();
          }
          if (Wicket.Browser.isSafari())return Wicket.Event.stop(event);
          break;
        case KEY_ESC:
          hideAutoComplete();
          return Wicket.Event.stop(event);
          break;
        case KEY_ENTER:
          if (selected > -1) {
            acObject.updateValue();
            hideAutoComplete();
            hidingAutocomplete = 1;
          } else if (Wicket.AutoCompleteSettings.enterHidesWithNoSelection == true) {
            hideAutoComplete();
            hidingAutocomplete = 1;
          }
          mouseactive = 0;
          if (typeof objonkeydown == "function")objonkeydown();

          if (selected > -1) {
            //return Wicket.Event.stop(event);
          }
          return true;
          break;
        default:
      }
    }

    obj.onkeyup = function(event) {
      switch (Wicket.Event.fix(event)) {
        case KEY_ENTER:
          return Wicket.Event.stop(event);
        case KEY_UP:
        case KEY_DOWN:
        case KEY_ESC:
        case KEY_TAB:
        case KEY_RIGHT:
        case KEY_LEFT:
        case KEY_SHIFT:
        case KEY_ALT:
        case KEY_CTRL:
          break;
        default:
          updateChoices();
      }
      if (typeof objonkeyup == "function")objonkeyup();
      return null;
    }

    obj.onkeypress = function(event) {
      if (Wicket.Event.fix(event) == KEY_ENTER) {
        if (selected > -1 || hidingAutocomplete == 1) {
          hidingAutocomplete = 0;
          return Wicket.Event.stop(event);
        }
      }
      if (typeof objonkeypress == "function") {
        return (objonkeypress.bind(obj))(event);
      }
    }
  }

  this.getMenuId = function() {
    return elementId + "-autocomplete";
  }

  function getAutocompleteMenu() {
    var choiceDiv = document.getElementById(acObject.getMenuId());
    if (choiceDiv == null) {
      var container = document.createElement("div");
      container.className = "wicket-aa-container";
      document.body.appendChild(container);
      container.style.display = "none";
      container.style.overflow = "auto";
      container.style.position = "absolute";
      container.id = acObject.getMenuId() + "-container";

      container.show = function() {
        Wicket.DOM.show(this.id)
      };
      container.hide = function() {
         Wicket.DOM.hide(this.id)
      };

      choiceDiv = document.createElement("div");
      container.appendChild(choiceDiv);
      choiceDiv.id = acObject.getMenuId();
      choiceDiv.className = "wicket-aa";


      // WICKET-1350/WICKET-1351
      container.onmouseout = function() {
        mouseactive = 0;
      };
      container.onmousemove = function() {
        mouseactive = 1;
      };
    }


    return choiceDiv;
  }

  function getAutocompleteContainer() {
    var node = getAutocompleteMenu().parentNode;

    return node;
  }

  function updateChoices() {
    if (cfg.preselect == true) {
      selected = 0;
    }
    else {
      selected = -1;
    }
    var value = Wicket.DOM.get(elementId).value;
    Wicket.Ajax.get({
        u: callbackUrl + "&q=" + processValue(value),
        sh: [doUpdateChoices],
        ch: "wicket-autocomplete|d"
    });
  }

  function processValue(param) {
    return (encodeURIComponent) ? encodeURIComponent(param) : escape(param);
  }

  function showAutoComplete() {
    var position = getPosition(Wicket.DOM.get(elementId));
    var container = getAutocompleteContainer();
    var input = Wicket.DOM.get(elementId);
    var index = getOffsetParentZIndex(elementId);
    container.show();
    container.style.zIndex = (!isNaN(Number(index)) ? Number(index) + 1 : index); 
    container.style.left = position[0] + 'px'
    container.style.top = (input.offsetHeight + position[1]) + 'px';
    container.style.width = input.offsetWidth + 'px';
    visible = 1;
    hideShowCovered();
  }

  function hideAutoComplete() {
    visible = 0;
    selected = -1;
    if (getAutocompleteContainer())
    {
      getAutocompleteContainer().hide();
      hideShowCovered();
    }
  }

  function getPosition(obj) {
    var leftPosition = 0;
    var topPosition = 0;
    do {
      topPosition += obj.offsetTop || 0;
      leftPosition += obj.offsetLeft || 0;
      obj = obj.offsetParent;
    } while (obj);
    return [leftPosition,topPosition];
  }

  function doUpdateChoices(attrs, jqXHR, data, textStatus) {

    // check if the input hasn't been cleared in the meanwhile
    var input = Wicket.DOM.get(elementId);
    if (!cfg.showListOnEmptyInput && (input.value == null || input.value == "")) {
      hideAutoComplete();
      return;
    }

    var element = getAutocompleteMenu();
    element.innerHTML = data;

    selectables = extractSelectables(element.firstChild);
    if (selectables.length > 0) {
      for (var i = 0; i < selectables.length; i++) {
        var node = selectables[i];

        node.onclick = function(event) {
          mouseactive = 0;
          acObject.updateValue();
          hideAutoComplete();
        }

        node.onmouseover = function(event) {
          selected = getElementIndex(this);
          render();
          showAutoComplete();
        }
      }
      showAutoComplete();
    } else {
      hideAutoComplete();
    }

    render();

    scheduleEmptyCheck();

    Wicket.Log.info("Response processed successfully.");
  }

  function extractSelectables(element) {
    // All children which are <li> elements are considered
    // as selectables
    var listElements = [];
    var nodeName = (cfg.choiceTagName || 'LI').toUpperCase();
    if (element) {
      (function(el) {
        if (el.nodeType == 1 && el.nodeName.toUpperCase() == nodeName) {
          // Add only list items
          listElements.push(el);
        }
        if (el.hasChildNodes()) {
          for (var i = 0; i < el.childNodes.length; i++) {
            arguments.callee(el.childNodes[i]);
          }
        }
      })(element);
    }
    return listElements;
  }


  function scheduleEmptyCheck() {
    window.setTimeout(function() {
      var input = Wicket.DOM.get(elementId);
      if (!cfg.showListOnEmptyInput && (input.value == null || input.value == "")) {
        hideAutoComplete();
      }
    }, 100);
  }

  this.updateValue = function() {
    var obj = Wicket.DOM.get(elementId);
    obj.value = this.getSelectedValue();
  }

  this.getSelectedValue = function() {
    var element = this.getSelectedElement();
    var attr = element.attributes['textvalue'];
    var value;
    if (attr == undefined) {
      value = element.innerHTML;
    } else {
      value = attr.value;
    }
    return stripHTML(value);
  }

  this.getSelectedElement = function() {
    return selectables[selected];
  }

  function getElementIndex(element) {
    for (var i = 0; i < selectables.length; i++) {
      if (selectables[i] == element)return i;
    }
    return -1;
  }

  function stripHTML(str) {
    return str.replace(/<[^>]+>/g, "");
  }

  function adjustScrollOffset(menu, item) {
    if (item.offsetTop + item.offsetHeight > menu.scrollTop + menu.offsetHeight) {
      menu.scrollTop = item.offsetTop + item.offsetHeight - menu.offsetHeight;
    } else
    // adjust to the top
      if (item.offsetTop < menu.scrollTop) {
        menu.scrollTop = item.offsetTop;
      }
  }

  function render() {
    var menu = getAutocompleteMenu();
    var height = 0;
    for (var i = 0; i < selectables.length; i++) {
      var node = selectables[i];

      var classNames = node.className.split(" ");
      for (var j = 0; j < classNames.length; j++) {
        if (classNames[j] == 'selected') {
          classNames[j] = '';
        }
      }

      if (selected == i) {
        classNames.push('selected');
        adjustScrollOffset(menu.parentNode, node);
      }

      node.className = classNames.join(" ");
      height += node.offsetHeight;
    }
    if (cfg.maxHeight > -1) {
      height = height < cfg.maxHeight ? height : cfg.maxHeight;
      menu.parentNode.style.height = height + "px";
    }
  }

  // From http://www.robertnyman.com/2006/04/24/get-the-rendered-style-of-an-element/
  function getStyle(obj, cssRule) {
    var cssRuleAlt = cssRule.replace(/\-(\w)/g, function(strMatch, p1) {
      return p1.toUpperCase();
    });
    var value = obj.style[cssRuleAlt];
    if (!value) {
      if (document.defaultView && document.defaultView.getComputedStyle) {
        value = document.defaultView.getComputedStyle(obj, "").getPropertyValue(cssRule);
      }
      else if (obj.currentStyle)
      {
        value = obj.currentStyle[cssRuleAlt];
      }
    }
    return value;
  }

  function isVisible(obj) {
    return getStyle(obj, "visibility");
  }

  function getOffsetParentZIndex(obj) {
    obj = typeof obj == "string" ? Wicket.$(obj) : obj;
    obj = obj.offsetParent;
    var index = "auto";
    do {
      var pos = getStyle(obj, "position");
      if (pos == "relative" || pos == "absolute" || pos == "fixed") {
        index = getStyle(obj, "z-index");
      }
      obj = obj.offsetParent;
    } while (obj && index == "auto");
    return index;
  }

  function hideShowCovered() {
    if (!/msie/i.test(navigator.userAgent) && !/opera/i.test(navigator.userAgent)) {
      return;
    }
    // IE7 fix, if this doesn't go in a timeout then the complete page could become invisible.
    // when closing the popup.
    setTimeout(hideShowCoveredTimeout, 1);
  }

  function hideShowCoveredTimeout() {
    var el = getAutocompleteContainer();
    var p = getPosition(el);

    var acLeftX = p[0];
    var acRightX = el.offsetWidth + acLeftX;
    var acTopY = p[1];
    var acBottomY = el.offsetHeight + acTopY;

    var hideTags = new Array("select", "iframe", "applet");

    for (var j = 0; j < hideTags.length; j++) {
      var tagsFound = document.getElementsByTagName(hideTags[j]);
      for (var i = 0; i < tagsFound.length; i++) {
        var tag = tagsFound[i];
        p = getPosition(tag);
        var leftX = p[0];
        var rightX = leftX + tag.offsetWidth;
        var topY = p[1];
        var bottomY = topY + tag.offsetHeight;

        if (this.hidden || (leftX > acRightX) || (rightX < acLeftX) || (topY > acBottomY) || (bottomY < acTopY)) {
          if (!tag.wicket_element_visibility) {
            tag.wicket_element_visibility = isVisible(tag);
          }
          tag.style.visibility = tag.wicket_element_visibility;
        } else {
          if (!tag.wicket_element_visibility) {
            tag.wicket_element_visibility = isVisible(tag);
          }
          tag.style.visibility = "hidden";
        }
      }
    }
  }

  initialize();
}