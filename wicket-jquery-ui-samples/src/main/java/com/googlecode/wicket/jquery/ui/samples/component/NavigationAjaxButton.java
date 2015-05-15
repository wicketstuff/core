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

		this.backwardButton = this.newBackwardButton();
		this.add(this.backwardButton);

		this.forwardButton = this.newForwardButton();
		this.add(this.forwardButton);

		this.setOutputMarkupId(true);
	}

	// Properties //
	public final AjaxButton getBackwardButton()
	{
		return this.backwardButton;
	}

	public final AjaxButton getForwardButton()
	{
		return this.forwardButton;
	}

	// Factories //
	private final AjaxButton newBackwardButton()
	{
		return new AjaxButton("backward") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
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

	private final AjaxButton newForwardButton()
	{
		return new AjaxButton("forward") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
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

	/**
	 * @param target
	 * @param button
	 */
	protected void onBackward(AjaxRequestTarget target, AjaxButton button)
	{
		// noop
	}

	/**
	 * @param target
	 * @param button
	 */
	protected void onForward(AjaxRequestTarget target, AjaxButton button)
	{
		// noop
	}
}
