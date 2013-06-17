package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.io;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;


public interface IReportKeyGenerator {
	String keyOf(ISerializedObjectTree tree);
}
