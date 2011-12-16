package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.container.YuiPanel;

/**
 * Example on using Animation...
 * 
 * @author josh
 * 
 */
public class YuiPanelPage extends WicketExamplePage
{

	private String opts = "{ \n"
			+
			// Panel config
			"  close     : true, \n"
			+ "  draggable : true, \n"
			+ "  underlay  : 'shadow', \n"
			+ "  modal     : false, \n"
			+

			// inherited config
			"  visible   : true, \n"
			+ "  effect    :\n "
			+ "    {effect:YAHOO.widget.ContainerEffect.FADE,duration:0.25},\n"
			+
			// "    [ {effect:YAHOO.widget.ContainerEffect.FADE,duration:0.25},\n"
			// +
			// "      {effect:YAHOO.widget.ContainerEffect.SLIDE,duration:0.25}\n"
			// +
			// "     ],\n" +
			//			
			"  monitorresize : true, \n" + "  x  : 50, \n" + "  y  : 100, \n"
			+ "  xy : [10,10], \n" +

			"  context     : ['alignId', 'tl', 'bl'], \n" + "  fixedcenter : false, \n"
			+ "  width  : '300px', \n" + "  height : '300px', \n" + "  zIndex : 5, \n"
			+ "  constraintoviewport : true, \n" + "  iframe : false, \n"
			+ "  autofillheight :'body' \n" + "}";

	@SuppressWarnings("serial")
	public YuiPanelPage()
	{
		add(new YuiPanel("yuiPanelConfigure")
		{
			@Override
			protected String getOpts()
			{
				return "{ " + "close:false, " + "draggable : false,"
						+ "width: '520px', height : '440px', "
						+ "autofillheight:'body', constraintoviewport : true"

						+ " }";
			}

			@Override
			protected Component newHeaderPanel(String id)
			{
				return new Label(id, "Configure YUI Panel");
			}

			@Override
			protected Component newBodyPanel(String id)
			{
				return new ConfigureYuiPanel(id);
			}
		});

		add(new EmptyPanel("yuiPanel"));
		setOutputMarkupId(true);
	}

	@SuppressWarnings("serial")
	private class ConfigureYuiPanel extends Panel
	{

		public ConfigureYuiPanel(String id)
		{
			super(id);

			Form<Object> form = new Form<Object>("form");

			form.add(new TextArea<String>("opts", new PropertyModel<String>(YuiPanelPage.this,
					"opts")));

			form.add(new AjaxSubmitLink("show")
			{

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form)
				{
					YuiPanelPage.this.replace(new YuiPanel("yuiPanel")
					{

						@Override
						protected String getOpts()
						{
							return YuiPanelPage.this.opts;
						}

						@Override
						protected void onHide(AjaxRequestTarget target, String type)
						{
							super.onHide(target, type);
							YuiPanelPage.this.replace(new EmptyPanel("yuiPanel"));

						}
					});

					target.addComponent(YuiPanelPage.this);
				}
			});
			add(form);
		}
	}
}
