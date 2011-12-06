package org.wicketstuff.scriptaculous.fx;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;

/**
 * simple scriptacoulus toaster / info window CSS inspired by dojo contrib
 * toaster
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class Toaster extends Panel {
	private WebMarkupContainer container;
	private Label label;

	private ToasterSettings toasterSettings;

	/**
	 * makes a default toaster, displayed at top left
	 * @param id
	 * @param model
	 */
	public Toaster(String id, IModel model, boolean manualSetup) {
		super(id, model);
		add(container = new WebMarkupContainer("container"));

		toasterSettings = new ToasterSettings(container);
		
		add(ScriptaculousAjaxBehavior.newJavascriptBindingBehavior());
		
		container.add(new AttributeModifier("class", new Model<String>(
				"toasterContainer" + this.getId())));
		container.setOutputMarkupPlaceholderTag(true);
		container.add(label=new Label("message", model));
		label.add(new AttributeModifier("class", new Model<String>("toasterContent" + this.getId())));
		
		if(!manualSetup){
			setupCSSLocation();
		}
	}
	/**
	 * call this if you've setup some custom location or css.
	 */
	public void setupCSSLocation()
	{
		add(new ToasterCSSHeaderContributor(this.getId(), toasterSettings));
	}
	
	public MarkupContainer setModel(IModel<?> model)
	{
		label.setDefaultModel(model);
		
		return this;
	}
	

	public void publishMessage(AjaxRequestTarget target) {
		target.addComponent(container);

		if (toasterSettings.getAppear() != null) {
			target.appendJavascript(toasterSettings.getAppear().toJavascript());
		}
		if (toasterSettings.getMiddle() != null) {
			target.appendJavascript(toasterSettings.getMiddle().toJavascript());
		}
		if (toasterSettings.getFade() != null) {
			target.appendJavascript(toasterSettings.getFade().toJavascript());
		}
	}

	public ToasterSettings getToasterSettings() {
		return toasterSettings;
	}

	public void setToasterSettings(ToasterSettings toasterSettings) {
		this.toasterSettings = toasterSettings;
	}

	public WebMarkupContainer getContainer() {
		return container;
	}

}
