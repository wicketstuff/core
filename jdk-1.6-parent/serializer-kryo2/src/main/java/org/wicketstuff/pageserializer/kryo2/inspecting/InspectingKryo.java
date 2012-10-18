package org.wicketstuff.pageserializer.kryo2.inspecting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.kryo2.DebuggingKryo;

import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Output;

import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;

public class InspectingKryo extends KryoReflectionFactorySupport {

	private final static Logger LOG = LoggerFactory.getLogger(InspectingKryo.class);
	private final InspectionKryoSerializer parent;
	
	public InspectingKryo(InspectionKryoSerializer parent) {
		this.parent = parent;
	}
	
	@Override
	public Registration writeClass(Output output, Class type) {
		try {
			before(output, type);
			return super.writeClass(output, type);
		} finally {
			after(output, type);
		}
	}

	@Override
	public void writeClassAndObject(Output output, Object object) {
		try {
			before(output, object);
			super.writeClassAndObject(output, object);
		} finally {
			after(output, object);
		}
	}

	@Override
	public void writeObject(Output output, Object object, Serializer serializer) {
		try {
			before(output, object);
			super.writeObject(output, object, serializer);
		} finally {
			after(output, object);
		}
	}

	@Override
	public void writeObject(Output output, Object object) {
		try {
			before(output, object);
			super.writeObject(output, object);
		} finally {
			after(output, object);
		}
	}

	@Override
	public void writeObjectOrNull(Output output, Object object, Class clazz) {
		try {
			before(output, object);
			super.writeObjectOrNull(output, object, clazz);
		} finally {
			after(output, object);
		}
	}

	@Override
	public void writeObjectOrNull(Output output, Object object,
			Serializer serializer) {
		try {
			before(output, object);
			super.writeObjectOrNull(output, object, serializer);
		} finally {
			after(output, object);
		}
	}

	private void before(Output output, Object object) {
		parent.serializingListener().begin(output.position(),object);
	}

	private void after(Output output, Object object) {
		parent.serializingListener().end(output.position(),object);
	}

}
