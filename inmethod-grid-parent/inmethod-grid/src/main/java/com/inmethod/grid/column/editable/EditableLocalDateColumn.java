package com.inmethod.grid.column.editable;

import java.time.LocalDate;
import java.time.format.FormatStyle;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

/**
 * Property column that uses a {@link LocalDateTextFieldPanel} as cell component
 * when the item is selected.
 *
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 */
public class EditableLocalDateColumn<M, I, S> extends EditablePropertyColumn<M, I, LocalDate, S>
{
	private static final long serialVersionUID = 1L;
	private IConverter<LocalDate> converter;

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
	 */
	public EditableLocalDateColumn(String columnId, IModel<String> headerModel,
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
	public EditableLocalDateColumn(String columnId, IModel<String> headerModel,
							String propertyExpression)
	{
		super(columnId, headerModel, propertyExpression);
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
	 */
	public EditableLocalDateColumn(IModel<String> headerModel, String propertyExpression, S sortProperty)
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
	 *            property expression used to get the displayed value for row object
	* @param dc
	*            DataConverter for how to properly display the Date/Time info
	 */
	public EditableLocalDateColumn(IModel<String> headerModel, String propertyExpression)
	{
		super(headerModel, propertyExpression);
	}

	@Override
	protected EditableCellPanel<M, I, LocalDate, S> newCellPanel(String componentId, IModel<I> rowModel,
		IModel<LocalDate> cellModel)
	{
		LocalDateTextFieldPanel<M, I, S> ldf = new LocalDateTextFieldPanel<>(componentId, cellModel, rowModel, this, FormatStyle.MEDIUM);
		converter = ldf.getConverter();
		return ldf;
	}

	@Override
	protected <C> CharSequence convertToString(C date)
	{
		if (null != date)
		{
			return converter.convertToString((LocalDate)date, getLocale());
		}
		else
		{
			return "";
		}
	}

}
