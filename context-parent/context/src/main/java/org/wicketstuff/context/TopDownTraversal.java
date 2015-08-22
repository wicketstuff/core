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
import org.apache.wicket.Page;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * @author zruchala
 */
public class TopDownTraversal extends TraversalStrategy {

	private static TopDownTraversal INSTANCE = new TopDownTraversal();

	public TraversalResult traverse(Component pComponent, List<Field> pFields) {
		Page page = pComponent.getPage();
		if (page == null) {
			throw new RuntimeException("The component [" + pComponent.toString() +
					"] passed to traverse does not belong to the page");   
		}

		final List<FieldValue> fieldValues = getSupportedFields(pFields);
		if (fieldValues.isEmpty()) {
			return new TraversalResultImpl(pComponent, fieldValues);
		}

		page.visitChildren(new IVisitor<Component, Object>() {
			Set<FieldValue> unresolved = new HashSet<FieldValue>(fieldValues);

			@Override
			public void component(Component pCurrent, IVisit<Object> pVisit) {
				unresolved = match(pCurrent, unresolved);
				if (unresolved.isEmpty()) {
					pVisit.stop();
				}
			}
		});

		return new TraversalResultImpl(pComponent, fieldValues);
	}

	public boolean supports(Field pField) {
		return pField.getAnnotation(Context.class).traversal() == Traversal.TOP_DOWN;
	}

	public static TopDownTraversal get() {
		return INSTANCE;
	}

}
