package org.wicketstuff.jwicket.ui.accordion;


import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;


public class SimpleAccordion<T extends Serializable> extends AbstractAccordion<T> {

	private static final long serialVersionUID = 1L;

	public SimpleAccordion(final String id, final IModel<? extends List<T>> list) {
		super(id, list);
	}



	protected Component getHeader(final String id, final IModel<T> t) {
		if (t != null)
			return new Label(id, String.valueOf(t.getObject()));
		else
			return new Label(id, "Header: <null>");
	}


	protected Component getContent(final String id, final IModel<T> t) {
		if (t != null)
			return new Label(id, String.valueOf(t.getObject()));
		else
			return new Label(id, "Content: <null>");
	}



	@Override
	protected AccordionBehavior getAccordionBehavior() {
		return new AccordionBehavior();
	}

}
