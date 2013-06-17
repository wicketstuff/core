package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.io;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;


public class StaticKeyGenerator implements IReportKeyGenerator {

	private String _staticValue;

	public StaticKeyGenerator(String staticValue) {
		_staticValue = staticValue;
	}
	
	@Override
	public String keyOf(ISerializedObjectTree tree) {
		return _staticValue;
	}

}
