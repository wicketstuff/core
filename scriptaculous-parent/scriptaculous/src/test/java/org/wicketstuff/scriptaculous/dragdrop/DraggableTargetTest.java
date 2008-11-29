package org.wicketstuff.scriptaculous.dragdrop;

import junit.framework.TestCase;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.util.tester.WicketTester;

public class DraggableTargetTest extends TestCase {
	boolean isFired = false;
	
	public void setUp() {
		isFired = false;
	}

	public void testAjaxRequestFiresOnDropOperation() {
		WicketTester tester = new WicketTester();

		DraggableTarget target = new DraggableTarget("target") {
			@Override
			protected void onDrop(Component component, AjaxRequestTarget target) {
				isFired = true;
			}
		};
		TestPage page = new TestPage();
		page.add(target);

		tester.startPage(page);
		tester.startComponent(target);
		tester.executeBehavior(target.onDropBehavior);
		
		assertTrue(isFired);
	}
	
	public void testAcceptingComponentWithDraggableBehaviorAddsClassToJavascript() {
		WicketTester tester = new WicketTester();

		Label label = new Label("testing");
		label.add(new DraggableBehavior());
		DraggableTarget target = new DraggableTarget("target") {
			@Override
			protected void onDrop(Component component, AjaxRequestTarget target) { }
		};
		target.accepts(label, "myClassName");
		TestPage page = new TestPage();
		page.add(target);

		tester.startPage(page);
		tester.startComponent(target);

		tester.assertContains("accept: 'myClassName'");
	}
	
	public void testExceptionThrownWhenAcceptingComponentWithoutDraggableBehavior() {
		DraggableTarget target = new DraggableTarget("target") {
			@Override
			protected void onDrop(Component component, AjaxRequestTarget target) { }
		};
		try {
			target.accepts(new Label("testing"), "myClassName");
			fail("Expected error");
		} catch (IllegalArgumentException expected) {}
	}
}
