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
package org.wicketstuff.gmap.geocoder;

import java.io.IOException;

public class GeocoderException extends IOException
{
    private final GeocoderStatus _status;

    public GeocoderException(GeocoderStatus status) {
        super(status.toString());
        _status = status;
    }

    public final GeocoderStatus getStatus() {
        return _status;
    }
}