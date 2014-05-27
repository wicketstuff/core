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

import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;

import com.google.gson.Gson;

/**
 * Object serializer/deserailizer that works with JSON format and based on Gson library.
 * 
 * @author andrea del bene
 *
 */
public class GsonObjectSerialDeserial implements IObjectSerialDeserial<String>
{
    private final Gson gson;
    
    public GsonObjectSerialDeserial(Gson gson)
    {
	this.gson = gson;
    }
    
    public GsonObjectSerialDeserial()
    {
	this.gson = new Gson();
    }

    @Override
    public String serializeObject(Object target, String mimeType)
    {
	return gson.toJson(target);
    }

    @Override
    public <E> E deserializeObject(String source, Class<E> targetClass,
	    String mimeType)
    {
	return gson.fromJson(source, targetClass);
    }

    public Gson getGson()
    {
        return gson;
    }

}
