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
package wicket.contrib.gmap.api;

import wicket.contrib.gmap.js.ObjectLiteral;

public class GMarkerOptions implements GValue, Cloneable
{
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	
	private boolean clickable = true;
	
	private boolean draggable = false;
	
	private boolean bouncy = true;
	
	private boolean autoPan = false;
	
	private GIcon icon = null;

	public GMarkerOptions()
	{
	}

	public GMarkerOptions(String title)
	{
		this.title = title;
	}

	public GMarkerOptions(String title, GIcon icon)
	{
		this.title = title;
		this.icon = icon;
	}

	public String getJSconstructor()
	{
		ObjectLiteral literal = new ObjectLiteral();

		if (title != null)
		{
			literal.setString("title", title);
		}
		if (!clickable)
		{
			literal.set("clickable", "false");
		}
		if (draggable)
		{
			literal.set("draggable", "true");
		}
		if (!bouncy)
		{
			literal.set("bouncy", "false");
		}
		if (autoPan)
		{
			literal.set("autoPan", "true");
		}
		if(icon != null)
		{
			literal.set("icon", icon.getJSconstructor());
		}

		return literal.toJS();
	}

	public String getTitle()
	{
		return title;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public boolean isClickable() {
		return clickable;
	}

	public boolean isBouncy() {
		return bouncy;
	}

	public boolean isAutoPan() {
		return autoPan;
	}

	public GIcon getIcon()
	{
		return icon;
	}
	
	public GMarkerOptions clone() {
		try
		{
			return (GMarkerOptions)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error(e);
		}
	}
	
	public GMarkerOptions clickable(boolean clickable) {
		GMarkerOptions clone = clone();
		clone.clickable = clickable;
		return clone;
	}
	
	public GMarkerOptions draggable(boolean draggable) {
		GMarkerOptions clone = clone();
		clone.draggable = draggable;
		return clone;
	}
	
	public GMarkerOptions autoPan(boolean autoPan) {
		GMarkerOptions clone = clone();
		clone.autoPan = autoPan;
		return clone;
	}
	
	public GMarkerOptions bouncy(boolean bouncy) {
		GMarkerOptions clone = clone();
		clone.bouncy = bouncy;
		return clone;
	}
	
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (autoPan ? 1231 : 1237);
		result = PRIME * result + (bouncy ? 1231 : 1237);
		result = PRIME * result + (clickable ? 1231 : 1237);
		result = PRIME * result + (draggable ? 1231 : 1237);
		result = PRIME * result + ((icon == null) ? 0 : icon.hashCode());
		result = PRIME * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GMarkerOptions other = (GMarkerOptions)obj;
		if (autoPan != other.autoPan)
			return false;
		if (bouncy != other.bouncy)
			return false;
		if (clickable != other.clickable)
			return false;
		if (draggable != other.draggable)
			return false;
		if (icon == null)
		{
			if (other.icon != null)
				return false;
		}
		else if (!icon.equals(other.icon))
			return false;
		if (title == null)
		{
			if (other.title != null)
				return false;
		}
		else if (!title.equals(other.title))
			return false;
		return true;
	}
}