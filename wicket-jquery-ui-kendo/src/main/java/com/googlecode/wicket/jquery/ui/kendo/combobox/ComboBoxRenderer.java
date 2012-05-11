package com.googlecode.wicket.jquery.ui.kendo.combobox;

import org.apache.wicket.util.lang.PropertyResolver;

public class ComboBoxRenderer<O> implements IComboBoxRenderer<O>
{
	private static final long serialVersionUID = 1L;

	private final String textExpression;
	private final String valueExpression;

	public ComboBoxRenderer()
	{
		this.textExpression = null;
		this.valueExpression = null;
	}
	
	public ComboBoxRenderer(String textExpression)
	{
		this.textExpression = textExpression;
		this.valueExpression = null;
	}

	public ComboBoxRenderer(String textExpression, String valueExpression)
	{
		this.textExpression = textExpression;
		this.valueExpression = valueExpression;
	}

	public String getText(O object)
	{
		return this.getText(object, this.textExpression);
	};
	
	@Override
	public String getText(O object, String expression)
	{
		if (expression != null)
		{
			Object value = PropertyResolver.getValue(expression, object); //if the object is null, null is returned
			
			if (value != null)
			{
				return value.toString();
			}
		}

		if (object != null)
		{
			return object.toString();
		}

		return "";
	}

	/**
	 * @param object 
	 * @return the index as String if the object is null or the property expression has not been found
	 * 
	 */
	@Override
	public String getValue(O object, int index)
	{
		if (this.valueExpression != null)
		{
			Object value = PropertyResolver.getValue(this.valueExpression, object); //if the object is null, null is returned
			
			if (value != null)
			{
				return value.toString();
			}
		}

		if (object != null)
		{
			return this.getText(object);
		}

		return Integer.toString(index);
	}
}