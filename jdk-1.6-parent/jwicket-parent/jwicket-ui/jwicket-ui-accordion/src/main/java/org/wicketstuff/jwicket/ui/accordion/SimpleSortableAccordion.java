package org.wicketstuff.jwicket.ui.accordion;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.wicketstuff.jwicket.ui.sortable.SortableBehavior;

import java.io.Serializable;
import java.util.List;


public class SimpleSortableAccordion<T extends Serializable> extends AbstractAccordion<T> {

	private static final long serialVersionUID = 1L;

	SortableBehavior sortableBehavior;

	public SimpleSortableAccordion(final String id, final IModel<? extends List<T>> list) {
		this(id, list, -1);
	}

	public SimpleSortableAccordion(final String id, final IModel<? extends List<T>> list, final int expanded) {
		super(id, list, expanded);
		
		accordion.add(sortableBehavior = new SortableBehavior());
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


	public SortableBehavior getSortableBehavior() {
		return this.sortableBehavior;
	}



	@Override
	protected AccordionBehavior initAccordionBehavior() {
		return new AccordionBehavior() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onExpand(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {
				SimpleSortableAccordion.this.onExpand(target, headerToExpand, contentToExpand, index);
			}
			@Override
			protected void onCollapse(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {
				SimpleSortableAccordion.this.onCollapse(target, headerToExpand, contentToExpand, index);
			}
		};
	}


	@Override
	protected void onExpand(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {}


	@Override
	protected void onCollapse(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {}

}
