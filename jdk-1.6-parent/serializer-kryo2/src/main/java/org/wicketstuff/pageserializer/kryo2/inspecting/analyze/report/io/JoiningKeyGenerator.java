package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.io;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

public class JoiningKeyGenerator implements IReportKeyGenerator {

	private final String _separator;
	private final IReportKeyGenerator[] _generator;

	public JoiningKeyGenerator(String separator, IReportKeyGenerator... generator) {
		_separator = separator;
		_generator = generator;
	}

	@Override
	public String keyOf(ISerializedObjectTree tree) {
		StringBuilder sb=new StringBuilder();
		boolean addSeparator=false;
		
		for (IReportKeyGenerator g : _generator) {
			if (addSeparator) {
				sb.append("-");
			} 
			addSeparator=true;
			
			String key=g.keyOf(tree);
			sb.append(key);
		}
		return sb.toString();
	}

}
