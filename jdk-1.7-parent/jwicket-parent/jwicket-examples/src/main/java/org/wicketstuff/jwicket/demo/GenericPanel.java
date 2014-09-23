package org.wicketstuff.jwicket.demo;


import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;


public abstract class GenericPanel<T> extends Panel {

	private static final long serialVersionUID = 1L;

	protected GenericPanel(final String id) {
		super(id);
	}

	protected GenericPanel(final String id, final IModel<T> model) {
		super(id, model);
	}


	@SuppressWarnings("unchecked")
	public final T getModelObject() {
		return (T)getDefaultModelObject();
	}

	public final void setModelObject(final T modelObject) {
		setDefaultModelObject(modelObject);
	}

	@SuppressWarnings("unchecked")
	public final IModel<T> getModel() {
		return (IModel<T>)getDefaultModel();
	}

	public final void setModel(final IModel<T> model) {
		setDefaultModel(model);
	}
}
