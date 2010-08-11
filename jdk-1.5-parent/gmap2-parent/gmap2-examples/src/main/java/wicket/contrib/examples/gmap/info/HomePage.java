package wicket.contrib.examples.gmap.info;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMapHeaderContributor;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GInfoWindowTab;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.event.ClickListener;
import wicket.contrib.gmap.event.InfoWindowCloseListener;
import wicket.contrib.gmap.event.InfoWindowOpenListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;

	private final GMap2 map;

	private final Label infoWindow;

	public HomePage()
	{
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		map = new GMap2("bottomPanel", new GMapHeaderContributor(GMapExampleApplication
				.get().getGoogleMapsAPIkey()));
		map.setOutputMarkupId(true);
		map.setMapType(GMapType.G_SATELLITE_MAP);
		map.setScrollWheelZoomEnabled(true);
		map.add(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, GLatLng gLatLng, GOverlay overlay)
			{
				if (gLatLng != null)
				{
					map.getInfoWindow().open(gLatLng, new HelloPanel());
				}
			}

		});
		map.add(new InfoWindowCloseListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onInfoWindowClose(AjaxRequestTarget target)
			{
				info("InfoWindow was closed");
				target.addComponent(feedback);
			}
		});
		map.add(new InfoWindowOpenListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onInfoWindowOpen(AjaxRequestTarget target)
			{
				info("InfoWindow was opened");
				target.addComponent(feedback);
			}
		});
		map.addControl(GControl.GSmallMapControl);
		map.getInfoWindow().open(new GLatLng(37.5, -122.1),
				new GInfoWindowTab("One", new HelloPanel()),
				new GInfoWindowTab("Two", new HelloPanel()));
		add(map);

		infoWindow = new Label("infoWindow", "openInfoWindow");
		infoWindow.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				map.getInfoWindow().open(new GLatLng(37.5, -122.1), new HelloPanel());
			}
		});
		add(infoWindow);
		add(new Link<Object>("reload")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
			}
		});
	}
}
