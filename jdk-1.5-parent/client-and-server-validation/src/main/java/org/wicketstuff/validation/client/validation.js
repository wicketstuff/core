/*
 * Copyright (C) 2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 *
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
 * Wicket Client and Server Validation Framework 
 *
 * @author Jeremy Thomerson
 */

// validation engine
function ClientAndServerValidationEngine() {
	this.validatorsByFormID = new Object();
	this.feedbackContainersByFormID = new Object();
	this.forms = new Array();
}

ClientAndServerValidationEngine.prototype.registerValidator = function(validator) {
	var formID = validator.getFormID();
	this.addForm(validator.getForm());
	if (this.validatorsByFormID[formID] == null) {
		this.validatorsByFormID[formID] = new Array();
	}
	this.validatorsByFormID[formID].push(validator);
};

ClientAndServerValidationEngine.prototype.addForm = function(form) {
	var found = false;
	for (var i = 0; i < this.forms.length; i++) {
		if (this.forms[i] == form) {
			found = true;
		}
	}
	if (found == false) {
		this.forms.push(form);
	}
};

ClientAndServerValidationEngine.prototype.addFormOnloadEvents = function() {
	for (var i = 0; i < this.forms.length; i++) {
		var form = this.forms[i];
		var validators = this.validatorsByFormID[form.id];
		var engine = this;
		// TODO: what if form already had an onsubmit?  we still need to handle that
		form.onsubmit = function() {
			var allValid = true;
			var messages = new Array();
			for (var v = 0; v < validators.length; v++) {
				var val = validators[v];
				var valid = val.validate();
				if (valid == false) {
					var message = val.getFailedMessage();
					if (message != null) {
						messages.push(message);
					}
					allValid = false;
				}
			}
			if (allValid == false || messages.length > 0) {
				engine.renderMessages(form.id, messages);
			}
			return allValid;
		}
	}
};

ClientAndServerValidationEngine.prototype.renderMessages = function(formID, messages) {
	var feedback = this.feedbackContainersByFormID[formID];
	if (feedback != null) {
		var html = '<ul>';
		for (var i = 0; i < messages.length; i++) {
			html += '<li class="feedbackPanelERROR">';
			html += '<span class="feedbackPanelERROR">' + messages[i] + '</span>';
			html += '</li>'
		}
		html += '</ul>';
		Wicket.replaceOuterHtml(feedback, html);	
		return;
	}	
	
	// default rendering is through a JS alert:
	var longMessage = '';
	for (var i = 0; i < messages.length; i++) {
		longMessage += ' - ' + messages[i] + '\n';
	}
	alert(longMessage);
};

ClientAndServerValidationEngine.prototype.registerFeedbackContainerForForm = function(formID, containerID) {
	this.feedbackContainersByFormID[formID] = document.getElementById(containerID);
};

ClientAndServerValidator = new ClientAndServerValidationEngine();

// base for all validators
function AbstractValidator(formID, componentID, failedValidationMessage) {
	this.construct(formID, componentID, failedValidationMessage);
}

AbstractValidator.prototype.construct = function(formID, componentID, failedValidationMessage) {
	this.setFormID(formID);
	this.setComponentID(componentID);
	this.setFailedValidationMessage(failedValidationMessage);
};

// getters / setters
AbstractValidator.prototype.getFailedValidationMessage = function() {
	return this.failedValidationMessage;
};

AbstractValidator.prototype.setFailedValidationMessage = function(failedValidationMessage) {
	this.failedValidationMessage = failedValidationMessage;
	return this;
};

AbstractValidator.prototype.getFormID = function() {
	return this.formID;
};

AbstractValidator.prototype.setFormID = function(formID) {
	this.formID = formID;
	return this;
};

AbstractValidator.prototype.getComponentID = function() {
	return this.componentID;
};

AbstractValidator.prototype.setComponentID = function(componentID) {
	this.componentID = componentID;
	return this;
};

// functionality methods
AbstractValidator.prototype.getForm = function() {
	return document.getElementById(this.getFormID());
};

AbstractValidator.prototype.getComponent = function() {
	return document.getElementById(this.getComponentID());
};

AbstractValidator.prototype.getFailedMessage = function() {
	var msg = this.interpolateVariables(this.failedValidationMessage);
	return msg;
};

AbstractValidator.prototype.interpolateVariables = function(message) {
	// TODO: there are better ways of doing this
	var msg = message;
	var value = this.getComponent().value;
	var indexOfVal = msg.indexOf('{VAL}');
	if (indexOfVal != -1) {
		msg = msg.substr(0, indexOfVal) + value + msg.substr(indexOfVal + 5);
	}
	return msg;
};

AbstractValidator.prototype.validate = function() {
	alert('Programming error - this method should be overridden by concrete validator instances');
	return false;
};

// RequiredValidator
function RequiredValidator(formID, componentID, failedValidationMessage) {
	this.construct(formID, componentID, failedValidationMessage);
}
RequiredValidator.prototype = new AbstractValidator();

RequiredValidator.prototype.validate = function() {
	var comp = this.getComponent();
	var val = comp.value;
	if (val == undefined || val == null || val == '') {
		return false;
	}
};

// StringExactLengthValidator
function StringExactLengthValidator(formID, componentID, failedValidationMessage) {
	this.construct(formID, componentID, failedValidationMessage);
}
StringExactLengthValidator.prototype = new AbstractValidator();

StringExactLengthValidator.prototype.setExactLength = function(length) {
	this.exactLength = length;
	return this;
};

StringExactLengthValidator.prototype.validate = function() {
	var comp = this.getComponent();
	var val = comp.value;
	if (val == undefined || val == null || val.length != this.exactLength) {
		return false;
	}
};

// StringLengthBetweenValidator
function StringLengthBetweenValidator(formID, componentID, failedValidationMessage) {
	this.construct(formID, componentID, failedValidationMessage);
}
StringLengthBetweenValidator.prototype = new AbstractValidator();

StringLengthBetweenValidator.prototype.setMinimumLength = function(length) {
	this.minimumLength = length;
	return this;
};

StringLengthBetweenValidator.prototype.setMaximumLength = function(length) {
	this.maximumLength = length;
	return this;
};

StringLengthBetweenValidator.prototype.validate = function() {
	var comp = this.getComponent();
	var val = comp.value;
	if (val == undefined || val == null || val.length < this.minimumLength || val.length > this.maximumLength) {
		return false;
	}
};

// StringMaximumLengthValidator
function StringMaximumLengthValidator(formID, componentID, failedValidationMessage) {
	this.construct(formID, componentID, failedValidationMessage);
}
StringMaximumLengthValidator.prototype = new AbstractValidator();

StringMaximumLengthValidator.prototype.setMaximumLength = function(length) {
	this.maximumLength = length;
	return this;
};

StringMaximumLengthValidator.prototype.validate = function() {
	var comp = this.getComponent();
	var val = comp.value;
	if (val == undefined || val == null || val.length > this.maximumLength) {
		return false;
	}
};

// StringMinimumLengthValidator
function StringMinimumLengthValidator(formID, componentID, failedValidationMessage) {
	this.construct(formID, componentID, failedValidationMessage);
}
StringMinimumLengthValidator.prototype = new AbstractValidator();

StringMinimumLengthValidator.prototype.setMinimumLength = function(length) {
	this.minimumLength = length;
	return this;
};

StringMinimumLengthValidator.prototype.validate = function() {
	var comp = this.getComponent();
	var val = comp.value;
	if (val == undefined || val == null || val.length < this.minimumLength) {
		return false;
	}
};

