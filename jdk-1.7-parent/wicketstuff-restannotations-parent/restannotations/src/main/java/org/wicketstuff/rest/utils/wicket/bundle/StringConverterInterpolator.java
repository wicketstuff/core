/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.utils.wicket.bundle;

import java.util.Locale;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;

/**
 * Utility class to convert strings to values and vice-versa. It relies on 
 * the Wicket converters registered for the current application.
 * 
 * @author andrea del bene
 *
 */
public class StringConverterInterpolator extends MapVariableInterpolator
{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	private final Locale locale;

	public StringConverterInterpolator(String string, Map<?, ?> variables,
		boolean exceptionOnNullVarValue, Locale locale)
	{
		super(string, variables, exceptionOnNullVarValue);
		this.locale = locale;
	}

	@Override
	protected String getValue(String variableName)
	{
		Object value = super.getValue(variableName);

		if (value == null)
		{
			return null;
		}
		else if (value instanceof String)
		{
			// small optimization - no need to bother with conversion
			// for String vars, e.g. {label}
			return (String)value;
		}
		else
		{
			IConverter converter = getConverter(value.getClass());
			if (converter == null)
			{
				return Strings.toString(value);
			}
			else
			{
				return converter.convertToString(value, getLocale());
			}
		}
	}

	private IConverter<?> getConverter(Class<? extends Object> clazz)
	{
		return Application.get().getConverterLocator().getConverter(clazz);
	}

	public Locale getLocale()
	{
		return locale;
	}

}
