package wicket.contrib.examples.gmap.top;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.event.ClickListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;

	private final Label markerLabel;

	public HomePage()
	{
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		final GMap2 topMap = new GMap2("topPanel", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		topMap.setDoubleClickZoomEnabled(true);
		topMap.add(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, GLatLng latLng, GOverlay overlay)
			{
				GMarker marker = (overlay instanceof GMarker) ? (GMarker)overlay : null;
				if (marker != null)
				{
					topMap.getInfoWindow().open(marker, new HelloPanel());
				}
				else if (latLng != null)
				{
					marker = new GMarker(latLng);
					topMap.addOverlay(marker);
				}
				markerSelected(target, marker);
			}
		});
		topMap.setZoom(10);
		GMarkerOptions options = new GMarkerOptions("Home").draggable(true).autoPan(true);
		topMap.addOverlay(new GMarker(new GLatLng(37.4, -122.1), options));
		topMap.addControl(GControl.GLargeMapControl);
		topMap.addControl(GControl.GMapTypeControl);
		add(topMap);

		final IModel<GMarker> markerModel = new Model<GMarker>(null);
		markerLabel = new Label("markerLabel", markerModel);
		markerLabel.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				GMarker marker = markerModel.getObject();
				if (marker != null)
				{
					GLatLng point = marker.getLatLng();

					GMarker random = new GMarker(new GLatLng(point.getLat()
							* (0.9995 + Math.random() / 1000), point.getLng()
							* (0.9995 + Math.random() / 1000)));

					topMap.addOverlay(random);
				}
			}
		});
		add(markerLabel);

		add(new Link<Object>("reload")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
			}
		});
	}

	private void markerSelected(AjaxRequestTarget target, GMarker marker)
	{
		markerLabel.setDefaultModelObject(marker);
		target.addComponent(markerLabel);
	}
}