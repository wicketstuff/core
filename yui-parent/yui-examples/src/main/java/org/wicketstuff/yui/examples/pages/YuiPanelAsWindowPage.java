package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.container.YuiPanel;

/**
 * A slightly more complex YUI panel example that demonstrates how to use multiple {@link YuiPanel} as an OS like windows.
 * 
 * @author Erik van Oosten
 */
public class YuiPanelAsWindowPage extends WicketExamplePage {

    private int panelId = 1;
    
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
    public YuiPanelAsWindowPage() {
        add(new YuiPanel("yuiPanelConfigure") {
            @Override
            protected String getOpts() {
                return "{ " + "close:false, " + "draggable : false,"
                        + "width: '520px', height : '440px', "
                        + "autofillheight:'body', constraintoviewport : true"

                        + " }";
            }

            @Override
            protected Component newHeaderPanel(String id) {
                return new Label(id, "Configure YUI Panel");
            }

            @Override
            protected Component newBodyPanel(String id) {
                return new ConfigureYuiPanel(id);
            }
        });

        add(new EmptyPanel("yuiPanel1").setOutputMarkupId(true));
        add(new EmptyPanel("yuiPanel2").setOutputMarkupId(true));
        add(new EmptyPanel("yuiPanel3").setOutputMarkupId(true));
        add(new EmptyPanel("yuiPanel4").setOutputMarkupId(true));
    }

    @SuppressWarnings("serial")
    private class ConfigureYuiPanel extends Fragment {

        public ConfigureYuiPanel(String id) {
            super(id, "configureYuiPanel", YuiPanelAsWindowPage.this);

            Form<Object> form = new Form<Object>("form");

            form.add(new TextArea<String>("opts", new PropertyModel<String>(YuiPanelAsWindowPage.this, "opts")));

            RadioGroup panelSelect = new RadioGroup("panelSelect", new PropertyModel(YuiPanelAsWindowPage.this, "panelId"));
            form.add(panelSelect);
            panelSelect.add(new Radio("p1", new Model(1)));
            panelSelect.add(new Radio("p2", new Model(2)));
            panelSelect.add(new Radio("p3", new Model(3)));
            panelSelect.add(new Radio("p4", new Model(4)));

            form.add(new AjaxSubmitLink("show") {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    final String panelMarkupId = "yuiPanel" + YuiPanelAsWindowPage.this.panelId;

                    YuiPanel panel = new YuiPanel(panelMarkupId) {
                        @Override
                        protected String getOpts() {
                            return YuiPanelAsWindowPage.this.opts;
                        }

                        protected Component newBodyPanel(String id) {
                            return new Label(id, "Hi from panel no " +  YuiPanelAsWindowPage.this.panelId);
                        }

                        protected String getResizeOpts() {
                            return "{ handles : ['br'], proxy: true, status: true, animate: false}";
                        }

						@Override
						protected void onHide(AjaxRequestTarget target, String type)
						{
							super.onHide(target, type);
							YuiPanelAsWindowPage.this.replace(new EmptyPanel(panelMarkupId));
						}
                    };
                    panel.setOutputMarkupId(true);

                    //
                    // uses overlay manager to make sure the active panel is on top
                    //
                    panel.setUsesOverlayManager(true);

                    YuiPanelAsWindowPage.this.replace(panel);

                    target.addComponent(panel);
                }
            });
            add(form);
        }
    }
}
