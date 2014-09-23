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
 */
package org.wicketstuff.jwicket.ui.tooltip;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

/**
 * Use this {@link Behavior} to place text/markup in the HTML attribute <code>data-tooltip</code> of
 * a component. This attribute is used by {@link JQueryUiTooltip} to obtain the content of tooltips.
 * <p/>
 * If you specify a {@link Component} as the content provider, the <code>data-tooltip</code>
 * attribute will be set by a JavaScript function.
 * 
 * @author Martin Knopf
 * 
 */
public class JQueryUiTooltipContent extends Behavior
{

	private String content;
	private Component contentComponent;

	/**
	 * 
	 * @param content
	 *            the value of the {@code data-tooltip} attribute. Markup will not be escaped by
	 *            jQuery UI.
	 */
	public JQueryUiTooltipContent(String content)
	{
		super();
		this.content = content;
	}

	/**
	 * 
	 * @param contentComponent
	 *            the component, the value of the {@code data-tooltip} attribute will be obtained
	 *            from
	 */
	public JQueryUiTooltipContent(Component contentComponent)
	{
		super();
		this.contentComponent = contentComponent;
	}

	/**
	 * Factory method.
	 * 
	 * @param content
	 *            the value of the {@code data-tooltip} attribute. Markup will not be escaped by
	 *            jQuery UI.
	 * @return a new instance of {@link JQueryUiTooltipContent}
	 */
	public static JQueryUiTooltipContent tooltipContent(String content)
	{
		return new JQueryUiTooltipContent(content);
	}

	/**
	 * Factory method.
	 * 
	 * @param contentComponent
	 *            the component, the value of the {@code data-tooltip} attribute will be obtained
	 *            from
	 * @return a new instance of {@link JQueryUiTooltipContent}
	 */
	public static JQueryUiTooltipContent tooltipContent(Component contentComponent)
	{
		return new JQueryUiTooltipContent(contentComponent);
	}

	@Override
	public void onComponentTag(Component component, ComponentTag tag)
	{
		super.onComponentTag(component, tag);

		if (this.content != null && this.contentComponent == null)
		{
			tag.append("data-tooltip", this.content, "");
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		if (this.content == null && this.contentComponent != null)
		{
			response.render(new OnDomReadyHeaderItem("$('#" + component.getMarkupId() +
				"').attr('data-tooltip',$('#" + this.contentComponent.getMarkupId() + "').html());"));
		}
	}
}
