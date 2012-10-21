package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

public interface ITreeFilter
{

	boolean accept(ISerializedObjectTree source, Level current);

}
