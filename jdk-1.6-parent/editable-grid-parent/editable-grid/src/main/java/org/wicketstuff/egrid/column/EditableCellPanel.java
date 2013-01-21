package org.wicketstuff.egrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class EditableCellPanel<T> extends Panel
{

	private static final long serialVersionUID = 1L;
	public abstract FormComponent<T> getEditableComponent();

	public EditableCellPanel(String id)
	{
		super(id);
	}
}
