package org.wicketstuff.yui.markup.html.container;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
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

	private void init()
	{
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
				+ getOpts() + ");" + getYuiPanelVar() + ".render();\n";
	}

	private String getResizeOpts()
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
