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

	private static final long serialVersionUID = 5827792929263787358L;

	private final float _width;

	private final float _height;

	public GSize(float width, float height)
	{
		this._width = width;
		this._height = height;
	}

	public float getWidth()
	{
		return _width;
	}

	public float getHeight()
	{
		return _height;
	}

	/**
	 * @see wicket.contrib.gmap.api.GValue#getJSconstructor()
	 */
	public String getJSconstructor()
	{
		return new Constructor("google.maps.Size").add(_width).add(_height).toJS();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + Float.floatToIntBits(_height);
		result = PRIME * result + Float.floatToIntBits(_width);
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GSize other = (GSize) obj;
		if (Float.floatToIntBits(_height) != Float.floatToIntBits(other._height))
			return false;
		if (Float.floatToIntBits(_width) != Float.floatToIntBits(other._width))
			return false;
		return true;
	}
}
