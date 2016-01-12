if (typeof(Wicketstuff) == "undefined") {
	var Wicketstuff = { };
}

Wicketstuff.DropDownList = function(elementId, updateChoicesFunc, updateValueFunc, config) {

  // member variables, must be set from within the updateChoicesFunc
  this.selectables = [];

  // ========================================================================
  // Private variables
  var selected = null;
  var mouseactive = 0;
  var visible = 0;
  var delayTimeOutId = null;

  // DOM-Id used for the aac menu
  var menuId = elementId + "-autocomplete";

  // Keys and Backup handlers
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

  var hidingAutocomplete = 0;		// are we hiding the autocomplete list

  // pointers of the browser events
  var objonkeydown;
  var objonblur;
  var objonkeyup;
  var objonkeypress;

  // ========================================================================

  // Reregister mouseclick handler ont the autocompletion menu
  // if still present from
  // a previous call. This is required to properly register
  // the mouse event handler again (using the new 'mouseactive'
  // variable which just has been created again)
  var choiceDiv=document.getElementById(menuId);
  if (choiceDiv != null) {
    addMouseActivityStateHandler(choiceDiv.parentNode);
  }

  // ========================================================================

  // Make this object available in key handlers
  var ddObject = this;
  var obj = Wicket.DOM.get(elementId);

  objonkeydown = obj.onkeydown;
  objonblur = obj.onblur;
  objonkeyup = obj.onkeyup;
  objonkeypress = obj.onkeypress;

  if(config.searchOnPaste) {
     obj.onpaste = function(event) {
         setTimeout(function() {
            ddObject.updateChoices();
         }, 50);
     };
  }

  obj.onblur = function(event) {
    if (mouseactive == 1) {
      Wicket.$(elementId).focus();
      return Wicket.Event.stop(event);
    }
    ddObject.hideDropDown();
    if (typeof objonblur == "function")objonblur();
  }

  obj.onkeydown = function(event) {
    switch (Wicket.Event.keyCode(event)) {
      case KEY_UP:
        if (selected > -1)selected--;
        if (selected == -1) {
          ddObject.hideDropDown();
          } else {
          ddObject.render();
        }
        if (Wicket.Browser.isSafari())return Wicket.Event.stop(event);
        break;
      case KEY_DOWN:
        if (selected < ddObject.selectables.length - 1) {
          selected++;
        }
        if (visible == 0) {
          ddObject.updateChoices();
        } else {
          ddObject.render();
          ddObject.showDropDown();
        }
        if (Wicket.Browser.isSafari())return Wicket.Event.stop(event);
        break;
      case KEY_ESC:
        ddObject.hideDropDown();
        return Wicket.Event.stop(event);
        break;
      case KEY_ENTER:
        if (selected > -1) {
          ddObject.updateValue();
          ddObject.hideDropDown();
          hidingAutocomplete = 1;
        } else if (config.enterHidesWithNoSelection) {
          ddObject.hideDropDown();
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
    switch (Wicket.Event.keyCode(event)) {
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
        ddObject.updateChoices();
    }
    if (typeof objonkeyup == "function") objonkeyup();
    return null;
  }

  obj.onkeypress = function(event) {
    if (Wicket.Event.keyCode(event) == KEY_ENTER) {
      if (selected > -1 || hidingAutocomplete == 1) {
        hidingAutocomplete = 0;
        return Wicket.Event.stop(event);
      }
    }
    if (typeof objonkeypress == "function") {
      return (objonkeypress.bind(obj))(event);
    }
  }

  // =================================================================================================

  this.updateChoices = function() {
      selected = config.preselect ? 0 : -1;
      if (!config.delay) {
          updateChoicesFunc(this, elementId);
      } else {
          // remove pending requests
          if(delayTimeOutId != null) {
              clearTimeout(delayTimeOutId);
              delayTimeOutId = null;
          };
          // Bind 'this'
          delayTimeOutId = setTimeout(function() {
              updateChoicesFunc(this, elementId);
          }.bind(this), config.delay);
      }
  };

  this.updateValue = function() {
    updateValueFunc(this,elementId,this.getSelectedElement());
  };

  this.render = function() {
    var menu = getMenu();
    var height = 0;
    for (var i = 0; i < this.selectables.length; i++) {
      var node = this.selectables[i];

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
    if (config.maxHeight > -1) {
      height = height < config.maxHeight ? height : config.maxHeight;
      menu.parentNode.style.height = height + "px";
    }
  }

  this.showDropDown = function() {
    var position = getPosition(Wicket.DOM.get(elementId));
    var container = getAutocompleteContainer();
    var input = Wicket.DOM.get(elementId);
    var index = getOffsetParentZIndex(elementId);
    var width = config.width ? config.width : input.offsetWidth;
    container.show();
    container.style.zIndex = (!isNaN(Number(index)) ? Number(index) + 1 : index);
    container.style.top = (input.offsetHeight + position[1]) + 'px';
    container.style.width = width + 'px';

    if (config.align && config.align == 'right') {
      // right align
      container.style.left = (position[0] + input.offsetWidth - width) + 'px';
    } else {
      // left align
      container.style.left = position[0] + 'px';
    }
    visible = 1;
    hideShowCovered();
  }

  this.hideDropDown = function() {
    visible = 0;
    selected = -1;
    if (getAutocompleteContainer()) {
      getAutocompleteContainer().hide();
      hideShowCovered();
    }
  }

  this.getSelectedElement = function() {
    return this.selectables[selected];
  }

  // callback for adding appropriate handlers
  this.addOnClickHandler = function(node) {
    node.onclick = function() {
      mouseactive = 0;
      ddObject.updateValue();
      ddObject.hideDropDown();
    }
  }

  this.addOnMouseOverHandler = function(node) {
    node.onmouseover = function() {
      selected = getElementIndex(ddObject.selectables,this);
      ddObject.render();
      ddObject.showDropDown();
    }
  }

  this.setSelectablesFromHtml = function(html) {
    var element = getMenu();
    element.innerHTML = html;

    var selectables = extractSelectables(element.firstChild);
    if (selectables.length > 0) {
      this.selectables = selectables;
      for (var i = 0; i < selectables.length; i++) {
        var node = selectables[i];

        this.addOnClickHandler(node);
        this.addOnMouseOverHandler(node);
      }
      this.showDropDown();
    } else {
      this.hideDropDown();
    }

    this.render();
  }

  // =============================================================================================================
  // Private Helper

  function getMenu () {
    var choiceDiv = document.getElementById(menuId);
    if (choiceDiv == null) {
      var container = document.createElement("div");
      container.className = config.menuClass ? config.menuClass + "-container" : "wicket-aa-container";
      document.body.appendChild(container);
      container.style.display = "none";
      container.style.overflow = "auto";
      container.style.position = "absolute";
      container.id = menuId + "-container";

      container.show = function() {
         Wicket.DOM.show(this.id)
      };
      container.hide = function() {
         Wicket.DOM.hide(this.id)
      };

      choiceDiv = document.createElement("div");
      container.appendChild(choiceDiv);
      choiceDiv.id = menuId;
      choiceDiv.className = config.menuClass || "wicket-aa";

      addMouseActivityStateHandler(container);
    }

    return choiceDiv;
  }


  function addMouseActivityStateHandler(container) {
    // WICKET-1350/WICKET-1351
    container.onmouseout = function() {
      mouseactive = 0;
    };
    container.onmousemove = function() {
      mouseactive = 1;
    };
  }

  function getAutocompleteContainer() {
    return getMenu().parentNode;
  }


  function getElementIndex(selectables,element) {
    for (var i = 0; i < selectables.length; i++) {
      if (selectables[i] == element)return i;
    }
    return -1;
  }

  function adjustScrollOffset(menu, item) {
    if (item.offsetTop + item.offsetHeight > menu.scrollTop + menu.offsetHeight) {
      menu.scrollTop = item.offsetTop + item.offsetHeight - menu.offsetHeight;
    } else {
      // adjust to the top
      if (item.offsetTop < menu.scrollTop) {
        menu.scrollTop = item.offsetTop;
      }
    }
  }

  function getPosition(obj) {
    var leftPosition = obj.offsetLeft || 0;
    var topPosition = obj.offsetTop || 0;
    obj = obj.offsetParent;
    while (obj && obj != document.documentElement && obj != document.body) {
      topPosition += obj.offsetTop || 0;
      topPosition -= obj.scrollTop || 0;
      leftPosition += obj.offsetLeft || 0;
      leftPosition -= obj.scrollLeft || 0;
      obj = obj.offsetParent;
    }
    return [leftPosition,topPosition];
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

  function isVisible(obj) {
    return getStyle(obj, "visibility");
  }

  function extractSelectables(element) {
    // All children which are <li> elements are considered
    // as selectables
    var listElements = [];
    var nodeName = (config.choiceTagName || 'LI').toUpperCase();
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
}
