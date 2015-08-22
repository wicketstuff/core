package org.wicketstuff.jquery.demo.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.jquery.demo.PageSupport;
import org.wicketstuff.jquery.ui.slider.Slider;
import org.wicketstuff.jquery.ui.slider.SliderHandleOptions;
import org.wicketstuff.jquery.ui.slider.SliderOptions;

/**
 * A demo page for {@link Slider Slider} component
 * 
 * Demonstrates how to create and use a slider with two handles
 * 
 * @author Martin Grigorov <martingrigorov @ users.sf.net>
 */
public class Page4Slider extends PageSupport
{

	private static final long serialVersionUID = 1L;

	public Page4Slider() throws Exception
	{
		super();

		final SliderHandleOptions sliderOneHandleSettings = new SliderHandleOptions("uno", 0);

		final SliderHandleOptions sliderTwoHandleSettings = new SliderHandleOptions("dos", 50);
		sliderTwoHandleSettings.setStyle("left: 188px");

		final SliderOptions sliderOptions = new SliderOptions();

		sliderOptions.setStepping(10);

		sliderOptions.setMin(0);

		sliderOptions.setMax(100);

		sliderOptions.setRange(true);

		// sliderOptions.setOnChange("console.log('ui: ' + ui.toSource());", "e", "ui");

		@SuppressWarnings("serial")
		final Slider slider = new Slider("slider", sliderOptions, sliderOneHandleSettings,
			sliderTwoHandleSettings)
		{

			@Override
			public void onChange(AjaxRequestTarget target, String handleId, int value)
			{
				info(String.format("Handle with id '%s' changed its value to '%d'", handleId, value));
				target.addChildren(getPage(), FeedbackPanel.class);
			}

		};
		add(slider);

	}
}