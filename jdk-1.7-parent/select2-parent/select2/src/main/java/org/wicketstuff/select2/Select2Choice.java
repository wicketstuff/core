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
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.select2.json.JsonBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Single-select Select2 component. Should be attached to a
 * {@code <input type='hidden'/>} element.
 *
 * @param <T> type of choice object
 * @author igor
 */
public class Select2Choice<T> extends AbstractSelect2Choice<T, T> {

	private static final long serialVersionUID = 1L;

	public Select2Choice(String id, IModel<T> model, List<T> choices, ChoiceRenderer<T> renderer) {
		super(id, model, choices, renderer);
	}

	public Select2Choice(String id, IModel<T> model, ChoiceProvider<T> provider) {
		super(id, model, provider);
	}

	public Select2Choice(String id, IModel<T> model) {
		super(id, model);
	}

	public Select2Choice(String id) {
		super(id);
	}

	@Override
	public void convertInput() {
		String input = getWebRequest().getRequestParameters().getParameterValue(getInputName()).toString();
		if (Strings.isEmpty(input)) {
			setConvertedInput(null);
		} else {
			if (isAjax()) {
				setConvertedInput(getProvider().toChoices(Collections.singleton(input)).iterator().next());
			} else {
				for (int i = 0; i < getChoices().size(); i++) {
					T item = getChoices().get(i);
					if (input.equals(getRenderer().getIdValue(item, i))) {
						setConvertedInput(item);
						break;
					}
				}
			}
		}
	}

	@Override
	protected void renderInitializationScript(IHeaderResponse response) {
		T value;
		if (getWebRequest().getRequestParameters().getParameterNames().contains(getInputName())) {
			convertInput();
			value = getConvertedInput();
		} else {
			value = getModelObject();
		}
		if (value != null) {

			JsonBuilder selection = new JsonBuilder();
			try {
				selection.object();
				if (isAjax()) {
					getProvider().toJson(value, selection);
				} else {
					renderChoice(value, selection);
				}
				selection.endObject();
			} catch (JSONException e) {
				throw new RuntimeException("Error converting model object to Json", e);
			}
			response.render(
					OnDomReadyHeaderItem.forScript(JQuery.execute("$('#%s').select2('data', %s);", getJquerySafeMarkupId(),
							selection.toJson())));
		}
	}
}
