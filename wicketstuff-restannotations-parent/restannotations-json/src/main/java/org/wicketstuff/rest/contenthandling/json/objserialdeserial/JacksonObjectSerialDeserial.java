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
package org.wicketstuff.rest.contenthandling.json.objserialdeserial;

import org.apache.wicket.WicketRuntimeException;
import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Object serializer/deserailizer that works with JSON format and based on Jackson library.
 * 
 * @author andrea del bene
 *
 */
public class JacksonObjectSerialDeserial implements
	IObjectSerialDeserial<String>
{

    private final ObjectMapper objMapper;

    public JacksonObjectSerialDeserial(ObjectMapper objMapper)
    {
	this.objMapper = objMapper;
    }
    
    public JacksonObjectSerialDeserial()
    {
	this.objMapper = new ObjectMapper();
    }    

    @Override
    public String serializeObject(Object target, String mimeType)
    {
	try
	{
	    return objMapper.writeValueAsString(target);
	} catch (Exception e)
	{
	    throw new WicketRuntimeException("An error occurred during object serialization.", e);
	}
    }

    @Override
    public <E> E deserializeObject(String source, Class<E> targetClass,
	    String mimeType)
    {
	try
	{
	    return objMapper.readValue(source, targetClass);
	} catch (Exception e)
	{
	    throw new WicketRuntimeException("An error occurred during object deserialization.", e);
	} 
    }

    public ObjectMapper getObjMapper()
    {
        return objMapper;
    }

}
