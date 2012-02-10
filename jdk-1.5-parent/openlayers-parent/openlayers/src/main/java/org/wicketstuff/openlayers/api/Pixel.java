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
package org.wicketstuff.openlayers.api;

import java.io.Serializable;

import org.wicketstuff.openlayers.js.Constructor;

/**
 * represents http://dev.openlayers.org/apidocs/files/OpenLayers/BaseTypes/Pixel-js.html
 */
public class Pixel implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private int x;
	private int y;

	private Size size = null;

	/**
	 * calculates offset via size
	 * 
	 * @param size
	 */
	public Pixel(Size size)
	{
		super();
		this.size = size;
	}

	public Size getSize()
	{
		return size;
	}

	public void setSize(Size size)
	{
		this.size = size;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Pixel(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	protected String getJSconstructor()
	{
		if (size == null)
		{
			Constructor constructor = new Constructor("OpenLayers.Pixel").add(x).add(y);
			return constructor.toJS();
		}
		else
		{
			Constructor constructor = new Constructor("OpenLayers.Pixel").add(
				"-" + size.getId() + ".w/2").add("-" + size.getId() + ".h");
			return constructor.toJS();

		}
	}

	public String getId()
	{
		return "pixel" + String.valueOf(System.identityHashCode(this));
	}

	/**
	 * create pixel as a variable.. Possibly to add to icon
	 * 
	 * @return
	 */
	public String getJSadd()
	{
		StringBuffer js = new StringBuffer();
		js.append("var " + getId() + " = " + getJSconstructor() + ";\n");
		return js.toString();
	}


}