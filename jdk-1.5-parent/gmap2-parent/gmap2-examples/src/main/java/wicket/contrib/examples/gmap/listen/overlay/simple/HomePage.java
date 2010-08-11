package wicket.contrib.examples.gmap.listen.overlay.simple;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GEvent;
import wicket.contrib.gmap.api.GEventHandler;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		final GMap2 topMap = new GMap2("topPanel", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		topMap.addControl(GControl.GLargeMapControl);
		add(topMap);

		GMarkerOptions options = new GMarkerOptions().draggable(true);
		final GMarker marker = new GMarker(topMap.getCenter(), options);
		final Label label = new Label("label", new PropertyModel<GLatLng>(marker,
				"latLng"));
		label.setOutputMarkupId(true);
		add(label);
		marker.addListener(GEvent.dragend, new GEventHandler()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(AjaxRequestTarget target)
			{
				target.addComponent(label);
			}
		});
		topMap.addOverlay(marker);
	}
}
