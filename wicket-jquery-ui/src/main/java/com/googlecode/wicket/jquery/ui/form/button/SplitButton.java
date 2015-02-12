package com.googlecode.wicket.jquery.ui.form.button;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;
import com.googlecode.wicket.jquery.ui.form.button.Button.ButtonBehavior;
import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;
import com.googlecode.wicket.jquery.ui.widget.menu.Menu;

/**
 * 
 * JQueryUI Version of a SplitButton.
 * 
 * @author Patrick Davids - Patrick1701
 * 
 */
public class SplitButton extends GenericPanel<List<IMenuItem>>{
	private static final long serialVersionUID = 1L;

	private static final JavaScriptResourceReference JS = new JavaScriptResourceReference(SplitButton.class, "SplitButton.js");
	
	private final AjaxLink<IMenuItem> button;
	
	public SplitButton(String id, IModel<List<IMenuItem>> items) {
		super(id, items);
		
		//Buttonset
		WebMarkupContainer buttonset = new WebMarkupContainer("buttonset");
		buttonset.add(new JQueryUIBehavior(JQueryWidget.getSelector(buttonset), "buttonset"));
		
		//Button 1 / Main-Button / left
		button = new AjaxLink<IMenuItem>("button", new Model<IMenuItem>()){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				if(getModelObject()==null){
					List<IMenuItem> items = SplitButton.this.getModelObject();
					if( ! items.isEmpty()){
						setModelObject(items.get(0));
					}
				}
			}
			
			@Override
			public IModel<?> getBody() {
				if(getModelObject()!=null){
					return getModelObject().getTitle();
				}
				return Model.of("");
			}
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				if(getModelObject()!=null){
					getModelObject().onClick(target);
				}
			}
		};
		button.add(new ButtonBehavior(JQueryWidget.getSelector(button)));//the button look
		button.setOutputMarkupId(true);
		buttonset.add(button);

		//Button 2 / Select / right
		final WebMarkupContainer select = new WebMarkupContainer("select");
		
		Options o = new Options();
		o.set("icons", new Options("primary", Options.asString(JQueryIcon.TRIANGLE_1_S)));
		o.set("text", false);
		select.add(new JQueryUIBehavior(JQueryWidget.getSelector(select), "button", o){
			private static final long serialVersionUID = 1L;

			@Override
			protected String $() {
				return super.$() + String.format("\njQuery(function() { jQuery('%s').click(function(){ return openMenuReturnFalse(this); }); });", JQueryWidget.getSelector(select));
			}
		});
		buttonset.add(select);
		add(buttonset);
		
		
	}
	
	@Override
	protected void onBeforeRender() {
		final Menu menu = new Menu("menu", getModelObject()){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target, IMenuItem item) {
				button.setModelObject(item);
				target.add(button);
			}
		};
		addOrReplace(menu);
		super.onBeforeRender();
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(JS));
	}
}