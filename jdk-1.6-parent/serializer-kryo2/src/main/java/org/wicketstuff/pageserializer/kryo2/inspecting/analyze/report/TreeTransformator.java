package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

public class TreeTransformator {
	private TreeTransformator() {
		// no instance
	}
	
	public static ISerializedObjectTree transform(ISerializedObjectTree source, ITreeFilter filter) {
		if (!filter.accept(source)) {
			return compact(source,filter);
		}
		return source;
	}

	private static ISerializedObjectTree compact(ISerializedObjectTree source,
			ITreeFilter filter) {
		return source;
	}
}
