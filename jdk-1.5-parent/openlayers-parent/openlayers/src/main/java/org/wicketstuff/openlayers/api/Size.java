/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.openlayers.api;

import java.io.Serializable;

import org.wicketstuff.openlayers.js.Constructor;

/**
 * http://dev.openlayers.org/apidocs/files/OpenLayers/BaseTypes/Size-js.html
 * 
 */
public class Size implements Serializable
{

	private static final long serialVersionUID = 1L;
	private float w;
	private float h;

	public Size(float width, float height)
	{
		w = width;
		h = height;
	}

	public float getWidth()
	{
		return w;
	}

	public float getHeight()
	{
		return h;
	}

	public String getJSconstructor()
	{
		Constructor constructor = new Constructor("OpenLayers.Size").add(w).add(h);
		return constructor.toJS();
	}

	public String getId()
	{
		return "size" + String.valueOf(System.identityHashCode(this));
	}

	/**
	 * create size as a variable.. Possibly to add to icon
	 * 
	 * @param map
	 * @return
	 */
	public String getJSadd()
	{
		StringBuffer js = new StringBuffer();
		js.append("var " + getId() + " = " + getJSconstructor() + ";\n");
		return js.toString();
	}

}
