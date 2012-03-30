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
package wicket.contrib.gmap.api;

/**
 * http://code.google.com/apis/maps/documentation/javascript/reference.html# MarkerImage
 * 
 * @author Christian Hennig (christian.hennig@freiheit.com)
 */
public class GIcon implements GValue, Cloneable
{

	private static final long serialVersionUID = 1714038753187423501L;

	private final String _url;

	private GSize _size = null;

	private GPoint _origin = null;

	private GSize _scaledSize = null;

	private GPoint _anchor = null;

	public GIcon()
	{
		_url = "http://www.google.com/mapfiles/marker.png";
	}

	public GIcon(final String image)
	{
		_url = image;

	}

	public GIcon(final String image, final GSize iconSize, final GPoint iconAnchor, final GPoint origin,
		final GSize scaledSize)
	{
		_url = image;
		_size = iconSize;
		_anchor = iconAnchor;
		_origin = origin;
		_scaledSize = scaledSize;
	}

	public GIcon(final String image, final GSize iconSize, final GPoint iconAnchor)
	{
		_url = image;
		_size = iconSize;
		_anchor = iconAnchor;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public GIcon clone()
	{
		try
		{
			return (GIcon) super.clone();
		}
		catch (final CloneNotSupportedException e)
		{
			throw new Error(e);
		}
	}

	public String getId()
	{
		return "icon" + String.valueOf(System.identityHashCode(this));
	}

	/**
	 * @see wicket.contrib.gmap.api.GValue#getJSconstructor()
	 */
	public String getJSconstructor()
	{
		final StringBuffer buffer = new StringBuffer();
		buffer.append("(function() {\n");
		buffer.append("var icon = new google.maps.MarkerImage(\'" + _url + "\' );\n");

		if (_size != null)
		{
			buffer.append("icon.size = ").append(_size.getJSconstructor()).append(";\n");
		}

		if (_anchor != null)
		{
			buffer.append("icon.anchor = ").append(_anchor.getJSconstructor()).append(";\n");
		}

		if (_origin != null)
		{
			buffer.append("icon.origin = ").append(_origin.getJSconstructor()).append(";\n");
		}
		if (_scaledSize != null)
		{
			buffer.append("icon.scaledSize = ").append(_scaledSize.getJSconstructor()).append(";\n");
		}

		buffer.append("return icon;\n");
		buffer.append("})()\n");
		return buffer.toString();
	}

	public GIcon setSchema(final String schema)
	{
		final String regex = "https?";
		_url.replaceFirst(regex, schema);
		return this;
	}

	public GSize getSize()
	{
		return _size;
	}

	/**
	 * The display size of the sprite or image. When using sprites, you must specify the sprite size. If the size is not
	 * provided, it will be set when the image loads.
	 */
	public GIcon setSize(GSize size)
	{
		_size = size;
		return this;
	}

	public GPoint getOrigin()
	{
		return _origin;
	}

	/**
	 * The position of the image within a sprite, if any. By default, the origin is located at the top left corner of
	 * the image (0, 0).
	 */
	public GIcon setOrigin(GPoint origin)
	{
		_origin = origin;
		return this;
	}

	public GSize getScaledSize()
	{
		return _scaledSize;
	}

	/**
	 * The size of the entire image after scaling, if any. Use this property to stretch/shrink an image or a sprite.
	 */
	public GIcon setScaledSize(GSize scaledSize)
	{
		_scaledSize = scaledSize;
		return this;
	}

	public GPoint getAnchor()
	{
		return _anchor;
	}

	/**
	 * The position at which to anchor an image in correspondance to the location of the marker on the map. By default,
	 * the anchor is located along the center point of the bottom of the image.
	 */
	public GIcon setAnchor(GPoint anchor)
	{
		_anchor = anchor;
		return this;
	}

	public String getUrl()
	{
		return _url;
	}

}