package org.wicketstuff.twitter.util;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Till Freier
 * 
 */
public abstract class PageParameterUtil
{
	/**
	 * 
	 * @param parameters
	 * @param name
	 * @param model
	 * @return
	 */
	public static PageParameters addModelParameter(final PageParameters parameters,
		final String name, final IModel<?> model)
	{
		if (parameters == null || name == null || model == null || model.getObject() == null)
			return parameters;

		if (model.getObject() instanceof Iterable<?>)
			return addModelParameter(parameters, name, (Iterable<?>)model.getObject());

		return parameters.add(name, model.getObject());
	}

	/**
	 * 
	 * @param parameters
	 * @param name
	 * @param it
	 * @return
	 */
	public static PageParameters addModelParameter(final PageParameters parameters,
		final String name, final Iterable<?> it)
	{
		if (parameters == null || name == null || it == null)
			return parameters;

		final StringBuilder sb = new StringBuilder();
		for (final Object value : it)
		{
			if (sb.length() > 0)
				sb.append(',');
			sb.append(value);
		}

		return parameters.add(name, sb.toString());
	}
}
