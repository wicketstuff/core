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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.wicket.Component;

import org.wicketstuff.context.TraversalStrategy.TraversalResult;

/**
 * {@link Instance} enables the lazy approach. The target is not known until the {@link #get()} is invoked.
 * 
 *  @Context private Instance<ParentPanel> panel;
 * 
 * @param <T>
 * 
 * @author zruchala
 */
public class Instance<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String fieldName;
	private final Component component;

	public Instance(Field pField, Component pComponent) {
		fieldName = pField.getName();
		component = pComponent;
	}

	@SuppressWarnings("unchecked")
	public T get() {
		try {
			Field field = component.getClass().getDeclaredField(fieldName);
			Context context = field.getAnnotation(Context.class);

			TraversalResult result;
			switch(context.traversal()) {
				case UP: result = UpTraversal.get().traverse(component, Arrays.asList(field)); break;
				case TOP_DOWN: result = TopDownTraversal.get().traverse(component, Arrays.asList(field)); break;
				default:
					throw new RuntimeException("Unknown or unset traversal method: " + context.traversal());
			}

			return (T) result.getValue(field);

		} catch (NoSuchFieldException pNoSuchFieldException) {
			throw new RuntimeException("An error while getting field [" + fieldName + "] of component [" + 
					component.toString() + "]", pNoSuchFieldException);
		} catch (SecurityException pSecurityException) {
			throw new RuntimeException("An error while getting field [" + fieldName + "] of component [" + 
					component.toString() + "]", pSecurityException);
		}
	}
}
