package org.wicketstuff.jwicket.ui.accordion;

public class ModelParkingLot extends WebMarkupContainer {
		private static final long serialVersionUID = 1L;

		public ModelParkingLot(final String id, final IModel<T> model) {
			super(id, model);
		}

		@SuppressWarnings("unchecked")
		public IModel<T> getModel() {
			return (IModel<T>)getDefaultModel();
		}
	}

