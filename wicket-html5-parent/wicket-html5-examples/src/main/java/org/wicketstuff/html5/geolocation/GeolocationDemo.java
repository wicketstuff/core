package org.wicketstuff.html5.geolocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.wicketstuff.html5.BasePage;

/**
 * Demonstrates the use of the HTML5 Geolocation API.
 * The demo tries to obtain a location (longitude and latitude) and use this 
 * location to obtain textual information about this location.
 * The reverse geocoding is done via OpenStreemap,
 * see http://wiki.openstreetmap.org/wiki/Nominatim#Reverse_Geocoding_.2F_Address_lookup
 */
public class GeolocationDemo extends BasePage
{

    private static final long serialVersionUID = -5976829447267815420L;
    private static final String BASE_URL = "http://nominatim.openstreetmap.org/reverse?format=xml&zoom=18&addressdetails=1&";
    private String result = "";

    public GeolocationDemo()
    {

        Model<String> resultModel = new Model<String>()
        {
            @Override
            public String getObject()
            {
                return result;
            }
        };

        final WebMarkupContainer wmcResult = new WebMarkupContainer("wmcResult");
        wmcResult.setOutputMarkupId(true);
        MultiLineLabel lbResult = new MultiLineLabel("result", resultModel);

        wmcResult.add(lbResult);
        add(wmcResult);


        final WebMarkupContainer geoLocator = new WebMarkupContainer("geolocator");

        AjaxGeolocationBehavior geolocationBehaviour = new AjaxGeolocationBehavior()
        {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onGeoAvailable(AjaxRequestTarget target, String latitude,
                    String longitude)
            {
                // permission to locate user was granted
                // we ask the OpenStreetMap webservice for details about this location 
                // and display the XML-response as a plain string 
                // (to keep things simple we are not parsing the XML)
                result = "We received a location. Latitude: "+ latitude+" Longitude: " + longitude +"\n";
                result = result + " The XML-Resonse from OpenStreetMap: \n" + proxyRequest(latitude, longitude);
                target.add(wmcResult);
            }

            @Override
            protected void onNotAvailable(AjaxRequestTarget target, String errorCode, String errorMessage)
            {
                // if the location is not available, show the reason why
                result = "An error occured. Error code: "+ errorCode + " Error message:" + errorMessage;
                target.add(wmcResult);
            }
        };
        // if the user does not react on the request to share his location with
        // us within 5 seconds we want the onNotAvailable-method to get triggered
        geolocationBehaviour.setTimeout(5000);

        geoLocator.add(geolocationBehaviour);


        add(geoLocator);
    }

    /**
     * Asks the OpenStreetMap webservice about details for the provided
     * location.
     * @param latitude Latitude value of the position
     * @param longitude Longitude value of the position 
     * @return the XML-response from the webservice as a plain string
     */
    private final String proxyRequest(final String latitude, final String longitude)
    {

        final StringBuilder sb = new StringBuilder();
        InputStream stream = null;

        try
        {
            String urlStr = BASE_URL + "lat=" + latitude + "&lon=" + longitude;

            final URL url = new URL(urlStr);
            stream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try
            {
                String line = "";
                while (line != null)
                {
                    line = reader.readLine();
                    if (line != null)
                    {
                        sb.append(line);
                        sb.append("\n");
                    }
                }
            }
            finally
            {
                reader.close();
            }

        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (stream != null)
            {
                try
                {
                    stream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
