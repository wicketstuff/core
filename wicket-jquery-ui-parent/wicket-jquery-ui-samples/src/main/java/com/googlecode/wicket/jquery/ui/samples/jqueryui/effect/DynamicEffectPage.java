package com.googlecode.wicket.jquery.ui.samples.jqueryui.effect;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;
import com.googlecode.wicket.jquery.ui.effect.Effect;
import com.googlecode.wicket.jquery.ui.effect.JQueryEffectBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;

public class DynamicEffectPage extends AbstractEffectPage
{
	private static final long serialVersionUID = 1L;

	public DynamicEffectPage()
	{
		this.add(new JQueryUIBehavior("#tabs", "tabs"));

		Form<Void> form = new Form<Void>("form");
		this.add(form);

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				// run effect with default speed //
				target.appendJavaScript(JQueryEffectBehavior.toString("#tabs", Effect.Explode));

				// re-display the panel //
				JQueryBehavior behavior = new JQueryUIBehavior("#tabs", "fadeIn");
				target.appendJavaScript(behavior.toString());
			}
		});
	}
}
