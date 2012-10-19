package org.wicketstuff.pageserializer.kryo2.inspecting;

import junit.framework.Assert;

import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.IObjectLabelizer;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ObjectTreeTracker;

public class TestObjectTreeTracker {

	@Test
	public void testSizes() {
		
		Object a="HA";
		Object b=1;
		
		IObjectLabelizer labelizer=new IObjectLabelizer() {
			
			@Override
			public String labelFor(Object object) {
				return null;
			}
		};
		
		ObjectTreeTracker tracker=new ObjectTreeTracker(labelizer,a);
		tracker.newItem(0, a);
		tracker.newItem(12,b);
		tracker.closeItem(14, b);
		tracker.closeItem(24, a);
		
		ISerializedObjectTree tree = tracker.end(a);
		Assert.assertEquals("size", 22,tree.size());
		Assert.assertEquals("size", 2,tree.childSize());
	}
}
