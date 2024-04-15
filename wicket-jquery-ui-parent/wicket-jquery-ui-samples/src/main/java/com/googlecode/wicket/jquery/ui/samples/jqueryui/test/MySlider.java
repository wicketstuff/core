package com.googlecode.wicket.jquery.ui.samples.jqueryui.test;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.form.slider.AjaxRangeSlider;
import com.googlecode.wicket.jquery.ui.form.slider.RangeValue;

public class MySlider extends FormComponentPanel<RangeValue>
{
	private static final long serialVersionUID = 1L;

	private AjaxRangeSlider slider;
	private final String label;

	public MySlider(String id, IModel<RangeValue> model, String label)
	{
		super(id, model);

		this.label = label;
		this.initialize();
	}

	// Methods //

	private final void initialize()
	{
		this.add(new Label("label", this.label));

		final TextField<Integer> lower = new TextField<Integer>("lower", new PropertyModel<Integer>(this.getModelObject(), "lower"), Integer.class);
		this.add(lower);

		final TextField<Integer> upper = new TextField<Integer>("upper", new PropertyModel<Integer>(this.getModelObject(), "upper"), Integer.class);
		this.add(upper);

		this.slider = new AjaxRangeSlider("slider", this.getModel(), lower, upper) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				MySlider.this.onError(target);
			}

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				MySlider.this.onValueChanged(handler);
			}
		};

		this.add(this.slider);
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(MySlider.class));
	}

	@Override
	public void convertInput()
	{
		this.setConvertedInput(this.slider.getConvertedInput());
	}

	// Events //

	/**
	 * @param target
	 */
	protected void onValueChanged(IPartialPageRequestHandler handler)
	{
		// noop
	}

	/**
	 * @param target
	 */
	protected void onError(AjaxRequestTarget target)
	{
		// noop
	}
}
