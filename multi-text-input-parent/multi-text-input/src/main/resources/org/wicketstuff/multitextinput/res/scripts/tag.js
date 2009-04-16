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
 * 
 * Author: Craig Tataryn (craiger AT tataryn DOT net)
 */
var BACKSPACE = 8;
var ENTER = 13;
var TAB = 9;
var tagEntries = [];

/*
 * Contains Tag objects, usage is as follows:
 *
 *     var ds =    {
 *                   results: ['apple', 'orange', 'banana', 'plum']
 *               };
 *   var tagEntry = new TagEntry(document.getElementById('tagEntry'), ds, 
 *                      'tags[]', 15);
 *
 * Where 'tagEntry' is the name of an element which will act as the container
 * for tags
 * 
 * parentElem   : Element this widget is attached to (ie. a div)
 * dataSource   : an array of strings to display in the widget when first rendered
 * formInputName: name of the form input which the component should use, items removed
 *                from the input will have it's form input name prepended with "removed_"
 * limitTagSize : allowable amount of characters the user can enter into the text field
 */
function TagEntry(parentElem, dataSource, formInputName, limitTagSize) {
    this.tags = [];

    $(parentElem).addClassName('myclearfix').addClassName('tag_parent');
    
    this.elem = $(document.createElement('div')).addClassName('myclearfix').addClassName('tag_entry');
    $(parentElem).appendChild(this.elem);
    $(parentElem).observe('click', function(evnt) {
        this.inputElem.removeClassName('tag_input_hide');
        this.inputElem.focus();
        this.unhighlightTag();
    }.bind(this));
    $(parentElem).observe('mousedown', function(evnt) { 
        evnt.cancelBubble = true;
        return false
    });

    this.ds = dataSource;
    //div which will hold our hidden inputs for form submission
    this.hiddenInputsContainer = $(document.createElement('div'));
    this.elem.appendChild(this.hiddenInputsContainer);
    this.hiddenInputName = formInputName;

    
    this.getTextWidth = _getTextWidth.bind(this);
    this.inputElem = $(document.createElement('input'));
    this.inputElem.type = 'text';
    if (limitTagSize != undefined) this.inputElem.maxLength = 15;
    this.inputElem.addClassName('tag_input');
    this.inputElem.observe('keydown', _inputElemKeyDownHandler.bind(this));
    this.inputElem.observe('keyup', _inputElemKeyUpHandler.bind(this));
    this.elem.appendChild(this.inputElem);
}

TagEntry.prototype.unhighlightTag = function() {
    if (this.tagHighlighted) {
        this.tagHighlighted.elem.removeClassName('tag_hover');
        this.tagHighlighted = null;
        this.inputElem.removeClassName('tag_input_hide');
    }
}

function _inputElemKeyDownHandler(evnt) {
    if (this.tagHighlighted) {
        evnt.preventDefault();
        if (evnt.keyCode == BACKSPACE) {
            this.tagHighlighted.remove();
            if (this.tags.length > 0) {
                var lastTag = this.tags[this.tags.length-1];
                lastTag.elem.addClassName('tag_hover');
                this.tagHighlighted = lastTag;
            } else {
                //unhide the input
                this.tagHighlighted = null;
                this.inputElem.removeClassName('tag_input_hide');
            }
        } else if (evnt.keyCode == TAB) {
            //unhide the input
            this.unhighlightTag();
        }
    } else {
        //set the width of the input based on the width of the text
        var size = this.getTextWidth($(evnt.target).value);
        //if (this.elem.getWidth() <= size+padding) 
        //    size = this.elem.getWidth() - padding;
        //else
            size = size + this.getTextWidth('WW');
        evnt.target.setStyle({'width': size + 'px'});

        var tagText = this.inputElem.value.strip();
        if (evnt.keyCode == ENTER) {
            evnt.preventDefault();
        } else if (evnt.keyCode == BACKSPACE && tagText == '' && this.tags.length > 0) {
            var lastTag = this.tags[this.tags.length-1];
            lastTag.elem.addClassName('tag_hover');
            this.tagHighlighted = lastTag;
            this.inputElem.addClassName('tag_input_hide');
        }
    }
}

function _inputElemKeyUpHandler(evnt) {
    if (this.tagHighlighted) {
        evnt.preventDefault();
    } else {
        var tagText = this.inputElem.value.strip();
        if (evnt.keyCode == ENTER && tagText != '') {
            //they hit Enter, add the tag if it's not empty
            this.addTag(tagText);
            this.inputElem.value = '';
            this.inputElem.focus();
        } 
    }
}

//little trick to get the width of text, add a span to the document
//and have it off screen, set the text you want to measure then check the span's offsetWidth
function _getTextWidth(text) {
        var span = $(document.createElement('span'));
        span.update(text);
        span.setStyle({position:'absolute', left:'-10000px'});
        this.elem.appendChild(span);
        var size = span.getWidth();
        this.elem.removeChild(span);
        return size;
}

TagEntry.prototype.renderTags = function() {
    var results = this.ds.results;
    if (results) {
        for (var i=0;i<results.length;i++) {
           this.addTag(results[i]); 
        }
    } 
}

function removeTag(tagText) {
    var node = this.hiddenInputsContainer.firstChild;
    while ( node && node.tagName.toLowerCase() == 'input' && node.value != tagText) { 
        node = node.nextSibling;
    }
    if (node && node.value == tagText) {
        var tag = new Tag(tagText);
        //add the new hidden input
        this.hiddenInputsContainer.appendChild(this.createNewTagHiddenInput(tag, true));
        //remove the old hidden input
        this.hiddenInputsContainer.removeChild(node);
    }
    var i;
    var found = false;
    for (i=0;i<this.tags.length;i++) {
        if (this.tags[i].value == tagText){
            found = true;
            break;
        }
    }
    if (found) {
        this.tags.splice(i,1);
    }
}

TagEntry.prototype.addTag = function(tagText) {
    for (i=0;i<this.tags.length;i++) {
        if (this.tags[i].value == tagText)  {
            //tag already exists
            return;
        }
    }
    var tag = new Tag(tagText);
    this.tags.push(tag);
    this.hiddenInputsContainer.appendChild(this.createNewTagHiddenInput(tag));
    this.elem.insertBefore(tag.elem, this.inputElem);
    tag.ondelete = removeTag.bind(this);
    tag.onmouseover = function(evnt) {
        this.unhighlightTag(); 
    }.bind(this);
}

TagEntry.prototype.createNewTagHiddenInput  = function(tag, removed) {
    var input = $(document.createElement('input'));
    input.type = 'hidden';
    var tagName = (removed ? 'removed_' : '') + this.hiddenInputName;
    input.name = tagName;
    input.value = tag.value;
    return input;
}

/*
 * Initialize this class, then use it like so:
 *
 * var tag = new Tag('facebook');
 * someElem.addChild(tag.elem);
 *
 */
function Tag(value) {
    this.value = value;
    this.elem = $(document.createElement('a'));
    this.elem.addClassName('tag');
    this.elem.href = '#';
    this.elem.tabIndex = -1;
    this.elem.observe('mouseover', function(evnt) {
        this.onmouseover(evnt);
        this.elem.addClassName('tag_hover'); 
    }.bind(this));
    this.elem.observe('mouseout', function(evnt) {
        this.elem.removeClassName('tag_hover'); 
    }.bind(this));

    //need to add the element to the DOM before innerXHTML is called
    this.render();
}

Tag.prototype.ondelete = function(tagText) {}

Tag.prototype.remove = function() {
    this.ondelete(this.value);
    this.elem.parentNode.removeChild(this.elem);
}

Tag.prototype.render = function() {
    //var tagHtml = ['<span><span><span><span>', this.value, '<span class="tag_x">&nbsp;</span>', '</span></span></span></span>'].join(''); 
    var prevSpan;
    var lastSpan;
    for (var i=0;i<4;i++) {
        lastSpan = $(document.createElement('span'));
        if (prevSpan == null) {
            this.elem.appendChild(lastSpan);
        } else {
            prevSpan.appendChild(lastSpan);
        }
        prevSpan = lastSpan;
    }
    lastSpan.update(this.value.escapeHTML());
    var xSpan = $(document.createElement('span'));
    xSpan.addClassName('tag_x');
    xSpan.observe('click', function(evnt) {
        this.remove();
    }.bind(this));
    xSpan.update(' ');
    lastSpan.appendChild(xSpan);
}
