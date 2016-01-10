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
package org.wicketstuff.jamon.component;

import java.util.Comparator;

import org.apache.wicket.model.PropertyModel;

import com.jamonapi.Monitor;

/**
 * Used to compare two {@link PropertyModel}s. As we know all property values from a {@link Monitor}
 * are {@link Comparable} we can safely cast them to {@link Comparable}. This code is taken from:
 * http://cwiki.apache.org/WICKET/simple-sortable-datatable-example.html
 * 
 * @author lars
 *
 */
@SuppressWarnings("unchecked")
final class PropertyModelObjectComparator implements Comparator<Object>
{
	private final boolean ascending;

	private final String sortProperty;

	PropertyModelObjectComparator(boolean ascending, String sortProperty)
	{
		this.ascending = ascending;
		this.sortProperty = sortProperty;
	}

	public int compare(Object o1, Object o2)
	{
		PropertyModel<Object> model1 = new PropertyModel<Object>(o1, sortProperty);
		PropertyModel<Object> model2 = new PropertyModel<Object>(o2, sortProperty);

		int compare = ((Comparable<Object>)model1.getObject()).compareTo(model2.getObject());

		return ascending ? compare : compare * -1;
	}
}