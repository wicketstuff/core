/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.examples;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.time.Duration;

/**
 * Application class for GMap Examples
 */
public abstract class GMapExampleApplication extends WebApplication
{

	private static final String GOOGLE_MAPS_API_KEY_PARAM = "GoogleMapsAPIkey";

	/**
	 * Covariant override for easy getting the current
	 * {@link GMapExampleApplication} without having to cast it.
	 */
	public static GMapExampleApplication get()
	{
		WebApplication webApplication = WebApplication.get();

		if (webApplication instanceof GMapExampleApplication == false)
		{
			throw new WicketRuntimeException(
					"The application attached to the current thread is not a "
							+ GMapExampleApplication.class.getSimpleName());
		}

		return (GMapExampleApplication)webApplication;
	}

	@Override
	protected void init()
	{
		super.init();
		getResourceSettings().setResourcePollFrequency(Duration.seconds(10));
		// Due to Firefox 3.0 we strip the wicket tags,
		// even in develop mode.
		// http://www.nabble.com/Gmap2-problem-with-Firefox-3.0-to18137475.html
		getMarkupSettings().setStripWicketTags(true);
	}

	/**
	 * Gets the init parameter 'GoogleMapsAPIkey' of the filter, or throws a
	 * WicketRuntimeException, if it is not set.
	 * 
	 * Pay attention at webapp deploy context, we need a different key for each
	 * deploy context check <a
	 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign
	 * Up</a> for more info.
	 * 
	 * Also the GClientGeocoder is pickier on this than the GMap2. Running on
	 * 'localhost' GMap2 will ignore the key and the maps will show up, but
	 * GClientGeocoder will not. So if the key doesn't match the url down to the
	 * directory GClientGeocoder will not work.
	 * 
	 * <pre>
	 * [...]
	 * &lt;init-param&gt;
	 *     &lt;param-name&gt;GoogleMapsAPIkey&lt;/param-name&gt;
	 *     &lt;param-value&gt;ABQIAAAAzaZpf6nHOd9w1PfLaM9u2xQRS2YPSd8S9D1NKPBvdB1fr18_CxR-svEYj6URCf5QDFq3i03mqrDlbA&lt;/param-value&gt;
	 * &lt;/init-param&gt;
	 * [...]
	 * </pre>
	 * 
	 * @return Google Maps API key
	 */
	public String getGoogleMapsAPIkey()
	{
		String googleMapsAPIkey = getInitParameter(GOOGLE_MAPS_API_KEY_PARAM);
		if (googleMapsAPIkey == null)
		{
			throw new WicketRuntimeException("There is no Google Maps API key configured in the "
					+ "deployment descriptor of this application.");
		}
		return googleMapsAPIkey;
	}
}