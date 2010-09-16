package com.inmethod.grid.column;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.util.string.Strings;

import com.inmethod.grid.IRenderable;

/**
 * A lightweight column that displays a property of row object specified by an
 * property expression.
 * 
 * @author Matej Knopp
 */
public class PropertyColumn extends AbstractLightWeightColumn
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
     *            property expression used to get the displayed value for row
     *            object
     * @param sortProperty
     *            optional string that will be returned by {@link ISortState} to
     *            indicate that the column is being sorted
     */
    public PropertyColumn(String columnId, IModel headerModel,
            String propertyExpression, String sortProperty)
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
     *            property expression used to get the displayed value for row
     *            object
     */
    public PropertyColumn(String columnId, IModel headerModel,
            String propertyExpression)
    {
        this(columnId, headerModel, propertyExpression, null);
    }

    /**
     * Constructor. The column id is omitted in this constructor, because the
     * property expression is used as column id.
     * 
     * @param headerModel
     *            model for column header
     * @param propertyExpression
     *            property expression used to get the displayed value for row
     *            object
     * @param sortProperty
     *            optional string that will be returned by {@link ISortState} to
     *            indicate that the column is being sorted
     */
    public PropertyColumn(IModel headerModel, String propertyExpression,
            String sortProperty)
    {
        this(propertyExpression, headerModel, propertyExpression, sortProperty);
    }

    /**
     * Constructor. The column id is omitted in this constructor, because the
     * property expression is used as column id.
     * 
     * @param headerModel
     *            model for column header
     * @param propertyExpression
     *            property expression used to get the displayed value for row
     *            object
     */
    public PropertyColumn(IModel headerModel, String propertyExpression)
    {
        this(propertyExpression, headerModel, propertyExpression);
    }

    private boolean escapeMarkup = true;

    /**
     * Sets whether the markup will be escaped. Set to <code>false</code> if the
     * property contains html snippets that need to be rendered as html (without
     * being escaped).
     * 
     * @param escape
     * @return <code>this</code> (useful for method chaining)
     */
    public PropertyColumn setEscapeMarkup(boolean escape)
    {
        this.escapeMarkup = escape;
        return this;
    }

    /**
     * Returns whether the markup will be escaped.
     * 
     * @return
     *         <code>true</code. if the markup will be escaped, <code>false</code>
     *         otherwise
     */
    public boolean isEscapeMarkup()
    {
        return escapeMarkup;
    }

    protected Object getProperty(Object object, String propertyExpression)
    {
        return PropertyResolver.getValue(propertyExpression, object);
    }

    protected Object getModelObject(IModel rowModel)
    {
        return rowModel.getObject();
    }

    private CharSequence getValue(IModel rowModel)
    {
        Object rowObject = getModelObject(rowModel);
        Object property = null;
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

    protected IConverter getConverter(Class<?> type)
    {
        return Application.get().getConverterLocator().getConverter(type);
    }

    protected Locale getLocale()
    {
        return Session.get().getLocale();
    }

    protected CharSequence convertToString(Object object)
    {
        if (object != null)
        {
            IConverter converter = getConverter(object.getClass());
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
    public IRenderable newCell(IModel rowModel)
    {
        return new IRenderable()
        {
            public void render(IModel rowModel, Response response)
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
