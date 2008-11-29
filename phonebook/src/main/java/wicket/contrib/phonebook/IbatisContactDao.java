/*
 * $Id: IbatisContactDao.java 479 2005-12-11 07:05:45Z ivaynberg $
 * $Revision: 479 $
 * $Date: 2005-12-11 07:05:45 +0000 (Sun, 11 Dec 2005) $
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * implements {@link ContactDao}.
 *
 * @author igor
 */
public class IbatisContactDao extends SqlMapClientDaoSupport implements
		ContactDao {

	/**
	 * Load a {@link Contact} from the DB, given it's <tt>id</tt> .
	 *
	 * @param id
	 *            The id of the Contact to load.
	 * @return Contact
	 */
	public Contact load(long id) throws DataAccessException {
		return (Contact) getSqlMapClientTemplate().queryForObject("getContact",
				new Long(id));
	}

	/**
	 * Save the contact to the DB
	 *
	 * @param contact
	 * @return persistent instance of contact
	 */
	public Contact save(Contact contact) {
		if (contact.getId() == 0) {
			getSqlMapClientTemplate().insert("insertContact", contact);
		} else {
			getSqlMapClientTemplate().update("updateContact", contact);
		}
		return contact;
	}

	/**
	 * Delete a {@link Contact} from the DB, given it's <tt>id</tt>.
	 *
	 * @param id
	 *            The id of the Contact to delete.
	 */
	public void delete(long id) {
		getSqlMapClientTemplate().delete("deleteContact", new Long(id));
	}

	/**
	 * Query the DB, using the supplied query details.
	 *
	 * @param qp
	 *            Query Paramaters to use.
	 *
	 * @return The results of the query as an Iterator.
	 */
	@SuppressWarnings("unchecked")
	public Iterator<Contact> find(final QueryParam qp, Contact filter) {
		Map<String, String> map = createMap(filter);
		map.put("sort", qp.getSort());
		map.put("sortasc", (qp.isSortAsc() ? " asc" : " desc"));
		List list = getSqlMapClientTemplate().queryForList("getContactList",
				map, qp.getFirst(), qp.getCount());
		return list.listIterator();
	}

	/**
	 * Return the number of Configs in the DB.
	 *
	 * @return count
	 */
	public int count(Contact filter) {
		Map<String, String> map = createMap(filter);

		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"getContactCount", map);
		return count.intValue();
	}

	/**
	 * Returns a list of unique last names
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUniqueLastNames() {
		return getSqlMapClientTemplate().queryForList("getUniqueLastNames",
				null);
	}

	/**
	 *
	 * @param filter
	 * @return
	 */
	private Map<String, String> createMap(Contact filter) {
		String firstname = filter.getFirstname();
		String lastname = filter.getLastname();
		String email = filter.getEmail();
		String phone = filter.getPhone();

		Map<String, String> map = new HashMap<String, String>();
		map.put("firstname", firstname == null ? null : "%"
				+ firstname.toUpperCase() + "%");
		map.put("lastname", lastname); // Selected via drop-down
		map
				.put("email", email == null ? null : "%" + email.toUpperCase()
						+ "%");
		map
				.put("phone", phone == null ? null : "%" + phone.toUpperCase()
						+ "%");
		return map;
	}
}
