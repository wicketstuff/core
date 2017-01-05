/**
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
package org.wicketstuff.rest.lambda.mounter;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class TestApplication extends WebApplication 
{
	static final Map<String, Object> map = new HashMap<>();
	
	static {
		map.put("integer", 123);
		map.put("string", "message");
	}
	
	@Override
	public Class<? extends Page> getHomePage() 
	{
		return Page.class;
	}
	
	@Override
	protected void init() 
	{
		super.init();
		
		LambdaRestMounter restMounter = new LambdaRestMounter(this);
		
		restMounter.get("/testget", (attributes) -> "hello!", Object::toString);
		restMounter.post("/testjson", (attributes) -> TestApplication.map, JSONObject::valueToString);

		restMounter.options("/testparam/${id}", (attributes) -> {
			PageParameters pageParameters = attributes.getPageParameters();
			return pageParameters.get("id");
		}
		, Object::toString);
		
		restMounter.delete("/deleteit", (attributes) -> 
			attributes.getWebResponse().write("deleted"));
	}
}
