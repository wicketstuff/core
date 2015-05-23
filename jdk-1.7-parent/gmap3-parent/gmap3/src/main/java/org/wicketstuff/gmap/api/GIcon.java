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
package org.wicketstuff.gmap.api;

/**
 * http://code.google.com/apis/maps/documentation/javascript/reference.html# MarkerImage
 *
 * @author Christian Hennig (christian.hennig@freiheit.com)
 */
public class GIcon implements GValue, Cloneable
{

    private static final long serialVersionUID = 1714038753187423501L;
    private static final String DEFAULT_URL = "http://www.google.com/mapfiles/marker%s.png";
    private final String url;
    private GSize size = null;
    private GPoint origin = null;
    private GSize scaledSize = null;
    private GPoint anchor = null;

    public GIcon()
    {
        url = DEFAULT_URL;
    }

    public GIcon(final String image)
    {
        this.url = image;

    }

    public GIcon(final char character) {
        String letter;
        if (Character.isLetter(character)) {
            letter = String.valueOf(Character.toUpperCase(character));
        } else {
            letter = "";
        }
        url = String.format(DEFAULT_URL, letter);
    }

    public GIcon(final String image, final GSize iconSize, final GPoint iconAnchor, final GPoint origin,
            final GSize scaledSize)
    {
        this.url = image;
        this.size = iconSize;
        this.anchor = iconAnchor;
        this.origin = origin;
        this.scaledSize = scaledSize;
    }

    public GIcon(final String image, final GSize iconSize, final GPoint iconAnchor)
    {
        this.url = image;
        this.size = iconSize;
        this.anchor = iconAnchor;
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
    @Override
    public String getJSconstructor()
    {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("(function() {\n");
        buffer.append("var icon = {\n");
        buffer.append("url: '").append(url).append("',\n");

        if (size != null)
        {
            buffer.append("size: ").append(size.getJSconstructor()).append(",\n");
        }

        if (anchor != null)
        {
            buffer.append("anchor: ").append(anchor.getJSconstructor()).append(",\n");
        }

        if (origin != null)
        {
            buffer.append("origin: ").append(origin.getJSconstructor()).append(",\n");
        }
        if (scaledSize != null)
        {
            buffer.append("scaledSize: ").append(scaledSize.getJSconstructor()).append("\n");
        }                
        
        buffer.append("}\n");
        buffer.append("return icon;\n");
        buffer.append("})()\n");
        return buffer.toString();
    }

    public GIcon setSchema(final String schema)
    {
        final String regex = "https?";
        url.replaceFirst(regex, schema);
        return this;
    }

    public GSize getSize()
    {
        return size;
    }

    /**
     * The display size of the sprite or image. When using sprites, you must specify the sprite size. If the size is not
     * provided, it will be set when the image loads.
     */
    public GIcon setSize(GSize size)
    {
        this.size = size;
        return this;
    }

    public GPoint getOrigin()
    {
        return origin;
    }

    /**
     * The position of the image within a sprite, if any. By default, the origin is located at the top left corner of
     * the image (0, 0).
     */
    public GIcon setOrigin(GPoint origin)
    {
        this.origin = origin;
        return this;
    }

    public GSize getScaledSize()
    {
        return scaledSize;
    }

    /**
     * The size of the entire image after scaling, if any. Use this property to stretch/shrink an image or a sprite.
     */
    public GIcon setScaledSize(GSize scaledSize)
    {
        this.scaledSize = scaledSize;
        return this;
    }

    public GPoint getAnchor()
    {
        return anchor;
    }

    /**
     * The position at which to anchor an image in correspondance to the location of the marker on the map. By default,
     * the anchor is located along the center point of the bottom of the image.
     */
    public GIcon setAnchor(GPoint anchor)
    {
        this.anchor = anchor;
        return this;
    }

    public String getUrl()
    {
        return url;
    }
}
