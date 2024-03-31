package org.wicketstuff.html5.markup.html;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * Component representing HTMLElement {@literal progress}.
 * 
 * @see https://developer.mozilla.org/en/HTML/Element/progress
 */
public class Progress extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	/**
	 * The maximum allowed value for the progress.
	 * <p>
	 * Only positive numbers are allowed.
	 * </p>
	 */
	private final IModel<? extends Number> max;

	/**
	 * The current value of the progress.
	 * <p>
	 * Only positive numbers are allowed, less than the {@link #max maximum} value.
	 * </p>
	 */
	private final IModel<? extends Number> current;

	/**
	 * Construct.
	 * 
	 * @param id
	 *            the component id
	 * @param current
	 *            the model that brings the current value
	 * @param max
	 *            the model that brings the maximum value
	 */
	public Progress(String id, IModel<? extends Number> current, IModel<? extends Number> max)
	{
		super(id);

		this.current = current;
		this.max = max;
	}
	
	/**
	 * Constructs an indeterminate progress.
	 * 
	 * @param id
	 *            the component id
	 */
	public Progress(String id)
	{
		this(id,null,null);
	}

	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);

		checkComponentTag(tag, "progress");
		if(isDeterminate())
		{
			tag.put("value", String.valueOf(getValue()));
			tag.put("max", String.valueOf(getMax()));
		}
	}

	@Override
	public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag)
	{
		replaceComponentTagBody(markupStream, openTag, getBody());
	}

	/**
	 * @return the content of the tag's body
	 */
	protected CharSequence getBody()
	{
		if(isDeterminate())
		{
			double currentValue = getValue().doubleValue();
			double maxValue = getMax().doubleValue();
	
			Double percentage = maxValue == 0d ? maxValue : ((currentValue / maxValue) * 100);
			return percentage.intValue() + " %";
		}
		else
		{
			return "";
		}
	}

	private Number getValue()
	{
		Number value = current.getObject();

		if (value == null || value.doubleValue() < 0d)
		{
			value = 0d;
		}

		Number max = getMax();
		if (value.doubleValue() > max.doubleValue())
		{
			value = max;
		}

		return value;
	}

	private Number getMax()
	{
		Number maxValue = max.getObject();

		if (maxValue == null || maxValue.doubleValue() < 0d)
		{
			maxValue = 0d;
		}

		return maxValue;
	}
	
	/**
	 * Returns {@code false} if this is an indeterminate progress.
	 * 
	 * @return {@code true} if this is a determinate progress
	 */
	public boolean isDeterminate() {
		return ((max!=null)&&(current!=null));
	}
}
