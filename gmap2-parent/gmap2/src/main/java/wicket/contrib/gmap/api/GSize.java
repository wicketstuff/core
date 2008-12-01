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
package wicket.contrib.gmap.api;

import wicket.contrib.gmap.js.Constructor;

/**
 * Represents an Maps API's GSize that contains width and height.
 * 
 * @author Robert Jacolin, Vincent Demay, Gregory Maes - Anyware Technologies
 */
public class GSize implements GValue
{
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	private float width;
	private float height;

	public GSize(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public String getJSconstructor()
	{
		return new Constructor("GSize").add(width).add(height).toJS();
	}
	
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + Float.floatToIntBits(height);
		result = PRIME * result + Float.floatToIntBits(width);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GSize other = (GSize)obj;
		if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
			return false;
		if (Float.floatToIntBits(width) != Float.floatToIntBits(other.width))
			return false;
		return true;
	}
}
