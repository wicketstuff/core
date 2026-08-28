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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event listener shared by the {@link AutoCompleteTextField} widget and the {@link AutoCompleteBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IAutoCompleteListener<T> extends IClusterable
{
	Logger LOG = LoggerFactory.getLogger(IAutoCompleteListener.class);

	/**
	 * Gets the {@link IElementSelectionStrategy} used to identify a selected choice and to resolve it from the cached choice list.
	 *
	 * @return the {@link IElementSelectionStrategy}
	 */
	IElementSelectionStrategy<T> getElementSelectionStrategy();

	/**
	 * Triggered when a selection has been made
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param choice the selected choice, or {@code null} if the identifier could not be resolved
	 * @param identifier the identifier of the selected item (JSON {@code id}; list index by default, or a business id)
	 */
	void onSelect(AjaxRequestTarget target, T choice, String identifier);

	/**
	 * Triggered when a selection has been made, using the identifier posted by the client.
	 * <p>
	 * The default implementation resolves the choice from {@code choiceList} via
	 * {@link #getElementSelectionStrategy()} and {@link IElementSelectionStrategy#findChoice(List, String)},
	 * then delegates to {@link #onSelect(AjaxRequestTarget, Object, String)}.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param choiceList the cached list of choices matching the last query
	 * @param identifier the identifier of the selected item (JSON {@code id}; list index by default, or a business id)
	 */
	default void onSelect(AjaxRequestTarget target, List<T> choiceList, String identifier)
	{
        T choice = getElementSelectionStrategy().findChoice(choiceList, identifier);
        onSelect(target, choice, identifier);
	}
}
