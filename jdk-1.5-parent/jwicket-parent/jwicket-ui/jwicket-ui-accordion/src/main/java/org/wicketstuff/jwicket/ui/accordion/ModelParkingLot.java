package org.wicketstuff.jwicket.ui.accordion;
import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

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

