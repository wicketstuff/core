package com.googlecode.wicket.jquery.ui.samples.pages.effect;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.effect.Effect;
import com.googlecode.wicket.jquery.ui.effect.JQueryEffectContainer;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.dropdown.DropDownList;

public class ContainerEffectPage extends AbstractEffectPage
{
	private static final long serialVersionUID = 1L;

	public ContainerEffectPage()
	{
		final JQueryEffectContainer container = new JQueryEffectContainer("container") {

			private static final long serialVersionUID = 1L;

			//XXX: report as changed: protected void onComplete(AjaxRequestTarget target) > public void onEffectComplete
			@Override
			public void onEffectComplete(AjaxRequestTarget target)
			{
				this.show(target);
			}
		};

		this.add(container);

		container.add(new JQueryBehavior("#tabs", "tabs"));

		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// DropDownList //
		final DropDownList<Effect> dropdown = new DropDownList<Effect>("effects", new Model<Effect>(Effect.Explode), Arrays.asList(Effect.values()));
		form.add(dropdown);

		// Button //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				container.play(target, dropdown.getModelObject()); //Effect enum available since core 1.1
				//container.play(target, "fold"); //also available
			}
		});
	}
}
