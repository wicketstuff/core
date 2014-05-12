/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.context;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * @author zruchala
 *
 */
class FieldValue {

	private final Class<?> resolvingClass;
	private final boolean isModel;
	private final Field field;

	private Object value;
	private boolean resolved = false;

	public static FieldValue of(Field pField) {
		Class<?> clazz = getRelevantType(pField);

		if (List.class.equals(clazz)) {
			return new CollectionFieldValue(pField, new ArrayList<Object>());
		} else if(Set.class.equals(clazz)) {
			return new CollectionFieldValue(pField, new HashSet<Object>());
		} 
		return new FieldValue(pField);
	}

	private static Class<?> getRelevantType(Field pField) {
		Class<?> clazz = pField.getType();
		if (Instance.class.isAssignableFrom(clazz)) {
			clazz = getGenericType(pField.getGenericType(), 1);
		}
		return clazz;
	}

	private FieldValue(Field pField) {
		resolvingClass = getResolvingClass(pField);
		isModel = isModel(pField);
		field = pField;
	}

	private Class<?> getResolvingClass(Field pField) {
		Class<?> clazz = pField.getType();

		int depth = 1;
		if (Instance.class.isAssignableFrom(clazz)) {
			clazz = getGenericType(pField.getGenericType(), 1);
			depth = 2;
		}

		if (List.class.equals(clazz) || Set.class.equals(clazz) ||
					IModel.class.isAssignableFrom(clazz)) {
			clazz = getGenericType(pField.getGenericType(), depth);
		}
		return clazz;
	}

	private boolean isModel(Field pField) {
		return IModel.class.isAssignableFrom(getRelevantType(pField));
	}

	private static Class<?> getGenericType(Type pType, int pDepth) {
		if (pDepth < 1) {
			return null;
		}

		int currentDepth = 0;
		Type current = pType;

		do {
			current = current instanceof ParameterizedType ?
					((ParameterizedType) current).getActualTypeArguments()[0] : null;
			currentDepth++;
		} while(currentDepth < pDepth && current != null);

		if (current instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) current).getRawType();
		} else {
			return (Class<?>) current;
		}
	}

	public boolean isResolved() {
		return resolved;
	}
	public Field getField() {
		return field;
	}

	protected void setValue(Object pValue) {
		value = pValue;
		resolved = true;
	}

	public Object getValue() {
		return value;
	}

	public void set(Component pComponent) {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		Object value = getValue();
		if (value != null) {
			try {
				field.set(pComponent, value);
			} catch(IllegalAccessException pException) {
				throw new RuntimeException("An error while injecting object [" + pComponent.toString() +
						"] of type [" + pComponent.getClass().getName() + "]", pException);
			}
		} else {
			if (field.getAnnotation(Context.class).required()) {
				throw new RuntimeException("The required dependency [" + field.getName() + "]  has not been resolved "
						+ "for component [" + pComponent.toString() + "]");
			}
		}
	}

	public void match(Component pCurrent) {
		Object value = null;

		String qualifier = field.getAnnotation(Context.class).qualifier();

		if (resolvingClass.isInstance(pCurrent)) {
			if (qualifier != null && qualifier.length() > 0) {
				List<Qualifier> behaviors = pCurrent.getBehaviors(Qualifier.class);
				if (behaviors != null && !behaviors.isEmpty()) {
					Qualifier qualifierBehavior = behaviors.iterator().next();
					if (qualifier.equals(qualifierBehavior.getName())) {
						value = pCurrent;
					}
				}
			} else {
				value = pCurrent;
			}
		}

		IModel<?> model = pCurrent.getDefaultModel();
		if (value == null) {
			if (model != null && resolvingClass.isInstance(model)) {
				value = model;
			}
		}

		if (value == null) {
			Object modelObject = pCurrent.getDefaultModelObject();
			if (modelObject != null && resolvingClass.isInstance(modelObject)) {
				value = isModel ? model : modelObject;
			}
		}

		if (value != null) {
			setValue(value);
		}
	}

	static class CollectionFieldValue extends FieldValue {

		private final Collection<Object> value;

		private CollectionFieldValue(Field pField, Collection<Object> pCollection) {
			super(pField);

			value = pCollection;
		}

		@Override
		public void setValue(Object pValue) {
			value.add(pValue);
		}

		@Override
		public Object getValue() {
			return value;
		}
	}

}
