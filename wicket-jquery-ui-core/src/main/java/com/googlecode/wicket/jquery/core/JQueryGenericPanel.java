package com.googlecode.wicket.jquery.core;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.model.IModel;

/**
 * A specialization of {@link JQueryPanel} that knows the type of its model object
 *
 * @param <T> the type of the model object
 */
public abstract class JQueryGenericPanel<T> extends JQueryPanel implements IGenericComponent<T>
{
	private static final long serialVersionUID = 1L;

	public JQueryGenericPanel(String id)
	{
		super(id);
	}

	public JQueryGenericPanel(String id, Options options)
	{
		super(id, options);
	}

	public JQueryGenericPanel(String id, IModel<?> model, Options options)
	{
		super(id, model, options);
	}

	public JQueryGenericPanel(String id, IModel<?> model)
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
