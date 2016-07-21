package org.wicketstuff.mbeanview;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.Objects;

/**
 * A converter to MBean values.
 */
@SuppressWarnings("rawtypes")
public class ValueConverter extends AbstractConverter
{

	private static final long serialVersionUID = 1L;
	
	private String type;

	public ValueConverter(String type)
	{
		this.type = type;
	}

	@Override
	protected Class getTargetType()
	{
		return Object.class;
	}

	@Override
	public Object convertToObject(String value, Locale locale) throws ConversionException
	{
		if (value == null)
		{
			return null;
		}
		else
		{
			try
			{
				return Objects.convertValue(value, getTypeFromJmx(type));
			}
			catch (Exception e)
			{
				throw newConversionException("invalid value", value, locale);
			}
		}
	}

	public static Class<?> getTypeFromJmx(String jmxType) throws Exception
	{
		if ("boolean".equals(jmxType))
		{
			return Boolean.class;
		}
		else if ("int".equals(jmxType))
		{
			return Integer.class;
		}
		else if ("double".equals(jmxType))
		{
			return Double.class;
		}
		else if ("long".equals(jmxType))
		{
			return Long.class;
		}
		else
		{
			return Class.forName(jmxType);
		}
	}
}