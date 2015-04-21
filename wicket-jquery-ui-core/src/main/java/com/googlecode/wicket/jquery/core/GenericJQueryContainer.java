package com.googlecode.wicket.jquery.core;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.model.IModel;

/**
 * A specialization of {@link JQueryContainer} that knows the type of its model object
 *
 * @param <T>
 *     the type of the model object
 */
public abstract class GenericJQueryContainer<T> extends JQueryContainer implements IGenericComponent<T> {

    public GenericJQueryContainer(String id) {
        super(id);
    }

    public GenericJQueryContainer(String id, IModel<?> model) {
        super(id, model);
    }

    // Properties //
    /**
     * Gets the {@link IModel}
     *
     * @return the {@link IModel}
     */
    @SuppressWarnings("unchecked")
    @Override
    public IModel<T> getModel()
    {
        return (IModel<T>) this.getDefaultModel();
    }

    @Override
    public void setModel(IModel<T> model) {
        setDefaultModel(model);
    }

    /**
     * Gets the {@link IModel}
     *
     * @return the {@link IModel}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getModelObject()
    {
        return (T) this.getDefaultModelObject();
    }

    /**
     * Sets the model object
     *
     * @param object The model object
     */
    @Override
    public void setModelObject(T object)
    {
        this.setDefaultModelObject(object);
    }


}
