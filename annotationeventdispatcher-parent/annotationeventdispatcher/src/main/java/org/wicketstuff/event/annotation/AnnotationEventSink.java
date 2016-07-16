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

import static java.lang.reflect.Modifier.isPublic;
import static java.util.Arrays.asList;
import static org.apache.wicket.RuntimeConfigurationType.DEVELOPMENT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.request.RequestHandlerExecutor;
import org.apache.wicket.util.collections.ClassMetaCache;
import org.apache.wicket.util.visit.Visit;

class AnnotationEventSink
{
	private final ClassMetaCache<Set<Method>> onEventMethodsByParameterType = new ClassMetaCache<Set<Method>>();
	private final ClassMetaCache<Set<Method>> onEventMethodsByPayloadType = new ClassMetaCache<Set<Method>>();

	public AnnotationEventSink(final Class<?> clazz)
	{
		boolean isDevelopmentMode = Application.get().getConfigurationType() == DEVELOPMENT;
		for (Method method : getAllMethods(clazz))
		{
			if (method.isAnnotationPresent(OnEvent.class))
			{
				Class<?> parameterTypes[] = method.getParameterTypes();
				if (isDevelopmentMode)
				{
					checkParameters(method, parameterTypes);
				}
				if (parameterTypes.length == 1)
				{
					addOnEventMethodForType(parameterTypes[0], method);
				}
			}
		}
	}

	private Set<Method> getAllMethods(final Class<?> clazz)
	{
		Set<Method> allMethods = new HashSet<Method>();
		allMethods.addAll(asList(clazz.getMethods()));
		allMethods.addAll(asList(clazz.getDeclaredMethods()));
		return allMethods;
	}

	private void checkParameters(final Method method, final Class<?>[] parameterTypes)
	{
		if (!isPublic(method.getModifiers()))
		{
			throw new RuntimeException("Invalid @OnEvent annotation in " + method
					+ ": @OnEvent annotated methods must be public");
		}
		if (parameterTypes.length != 1)
		{
			throw new RuntimeException("Invalid @OnEvent annotation in " + method
					+ ": @OnEvent annotated methods must have exactly one parameter");
		}
	}

	private void addOnEventMethodForType(final Class<?> type, final Method method)
	{
		Set<Method> methods = onEventMethodsByParameterType.get(type);
		if (methods == null)
		{
			methods = new HashSet<Method>();
			onEventMethodsByParameterType.put(type, methods);
		}
		methods.add(method);
	}

	public void onEvent(final Object sink, final IEvent<?> event)
	{
		if (event == null)
		{
			return;
		}
		Object payload = event.getPayload();
		if (payload == null)
		{
			return;
		}
		Set<Method> onEventMethods = getOnEventMethods(payload.getClass());
		if (!onEventMethods.isEmpty())
		{
			onEvent(onEventMethods, sink, payload, event);
		}
	}

	private Set<Method> getOnEventMethods(final Class<?> payloadType)
	{
		Set<Method> onEventMethods = onEventMethodsByPayloadType.get(payloadType);
		if (onEventMethods == null)
		{
			onEventMethods = new HashSet<Method>();
			for (Class<?> type : CompatibleTypesCache.getCompatibleTypes(payloadType))
			{
				Set<Method> methods = onEventMethodsByParameterType.get(type);
				if (methods != null)
				{
					onEventMethods.addAll(methods);
				}
			}
			onEventMethodsByPayloadType.put(payloadType, onEventMethods);
		}
		return onEventMethods;
	}

	private void onEvent(final Set<Method> onEventMethods, final Object sink, final Object payload,
			final IEvent<?> event)
	{
		try
		{
			for (Method method : onEventMethods)
			{
				if (canCallListenerInterface(sink))
				{
					OnEvent onEvent = method.getAnnotation(OnEvent.class);
					if (isPayloadApplicableToHandler(onEvent, payload))
					{
						Object result = method.invoke(sink, payload);
						if (result instanceof Visit<?>)
						{
							Visit<?> visit = (Visit<?>) result;
							if (visit.isDontGoDeeper())
							{
								event.dontBroadcastDeeper();
							}
							else if (visit.isStopped())
							{
								event.stop();
								break;
							}
						}
						else if (onEvent.stop())
						{
							event.stop();
							break;
						}
					}
				}
			}
		} catch (InvocationTargetException e)
		{
			if (e.getCause() instanceof RequestHandlerExecutor.ReplaceHandlerException)
			{
				throw ((RequestHandlerExecutor.ReplaceHandlerException)e.getCause());
			}
			else
			{
				throw new IllegalStateException("Failed to invoke @OnEvent method", e);
			}
		} catch (IllegalAccessException e)
		{
			throw new IllegalStateException("Failed to invoke @OnEvent method", e);
		}
	}

	private boolean canCallListenerInterface(final Object obj)
	{
		boolean canCall = true;
		if (obj instanceof Component)
		{
			Component c = (Component) obj;
			canCall = c.canCallListenerInterface();
		}
		return canCall;
	}

	private boolean isPayloadApplicableToHandler(final OnEvent onEvent, final Object payload)
	{
		boolean applicable = true;
		if (payload instanceof ITypedEvent)
		{
			Class<?>[] methodTypes = onEvent.types();
			ITypedEvent event = (ITypedEvent) payload;
			List<Class<?>> eventTypes = event.getTypes();
			for (int i = 0; i < methodTypes.length; i++)
			{
				if (!methodTypes[i].isAssignableFrom(eventTypes.get(i)))
				{
					applicable = false;
					break;
				}
			}
		}
		return applicable;
	}
}
