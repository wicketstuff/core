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
package wicket.contrib.gmap.util;

import java.io.IOException;

public class GeocoderException extends IOException {

	private static final long serialVersionUID = 1L;

	public static final int G_GEO_SUCCESS = 200;

	public static final int G_GEO_BAD_REQUEST = 400;

	public static final int G_GEO_SERVER_ERROR = 500;

	public static final int G_GEO_MISSING_QUERY = 601;

	public static final int G_GEO_UNKNOWN_ADDRESS = 602;

	public static final int G_GEO_UNAVAILABLE_ADDRESS = 603;

	public static final int G_GEO_UNKNOWN_DIRECTIONS = 604;

	public static final int G_GEO_BAD_KEY = 610;

	public static final int G_GEO_TOO_MANY_QUERIES = 620;

	private int status;

	public GeocoderException(int status) {
		super("Status " + status);

		this.status = status;
	}

	public final int getStatus() {
		return status;
	}
}