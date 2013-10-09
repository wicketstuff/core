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
package org.wicketstuff.rest.contenthandling.serialdeserial;

import org.wicketstuff.rest.contenthandling.RestMimeTypes;
import org.wicketstuff.rest.resource.RestResourceFullAnnotated;

public class TestJsonDesSer extends TextualObjectSerialDeserial
{
	public TestJsonDesSer()
	{
		super("UTF-8", RestMimeTypes.APPLICATION_JSON);
	}

	static public Object getObject()
	{
		return RestResourceFullAnnotated.createTestPerson();
	}

	static public String getJSON()
	{
		return "{\"name\" : \"Mary\", \"surname\" : \"Smith\", \"email\" : \"m.smith@gmail.com\"}";
	}

	@Override
	public String serializeObject(Object targetObject, String mimeType)
	{
		return getJSON();
	}

	@Override
	public <T> T deserializeObject(String source, Class<T> targetClass, String mimeType)
	{
		return (T)getObject();
	}
}
