/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.event.annotation;

import org.apache.wicket.Component;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.apache.wicket.event.Broadcast.BREADTH;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Tests for AnnotationEventDispatcher
 */
public class AnnotationEventDispatcherTest
{
	private final MockApplication testApp = new MockApplication();
	private final WicketTester tester = new WicketTester(testApp);
	final EventMock mock = Mockito.mock(EventMock.class);
	private Component component;

	/**
	 * Create and initialize the dispatcher
	 */
	@Before
	public void setup()
	{
		reset(mock);
	}

	/**
	 * Verify that event is passed to annotated methods in the event sink class and the super class
	 */
	@Test
	public void eventIsPassedToCorrectEventMethods()
	{
		component = tester.startComponentInPage(new TestComponent("id"));
		component.send(testApp, BREADTH, "Hello!");
		verify(mock).onStringEvent();
		verify(mock).onStringEventInBaseClass();
	}

	/**
	 * Verify that all annotated methods that accept the event payload are called
	 */
	@Test
	public void eventIsPassedToAllOnEventMethodsThatAcceptGivenEventType()
	{
		component = tester.startComponentInPage(new TestComponent("id"));
		component.send(testApp, BREADTH, new IllegalArgumentException());
		verify(mock).onExceptionEvent();
		verify(mock).onRuntimeExceptionEvent();
	}

	/**
	 * Verify that an exception is raised when the annotated method does not contain exactly one
	 * parameter
	 */
	@Test(expected = RuntimeException.class)
	public void componentCannotBeInstantiatedIfAnnotatedMethodDoesNotContainOneParameter()
	{
		tester.startComponentInPage(new IncorrectNumberOfParameters("id"));
	}

	/**
	 * Verify that an exception is raised when the annotated method is not public
	 */
	@Test(expected = RuntimeException.class)
	public void componentCannotBeInstantiatedIfAnnotatedMethodIsNotPublic()
	{
		tester.startComponentInPage(new IncorrectMethodVisibility("id"));
	}

	/**
	 * Verify that event is passed to annotated methods only
	 */
	@Test
	public void eventIsNotPassedToMethodsThatAreNotAnnotated()
	{
		NotAnnotatedTestComponent testComponent = new NotAnnotatedTestComponent();
		tester.startComponentInPage(testComponent);
		testComponent.send(testApp, BREADTH, "Hello!");
		verify(mock, never()).onStringEvent();
	}

	private class TestComponent extends AbstractTestComponent
	{
		private static final long serialVersionUID = 1L;

		public TestComponent(final String id)
		{
			super(id);
		}

		@OnEvent
		public void onStringEvent(final String message)
		{
			mock.onStringEvent();
		}

		@OnEvent
		public void onExceptionEvent(final Exception e)
		{
			mock.onExceptionEvent();
		}

		@OnEvent
		public void onRuntimeExceptionEvent(final RuntimeException e)
		{
			mock.onRuntimeExceptionEvent();
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

		@OnEvent
		public void onBaseClassEvent(final String message)
		{
			mock.onStringEventInBaseClass();
		}
	}

	private class IncorrectNumberOfParameters extends AbstractTestComponent
	{
		private static final long serialVersionUID = 1L;

		public IncorrectNumberOfParameters(final String id)
		{
			super(id);
		}

		@OnEvent
		public void onStringEvent(final String message, final String fails)
		{
			mock.onStringEvent();
		}
	}

	private class IncorrectMethodVisibility extends AbstractTestComponent
	{
		private static final long serialVersionUID = 1L;

		public IncorrectMethodVisibility(final String id)
		{
			super(id);
		}

		@OnEvent
		protected void onStringEvent(final String message)
		{
			mock.onStringEvent();
		}
	}

	private class NotAnnotatedTestComponent extends Component
	{
		private static final long serialVersionUID = 1L;

		public NotAnnotatedTestComponent()
		{
			super("id");
		}

		@Override
		protected void onRender()
		{
			// do nothing
		}

		public void onStringEvent(final String message, final String allowed)
		{
			mock.onStringEvent();
		}
	}

	private static class EventMock
	{
		public void onStringEvent()
		{
			// do nothing
		}

		public void onStringEventInBaseClass()
		{
			// do nothing
		}

		public void onExceptionEvent()
		{
			// do nothing
		}

		public void onRuntimeExceptionEvent()
		{
			// do nothing
		}
	}
}
