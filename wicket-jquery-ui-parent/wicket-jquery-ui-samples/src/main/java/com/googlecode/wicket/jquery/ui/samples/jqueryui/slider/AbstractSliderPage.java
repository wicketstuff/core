package com.googlecode.wicket.jquery.ui.samples.jqueryui.slider;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractSliderPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultSliderPage.class, "Slider"), // lf
				new DemoLink(InputSliderPage.class, "Slider, with input"), // lf
				new DemoLink(OptionSliderPage.class, "Slider, with options"), // lf
				new DemoLink(RangeSliderPage.class, "Range Slider"), // lf
				new DemoLink(InputRangeSliderPage.class, "Range Slider, with inputs"), // lf
				new DemoLink(AjaxSliderPage.class, "Ajax Slider"), // lf
				new DemoLink(InputAjaxSliderPage.class, "Ajax Slider, with input"), // lf
				new DemoLink(AjaxRangeSliderPage.class, "Ajax Range Slider"), // lf
				new DemoLink(InputAjaxRangeSliderPage.class, "Ajax Range Slider, with inputs"), // lf
				new DemoLink(ColorPickerPage.class, "<b>Demo:</b> Ajax Color Picker") // lf
		);
	}
}
