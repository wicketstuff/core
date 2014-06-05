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

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.IEventDispatcher;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.util.collections.ClassMetaCache;

import java.lang.reflect.Method;

/**
 * <p>
 * Delivers events to any {@link Component} methods that are annotated with {@link OnEvent}
 * annotation and take exactly one parameter that matches the event payload type. To use this
 * dispatcher, you should register it as a component instantiation listener and an event dispatcher
 * in the application initialization, for example:
 * </p>
 * {@code
 * AnnotationEventDispatcher dispatcher = new AnnotationEventDispatcher();
 * getComponentInstantiationListeners().add(dispatcher);
 * getFrameworkSettings().add(dispatcher);
 * }
 */
public class AnnotationEventDispatcher implements IEventDispatcher, IComponentInstantiationListener
{
	private static final AnnotationEventSink EMPTY_SINK = new AnnotationEventSink(Void.class);
	private final ClassMetaCache<AnnotationEventSink> eventSinkByClass = new ClassMetaCache<AnnotationEventSink>();

	@Override
	public void onInstantiation(final Component component)
	{
		Class<?> componentClass = component.getClass();
		if (eventSinkByClass.get(componentClass) == null)
		{
			if (containsOnEventMethod(componentClass))
			{
				eventSinkByClass.put(componentClass, new AnnotationEventSink(componentClass));
			}
			else
			{
				eventSinkByClass.put(componentClass, EMPTY_SINK);
			}
		}
	}

	private boolean containsOnEventMethod(final Class<?> clazz)
	{
		for (Method method : clazz.getMethods())
		{
			if (method.isAnnotationPresent(OnEvent.class))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void dispatchEvent(final Object sink, final IEvent<?> event, final Component component)
	{
		AnnotationEventSink eventSink = eventSinkByClass.get(sink.getClass());
		AnnotationEventDispatcherConfig config = Application.get().getMetaData(
				Initializer.ANNOTATION_EVENT_DISPATCHER_CONFIG_CONTEXT_KEY);
		if (eventSink != null
				&& eventSink != EMPTY_SINK
				&& (config.getEventFilter() == null || config.getEventFilter().isAssignableFrom(
						event.getPayload().getClass())))
		{
			eventSink.onEvent(sink, event);
		}
	}

}
