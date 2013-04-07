package com.googlecode.wicket.jquery.ui.samples.pages.test;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

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
		this.init();
	}

	private void init()
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
			public void onValueChanged(AjaxRequestTarget target)
			{
				MySlider.this.onValueChanged(target);
			}
		};

		this.add(this.slider);
	}

	@Override
	protected void convertInput()
	{
		this.setConvertedInput(this.slider.getConvertedInput());
	}

	// Events //
	protected void onValueChanged(AjaxRequestTarget target)
	{

	}

	protected void onError(AjaxRequestTarget target)
	{

	}
}
