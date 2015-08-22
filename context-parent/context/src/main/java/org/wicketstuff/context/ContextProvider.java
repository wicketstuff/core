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
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.application.IComponentInitializationListener;
import org.apache.wicket.util.collections.ClassMetaCache;
import org.wicketstuff.context.TraversalStrategy.TraversalResult;

/**
 * @author zruchala
 */
public class ContextProvider implements IComponentInitializationListener {

	private static MetaDataKey<ContextProvider> CONTEXT_PROVIDER_KEY = new MetaDataKey<ContextProvider>() {
		private static final long serialVersionUID = 1L;
	};

	private final ClassMetaCache<Field[]> cache = new ClassMetaCache<Field[]>();

	public ContextProvider() {
		Application.get().setMetaData(CONTEXT_PROVIDER_KEY, this);
	}

	public static ContextProvider get() {
		return Application.get().getMetaData(CONTEXT_PROVIDER_KEY);
	}

	public void inject(Component pComponent) {
		onInitialize(pComponent);
	}

	@Override
	public void onInitialize(Component pComponent) {
		Field[] annotatedFields = getAnnotatedFields(pComponent.getClass());
		if (annotatedFields.length == 0) {
			return;
		}
		initializeInstances(selectByType(annotatedFields, Instance.class), pComponent);
		injectValues(selectRejectedByType(annotatedFields, Instance.class), pComponent);
	}

	private Field[] getAnnotatedFields(Class<?> pClass) {
		return getFromCache(pClass);
	}

	private Field[] getFromCache(Class<?> pClass) {
		Field[] fields = cache.get(pClass);
		return fields != null ? fields : putInCache(pClass);
	}

	private Field[] putInCache(Class<?> pClass) {
		Field[] fields = collectAnnotatedFieldsForHierarchy(pClass);
		cache.put(pClass, fields);
		return fields;
	}

	private Field[] collectAnnotatedFieldsForHierarchy(Class<?> pClazz) {
		List<Field> fields = new ArrayList<Field>();
		Class<?> current = pClazz;
		while (current != null) {
			fields.addAll(filterAnnotatedFields(current.getDeclaredFields()));
			current = current.getSuperclass();
		}
		return fields.toArray(new Field[fields.size()]);
	}

	private List<Field> filterAnnotatedFields(Field[] pDeclaredFields) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : pDeclaredFields) {
			if (field.isAnnotationPresent(Context.class)) {
				fields.add(field);
			}
		}
		return fields;
	}

	private List<Field> selectByType(Field[] pFields, Class<?> pType) {
		List<Field> matchedFields = new ArrayList<Field>();
		for(Field field : pFields) {
			if (Instance.class.equals(field.getType())) {
				matchedFields.add(field);
			}
		}
		return matchedFields;
	}
	
	private List<Field> selectRejectedByType(Field[] pFields, Class<?> pType) {
		List<Field> rejectedFields = new ArrayList<Field>();
		for(Field field : pFields) {
			if (!Instance.class.equals(field.getType())) {
				rejectedFields.add(field);
			}
		}
		return rejectedFields;
	}

	private void initializeInstances(List<Field> pFields, Component pComponent) {
		for(Field field : pFields) {
			try {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}

				if (field.get(pComponent) == null) {
					field.set(pComponent, new Instance<Object>(field, pComponent));
				}
			} catch(IllegalAccessException pException) {
				throw new RuntimeException("An error while injecting object instance into field [" + field.getName() + "] of component [" + 
						pComponent.toString() + "]", pException);
			}
		}
	}
	
	private void injectValues(List<Field> pFields, Component pComponent) {
		if (pFields.size() == 0) {
			return;
		}
		
		TraversalResult result = UpTraversal.get().traverse(pComponent, pFields);
		result.inject();

		result = TopDownTraversal.get().traverse(pComponent, pFields);
		result.inject();
	}
}
