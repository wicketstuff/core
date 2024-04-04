/*
 * Copyright 2012 Igor Vaynberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.select2;

import java.util.Collection;

import org.apache.wicket.model.IDetachable;

import com.github.openjson.JSONException;
import com.github.openjson.JSONStringer;

/**
 * <p>
 * Acts as a bridge between Select2 components and the application's domain model.
 * </p>
 * <p>
 * The two important services provided by implementations are:
 * <ul>
 * <li>Feeding choices, represented by the application's domain objects, to Select2 components</li>
 * <li>Transcoding said choices to and from Json so they can consumed by Select2</li>
 * </li>
 * </ul>
 * </p>
 * <p>
 * For the most common usecase where each choice is rendered as a text string see
 * {@link StringTextChoiceProvider}.
 * </p>
 *
 * @author igor
 *
 * @param <T>
 *            type of choice object
 */
public abstract class ChoiceProvider<T> implements IDetachable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Get the value for displaying to an end user.
	 *
	 * @param object
	 *            the actual object
	 * @return the value meant for displaying to an end user
	 */
	public abstract String getDisplayValue(T object);

	/**
	 * This method is called to get the id value of an object (used as the value attribute of a
	 * choice element) The id can be extracted from the object like a primary key, or if the list is
	 * stable you could just return a toString of the index.
	 * <p>
	 * Note that the given index can be {@code -1} if the object in question is not contained in the
	 * available choices.
	 *
	 * @param object
	 *            The object for which the id should be generated
	 * @return String
	 */
	public abstract String getIdValue(T object);

	/**
	 * Queries application for choices that match the search {@code term} and adds them to the
	 * {@code response}
	 *
	 * @param term
	 *            search term
	 * @param page
	 *            requested search term results page
	 * @param response
	 *            aggregate for matching choices as well as other response options
	 */
	public abstract void query(String term, int page, Response<T> response);

	/**
	 * Converts the specified choice to Json.
	 *
	 * <p>
	 * At the very least each choice should contain an {@code id} attribute. If no custom rendering
	 * function is specified, the {@code text} attribute should also be provided
	 * </p>
	 *
	 * <p>
	 * Example: If mapping a User {Long id, String name} using default rendering the code should
	 * look like this:
	 *
	 * <pre>
	 * toJson(User choice, JSONWriter writer)
	 * {
	 * 	writer.key(&quot;id&quot;).value(choice.getId()).key(&quot;text&quot;).value(choice.getName());
	 * }
	 * </pre>
	 *
	 * </p>
	 *
	 * @param choice
	 *            choice to convert
	 * @param stringer
	 *            Json writer that should be used to covnert the choice
	 * @throws JSONException
	 */
	protected void toJson(T choice, JSONStringer stringer) throws JSONException {
		stringer.key("id").value(getIdValue(choice)).key("text").value(getDisplayValue(choice));
	}

	/**
	 * Converts a list of choice ids back into application's choice objects. When the choice
	 * provider is attached to a single-select component the {@code ids} collection will contain
	 * exactly one id, and a collection containing exactly one choice should be returned.
	 *
	 * @param ids
	 *            collection containing choice ids
	 * @return collection containing application choice objects
	 */
	public abstract Collection<T> toChoices(Collection<String> ids);

	@Override
	public void detach()
	{
	}
}
