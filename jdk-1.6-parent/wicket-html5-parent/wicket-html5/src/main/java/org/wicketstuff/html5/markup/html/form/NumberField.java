package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.markup.html.MarkupUtil;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.RangeValidator;

public class NumberField extends TextField<Double> {

	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            See Component
	 */
	public NumberField(final String id)
	{
		super(id);
		setType(Double.class);
	}

	/**
	 * @param id
	 * @param model
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public NumberField(final String id, final IModel<Double> model)
	{
		super(id, model);
		setType(Double.class);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		if (MarkupUtil.isMarkupHtml5Compliant(this)) {
			
			ValueMap attributes = getMarkupAttributes();
			Double min = attributes.getAsDouble("min");
			Double max = attributes.getAsDouble("max");
			
			if (min == null) {
				min = Double.MIN_VALUE;
			}
			
			if (max == null) {
				max = Double.MAX_VALUE;
			}
						
			add(new RangeValidator<Double>(min, max));
		}
	}

	@Override
	protected final String getInputType()
	{
		return "number";
	}
}
