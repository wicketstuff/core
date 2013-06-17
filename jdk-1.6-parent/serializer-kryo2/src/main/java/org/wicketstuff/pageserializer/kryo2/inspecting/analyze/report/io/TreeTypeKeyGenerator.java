package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.io;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;


public class TreeTypeKeyGenerator implements IReportKeyGenerator {

	private final String _defaultKey;

	public TreeTypeKeyGenerator(String defaultKey) {
		_defaultKey = defaultKey;
	}
	
	@Override
	public String keyOf(ISerializedObjectTree tree) {
		Class<? extends Object> type = tree.type();
		if (type==null) {
			if (!tree.children().isEmpty()) {
				type=tree.children().get(0).type();
			}
		}
		
		return type!=null ? type.getName().replace(".", "-") : _defaultKey;
	}
	
}
