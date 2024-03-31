/*
 * $Id: Contact.java 773 2006-06-08 12:54:03 -0700 (Thu, 08 Jun 2006) gwynevans $
 * $Revision: 773 $
 * $Date: 2006-06-08 12:54:03 -0700 (Thu, 08 Jun 2006) $
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

import java.io.Serializable;

/**
 * Contact details. This is the business object that we persist to the DB.
 * 
 * @author igor
 */
public class Contact implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long id;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;

	public long getId()
	{
		return id;
	}

	/**
	 * This is required for iBatis, but not for Hibernate
	 * 
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getFullName()
	{
		return firstname + " " + lastname;
	}

	@Override
	public String toString()
	{
		return "[Contact id=" + id + ", firstname=" + firstname + ", lastname=" + lastname +
			", phone=" + phone + ", email=" + email;
	}

}
