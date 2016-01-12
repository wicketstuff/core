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
 
 /**
 * @class Spinner Control Widget
 *
 * @author Gerolf Seitz (gerolf dot seitz at gmail dot com)
 * @author Ken Snyder (kendsnyder at gmail dot com)
 * @date 2007-09-06
 * @version 1.0
 * @tested in FF2, IE7, Safari 3, Opera 9
 */

if (typeof(Wicket) == "undefined")
	Wicket = { };

Wicket.Spinner = function() { this.initialize.apply(this, arguments); };


Wicket.Spinner.prototype = {

  /**
   * @constructor  Create the spinner using an input, an up button, and a down button
   *
   * @param string/Element  inputElement
   * @param string/Element  upElement
   * @param string/Element  downElement
   * @param object          options
   * Available Options:
   *   interval     The amount to increment (default=1)
   *   round        The number of decimal points to which to round (default=0)
   *   min          The lowest allowed value, false for no min (default=false)
   *   max          The highest allowed value, false for no max (default=false)
   *   prefix       String to prepend when updating (default='')
   *   suffix       String to append when updating (default='')
   *   data         An array giving a list of items through which to iterate (default=false)
   *   onIncrement  Function to call after incrementing
   *   onDecrement  Function to call after decrementing
   *   afterUpdate  Function to call after update of the value
   *   onStop       Function to call on click or mouseup
   * @return void
   */
  initialize: function(inputElement, upElement, downElement, options) {
    // store the elements    
    this.inputElement = this.get(inputElement);
    this.upElement = this.get(upElement);
    this.downElement = this.get(downElement);
    // store the options
    this.options = {};    
    this.augment(this.options, {
      interval: 1,
      round: 0,
      min: false,
      max: false,
      prefix: '',
      suffix: '',
      data: false,
      onIncrement: function() {},      
      onDecrement: function() {},      
      afterUpdate: function() {},      
      onStop: function() {}      
    });
    this.augment(this.options, options);
    // set initial values
    this.reset();
    // build our update function
    this.buildUpdateFunction();
    // define the rate of increasing speed
    if (navigator.userAgent.match(/MSIE\s([^;]*)/)) {
      this.speedHash = {5: 300, 10: 175, 20: 90, 30: 17};
    } else {
      this.speedHash = {5: 250, 10: 85, 20: 35, 30: 10};
    }
    // attach listeners
    this.observe();
  },
  
  /**
   * Shortcut for document.getElementById(id)
   */
  get: function(id) {
    return document.getElementById(id);
  },
  
  /**
   * Adds all properties of augmenter to the augmentee, overwriting existing ones
   * 
   * @ return void
   */
  augment: function(augmentee, augmenter) {
    for (el in augmenter) {
      augmentee[el] = augmenter[el];
    }  
  },
  
  /**
   * Creates a delegate for a function to retain scope
   * 
   * @return the delegated method
   */
  createDelegate : function(method, obj, args) {
	return function() {
        return method.apply(obj || window, args || arguments);   
	} 
  },
  
  /**
   * Helper function to define the update function
   *
   * @return void
   */
  buildUpdateFunction: function() {
    // do we have a data list?
    if (this.options.data == false) {
      // no, we are an integer or decimal
      this.updateValue = this.createDelegate(function(multiplier) {
        // parse the value ignoring the substring
        var value = parseFloat(this.inputElement.value.replace(/^(.*?)([\-\d\.]+)(.*)$/, '$2'));
        if (isNaN(value)) value = this.options.min || 0;
        // what are we adding
        if (multiplier == 1) {
          value = (value + this.options.interval).toFixed(this.options.round);
        } else if (multiplier == -1) {
          value = (value - this.options.interval).toFixed(this.options.round);
        }
        // ensure value falls between the min and max
        if (this.options.min !== false)
          value = Math.max(this.options.min, value);
        if (this.options.max !== false)
          value = Math.min(this.options.max, value);            
        this.setValue(value);
        // call our afterUpdate function
        this.options.afterUpdate(this);
      }, this);
      // set an initial value if not given
      if (this.inputElement.value === '') {
        this.inputElement.value = this.options.min || 0;
      }
    } else if (this.options.data.constructor == Array && this.options.data.length) {
      // we have a data list
      // set the position pointer to the current or first element
      var current = this.options.data.indexOf(this.inputElement.value);
      this.pos = current == -1 ? 0 : current;
      // define our function
      this.updateValue = this.createDelegate(function(multiplier) {
        // advance the pointer forward or backward, wrapping between the last and first item
        this.pos = this.pos + multiplier;
        this.pos = this.pos < 0 ? this.options.data.length -1 : (
          this.pos > this.options.data.length - 1 ? 0 : this.pos
        );
        // update the value to the prefix, plus the rounded number, plus the suffix
        this.setValue(this.options.data[this.pos]);
        // call our afterUpdate function
        this.options.afterUpdate(this);
      }, this);
      // set an initial value if not given
      if (this.inputElement.value === '') {
        this.inputElement.value = this.options.data[0];
      }
    } else {
      // we have an invalid data option
      throw new Error('SpinnerControl.initialize(): invlalid value for options.data');
    }  
  },
  setValue: function(value) {
    this.inputElement.value = this.options.prefix + value + this.options.suffix;  
  },
  /**
   * Helper function to attach listeners
   */
  observe: function() {
    // define a pre-bound stop function
    var stop = this.createDelegate(this.stop, this);        
    // observe the input
    // begin incrementing at start of a keypress
    this.inputElement.onkeydown = this.createDelegate(function(evt) {this.keyStart(evt); }, this);    
   
   // stop incrementing at the end of a keypress       
   this.inputElement.onkeyup = stop;
   
    // reformat and enforce min-max for typed values 
    this.inputElement.onblur = this.createDelegate(this.updateValue, this, [0]);
    
    // observe the up element
    // begin incrementing at start of click    
    this.upElement.onmousedown = this.createDelegate(this.clickStart, this, [1]);
    
    // stop incrementing at end of click     
    this.upElement.onmouseup = stop;
    
   // in the case of a click and drag, also stop     
    this.upElement.onmouseout = stop;
      
    // observe the down element  
    // begin decrementing at start of click
    this.downElement.onmousedown = this.createDelegate(this.clickStart, this, [-1]);
    // stop decrementing at end of click 
    this.downElement.onmouseup = stop;
   // in the case of a click and drag, also stop 
    this.downElement.onmouseout = stop;
  },
  /**
   * Start incrementing or decrementing based on a pressed key
   *
   * @event keydown on this.inputElement
   * @param object evt
   * @return void
   */
  keyStart: function(evt) {
    if (this.running == false) {
      if (window.event) evt = window.event;
      var key;
      if (typeof(evt.keyCode) != 'undefined') key = evt.keyCode;
      else if (evt.which) key = evt.which;
      if (key == 0x26) { // KEY_UP
        this.running = 'key';
        this.increment();
      } else if (key == 0x28) { // KEY_DOWN
        this.running = 'key';
        this.decrement();
      }
    }
  },
  /**
   * Start incrementing or decrementing based on a mousedown action
   *
   * @param boolean multiplier  If multipler is 1, increment
   * @return void
   */  
  clickStart: function() {
    this.running = 'mouse';
    multiplier = arguments[0];
    if (multiplier == 1) {
      this.increment();
    } else {
      this.decrement();
    }
  },
  /**
   * Set to resting state
   *
   * return @void
   */
  reset: function() {
    // blur the up/down buttons if we got started by clicking
    if (this.running == 'mouse') {
      this.upElement.blur();
      this.downElement.blur();      
    }
    this.running = false;
    this.iterations = 0;
  },
  /**
   * Reset and clear timeout
   *
   * @return void
   */
  stop: function() {
    this.reset();
    window.clearTimeout(this.timeout);
    this.options.onStop(this);
  },
  /**
   * Increment the value
   *
   * @return void
   */
  increment: function() {
    this.updateValue(1);
    this.timeout = window.setTimeout(this.createDelegate(this.increment, this), this.getSpeed());
    this.options.onIncrement(this);
    this.checkInputReplacement();
  },
  /**
   * Decrement the value
   *
   * @return void
   */  
  decrement: function() {
    this.updateValue(-1);
    this.timeout = window.setTimeout(this.createDelegate(this.decrement, (this)), this.getSpeed());
    this.options.onDecrement(this);
    this.checkInputReplacement();
  },
  /**
    * If the input element is replaced by Ajax while incrementing/decrementing using timer, that timer needs to get stopped
    * or it will enter an endless loop of increment/decrement (no mouse out/key up will come to stop it rescheduling itself).
    */
  checkInputReplacement: function() {
   	if (this.inputElement != this.get(this.inputElement.id)) this.stop();
  },
  /**
   * Get the delay for the next timeout
   * Overwrite this function for custom speed schemes
   *
   * @return integer
   */  
  getSpeed: function() {
    this.iterations++;
    for (var iterations in this.speedHash) {
      if (this.iterations < iterations) {
        return this.speedHash[iterations];
      }
    }
    return this.speedHash[30];
  } 
};