/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
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
package org.wicketstuff.pageserializer.kryo2.inspecting.validation;

import java.io.Serializable;

import org.wicketstuff.pageserializer.common.listener.ISerializationListener;

import com.esotericsoftware.kryo.KryoException;

public class DefaultJavaSerializationValidator implements ISerializationListener
{

	@Override
	public void begin(Object object)
	{
		checkSerializable(object);
	}

	@Override
	public void before(int position, Object object)
	{
		checkSerializable(object);
	}

	@Override
	public void after(int position, Object object)
	{

	}

	@Override
	public void end(Object object, Exception ex)
	{
		
	}

	private void checkSerializable(Object object)
	{
		if (object==null) return;
		
		if (!(object instanceof Serializable)) {
			throw new NotSerializableException(""+object.getClass()+" is not Serializable");
		}
	}

	static class NotSerializableException extends KryoException {

		public NotSerializableException(String message)
		{
			super(message);
		}
		
	}
}
