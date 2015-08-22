/*
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package wicket.contrib.tinymce4.settings;

import java.io.Serializable;

public class Toolbar implements Serializable
{
    private static final long serialVersionUID = 1L;
	public static final String SEPARATOR = "|";
    
	private final String id;
	private final StringBuffer buffer = new StringBuffer();
	
	public Toolbar(String id, String value) 
	{
		this(id);
		buffer.append(value);
	}
	
	public Toolbar(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	
	public void appendButton(String buttonName)
	{
		buffer.append(buttonName).append(' ');
	}
	
	public void appendSeparator()
	{
		appendButton(SEPARATOR);
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}
}
