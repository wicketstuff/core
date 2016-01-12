/* Copyright (c) 2013 Martin Knopf
 * 
 * Licensed under the MIT license;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://opensource.org/licenses/MIT
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *! jQuery UI - v1.10.3 - 2013-05-30
 * http://jqueryui.com
 * Includes: jquery.ui.core.js, jquery.ui.widget.js, jquery.ui.position.js, jquery.ui.tooltip.js
 * Copyright 2013 jQuery Foundation and other contributors Licensed MIT
 */
package org.wicketstuff.jwicket.demo.tooltip;

import static org.wicketstuff.jwicket.ui.tooltip.JQueryUiTooltip.tooltip_1_10_3;
import static org.wicketstuff.jwicket.ui.tooltip.JQueryUiTooltipContent.tooltipContent;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;
import org.wicketstuff.jwicket.JsOption;
import org.wicketstuff.jwicket.ui.tooltip.JQueryUiTooltip;

/**
 * 
 * @author Martin Knopf
 * 
 */
public class JQueryUiTooltipPage extends WebPage
{

	public static final CssResourceReference uiTooltipCss = new CssResourceReference(
		JQueryUiTooltipPage.class, "jquery-ui-1.10.3.custom.css");

	public JQueryUiTooltipPage()
	{
		super();

		// jQuery 1.6+ required for the jQueryUi tooltip widget

		Component individualTooltip = new WebMarkupContainer("individualTooltip");
		individualTooltip.add(tooltip_1_10_3() /**/
		.setContent("tooltip content") /**/
		.setItems("#" + individualTooltip.getMarkupId()) /**/
		.setPosition(new JsOption("my", "'center bottom-20'"), new JsOption("at", "'left top'")) /**/
		.addCssResource(uiTooltipCss));
		add(individualTooltip);

		// make multiple tooltips look the same by providing a CSS class selector to the tooltip
		add(tooltip_1_10_3(".textWithTooltip") /**/
		.setItems(".textWithTooltip") /**/
		.setPosition(new JsOption("my", "'center bottom-20'"), new JsOption("at", "'center top'")) /**/
		);

		add(new WebMarkupContainer("tooltipFromTitle"));
		add(new WebMarkupContainer("tooltipFromDataTooltip"));
		add(new WebMarkupContainer("tooltipWithMarkup").add(tooltipContent("<strong>tooltip content with markup</strong>")));

		TooltipContent tooltipContent = new TooltipContent("tooltipContent");
		add(tooltipContent);
		add(new WebMarkupContainer("tooltipFromComponent").add(tooltipContent(tooltipContent)));
	}
}
