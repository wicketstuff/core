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

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.utils.RendererUtils;

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
		return new IRequestHandler() {

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
						builder.append(Options.QUOTE).append("id").append(Options.QUOTE).append(": ").append(Options.QUOTE).append(Integer.toString(index)).append(Options.QUOTE); /* id is a reserved word */
						builder.append(", ");
						builder.append(Options.QUOTE).append("value").append(Options.QUOTE).append(": ").append(Options.QUOTE).append(renderer.getText(choice)).append(Options.QUOTE); /* value is a reserved word */

						for (String property : AutoCompleteChoiceModelBehavior.this.getProperties())
						{
							builder.append(", ");
							builder.append(RendererUtils.getJsonBody(choice, renderer, property));
						}

						builder.append(" }");
					}

					builder.append(" ]");

					response.write(builder);
				}
			}

			@Override
			public void detach(final IRequestCycle requestCycle)
			{
			}
		};
	}
}
