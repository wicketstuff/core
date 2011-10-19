package com.inmethod.grid.column.editable;

import org.apache.wicket.Component;
import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.inmethod.grid.column.PropertyColumn;

/**
 * Property column that uses a {@link DateTextFieldPanel} as cell component
 * when the item is selected.
 *
 * @author Tom Burton
 *
 * TODO: rename to EditableDateColumn
 */
public class DatePropertyColumn extends EditablePropertyColumn {

	private static final long serialVersionUID = 1L;

  private DateConverter converter;

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
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
   * @param dc
   *            DateConverter to use to display a properly formatted date/time
	 */
	public DatePropertyColumn(String columnId, IModel headerModel,
                            String propertyExpression, String sortProperty,
                            DateConverter dc)
  {
		super(columnId, headerModel, propertyExpression, sortProperty);
    converter = dc;
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
   * @param dc
   *            DateConverter to use to display a properly formatted date/time
	 */
	public DatePropertyColumn(String columnId, IModel headerModel,
                            String propertyExpression,
                            DateConverter dc)
  {
		super(columnId, headerModel, propertyExpression);
    converter = dc;
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 * 
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
   * @param dc
   *            DateConverter to use to display a properly formatted date/time
	 */
	public DatePropertyColumn(IModel headerModel, String propertyExpression,
                            String sortProperty,
                            DateConverter dc)
  {
		super(headerModel, propertyExpression, sortProperty);
    converter = dc;
	}

  /**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 *
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
   * @param dc
   *            DataConverter for how to properly display the Date/Time info
	 */
	public DatePropertyColumn(IModel headerModel, String propertyExpression,
                            DateConverter dc)
  {
		super(headerModel, propertyExpression);
    converter = dc;
	}

  /** {@inheritDoc} */
  @Override
	protected EditableCellPanel newCellPanel(String componentId, IModel rowModel,
                                           IModel cellModel)
  {
		return new DateTextFieldPanel(componentId, cellModel, rowModel, this,
                                  converter);
	}

  /** {@inheritDoc} */
  @Override
  protected CharSequence convertToString(Object obj)
  {
    if (null != obj) { return converter.convertToString(obj,getLocale()); }
    else { return ""; }
  }

}
