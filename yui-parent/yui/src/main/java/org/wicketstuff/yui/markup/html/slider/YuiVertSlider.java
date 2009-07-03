/*
 * $Id: Slider.java 5132 2006-03-26 02:13:41 -0800 (Sun, 26 Mar 2006)
 * jdonnerstag $ $Revision: 5189 $ $Date: 2006-03-26 02:13:41 -0800 (Sun, 26 Mar
 * 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.markup.html.slider;

import org.apache.wicket.RequestCycle;
import org.wicketstuff.yui.helper.CSSInlineStyle;
import org.wicketstuff.yui.helper.ImageResourceInfo;

/**
 * Vertical Slider
 * 
 * @author josh
 * 
 */
public class YuiVertSlider extends YuiSlider
{
	private static final long serialVersionUID = 1L;

	public YuiVertSlider(String id, YuiSliderSettings settings)
	{
		super(id, settings);
	}

	@Override
	protected String getSliderTypJS()
	{
		return "YAHOO.widget.Slider.getVertSlider";
	}

	@Override
	protected String getBackgroundStyle()
	{
		YuiSliderSettings settings = getSliderSettings();

		ImageResourceInfo bgInfo = new ImageResourceInfo(settings.getBackgroundResource());
		ImageResourceInfo thumbInfo = new ImageResourceInfo(settings.getThumbResource());

		int width = bgInfo.getWidth();
		int thumbHeight = thumbInfo.getHeight();
		int bgLength = settings.getLength();

		String height = Integer.toString(bgLength - thumbHeight);

		CSSInlineStyle background = new CSSInlineStyle();
		background.add("height", height + "px");
		background.add("width", width + "px");
		background.add("background", "url("
				+ RequestCycle.get().urlFor(settings.getBackgroundResource()) + ") repeat-y");

		return background.getStyle();
	}

	@Override
	protected String getThumbStyle()
	{
		YuiSliderSettings settings = getSliderSettings();
		ImageResourceInfo thumbInfo = new ImageResourceInfo(settings.getThumbResource());
		CSSInlineStyle thumb = new CSSInlineStyle();
		thumb.add("height", thumbInfo.getHeight() + "px");
		thumb.add("top", 0 - thumbInfo.getHeight() + "px");
		return thumb.getStyle();
	}

	@Override
	protected String getLeftUpStyle()
	{
		YuiSliderSettings settings = getSliderSettings();
		ImageResourceInfo leftUp = new ImageResourceInfo(settings.getLeftUpResource());
		CSSInlineStyle background = new CSSInlineStyle();
		background.add("background", "url("
				+ RequestCycle.get().urlFor(settings.getLeftUpResource()) + ") no-repeat");
		background.add("height", leftUp.getHeight() + "px");
		return background.getStyle();
	}

	@Override
	protected String getRightDownStyle()
	{
		YuiSliderSettings settings = getSliderSettings();
		ImageResourceInfo rightDown = new ImageResourceInfo(settings.getRightDownResource());
		CSSInlineStyle background = new CSSInlineStyle();
		background.add("background", "url("
				+ RequestCycle.get().urlFor(settings.getRightDownResource()) + ") no-repeat");
		background.add("height", rightDown.getHeight() + "px");
		return background.getStyle();
	}

	@Override
	protected String getCssClass()
	{
		return "yuislider-vert";
	}
}
