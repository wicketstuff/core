package org.wicketstuff.pageserializer.kryo2.inspecting;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeSizeReport implements ISerializedObjectTreeProcessor {

	private final static Logger LOG = LoggerFactory
			.getLogger(TypeSizeReport.class);
	
	@Override
	public void process(ISerializedObjectTree tree) {
		Map<Class<?>, Counter> map=new HashMap<Class<?>, TypeSizeReport.Counter>();
		process(tree,map);
	}
	
	private void process(ISerializedObjectTree tree, Map<Class<?>, Counter> map) {
		Counter counter=getOrCreate(map,tree.type());
		counter.increment(tree.size()+tree.childSize());
		
		for (ISerializedObjectTree child : tree.children()) {
			process(child,map);
		}
	}

	private Counter getOrCreate(Map<Class<?>, Counter> map,
			Class<? extends Object> type) {
		Counter ret = map.get(type);
		if (ret==null) {
			ret=new Counter();
			map.put(type, ret);
		}
		return ret;
	}

	static class Counter {
		int size;
		
		void increment(int diff) {
			size=size+diff;
		}
	}

}
