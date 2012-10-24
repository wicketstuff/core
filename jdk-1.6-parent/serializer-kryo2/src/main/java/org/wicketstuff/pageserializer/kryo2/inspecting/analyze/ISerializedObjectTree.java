package org.wicketstuff.pageserializer.kryo2.inspecting.analyze;

import java.util.List;

/**
 * serialized tree
 * 
 * @author mosmann
 * 
 */
public interface ISerializedObjectTree
{

	/**
	 * node type
	 * 
	 * @return type
	 */
	Class<? extends Object> type();

	/**
	 * node label if any
	 * 
	 * @return type
	 */
	String label();

	/**
	 * size of object without children
	 * 
	 * @return size in bytes
	 */
	int size();

	/**
	 * size of all children
	 * 
	 * @return size in bytes
	 */
	int childSize();

	/**
	 * list of children
	 * 
	 * @return immutable
	 */
	List<? extends ISerializedObjectTree> children();

}
