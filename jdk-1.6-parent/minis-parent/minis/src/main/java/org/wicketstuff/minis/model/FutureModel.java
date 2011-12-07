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
package org.wicketstuff.minis.model;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * A read-only model implementation that retrieves the model object from a {@link Future} object.
 * This is useful when retrieving data from slow back-ends asynchronously before the synchronous
 * rendering of the Wicket components happens.
 * 
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class FutureModel<T> extends AbstractReadOnlyModel<T>
{
	private static final long serialVersionUID = 1L;

	private transient Future<T> future;
	private T futureValue = null;

	public FutureModel(final Future<T> future)
	{
		this.future = future;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void detach()
	{
		try
		{
			if (futureValue == null && future != null)
				futureValue = future.get();
		}
		catch (final InterruptedException ex)
		{
			throw new WicketRuntimeException(ex);
		}
		catch (final ExecutionException ex)
		{
			throw new WicketRuntimeException(ex);
		}
		future = null;
		super.detach();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getObject()
	{
		try
		{
			if (futureValue == null && future != null)
				futureValue = future.get();
		}
		catch (final InterruptedException ex)
		{
			throw new WicketRuntimeException(ex);
		}
		catch (final ExecutionException ex)
		{
			throw new WicketRuntimeException(ex);
		}
		return futureValue;
	}
}