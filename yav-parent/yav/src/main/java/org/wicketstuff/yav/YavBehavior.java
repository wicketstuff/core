package org.wicketstuff.yav;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converters.DateConverter;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.value.IValueMap;
import org.wicketstuff.yav.alerts.AlertType;

/**
 * This is the main Behavior that contributes to the header with the appropriate JS files and builds the 
 * Yav rules.
 * 
 * @author Zenika
 */
public class YavBehavior extends AbstractBehavior implements IHeaderContributor {
	private static final long serialVersionUID = 1L;

	private AlertType alertType = AlertType.INNER_HTML;

	/**
	 * Default constructor
	 */
	public YavBehavior() {
		super();
	}

	/**
	 * Constructor defining an AlertType
	 * 
	 * @param alertType
	 */
	public YavBehavior(AlertType alertType) {
		this.alertType = alertType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket
	 * .markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		response.renderCSSReference(new CompressedResourceReference(
				YavBehavior.class, "style/yav-style.css"));

		addJavascriptReference(response, "yav.js");
		addJavascriptReference(response, "yav-config.js");
		addJavascriptReference(response, "util.js");

		// Add an onload contributor that will call a function which is defined
		// during onComponentTag method call which is processed after the head
		// is rendered (warning, not compliant with XHTML 1.0 Strict DTD)
		response.renderOnLoadJavascript("yavInit()");
	}

	/**
	 * @param response
	 * @param resource
	 */
	private void addJavascriptReference(IHeaderResponse response,
			String resource) {
		response.renderJavascriptReference(new JavascriptResourceReference(
				YavBehavior.class, resource));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache
	 * .wicket.Component, org.apache.wicket.markup.ComponentTag)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);

		if (!Form.class.isAssignableFrom(component.getClass())) {
			throw new WicketRuntimeException(
					"This behavior is only applicable on a Form component");
		}

		Form form = (Form) component;

		// Retrieve and set form name
		String formName = verifyFormName(form, tag);

		tag.put("onsubmit", "return yav.performCheck('" + formName
				+ "', rules);");

		// Open the Yav script (inlined JavaScript)
		AppendingStringBuffer buffer = new AppendingStringBuffer("<script>\n");
		buffer.append("var rules=new Array();\n");

		// Visit all form components and check for validators (and write the
		// appropriate Yav rules in the current inlined JavaScript)
		form.visitFormComponents(new YavFormComponentVisitor(buffer, form));

		// Build the call to the yav.init with the proper form name
		buffer.append("function yavInit() {\n");
		buffer.append("    yav.init('" + formName + "', rules);\n");
		buffer.append("}\n");
	
		// Close the Yav script
		buffer.append("</script>\n");
		
		// Write the generated script into the response
		Response response = RequestCycle.get().getResponse();
		response.write(buffer.toString());
	}

	/**
	 * @param form
	 * @param tag
	 * @return
	 */
	private String verifyFormName(Form form, ComponentTag tag) {
		IValueMap attributes = tag.getAttributes();
		String value = attributes.getString("name");
		if (value == null) {
			value = form.getId();
			tag.put("name", value);
		}
		return value;
	}

	/**
	 * @return
	 */
	public AlertType getAlertType() {
		return alertType;
	}

	/**
	 * @param alertType
	 */
	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}
}
