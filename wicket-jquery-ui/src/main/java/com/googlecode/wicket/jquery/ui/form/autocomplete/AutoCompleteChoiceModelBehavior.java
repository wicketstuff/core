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

import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestParameters;

import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.BuilderUtils;

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

	public AutoCompleteChoiceModelBehavior(ITextRenderer<? super T> renderer, IJQueryTemplate template)
	{
		super(renderer, template);
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		StringBuilder builder = new StringBuilder("[ ");

		List<T> choices = this.getChoices();

		if (choices != null)
		{
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
				BuilderUtils.append(builder, "value", this.renderer.getText(choice)); /* 'value' is a reserved word */
				builder.append(", ");

				// ITextRenderer //
				builder.append(this.renderer.render(choice)); // #198

				// Additional properties (like template properties) //
				List<String> properties = AutoCompleteChoiceModelBehavior.this.getProperties();

				for (String property : properties)
				{
					builder.append(", ");
					BuilderUtils.append(builder, property, this.renderer.getText(choice, property));
				}

				builder.append(" }");
			}
		}

		return builder.append(" ]").toString();
	}
}
