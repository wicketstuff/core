/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.pageserializer.common.analyze.reportbuilder;

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