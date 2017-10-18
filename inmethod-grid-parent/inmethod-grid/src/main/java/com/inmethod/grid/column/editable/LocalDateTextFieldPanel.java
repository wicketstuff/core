package com.inmethod.grid.column.editable;

import java.time.LocalDate;
import java.time.format.FormatStyle;

import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import com.inmethod.grid.column.AbstractColumn;

/**
 * Backing Panel for {@link EditableLocalDateColumn}
 * Panel with a LocalDateTextField that updates the property of the row immediately after
 * user leaves the field.
 * Based on (Read: Copy-Paste Modify) {@link EditablePropertyColumn}
 *
 * @author Tom Burton
 */
public class LocalDateTextFieldPanel<M, I, S> extends EditableCellPanel<M, I, LocalDate, S>
{
	private static final long serialVersionUID = 1L;
	private static final String DateTextField_ID = "dateTextField";
	private IConverter<LocalDate> converter;

	protected class DefaultDateTextField extends LocalDateTextField
	{
		private static final long serialVersionUID = 1L;

		/** Constructor for DefaultDateTextField
		 *  @param id component Id
		 *  @param object model to be edited
		 */
		protected DefaultDateTextField(String id, IModel<LocalDate> object)
		{
			super(id, object, FormatStyle.MEDIUM);
		}

		/** Constructor for DefaultDateTextField
		 *  @param id component Id
		 *  @param object model to be edited
		 *  @param pattern Date pattern to use during conversion
		 *  @see LocalDateTextField#LocalDateTextField(String, IModel, String)
		 */
		protected DefaultDateTextField(String id, IModel<LocalDate> object, String pattern)
		{
			super(id, object, pattern);
		}

		/** Constructor for DefaultDateTextField
		 *  @param id component Id
		 *  @param date model to be edited
		 *  @param style date style to use for date display
		 *  @see LocalDateTextField#LocalDateTextField(String, IModel, FormatStyle)
		 */
		protected DefaultDateTextField(String id, IModel<LocalDate> date, FormatStyle style)
		{
			super(id, date, style);
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

		@SuppressWarnings("unchecked")
		@Override
		protected IConverter<?> createConverter(Class<?> clazz) {
			converter = (IConverter<LocalDate>)super.createConverter(clazz);
			return converter;
		}
	}

	/**
	 * Constructor
	 * @param id
	 * 		component id
	 * @param date
	 * 		date for the field
	 * @param rowModel
	 *    date for the data row
	 * @param column
	 * 		column to which this panel belongs
	 * @param style
	 *    date style to display
	 */
	public LocalDateTextFieldPanel(String id, final IModel<LocalDate> date, IModel<I> rowModel,
		AbstractColumn<M, I, S> column, FormatStyle style)
	{
		super(id, column, rowModel);

		LocalDateTextField tf = newDateTextField(DateTextField_ID, date, style);
		tf.setOutputMarkupId(true);
		tf.setLabel(column.getHeaderModel());
		add(tf);
	}

	/** newDateTextField
	 * 
	 *  @param id component Id
	 *  @param date date of the field to be edited
	 *  @param dc DateConverter
	 *  @see DateTextField#DateTextField(String, IModel, FormatStyle)
	 *  @return DateTextField
	 */
	protected LocalDateTextField newDateTextField(final String id, final IModel<LocalDate> date, FormatStyle style)
	{
		return new DefaultDateTextField(id, date, style);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FormComponent<LocalDate> getEditComponent() {
		return (FormComponent<LocalDate>) get(DateTextField_ID);
	}

	public IConverter<LocalDate> getConverter() {
		return converter;
	}
}
