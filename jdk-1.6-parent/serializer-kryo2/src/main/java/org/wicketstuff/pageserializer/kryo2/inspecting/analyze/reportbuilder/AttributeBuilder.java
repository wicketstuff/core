package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * builder for generic typed attributes
 * @author mosmann
 *
 */
public class AttributeBuilder
{
	Map<Class<? extends Enum>, Enum> attributes = new HashMap<Class<? extends Enum>, Enum>();
	Map<TypedAttribute, Serializable> typedAttributes = new HashMap<TypedAttribute, Serializable>();

	public AttributeBuilder set(Enum<?> attribute)
	{
		attributes.put(attribute.getClass(), attribute);
		return this;
	}

	public <T extends Enum<T>> T get(T defaultValue)
	{
		T ret = (T)attributes.get(defaultValue.getClass());
		return ret != null ? ret : defaultValue;
	}

	public <T extends Serializable> AttributeBuilder set(TypedAttribute<T> type, T value)
	{
		typedAttributes.put(type, value);
		return this;
	}

	public <T extends Serializable> T get(TypedAttribute<T> type, T defaultValue)
	{
		T ret = (T)typedAttributes.get(type);
		return ret != null ? ret : defaultValue;
	}

	public IAttributes build()
	{
		return new ImmutableAttributes(attributes, typedAttributes);
	}

	private static class ImmutableAttributes implements IAttributes
	{
		private final Map<Class<? extends Enum>, Enum> attributes;
		private final Map<TypedAttribute, Serializable> typedAttributes;

		private ImmutableAttributes(Map<Class<? extends Enum>, Enum> attributes,
			Map<TypedAttribute, Serializable> typedAttributes)
		{
			final Map<Class<? extends Enum>, Enum> attributesCopy = new HashMap<Class<? extends Enum>, Enum>();
			attributesCopy.putAll(attributes);
			final Map<TypedAttribute, Serializable> typedAttributesCopy = new HashMap<TypedAttribute, Serializable>();
			typedAttributesCopy.putAll(typedAttributes);

			this.attributes = attributesCopy;
			this.typedAttributes = typedAttributesCopy;
		}

		@Override
		public <T extends Enum<T>> T get(T defaultValue)
		{
			T ret = (T)attributes.get(defaultValue.getClass());
			return ret != null ? ret : defaultValue;
		}

		@Override
		public <T extends Serializable> T get(TypedAttribute<T> type, T defaultValue)
		{
			T ret = (T)typedAttributes.get(type);
			return ret != null ? ret : defaultValue;
		}
	}


}