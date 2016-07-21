/**
 * 
 */
package org.wicketstuff.yui.markup.html.anim.stretch;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.wicketstuff.yui.behavior.animation.YuiEasing;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * @author jcompagner
 * 
 */
public abstract class StretchPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	public static final String EXPANDED = "EXPANDED";
	public static final String COLLAPSED = "COLLAPSED";

	private Label label;
	private Component stretchPanel;
	private int collapsedHeight = 0;
	private int expandedHeight = 200;
	private double delay = 1;
	private YuiEasing easing = YuiEasing.easeOut;

	private String defaultState = COLLAPSED;

	/**
	 * @param id
	 */
	public StretchPanel(String id, String toggleLabel)
	{
		this(id, new Model(toggleLabel));
	}

	public StretchPanel(String id, IModel toggleLabel)
	{
		super(id);
		add(YuiHeaderContributor.forModule("animation"));
		label = new Label("stretchToggle", toggleLabel);
		label.setOutputMarkupId(true);
		stretchPanel = getStretchPanel("stretcher");
		stretchPanel.setOutputMarkupId(true);
		add(label);
		add(stretchPanel);
	}

	/**
	 * @see org.apache.wicket.markup.html.panel.Panel#renderHead(org.apache.wicket.markup.html.internal.HtmlHeaderContainer)
	 */
	@Override
	public void renderHead(HtmlHeaderContainer container)
	{
		super.renderHead(container);

		IHeaderResponse headerResponse = container.getHeaderResponse();

		AppendingStringBuffer asb = new AppendingStringBuffer("var tog = document.getElementById('");
		asb.append(label.getMarkupId());
		asb.append("');\n");
		asb.append("var slide = document.getElementById('");
		asb.append(stretchPanel.getMarkupId());
		asb.append("');\n");

		asb.append("slideFunction = function(e, obj) {\n");
		asb.append("var att = {height: { to: " + expandedHeight + " }};\n");
		asb.append("if(this.style.height == '" + expandedHeight + "px') {\n");
		asb.append("att = {height: { to: " + collapsedHeight + " }};\n");
		asb.append("}\n");
		asb.append("var anim = new YAHOO.util.Anim(this, att, " + delay + ", " + easing.constant()
				+ ");\n");
		asb.append("anim.animate();\n");
		asb.append("}\n");
		asb.append("YAHOO.util.Event.on(tog, 'click', slideFunction, slide, true);\n");
		int initialHeight = collapsedHeight;
		if (EXPANDED.equals(defaultState))
		{
			initialHeight = expandedHeight;
		}
		asb.append("var att = {height: { to: " + initialHeight + " }};\n");
		asb.append("var anim = new YAHOO.util.Anim(slide, att, " + delay + ", YAHOO.util.Easing."
				+ easing + ");\n");
		asb.append("anim.animate();\n");

		headerResponse.renderOnDomReadyJavascript(asb.toString());

	}

	protected abstract Component getStretchPanel(String id);

	/**
	 * Set the collapsed height (default 0)
	 * 
	 * @param collapsedHeight
	 */
	public void setCollapsedHeight(int collapsedHeight)
	{
		this.collapsedHeight = collapsedHeight;
	}

	/**
	 * Set the Expanded height (default 200)
	 * 
	 * @param expandedHeight
	 */
	public void setExpandedHeight(int expandedHeight)
	{
		this.expandedHeight = expandedHeight;
	}

	/**
	 * Set the delay in seconds, the time to collapse or expand completely.
	 * (default 1s)
	 * 
	 * @param delay
	 */
	public void setDelayTime(double delay)
	{
		this.delay = delay;
	}

	/**
	 * @param yuiEasing
	 */
	public void setEasing(YuiEasing yuiEasing)
	{
		this.easing = yuiEasing;
	}

	/**
	 * Set the default state, one of {@link StretchPanel#EXPANDED} or
	 * {@link StretchPanel#COLLAPSED}
	 * 
	 * @param defaultState
	 */
	public void setDefaultState(String defaultState)
	{
		this.defaultState = defaultState;
	}
}
