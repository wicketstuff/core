package org.wicketstuff.jquery.ui.slider;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * An integration of JQuery UI Slider widget (http://docs.jquery.com/UI/Slider/slider)
 * 
 * @author Martin Grigorov <martingrigorov @ users.sf.net>
 */
public class SliderHandle extends Panel
{

	private static final long serialVersionUID = 1L;

	public SliderHandle(final String wid)
	{
		this(wid, null);
	}

	public SliderHandle(final String wid, final SliderHandleOptions handleSettings)
	{
		super(wid);

		setRenderBodyOnly(true);

		final WebMarkupContainer handle = new WebMarkupContainer("sliderHandle");
		handle.setOutputMarkupId(true);

		if (handleSettings != null)
			handle.setMarkupId(handleSettings.getId());

		add(handle);

		if (handleSettings != null)
		{
			if (handleSettings.getStyle() != null)
			{
				handle.add(new AttributeAppender("style", Model.of(handleSettings.getStyle()), " "));
			}
		}
	}

}
