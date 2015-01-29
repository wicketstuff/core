package org.wicketstuff.stateless;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.Method;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class StatelessAjaxFormSubmitBehavior extends StatelessAjaxEventBehavior
{

	public StatelessAjaxFormSubmitBehavior(String event)
	{
		super(event);
	}

	@Override
	protected PageParameters getPageParameters()
	{
		return null;
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);

		Form<?> form = getForm();
		attributes.setFormId(form.getMarkupId());

		String formMethod = form.getMarkupAttributes().getString("method");
		if (formMethod == null || "POST".equalsIgnoreCase(formMethod))
		{
			attributes.setMethod(Method.POST);
		}

		if (form.getRootForm().isMultiPart())
		{
			attributes.setMultipart(true);
			attributes.setMethod(Method.POST);
		}
		
		attributes.setPreventDefault(true);
	}
	
	/**
	 * Finds form that will be submitted
	 * 
	 * @return form to submit or {@code null} if none found
	 */
	protected Form<?> getForm()
	{
		// try to find form in the hierarchy of owning component
		Component component = getComponent();
		if (component instanceof Form<?>)
		{
			return (Form<?>)component;
		}
		else
		{
			return component.findParent(Form.class);
		}
	}
	
	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
		getForm().getRootForm().onFormSubmitted();
	}
}
