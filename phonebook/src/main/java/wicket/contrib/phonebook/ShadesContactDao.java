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

import hendrey.orm.DatabaseSession;
import hendrey.orm.ORMDictionary;
import hendrey.orm.ORMapping;
import hendrey.orm.Query;
import hendrey.orm.Record;
import hendrey.orm.RecordSet;
import hendrey.shades.DatabaseSessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

/**
 * 
 * @author Geoffrey Rummens Hendrey
 */
public class ShadesContactDao implements ContactDao
{
	private final ORMDictionary dict = ShadesORMDictionary.getInstance();
	private final ORMapping orm = dict.getORM("CONTACT");
	private final DatabaseSession dbSess = DatabaseSessionFactory.newSession(dict);
	private DataSource dataSource;

	/** Creates a new instance of ShadesContactDao */
	public ShadesContactDao()
	{
		dbSess.printSQL(System.out);
	}

	public Contact load(long id)
	{
		Connection con = null;
		try
		{
			con = dataSource.getConnection();
			Query q = dict.getQuery("byId");
			dbSess.setParameter("id", Long.valueOf(id));
			return (Contact)dbSess.executeQuery(con, q).populateNext(new Contact());
		}
		catch (SQLException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				// dbSess.clear();
				con.close();
			}
			catch (SQLException ex)
			{
				throw new RuntimeException(ex);
			}
		}

	}

	public Contact save(Contact contact)
	{
		Connection con = null;
		try
		{
			con = dataSource.getConnection();
			System.out.println("saving Contact: " + contact);
			Record[] loadedRecords = dbSess.getRecords(contact);
			if (0 != loadedRecords.length)
			{
				dbSess.update(con, new Object[] { contact });
				System.out.println("updated");
			}
			else
			{
				dbSess.insert(con, contact, new ORMapping[] { orm });
				System.out.println("saved");
			}
			return contact;
		}
		catch (SQLException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				dbSess.clear();
				con.close();
			}
			catch (SQLException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}

	public void delete(long id)
	{
		Connection con = null;
		try
		{
			con = dataSource.getConnection();
			dbSess.delete(con, dbSess.getRecords(load(id)));
		}
		catch (SQLException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				dbSess.clear();
				con.close();
			}
			catch (SQLException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}

	public Iterator<Contact> find(QueryParam qp, Contact filter)
	{
		System.out.println("looking for contacts like " + filter);
		Query q = dict.getQuery("byOrderedResemblance");
		ShadesORMDictionary.filterCandidate.resembles(filter, orm.getColumnSet("nonKeyFields"));
		dbSess.setParameter("first", Long.valueOf(qp.getFirst()));
		dbSess.setParameter("count", Long.valueOf(qp.getCount()));
		q.clause("ORDER BY").enable(qp.hasSort());
		dbSess.setParameter("order", qp.getSort());
		if (qp.isSortAsc())
		{
			dbSess.setParameter("direction", "ASC");
		}
		else
		{
			dbSess.setParameter("direction", "DESC");
		}
		/*
		 * if(qp.hasSort()){ dbSess.setParameter("order", qp.getSort());
		 * if(qp.isSortAsc())dbSess.setParameter("direction", "ASC"); else
		 * dbSess.setParameter("direction", "DESC"); }else{ q.clause("ORDER
		 * BY").disable(); //dbSess.setParameter("order",""); //dbSess.setParameter("direction",
		 * ""); }
		 */
		Connection con = null;
		List<Contact> list = new ArrayList<Contact>();
		try
		{
			con = dataSource.getConnection();
			dbSess.executeQuery(con, q).populateList(list, Contact.class);
			System.out.println(list);
			return list.iterator();
		}
		catch (SQLException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				dbSess.clear();
				con.close();
			}
			catch (SQLException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}

	public int count(Contact filter)
	{
		dbSess.clear();
		Query q = dict.getQuery("byResemblance");
		ShadesORMDictionary.filterCandidate.resembles(filter, orm.getColumnSet("nonKeyFields"));
		Connection con = null;
		try
		{
			return dbSess.count(con = dataSource.getConnection(), q);
		}
		catch (SQLException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (SQLException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}

	public List<String> getUniqueLastNames()
	{
		Query q = dict.getQuery("selectDistinctLastnameOnly");
		RecordSet rs;
		Connection con = null;
		try
		{
			rs = dbSess.executeQuery(con = dataSource.getConnection(), q);
			List<String> lastnames = new ArrayList<String>();
			Contact c = new Contact();
			while (rs.next())
			{
				rs.populate(c);
				lastnames.add(c.getLastname());
			}
			return lastnames;
		}
		catch (SQLException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				dbSess.clear();
				con.close();
			}
			catch (SQLException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}

	public final void setDataSource(DataSource ds)
	{
		dataSource = ds;
	}

	public final DataSource getDataSource()
	{
		return dataSource;
	}
}
