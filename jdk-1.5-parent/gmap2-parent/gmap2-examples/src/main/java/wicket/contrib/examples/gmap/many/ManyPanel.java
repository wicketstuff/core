package wicket.contrib.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GMapType;

public abstract class ManyPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	final GMap2 gMap;

	private WebMarkupContainer n, ne, e, se, s, sw, w, nw;

	public ManyPanel(String id, String gMapKey)
	{
		super(id);
		gMap = new GMap2("gMap", gMapKey);
		gMap.setZoom(7);
		gMap.setOutputMarkupId(true);
		add(gMap);
		final AjaxFallbackLink<Void> normal = new AjaxFallbackLink<Void>("normal")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				gMap.setMapType(GMapType.G_NORMAL_MAP);
			}
		};
		add(normal);
		final AjaxFallbackLink<Void> satellite = new AjaxFallbackLink<Void>("satellite")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				gMap.setMapType(GMapType.G_SATELLITE_MAP);
			}
		};
		add(satellite);
		final AjaxFallbackLink<Void> hybrid = new AjaxFallbackLink<Void>("hybrid")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				gMap.setMapType(GMapType.G_HYBRID_MAP);
			}
		};
		add(hybrid);
		n = new WebMarkupContainer("n");
		add(n);
		ne = new WebMarkupContainer("ne");
		add(ne);
		e = new WebMarkupContainer("e");
		add(e);
		se = new WebMarkupContainer("se");
		add(se);
		s = new WebMarkupContainer("s");
		add(s);
		sw = new WebMarkupContainer("sw");
		add(sw);
		w = new WebMarkupContainer("w");
		add(w);
		nw = new WebMarkupContainer("nw");
		add(nw);

		n.add(gMap.new PanDirectionBehavior("onclick", 0, 1));
		ne.add(gMap.new PanDirectionBehavior("onclick", -1, 1));
		e.add(gMap.new PanDirectionBehavior("onclick", -1, 0));
		se.add(gMap.new PanDirectionBehavior("onclick", -1, -1));
		s.add(gMap.new PanDirectionBehavior("onclick", 0, -1));
		sw.add(gMap.new PanDirectionBehavior("onclick", 1, -1));
		w.add(gMap.new PanDirectionBehavior("onclick", 1, 0));
		nw.add(gMap.new PanDirectionBehavior("onclick", 1, 1));

		AjaxFallbackLink<Object> close = new AjaxFallbackLink<Object>("close")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				closing(target);
			}
		};
		add(close);
	}

	protected abstract void closing(AjaxRequestTarget target);
}
