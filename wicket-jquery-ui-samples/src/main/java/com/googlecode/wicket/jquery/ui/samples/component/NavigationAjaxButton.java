package com.googlecode.wicket.jquery.ui.samples.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;

public class NavigationAjaxButton extends Panel
{
	private static final long serialVersionUID = 1L;
	private final AjaxButton backwardButton;
	private final AjaxButton forwardButton;

	public NavigationAjaxButton(String id)
	{
		super(id);

		this.setOutputMarkupId(true);
		this.add(this.backwardButton = this.newBackwardButton());
		this.add(this.forwardButton = this.newForwardButton());
	}

	// Properties //
	public AjaxButton getBackwardButton()
	{
		return this.backwardButton;
	}

	public AjaxButton getForwardButton()
	{
		return this.forwardButton;
	}

	// Factories //
	private AjaxButton newBackwardButton()
	{
		return new AjaxButton("backward") {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryIcon getIcon()
			{
				return JQueryIcon.CIRCLE_TRIANGLE_W;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				NavigationAjaxButton.this.onBackward(target, this);
			}
		};
	}


	private AjaxButton newForwardButton()
	{
		return new AjaxButton("forward") {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryIcon getIcon()
			{
				return JQueryIcon.CIRCLE_TRIANGLE_E;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				NavigationAjaxButton.this.onForward(target, this);
			}
		};
	}

	protected void onBackward(AjaxRequestTarget target, AjaxButton button)
	{
	}

	protected void onForward(AjaxRequestTarget target, AjaxButton button)
	{
	}
}
