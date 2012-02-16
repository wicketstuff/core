package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

public class RangeTextField extends TextField<Double> {

	private static final long serialVersionUID = 1L;

	private Double minimum;
	
	private Double maximum;
	
	private Double step;
	
	/**
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public RangeTextField(final String id)
	{
		this(id, null);
	}

	/**
	 * @param id
	 * @param model
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public RangeTextField(final String id, IModel<Double> model)
	{
		super(id, model);
		setRequired(true);
		setType(Double.class);
	}

	/**
	 * Processes the component tag.
	 * 
	 * @param tag
	 *            Tag to modify
	 * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);
	
		if (minimum != null) {
			tag.put("min", minimum.toString());
		}
		
		if (maximum != null) {
			tag.put("max", maximum.toString());
		}
		
		if (step != null) {
			tag.put("step", step.toString());
		}
	}

	/**
	 * @see org.apache.wicket.markup.html.form.TextField#getInputType()
	 */
	@Override
	protected String getInputType()
	{
		return "range";
	}

	/**
	 * @return the minimum
	 */
	public Double getMinimum() {
		return minimum;
	}

	/**
	 * @return the maximum
	 */
	public Double getMaximum() {
		return maximum;
	}

	/**
	 * @return the step
	 */
	public Double getStep() {
		return step;
	}

	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(Double step) {
		this.step = step;
	}
}
