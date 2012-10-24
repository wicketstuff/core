package org.wicketstuff.pageserializer.kryo2.inspecting.analyze;

/**
 * a serialized tree processor can process a serialized tree
 * 
 * @author mosmann
 * 
 */
public interface ISerializedObjectTreeProcessor
{
	/**
	 * process serialized tree data 
	 * @param tree source
	 */
	void process(ISerializedObjectTree tree);
}
