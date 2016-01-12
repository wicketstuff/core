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
package org.wicketstuff.pageserializer.kryo2.inspecting;

import org.apache.wicket.util.lang.Bytes;
import org.wicketstuff.pageserializer.common.listener.ISerializationListener;
import org.wicketstuff.pageserializer.kryo2.KryoSerializer;

import com.esotericsoftware.kryo.Kryo;

/**
 * serializer with serialization process hooks
 * 
 * @author mosmann
 */
public class InspectingKryoSerializer extends KryoSerializer
{

	private final ISerializationListener serializingListener;

	/**
	 * 
	 * @param size
	 *            buffer size, write will fail if buffer is to small
	 * @param serializingListener
	 *            serialization listener
	 */
	public InspectingKryoSerializer(Bytes size, ISerializationListener serializingListener)
	{
		super(size);
		this.serializingListener = serializingListener;
	}

	@Override
	protected Kryo createKryo()
	{
		return new InspectingKryo(this);
	}

	@Override
	public byte[] serialize(Object object)
	{
		RuntimeException exceptionIfAny = null;
		byte[] ret;
		try
		{
			serializingListener.begin(object);
			ret = super.serialize(object);
		}
		catch (RuntimeException ex)
		{
			exceptionIfAny = ex;
			throw ex;
		}
		finally
		{
			serializingListener.end(object, exceptionIfAny);
		}
		return ret;
	}

	protected final ISerializationListener serializingListener()
	{
		return serializingListener;
	}
}
