/*
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

import java.util.StringTokenizer;
import org.wicketstuff.gmap.js.Constructor;

/**
 * Represents an Google Maps API's <a href= "http://www.google.com/apis/maps/documentation/reference.html#GLatLngBounds"
 * >GLatLngBounds</a>.
 */
public class GLatLngBounds implements GValue
{

    /** Default serialVersionUID */
    private static final long serialVersionUID = 1L;
    private final GLatLng sw;
    private final GLatLng ne;

    /**
     * Construct.
     *
     * @param sw
     * @param ne
     */
    public GLatLngBounds(GLatLng sw, GLatLng ne)
    {
        this.sw = sw;
        this.ne = ne;
    }

    public GLatLng getSW()
    {
        return sw;
    }

    public GLatLng getNE()
    {
        return ne;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return getJSconstructor();
    }

    /**
     * @see wicket.contrib.gmap.api.GValue#getJSconstructor()
     */
    public String getJSconstructor()
    {
        return new Constructor("google.maps.LatLngBounds").add(sw.getJSconstructor()).add(ne.getJSconstructor())
                .toJS();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return sw.hashCode() ^ ne.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof GLatLngBounds)
        {
            GLatLngBounds t = (GLatLngBounds) obj;
            return t.sw.equals(sw) && t.ne.equals(ne);
        }
        return false;
    }

    /**
     * ((37.34068368469045, -122.48519897460936), (37.72184917678752, -121.79855346679686))
     */
    public static GLatLngBounds parse(String value)
    {
        StringTokenizer tokenizer;
        try
        {
            tokenizer = new StringTokenizer(value, "(, )");
        }
        catch (NullPointerException e)
        {
            return null;
        }
        if (tokenizer.countTokens() != 4)
        {
            return null;
        }

        GLatLng sw = new GLatLng(Float.valueOf(tokenizer.nextToken()), Float.valueOf(tokenizer.nextToken()));
        GLatLng ne = new GLatLng(Float.valueOf(tokenizer.nextToken()), Float.valueOf(tokenizer.nextToken()));
        return new GLatLngBounds(sw, ne);
    }
}
