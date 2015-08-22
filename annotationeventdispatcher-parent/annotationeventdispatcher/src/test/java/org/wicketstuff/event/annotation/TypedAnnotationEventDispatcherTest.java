package org.wicketstuff.event.annotation;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.Visit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class TypedAnnotationEventDispatcherTest
{
	private final MockApplication testApp = new MockApplication();
	private final WicketTester tester = new WicketTester(testApp);
	
	@After
	public void cleanup() {
		tester.destroy();
	}
	
	@Test
	public void eventSentToProperHandlersBaseOnPayloadType()
	{		
		ComponentOne one = new ComponentOne("id1");
		ComponentTwo two = new ComponentTwo("id2");
		TestContainer container = new TestContainer("container");
		container.add(one, two);
		tester.startComponentInPage(container);
		one.send(one, Broadcast.BUBBLE, new SaveEvent<>(null, new Widget()));
		Assert.assertEquals(0, one.personsHandled);
		Assert.assertEquals(1, one.widgetsHandled);
		Assert.assertEquals(0, two.savesHandled);
		Assert.assertEquals(0, container.personsHandled);
		Assert.assertEquals(1, container.widgetsHandled);
	}
	
	@Test
	public void eventDoesNotPropogateOnVisitWithStop()
	{		
		ComponentOne one = new ComponentOne("id1");
		ComponentTwo two = new ComponentTwo("id2");
		TestContainer container = new TestContainer("container");
		container.add(one, two);
		tester.startComponentInPage(container);
		one.send(one, Broadcast.BUBBLE, new SaveEvent<>(null, new Person()));
		Assert.assertEquals(1, one.personsHandled);
		Assert.assertEquals(0, one.widgetsHandled);
		Assert.assertEquals(0, two.savesHandled);
		Assert.assertEquals(0, container.widgetsHandled);
		Assert.assertEquals(0, container.personsHandled);
	}
	
	@Test
	public void eventDoesNotPropogateOnAnnotationWithStop()
	{		
		ComponentTwo two = new ComponentTwo("id1");
		ComponentFour four = new ComponentFour("id2");
		TestContainer container = new TestContainer("container");
		container.add(two, four);
		tester.startComponentInPage(container);
		four.send(four, Broadcast.BUBBLE, new SaveEvent<>(null, new Person()));
		Assert.assertEquals(0, two.savesHandled);
		Assert.assertEquals(1, four.personsHandled);
		Assert.assertEquals(0, four.widgetsHandled);
		Assert.assertEquals(0, container.widgetsHandled);
		Assert.assertEquals(0, container.personsHandled);
	}

	@Test
	public void eventPropogatesToHandlersWithoutTypes()
	{		
		ComponentOne one = new ComponentOne("id1");
		ComponentTwo two = new ComponentTwo("id2");
		TestContainer container = new TestContainer("container");
		container.add(one, two);
		tester.startComponentInPage(container);
		one.send(container.getApplication(), Broadcast.BREADTH, new SaveEvent<>(null, new Widget()));
		Assert.assertEquals(0, one.personsHandled);
		Assert.assertEquals(1, one.widgetsHandled);
		Assert.assertEquals(1, two.savesHandled);
		Assert.assertEquals(1, container.widgetsHandled);
		Assert.assertEquals(0, container.personsHandled);
	}
	
	@Test
	public void eventPropogatesToHandlersOfThePayloadSuperType()
	{		
		ComponentOne one = new ComponentOne("id1");
		ComponentTwo two = new ComponentTwo("id2");
		TestContainer container = new TestContainer("container");
		container.add(one, two);
		tester.startComponentInPage(container);
		one.send(container.getApplication(), Broadcast.BREADTH, new SaveEvent<>(null, new SuperWidget()));
		Assert.assertEquals(0, one.personsHandled);
		Assert.assertEquals(1, one.widgetsHandled);
		Assert.assertEquals(1, two.savesHandled);
		Assert.assertEquals(1, container.widgetsHandled);
		Assert.assertEquals(0, container.personsHandled);
	}
	
	@Test
	public void eventNotSentToNonVisibleComponent()
	{		
		ComponentOne one = new ComponentOne("id1");
		ComponentTwo two = new ComponentTwo("id2");
		TestContainer container = new TestContainer("container");
		container.add(one, two);
		two.setVisible(false);
		tester.startComponentInPage(container);
		one.send(container.getApplication(), Broadcast.BREADTH, new SaveEvent<>(null, new Widget()));
		Assert.assertEquals(0, one.personsHandled);
		Assert.assertEquals(1, one.widgetsHandled);
		Assert.assertEquals(0, two.savesHandled);
		Assert.assertEquals(1, container.widgetsHandled);
		Assert.assertEquals(0, container.personsHandled);
	}
	
	@Test(expected = IllegalStateException.class)
	public void exceptionWhenGenericDoesNotAgreeWithTypes()
	{		
		ComponentTwo two = new ComponentTwo("id1");
		ComponentThree three = new ComponentThree("id2");
		TestContainer container = new TestContainer("container");
		container.add(two, three);
		tester.startComponentInPage(container);
		two.send(container.getApplication(), Broadcast.BREADTH, new SaveEvent<>(null, new Person()));
	}
	
	/**
	 * https://github.com/wicketstuff/core/pull/353
	 */
	@Test(expected = RestartResponseException.class)
	public void replaceHandlerExceptionPropogated()
	{
		ComponentTwo two = new ComponentTwo("id1");
		ComponentThree three = new ComponentThree("id2");
		TestContainer container = new TestContainer("container");
		container.add(two, three);
		tester.startComponentInPage(container);
		two.send(container.getApplication(), Broadcast.BREADTH, new SaveEvent<Company>(null,
			new Company()));
	}

	private class Person {
		
	}
	
	private class Widget {
		
	}
	
	private class Company
	{

	}

	class SuperWidget extends Widget {
		
	}
	
	private class SaveEvent<T> extends AbstractPayloadTypedEvent<T> {

		public SaveEvent(final AjaxRequestTarget target, final T payload)
		{
			super(target, payload);
		}
		
	}
	
	private class TestContainer extends Panel
	{
		private static final long serialVersionUID = 1L;
		
		int personsHandled = 0;
		int widgetsHandled = 0;

		public TestContainer(final String id)
		{
			super(id);
		}

		@OnEvent(types = Widget.class)
		public void handleSaveWidgetEvent(final SaveEvent<Widget> event)
		{
			widgetsHandled++;
		}
		
		@OnEvent(types = Person.class)
		public void handleSavePersonEvent(final SaveEvent<Person> event)
		{
			personsHandled++;
		}
		
		@OnEvent(types = Company.class)
		public void handleCompanyPersonEvent(final SaveEvent<Company> event)
		{
			throw new RestartResponseException(new Page()
			{
			});
		}

	}

	private class ComponentOne extends AbstractTestComponent
	{
		private static final long serialVersionUID = 1L;

		public int personsHandled = 0;
		public int widgetsHandled = 0;
		
		public ComponentOne(final String id)
		{
			super(id);
		}

		@OnEvent(types = Widget.class)
		public void handleSaveWidgetEvent(final SaveEvent<Widget> event)
		{
			widgetsHandled++;
		}
		
		@OnEvent(types = Person.class)
		public IVisit<?> handleSavePersonEvent(final SaveEvent<Person> event)
		{
			personsHandled++;
			Visit<?> visit = new Visit<>();
			visit.stop();
			return visit;
		}
	}
	
	private class ComponentTwo extends AbstractTestComponent
	{
		private static final long serialVersionUID = 1L;

		public int savesHandled = 0;
		
		public ComponentTwo(final String id)
		{
			super(id);
		}

		@OnEvent
		public void handleSaveWidgetEvent(final SaveEvent<?> event)
		{
			savesHandled++;
		}
		
	}
	
	private class ComponentThree extends AbstractTestComponent
	{
		private static final long serialVersionUID = 1L;

		
		public ComponentThree(final String id)
		{
			super(id);
		}

		@OnEvent(types = Person.class)
		public void mismatchedClassHandler(final SaveEvent<Widget> event)
		{
			Widget widget = event.getPayload();
			System.out.println("widget = " + widget);
		}
		
	}
	
	private class ComponentFour extends AbstractTestComponent
	{
		private static final long serialVersionUID = 1L;

		public int personsHandled = 0;
		public int widgetsHandled = 0;
		
		public ComponentFour(final String id)
		{
			super(id);
		}

		@OnEvent(types = Widget.class)
		public void handleSaveWidgetEvent(final SaveEvent<Widget> event)
		{
			widgetsHandled++;
		}
		
		@OnEvent(types = Person.class, stop = true)
		public void handleSavePersonEvent(final SaveEvent<Person> event)
		{
			personsHandled++;
		}
	}
	
	private abstract class AbstractTestComponent extends Component
	{
		private static final long serialVersionUID = 1L;

		public AbstractTestComponent(final String id)
		{
			super(id);
		}

		@Override
		protected void onRender()
		{
			// do nothing
		}

	}
	
}
