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
package org.wicketstuff.rest.contenthandling.objserialdeserial;

import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;
import org.wicketstuff.rest.resource.RestResourceFullAnnotated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJsonDesSer implements IObjectSerialDeserial<String>
{
	final ObjectMapper mapper = new ObjectMapper();
	
	static public Object getObject()
	{
		return RestResourceFullAnnotated.createTestPerson();
	}

	static public String getJSON()
	{
		return "{\"email\":\"m.smith@gmail.com\",\"name\":\"Mary\",\"surname\":\"Smith\"}";
	}

	@Override
	public String serializeObject(Object targetObject, String mimeType)
	{	
		try
		{
			return mapper.writeValueAsString(targetObject);
		}
		catch (JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T deserializeObject(String source, Class<T> targetClass, String mimeType)
	{
		return (T)getObject();
	}
}
