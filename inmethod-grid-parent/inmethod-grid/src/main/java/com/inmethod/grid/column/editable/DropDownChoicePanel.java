/**
 *
 */
package com.inmethod.grid.column.editable;

import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.column.AbstractColumn;

/**
 * Backing Panel for {@link DropDownChoiceColumn} with a {@link DropDownChoice}
 * that updates the property of the row immediately after the user leaves the
 * field.
 *
 * @author Tom Burton
 */
public class DropDownChoicePanel<M, I, P, S> extends EditableCellPanel<M, I, P, S> { // Does serialVersionUID really need to be explicitly set
	private static final long serialVersionUID = 1L;

	private static final String DropDownChoice_ID = "DropDownChoice";

	protected class DefaultDropDownChoice<T> extends DropDownChoice<T> {
		private static final long serialVersionUID = 1L;

		protected DefaultDropDownChoice(String id, IModel<T> object, IModel<? extends List<T>> choices) {
			super(id, object, choices);
		}

		protected DefaultDropDownChoice(String id, IModel<T> object, IModel<? extends List<T>> choices,
				IChoiceRenderer<T> renderer) {
			super(id, object, choices, renderer);
		}

		/** {@inheritDoc} */
		@Override
		protected void onComponentTag(ComponentTag tag) {
			super.onComponentTag(tag);

			if (!isValid()) {
				tag.put("class", "imxt-invalid");
				FeedbackMessage message = getFeedbackMessages().first();
				if (message != null) {
					tag.put("title", message.getMessage().toString());
				}
			}
		}
	}

	/**
	 * Constructor
	 *
	 * @param id       component id
	 * @param model    model for the field
	 * @param rowModel model for the data row
	 * @param column   column to which this panel belongs
	 * @param choices  choices for displaying in the drop down list
	 * @param renderer how to display the data in the drop down
	 */
	public DropDownChoicePanel(String id, final IModel<P> model, IModel<I> rowModel, AbstractColumn<M, I, S> column,
			IModel<? extends List<P>> choices, IChoiceRenderer<P> renderer) {
		super(id, column, rowModel);

		DropDownChoice<P> ddc;
		if (null == renderer) {
			ddc = newDropDownChoice(DropDownChoice_ID, model, choices);
		} else {
			ddc = newDropDownChoice(DropDownChoice_ID, model, choices, renderer);
		}
		ddc.setOutputMarkupId(true);
		ddc.setLabel(column.getHeaderModel());
		add(ddc);
	}

	/**
	 * newDropDownChoice
	 *
	 * @param id      component id
	 * @param model   field model
	 * @param choices options to display in the drop down
	 * @return DropDownChoice FormComponent
	 */
	protected DropDownChoice<P> newDropDownChoice(String id, IModel<P> model, IModel<? extends List<P>> choices) {
		return new DefaultDropDownChoice<P>(id, model, choices);
	}

	/**
	 * newDropDownChoice
	 *
	 * @param id       component id
	 * @param model    field model
	 * @param choices  options to display in the drop down
	 * @param renderer how to display the data
	 * @return DropDownChoice FormComponent
	 */
	protected DropDownChoice<P> newDropDownChoice(String id, IModel<P> model, IModel<? extends List<P>> choices,
			IChoiceRenderer<P> renderer) {
		return new DefaultDropDownChoice<P>(id, model, choices, renderer);
	}

	/** {@inheritDoc} */
	@Override
	public FormComponent<P> getEditComponent() {
		return (FormComponent<P>) get(DropDownChoice_ID);
	}

}
