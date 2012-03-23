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
package com.inmethod.grid.examples.contact;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * simple database for contacts
 * 
 * @author Igor Vaynberg
 * 
 */
public class ContactsDatabase implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final Map<Long, Contact> map = new ConcurrentHashMap<Long, Contact>();
	private final List<Contact> fnameIdx = new Vector<Contact>();
	private final List<Contact> lnameIdx = new Vector<Contact>();
	private final List<Contact> fnameDescIdx = new Vector<Contact>();
	private final List<Contact> lnameDescIdx = new Vector<Contact>();

	/**
	 * Constructor
	 * 
	 * @param count
	 *            number of contacts to generate at startup
	 */
	public ContactsDatabase(long count)
	{
		for (int i = 0; i < count; i++)
		{
			add(ContactGenerator.getInstance().generate());
		}
		updateIndices();
	}

	/**
	 * find contact by id
	 * 
	 * @param id
	 * @return contact
	 */
	public Contact get(long id)
	{
		Contact c = map.get(Long.valueOf(id));
		if (c == null)
		{
			throw new RuntimeException("contact with id [" + id + "] not found in the database");
		}
		return c;
	}

	protected void add(final Contact contact)
	{
		map.put(Long.valueOf(contact.getId()), contact);
		fnameIdx.add(contact);
		lnameIdx.add(contact);
		fnameDescIdx.add(contact);
		lnameDescIdx.add(contact);
	}

	/**
	 * select contacts and apply sort
	 * 
	 * @param first
	 * @param count
	 * @param sortProperty
	 * @param sortAsc
	 * @return list of contacts
	 */
	public List<Contact> find(long first, long count, String sortProperty, boolean sortAsc)
	{
		List<Contact> index = getIndex(sortProperty, sortAsc);
		long last = first + count;
		if (last > index.size())
			last = index.size();
		List<Contact> sublist = index.subList((int)first, (int)last);
		return sublist;
	}

	protected List<Contact> getIndex(String prop, boolean asc)
	{
		if (prop == null)
		{
			return fnameIdx;
		}
		if (prop.equals("firstName"))
		{
			return asc ? fnameIdx : fnameDescIdx;
		}
		else if (prop.equals("lastName"))
		{
			return asc ? lnameIdx : lnameDescIdx;
		}
		throw new RuntimeException("uknown sort option [" + prop +
			"]. valid options: [firstName] , [lastName]");
	}

	/**
	 * @return number of contacts in the database
	 */
	public int getCount()
	{
		return fnameIdx.size();
	}

	/**
	 * add contact to the database
	 * 
	 * @param contact
	 */
	public void save(final Contact contact)
	{
		if (contact.getId() == 0)
		{
			contact.setId(ContactGenerator.getInstance().generateId());
			add(contact);
			updateIndices();
		}
		else
		{
			throw new IllegalArgumentException("contact [" + contact.getFirstName() +
				"] is already persistent");
		}
	}

	/**
	 * delete contact from the database
	 * 
	 * @param contact
	 */
	public void delete(final Contact contact)
	{
		map.remove(Long.valueOf(contact.getId()));

		fnameIdx.remove(contact);
		lnameIdx.remove(contact);
		fnameDescIdx.remove(contact);
		lnameDescIdx.remove(contact);

		contact.setId(0);
	}

	private void updateIndices()
	{
		Collections.sort(fnameIdx, new Comparator<Contact>()
		{
			public int compare(Contact arg0, Contact arg1)
			{
				return arg0.getFirstName().compareTo(arg1.getFirstName());
			}
		});

		Collections.sort(lnameIdx, new Comparator<Contact>()
		{
			public int compare(Contact arg0, Contact arg1)
			{
				return arg0.getLastName().compareTo(arg1.getLastName());
			}
		});

		Collections.sort(fnameDescIdx, new Comparator<Contact>()
		{
			public int compare(Contact arg0, Contact arg1)
			{
				return arg1.getFirstName().compareTo(arg0.getFirstName());
			}
		});

		Collections.sort(lnameDescIdx, new Comparator<Contact>()
		{
			public int compare(Contact arg0, Contact arg1)
			{
				return arg1.getLastName().compareTo(arg0.getLastName());
			}
		});

	}

}
