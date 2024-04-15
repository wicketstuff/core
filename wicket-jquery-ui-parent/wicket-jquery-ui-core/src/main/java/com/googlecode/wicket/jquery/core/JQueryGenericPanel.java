package com.googlecode.wicket.jquery.core;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.model.IModel;

/**
 * A specialization of {@link JQueryPanel} that knows the type of its model object
 *
 * @param <T> the type of the model object
 */
public abstract class JQueryGenericPanel<T> extends JQueryPanel implements IGenericComponent<T, JQueryGenericPanel<T>>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public JQueryGenericPanel(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public JQueryGenericPanel(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public JQueryGenericPanel(String id, IModel<?> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public JQueryGenericPanel(String id, IModel<?> model, Options options)
	{
		super(id, model, options);
	}

	// Properties //

	/**
	 * Gets the underlying widget selector
	 * 
	 * @see JQueryBehavior#getSelector()
	 */
	public abstract String getSelector();
}
