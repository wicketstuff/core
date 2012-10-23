package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder;

import java.io.Serializable;

public interface IAttributes
{
	<T extends Enum<T>> T get(T defaultValue);

	<T extends Serializable> T get(TypedAttribute<T> type, T defaultValue);

}
