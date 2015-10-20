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
package org.wicketstuff.pageserializer.kryo2;

import java.lang.reflect.InvocationHandler;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.GregorianCalendar;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.ClassSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CurrencySerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringBufferSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringBuilderSerializer;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyMapSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptySetSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonListSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonMapSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonSetSerializer;
import de.javakaffee.kryoserializers.GregorianCalendarSerializer;
import de.javakaffee.kryoserializers.JdkProxySerializer;
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;
import de.javakaffee.kryoserializers.cglib.CGLibProxySerializer;

/**
 * An {@link ISerializer} based on <a href="http://code.google.com/p/kryo">kryo</a> and <a
 * href="https://github.com/magro/kryo-serializers">additional kryo serializers</a>
 */
public class KryoSerializer implements ISerializer
{

	private static final Logger LOG = LoggerFactory.getLogger(KryoSerializer.class);

	/**
	 * The size of the {@link ByteBuffer} that is used to hold the serialized page
	 */
	private static final Bytes DEFAULT_BUFFER_SIZE = Bytes.megabytes(10L);

	private final Bytes bufferSize;

	/**
	 * Store a per thread Kryo instance (as Kryo is 
	 * not thread safe).
	 */
	private ThreadLocal<Kryo> kryo =  new ThreadLocal<Kryo>();

	/**
	 * Constructor using default buffer size.
	 */
	public KryoSerializer()
	{
		this(DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Constructor.
	 * 
	 * @param bufferSize The buffer size;
	 */
	public KryoSerializer(final Bytes bufferSize)
	{
		this.bufferSize = Args.notNull(bufferSize, "bufferSize");
		LOG.debug("Buffer size: '{}'", bufferSize);
	}

	/**
	 * Factory method for Kryo serializers.
	 * 
	 * @return
	 */
	protected Kryo createKryo()
	{
		return new KryoReflectionFactorySupport();
	}
	
	
	/**
	 * @return the Kryo serializer for the current thread.
	 */
	protected  Kryo getKryo() {
		if(this.kryo.get() == null) {
			Kryo kryo = createKryo();
			internalInit(kryo);
			this.kryo.set(kryo);
		}
		return this.kryo.get();
	}

	@Override
	public byte[] serialize(final Object object)
	{
		LOG.debug("Going to serialize: '{}'", object);
		Output buffer = getBuffer(object);
		try {
			getKryo().writeClassAndObject(buffer, object);
			byte[] data = buffer.toBytes();
			if (data == null)
			{
				LOG.error("Kryo wasn't able to serialize: '{}'", object);
			}

			// release the memory for the buffer
			buffer.clear();
			buffer = null;

			return data;
		} finally {
			System.runFinalization();
		}
	}

	@Override
	public Object deserialize(byte[] data)
	{
		Input buffer = new Input(data);
		Object object = getKryo().readClassAndObject(buffer);
		LOG.debug("Deserialized: '{}'", object);

		// release the memory for the buffer
		// buffer.clear();
		buffer = null;
		System.runFinalization();

		return object;
	}

	/**
	 * Creates the buffer that will be used to serialize the {@code target}
	 * 
	 * @param target
	 *            the object that will be serialized. Can be used to decide dynamically what size to
	 *            use
	 * @return the buffer that will be used to serialize the {@code target}
	 */
	protected Output getBuffer(Object target)
	{
		return new Output((int)bufferSize.bytes());
	}

	/**
	 * Configures {@link Kryo} with some custom {@link Serializer}s and registers some known Wicket
	 * classes which are known to be serialized sooner or later
	 * 
	 * @param kryo
	 *            the {@link Kryo} instance to configured
	 */
	private void internalInit(final Kryo kryo)
	{

		kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
		kryo.register(Class.class, new ClassSerializer());
		kryo.register(Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer());
		kryo.register(Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer());
		kryo.register(Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer());
		kryo.register(Collections.singletonList("").getClass(),
			new CollectionsSingletonListSerializer());
		kryo.register(Collections.singleton("").getClass(), new CollectionsSingletonSetSerializer());
		kryo.register(Collections.singletonMap("", "").getClass(),
			new CollectionsSingletonMapSerializer());
		kryo.register(Currency.class, new CurrencySerializer());
		kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
		kryo.register(InvocationHandler.class, new JdkProxySerializer());
		kryo.register(StringBuffer.class, new StringBufferSerializer());
		kryo.register(StringBuilder.class, new StringBuilderSerializer());
		UnmodifiableCollectionsSerializer.registerSerializers(kryo);
		SynchronizedCollectionsSerializer.registerSerializers(kryo);
		kryo.register(CGLibProxySerializer.CGLibProxyMarker.class, new CGLibProxySerializer());
		kryo.register(InvocationHandler.class, new JdkProxySerializer());

		kryo.setRegistrationRequired(false);
		kryo.register(Panel.class);
		kryo.register(WebPage.class);
		kryo.register(WebMarkupContainer.class);
		kryo.register(Link.class);
		kryo.register(Label.class);
		kryo.register(ListView.class);

		init(kryo);
	}

	/**
	 * A method which can be overridden by users to do more configuration
	 * 
	 * @param kryo
	 *            the {@link Kryo} instance to configure
	 */
	protected void init(final Kryo kryo)
	{

	}
}
