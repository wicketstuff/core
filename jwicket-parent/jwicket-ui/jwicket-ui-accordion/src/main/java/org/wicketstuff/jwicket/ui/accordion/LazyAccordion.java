package org.wicketstuff.jwicket.ui.accordion;


import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;


public abstract class LazyAccordion<T extends Serializable> extends AbstractAccordion<T> {

	private static final long serialVersionUID = 1L;


	public LazyAccordion(final String id, final IModel<? extends List<T>> list) {
		this(id, list, -1);
	}

	public LazyAccordion(final String id, final IModel<? extends List<T>> list, final int expanded) {
		super(id, list, expanded);
		
		getAccordionBehavior().setAutoHeight(false);
		getAccordionBehavior().setCollapsible(true);
		getAccordionBehavior().setActive(-1);
	}


	@Override
	protected AccordionBehavior initAccordionBehavior() {
		return new AccordionBehavior() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onExpand(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand) {
				Component contentAnchor = ((WebMarkupContainer)contentToExpand).get("contentAnchor");
				if (contentAnchor != null) {
					Component content = ((WebMarkupContainer)contentAnchor).get("content");
					// The Model is parked in the content
					if (content != null && content instanceof LazyAccordion<?>.ModelParkingLot) {
						// Currently there is only a placeholder for the content. Replace the
						// Placeholder with real content
						ModelParkingLot modelParkingLot = (ModelParkingLot)content;
						((WebMarkupContainer)contentAnchor).addOrReplace(getLazyContent("content", modelParkingLot.getModel()));
						target.addComponent(contentAnchor);
					}
					// else: this was already expanded before
				}
			}
		};
	}



	protected Component getHeader(final String id, final IModel<T> t) {
		if (t != null)
			return new Label(id, String.valueOf(t.getObject()));
		else
			return new Label(id, "Header: <null>");
	}


	protected Component getContent(final String id, final IModel<T> t) {
		return new ModelParkingLot(id, t); // parking lot for the model
	}


	protected abstract Component getLazyContent(final String id, final IModel<T> t);



	private class ModelParkingLot extends WebMarkupContainer {
		private static final long serialVersionUID = 1L;

		public ModelParkingLot(final String id, final IModel<T> model) {
			super(id, model);
		}

		@SuppressWarnings("unchecked")
		public IModel<T> getModel() {
			return (IModel<T>)getDefaultModel();
		}
	}
}
