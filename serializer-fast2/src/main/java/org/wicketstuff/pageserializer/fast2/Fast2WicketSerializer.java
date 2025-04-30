/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson jeremy@thomersonfamily.com
 * 	2012 Michael Mosmann michael@mosmann.de
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
package org.wicketstuff.pageserializer.fast2;

import org.apache.wicket.serialize.ISerializer;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectOutput;
import org.nustaq.serialization.FSTSerialisationListener;
import org.wicketstuff.pageserializer.common.listener.ISerializationListener;


/**
 * A Wicket serializer using the Fast 2 library.
 */
public class Fast2WicketSerializer implements ISerializer
{

	private final FSTConfiguration fastSerializationConfig;

	private ISerializationListener listener;

	/**
	 * Build a Fast2 serializer with a default sensible configuration.
	 */
	public Fast2WicketSerializer()
	{
		this(getDefaultFSTConfiguration());
	}

	/**
	 * Build a Fast2 serializer with a custom configuration.
	 */
	public Fast2WicketSerializer(FSTConfiguration config)
	{
		fastSerializationConfig = config;
	}

	public static final FSTConfiguration getDefaultFSTConfiguration()
	{
		FSTConfiguration config = FSTConfiguration.createDefaultConfiguration();
		config.setForceSerializable(false);
		return config;
	}

	@Override
	public byte[] serialize(Object object)
	{
		Exception exception = null;

		try
		{
			FSTObjectOutput out = fastSerializationConfig.getObjectOutput();

			if (listener != null)
			{
				out.setListener(new ListenerAdapter(listener));
				listener.begin(object);
			}
			out.writeObject(object);
			out.setListener(null);

			return out.getCopyOfWrittenBuffer();
		}
		catch (Exception e)
		{
			exception = e;
			throw new Fast2WicketSerialException(String.format(
					"Unable to serialize the object %1$s", object), e);
		}
		finally
		{
			if (listener != null)
			{
				listener.end(object, exception);
			}
		}
	}

	/**
	 * Define a listener to inspect the serialization process.
	 *
	 * @param listener
	 * @return the Serializer for chaining.
	 */
	public Fast2WicketSerializer setListener(ISerializationListener listener)
	{
		this.listener = listener;
		return this;
	}

	/**
	 * @return the listener used to inspect the serialization process.
	 */
	public ISerializationListener getListener()
	{
		return listener;
	}

	@Override
	public Object deserialize(byte[] data)
	{
		try
		{
			return fastSerializationConfig.getObjectInput(data).readObject();
		}
		catch (Exception e)
		{
			throw new Fast2WicketSerialException("Unable to deserialize the data", e);
		}
	}

	static class ListenerAdapter implements FSTSerialisationListener
	{
		private final ISerializationListener listener;

		public ListenerAdapter(ISerializationListener listener)
		{
			this.listener = listener;
		}

		@Override
		public void objectWillBeWritten(Object obj, int streamPosition)
		{
			listener.before(streamPosition, obj);
		}

		@Override
		public void objectHasBeenWritten(Object obj, int oldStreamPosition, int streamPosition)
		{
			listener.after(streamPosition, obj);
		}
	}
}
