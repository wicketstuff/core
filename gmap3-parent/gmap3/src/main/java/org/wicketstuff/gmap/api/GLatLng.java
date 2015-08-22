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

import java.math.BigDecimal;
import java.util.StringTokenizer;

import org.wicketstuff.gmap.js.Constructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Google Maps API's <a href=
 * "http://code.google.com/apis/maps/documentation/javascript/reference.html#LatLng" >LatLng</a>.
 */
public class GLatLng implements GValue
{

    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private final double lat;
    private final double lng;
    private final boolean unbounded;

    /**
     * Construct.
     *
     * @param lat
     * @param lng
     */
	@JsonCreator
    public GLatLng(@JsonProperty("lat") double lat, @JsonProperty("lng") double lng)
    {
        this(lat, lng, false);
    }

    /**
     * Construct.
     *
     * @param lat
     * @param lng
     * @param unbounded
     */
    public GLatLng(double lat, double lng, boolean unbounded)
    {
        this.lat = lat;
        this.lng = lng;
        this.unbounded = unbounded;
    }

    public double getLat()
    {
        return lat;
    }

    public double getLng()
    {
        return lng;
    }

    @Override
    public String toString()
    {
        return getJSconstructor();
    }

    @Override
    public String getJSconstructor()
    {

        return new Constructor("google.maps.LatLng").add(BigDecimal.valueOf(lat).toString()).add(BigDecimal.valueOf(lng).toString()).add(
                Boolean.valueOf(unbounded)).toJS();
    }

    public String getArguments()
    {

        return new StringBuilder().append(BigDecimal.valueOf(lat).toString()).append(",").append(BigDecimal.valueOf(lng).toString()).append(",").append(
                Boolean.valueOf(unbounded)).toString();
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int PRIME = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = PRIME * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = PRIME * result + (int) (temp ^ (temp >>> 32));
        result = PRIME * result + (unbounded
                ? 1231
                : 1237);
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!super.equals(obj))
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final GLatLng other = (GLatLng) obj;
        if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
        {
            return false;
        }
        if (Double.doubleToLongBits(lng) != Double.doubleToLongBits(other.lng))
        {
            return false;
        }
        if (unbounded != other.unbounded)
        {
            return false;
        }
        return true;
    }

    /**
     * (37.34068368469045, -122.48519897460936)
     */
    public static GLatLng parse(String value)
    {
        try
        {
            StringTokenizer tokenizer = new StringTokenizer(value, "(, )");

            float lat = Float.valueOf(tokenizer.nextToken());
            float lng = Float.valueOf(tokenizer.nextToken());
            return new GLatLng(lat, lng);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
