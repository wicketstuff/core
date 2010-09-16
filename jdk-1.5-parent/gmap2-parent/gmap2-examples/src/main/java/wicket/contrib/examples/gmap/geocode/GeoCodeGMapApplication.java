package wicket.contrib.examples.gmap.geocode;

import org.apache.wicket.Application;
import org.apache.wicket.Page;

import wicket.contrib.examples.GMapExampleApplication;

public class GeoCodeGMapApplication extends GMapExampleApplication
{
	private ServerGeocoder serverGeocoder = null;
	
	
	/**
	 * @see wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init()
	{
		super.init();
		
		this.serverGeocoder = new ServerGeocoder(getGoogleMapsAPIkey());
	}
	
	
	public static GeoCodeGMapApplication get() {
		return (GeoCodeGMapApplication)Application.get();
	}
	
	

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}


	/**
	 * @return the serverGeocoder
	 */
	public ServerGeocoder getServerGeocoder() {
		return serverGeocoder;
	}
}
