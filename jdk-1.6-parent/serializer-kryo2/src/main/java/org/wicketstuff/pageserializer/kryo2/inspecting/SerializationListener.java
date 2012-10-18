package org.wicketstuff.pageserializer.kryo2.inspecting;

public class SerializationListener implements ISerializationListener {

	private final ISerializationListener[] listener;

	public SerializationListener(ISerializationListener... listener) {
		this.listener = listener;
	}

	@Override
	public void begin(Object object) {
		for (ISerializationListener l : listener) {
			l.begin(object);
		}
	}

	@Override
	public void before(int position, Object object) {
		for (ISerializationListener l : listener) {
			l.before(position, object);
		}
	}

	@Override
	public void after(int position, Object object) {
		for (ISerializationListener l : listener) {
			l.after(position, object);
		}
	}

	@Override
	public void end(Object object) {
		for (ISerializationListener l : listener) {
			l.end(object);
		}
	}
	
	public static ISerializationListener listOf(ISerializationListener ... listener) {
		return new SerializationListener(listener);
	}
}
