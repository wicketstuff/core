package org.wicketstuff.lambda.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.danekja.java.util.function.serializable.SerializableFunction;

/**
 * {@link SerializableBiConsumer} to use with {@link Behavior} for
 * setting/changing an attribute value.
 */
public class AttributeHandler implements SerializableBiConsumer<Component, ComponentTag> {

	private static final long serialVersionUID = 1L;

	private String name;
	private SerializableFunction<String, CharSequence> attributeHandler;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of the attribute
	 * @param attributeHandler
	 *            handler to call to set/replace the attribute value
	 */
	public AttributeHandler(String name, SerializableFunction<String, CharSequence> attributeHandler) {
		this.name = name;
		this.attributeHandler = attributeHandler;
	}

	@Override
	public void accept(Component component, ComponentTag tag) {
		if (tag.getType() != TagType.CLOSE) {
			String oldValue = tag.getAttribute(name);
			tag.put(name, attributeHandler.apply(oldValue));
		}
	}

}
