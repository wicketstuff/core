package org.wicketstuff.yui.markup.html.container;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.behavior.resize.YuiResize;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderModule;

public class YuiPanel extends Panel implements Serializable
{

	private static final long serialVersionUID = 1L;

	private static final String MODULE_NAME = "wicket_yui_panel";

	private static final ResourceReference MODULE_JS_REF = new ResourceReference(YuiPanel.class,
			"YuiPanel.js");

	private static final String[] MODULE_REQUIRES = { "container", "animation", "dragdrop",
			"utilities" };

	protected WebMarkupContainer container;

	private WebMarkupContainer yuiPanel;

	private AbstractDefaultAjaxBehavior ajaxBehavior;

	private boolean usesOverlayManager;

	private static final String EVENT_TYPE = "type";

	/**
	 * Ctor.
	 * 
	 * @param id component id
	 * @param model model
	 */
	public YuiPanel(String id, IModel<?> model)
	{
		super(id, model);
		init();
	}

	public YuiPanel(String id)
	{
		super(id);
		init();
	}

	/**
	 * @param usesOverlayManager true to make a panel that is registered at a YUI overlay
	 *   manager, false otherwise (the default is false)
	 * @return this
	 */
	public YuiPanel setUsesOverlayManager(boolean usesOverlayManager)
	{
		this.usesOverlayManager = usesOverlayManager;
		return this;
	}
	
	private void init()
	{

		add(ajaxBehavior = new AbstractDefaultAjaxBehavior()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target)
			{
				final String type = RequestCycle.get().getRequest().getParameter(EVENT_TYPE);
				if (usesOverlayManager) {
					target.prependJavascript(deregisterPanelJs());
				}
				onHide(target, type);
			}
		});

		add(container = new WebMarkupContainer("yuiPanel_container"));
		container.add(new AttributeModifier("class", true, new Model<String>(getCssClass())));

		// add to container
		container.add(yuiPanel = new WebMarkupContainer("yuiPanel"));
		yuiPanel.setOutputMarkupId(true);

		yuiPanel.add(new YuiResize()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected String getOpts()
			{
				return getResizeOpts();
			}

			@Override
			protected String getStartResizeJs()
			{
				return " function() {" + " var cfg = "
						+ getYuiPanelVar()
						+ ".cfg; "
						+ " if (cfg.getProperty(\"constraintoviewport\")) { "
						+ "   var D = YAHOO.util.Dom;"
						+ "   var clientRegion = D.getClientRegion();"
						+ "   var elRegion = D.getRegion("
						+ getYuiPanelVar()
						+ ".element);"
						+ "   "
						+ getYuiResizeVar()
						+ ".set(\"maxWidth\", clientRegion.right - elRegion.left - YAHOO.widget.Overlay.VIEWPORT_OFFSET);"
						+ "   "
						+ getYuiResizeVar()
						+ ".set(\"maxHeight\", clientRegion.bottom - elRegion.top - YAHOO.widget.Overlay.VIEWPORT_OFFSET);"
						+ " } else { " + "   " + getYuiResizeVar() + ". set(\"maxWidth\", null); "
						+ "   " + getYuiResizeVar() + ".set(\"maxHeight\", null);" + "}" + "} ";
			}

			@Override
			protected String getResizeJs()
			{
				return " function(args) {" + " var panelHeight = args.height;" + " "
						+ getYuiPanelVar() + ".cfg.setProperty(\"height\", panelHeight + \"px\");"
						+ "} ";
			}
		});

		yuiPanel.add(newHeaderPanel("hd"));
		yuiPanel.add(newBodyPanel("bd"));
		yuiPanel.add(newFooterPanel("ft"));

		add(YuiLoaderContributor.addModule(new YuiLoaderModule(MODULE_NAME,
				YuiLoaderModule.ModuleType.js, MODULE_JS_REF, MODULE_REQUIRES)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String onSuccessJS()
			{
				return YuiPanel.this.getInitJS();
			}
		}));
	}

	protected void onHide(AjaxRequestTarget target, String type)
	{
	}

	protected Component newFooterPanel(String id)
	{
		return new Label(id, "FOOTER");
	}

	protected Component newBodyPanel(String id)
	{
		return new Label(id, "BODY");
	}

	protected Component newHeaderPanel(String id)
	{
		return new Label(id, "HEADER");
	}

	protected String getCssClass()
	{
		return "yui-skin-sam";
	}

	public String getInitJS()
	{
		return "var " + getYuiPanelVar() + " = new YAHOO.widget.Panel(\"" + getYuiPanelId() + "\","
				+ getOpts() + ");" + getYuiPanelVar() + ".render();\n"
				+ subscribeEventCallback("hide")
				+ (usesOverlayManager ? registerPanelJs() : "");
	}

    protected String subscribeEventCallback(String jsEvent)
	{
		return getYuiPanelVar() + ".subscribe(\"hide\", function(type, args) { "
				+ "wicketAjaxGet('" + ajaxBehavior.getCallbackUrl(true) + "&" + EVENT_TYPE
				+ "='+type);" + " } );\n";
	}

    private String registerPanelJs()
    {
	    return "WicketStuff.Yui.registerPanel('"+ getYuiPanelId() +"',"+ getYuiPanelVar() +");\n";
	}

    private String deregisterPanelJs()
    {
	    return "WicketStuff.Yui.deregisterPanel("+ getYuiPanelVar() +");\n";
	}

	protected String getResizeOpts()
	{
		return "{ handles : ['br'], proxy: true, status: true, animate: true, animateDuration: .75, "
				+ "animateEasing: YAHOO.util.Easing.backBoth}";
	}

	protected String getOpts()
	{
		return "{draggable: true, \n" + "width: \"500px\", \n" + "height: \"150px\", \n"
				+ "autofillheight: \"body\", \n" + "constraintoviewport: true, \n" + "context: [\""
				+ container.getMarkupId() + "\", \"tl\", \"bl\"],"
				+ "effect:{effect:YAHOO.widget.ContainerEffect.FADE,duration:0.25}" + " }";
	}

	private String getYuiPanelVar()
	{
		return "var_" + getYuiPanelId();
	}

	private String getYuiPanelId()
	{
		return yuiPanel.getMarkupId();
	}
}
