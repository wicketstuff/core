package org.wicketstuff.event.annotation;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.lang.Args;

import java.util.List;

/**
 * Base class for implementations of {@link ITypedEvent}.
 * 
 * @param <T>
 *            type of the payload
 */
public class AbstractTypedEvent<T> extends AbstractPayloadEvent<T> implements ITypedEvent
{

	private final List<Class<?>> types;

	protected AbstractTypedEvent(final AjaxRequestTarget target, final T payload,
			final List<Class<?>> types)
	{
		super(target, payload);
		Args.notNull(types, "types");
		this.types = types;
	}

	@Override
	public List<Class<?>> getTypes()
	{
		return types;
	}

}
