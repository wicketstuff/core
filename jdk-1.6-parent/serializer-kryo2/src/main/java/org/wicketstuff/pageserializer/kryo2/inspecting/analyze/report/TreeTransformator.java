package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ImmutableTree;

public class TreeTransformator {
	private TreeTransformator() {
		// no instance
	}

	public static ISerializedObjectTree compact(ISerializedObjectTree source,
			ITreeFilter filter) {
		if (filter.accept(source)) {
			if (!source.children().isEmpty()) {
				boolean changed = false;

				List<ISerializedObjectTree> filteredList = new ArrayList<ISerializedObjectTree>();
				for (ISerializedObjectTree child : source.children()) {
					ISerializedObjectTree filtered = compact(child, filter);
					filteredList.add(filtered);
					if (filtered != child)
						changed = true;
				}

				if (changed) {
					return new ImmutableTree(source.type(), source.label(),
							source.size(), filteredList);
				}
			}
			return source;
		} else {
			return new ImmutableTree(source.type(), source.label(),
					source.size()+source.childSize(), new ArrayList<ISerializedObjectTree>());
		}
	}
}
