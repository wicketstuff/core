/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
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
package org.wicketstuff.openlayers.js;

public class ObjectLiteral
{
	private StringBuffer buffer = new StringBuffer("{");
	private boolean first = true;

	public ObjectLiteral setString(String name, String value)
	{
		return set(name, "\"" + value + "\"");
	}

	public ObjectLiteral set(String name, String value)
	{
		if (!first)
		{
			buffer.append(", ");
		}
		buffer.append(name);
		buffer.append(": ");
		buffer.append(value);

		first = false;
		return this;
	}

	public String toJS()
	{
		buffer.append("}");

		String string = buffer.toString();

		buffer.deleteCharAt(buffer.length() - 1);

		return string;
	}
}
