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
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
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

		var feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		form.add(feedback);

		var editor = new TextArea<>("text", text);
		editor.setRequired(true);
		editor.setLabel(Model.of("Text"));
		editor.add(new HugeRteBehavior(HugeRteConfig.defaults()
			.withPlugins(HugeRtePlugin.LISTS, HugeRtePlugin.LINK, HugeRtePlugin.AUTOSAVE)
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
				HugeRteBehavior.markSaved(target, editor);
				target.add(output, feedback);
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(feedback);
			}
		});

		form.add(new Button("submit")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				saved.setObject(text.getObject());
			}
		});

		add(new AjaxLink<Void>("focus")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				HugeRteBehavior.focus(target, editor);
			}
		});

		add(new AjaxLink<Void>("enable")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				editor.setEnabled(!editor.isEnabled());
				target.add(form);
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

		var second = Model.of("<p>A second editor. Change it and leave it.</p>");
		var live = new Label("live", second);
		live.setOutputMarkupId(true);

		var toggled = new WebMarkupContainer("toggled");
		toggled.setOutputMarkupPlaceholderTag(true);
		var secondEditor = new TextArea<>("second", second);
		secondEditor.add(new HugeRteBehavior(HugeRteConfig.defaults()
			.withMenubar(false)
			.withoutToolbar()
			.with("height", 200)));
		secondEditor.add(new AjaxFormComponentUpdatingBehavior("change")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				target.add(live);
			}
		});
		toggled.add(secondEditor);
		toggled.add(live);
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
