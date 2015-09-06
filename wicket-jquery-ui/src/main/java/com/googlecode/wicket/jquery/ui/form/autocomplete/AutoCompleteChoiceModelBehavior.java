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
package com.googlecode.wicket.jquery.ui.form.autocomplete;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.http.WebResponse;

import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.utils.BuilderUtils;
import com.googlecode.wicket.jquery.core.utils.ListUtils;

/**
 * Provides the {@link AbstractAjaxBehavior} for the {@link AutoCompleteTextField}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
abstract class AutoCompleteChoiceModelBehavior<T> extends ChoiceModelBehavior<T>
{
	private static final long serialVersionUID = 1L;

	public AutoCompleteChoiceModelBehavior(ITextRenderer<? super T> renderer)
	{
		super(renderer);
	}

	@Override
	protected IRequestHandler newRequestHandler()
	{
		return new AutoCompleteChoiceModelRequestHandler();
	}

	/**
	 * Provides the {@link IRequestHandler}
	 */
	protected class AutoCompleteChoiceModelRequestHandler implements IRequestHandler
	{
		@Override
		public void respond(final IRequestCycle requestCycle)
		{
			WebResponse response = (WebResponse) requestCycle.getResponse();

			final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
			response.setContentType("application/json; charset=" + encoding);
			response.disableCaching();

			List<T> choices = AutoCompleteChoiceModelBehavior.this.getChoices();

			if (choices != null)
			{
				StringBuilder builder = new StringBuilder("[ ");

				int index = 0;
				for (T choice : choices)
				{
					if (index++ > 0)
					{
						builder.append(", ");
					}

					builder.append("{ ");
					BuilderUtils.append(builder, "id", Integer.toString(index)); /* 'id' is a reserved word */
					builder.append(", ");
					BuilderUtils.append(builder, "value", renderer.getText(choice)); /* 'value' is a reserved word */

					// Additional properties (like template properties); see #198 //
					List<String> properties = ListUtils.exclude(AutoCompleteChoiceModelBehavior.this.getProperties(), renderer.getTextField());

					for (String property : properties)
					{
						builder.append(", ");
						BuilderUtils.resolve(builder, choice, property);
					}

					builder.append(" }");
				}

				response.write(builder.append(" ]"));
			}
		}

		@Override
		public void detach(final IRequestCycle requestCycle)
		{
			// noop
		}
	}
}
