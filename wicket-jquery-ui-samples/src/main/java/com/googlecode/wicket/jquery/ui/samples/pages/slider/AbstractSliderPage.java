package com.googlecode.wicket.jquery.ui.samples.pages.slider;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractSliderPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractSliderPage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultSliderPage.class, "Slider"),
				new DemoLink(InputSliderPage.class, "Slider, with input"),
				new DemoLink(OptionSliderPage.class, "Slider, with options"),
				new DemoLink(RangeSliderPage.class, "Range Slider"),
				new DemoLink(InputRangeSliderPage.class, "Range Slider, with inputs"),
				new DemoLink(AjaxSliderPage.class, "Ajax Slider"),
				new DemoLink(InputAjaxSliderPage.class, "Ajax Slider, with input"),
				new DemoLink(AjaxRangeSliderPage.class, "Ajax Range Slider"),
				new DemoLink(InputAjaxRangeSliderPage.class, "Ajax Range Slider, with inputs"),
				new DemoLink(ColorPickerPage.class, "<b>Demo:</b> Ajax Color Picker")
			);
	}
}
