package com.googlecode.wicket.jquery.core;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.model.IModel;

/**
 * A specialization of {@link JQueryContainer} that knows the type of its model object
 *
 * @param <T> the type of the model object
 */
public abstract class JQueryGenericContainer<T> extends JQueryContainer implements IGenericComponent<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param id the markup id
	 */
	public JQueryGenericContainer(String id)
	{
		super(id);
	}

	/**
	 * Constructor.
	 * 
	 * @param id the markup id
	 * @param model the model
	 */
	public JQueryGenericContainer(String id, IModel<?> model)
	{
		super(id, model);
	}

	// Properties //

	@Override
	@SuppressWarnings("unchecked")
	public IModel<T> getModel()
	{
		return (IModel<T>) this.getDefaultModel();
	}

	@Override
	public void setModel(IModel<T> model)
	{
		this.setDefaultModel(model);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getModelObject()
	{
		return (T) this.getDefaultModelObject();
	}

	@Override
	public void setModelObject(T object)
	{
		this.setDefaultModelObject(object);
	}
}
