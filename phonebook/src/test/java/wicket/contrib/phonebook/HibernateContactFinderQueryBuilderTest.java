/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.phonebook;

import org.hibernate.type.StandardBasicTypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kare Nuorteva
 */
public class HibernateContactFinderQueryBuilderTest extends Assert
{
	private HibernateContactFinderQueryBuilder builder;
	private Contact filter;

	@Before
	public void before() throws Exception
	{
		builder = new HibernateContactFinderQueryBuilder();
		filter = new Contact();
		builder.setFilter(filter);
	}

    @Test
	public void testCountsQueryResultsWhenRequested() throws Exception
	{
		builder.setCount(true);
		String hql = builder.buildHql();
		assertTrue(hql.startsWith("select count(*) from Contact target where 1=1 "));
	}

    @Test
	public void testDoesNotCountResults() throws Exception
	{
		builder.setCount(false);
		String hql = builder.buildHql();
		assertTrue(hql.startsWith("from Contact target where 1=1 "));
	}

    @Test
	public void testFilterCannotBeNull() throws Exception
	{
		try
		{
			builder.setFilter(null);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException expected)
		{
			assertTrue(true);
		}
	}

    @Test
	public void testFiltersByFirstName() throws Exception
	{
		filter.setFirstname("James");
		assertTrue(builder.buildHql().endsWith("and upper(target.firstname) like (?)"));
		assertEquals("%JAMES%", builder.getParameters()[0]);
		assertEquals(StandardBasicTypes.STRING, builder.getTypes()[0]);
	}

    @Test
	public void testFiltersByLastName() throws Exception
	{
		filter.setLastname("Bond");
		assertTrue(builder.buildHql().endsWith("and upper(target.lastname) like (?)"));
		assertEquals("%BOND%", builder.getParameters()[0]);
		assertEquals(StandardBasicTypes.STRING, builder.getTypes()[0]);
	}

    @Test
	public void testFiltersByPhone() throws Exception
	{
		filter.setPhone("+12345");
		assertTrue(builder.buildHql().endsWith("and upper(target.phone) like (?)"));
		assertEquals("%+12345%", builder.getParameters()[0]);
		assertEquals(StandardBasicTypes.STRING, builder.getTypes()[0]);
	}

    @Test
	public void testFiltersByEmail() throws Exception
	{
		filter.setEmail("james@bond.com");
		assertTrue(builder.buildHql().endsWith("and upper(target.email) like (?)"));
		assertEquals("%JAMES@BOND.COM%", builder.getParameters()[0]);
		assertEquals(StandardBasicTypes.STRING, builder.getTypes()[0]);
	}

    @Test
	public void testOrdersAscendingByFirstName() throws Exception
	{
		builder.setQueryParam(new QueryParam(0, 10, "firstname", true));
		assertTrue(builder.buildHql().endsWith("order by upper(target.firstname) asc"));
	}

    @Test
	public void testOrdersDescendingByLastName() throws Exception
	{
		builder.setQueryParam(new QueryParam(0, 10, "lastname", false));
		assertTrue(builder.buildHql().endsWith("order by upper(target.lastname) desc"));
	}

    @Test
	public void testDoesNotOrderIfSortParameterIsNotDefined() throws Exception
	{
		builder.setQueryParam(new QueryParam(0, 10));
		assertTrue(builder.buildHql().endsWith("from Contact target where 1=1 "));
	}

    @Test
	public void testOrdersIfCountIsNotRequested() throws Exception
	{
		builder.setQueryParam(new QueryParam(0, 10, "lastname", false));
		builder.setCount(false);
		assertTrue(builder.buildHql().endsWith("order by upper(target.lastname) desc"));
	}
}
