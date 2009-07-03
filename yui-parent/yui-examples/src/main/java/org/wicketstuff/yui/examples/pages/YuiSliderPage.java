/*
 * $Id: SliderPage.java 4820 2006-03-08 08:21:01Z eelco12 $ $Revision: 4820 $
 * $Date: 2006-03-08 16:21:01 +0800 (Wed, 08 Mar 2006) $
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
package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.slider.YuiHorizSlider;
import org.wicketstuff.yui.markup.html.slider.YuiSliderSettings;
import org.wicketstuff.yui.markup.html.slider.YuiVertSlider;

public class YuiSliderPage extends WicketExamplePage
{
	/**
	 * Construct.
	 */

	Label label3;

	TextField<String> text3;

	int value;

	public YuiSliderPage()
	{
		add(new YuiHorizSlider("hslider1", YuiSliderSettings.getHorizDefault(300, 20, 150)));
		add(new YuiHorizSlider("hslider2", YuiSliderSettings.getHorizDefault(150, 1, 0)));
		add(new YuiVertSlider("vslider1", YuiSliderSettings.getVertDefault(300, 10, 150)));
		add(new YuiVertSlider("vslider2", YuiSliderSettings.getVertDefault(200, 1, 73)));

		// customise
		add(new YuiHorizSlider("hslider3", YuiSliderSettings.getHorizDefault(200, 20, 100))
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected String getOnChangeJSFunc()
			{
				StringBuffer buffy = new StringBuffer();
				buffy.append("function(offsetFromStart) { ");

				// show pixel on label
				buffy.append("YAHOO.util.Dom.get('").append(label3.getMarkupId()).append(
						"').innerHTML = offsetFromStart;");

				// calculate using javascript and populate text field
				buffy.append("YAHOO.util.Dom.get('").append(text3.getMarkupId()).append(
						"').value = Math.round((offsetFromStart - 100)/25);");

				buffy.append("}");
				return buffy.toString();
			}
		});

		add(label3 = new Label("slider3Label", ""));
		label3.setOutputMarkupId(true);

		add(text3 = new TextField<String>("slider3Text", new PropertyModel<String>(this, "value")));
		text3.setOutputMarkupId(true);

	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}
}
