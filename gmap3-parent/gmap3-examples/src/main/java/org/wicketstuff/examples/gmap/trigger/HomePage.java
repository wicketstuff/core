package org.wicketstuff.examples.gmap.trigger;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;

/**
 * Demonstrates how to trigger events on Map.
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		final GMap map = new GMap("map");
		map.setOutputMarkupId( true );
		add(map);

		WebMarkupContainer resize = new WebMarkupContainer( "resize" );
		resize.add( new AttributeModifier( "onclick", createResizeScript(map, 600, 600) + map.getTriggerResizeScript().toString() ) );
		add(resize);

		WebMarkupContainer resizeWrong = new WebMarkupContainer( "resizeWrong" );
		resizeWrong.add( new AttributeModifier( "onclick", createResizeScript(map, 600, 600) ));
		add(resizeWrong);
	}

	private String createResizeScript(GMap map, int width, int height) {
		return "$('#"+map.getMarkupId()+"').css('width', '" + width +"px'); "+"$('#"+map.getMarkupId()+"').css('height', '" + height +"px');";
	}
}
