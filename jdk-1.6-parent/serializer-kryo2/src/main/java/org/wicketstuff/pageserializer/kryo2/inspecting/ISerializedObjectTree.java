package org.wicketstuff.pageserializer.kryo2.inspecting;

import java.util.Collection;
import java.util.List;

public interface ISerializedObjectTree {

	/**
	 * size of object without children
	 * @return
	 */
	int size();

	Class<? extends Object> type();

	String label();

	List<? extends ISerializedObjectTree> children();

	int childSize();

}
