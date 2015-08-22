package com.inmethod.grid.column;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.util.string.Strings;

import com.inmethod.grid.IRenderable;

/**
 * A lightweight column that displays a property of row object specified by an property expression.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * @param <P>
 *            type of the property
 * 
 * @author Matej Knopp
 */
public class PropertyColumn<M, I, P, S> extends AbstractLightWeightColumn<M, I, S>
{

	private static final long serialVersionUID = 1L;

	private final String propertyExpression;

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
	public PropertyColumn(String columnId, IModel<String> headerModel, String propertyExpression,
		S sortProperty)
	{
		super(columnId, headerModel, sortProperty);
		this.propertyExpression = propertyExpression;
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
	public PropertyColumn(String columnId, IModel<String> headerModel, String propertyExpression)
	{
		this(columnId, headerModel, propertyExpression, null);
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
	public PropertyColumn(IModel<String> headerModel, String propertyExpression, S sortProperty)
	{
		this(propertyExpression, headerModel, propertyExpression, sortProperty);
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 * 
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 */
	public PropertyColumn(IModel<String> headerModel, String propertyExpression)
	{
		this(propertyExpression, headerModel, propertyExpression);
	}

	private boolean escapeMarkup = true;

	/**
	 * Sets whether the markup will be escaped. Set to <code>false</code> if the property contains
	 * html snippets that need to be rendered as html (without being escaped).
	 * 
	 * @param escape
	 * @return <code>this</code> (useful for method chaining)
	 */
	public PropertyColumn<M, I, P, S> setEscapeMarkup(boolean escape)
	{
		escapeMarkup = escape;
		return this;
	}

	/**
	 * Returns whether the markup will be escaped.
	 * 
	 * @return <code>true</code. if the markup will be escaped, <code>false</code> otherwise
	 */
	public boolean isEscapeMarkup()
	{
		return escapeMarkup;
	}

	protected P getProperty(Object object, String propertyExpression)
	{
		return (P)PropertyResolver.getValue(propertyExpression, object);
	}

	protected I getModelObject(IModel<I> rowModel)
	{
		return rowModel.getObject();
	}

	private CharSequence getValue(IModel<I> rowModel)
	{
		I rowObject = getModelObject(rowModel);
		P property = null;
		if (rowObject != null)
		{
			try
			{
				property = getProperty(rowObject, getPropertyExpression());
			}
			catch (NullPointerException e)
			{

			}
		}
		CharSequence string = convertToString(property);
		if (isEscapeMarkup() && string != null)
		{
			string = Strings.escapeMarkup(string.toString());
		}
		return string;
	}

	protected <C> IConverter<C> getConverter(Class<C> type)
	{
		return Application.get().getConverterLocator().getConverter(type);
	}

	protected Locale getLocale()
	{
		return Session.get().getLocale();
	}

	protected <C> CharSequence convertToString(C object)
	{
		if (object != null)
		{
			@SuppressWarnings("unchecked")
			Class<C> cKlazz = (Class<C>)object.getClass();
			IConverter<C> converter = getConverter(cKlazz);
			return converter.convertToString(object, getLocale());
		}
		else
		{
			return "";
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IRenderable<I> newCell(IModel<I> rowModel)
	{
		return new IRenderable<I>()
		{
			public void render(IModel<I> rowModel, Response response)
			{
				CharSequence value = getValue(rowModel);
				if (value != null)
				{
					response.write(value);
				}
			}
		};
	}

	/**
	 * Returns the property expression.
	 * 
	 * @return property expression
	 */
	public String getPropertyExpression()
	{
		return propertyExpression;
	}
}
