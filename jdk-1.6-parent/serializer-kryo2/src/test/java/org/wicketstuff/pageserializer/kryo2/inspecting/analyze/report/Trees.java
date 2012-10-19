package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.List;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

public class Trees {
	private Trees() {
		// no instance
	}
	
	public static boolean equals(ISerializedObjectTree a,ISerializedObjectTree b) {
		if (a==b) return true;
		if ((a==null) || (b==null)) return false;
		if (!a.type().equals(b.type())) return false;
		if (!equals(a.label(),b.label())) return false;
		if (a.size()!=b.size()) return false;
		return equals(a.children(),b.children());
	}
	
	public static boolean equals(String a,String b) {
		if (a==b) return true;
		if ((a==null) || (b==null)) return false;
		return a.equals(b);
	}

	public static boolean equals(List<? extends ISerializedObjectTree> a,
			List<? extends ISerializedObjectTree> b) {
		if (a==b) return true;
		if ((a==null) || (b==null)) return false;
		if (a.size()!=b.size()) return false;
		for (int i=0;i<a.size();i++) {
			if (!equals(a.get(i),b.get(i))) return false;
		}
		return true;
	}
}
