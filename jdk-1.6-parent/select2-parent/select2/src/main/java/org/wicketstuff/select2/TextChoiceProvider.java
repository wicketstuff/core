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

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONWriter;

/**
 * Takes care of Json serialization for the most common usecase where each choice is rendered as a
 * text string.
 * 
 * @author igor
 * 
 * @param <T>
 *            type of choice object
 */
public abstract class TextChoiceProvider<T> extends ChoiceProvider<T>
{
	private static final long serialVersionUID = 1L;

	protected abstract String getDisplayText(T choice);

	protected abstract Object getId(T choice);

	@Override
	public final void toJson(T choice, JSONWriter writer) throws JSONException
	{
		writer.key("id").value(getId(choice)).key("text").value(getDisplayText(choice));
	};

}
