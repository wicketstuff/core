package org.wicketstuff.stateless;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.Method;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class StatelessAjaxFormSubmitBehavior extends StatelessAjaxEventBehavior
{

	private boolean defaultFormProcessing = true;

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

		Form<?> form = findForm();
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
	protected Form<?> findForm()
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
		findForm().getRootForm().onFormSubmitted(new AjaxFormSubmitter(this, target));
	}
	
	class AjaxFormSubmitter implements IFormSubmitter
	{

		private final StatelessAjaxFormSubmitBehavior submitBehavior;
		
		private final AjaxRequestTarget target;
		
		public AjaxFormSubmitter(StatelessAjaxFormSubmitBehavior submitBehavior,
			AjaxRequestTarget target)
		{
			this.submitBehavior = submitBehavior;
			this.target = target;
		}

		@Override
		public Form<?> getForm()
		{
			return StatelessAjaxFormSubmitBehavior.this.findForm();
		}

		@Override
		public boolean getDefaultFormProcessing()
		{
			return StatelessAjaxFormSubmitBehavior.this.getDefaultFormProcessing();
		}

		@Override
		public void onSubmit()
		{
			StatelessAjaxFormSubmitBehavior.this.onSubmit(target);
		}

		@Override
		public void onAfterSubmit()
		{
			StatelessAjaxFormSubmitBehavior.this.onAfterSubmit(target);			
		}

		@Override
		public void onError()
		{
			StatelessAjaxFormSubmitBehavior.this.onError(target);
		}
		
	}
	
	/**
	 * Override this method to provide special submit handling in a multi-button form. This method
	 * will be called <em>after</em> the form's onSubmit method.
	 */
	protected void onAfterSubmit(AjaxRequestTarget target)
	{
	}

	/**
	 * Override this method to provide special submit handling in a multi-button form. This method
	 * will be called <em>before</em> the form's onSubmit method.
	 */
	protected void onSubmit(AjaxRequestTarget target)
	{
	}

	/**
	 * Listener method invoked when the form has been processed and errors occurred
	 * 
	 * @param target
	 */
	protected void onError(AjaxRequestTarget target)
	{
	}
	
	public boolean getDefaultFormProcessing()
	{
		return defaultFormProcessing ;
	}

	public void setDefaultFormProcessing(boolean defaultFormProcessing)
	{
		this.defaultFormProcessing = defaultFormProcessing;
	}
}
