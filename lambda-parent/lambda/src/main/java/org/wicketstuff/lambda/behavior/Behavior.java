package org.wicketstuff.lambda.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;

/**
 * Subclass of and drop in replacement for
 * {@link org.apache.wicket.behavior.Behavior} that uses a lambda for event
 * handling.
 * 
 * @see org.apache.wicket.behavior.Behavior
 */
public class Behavior extends org.apache.wicket.behavior.Behavior {

	private static final long serialVersionUID = 1L;

	private SerializableBiConsumer<Component, ComponentTag> componentTagHandler;

	/**
	 * Constructor
	 */
	public Behavior() {
		this(null);
	}

	/**
	 * Constructor
	 * 
	 * @param componentTagHandler
	 *            handler to call to process the tag
	 */
	public Behavior(SerializableBiConsumer<Component, ComponentTag> componentTagHandler) {
		super();
		this.componentTagHandler = componentTagHandler;
	}

	/**
	 * Sets the handler to call to process the tag.
	 * 
	 * @param componentTagHandler
	 *            handler to call to process the tag
	 * @return this
	 */
	public Behavior componentTagHandler(SerializableBiConsumer<Component, ComponentTag> componentTagHandler) {
		this.componentTagHandler = componentTagHandler;
		return this;
	}

	@Override
	public final void onComponentTag(Component component, ComponentTag tag) {
		if (componentTagHandler != null) {
			componentTagHandler.accept(component, tag);
		} else {
			throw new WicketRuntimeException("componentTagHandler not specified");
		}
	}

}
