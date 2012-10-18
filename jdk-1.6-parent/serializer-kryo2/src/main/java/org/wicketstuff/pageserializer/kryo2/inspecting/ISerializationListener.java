package org.wicketstuff.pageserializer.kryo2.inspecting;

public interface ISerializationListener {

	void begin(int position, Object object);

	void end(int position, Object object);

}
