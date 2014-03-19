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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryDurableAjaxBehavior;
import org.wicketstuff.jwicket.JQueryResourceReference;
import org.wicketstuff.jwicket.JQueryResourceReferenceType;
import org.wicketstuff.jwicket.JsOption;

/**
 * This is an integration of the jQuery UI tooltip widget. You can add it to a Wicket
 * {@link Component} to give it a jQuery UI tooltip. This {@link Behavior} will generate the tooltip
 * JavaScript.
 * <p/>
 * adf
 * <p/>
 * <strong>Default selector: </strong>the component's markup id
 * <p/>
 * <strong>Default tooltip content: </strong>value(s) of the <code>title</code> attribute(s) or the
 * <code>data-tolltip</code> attribute(s) dynamically obtained by a JS function
 * <p/>
 * You can use custom CSS through {@link ResourceReference}s. If no user {@link ResourceReference}
 * for CSS is provided a default style will be used.
 * <p/>
 * An instance of this class can be added to one and only one {@link Component}. Another
 * {@link Component} that should have exactly the same behavior needs it's own instance.
 * 
 * @author Martin Knopf
 * 
 */
public class JQueryUiTooltip extends JQueryDurableAjaxBehavior
{
	private static final String WIDGET_NAME = "tooltip";

	public static final JQueryResourceReference uiTooltipJs_1_10_3 = JQuery.isDebug()
		? new JQueryResourceReference(JQueryUiTooltip.class, "jquery-ui-1.10.3.tooltip.js",
			JQueryResourceReferenceType.NOT_OVERRIDABLE) : new JQueryResourceReference(
			JQueryUiTooltip.class, "jquery-ui-1.10.3.tooltip.min.js",
			JQueryResourceReferenceType.NOT_OVERRIDABLE);

	public static final CssResourceReference uiTooltipCss_1_10_3 = JQuery.isDebug()
		? new CssResourceReference(JQueryUiTooltip.class, "jquery-ui-1.10.3.custom.css")
		: new CssResourceReference(JQueryUiTooltip.class, "jquery-ui-1.10.3.custom.min.css");

	private final String componentSelector;
	private final String defaultContent = "function(){" /**/
		+ "var title=$(this).attr('title');" /**/
		+ "if(typeof title!=='undefined' && title!==false){return title;}" /**/
		+ "else{return $(this).attr('data-tooltip');}" /**/
		+ "}";
	private final List<ResourceReference> cssResourceReferences;
	private boolean withoutCss;
	private final JQueryUiWidget widget;


	/**
	 * 
	 * @param customJQueryUiTooltipJs
	 *            a {@link JQueryResourceReference} which should provide all jQuery UI libraries
	 *            needed to use the jQuery UI tooltip widget
	 */
	public JQueryUiTooltip(JQueryResourceReference customJQueryUiTooltipJs)
	{
		this("", customJQueryUiTooltipJs);
	}

	/**
	 * 
	 * @param componentSelector
	 *            the selector which will be used by jQuery to add the tooltip(s)
	 * @param customJQueryUiTooltipJs
	 *            a {@link JQueryResourceReference} which should provide all jQuery UI libraries
	 *            needed to use the jQuery UI tooltip widget
	 */
	public JQueryUiTooltip(String componentSelector, JQueryResourceReference customJQueryUiTooltipJs)
	{
		super(customJQueryUiTooltipJs);
		this.componentSelector = componentSelector;
		this.widget = new JQueryUiWidget(WIDGET_NAME);
		this.cssResourceReferences = new ArrayList<ResourceReference>();
		addUserProvidedResourceReferences(org.apache.wicket.resource.JQueryResourceReference.get());
		setDefaultOptions();
	}

	/**
	 * Constructor for testing.
	 * 
	 * @param widget
	 */
	protected JQueryUiTooltip(JQueryUiWidget widget)
	{
		super(uiTooltipJs_1_10_3);
		this.componentSelector = "";
		this.widget = widget;
		this.cssResourceReferences = new ArrayList<ResourceReference>();
		addUserProvidedResourceReferences(org.apache.wicket.resource.JQueryResourceReference.get());
		setDefaultOptions();
	}

	/**
	 * Factory method.
	 * 
	 * @return a new instance of {@link JQueryUiTooltip} using jQuery UI 1.10.3
	 */
	public static JQueryUiTooltip tooltip_1_10_3()
	{
		return new JQueryUiTooltip(uiTooltipJs_1_10_3);
	}

	/**
	 * 
	 * Factory method.
	 * 
	 * @param componentSelector
	 *            the selector which will be used by jQuery to add the tooltip(s)
	 * @return a new instance of {@link JQueryUiTooltip} using jQuery UI 1.10.3
	 */
	public static JQueryUiTooltip tooltip_1_10_3(String componentSelector)
	{
		return new JQueryUiTooltip(componentSelector, uiTooltipJs_1_10_3);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		if (!response.wasRendered(uiTooltipCss_1_10_3) && !withoutCss)
		{
			if (this.cssResourceReferences.isEmpty())
			{
				response.render(CssReferenceHeaderItem.forReference(uiTooltipCss_1_10_3));
			}
			else
			{
				for (ResourceReference cssHeaderItem : this.cssResourceReferences)
				{
					response.render(CssReferenceHeaderItem.forReference(cssHeaderItem));
				}
			}
			response.markRendered(uiTooltipCss_1_10_3);
		}
	}

	@Override
	protected JsBuilder getJsBuilder()
	{
		if (this.widget.getOption("items") == null)
		{
			if (getComponent() instanceof Page)
			{
				this.widget.setOption("items", "document");
				;
			}
			else
			{
				this.widget.setOption("items", "'#" + getComponent().getMarkupId() + "'");
			}

		}
		JsBuilder builder = new JsBuilder();
		builder.append(this.widget.buildJS("'".concat(componentSelector()).concat("'")));
		return builder;
	}

	/**
	 * Sets some default options.
	 */
	private void setDefaultOptions()
	{
		setContent(new JsFunction(defaultContent));
	}

	private String componentSelector()
	{
		if (componentSelector.isEmpty())
		{
			return "#".concat(getComponent().getMarkupId());
		}
		else
		{
			return componentSelector;
		}
	}

	/**
	 * Adds the given CSS {@link ResourceReference} to the response for custom tooltip styling. If
	 * no user {@link ResourceReference} is provided a default style will be used.
	 * 
	 * @param cssResourceReference
	 * @return
	 */
	public JQueryUiTooltip addCssResource(ResourceReference cssResourceReference)
	{
		this.cssResourceReferences.add(cssResourceReference);
		this.withoutCss = false;
		return this;
	}

	/**
	 * Turns of adding any jQuery UI CSS to the response (even the default one).
	 * 
	 * @return
	 */
	public JQueryUiTooltip withoutCss()
	{
		withoutCss = true;
		return this;
	}

	/**
	 * Sets the <code>content</code> option of the tooltip widget like this: {@code content:
	 * '<h1>tooltip content</h1>'}.
	 * <p/>
	 * Note: The <code>content</code> option has to stay in sync with the <code>items</code> option
	 * (see <a href="http ://api.jqueryui
	 * .com/tooltip/#option-content">http://api.jqueryui.com/tooltip/#option-content</a>).
	 * 
	 * @param content
	 *            a String containing the content of this tooltip (e.g.
	 *            {@code <h1>tooltip content</h1>})
	 * @return
	 */
	public JQueryUiTooltip setContent(String content)
	{
		this.widget.setOption("content", "'" + content + "'");
		return this;
	}

	/**
	 * Sets the <code>content</code> option of the tooltip widget like this:
	 * <code>content:function(){return 'tooltip content';}</code>.
	 * <p/>
	 * Note: The <code>content</code> option has to stay in sync with the <code>items</code> option
	 * (see <a href="http ://api.jqueryui
	 * .com/tooltip/#option-content">http://api.jqueryui.com/tooltip/#option-content</a>).
	 * 
	 * @param content
	 *            a {@link JsFunction} containing a JavaScript function which returns the content of
	 *            this tooltip (e.g.
	 *            <code>new JsFunction("function(){return 'tooltip content';}")</code>)
	 * @return
	 */
	public JQueryUiTooltip setContent(JsFunction content)
	{
		this.widget.setOption("content", content.toString());
		return this;
	}

	/**
	 * Sets the <code>content</code> option of the tooltip widget like this:
	 * {@code content:$('#markupIdOfGivenComponent').html()}.
	 * <p/>
	 * Note: The <code>content</code> option has to stay in sync with the <code>items</code> option
	 * (see <a href="http ://api.jqueryui
	 * .com/tooltip/#option-content">http://api.jqueryui.com/tooltip/#option-content</a>).
	 * 
	 * @param content
	 *            the {@link Component} whose markup will be used as the content of this tooltip
	 * @return
	 */
	public JQueryUiTooltip setContent(Component content)
	{
		this.widget.setOption("content", "$('#" + content.getMarkupId() + "').html()");
		return this;
	}

	/**
	 * 
	 * Sets the <code>disabled</code> option of the tooltip widget like this:
	 * <code>disabled:true</code>.
	 * 
	 * @param disabled
	 *            a boolean containing the disabled option of this tooltip
	 * @return
	 */
	public JQueryUiTooltip setDisabled(boolean disabled)
	{
		this.widget.setOption("disabled", disabled ? "true" : "false");
		return this;
	}

	/**
	 * 
	 * Sets the <code>hide</code> option of the tooltip widget.
	 * 
	 * @param hide
	 *            see <a href="http://api.jqueryui.com/tooltip/#option-hide"
	 *            >http://api.jqueryui.com/tooltip/#option-hide</a>
	 * @return
	 */
	public JQueryUiTooltip setHide(Boolean hide)
	{
		this.widget.setOption("hide", hide ? "true" : "false");
		return this;
	}

	/**
	 * 
	 * Sets the <code>hide</code> option of the tooltip widget.
	 * 
	 * @param hide
	 *            see <a href="http://api.jqueryui.com/tooltip/#option-hide"
	 *            >http://api.jqueryui.com/tooltip/#option-hide</a>
	 * @return
	 */
	public JQueryUiTooltip setHide(int hide)
	{
		this.widget.setOption("hide", String.valueOf(hide));
		return this;
	}

	/**
	 * 
	 * Sets the <code>hide</code> option of the tooltip widget.
	 * 
	 * @param hide
	 *            see <a href="http://api.jqueryui.com/tooltip/#option-hide"
	 *            >http://api.jqueryui.com/tooltip/#option-hide</a>
	 * @return
	 */
	public JQueryUiTooltip setHide(String hide)
	{
		this.widget.setOption("hide", "'" + hide + "'");
		return this;
	}

	/**
	 * 
	 * Sets the <code>hide</code> option of the tooltip widget.
	 * 
	 * @param options
	 *            see <a href="http://api.jqueryui.com/tooltip/#option-hide"
	 *            >http://api.jqueryui.com/tooltip/#option-hide</a>
	 * @return
	 */
	public JQueryUiTooltip setHide(JsOption... options)
	{
		this.widget.setOption("hide", asString(options));
		return this;
	}

	private String asString(JsOption... options)
	{
		String showOptionsString = "{";
		for (JsOption option : options)
		{
			showOptionsString = showOptionsString + "," + option.toString();
		}
		showOptionsString = showOptionsString.replaceFirst(",", "").concat("}");
		return showOptionsString;
	}

	/**
	 * 
	 * Sets the <code>items</code> option of the tooltip widget like this:
	 * <code>items:'.element'</code>.
	 * <p/>
	 * Note: The <code>items</code> option has to stay in sync with the <code>content</code> option
	 * (see <a href="http://api.jqueryui.com/tooltip/#option-items"
	 * >http://api.jqueryui.com/tooltip/#option-items</a>).
	 * 
	 * @param itemsSelector
	 *            a String containing the selector(s) of the element(s) this tooltip should be
	 *            applied to (e.g. .element)
	 * @return
	 */
	public JQueryUiTooltip setItems(String itemsSelector)
	{
		this.widget.setOption("items", "'" + itemsSelector + "'");
		return this;
	}

	/**
	 * 
	 * Sets the <code>position</code> option of the tooltip widget like this:
	 * <code>position:{my:'center'}</code>.
	 * 
	 * @param position
	 *            a String containing the position object of this tooltip (e.g. {my:'center'})
	 * @return
	 */
	public JQueryUiTooltip setPosition(JsOption... options)
	{
		this.widget.setOption("position", asString(options));
		return this;
	}

	/**
	 * 
	 * Sets the <code>show</code> option of the tooltip widget.
	 * 
	 * @param show
	 *            see <a href="http://api.jqueryui.com/tooltip/#option-show" >
	 *            http://api.jqueryui.com/tooltip/#option-show</a>
	 * @return
	 */
	public JQueryUiTooltip setShow(Boolean show)
	{
		this.widget.setOption("show", show ? "true" : "false");
		return this;
	}

	/**
	 * 
	 * Sets the <code>show</code> option of the tooltip widget.
	 * 
	 * @param show
	 *            see <a href="http://api.jqueryui.com/tooltip/#option-show" >
	 *            http://api.jqueryui.com/tooltip/#option-show</a>
	 * @return
	 */
	public JQueryUiTooltip setShow(int show)
	{
		this.widget.setOption("show", String.valueOf(show));
		return this;
	}

	/**
	 * 
	 * Sets the <code>show</code> option of the tooltip widget.
	 * 
	 * @param show
	 *            see <a href="http://api.jqueryui.com/tooltip/#option-show" >
	 *            http://api.jqueryui.com/tooltip/#option-show</a>
	 * @return
	 */
	public JQueryUiTooltip setShow(String show)
	{
		this.widget.setOption("show", "'" + show + "'");
		return this;
	}

	/**
	 * 
	 * Sets the <code>show</code> option of the tooltip widget.
	 * 
	 * @param options
	 *            see <a href="http://api.jqueryui.com/tooltip/#option-show" >
	 *            http://api.jqueryui.com/tooltip/#option-show</a>
	 * @return
	 */
	public JQueryUiTooltip setShow(JsOption... options)
	{
		this.widget.setOption("show", asString(options));
		return this;
	}

	/**
	 * 
	 * Sets the <code>tooltipClass</code> option of the tooltip widget which will be rendered like
	 * this: <code>tooltipClass:'className'</code>.
	 * 
	 * @param tooltipClass
	 *            a String containing the tooltipClass option of this tooltip (e.g. className)
	 * @return
	 */
	public JQueryUiTooltip setTooltipClass(String tooltipClass)
	{
		this.widget.setOption("tooltipClass", "'" + tooltipClass + "'");
		return this;
	}

	/**
	 * Sets the <code>track</code> option of the tooltip widget like this: <code>track:true</code> .
	 * 
	 * @param track
	 *            a boolean containing the track option of this tooltip
	 * @return
	 */
	public JQueryUiTooltip setTrack(boolean track)
	{
		this.widget.setOption("track", track ? "true" : "false");
		return this;
	}

	/**
	 * Sets the <code>close</code> event handler of the tooltip widget like this:
	 * <code>close:function(event,ui){some code;}</code>.
	 * 
	 * @param close
	 *            a String containing the close event handler of this tooltip (e.g.
	 *            <code>function(event,ui){some code;}</code>.)
	 * @return
	 */
	public JQueryUiTooltip setOnClose(String close)
	{
		this.widget.setEventHandler("close", close);
		return this;
	}

	/**
	 * Sets the <code>create</code> event handler of the tooltip widget like this:
	 * <code>create:function(event,ui){some code;}</code>.
	 * 
	 * @param create
	 *            a String containing the create event handler of this tooltip (e.g.
	 *            <code>function(event,ui){some code;}</code>.)
	 * @return
	 */
	public JQueryUiTooltip setOnCreate(String create)
	{
		this.widget.setEventHandler("create", create);
		return this;
	}

	/**
	 * Sets the <code>open</code> event handler of the tooltip widget like this:
	 * <code>open:function(event,ui){some code;}</code>.
	 * 
	 * @param open
	 *            a String containing the open event handler of this tooltip (e.g.
	 *            <code>function(event,ui){some code;}</code>.)
	 * @return
	 */
	public JQueryUiTooltip setOnOpen(String open)
	{
		this.widget.setEventHandler("open", open);
		return this;
	}
}
