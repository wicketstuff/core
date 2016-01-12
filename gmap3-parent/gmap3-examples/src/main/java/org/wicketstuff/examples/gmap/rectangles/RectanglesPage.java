package org.wicketstuff.examples.gmap.rectangles;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GEvent;
import org.wicketstuff.gmap.api.GEventHandler;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GLatLngBounds;
import org.wicketstuff.gmap.api.GRectangle;

/**
 * Demonstrates the usage of rectangles.
 */
public class RectanglesPage extends WicketExamplePage
{

	/**
	 * Displays a rectangle on the map which can be resized and dragged around.
	 */
	public RectanglesPage()
	{
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		feedback.setOutputMarkupId(true);

		GMap map = new GMap("topPanel");
		GRectangle rectangle = new GRectangle(new GLatLngBounds(new GLatLng(37.35, -121.9), new GLatLng(37.45, -121.8)), "#000000", 4, 0.7f, "#E9601A", 0.7f);
		rectangle.setEditable(true);
		rectangle.setDraggable(true);
		GEventHandler eventHandler = new GEventHandler()
		{
			@Override
			public void onEvent(AjaxRequestTarget target)
			{
				WebRequest request = (WebRequest) getRequest();
				IRequestParameters list = request.getRequestParameters();
				StringValue event = request.getRequestParameters().getParameterValue("overlay.event");
				feedback.success("Triggered event: " + event);
				StringValue bounds = request.getRequestParameters().getParameterValue("overlay.bounds");
				feedback.success("Bounds: " + bounds);
				if (event.toString().equals("dragend"))
				{
					feedback.success("Draging ended");
				}

				target.add(feedback);
			}
		};
		// we want to get notified when the user changes the bounds of the rectangle
		// or after he stopped draging the rectangle around or clicking with the mouse
		// on it
		rectangle.addListener(GEvent.dragend, eventHandler);
		rectangle.addListener(GEvent.bounds_changed, eventHandler);
		rectangle.addListener(GEvent.mousedown, eventHandler);
		rectangle.addListener(GEvent.mouseup, eventHandler);

		map.addOverlay(rectangle);
		map.setZoom(9);
		add(map);
	}
}
