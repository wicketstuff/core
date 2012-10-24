package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

/**
 * tree filter
 * @author mosmann
 *
 */
public interface ITreeFilter
{
	/**
	 * returns true if the node should not be touched 
	 * @param node tree node
	 * @param level
	 * @return 
	 */
	boolean accept(ISerializedObjectTree node, Level level);

}
