package org.wicketstuff.pageserializer.kryo2.inspecting.analyze;

import org.wicketstuff.pageserializer.kryo2.common.Interfaces;


public class TreeProcessors {

	public static ISerializedObjectTreeProcessor listOf(final ISerializedObjectTreeProcessor ... processors) {
		return Interfaces.proxyList(ISerializedObjectTreeProcessor.class, processors);
	}
}
