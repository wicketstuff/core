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
 * Panel with a DropDownChoice that updates the property of the row immediately after
 * user leaves the field.
 *
 * @author Tom Burton
 * TODO: make Generic
 */
public class DropDownChoicePanel<M, I, P> extends EditableCellPanel<M, I, P>
{ //Does serialVersionUID really need to be explicitly set
	private static final long serialVersionUID = 1L;

	private static final String DropDownChoice_ID = "DropDownChoice";

	protected class DefaultDropDownChoice<P> extends DropDownChoice<P>
  {
		private static final long serialVersionUID = 1L;

		/** {@inheritDoc} */
		protected DefaultDropDownChoice(String id, IModel<P> object,
                                    IModel<? extends List<P>> choices)
    {
			super(id, object, choices);
		}

    /** {@inheritDoc} */
		protected DefaultDropDownChoice(String id, IModel<P> object,
                                    IModel<? extends List<P>> choices,
                                    IChoiceRenderer<P> renderer)
    {
			super(id, object, choices, renderer);
		}

    /** {@inheritDoc} */
		@Override
		protected void onComponentTag(ComponentTag tag)
    {
			super.onComponentTag(tag);

			if (!isValid())
      {
				tag.put("class", "imxt-invalid");
				FeedbackMessage message = getFeedbackMessages().first();
				if (message != null)
        {
          tag.put("title", message.getMessage().toString());
				}
			}
		}
	}

	/**
	 * Constructor
	 * @param id
	 * 		component id
	 * @param model
	 * 		model for the field
   * @param rowModel
   *    model for the data row
	 * @param column
	 * 		column to which this panel belongs
   * @param choices
   *    choices for displaying in the drop down list
   * @param renderer
   *    how to display the data in the drop down
	 */
	public DropDownChoicePanel(String id, final IModel<P> model, IModel<I> rowModel,
                             AbstractColumn column,
                             IModel<? extends List<P>> choices,
                             IChoiceRenderer<P> renderer)
  {
		super(id, column, rowModel);
		
		DropDownChoice ddc;
    if ( null == renderer )
    { ddc = newDropDownChoice(DropDownChoice_ID, model, choices); }
    else
    { ddc = newDropDownChoice(DropDownChoice_ID, model, choices, renderer); }
		ddc.setOutputMarkupId(true);
		ddc.setLabel(column.getHeaderModel());
		add(ddc);
	}

	/** newDropDownChoice
	 *  @param id component id
	 *  @param model field model
   *  @param choices options to display in the drop down
	 *  @return  DropDownChoice FormComponent
	 */
	protected DropDownChoice newDropDownChoice(String id, IModel<P> model,
                                             IModel<? extends List<P>> choices)
  {
		return new DefaultDropDownChoice(id, model, choices);
	}

  /** newDropDownChoice
	 *  @param id component id
	 *  @param model field model
   *  @param choices options to display in the drop down
   *  @param renderer how to display the data
	 *  @return  DropDownChoice FormComponent
	 */
	protected DropDownChoice newDropDownChoice(String id, IModel<P> model,
                                             IModel<? extends List<P>> choices,
                                             IChoiceRenderer<P> renderer)
  {
		return new DefaultDropDownChoice(id, model, choices, renderer);
	}

  /** {@inheritDoc} */
	@Override
	public FormComponent getEditComponent()
  {
		return (FormComponent) get(DropDownChoice_ID);
	}

}
