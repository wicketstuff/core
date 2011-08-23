package org.wicketstuff.pageserializer.kryo;

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

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.ClassSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyMapSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptySetSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonListSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonMapSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonSetSerializer;
import de.javakaffee.kryoserializers.CurrencySerializer;
import de.javakaffee.kryoserializers.GregorianCalendarSerializer;
import de.javakaffee.kryoserializers.JdkProxySerializer;
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;
import de.javakaffee.kryoserializers.StringBufferSerializer;
import de.javakaffee.kryoserializers.StringBuilderSerializer;
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

	private final Kryo kryo;

	public KryoSerializer()
	{
		this(DEFAULT_BUFFER_SIZE);
	}

	public KryoSerializer(final Bytes bufferSize)
	{

		this.bufferSize = Args.notNull(bufferSize, "bufferSize");
		LOG.debug("Buffer size: '{}'", bufferSize);

		kryo = createKryo();

		internalInit(kryo);
	}

	protected Kryo createKryo()
	{
		return new KryoReflectionFactorySupport();
	}

	@Override
	public byte[] serialize(final Object object)
	{
		LOG.debug("Going to serialize: '{}'", object);
		ByteBuffer buffer = getBuffer(object);
		kryo.writeClassAndObject(buffer, object);
		byte[] data;
		if (buffer.hasArray())
		{
			data = new byte[buffer.position()];
			buffer.flip();
			buffer.get(data);
		}
		else
		{
			LOG.error("Kryo wasn't able to serialize: '{}'", object);
			data = null;
		}

		// release the memory for the buffer
		buffer.clear();
		buffer = null;
		System.runFinalization();

		return data;
	}

	@Override
	public Object deserialize(byte[] data)
	{
		ByteBuffer buffer = ByteBuffer.wrap(data);
		Object object = kryo.readClassAndObject(buffer);
		LOG.debug("Deserialized: '{}'", object);

		// release the memory for the buffer
		buffer.clear();
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
	protected ByteBuffer getBuffer(Object target)
	{
		return ByteBuffer.allocate((int)bufferSize.bytes());
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

		kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer(kryo));
		kryo.register(Class.class, new ClassSerializer(kryo));
		kryo.register(Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer());
		kryo.register(Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer());
		kryo.register(Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer());
		kryo.register(Collections.singletonList("").getClass(),
			new CollectionsSingletonListSerializer(kryo));
		kryo.register(Collections.singleton("").getClass(), new CollectionsSingletonSetSerializer(
			kryo));
		kryo.register(Collections.singletonMap("", "").getClass(),
			new CollectionsSingletonMapSerializer(kryo));
		kryo.register(Currency.class, new CurrencySerializer(kryo));
		kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
		kryo.register(InvocationHandler.class, new JdkProxySerializer(kryo));
		kryo.register(StringBuffer.class, new StringBufferSerializer(kryo));
		kryo.register(StringBuilder.class, new StringBuilderSerializer(kryo));
		UnmodifiableCollectionsSerializer.registerSerializers(kryo);
		SynchronizedCollectionsSerializer.registerSerializers(kryo);
		kryo.register(CGLibProxySerializer.CGLibProxyMarker.class, new CGLibProxySerializer(kryo));
		kryo.register(InvocationHandler.class, new JdkProxySerializer(kryo));
		kryo.register(WicketChildListSerializer.CLASS, new WicketChildListSerializer(kryo));

		kryo.setRegistrationOptional(true);
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