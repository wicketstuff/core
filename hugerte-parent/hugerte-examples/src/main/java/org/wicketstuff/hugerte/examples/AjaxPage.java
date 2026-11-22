/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.hugerte.examples;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.wicketstuff.hugerte.HugeRteBehavior;
import org.wicketstuff.hugerte.HugeRteConfig;
import org.wicketstuff.hugerte.HugeRtePlugin;

/**
 * Saves the editor with a plain {@link AjaxButton} and re-renders the editor with Ajax.
 */
public class AjaxPage extends BasePage
{
	private static final long serialVersionUID = 1L;

	public AjaxPage()
	{
		var text = Model.of("<p>Change this text and press <em>Save</em>.</p>");
		var saved = Model.of("");

		var form = new Form<Void>("form");
		form.setOutputMarkupId(true);
		add(form);

		var editor = new TextArea<>("text", text);
		editor.add(new HugeRteBehavior(HugeRteConfig.defaults()
			.withPlugins(HugeRtePlugin.LISTS, HugeRtePlugin.LINK)
			.withToolbar("undo redo | bold italic | bullist numlist | link")
			.withMenubar(false)
			.with("height", 250)));
		form.add(editor);

		var output = new Label("saved", saved);
		output.setOutputMarkupId(true);
		add(output);

		form.add(new AjaxButton("save")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				saved.setObject(text.getObject());
				target.add(output);
			}
		});

		add(new AjaxLink<Void>("rerender")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				target.add(form);
			}
		});

		var toggled = new WebMarkupContainer("toggled");
		toggled.setOutputMarkupPlaceholderTag(true);
		toggled.add(new TextArea<>("second", Model.of("<p>A second editor.</p>"))
			.add(new HugeRteBehavior(HugeRteConfig.defaults().withMenubar(false))));
		add(toggled);

		add(new AjaxLink<Void>("toggle")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				toggled.setVisible(!toggled.isVisible());
				target.add(toggled);
			}
		});
	}
}
