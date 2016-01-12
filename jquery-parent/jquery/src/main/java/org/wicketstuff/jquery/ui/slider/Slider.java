package org.wicketstuff.jquery.ui.slider;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * An integration of JQuery UI Slider widget (http://docs.jquery.com/UI/Slider/slider)
 * 
 * @author Martin Grigorov <martingrigorov @ users.sf.net>
 */
public abstract class Slider extends Panel
{

	private static final long serialVersionUID = 1L;

	private final SliderOptions sliderOptions;

	public Slider(final String wid, final SliderOptions sliderOptions,
		final SliderHandleOptions... sliderHandlesOptions)
	{
		super(wid);

		setOutputMarkupId(true);

		this.sliderOptions = sliderOptions;

		this.sliderOptions.setHandles(sliderHandlesOptions);

		final RepeatingView sliderHandles = new RepeatingView("sliderHandles");
		sliderHandles.setRenderBodyOnly(true);
		add(sliderHandles);

		if (sliderHandlesOptions != null && sliderHandlesOptions.length > 0)
		{

			for (int i = 0; i < sliderHandlesOptions.length; i++)
			{

				final SliderHandleOptions sliderHandleSettings = sliderHandlesOptions[i];

				final SliderHandle sliderHandle = new SliderHandle(sliderHandles.newChildId(),
					sliderHandleSettings);

				sliderHandles.add(sliderHandle);
			}
		}
		else
		{
			final SliderHandle sliderHandle = new SliderHandle("handle");
			sliderHandles.add(sliderHandle);
		}

		add(new SliderBehavior());
	}

	public SliderOptions getOptions()
	{
		return sliderOptions != null ? sliderOptions : new SliderOptions();
	}

	/**
	 * A callback method which will be called when any of the handles change its value
	 * 
	 * @param target
	 *            the current request target
	 * @param handleId
	 *            the HTML id of the handle that triggered this event
	 * @param newValue
	 *            the value of this handle
	 */
	public abstract void onChange(final AjaxRequestTarget target, final String handleId,
		final int newValue);

}
