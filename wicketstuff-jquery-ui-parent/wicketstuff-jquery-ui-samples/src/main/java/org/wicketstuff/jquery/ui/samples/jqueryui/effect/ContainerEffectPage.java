/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.effect;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.ui.JQueryUIBehavior;
import org.wicketstuff.jquery.ui.effect.Effect;
import org.wicketstuff.jquery.ui.effect.JQueryEffectContainer;
import org.wicketstuff.jquery.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.form.dropdown.DropDownList;

public class ContainerEffectPage extends AbstractEffectPage
{
	private static final long serialVersionUID = 1L;

	public ContainerEffectPage()
	{
		final JQueryEffectContainer container = new JQueryEffectContainer("container") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEffectComplete(AjaxRequestTarget target)
			{
				this.show(target);
			}
		};

		this.add(container.add(new JQueryUIBehavior("#tabs", "tabs")));

		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// DropDownList //
		final DropDownList<Effect> dropdown = new DropDownList<Effect>("effects", Model.of(Effect.Explode), Arrays.asList(Effect.values()));
		form.add(dropdown);

		// Button //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				container.play(target, dropdown.getModelObject()); // Effect enum available since core 1.1
				// container.play(target, "fold"); //also available
			}
		});
	}
}
