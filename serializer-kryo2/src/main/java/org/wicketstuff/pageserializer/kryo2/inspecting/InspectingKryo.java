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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Output;

import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;

/**
 * main entry for serialization, deserialization
 * 
 * @author mosmann
 * 
 */
public class InspectingKryo extends KryoReflectionFactorySupport
{

	private final static Logger LOG = LoggerFactory.getLogger(InspectingKryo.class);
	private final InspectingKryoSerializer parent;

	protected InspectingKryo(InspectingKryoSerializer parent)
	{
		this.parent = parent;
	}

	@Override
	public Registration writeClass(Output output, Class type)
	{
		Registration ret;
		before(output, type);
		ret = super.writeClass(output, type);
		after(output, type);
		return ret;
	}

	@Override
	public void writeClassAndObject(Output output, Object object)
	{
		before(output, object);
		super.writeClassAndObject(output, object);
		after(output, object);
	}

	@Override
	public void writeObject(Output output, Object object, Serializer serializer)
	{
		before(output, object);
		super.writeObject(output, object, serializer);
		after(output, object);
	}

	@Override
	public void writeObject(Output output, Object object)
	{
		before(output, object);
		super.writeObject(output, object);
		after(output, object);
	}

	@Override
	public void writeObjectOrNull(Output output, Object object, Class clazz)
	{
		before(output, object);
		super.writeObjectOrNull(output, object, clazz);
		after(output, object);
	}

	@Override
	public void writeObjectOrNull(Output output, Object object, Serializer serializer)
	{
		before(output, object);
		super.writeObjectOrNull(output, object, serializer);
		after(output, object);
	}

	private void before(Output output, Object object)
	{
		parent.serializingListener().before(output.position(), object);
	}

	private void after(Output output, Object object)
	{
		parent.serializingListener().after(output.position(), object);
	}

}
