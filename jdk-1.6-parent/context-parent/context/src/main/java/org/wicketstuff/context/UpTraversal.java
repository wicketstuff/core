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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;

/**
 * @author zruchala
 */
public class UpTraversal extends TraversalStrategy {

	private static final UpTraversal INSTANCE = new UpTraversal();

	public TraversalResult traverse(Component pComponent, List<Field> pFields) {
		final List<FieldValue> supportedValues = getSupportedFields(pFields);
		if (supportedValues.isEmpty()) {
			return new TraversalResultImpl(pComponent, supportedValues);
		}
		Set<FieldValue> unresolved = new HashSet<FieldValue>(supportedValues);
		Component current = pComponent.getParent();
		while (current != null && !unresolved.isEmpty()) {
			unresolved = match(current, unresolved);
			current = current.getParent();
		}

		return new TraversalResultImpl(pComponent, supportedValues);
	}

	@Override
	public boolean supports(Field pField) {
		return pField.getAnnotation(Context.class).traversal() == Traversal.UP;
	}

	public static TraversalStrategy get() {
		return INSTANCE;
	}
}
