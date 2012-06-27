package com.inmethod.grid.column.editable;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.model.IModel;

/**
 * Property column that uses a {@link CheckBoxPanel} as cell component
 * when the item is selected.
 *
 * @author Tom Burton
 */
public class EditableCheckBoxColumn<M, I, S> extends EditablePropertyColumn<M, I, Boolean, S>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState}
   *            to indicate that the column is being sorted
	 */
	public EditableCheckBoxColumn(String columnId, IModel<String> headerModel,
                                String propertyExpression, S sortProperty)
  {
		super(columnId, headerModel, propertyExpression, sortProperty);
	}

	/**
	 * Constructor.
	 * 
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 */
	public EditableCheckBoxColumn(String columnId, IModel<String> headerModel,
                                String propertyExpression)
  {
		super(columnId, headerModel, propertyExpression);
	}

	/**
	 * Constructor. The column id is omitted in this constructor,
   * because the property expression is
	 * used as column id.
	 * 
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState}
   *            to indicate that the column is being sorted
	 */
	public EditableCheckBoxColumn(IModel<String> headerModel, String propertyExpression, S sortProperty)
  {
		super(headerModel, propertyExpression, sortProperty);
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 * 
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row
   *            object
	 */
	public EditableCheckBoxColumn(IModel<String> headerModel,
                                String propertyExpression)
  {
		super(headerModel, propertyExpression);
	}

  /** {@inheritDoc} */
	@Override
  protected EditableCellPanel<M, I, Boolean, S> newCellPanel(String componentId, IModel<I> rowModel,
                                           IModel<Boolean> cellModel)
  {	return new CheckBoxPanel<M, I, S>(componentId, cellModel, rowModel, this); }

}
