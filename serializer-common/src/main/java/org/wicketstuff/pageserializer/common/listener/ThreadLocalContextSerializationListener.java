/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
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
package org.wicketstuff.pageserializer.common.listener;

/**
 * serialization listener which handles a thread local context 
 * @author mosmann
 *
 * @param <T> context type
 */
public abstract class ThreadLocalContextSerializationListener<T> implements ISerializationListener
{
	private final ThreadLocal<T> contextContainer = new ThreadLocal<T>();

	/**
	 * creates a context for one serialization process
	 * @param object object to be serialized
	 * @return context based on this
	 */
	protected abstract T createContext(Object object);

	@Override
	public final void begin(Object object)
	{
		T context = createContext(object);
		contextContainer.set(context);
		begin(context, object);
	}

	/**
	 * @see ISerializationListener#begin(Object)
	 */
	protected abstract void begin(T context, Object object);

	@Override
	public final void before(int position, Object object)
	{
		before(contextContainer.get(), position, object);
	}

	/**
	 * @see ISerializationListener#before(int, Object)
	 */
	protected abstract void before(T context, int position, Object object);

	@Override
	public final void after(int position, Object object)
	{
		after(contextContainer.get(), position, object);
	}

	/**
	 * @see ISerializationListener#after(int, Object)
	 */
	protected abstract void after(T context, int position, Object object);

	@Override
	public void end(Object object, Exception exception)
	{
		T context = contextContainer.get();
		contextContainer.remove();
		end(context, object, exception);
	}

	/**
	 * @see ISerializationListener#end(Object, RuntimeException)
	 */
	protected abstract void end(T context, Object object, Exception exception);
}
