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
package org.wicketstuff.jquery.ui.form.autocomplete;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link IElementSelectionStrategy} that identifies choices by their index in the current result list.
 * This is the default strategy used by {@link AbstractAutoCompleteTextField}.
 *
 * @param <T> the choice type
 * @author reiern70
 */
public class IndexBasedElementSelectionStrategy<T> implements IElementSelectionStrategy<T>
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(IndexBasedElementSelectionStrategy.class);

	private static final IndexBasedElementSelectionStrategy<Object> INSTANCE = new IndexBasedElementSelectionStrategy<>();

	/**
	 * Gets a shared instance of this strategy.
	 *
	 * @param <T> the choice type
	 * @return the shared strategy
	 */
	@SuppressWarnings("unchecked")
	public static <T> IndexBasedElementSelectionStrategy<T> get()
	{
		return (IndexBasedElementSelectionStrategy<T>) INSTANCE;
	}

	private Object readResolve()
	{
		return INSTANCE;
	}

	@Override
	public String getIdentifier(T choice, int index)
	{
		return Integer.toString(index);
	}

	@Override
	public T findChoice(List<T> choices, String identifier)
	{
		if (choices == null || identifier == null)
		{
			return null;
		}

		try
		{
			int index = Integer.parseInt(identifier);

			if (-1 < index && index < choices.size())
			{
				return choices.get(index);
			}
		}
		catch (NumberFormatException e)
		{
			logger.warn("Failed to parse identifier '{}' as integer for choices lookup.", identifier, e);
			return null;
		}

		return null;
	}
}
