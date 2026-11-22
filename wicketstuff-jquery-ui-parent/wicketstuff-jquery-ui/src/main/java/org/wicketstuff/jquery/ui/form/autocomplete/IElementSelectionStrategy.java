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

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.util.io.IClusterable;

/**
 * Strategy used to identify a selected auto-complete choice and to resolve it from the cached choice list.
 * <p>
 * The identifier is written to the JSON {@code id} field and posted back when the user selects an item.
 * {@link IndexBasedElementSelectionStrategy} is the default implementation.
 * <p>
 * Implementations must be {@link Serializable}: the strategy is stored on a Wicket component and is
 * serialized with the page/session.
 *
 * @param <T> the choice type
 * @author reiern70
 */
public interface IElementSelectionStrategy<T> extends IClusterable, Serializable
{
	/**
	 * Gets the identifier that uniquely represents the given choice in the current result set.
	 *
	 * @param choice the choice
	 * @param index the index of the choice in the current result list
	 * @return the identifier posted to the client (JSON {@code id})
	 */
	String getIdentifier(T choice, int index);

	/**
	 * Resolves the selected choice from the cached list using the identifier posted by the client.
	 *
	 * @param choices the cached list of choices
	 * @param identifier the identifier posted by the client
	 * @return the matching choice, or {@code null} if none matches
	 */
	T findChoice(List<T> choices, String identifier);
}
