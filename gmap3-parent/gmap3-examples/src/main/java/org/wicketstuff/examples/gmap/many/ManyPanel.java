package org.wicketstuff.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GMapType;

public abstract class ManyPanel extends Panel
{

    private static final long serialVersionUID = 591561714018540952L;
    final GMap gMap;
    private final WebMarkupContainer n, ne, e, se, s, sw, w, nw;

    public ManyPanel(String id)
    {
        super(id);
        gMap = new GMap("gMap", WicketExamplePage.API_KEY);
        gMap.setZoom(7);
        gMap.setOutputMarkupId(true);
        add(gMap);
        final AjaxLink<Void> normal = new AjaxLink<Void>("normal")
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                gMap.setMapType(GMapType.ROADMAP);
            }
        };
        add(normal);
        final AjaxLink<Void> satellite = new AjaxLink<Void>("satellite")
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                gMap.setMapType(GMapType.SATELLITE);
            }
        };
        add(satellite);
        final AjaxLink<Void> hybrid = new AjaxLink<Void>("hybrid")
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                gMap.setMapType(GMapType.HYBRID);
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

        AjaxLink<Object> close = new AjaxLink<Object>("close")
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
