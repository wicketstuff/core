package org.wicketstuff.pageserializer.kryo2.inspecting.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImmutableTree implements ISerializedObjectTree {
	
	final Class<?> type;
	final String label;
	final int size;
	final int childSize;
	
	final List<ImmutableTree> children;
	
	public ImmutableTree(Class<?> type,String label,int size,List<ImmutableTree> children) {
		this.type=type;
		this.label=label;
		this.size=size;
		List<ImmutableTree> lchildren=new ArrayList<ImmutableTree>();
		lchildren.addAll(children);
		this.children=Collections.unmodifiableList(lchildren);
		int childSize=0;
		for (ImmutableTree child : children) {
			childSize=childSize+child.childSize()+child.size();
		}
		this.childSize=childSize;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public int childSize() {
		return childSize;
	}

	@Override
	public Class<? extends Object> type() {
		return type;
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public List<? extends ISerializedObjectTree> children() {
		return children;
	}
}