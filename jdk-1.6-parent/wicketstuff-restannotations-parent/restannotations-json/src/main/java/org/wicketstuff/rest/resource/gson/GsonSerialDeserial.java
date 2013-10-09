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
package org.wicketstuff.rest.resource.gson;

import org.wicketstuff.rest.contenthandling.RestMimeTypes;
import org.wicketstuff.rest.contenthandling.serialdeserial.TextualObjectSerialDeserial;

import com.google.gson.Gson;

/**
 * Textual object serializer/deserializer that works with JSON format and uses <a
 * href="http://code.google.com/p/google-gson/">Gson library</a>.
 * 
 * @author andrea del bene
 * 
 */
public class GsonSerialDeserial extends TextualObjectSerialDeserial
{
	/** The Gson instance used by the serial/deserial */
	private final Gson gson;
	/** Charset used by the serial/deserial */
	public static final String CHARSET_UTF_8 = "UTF-8";
	/** Mime type used by the serial/deserial */
	public static final String APPLICATION_JSON = RestMimeTypes.APPLICATION_JSON;

	/**
	 * Default constructor that creates an instance of Gson entity.
	 */
	public GsonSerialDeserial()
	{
		super(CHARSET_UTF_8, APPLICATION_JSON);
		this.gson = buildDefaultGson();
	}

	/**
	 * Default constructor that uses the provided instance of Gson.
	 * 
	 * @param gson
	 *            the instance to use.
	 */
	public GsonSerialDeserial(Gson gson)
	{
		super(CHARSET_UTF_8, APPLICATION_JSON);
		this.gson = gson;
	}

	/**
	 * Builds the default instance of Gson.
	 * 
	 * @return
	 */
	protected Gson buildDefaultGson()
	{
		return new Gson();
	}

	public Gson getGson()
	{
		return gson;
	}

	@Override
	public String serializeObject(Object targetObject, String mimeType)
	{
		return gson.toJson(targetObject);
	}

	@Override
	public <T> T deserializeObject(String source, Class<T> targetClass, String mimeType)
	{
		return gson.fromJson(source, targetClass);
	}
}