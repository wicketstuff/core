package org.wicketstuff.pageserializer.kryo2.inspecting;

public interface ISerializationListener {

	void start(Object object);
	
	void begin(int position, Object object);

	void end(int position, Object object);

	void end(Object object);
}
