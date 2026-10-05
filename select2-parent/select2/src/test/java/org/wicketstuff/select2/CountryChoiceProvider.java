/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lexx
 */
public final class CountryChoiceProvider extends ChoiceProvider<Country>
{
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 5;

	@Override
	public void query(String term, int page, Response<Country> response)
	{
		response.addAll(countryMatches(term, page, PAGE_SIZE));
		response.setHasMore(response.size() == PAGE_SIZE);
	}

	@Override
	public String getIdValue(Country choice) {
		return choice.name();
	}

	@Override
	public String getDisplayValue(Country choice) {
		return choice.getDisplayName();
	}

	@Override
	public Collection<Country> toChoices(Collection<String> ids)
	{
		ArrayList<Country> countries = new ArrayList<>(PAGE_SIZE);
		for (String id : ids)
		{
			countries.add(Country.valueOf(id));
		}
		return countries;
	}

	private static List<Country> countryMatches(String term, int page, int pageSize)
	{
		List<Country> result = new ArrayList<>(pageSize);
		int offset = page * pageSize;
		int matched = 0;
		for (Country country : Country.values())
		{
			if (result.size() == pageSize)
			{
				break;
			}
			if (country.getDisplayName().toUpperCase().contains(term.toUpperCase()))
			{
				matched++;
				if (matched > offset)
				{
					result.add(country);
				}
			}
		}
		return result;
	}

	@Override
	public void detach()
	{
		// nop
	}

}
