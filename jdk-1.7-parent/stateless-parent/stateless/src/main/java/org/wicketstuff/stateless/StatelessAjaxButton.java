package org.wicketstuff.stateless;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Stateless version of AjaxButton
 * 
 * @since 1.3
 * 
 * @author Igor Vaynberg (ivaynberg)
 */
public abstract class StatelessAjaxButton extends Button
{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(StatelessAjaxButton.class);

	private final Form<?> form;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public StatelessAjaxButton(String id)
	{
		this(id, null, null);
	}

	/**
	 * 
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 *            model used to set <code>value</code> markup attribute
	 */
	public StatelessAjaxButton(String id, IModel<String> model)
	{
		this(id, model, null);
	}

	/**
	 * 
	 * Construct.
	 * 
	 * @param id
	 * @param form
	 */
	public StatelessAjaxButton(String id, Form<?> form)
	{
		this(id, null, form);
	}


	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 *            model used to set <code>value</code> markup attribute
	 * @param form
	 */
	public StatelessAjaxButton(String id, IModel<String> model, final Form<?> form)
	{
		super(id, model);
		this.form = form;
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		add(newAjaxFormSubmitBehavior("click"));
	}

	protected StatelessAjaxFormSubmitBehavior newAjaxFormSubmitBehavior(String event)
	{
		return new StatelessAjaxFormSubmitBehavior(event)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				StatelessAjaxButton.this.onSubmit(target, StatelessAjaxButton.this.getForm());
			}

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target)
			{
				StatelessAjaxButton.this.onAfterSubmit(target, StatelessAjaxButton.this.getForm());
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				StatelessAjaxButton.this.onError(target, StatelessAjaxButton.this.getForm());
			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);

				// do not allow normal form submit to happen
				attributes.setPreventDefault(true);

				StatelessAjaxButton.this.updateAjaxAttributes(attributes);
			}
		};
	}

	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
	}

	/**
	 * Returns the form if it was set in constructor, otherwise returns the form nearest in parent
	 * hierarchy.
	 * 
	 * @see org.apache.wicket.markup.html.form.FormComponent#getForm()
	 */
	@Override
	public Form<?> getForm()
	{
		if (form != null)
		{
			return form;
		}
		else
		{
			return super.getForm();
		}
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		// WICKET-5594 prevent non-Ajax submit
		tag.put("type", "button");

		super.onComponentTag(tag);
	}

	/**
	 * This method is never called.
	 * 
	 * @see #onSubmit(AjaxRequestTarget, Form)
	 */
	@Override
	public final void onSubmit()
	{
		logger.warn("unexpected invocation of #onSubmit() on {}", this);
	}

	@Override
	public final void onAfterSubmit()
	{
		logger.warn("unexpected invocation of #onAfterSubmit() on {}", this);
	}

	/**
	 * This method is never called.
	 * 
	 * @see #onError(AjaxRequestTarget, Form)
	 */
	@Override
	public final void onError()
	{
		logger.warn("unexpected invocation of #onError() on {}", this);
	}

	/**
	 * Listener method invoked on form submit with no errors, before {@link Form#onSubmit()}.
	 * 
	 * @param target
	 * @param form
	 */
	protected void onSubmit(AjaxRequestTarget target, Form<?> form)
	{
	}

	/**
	 * Listener method invoked on form submit with no errors, after {@link Form#onSubmit()}.
	 * 
	 * @param target
	 * @param form
	 */
	protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
	{
	}

	/**
	 * Listener method invoked on form submit with errors
	 * 
	 * @param target
	 * @param form
	 */
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{
	}
	
	@Override
	protected boolean getStatelessHint() 
	{
		return true;
	};
}
