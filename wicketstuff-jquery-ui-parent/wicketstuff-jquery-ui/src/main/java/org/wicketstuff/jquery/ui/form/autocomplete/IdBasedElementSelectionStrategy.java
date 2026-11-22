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
import java.util.Objects;

/**
 * {@link IElementSelectionStrategy} that identifies choices by an id value.
 * Subclasses implement {@link #getValue(Object, int)} to extract that id.
 *
 * @param <T> the choice type
 * @author reiern70
 */
public abstract class IdBasedElementSelectionStrategy<T> implements IElementSelectionStrategy<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the id value that uniquely represents the given choice.
	 *
	 * @param choice the choice
	 * @param index the index of the choice in the current result list
	 * @return the id value, or {@code null} if none
	 */
	public abstract String getValue(T choice, int index);

	@Override
	public String getIdentifier(T choice, int index)
	{
		return this.getValue(choice, index);
	}

	@Override
	public T findChoice(List<T> choices, String identifier)
	{
		if (choices == null || identifier == null)
		{
			return null;
		}

		for (int index = 0; index < choices.size(); ++index)
		{
			T choice = choices.get(index);

			if (Objects.equals(identifier, this.getValue(choice, index)))
			{
				return choice;
			}
		}

		return null;
	}
}
