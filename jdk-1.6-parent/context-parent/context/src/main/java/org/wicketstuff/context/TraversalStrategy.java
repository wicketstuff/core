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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;

/**
 * @author zruchala
 */
public abstract class TraversalStrategy {

	/**
	 * The traversal result and operation allowed to perform on it.
	 */
	public interface TraversalResult {
		/**
		 * Inject the resolved context into fields.
		 */
		void inject();

		/**
		 * Get the resolved context for the given field. 
		 * 
		 * @param pField the field to check.
		 * @return the resolved object or null if none.
		 */
		Object getValue(Field pField);
	}

	public static class TraversalResultImpl implements TraversalResult {

		private List<FieldValue> fieldsValues;
		private Component component;

		public TraversalResultImpl(Component pComponent, List<FieldValue> pFieldValues) {
			fieldsValues = pFieldValues;
			component = pComponent;
		}

		@Override
		public void inject() {
			for(FieldValue fieldValue : fieldsValues) {
				fieldValue.set(component);
			}
		}

		@Override
		public Object getValue(Field pField) {
			for(FieldValue fieldValue : fieldsValues) {
				if (fieldValue.getField().equals(pField)) {
					return fieldValue.getValue();
				}
			}
			return null;
		}
	}

	/**
	 * The traverse procedure. 
	 * 
	 * @param pComponent the component for which the traversal is performed.
	 * @param pFields the set of context fields 
	 */
	abstract public TraversalResult traverse(Component pComponent, List<Field> pFields);

	abstract public boolean supports(Field pField); 

	protected List<FieldValue> getSupportedFields(List<Field> pFields) {
		List<FieldValue> fields = new ArrayList<FieldValue>();
		for(Field field : pFields) {
			if (supports(field)) {
				fields.add(FieldValue.of(field));
			}
		}
		return fields;
	}

	/**
	 * Match the component to the set of unresolved fields.
	 * 
	 * @param pComponent the component that is being matched.
	 * @param pFieldValues the unresolved fields.
	 * 
	 * @return the still unresolved fields.
	 */
	protected Set<FieldValue> match(Component pComponent, Set<FieldValue> pFieldValues) {
		Set<FieldValue> unresolved = new HashSet<FieldValue>(pFieldValues);
		for(FieldValue fieldValue : pFieldValues) {
			fieldValue.match(pComponent);
			if (fieldValue.isResolved()) {
				unresolved.remove(fieldValue);
			}
		}
		return unresolved;
	}
}
