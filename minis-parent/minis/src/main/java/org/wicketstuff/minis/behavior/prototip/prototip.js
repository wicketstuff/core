//  Prototip 1.2.1 - 08-03-2008

//  Copyright (c) 2008 Nick Stakenburg (http://www.nickstakenburg.com)
//
//  Permission is hereby granted, free of charge, to any person obtaining
//  a copy of this software and associated documentation files (the
//  "Software"), to deal in the Software without restriction, including
//  without limitation the rights to use, copy, modify, merge, publish,
//  distribute, sublicense, and/or sell copies of the Software, and to
//  permit persons to whom the Software is furnished to do so, subject to
//  the following conditions:
//
//  The above copyright notice and this permission notice shall be
//  included in all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
//  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
//  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
//  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
//  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
//  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
//  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

//  More information on this project:
//  http://www.nickstakenburg.com/projects/prototip/

var Prototip = {
  Version: '1.2.1',

  REQUIRED_Prototype: '1.6.0.2',
  REQUIRED_Scriptaculous: '1.8.1',

  start: function() {
    this.require('Prototype');
    Tips.initialize();
    Element.observe(window, 'unload', this.unload);
  },

  // Version check
  require: function(library) {
    if ((typeof window[library] == 'undefined') ||
      (this.convertVersionString(window[library].Version) <
       this.convertVersionString(this['REQUIRED_' + library])))
      throw('Lightview requires ' + library + ' >= ' + this['REQUIRED_' + library]);
  },

  convertVersionString: function(versionString) {
    var v = versionString.replace(/_.*|\./g, '');
    v = parseInt(v + '0'.times(4-v.length));
    return versionString.indexOf('_') > -1 ? v-1 : v;
  },

  capture: function(func) {
    if (!Prototype.Browser.IE) {
      func = func.wrap(function(proceed, event) {
      var rel = event.relatedTarget, cur = event.currentTarget;
      if (rel && rel.nodeType == Node.TEXT_NODE) rel = rel.parentNode;
      if (rel && rel != cur && rel.descendantOf && !(rel.descendantOf(cur)))
        proceed(event);
      });
    }
    return func;
  },

  unload: function() { Tips.removeAll(); }
};

var Tips = {
  // Configuration
  closeButtons: false,
  zIndex: 1200,

  tips : [],
  visible : [],

  initialize: function() {
    this.zIndexTop = this.zIndex;
  },

  useEvent : (function(IE) { return {
    'mouseover': (IE ? 'mouseenter' : 'mouseover'),
    'mouseout': (IE ? 'mouseleave' : 'mouseout'),
    'mouseenter': (IE ? 'mouseenter' : 'mouseover'),
    'mouseleave': (IE ? 'mouseleave' : 'mouseout')
  };})(Prototype.Browser.IE),

  fixIE: (function(agent) {
    var version = new RegExp('MSIE ([\\d.]+)').exec(agent);
    return version ? (parseFloat(version[1]) < 7) : false;
  })(navigator.userAgent),

  add: function(tip) {
    this.tips.push(tip);
  },

  remove: function(element) {
    var tip = this.tips.find(function(t){ return t.element == $(element); });
    if (tip) {
      tip.deactivate();
      if (tip.tooltip) {
        tip.wrapper.remove();
        if (Tips.fixIE) tip.iframeShim.remove();
      }
      this.tips = this.tips.without(tip);
    }
  },

  removeAll: function() {
    this.tips.each(function(tip) { this.remove(tip.element); }.bind(this));
  },

  raise: function(tip) {
    if (tip.highest) return;
    if (this.visible.length == 0) {
      this.zIndexTop = this.zIndex;
      for (var i=0;i<this.tips.length;i++) {
        this.tips[i].wrapper.style.zIndex = this.zIndex;
      }
    }
    tip.style.zIndex = this.zIndexTop++;
    for (var i=0;i<this.tips.length;i++) { this.tips[i].wrapper.highest = false; };
    tip.highest = true;
  },

  addVisibile: function(tip) {
    this.removeVisible(tip);
    this.visible.push(tip);
  },

  removeVisible: function(tip) {
    this.visible = this.visible.without(tip);
  }
};
Tips.initialize();

var Tip = Class.create({
  initialize: function(element, content) {
    this.element = $(element);
    Tips.remove(this.element);

    this.content = content;

    var isHooking = (arguments[2] && arguments[2].hook);
    var isShowOnClick = (arguments[2] && arguments[2].showOn == 'click');

    this.options = Object.extend({
      className: 'default',                 // see css, this will lead to .prototip .default
      closeButton: Tips.closeButtons,       // true, false
      delay: !isShowOnClick ? 0.2 : false,  // seconds before tooltip appears
      duration: 0.3,                        // duration of the effect
      effect: false,                        // false, 'appear' or 'blind'
      hideAfter: false,                     // second before hide after no hover/activity
      hideOn: 'mouseleave',                 // or any other event, false
      hook: false,                          // { element: topLeft|topRight|bottomLeft|bottomRight, tip: see element }
      offset: isHooking ? {x:0, y:0} : {x:16, y:16},
      fixed: isHooking ? true : false,      // follow the mouse if false
      showOn: 'mousemove',
      target: this.element,                 // or another element
      title: false,
      viewport: isHooking ? false : true    // keep within viewport if mouse is followed
    }, arguments[2] || {});

    this.target = $(this.options.target);

    this.setup();

    if (this.options.effect) {
      Prototip.require('Scriptaculous');
      this.queue = { position: 'end', limit: 1, scope: this.wrapper.identify() }
    }

    Tips.add(this);
    this.activate();
  },

  setup: function() {
    this.wrapper = new Element('div', { className: 'prototip' }).setStyle({
      display: 'none', zIndex: Tips.zIndex });
    this.wrapper.identify();

    if (Tips.fixIE) {
      this.iframeShim = new Element('iframe', {
        className : 'iframeShim',
		src: 'javascript:false;',
		frameBorder: 0
      }).setStyle({
        display: 'none',
		zIndex: Tips.zIndex - 1
      });
    }

    this.tip = new Element('div', { className : 'content' }).insert(this.content);
    this.tip.insert(new Element('div').setStyle({ clear: 'both' }));

    if (this.options.closeButton || (this.options.hideOn.element && this.options.hideOn.element == 'closeButton'))
      this.closeButton = new Element('a', { href: '#', className: 'close' });
  },

  build: function() {
    if (Tips.fixIE) document.body.appendChild(this.iframeShim).setOpacity(0);

    // effects go smooth with extra wrapper
    var wrapper = 'wrapper';
    if (this.options.effect) {
      this.effectWrapper = this.wrapper.appendChild(new Element('div', { className: 'effectWrapper' }));
      wrapper = 'effectWrapper';
    }

    this.tooltip = this[wrapper].appendChild(new Element('div', { className: 'tooltip ' + this.options.className }));

    if (this.options.title || this.options.closeButton) {
      this.toolbar = this.tooltip.appendChild(new Element('div', { className: 'toolbar' }));
      this.title = this.toolbar.appendChild(new Element('div', { className: 'title' }).update(this.options.title || ' '));
    }

    this.tooltip.insert(this.tip);
    document.body.appendChild(this.wrapper);

    // fixate elements for better positioning and effects
    var fixate = (this.options.effect) ? [this.wrapper, this.effectWrapper]: [this.wrapper];
    if (Tips.fixIE) fixate.push(this.iframeShim);

    // fix width
    var fixedWidth = this.wrapper.getWidth();
    fixate.invoke('setStyle', { width: fixedWidth + 'px' });

    // make toolbar width fixed
    if(this.toolbar) {
      this.wrapper.setStyle({ visibility : 'hidden' }).show();
      this.toolbar.setStyle({ width: this.toolbar.getWidth() + 'px'});
      this.wrapper.hide().setStyle({ visibility : 'visible' });
    }

    // add close button
    if (this.closeButton)
      this.title.insert({ top: this.closeButton }).insert(new Element('div').setStyle({ clear: 'both' }));

    var fixedHeight = this.wrapper.getHeight();
    fixate.invoke('setStyle', { width: fixedWidth + 'px', height: fixedHeight + 'px' });

    this[this.options.effect ? wrapper : 'tooltip'].hide();
  },

  activate: function() {
    this.eventShow = this.showDelayed.bindAsEventListener(this);
    this.eventHide = this.hide.bindAsEventListener(this);

    // if fixed use mouseover instead of mousemove for less event calls
    if (this.options.fixed && this.options.showOn == 'mousemove') this.options.showOn = 'mouseover';

    if(this.options.showOn == this.options.hideOn) {
      this.eventToggle = this.toggle.bindAsEventListener(this);
      this.element.observe(this.options.showOn, this.eventToggle);
    }

    var hideOptions = {
      'element': this.eventToggle ? [] : [this.element],
      'target': this.eventToggle ? [] : [this.target],
      'tip': this.eventToggle ? [] : [this.wrapper],
      'closeButton': [],
      'none': []
    };
    var el = this.options.hideOn.element;
    this.hideElement = el || (!this.options.hideOn ? 'none' : 'element');
    this.hideTargets = hideOptions[this.hideElement];
    if (!this.hideTargets && el && Object.isString(el)) this.hideTargets = this.tip.select(el);

    var realEvent = {'mouseenter': 'mouseover', 'mouseleave': 'mouseout'};
    $w('show hide').each(function(e) {
      var E = e.capitalize();
      var event = (this.options[e + 'On'].event || this.options[e + 'On']);
      this[e + 'Action'] = event;
      if (['mouseenter', 'mouseleave', 'mouseover', 'mouseout'].include(event)) {
        this[e + 'Action'] = (Tips.useEvent[event] || event);
        this['event' + E] = Prototip.capture(this['event' + E]);
      }
    }.bind(this));

    if (!this.eventToggle) this.element.observe(this.options.showOn, this.eventShow);
    if (this.hideTargets) this.hideTargets.invoke('observe', this.hideAction, this.eventHide);

    // add postion observer to moving showOn click tips
    if (!this.options.fixed && this.options.showOn == 'click') {
      this.eventPosition = this.position.bindAsEventListener(this);
      this.element.observe('mousemove', this.eventPosition);
    }

    // close button
    this.buttonEvent = this.hide.wrap(function(proceed, event) {
      event.stop();
      proceed(event);
    }).bindAsEventListener(this);
    if (this.closeButton) this.closeButton.observe('click', this.buttonEvent);

    // delay timeout
    if (this.options.showOn != 'click' && (this.hideElement != 'element')) {
      this.eventCheckDelay = Prototip.capture(function() {
        this.clearTimer('show');
      }).bindAsEventListener(this);
      this.element.observe(Tips.useEvent['mouseout'], this.eventCheckDelay);
    }

    // activity (hideAfter, raise)
    var elements = [this.element, this.wrapper];
    this.activityEnter = Prototip.capture(function() {
      Tips.raise(this.wrapper);
      this.cancelHideAfter();
    }).bindAsEventListener(this);
    this.activityLeave = Prototip.capture(this.hideAfter).bindAsEventListener(this);
    elements.invoke('observe', Tips.useEvent['mouseover'], this.activityEnter);
    elements.invoke('observe', Tips.useEvent['mouseout'], this.activityLeave);
  },

  deactivate: function() {
    if(this.options.showOn == this.options.hideOn)
      this.element.stopObserving(this.options.showOn, this.eventToggle);
    else {
      this.element.stopObserving(this.options.showOn, this.eventShow);
      if (this.hideTargets) this.hideTargets.invoke('stopObserving');
    }

    if (this.eventPosition) this.element.stopObserving('mousemove', this.eventPosition);
    if (this.closeButton) this.closeButton.stopObserving();
    if (this.eventCheckDelay) this.element.stopObserving('mouseout', this.eventCheckDelay);
    this.wrapper.stopObserving();
    this.element.stopObserving(Tips.useEvent['mouseover'], this.activityEnter);
    this.element.stopObserving(Tips.useEvent['mouseout'], this.activityLeave);
  },

  showDelayed: function(event) {
    if (!this.tooltip) this.build();
    this.position(event); // follow mouse
    if (this.wrapper.visible()) return;

    this.clearTimer('show');
    this.showTimer = this.show.bind(this).delay(this.options.delay);
  },

  clearTimer: function(timer) {
    if (this[timer + 'Timer']) clearTimeout(this[timer + 'Timer']);
  },

  show: function() {
    if (this.wrapper.visible() && this.options.effect != 'appear') return;

    if (Tips.fixIE) this.iframeShim.show();
    Tips.addVisibile(this.wrapper);
    this.wrapper.show();

    if (!this.options.effect) this.tooltip.show();
    else {
      if (this.activeEffect) Effect.Queues.get(this.queue.scope).remove(this.activeEffect);
      this.activeEffect = Effect[Effect.PAIRS[this.options.effect][0]](this.effectWrapper,
        { duration: this.options.duration, queue: this.queue});
    }
  },

  hideAfter: function(event) {
    if (!this.options.hideAfter) return;
    this.cancelHideAfter();
    this.hideAfterTimer = this.hide.bind(this).delay(this.options.hideAfter);
  },

  cancelHideAfter: function() {
    if (this.options.hideAfter) this.clearTimer('hideAfter');
  },

  hide: function() {
    this.clearTimer('show');
    if(!this.wrapper.visible()) return;

    if (!this.options.effect) {
      if (Tips.fixIE) this.iframeShim.hide();
      this.tooltip.hide();
      this.wrapper.hide();
      Tips.removeVisible(this.wrapper);
    }
    else {
      if (this.activeEffect) Effect.Queues.get(this.queue.scope).remove(this.activeEffect);
      this.activeEffect = Effect[Effect.PAIRS[this.options.effect][1]](this.effectWrapper,
        { duration: this.options.duration, queue: this.queue, afterFinish: function() {
        if (Tips.fixIE) this.iframeShim.hide();
        this.wrapper.hide();
        Tips.removeVisible(this.wrapper);
      }.bind(this)});
    }
  },

  toggle: function(event) {
    if (this.wrapper && this.wrapper.visible()) this.hide(event);
    else this.showDelayed(event);
  },

  position: function(event) {
    Tips.raise(this.wrapper);

    var offset = {left: this.options.offset.x, top: this.options.offset.y};
    var targetPosition = Position.cumulativeOffset(this.target);
    var tipd = this.wrapper.getDimensions();
    var pos = { left: (this.options.fixed) ? targetPosition[0] : Event.pointerX(event),
      top: (this.options.fixed) ? targetPosition[1] : Event.pointerY(event) };

    // add offsets
    pos.left += offset.left;
    pos.top += offset.top;

    if (this.options.hook) {
      var dims = {target: this.target.getDimensions(), tip: tipd}
      var hooks = {target: Position.cumulativeOffset(this.target), tip: Position.cumulativeOffset(this.target)}

      for (var z in hooks) {
        switch (this.options.hook[z]) {
          case 'topRight':
            hooks[z][0] += dims[z].width;
            break;
          case 'topMiddle':
            hooks[z][0] += (dims[z].width / 2);
            break;
          case 'rightMiddle':
            hooks[z][0] += dims[z].width;
            hooks[z][1] += (dims[z].height / 2);
            break;
          case 'bottomLeft':
            hooks[z][1] += dims[z].height;
            break;
          case 'bottomRight':
            hooks[z][0] += dims[z].width;
            hooks[z][1] += dims[z].height;
            break;
          case 'bottomMiddle':
            hooks[z][0] += (dims[z].width / 2);
            hooks[z][1] += dims[z].height;
            break;
          case 'leftMiddle':
            hooks[z][1] += (dims[z].height / 2);
            break;
        }
      }

      // move based on hooks
      pos.left += -1*(hooks.tip[0] - hooks.target[0]);
      pos.top += -1*(hooks.tip[1] - hooks.target[1]);
    }

    // move tooltip when there is a different target
    if (!this.options.fixed && this.element !== this.target) {
      var elementPosition = Position.cumulativeOffset(this.element);
      pos.left += -1*(elementPosition[0] - targetPosition[0]);
      pos.top += -1*(elementPosition[1] - targetPosition[1]);
    }

    if (!this.options.fixed && this.options.viewport) {
      var scroll = document.viewport.getScrollOffsets();
      var viewport = document.viewport.getDimensions();
      var pair = {left: 'width', top: 'height'};

      for(var z in pair) {
        if ((pos[z] + tipd[pair[z]] - scroll[z]) > viewport[pair[z]])
          pos[z] = pos[z] - tipd[pair[z]] - 2*offset[z];
      }
    }

    var setPos = { left: pos.left + 'px', top: pos.top + 'px' };
    this.wrapper.setStyle(setPos);
    if (Tips.fixIE) this.iframeShim.setStyle(setPos);
  }
});

Prototip.start();