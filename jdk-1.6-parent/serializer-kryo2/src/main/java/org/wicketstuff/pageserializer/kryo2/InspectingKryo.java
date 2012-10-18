package org.wicketstuff.pageserializer.kryo2;

import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Output;

import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;

public class InspectingKryo extends KryoReflectionFactorySupport {

	@Override
	public Registration writeClass(Output output, Class type) {
		return super.writeClass(output, type);
	}

	@Override
	public void writeClassAndObject(Output output, Object object) {
		super.writeClassAndObject(output, object);
	}

	@Override
	public void writeObject(Output output, Object object, Serializer serializer) {
		super.writeObject(output, object, serializer);
	}

	@Override
	public void writeObject(Output output, Object object) {
		super.writeObject(output, object);
	}

	@Override
	public void writeObjectOrNull(Output output, Object object, Class clazz) {
		super.writeObjectOrNull(output, object, clazz);
	}

	@Override
	public void writeObjectOrNull(Output output, Object object,
			Serializer serializer) {
		super.writeObjectOrNull(output, object, serializer);
	}

}
