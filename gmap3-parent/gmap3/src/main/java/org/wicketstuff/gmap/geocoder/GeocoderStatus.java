/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.gmap.geocoder;

/**
 * Possible values of return status field.
 * 
 * See <a href="https://developers.google.com/maps/documentation/geocoding/#StatusCodes">https://developers.google.com/maps/documentation/geocoding/#StatusCodes</a>.
 * @author Dieter Tremel <tremel@tremel-computer.de>
 */
public enum GeocoderStatus {

    /**
     * indicates that no errors occurred; the address was successfully parsed
     * and at least one geocode was returned.
     */
    OK,
    /**
     * indicates that the geocode was successful but returned no results. This
     * may occur if the geocode was passed a non-existent address or a latlng in
     * a remote location.
     */
    ZERO_RESULTS,
    /**
     * indicates that you are over your quota.
     */
    OVER_QUERY_LIMIT,
    /**
     * indicates that your request was denied, possibly because the request
     * includes a result_type or location_type parameter but does not include an
     * API key or client ID.
     */
    REQUEST_DENIED,
    /**
     * generally indicates that the query (address, components or latlng) is
     * missing or an invalid result_type or location_type was given.
     */
    INVALID_REQUEST,
    /**
     * indicates that the request could not be processed due to a server error.
     * The request may succeed if you try again.
     */
    UNKNOWN_ERROR
}
