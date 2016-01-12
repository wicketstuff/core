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

import hendrey.shades.DefaultHsqlORMapping;

/**
 * @author Geoffrey Rummens Hendrey
 */
public class ShadesContactORM extends DefaultHsqlORMapping
{
	private static final long serialVersionUID = 1L;

	@Override
	public String[] getColumnNames()
	{
		return new String[] { "ID", "FIRSTNAME", "LASTNAME", "EMAIL", "PHONE" };
	}

	@Override
	public String[] getColumnSet(String columnSetName)
	{
		if (columnSetName.equalsIgnoreCase("NonKeyFields"))
		{
			return new String[] { "FIRSTNAME", "LASTNAME", "EMAIL", "PHONE" };
		}
		else
		{
			throw new RuntimeException("unknown columnSetName: " + columnSetName);
		}
	}

	@Override
	public Class<?> getBeanClass()
	{
		return Contact.class;
	}

	@Override
	public boolean isGeneratedKey(String columnName)
	{
		return isIdentityColumn(columnName);
	}

	@Override
	public boolean isIdentityColumn(String columnName)
	{
		return columnName.endsWith("ID");
	}

	@Override
	public String[] getNonPojoColumns()
	{
		return new String[] { };
	}
}
