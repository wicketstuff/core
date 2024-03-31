/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/**
 * Adds a value as get argument to the url
 * 
 * 
 * Example:
 * 
 * var url = '${wicket:ajaxCallbackUrl()}';
 * Wicket.Ajax.applyGetParameter(url,{param:value});
 */
Wicket.Ajax.applyGetParameters = function(url, options) {
	return url + "&" + $.param(options);
}

/**
 * Wraps the url into a Wicket.Ajax.get-Call
 * 
 * Example:
 * 
 * var url = '${wicket:ajaxCallbackUrl()}'; Wicket.Ajax.wrapget(url);
 * 
 */
Wicket.Ajax.wrapget = function(url) {
	Wicket.Ajax.get({
		'u' : url 
	});
}

/**
 * Wraps the url into a Wicket.Ajax.post-call
 * 
 * Example:
 * 
 * var url = '${wicket:ajaxCallbackUrl()}'; Wicket.Ajax.wrappost(url);
 * 
 */
Wicket.Ajax.wrappost = function(url, options) {
	Wicket.Ajax.post({
		'u' :  url ,
		ep : options
	});
}
