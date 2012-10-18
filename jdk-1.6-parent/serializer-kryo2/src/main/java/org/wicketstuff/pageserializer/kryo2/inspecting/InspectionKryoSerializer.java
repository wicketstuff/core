package org.wicketstuff.pageserializer.kryo2.inspecting;

import org.apache.wicket.util.lang.Bytes;
import org.wicketstuff.pageserializer.kryo2.KryoSerializer;

import com.esotericsoftware.kryo.Kryo;

public class InspectionKryoSerializer extends KryoSerializer {

	private final ISerializationListener serializingListener;

	public InspectionKryoSerializer(Bytes size, ISerializationListener serializingListener) {
		super(size);
		this.serializingListener = serializingListener;
	}

	@Override
	protected Kryo createKryo() {
		return new InspectingKryo(this);
	}

	@Override
	public byte[] serialize(Object object) {
		try {
			serializingListener.start(object);
			return super.serialize(object);
		} finally {
			serializingListener.end(object);
		}
	}

	protected final ISerializationListener serializingListener() {
		return serializingListener;
	}
}
