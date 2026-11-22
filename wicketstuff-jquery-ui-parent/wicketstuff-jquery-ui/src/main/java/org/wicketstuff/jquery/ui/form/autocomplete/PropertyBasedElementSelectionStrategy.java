/*
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
package org.wicketstuff.jquery.ui.form.autocomplete;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.util.lang.Args;

/**
 * {@link IdBasedElementSelectionStrategy} that resolves the id from a bean property expression.
 *
 * @param <T> the choice type
 * @author reiern70
 */
public class PropertyBasedElementSelectionStrategy<T> extends IdBasedElementSelectionStrategy<T>
{
	private static final long serialVersionUID = 1L;

	private final String idExpression;

	/**
	 * Constructor
	 *
	 * @param idExpression the property expression resolved on each choice (e.g. {@code "id"})
	 */
	public PropertyBasedElementSelectionStrategy(String idExpression)
	{
		this.idExpression = Args.notEmpty(idExpression, "idExpression");
	}

	@Override
	public String getValue(T choice, int index)
	{
		if (choice == null)
		{
			return null;
		}

		Object value = PropertyResolver.getValue(this.idExpression, choice);

		return value != null ? value.toString() : null;
	}
}
