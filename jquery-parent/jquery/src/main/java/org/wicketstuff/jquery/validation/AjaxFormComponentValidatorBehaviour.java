package org.wicketstuff.jquery.validation;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.resource.JQueryResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * 
 * Ajax validation behaviour that triggers when the selected event is fired. The default action is
 * to add a css class to the parent element of the component and add any error messages in a span
 * directly after the component. You can change this behaviour by overriding getErrorJavascript().
 * 
 * By default it uses JQuery to do the markup manipulation. You can either skip jquery or load
 * another library by overriding the renderHead method.
 * 
 * This behaviour will also update the model object when the selected client side event fires.
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * @created 2008-06-01
 * 
 */
public class AjaxFormComponentValidatorBehaviour extends AjaxFormComponentUpdatingBehavior
{
	private static final long serialVersionUID = 1L;
	public static final String ERROR_COMPONENT_CLASS = "error";
	public static final String ERROR_MESSAGE_CLASS = "error";
	private boolean errorDetected;

	/* Default constructor, attaches to the onblur clientside event */
	public AjaxFormComponentValidatorBehaviour()
	{
		this("onblur");
	}

	public AjaxFormComponentValidatorBehaviour(String event)
	{
		super(event);
		errorDetected = false;
	}

	/**
	 * When the component renders, onUpdate will remove the markup containing any previous
	 * error-message.
	 * 
	 * @param target
	 */
	@Override
	protected void onUpdate(AjaxRequestTarget target)
	{
		if (errorDetected)
		{
			// Remove the error-class on the surrounding element
			target.appendJavaScript("$('#" + getComponent().getMarkupId() + "').removeClass('" +
				ERROR_COMPONENT_CLASS + "');");

			// Remove the previously added error-messages
			target.appendJavaScript(getRemovePreviousErrorsScript());
		}
	}

	/**
	 * Javascript used to remove previously shown error messages.
	 * 
	 * @return
	 */
	protected String getRemovePreviousErrorsScript()
	{
		return "$('#" + getComponent().getMarkupId() + "').next().remove();";
	}

	/**
	 * Adds the error-javascript to the response since the component has errors.
	 * 
	 * @param target
	 *            The AjaxRequestTarget you can add components to
	 * @param e
	 *            The exception if any
	 */
	@Override
	protected void onError(AjaxRequestTarget target, RuntimeException e)
	{
		super.onError(target, e);
		target.appendJavaScript(getErrorJavascript());
		errorDetected = true;
	}

	/**
	 * Returns the javascript that will manipulate the markup to show the error message. Defaults to
	 * adding a CSS-class to the parent element (nice if you add a
	 * <p>
	 * or <div> around your label/component)
	 * 
	 * @return
	 */
	protected String getErrorJavascript()
	{
		StringBuilder b = new StringBuilder();

		// Remove the previously added error-messages
		if (errorDetected)
			b.append(getRemovePreviousErrorsScript());

		// Add the ERROR class to the sourrounding component
		b.append("$('#" + getComponent().getMarkupId() + "').parent().addClass('" +
			ERROR_COMPONENT_CLASS + "');");

		// Create list of error messages, separated by the chosen separator markup
		List<FeedbackMessage> messages = Session.get()
			.getFeedbackMessages()
			.messages(new ComponentFeedbackMessageFilter(getComponent()));
		StringBuilder mb = new StringBuilder("");

		for (int i = 0; i < messages.size(); i++)
		{
			String msg = messages.get(i).getMessage().toString().replace("'", "\\'");
			mb.append(msg);
			if (i + 1 < messages.size())
				mb.append(getErrorSeparator());
		}

		// Add the span with the error messages
		b.append("$('#" + getComponent().getMarkupId() + "').after('<span class=\"" +
			ERROR_MESSAGE_CLASS + "\">" + mb.toString() + "</span>');");
		return b.toString();
	}

	/**
	 * The error separator is used to differentiate each error-message in case a component has more
	 * than one error message
	 * 
	 * Defaults to separating using a <br/>
	 * tag.
	 * 
	 * @return the markup for separating multiple error message
	 */
	protected String getErrorSeparator()
	{
		return "<br/>";
	}

	/**
	 * Add the JQuery library so we can use it in the onError method to manipulate the markup.
	 * 
	 * @param response
	 */
	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(JQueryResourceReference.get()));
	}
}
