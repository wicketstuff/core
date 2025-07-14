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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.BindableType;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;


/**
 * @author Kare Nuorteva
 */
public class HibernateContactFinderQueryBuilder
{
	private List<String> parameters;
	private List<BindableType<?>> types;
	private boolean count;
	private Contact filter = new Contact();
	private QueryParam queryParam;

	public String buildHql()
	{
		parameters = new ArrayList<String>();
		types = new ArrayList<BindableType<?>>();
		StringBuilder hql = new StringBuilder();
		addCountClause(hql);
		hql.append("from Contact target where 1=1 ");
		addMatchingCondition(hql, filter.getFirstname(), "firstname");
		addMatchingCondition(hql, filter.getLastname(), "lastname");
		addMatchingCondition(hql, filter.getPhone(), "phone");
		addMatchingCondition(hql, filter.getEmail(), "email");
		addOrderByClause(hql);
		return hql.toString();
	}

	private void addCountClause(StringBuilder hql)
	{
		if (count)
		{
			hql.append("select count(*) ");
		}
	}

	private void addMatchingCondition(StringBuilder hql, String value, String name)
	{
		if (value != null)
		{
			hql.append("and upper(target.");
			hql.append(name);
			hql.append(") like (?)");
			parameters.add("%" + value.toUpperCase() + "%");
			types.add(StandardBasicTypes.STRING);
		}
	}

	private void addOrderByClause(StringBuilder hql)
	{
		if (!count && queryParam != null && queryParam.hasSort())
		{
			hql.append("order by upper(target.");
			hql.append(queryParam.getSort());
			hql.append(") ");
			hql.append(queryParam.isSortAsc() ? "asc" : "desc");
		}
	}

	public void setQueryParam(QueryParam queryParam)
	{
		this.queryParam = queryParam;
	}

	public void setFilter(Contact filter)
	{
		if (filter == null)
		{
			throw new IllegalArgumentException("Null value not allowed.");
		}
		this.filter = filter;
	}

	public void setCount(boolean count)
	{
		this.count = count;
	}

	public void setParameters(Query q) {
		for (int i = 0; i < parameters.size(); ++i) {
			q.setParameter(i, parameters.get(i), types.get(i));
		}
	}

	public String[] getParameters()
	{
		return parameters.toArray(new String[0]);
	}

	public BindableType<?>[] getTypes()
	{
		return types.toArray(new BindableType[types.size()]);
	}
}
