package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

/**
 * sort and render the tree based on absolute sizes
 * @author mosmann
 *
 */
public class SortedTreeSizeReport extends TreeSizeReport
{

	@Override
	protected List<? extends ISerializedObjectTree> preProcess(
		List<? extends ISerializedObjectTree> children)
	{

		List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();
		ret.addAll(children);
		Collections.sort(ret, new Comparator<ISerializedObjectTree>()
		{
			@Override
			public int compare(ISerializedObjectTree o1, ISerializedObjectTree o2)
			{
				int s1 = o1.size() + o1.childSize();
				int s2 = o2.size() + o2.childSize();
				return s1 == s2 ? 0 : s1 < s2 ? 1 : -1;
			}
		});
		return ret;
	}
}
