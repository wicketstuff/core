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

import hendrey.orm.ORMDictionary;
import hendrey.orm.ORMapping;
import hendrey.orm.Query;
import hendrey.orm.RecordCandidate;
import hendrey.shades.ORMDictionaryFactory;
import hendrey.shades.QueryFactory;
import hendrey.shades.tools.TableCreator;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author Geoffrey Rummens Hendrey
 */
public class ShadesORMDictionary implements InitializingBean
{
	private static final ORMDictionary dict = ORMDictionaryFactory.getInstance("phonebook-schema");
	private DataSource dataSource; // required for table creation
	/*
	 * DAO can grab filterCandidate and call .resembles on the fly to dynamically reconfigure the
	 * query
	 */
	static RecordCandidate filterCandidate;

	static
	{
		ORMapping orm = new ShadesContactORM();
		dict.defineORMapping("CONTACT", orm);
		defineQueryById(orm);
		defineQueryByResemblance(orm);
		defineQueryByResemblanceWithFilter(orm);
		defineQueryforDistinctLastname(orm);
	}

	private static void defineQueryforDistinctLastname(ORMapping orm)
	{
		Query q = QueryFactory.newImmutableQuery("SELECT DISTINCT LASTNAME AS \"CONTACT.LASTNAME\" FROM CONTACT");
		q.candidate(orm).setFetchColumns(new String[] { "LASTNAME" });
		dict.defineQuery("selectDistinctLastnameOnly", q);
	}

	private static void defineQueryByResemblanceWithFilter(ORMapping orm)
	{
		Query q = QueryFactory.newQuery(dict);
		filterCandidate = q.candidate(orm, "CONTACT");
		q.clause("ORDER BY").append("${order} ${direction} LIMIT ${count} OFFSET ${first}");
		dict.defineQuery("byOrderedResemblance", q);
	}

	private static void defineQueryByResemblance(ORMapping orm)
	{
		Query q = QueryFactory.newQuery(dict);
		q.candidate(orm, "CONTACT");
		dict.defineQuery("byResemblance", q);
	}

	private static void defineQueryById(ORMapping orm)
	{
		Query q = QueryFactory.newQuery(dict);
		q.candidate(orm).where("ID=${id}", new String[] { });
		dict.defineQuery("byId", q);
	}

	public static ORMDictionary getInstance()
	{
		return dict; // return the static, configured ORMDictionary
	}

	public void afterPropertiesSet() throws Exception
	{
		Connection c = null;
		try
		{
			String ddl = TableCreator.getDDL(dict);
			c = dataSource.getConnection();
			Statement s = c.createStatement();
			try
			{
				s.execute(ddl);
			}
			finally
			{
				s.close();
			}
			c.commit();
		}
		finally
		{
			c.close();
		}
	}

	/** Creates a new instance of ShadesPhonebookORMDictionary */
	private ShadesORMDictionary()
	{
	}

	public DataSource getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

}
