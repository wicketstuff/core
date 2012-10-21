package org.wicketstuff.pageserializer.kryo2.inspecting;

import org.apache.wicket.util.lang.Bytes;
import org.wicketstuff.pageserializer.kryo2.KryoSerializer;

import com.esotericsoftware.kryo.Kryo;

public class InspectingKryoSerializer extends KryoSerializer
{

	private final ISerializationListener serializingListener;

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
		byte[] ret;
//		try
		{
			serializingListener.begin(object);
			ret = super.serialize(object);
		}
//		finally
		{
			serializingListener.end(object);
		}
		return ret;
	}

	protected final ISerializationListener serializingListener()
	{
		return serializingListener;
	}
}
