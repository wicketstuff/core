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
package com.googlecode.wicket.jquery.ui.form.autocomplete;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides simple utils that can be used in {@link AutoCompleteTextField#getChoices(String)}
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class AutoCompleteUtils
{
	private static final int MAX = 20;
	
	/**
	 * Returns a sub list of items of type T having their textual representation (toString()) containing the search criteria<br/>
	 * The max size of the sub list is {@link #MAX}
	 * @param <T> the type
	 * @param search search criteria
	 * @param list reference list
	 * @return the sub list
	 */
	public static <T> List<T> contains(String search, List<T> list)
	{
		return AutoCompleteUtils.contains(search, list, MAX);
	}
	
	/**
	 * Returns a sub list of items of type T having their textual representation (toString()) containing the search criteria<br/>
	 * @param <T> the type
	 * @param search search criteria
	 * @param list reference list
	 * @param max max size of the sub list to be returned
	 * @return the sub list
	 */
	public static <T> List<T> contains(String search, List<T> list, int max)
	{
		List<T> choices = new ArrayList<T>();
	
		int count = 0;
		for (T choice : list)
		{
			if (choice.toString().toLowerCase().contains(search.toLowerCase()))
			{
				choices.add(choice);
				
				if (++count == max) { break; }
			}
		}
	
		return choices;
	}
	
	/**
	 * Returns a sub list of items of type T having their textual representation (toString()) starting with the search criteria<br/>
	 * The max size of the sub list is {@link #MAX}
	 * @param <T> the type
	 * @param search search criteria
	 * @param list reference list
	 * @return the sub list
	 */
	public static <T> List<T> startsWith(String search, List<T> list)
	{
		return AutoCompleteUtils.startsWith(search, list, MAX);
	}

	/**
	 * Returns a sub list of items of type T having their textual representation (toString()) starting with the search criteria<br/>
	 * @param <T> the type
	 * @param search search criteria
	 * @param list reference list
	 * @param max max size of the sub list to be returned
	 * @return the sub list
	 */
	public static <T> List<T> startsWith(String search, List<T> list, int max)
	{
		List<T> choices = new ArrayList<T>();
	
		int count = 0;
		for (T choice : list)
		{
			if (choice.toString().toLowerCase().startsWith(search.toLowerCase()))
			{
				choices.add(choice);
				
				if (++count == max) { break; }
			}
		}
	
		return choices;
	}
}
