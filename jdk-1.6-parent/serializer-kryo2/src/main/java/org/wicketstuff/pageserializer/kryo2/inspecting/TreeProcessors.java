package org.wicketstuff.pageserializer.kryo2.inspecting;


public class TreeProcessors {

	public static ISerializedObjectTreeProcessor listOf(final ISerializedObjectTreeProcessor ... processors) {
		return Interfaces.proxyList(ISerializedObjectTreeProcessor.class, processors);
	}
}
