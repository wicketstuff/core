/***********************************************************************
 * YAV - Yet Another Validator  v2.0                                   *
 * Copyright (C) 2005-2008                                             *
 * Author: Federico Crivellaro <f.crivellaro@gmail.com>                *
 * WWW: http://yav.sourceforge.net                                     *
 *                                                                     *
 * This library is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU Lesser General Public          *
 * License as published by the Free Software Foundation; either        *
 * version 2.1 of the License, or (at your option) any later version.  *
 *                                                                     *
 * This library is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU   *
 * Lesser General Public License for more details.                     *
 *                                                                     *
 * You should have received a copy of the GNU Lesser General Public    *
 * License along with this library; if not, write to the Free Software *
 * Foundation, Inc.,59 Temple Place,Suite 330,Boston,MA 02111-1307 USA *
 *                                                                     *
 * last revision:  2 MAR 2008                                          *
 ***********************************************************************/

var yav = {

//------------------------------------------------------------ PUBLIC FUNCTIONS
undef: undefined,
isFocusSet: undefined,
internalRules: undefined,
f: undefined,
formEvt: undefined,
fieldsEvt: new Array(),
rulesEvt: new Array(),
helpEvt: new Array(),
mask: new Array(),
onOKEvt: new Array(),
onErrorEvt: new Array(),
preValidationEvt: new Array(),
filterByName: null,

performCheck: function (formName, strRules, alertType, filterErrorsByName) {
	yav.filterByName = (filterErrorsByName) ? filterErrorsByName: null;
	for(var j=0; j<yav.preValidationEvt.length; j++) {
		if (yav.filterByName==yav.preValidationEvt[j].name) {
			var preValidationResult = eval(yav.preValidationEvt[j].fn);
			yav.preValidationEvt[j].executedWithSuccess = preValidationResult;
			if (!preValidationResult) {
				return preValidationResult;
			}
			break;
		}
	}
	yav.isFocusSet = false;
    var rules = yav.makeRules(strRules);
    yav.internalRules = yav.makeRules(strRules);
    yav.f = document.forms[formName];
    if( !yav.f ) {
        yav.debug('DEBUG: could not find form object ' + formName);
        return null;
    }
    var errors = new Array();
    var ix = 0;
    if (rules.length) {
        for(var i=0; i<rules.length; i++) {
            var aRule = rules[i];
            if (aRule!=null) {
				yav.highlight(yav.getField(yav.f, aRule.el), yav_config.inputclassnormal);
            }
        }
    } else {
        if (rules!=null) {
			yav.highlight(yav.getField(yav.f, rules.el), yav_config.inputclassnormal);
        }
    }
    if (rules.length) {
		for(var i=0; i<rules.length; i++) {
            var aRule = rules[i];
            var anErr = null;
            if (aRule==null) {
                //do nothing
            } else if (aRule.ruleType=='pre-condition' || aRule.ruleType=='post-condition' || aRule.ruleType=='andor-operator') {
                //do nothing
            } else if (aRule.ruleName=='implies') {
                pre  = aRule.el;
                post = aRule.comparisonValue;
                var oldClassName = yav.getField(yav.f, rules[pre].el).className;
				if ( yav.filterByName!=null ) {
					if (rules[pre].el==yav.filterByName || rules[post].el==yav.filterByName) {
						yav.clearInlineSpans(rules[pre].el, rules[post].el);
					}
				}
                if ( yav.checkRule(yav.f, rules[pre])==null && yav.checkRule(yav.f, rules[post])!=null ) {
                    anErr = yav.deleteInline(aRule.alertMsg) + '__inline__'+rules[post].el;
                } else if ( yav.checkRule(yav.f, rules[pre])!=null ) {
                    yav.getField(yav.f, rules[pre].el).className = oldClassName;
                }
            } else if (aRule.ruleName=='date_lt' || aRule.ruleName=='date_le') {
                if ( yav.filterByName!=null ) {
					if (aRule.comparisonValue && aRule.comparisonValue.indexOf('$'+yav.filterByName)==0) {
						yav.clearInlineSpans(aRule.el, yav.filterByName);
					}
				}
                anErr = yav.checkRule(yav.f, aRule);
            } else {
                anErr = yav.checkRule(yav.f, aRule);
            }
            if ( anErr!=null ) {
				if (yav.filterByName && yav.filterByName!=null) {
					if (aRule.ruleName=='implies') {
						if (rules[pre].el==yav.filterByName || rules[post].el==yav.filterByName) {
							yav.clearInlineSpans(rules[pre].el, rules[post].el);
						}
						aRule = rules[aRule.comparisonValue];
					}
					//todo
					if (aRule.ruleName=='or') {
                        var tmp = aRule.comparisonValue.split('-');
                        for(var t=0; t<tmp.length; t++) {
                            yav.clearInlineSpans(rules[tmp[t]].el);
                        }
						if (rules[aRule.el].el==yav.filterByName) {
							yav.clearInlineSpans(rules[aRule.el].el);
						}
						aRule = rules[aRule.el];
					}
					//
					if (aRule.el==yav.filterByName || (aRule.comparisonValue && aRule.comparisonValue.indexOf('$'+yav.filterByName)==0)) {
						for(var z=0; z<rules.length; z++) {
							if (rules[z].ruleName=='implies' && rules[rules[z].el].el==aRule.el) {
								yav.clearInlineSpans(rules[rules[z].comparisonValue].el);
							}
						}
						errors[ix] = anErr;
	                    ix++;
						break;				
					}
				} else {
	                errors[ix] = anErr;
	                ix++;
				}
            }
        }//for
    } else {
        var myRule = rules;
        err = yav.checkRule(yav.f, myRule);
        if ( err!=null ) {
			if (yav.filterByName && yav.filterByName != null) {
				if (myRule.el == yav.filterByName) {
					errors[0] = err;
				}
			} else {
				errors[0] = err;
			}
        }
    }
	var retval = yav.displayAlert(errors, alertType);
	yav.filterByName = null;
    return retval;
},

checkKeyPress: function (ev, obj, strRules) {
    var keyCode = null;
	keyCode = (typeof(ev.which))!='undefined' ? ev.which : window.event.keyCode;
    var rules = yav.makeRules(strRules);
    var keyAllowed = true;
    if (rules.length) {
        for(var i=0; i<rules.length; i++) {
            var aRule = rules[i];
            if (aRule.ruleName=='keypress' && aRule.el==obj.name) {
                keyAllowed = yav.isKeyAllowed(keyCode, aRule.comparisonValue);
                break;
            }
        }
    } else {
        var aRule = rules;
        if (aRule.ruleName=='keypress' && aRule.el==obj.name) {
            keyAllowed = yav.isKeyAllowed(keyCode, aRule.comparisonValue);
        }
    }
    if (!keyAllowed) {
        if ( typeof(ev.which)=='undefined' ) {
            window.event.keyCode=0;
        } else {
            ev.preventDefault();
            ev.stopPropagation();
            ev.returnValue=false;
        }
    }
    return keyAllowed;
},

init: function (formName, strRules) {
	yav.addMask('alphabetic', null, null, yav_config.alphabetic_regex);
	yav.addMask('alphanumeric', null, null, yav_config.alphanumeric_regex);
	yav.addMask('alnumhyphen', null, null, yav_config.alnumhyphen_regex);
	yav.addMask('alnumhyphenat', null, null, yav_config.alnumhyphenat_regex);
	yav.addMask('alphaspace', null, null, yav_config.alphaspace_regex);
	yav.formEvt = formName;
	yav.rulesEvt = strRules;
	if (strRules.length) {
        for(var i=0; i<strRules.length; i++) {
			var aRule = yav.splitRule(strRules[i]);
			var elm = yav.getField(document.forms[formName], aRule.el);
			if (elm && aRule.ruleName=='mask') {
				yav.addEvent(elm, 'keypress', yav.maskEvt.bindAsEventListener(elm));
			} else if (elm && !yav.inArray(yav.fieldsEvt, aRule.el) ) {
				var eventAdded = false;
				for(var j=0; j<yav.onOKEvt.length; j++) {
					if (elm.name==yav.onOKEvt[j].name) {
						yav.addEvent(elm, yav.onOKEvt[j].evType, 
							function(){
								if (yav.performEvt(this.name)) {
									yav.performOnOKEvt(this.name);
								} else {
									for(var k=0; k<yav.preValidationEvt.length; k++) {
										if (this.name==yav.preValidationEvt[k].name) {
											if (yav.preValidationEvt[k].executedWithSuccess==false) {
												yav.preValidationEvt[k].executedWithSuccess = null;
												return;
											}
											yav.preValidationEvt[k].executedWithSuccess = null;
											break;
										}
									}
									yav.performOnErrorEvt(this.name);
								}
						    } );
						eventAdded = true;
						break;
					}
				}
				if (!eventAdded) {
					for(var j=0; j<yav.onErrorEvt.length; j++) {
						if (elm.name==yav.onErrorEvt[j].name) {
							yav.addEvent(elm, yav.onErrorEvt[j].evType, 
								function(){
									if (!yav.performEvt(this.name)) {
										for(var k=0; k<yav.preValidationEvt.length; k++) {
											if (this.name==yav.preValidationEvt[k].name) {
												if (yav.preValidationEvt[k].executedWithSuccess==false) {
													yav.preValidationEvt[k].executedWithSuccess = null;
													return;
												}
												yav.preValidationEvt[k].executedWithSuccess = null;
												break;
											}
										}
										yav.performOnErrorEvt(this.name);
									}
							    } );
							eventAdded = true;
							break;
						}
					}
				}
				yav.fieldsEvt.push(aRule.el);
				if (!eventAdded) {
					yav.addEvent(elm, 'blur', 
					  function(){
						yav.performEvt(this.name);
					  });
				}
			}
        }
    } else {
		var rule = yav.splitRule(strRules);
		var elm = yav.getField(document.forms[formName], rule.el);
		if (elm && rule.ruleName=='mask') {
			yav.addEvent(elm, 'keypress', yav.maskEvt.bindAsEventListener(elm));
		} else if (elm) {
			var eventAdded = false;
			for(var j=0; i<yav.onOKEvt.length; j++) {
				if (elm.name==yav.onOKEvt[i].name) {
					yav.addEvent(elm, yav.onOKEvt[j].evType, 
						function(){
						    if (yav.performEvt(this.name)) {
								yav.performOnOKEvt(this.name);
							}
					    } );
					eventAdded = true;
					break;
				}
			}
			for(var j=0; j<yav.onErrorEvt.length; j++) {
				if (elm.name==yav.onErrorEvt[j].name) {
					yav.addEvent(elm, yav.onErrorEvt[j].evType, 
						function(){
							if (!yav.performEvt(this.name)) {
								for(var k=0; k<yav.preValidationEvt.length; k++) {
									if (this.name==yav.preValidationEvt[k].name) {
										if (yav.preValidationEvt[k].executedWithSuccess==false) {
											yav.preValidationEvt[k].executedWithSuccess = null;
											return;
										}
										yav.preValidationEvt[k].executedWithSuccess = null;
										break;
									}
								}
								yav.performOnErrorEvt(this.name);
							}
					    } );
					eventAdded = true;
					break;
				}
			}
			if (!eventAdded) {
				yav.addEvent(elm, 'blur', 
			      function(){
				    yav.performEvt(this.name);
			      });
			}
		}
	}
	if (yav.helpEvt.length>0) {
        for(var i=0; i<yav.helpEvt.length; i++) {
			var elm = yav.getField(document.forms[formName], yav.helpEvt[i].name);
			if ( elm ) {
			    if ( elm.focus ) {
    				yav.addEvent(elm, 'focus', 
    				  function(){
    					yav.showHelpEvt(this.name);
    				  });
    		    } else {
    				yav.addEvent(elm, 'click', 
    				  function(){
    					yav.showHelpEvt(this.name);
    				  });
    		    }
				if ( !yav.inArray(yav.fieldsEvt, yav.helpEvt[i].name) ) {
					yav.addEvent(elm, 'blur', 
					  function(){
						yav.cleanInline(this.name);
					  });
				}
			}
        }
	}
},

displayMsg: function(name, msg, clazz) {
    var elm = yav.get(yav_config.errorsdiv+'_'+name);
	if (elm) {
		elm.innerHTML = msg;
	    elm.className = clazz;
	    elm.style.display = '';
	} else {
		elm = yav.get(yav_config.errorsdiv);
		if (elm) {
			elm.innerHTML = msg;
		    elm.className = clazz;
		    elm.style.display = '';
		} else {
			alert(msg);
		}
	}
},

cleanInline: function(name) {
    yav.get(yav_config.errorsdiv+'_'+name).innerHTML = '';
    yav.get(yav_config.errorsdiv+'_'+name).className = '';
    yav.get(yav_config.errorsdiv+'_'+name).style.display = 'none';
},

addHelp: function (name, helpMsg) {
	var elem = new Object();
	elem.name = name;
	elem.help = helpMsg;
	yav.helpEvt.push(elem);
},

addMask: function (name, format, charsAllowed, regex) {
	var elem = new Object();
	elem.name = name;
	elem.format = format;
	elem.charsAllowed = charsAllowed;
	elem.regex = regex ? regex : null;
	yav.mask.push(elem);
},

postValidation_OnOK: function(name, evType, fn){
	var elem = new Object();
	elem.name = name;
	elem.evType = evType;
	elem.fn = fn;
	yav.onOKEvt.push(elem);
},

postValidation_OnError: function(name, evType, fn){
	var elem = new Object();
	elem.name = name;
	elem.evType = evType;
	elem.fn = fn;
	yav.onErrorEvt.push(elem);
},

preValidation: function(fn, name){
	var elem = new Object();
	elem.name = (name && name!=null)? name : null;
	elem.fn = fn;
	elem.executedWithSuccess = null;
	yav.preValidationEvt.push(elem);
},

//------------------------------------------------------------ PRIVATE FUNCTIONS

inArray: function(arr, value) {
	var found = false;
	for (var i=0;i<arr.length;i++) {
		if (arr[i]==value) {
			found = true;
			break;
		}
	}
	return found;
},

performEvt: function(name) {
	var elm = yav.get(yav_config.errorsdiv);
    if (elm) {
		elm.innerHTML = '';
	    elm.className = '';
	    elm.style.display = 'none';
	}
	return yav.performCheck(yav.formEvt, yav.rulesEvt, 'inline', name); 
},

performOnOKEvt: function(name) {
    for(var j=0; j<yav.onOKEvt.length; j++) {
		if (name==yav.onOKEvt[j].name) {
			eval(yav.onOKEvt[j].fn);
			break;
		}
	}
},

performOnErrorEvt: function(name) {
    for(var j=0; j<yav.onErrorEvt.length; j++) {
		if (name==yav.onErrorEvt[j].name) {
			eval(yav.onErrorEvt[j].fn);
			break;
		}
	}
},

showHelpEvt: function(name) {
    for(var i=0; i<yav.helpEvt.length; i++) {
		if (yav.helpEvt[i].name==name) {
            yav.get(yav_config.errorsdiv+'_'+name).innerHTML = yav.helpEvt[i].help;
            yav.get(yav_config.errorsdiv+'_'+name).className = yav_config.innerhelp;
            yav.get(yav_config.errorsdiv+'_'+name).style.display = '';
			break;
		}
    }
},

maskEvt: function(ev) {
    var mask = null;
	var myRule = null;
    for(var i=0; i<yav.rulesEvt.length; i++) {
		var aRule = yav.splitRule(yav.rulesEvt[i]);
		var elm = yav.getField(document.forms[yav.formEvt], aRule.el);
		if (elm && aRule.ruleName=='mask' && elm.name==this.name) {
		    for(var j=0; j<yav.mask.length; j++) {
				if ( yav.mask[j].name==aRule.comparisonValue ) {
					mask = yav.mask[j];
					break;
				}
		    }
			myRule = aRule;
			break;
		}
    }
    var key  = (typeof(ev.which))!='undefined' ? ev.which : window.event.keyCode;
    var ch      = String.fromCharCode(key);
    var str     = this.value + ch;
    var pos     = str.length;
	if (key==8 || key==0) { 
		return true;
	}
	var keyAllowed = false;
	if (mask==null) {
		if ( yav.isKeyAllowed(key, myRule.comparisonValue) ) {
			keyAllowed = true;
		} else {
			if ( typeof(ev.which)=='undefined' ) {
		        window.event.keyCode=0;
		    } else {
		        ev.preventDefault();
		        ev.stopPropagation();
		        ev.returnValue=false;
		    }
		}
		return keyAllowed;
	} else if ( mask.format==null ) {
		reg = new RegExp(mask.regex);
        if ( reg.test(ch) ) {
			keyAllowed = true;
		} else {
			if ( typeof(ev.which)=='undefined' ) {
		        window.event.keyCode=0;
		    } else {
		        ev.preventDefault();
		        ev.stopPropagation();
		        ev.returnValue=false;
		    }
		}
		return keyAllowed;
	} else if ( yav.isKeyAllowed(key, mask.charsAllowed) && pos <= mask.format.length ) {
        if ( mask.format.charAt(pos - 1) != ' ' ) {
            str = this.value + mask.format.charAt(pos - 1) + ch;
        }
		this.value = str;
		keyAllowed = true;
    }
	if ( typeof(ev.which)=='undefined' ) {
        window.event.keyCode=0;
    } else {
        ev.preventDefault();
        ev.stopPropagation();
        ev.returnValue=false;
    }
	return keyAllowed;
},

displayAlert: function (messages, alertType) {
    var retval =null;
    yav.clearAllInlineSpans();
    if (alertType=='classic') {
        retval = yav.displayClassic(messages);
    } else if (alertType=='innerHtml') {
        retval = yav.displayInnerHtml(messages);
    }else if (alertType=='inline') {
        retval = yav.displayInline(messages);
    }else if (alertType=='jsVar') {
        retval = yav.displayJsVar(messages);
    } else {
        yav.debug('DEBUG: alert type ' + alertType + ' not supported');
    }
    return retval;
},

displayClassic: function (messages) {
    var str = '';
    if ( messages!=null && messages.length>0 ) {
    	if (yav.strTrim(yav_config.HEADER_MSG).length > 0) {
            str += yav_config.HEADER_MSG + '\n\n';
        }
        for (var i=0; i<messages.length; i++) {
            str += ' ' + yav.deleteInline(messages[i]) + '\n';
        }
    	if (yav.strTrim(yav_config.FOOTER_MSG).length > 0) {
            str += '\n' + yav_config.FOOTER_MSG;
        }
        alert(str);
        return false;
    } else {
    	return true;
    }
},

displayInnerHtml: function (messages) {
    if ( messages!=null && messages.length>0 ) {
        var str = '';
    	if (yav.strTrim(yav_config.HEADER_MSG).length > 0) {
            str += yav_config.HEADER_MSG;
        }
        str += '<ul>';
        for (var i=0; i<messages.length; i++) {
            str += '<li>'+yav.deleteInline(messages[i])+'</li>';
        }
        str += '</ul>';
    	if (yav.strTrim(yav_config.FOOTER_MSG).length > 0) {
            str += yav_config.FOOTER_MSG;
        }
        yav.get(yav_config.errorsdiv).innerHTML = str;
        yav.get(yav_config.errorsdiv).className = yav_config.innererror;
        yav.get(yav_config.errorsdiv).style.display = 'block';
        return false;
    } else {
        yav.get(yav_config.errorsdiv).innerHTML = '';
        yav.get(yav_config.errorsdiv).className = '';
        yav.get(yav_config.errorsdiv).style.display = 'none';
        return true;
    }
},

displayInline: function (messages) {
    if ( messages!=null && messages.length>0 ) {
        var genericErrors = new Array();
        var genericErrIndex = 0;
        for (var i=0; i<messages.length; i++) {
            var elName = messages[i].substring(messages[i].indexOf('__inline__')+10);
            if ( yav.get(yav_config.errorsdiv+'_'+elName) ) {
                yav.get(yav_config.errorsdiv+'_'+elName).innerHTML = yav.deleteInline(messages[i]);
                yav.get(yav_config.errorsdiv+'_'+elName).className = yav_config.innererror;
                yav.get(yav_config.errorsdiv+'_'+elName).style.display = '';
            } else {
                genericErrors[genericErrIndex] = messages[i];
                genericErrIndex++;
            }
        }
        if (genericErrIndex>0) {
            yav.displayInnerHtml(genericErrors);
        }
        return false;
    } else {
        return true;
    }
},

clearAllInlineSpans: function () {
    var allDivs = document.getElementsByTagName("span");
    for (var j=0; j<allDivs.length; j++) {
        var idName = allDivs[j].id;
        if ( idName.indexOf(yav_config.errorsdiv+'_')==0 ) {
			if (yav.filterByName!=null) {
				if ( idName==yav_config.errorsdiv+'_'+yav.filterByName ) {
					yav.get(idName).innerHTML = '';
		            yav.get(idName).className = '';
		            yav.get(idName).style.display = 'none';
				}
			} else {
	            yav.get(idName).innerHTML = '';
	            yav.get(idName).className = '';
	            yav.get(idName).style.display = 'none';
			}
        }
    }
},

clearInlineSpans: function () {
    var allDivs = document.getElementsByTagName("span");
    for (var j=0; j<allDivs.length; j++) {
        var idName = allDivs[j].id;
        if ( idName.indexOf(yav_config.errorsdiv+'_')==0 ) {
			for (var k=0; k<arguments.length; k++) {
				if ( idName==yav_config.errorsdiv+'_'+arguments[k] ) {
					yav.get(idName).innerHTML = '';
		            yav.get(idName).className = '';
		            yav.get(idName).style.display = 'none';
				}
			}
        }
    }
},

displayJsVar: function (messages) {
    yav.get(yav_config.errorsdiv).className = '';
    yav.get(yav_config.errorsdiv).style.display = 'none';
    if ( messages!=null && messages.length>0 ) {
        for (var i=0; i<messages.length; i++) {
            messages[i] = yav.deleteInline(messages[i]);
        }
        var str = '';
        str += '<script>var jsErrors;</script>';
        yav.get(yav_config.errorsdiv).innerHTML = str;
        jsErrors = messages;
        return false;
    } else {
        yav.get(yav_config.errorsdiv).innerHTML = '<script>var jsErrors;</script>';
        return true;
    }
},

rule: function (el, ruleName, comparisonValue, alertMsg, ruleType) {
    var checkArguments = arguments.length>=4 && arguments[0]!=null && arguments[1]!=null;
    if ( !checkArguments ) {
        return false;
    }
    tmp = el.split(':');
    nameDisplayed = '';
    if (tmp.length == 2) {
        nameDisplayed = tmp[1];
        el = tmp[0];
    }
    this.el = el;
    this.nameDisplayed = nameDisplayed;
    this.ruleName = ruleName;
    this.comparisonValue = comparisonValue;
    this.ruleType = ruleType;
	if (alertMsg==yav.undef || alertMsg==null) {
        this.alertMsg = yav.getDefaultMessage(el, nameDisplayed, ruleName, comparisonValue)+'__inline__'+this.el;
    } else {
        this.alertMsg = alertMsg+'__inline__'+this.el;
    }
},

checkRule: function (f, myRule) {
    retVal = null;
    if (myRule != null) {
        if (myRule.ruleName=='custom') {
			var customFunction = null;
			if (myRule.comparisonValue!=null) {
				customFunction = ' retVal = ' + myRule.comparisonValue;
			} else { // deprecated, maintained for back compatibility
	            customFunction = ' retVal = ' + myRule.el;
			}
			retVal = eval(customFunction);
			if (myRule.comparisonValue!=null && retVal!=this.undef && retVal!=null) {
				retVal += '__inline__'+myRule.el;
			}
			if (retVal!=null && myRule.comparisonValue!=null) {
				yav.highlight(yav.getField(yav.f, myRule.el), yav_config.inputclasserror);
			}
        } else if (myRule.ruleName=='and') {
            var op_1 = myRule.el;
            var op_next = myRule.comparisonValue;
            if ( yav.checkRule(f, yav.internalRules[op_1])!=null ) {
                retVal = myRule.alertMsg;
                if (myRule.ruleType=='pre-condition' || myRule.ruleType=='andor-operator') {
                    //yav.highlight(yav.getField(f, yav.internalRules[op_1].el), yav_config.inputclasserror);
                }
            } else {
                var op_k = op_next.split('-');
                for(var k=0; k<op_k.length; k++) {
                    if ( yav.checkRule(f, yav.internalRules[op_k[k]])!=null ) {
                        retVal = myRule.alertMsg;
                        if (myRule.ruleType=='pre-condition' || myRule.ruleType=='andor-operator') {
                            //yav.highlight(yav.getField(f, yav.internalRules[op_k[k]].el), yav_config.inputclasserror);
                        }
                        break;
                    }
                }
            }
        } else if (myRule.ruleName=='or') {
            var op_1 = myRule.el;
            var op_next = myRule.comparisonValue;
            var success = false;
            if ( yav.checkRule(f, yav.internalRules[op_1])==null ) {
                success = true;
            } else {
                if (myRule.ruleType=='pre-condition' || myRule.ruleType=='andor-operator') {
                    //yav.highlight(yav.getField(f, yav.internalRules[op_1].el), yav_config.inputclasserror);
                }
                var op_k = op_next.split('-');
                for(var k=0; k<op_k.length; k++) {
                    if ( yav.checkRule(f, yav.internalRules[op_k[k]])==null ) {
                        success = true;
                        break;
                    } else {
                        if (myRule.ruleType=='pre-condition' || myRule.ruleType=='andor-operator') {
                            //yav.highlight(yav.getField(f, yav.internalRules[op_k[k]].el), yav_config.inputclasserror);
                        }
                    }
                }
            }
            if (success) {
                yav.highlight(yav.getField(f, yav.internalRules[op_1].el), yav_config.inputclassnormal);
                var op_k = op_next.split('-');
                for(var k=0; k<op_k.length; k++) {
                    yav.highlight(yav.getField(f, yav.internalRules[op_k[k]].el), yav_config.inputclassnormal);
                }
            } else {
                retVal = myRule.alertMsg;
            }
        } else {
            el = yav.getField(f, myRule.el);
            if (el == null) {
                yav.debug('DEBUG: could not find element ' + myRule.el);
                return null;
            }
            var err = null;
            if(el.type) {
                if(el.type=='hidden'||el.type=='text'||el.type=='password'||el.type=='textarea') {
					err = yav.checkText(el, myRule);
                } else if(el.type=='checkbox') {
                    err = yav.checkCheckbox(el, myRule);
                } else if(el.type=='select-one') {
                    err = yav.checkSelOne(el, myRule);
                } else if(el.type=='select-multiple') {
                    err = yav.checkSelMul(el, myRule);
                } else if(el.type=='radio') {
                    err = yav.checkRadio(el, myRule);
                } else {
                    yav.debug('DEBUG: type '+ el.type +' not supported');
                }
            } else {
                err = yav.checkRadio(el, myRule);
            }
            retVal = err;
        }
    }
    return retVal;
},

checkRadio: function (el, myRule) {
    var err = null;
    if (myRule.ruleName=='required') {
        var radios = el;
	    var found=false;
	    if (isNaN(radios.length) && radios.checked) {
	    	found=true;
	    } else {
		    for(var j=0; j < radios.length; j++) {
		        if(radios[j].checked) {
		            found=true;
		            break;
		        }
		    }
		}
        if( !found ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='equal') {
        var radios = el;
	    var found=false;
	    if (isNaN(radios.length) && radios.checked) {
	    	if (radios.value==myRule.comparisonValue) {
	    	    found=true;
	    	}
	    } else {
		    for(var j=0; j < radios.length; j++) {
		        if(radios[j].checked) {
        	    	if (radios[j].value==myRule.comparisonValue) {
        	    	    found=true;
                        break;
        	    	}
		        }
		    }
		}
        if( !found ) {
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='notequal') {
        var radios = el;
	    var found=false;
	    if (isNaN(radios.length) && radios.checked) {
	    	if (radios.value!=myRule.comparisonValue) {
	    	    found=true;
	    	}
	    } else {
		    for(var j=0; j < radios.length; j++) {
		        if(radios[j].checked) {
        	    	if (radios[j].value!=myRule.comparisonValue) {
        	    	    found=true;
                        break;
        	    	}
		        }
		    }
		}
        if( !found ) {
            err = myRule.alertMsg;
        }
    } else {
        yav.debug('DEBUG: rule ' + myRule.ruleName + ' not supported for radio');
    }
    return err;
},

checkText: function (el, myRule) {
    err = null;
    if (yav_config.trimenabled) {
    	el.value = yav.strTrim(el.value);
    }
    if (myRule.ruleName=='required') {
        if ( el.value==null || el.value=='' ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='equal') {
        err = yav.checkEqual(el, myRule);
    } else if (myRule.ruleName=='notequal') {
        err = yav.checkNotEqual(el, myRule);
    } else if (myRule.ruleName=='numeric') {
        reg = new RegExp("^[0-9]*$");
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='alphabetic') {
        reg = new RegExp(yav_config.alphabetic_regex);
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='alphanumeric') {
        reg = new RegExp(yav_config.alphanumeric_regex);
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='alnumhyphen') {
        reg = new RegExp(yav_config.alnumhyphen_regex);
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='alnumhyphenat') {
        reg = new RegExp(yav_config.alnumhyphenat_regex);
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='alphaspace') {
        reg = new RegExp(yav_config.alphaspace_regex);
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='email') {
        reg = new RegExp(yav_config.email_regex);
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='maxlength') {
        if ( isNaN(myRule.comparisonValue) ) {
            yav.debug('DEBUG: comparisonValue for rule ' + myRule.ruleName + ' not a number');
        }else if ( el.value.length > myRule.comparisonValue ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='minlength') {
        if ( isNaN(myRule.comparisonValue) ) {
            yav.debug('DEBUG: comparisonValue for rule ' + myRule.ruleName + ' not a number');
        } else if ( el.value.length < myRule.comparisonValue ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='numrange') {
        reg = new RegExp("^[-+]{0,1}[0-9]*[.]{0,1}[0-9]*$");
        if ( !reg.test(yav.unformatNumber(el.value)) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        } else {
            regRange = new RegExp("^[0-9]+-[0-9]+$"); 
            if ( !regRange.test(myRule.comparisonValue) ) {
                yav.debug('DEBUG: comparisonValue for rule ' + myRule.ruleName + ' not in format number1-number2');
            } else {
                rangeVal = myRule.comparisonValue.split('-');
                if (eval(yav.unformatNumber(el.value))<eval(rangeVal[0]) || eval(yav.unformatNumber(el.value))>eval(rangeVal[1])) {
                    yav.highlight(el, yav_config.inputclasserror); 
                    err = myRule.alertMsg;
                }
            }
        }
    } else if (myRule.ruleName=='regexp') {
        reg = new RegExp(myRule.comparisonValue);
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else if (myRule.ruleName=='integer') {
        err = yav.checkInteger(el, myRule);
    } else if (myRule.ruleName=='double') {
        err = yav.checkDouble(el, myRule);
    } else if (myRule.ruleName=='date') {
        err = yav.checkDate(el, myRule);
    } else if (myRule.ruleName=='date_lt') {
        err = yav.checkDateLessThan(el, myRule, false);
    } else if (myRule.ruleName=='date_le') {
        err = yav.checkDateLessThan(el, myRule, true);
    } else if (myRule.ruleName=='keypress') {
        // do nothing
    } else if (myRule.ruleName=='empty') {
        if ( el.value!=null && el.value!='' ) {
            yav.highlight(el, yav_config.inputclasserror);
            err = myRule.alertMsg;
        }
    } else {
        yav.debug('DEBUG: rule ' + myRule.ruleName + ' not supported for ' + el.type);
    }
    return err;
},

checkInteger: function (el, myRule) {
    reg = new RegExp("^[-+]{0,1}[0-9]*$");
    if ( !reg.test(el.value) ) {
        yav.highlight(el, yav_config.inputclasserror);
        return myRule.alertMsg;
    }
},

checkDouble: function (el, myRule) {
    var sep = yav_config.DECIMAL_SEP;
    reg = new RegExp("^[-+]{0,1}[0-9]*[" + sep + "]{0,1}[0-9]*$");
    if ( !reg.test(el.value) ) {
        yav.highlight(el, yav_config.inputclasserror);
        return myRule.alertMsg;
    }
},

checkDate: function (el, myRule) {
    error = null;
    if (el.value!='') {
        var dateFormat = yav_config.DATE_FORMAT;
        ddReg = new RegExp("dd");
        MMReg = new RegExp("MM");
        yyyyReg = new RegExp("yyyy");
        if ( !ddReg.test(dateFormat) || !MMReg.test(dateFormat) || !yyyyReg.test(dateFormat)  ) {
            yav.debug('DEBUG: locale format ' + dateFormat + ' not supported');
        } else {
            ddStart = dateFormat.indexOf('dd');
            MMStart = dateFormat.indexOf('MM');
            yyyyStart = dateFormat.indexOf('yyyy');
        }
        strReg = dateFormat.replace('dd','[0-9]{2}').replace('MM','[0-9]{2}').replace('yyyy','[0-9]{4}');
        reg = new RegExp("^" + strReg + "$");
        if ( !reg.test(el.value) ) {
            yav.highlight(el, yav_config.inputclasserror);
            error = myRule.alertMsg;
        } else {
            dd   = el.value.substring(ddStart, ddStart+2);
            MM   = el.value.substring(MMStart, MMStart+2);
            yyyy = el.value.substring(yyyyStart, yyyyStart+4);
            if ( !yav.checkddMMyyyy(dd, MM, yyyy) ) {
                yav.highlight(el, yav_config.inputclasserror);
                error = myRule.alertMsg;
            }
        }
    }
    return error;
},

checkDateLessThan: function (el, myRule, isEqualAllowed) {
    error = null;
    var isDate = yav.checkDate(el, myRule)==null ? true : false;
    if ( isDate && el.value!='' ) {
        var dateFormat = yav_config.DATE_FORMAT;
        ddStart = dateFormat.indexOf('dd');
        MMStart = dateFormat.indexOf('MM');
        yyyyStart = dateFormat.indexOf('yyyy');
        dd   = el.value.substring(ddStart, ddStart+2);
        MM   = el.value.substring(MMStart, MMStart+2);
        yyyy = el.value.substring(yyyyStart, yyyyStart+4);
        myDate = "" + yyyy + MM + dd;
        strReg = dateFormat.replace('dd','[0-9]{2}').replace('MM','[0-9]{2}').replace('yyyy','[0-9]{4}');
        reg = new RegExp("^" + strReg + "$");
        var isMeta = myRule.comparisonValue.indexOf('$')==0 
            ? true
            : false;
        var comparisonDate = '';
        if (isMeta) {
            toSplit = myRule.comparisonValue.substr(1);
            tmp = toSplit.split(':');
            if (tmp.length == 2) {
                comparisonDate = yav.getField(yav.f, tmp[0]).value;
            } else {
                comparisonDate = yav.getField(yav.f, myRule.comparisonValue.substr(1)).value;
            }
        } else {
            comparisonDate = myRule.comparisonValue;
        }
        if ( !reg.test(comparisonDate) ) {
            yav.highlight(el, yav_config.inputclasserror);
            error = myRule.alertMsg;
        } else {
            cdd   = comparisonDate.substring(ddStart, ddStart+2);
            cMM   = comparisonDate.substring(MMStart, MMStart+2);
            cyyyy = comparisonDate.substring(yyyyStart, yyyyStart+4);
            cDate = "" + cyyyy + cMM + cdd;
            if (isEqualAllowed) {
                if ( !yav.checkddMMyyyy(cdd, cMM, cyyyy) || myDate>cDate ) {
                    yav.highlight(el, yav_config.inputclasserror);
                    error = myRule.alertMsg;
                }
            } else {
                if ( !yav.checkddMMyyyy(cdd, cMM, cyyyy) || myDate>=cDate ) {
                    yav.highlight(el, yav_config.inputclasserror);
                    error = myRule.alertMsg;
                }
            }
        }
    } else {
        if ( el.value!='' ) {
            yav.highlight(el, yav_config.inputclasserror);
            error = myRule.alertMsg;
        }
    }
    return error;
},

checkEqual: function (el, myRule) {
    error = null;
    var isMeta = myRule.comparisonValue.indexOf('$')==0 
        ? true
        : false;
    var comparisonVal = '';
    if (isMeta) {
        toSplit = myRule.comparisonValue.substr(1);
        tmp = toSplit.split(':');
        if (tmp.length == 2) {
            comparisonVal = yav.getField(yav.f, tmp[0]).value;
        } else {
            comparisonVal = yav.getField(yav.f, myRule.comparisonValue.substr(1)).value;
        }
    } else {
        comparisonVal = myRule.comparisonValue;
    }
    if ( el.value!=comparisonVal ) {
        yav.highlight(el, yav_config.inputclasserror);
        error = myRule.alertMsg;
    }
    return error;
},

checkNotEqual: function (el, myRule) {
    error = null;
    var isMeta = myRule.comparisonValue.indexOf('$')==0 
        ? true
        : false;
    var comparisonVal = '';
    if (isMeta) {
        toSplit = myRule.comparisonValue.substr(1);
        tmp = toSplit.split(':');
        if (tmp.length == 2) {
            comparisonVal = yav.getField(yav.f, tmp[0]).value;
        } else {
            comparisonVal = yav.getField(yav.f, myRule.comparisonValue.substr(1)).value;
        }
    } else {
        comparisonVal = myRule.comparisonValue;
    }
    if ( el.value==comparisonVal ) {
        yav.highlight(el, yav_config.inputclasserror);
        error = myRule.alertMsg;
    }
    return error;
},

checkddMMyyyy: function (dd, MM, yyyy) {
    retVal = true;
    if (    (dd<1) || (dd>31) || (MM<1) || (MM>12) ||
            (dd==31 && (MM==2 || MM==4 || MM==6 || MM==9 || MM==11) ) ||
            (dd >29 && MM==2) ||
            (dd==29 && (MM==2) && ((yyyy%4 > 0) || (yyyy%4==0 && yyyy%100==0 && yyyy%400>0 )) )) {
       retVal = false;
    }
    return retVal;
},

checkCheckbox: function (el, myRule) {
    if (myRule.ruleName=='required') {
        if ( !el.checked ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else if (myRule.ruleName=='equal') {
        if ( !el.checked || el.value!=myRule.comparisonValue ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else if (myRule.ruleName=='notequal') {
        if ( el.checked && el.value==myRule.comparisonValue ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else {
        yav.debug('DEBUG: rule ' + myRule.ruleName + ' not supported for ' + el.type);
    }
},

checkSelOne: function (el, myRule) {
    if (myRule.ruleName=='required') {
        var found = false;
        var inx = el.selectedIndex;
        if(inx>=0 && el.options[inx].value) {
            found = true;
        }
        if ( !found ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else if (myRule.ruleName=='equal') {
        var found = false;
        var inx = el.selectedIndex;
        if(inx>=0 && el.options[inx].value==myRule.comparisonValue) {
            found = true;
        }
        if ( !found ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else if (myRule.ruleName=='notequal') {
        var found = false;
        var inx = el.selectedIndex;
        if(inx>=0 && el.options[inx].value!=myRule.comparisonValue) {
            found = true;
        }
        if ( !found ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else {
        yav.debug('DEBUG: rule ' + myRule.ruleName + ' not supported for ' + el.type);
    }
},

checkSelMul: function (el, myRule) {
    if (myRule.ruleName=='required') {
        var found = false;
        opts = el.options;
        for(var i=0; i<opts.length; i++) {
            if(opts[i].selected && opts[i].value) {
                found = true;
                break;
            }
        }
        if ( !found ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else if (myRule.ruleName=='equal') {
        var found = false;
        opts = el.options;
        for(var i=0; i<opts.length; i++) {
            if(opts[i].selected && opts[i].value==myRule.comparisonValue) {
                found = true;
                break;
            }
        }
        if ( !found ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else if (myRule.ruleName=='notequal') {
        var found = false;
        opts = el.options;
        for(var i=0; i<opts.length; i++) {
            if(opts[i].selected && opts[i].value!=myRule.comparisonValue) {
                found = true;
                break;
            }
        }
        if ( !found ) {
            yav.highlight(el, yav_config.inputclasserror);
            return myRule.alertMsg;
        }
    } else {
        yav.debug('DEBUG: rule ' + myRule.ruleName + ' not supported for ' + el.type);
    }
},

debug: function (msg) {
   if (yav_config.debugmode) {
        alert(msg);
   }
},

strTrim: function (str) {
    return str.replace(/^\s+/,'').replace(/\s+$/,'');
},

makeRules: function (strRules) {
    var rules=new Array();
    if (strRules.length) {
        for(var i=0; i<strRules.length; i++) {
            rules[i] = yav.splitRule(strRules[i]);
        }
    } else {
        rules[0] = yav.splitRule(strRules);
    }
    return rules;
},

splitRule: function (strRule) {
    var retval = null;
    if (strRule!=yav.undef) {
        params = strRule.split(yav_config.RULE_SEP);
        switch (params.length) {
            case 2:
                retval = new yav.rule(params[0], params[1], null, null, null);
                break;
            case 3:
                if (yav.threeParamRule(params[1])) {
                    retval = new yav.rule(params[0], params[1], params[2], null, null);
                } else if (params[2]=='pre-condition' || params[2]=='post-condition' || params[2]=='andor-operator') {
                    retval = new yav.rule(params[0], params[1], null, 'foo', params[2]);
                } else {
                    retval = new yav.rule(params[0], params[1], null, params[2], null);
                }
                break;
            case 4:
                if (yav.threeParamRule(params[1]) && (params[3]=='pre-condition' || params[3]=='post-condition' || params[3]=='andor-operator')) {
                    retval = new yav.rule(params[0], params[1], params[2], 'foo', params[3]);
                } else {
                    retval = new yav.rule(params[0], params[1], params[2], params[3], null);
                }
                break;
            default:
                yav.debug('DEBUG: wrong definition of rule');
        }
    }
    return retval;
},

threeParamRule: function (ruleName) {
    return (ruleName=='equal' || ruleName=='notequal' || ruleName=='minlength' || ruleName=='maxlength' || ruleName=='date_lt' || ruleName=='date_le' || ruleName=='implies' || ruleName=='regexp' || ruleName=='numrange' || ruleName=='keypress' || ruleName=='and' || ruleName=='or' || ruleName=='custom' || ruleName=='mask')
        ? true
        : false;
},

highlight: function (el, clazz) {
	if (yav.rulesEvt.length>0 && clazz==yav_config.inputclasserror) {
		return;
	}
    if (!yav.isFocusSet && clazz==yav_config.inputclasserror) {
        if (  (!el.type) && (el.length>0) && (el.item(0).type=='radio') ) {
            el.item(0).focus();
        } else {
            el.focus();   
        }
        yav.isFocusSet = true;
    }
    if (el!=yav.undef && yav_config.inputhighlight) {
        if ( yav_config.multipleclassname ) {
            yav.highlightMultipleClassName(el, clazz);
        } else {
            el.className = clazz;
        }        
    }
},

highlightMultipleClassName: function (el, clazz) {
    re = new RegExp("(^|\\s)("+yav_config.inputclassnormal+"|"+yav_config.inputclasserror+")($|\\s)");
    el.className = yav.strTrim (
    ( (typeof el.className != "undefined")
        ? el.className.replace(re, "")
        : ""
    ) + " " + clazz );
},

getDefaultMessage: function (el, nameDisplayed, ruleName, comparisonValue) {
    if (nameDisplayed.length == 0) {
        nameDisplayed = el;
    }
    var msg = yav_config.DEFAULT_MSG;
    if (ruleName=='required') {
        msg = yav_config.REQUIRED_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='minlength') {
        msg = yav_config.MINLENGTH_MSG.replace('{1}', nameDisplayed).replace('{2}', comparisonValue);
    } else if (ruleName=='maxlength') {
        msg = yav_config.MAXLENGTH_MSG.replace('{1}', nameDisplayed).replace('{2}', comparisonValue);
    } else if (ruleName=='numrange') {
        msg = yav_config.NUMRANGE_MSG.replace('{1}', nameDisplayed).replace('{2}', comparisonValue);
    } else if (ruleName=='date') {
        msg = yav_config.DATE_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='numeric') {
        msg = yav_config.NUMERIC_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='integer') {
        msg = yav_config.INTEGER_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='double') {
        msg = yav_config.DOUBLE_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='equal') {
        msg = yav_config.EQUAL_MSG.replace('{1}', nameDisplayed).replace('{2}', yav.getComparisonDisplayed(comparisonValue));
    } else if (ruleName=='notequal') {
        msg = yav_config.NOTEQUAL_MSG.replace('{1}', nameDisplayed).replace('{2}', yav.getComparisonDisplayed(comparisonValue));
    } else if (ruleName=='alphabetic') {
        msg = yav_config.ALPHABETIC_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='alphanumeric') {
        msg = yav_config.ALPHANUMERIC_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='alnumhyphen') {
        msg = yav_config.ALNUMHYPHEN_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='alnumhyphenat') {
        msg = yav_config.ALNUMHYPHENAT_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='alphaspace') {
        msg = yav_config.ALPHASPACE_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='email') {
        msg = yav_config.EMAIL_MSG.replace('{1}', nameDisplayed);
    } else if (ruleName=='regexp') {
        msg = yav_config.REGEXP_MSG.replace('{1}', nameDisplayed).replace('{2}', comparisonValue);
    } else if (ruleName=='date_lt') {
        msg = yav_config.DATE_LT_MSG.replace('{1}', nameDisplayed).replace('{2}', yav.getComparisonDisplayed(comparisonValue));
    } else if (ruleName=='date_le') {
        msg = yav_config.DATE_LE_MSG.replace('{1}', nameDisplayed).replace('{2}', yav.getComparisonDisplayed(comparisonValue));
    } else if (ruleName=='empty') {
        msg = yav_config.EMPTY_MSG.replace('{1}', nameDisplayed);
    }
    return msg;
},

getComparisonDisplayed: function (comparisonValue) {
    comparisonDisplayed = comparisonValue;
    if (comparisonValue.substring(0, 1)=='$') {
        comparisonValue = comparisonValue.substring(1, comparisonValue.length);
        tmp = comparisonValue.split(':');
        if (tmp.length == 2) {
            comparisonDisplayed = tmp[1];
        } else {
            comparisonDisplayed = comparisonValue;
        }
    }
    return comparisonDisplayed;
},

isKeyAllowed: function (keyCode, charsAllowed) {
    retval = false;
    var aCharCode;
    if (keyCode==8 || keyCode==0) {
        retval = true;
    } else {
        for(var i=0; i<charsAllowed.length; i++) {
            aCharCode = charsAllowed.charCodeAt(i);
            if (aCharCode==keyCode) {
                retval = true;
                break;
            }
        }
    }
    return retval;
},

getField: function (formObj, fieldName){
	var retval = null;
	if (formObj.elements[fieldName]){
		retval = formObj.elements[fieldName];
	}else if (yav.get(fieldName)){
		retval = yav.get(fieldName);
	}
	return retval;
},

get: function(id) {
	return document.getElementById(id);
},

unformatNumber: function (viewValue){
    var retval = viewValue.replace(yav_config.THOUSAND_SEP, ""); 
    retval = retval.replace(yav_config.DECIMAL_SEP, ".");
    return retval;
},

deleteInline: function (msg) {
    if (msg.indexOf('__inline__')==-1) {
        return msg;
    } else {
        return msg.substring(0, msg.indexOf('__inline__'));
    }
},

addEvent: function(elm, evType, fn) {
    if (elm.addEventListener) {
        elm.addEventListener(evType, fn, false);
        return true;
    }
    else if (elm.attachEvent) {
		// The main drawback of the Microsoft event registration model is that 
		// attachEvent() creates a reference to the function and does not copy it.
		// so these lines (commented out) don't work in some circumstances.
        //var r = elm.attachEvent('on'+evType, fn);
        //return r;
		elm['on' + evType] = fn;
    }
    else {
        elm['on' + evType] = fn;
	}
},

call: function(elmName, evType, fn) {
	var elm = document.forms[yav.formEvt].elements[elmName];
	addEvent(elm, evType, fn);
}


}//end namespace 'yav'

Function.prototype.bindAsEventListener = function(object) {
  var __method = this;
  return function(event) {
    return __method.call(object, event || window.event);
  }
}
