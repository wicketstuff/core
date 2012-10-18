package org.wicketstuff.pageserializer.kryo2.inspecting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeSizeReport implements ISerializedObjectTreeProcessor {

	private final static Logger LOG = LoggerFactory
			.getLogger(TypeSizeReport.class);
	
	@Override
	public void process(ISerializedObjectTree tree) {
		Map<Class<?>, Counter> map=new HashMap<Class<?>, TypeSizeReport.Counter>();
		process(tree,map);

		List<Map.Entry<Class<?>, Counter>> sorted=new ArrayList<Map.Entry<Class<?>,Counter>>();
		sorted.addAll(map.entrySet());
		Collections.sort(sorted,new Comparator<Map.Entry<Class<?>, Counter>>() {
			@Override
			public int compare(Entry<Class<?>, Counter> o1,
					Entry<Class<?>, Counter> o2) {
				int s1=o1.getValue().size;
				int s2=o2.getValue().size;
				return s1==s2 ? 0 : s1>s2 ? -1 : 1;
			}
		});
		
		StringBuilder sb=new StringBuilder();
		sb.append("\n-----------------------------\n");
		int labelColumnSize=0;
		int sizeColumnSize=0;
		for (Map.Entry<Class<?>, Counter> e : sorted) {
			labelColumnSize=Math.max(labelColumnSize, e.getKey().getName().length());
			sizeColumnSize=Math.max(sizeColumnSize, (""+e.getValue().size).length());
		}
		for (Map.Entry<Class<?>, Counter> e : sorted) {
			Reports.label(sb, e.getKey().getName(),labelColumnSize+3,'.');
			Reports.rightColumn(sb, sizeColumnSize, '.', ""+e.getValue().size, '<');
			sb.append("\n");
		}
		LOG.debug(sb.toString());
	}
	
	private void process(ISerializedObjectTree tree, Map<Class<?>, Counter> map) {
		Counter counter=getOrCreate(map,tree.type());
		counter.increment(tree.size());
		
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
