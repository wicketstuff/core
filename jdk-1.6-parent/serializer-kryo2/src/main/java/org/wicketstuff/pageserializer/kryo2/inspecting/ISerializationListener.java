package org.wicketstuff.pageserializer.kryo2.inspecting;

public interface ISerializationListener
{

	void begin(Object object);

	void before(int position, Object object);

	void after(int position, Object object);

	void end(Object object);
}
