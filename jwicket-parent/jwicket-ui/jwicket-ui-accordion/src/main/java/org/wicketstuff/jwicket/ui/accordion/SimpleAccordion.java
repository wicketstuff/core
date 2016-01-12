package org.wicketstuff.jwicket.ui.accordion;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.List;


public class SimpleAccordion<T extends Serializable> extends AbstractAccordion<T> {

	private static final long serialVersionUID = 1L;

	public SimpleAccordion(final String id, final IModel<? extends List<T>> list) {
		super(id, list);
	}

	public SimpleAccordion(final String id, final IModel<? extends List<T>> list, final int expanded) {
		super(id, list, expanded);
	}


	protected Component getHeader(final String id, final IModel<T> t, final int index) {
		if (t != null)
			return new Label(id, String.valueOf(t.getObject()));
		else
			return new Label(id, "Header: <null>");
	}


	protected Component getContent(final String id, final IModel<T> t, final int index) {
		if (t != null)
			return new Label(id, String.valueOf(t.getObject()));
		else
			return new Label(id, "Content: <null>");
	}



	@Override
	protected AccordionBehavior initAccordionBehavior() {
		return new AccordionBehavior() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onExpand(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {
				SimpleAccordion.this.onExpand(target, headerToExpand, contentToExpand, index);
			}
			@Override
			protected void onCollapse(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {
				SimpleAccordion.this.onCollapse(target, headerToExpand, contentToExpand, index);
			}
		};
	}


	@Override
	protected void onExpand(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {}


	@Override
	protected void onCollapse(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {}

}