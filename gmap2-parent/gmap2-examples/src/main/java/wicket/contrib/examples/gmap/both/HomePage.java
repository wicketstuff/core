package wicket.contrib.examples.gmap.both;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMap2.SetMapTypeBehavior;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.event.MapTypeChangedListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	private final GMap2 map;

	private final Label mapTypeLabel;

	public HomePage()
	{
		map = new GMap2("panel", GMapExampleApplication.get().getGoogleMapsAPIkey());
		map.addControl(GControl.GMapTypeControl);
		add(map);
		map.add(new MapTypeChangedListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onMapTypeChanged(AjaxRequestTarget target)
			{
				target.addComponent(mapTypeLabel);
			}
		});
		mapTypeLabel = new Label("switchLabel", new PropertyModel<GMapType>(map, "mapType"));
		mapTypeLabel.add(map.new SetMapTypeBehavior("onclick", GMapType.G_HYBRID_MAP));
		mapTypeLabel.setOutputMarkupId(true);
		add(mapTypeLabel);
	}
}
