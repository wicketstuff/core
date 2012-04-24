package wicket.contrib.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GMapType;

public abstract class ManyPanel extends Panel {

    private static final long serialVersionUID = 591561714018540952L;

    final GMap _gMap;

    private final WebMarkupContainer n, ne, e, se, s, sw, w, nw;

    public ManyPanel( String id ) {
        super( id );
        _gMap = new GMap( "gMap" );
        _gMap.setZoom( 7 );
        _gMap.setOutputMarkupId( true );
        add( _gMap );
        final AjaxFallbackLink<Void> normal = new AjaxFallbackLink<Void>( "normal" ) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick( AjaxRequestTarget target ) {
                _gMap.setMapType( GMapType.ROADMAP );
            }
        };
        add( normal );
        final AjaxFallbackLink<Void> satellite = new AjaxFallbackLink<Void>( "satellite" ) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick( AjaxRequestTarget target ) {
                _gMap.setMapType( GMapType.SATELLITE );
            }
        };
        add( satellite );
        final AjaxFallbackLink<Void> hybrid = new AjaxFallbackLink<Void>( "hybrid" ) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick( AjaxRequestTarget target ) {
                _gMap.setMapType( GMapType.HYBRID );
            }
        };
        add( hybrid );
        n = new WebMarkupContainer( "n" );
        add( n );
        ne = new WebMarkupContainer( "ne" );
        add( ne );
        e = new WebMarkupContainer( "e" );
        add( e );
        se = new WebMarkupContainer( "se" );
        add( se );
        s = new WebMarkupContainer( "s" );
        add( s );
        sw = new WebMarkupContainer( "sw" );
        add( sw );
        w = new WebMarkupContainer( "w" );
        add( w );
        nw = new WebMarkupContainer( "nw" );
        add( nw );

        n.add( _gMap.new PanDirectionBehavior( "onclick", 0, 1 ) );
        ne.add( _gMap.new PanDirectionBehavior( "onclick", -1, 1 ) );
        e.add( _gMap.new PanDirectionBehavior( "onclick", -1, 0 ) );
        se.add( _gMap.new PanDirectionBehavior( "onclick", -1, -1 ) );
        s.add( _gMap.new PanDirectionBehavior( "onclick", 0, -1 ) );
        sw.add( _gMap.new PanDirectionBehavior( "onclick", 1, -1 ) );
        w.add( _gMap.new PanDirectionBehavior( "onclick", 1, 0 ) );
        nw.add( _gMap.new PanDirectionBehavior( "onclick", 1, 1 ) );

        AjaxFallbackLink<Object> close = new AjaxFallbackLink<Object>( "close" ) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick( AjaxRequestTarget target ) {
                closing( target );
            }
        };
        add( close );
    }

    protected abstract void closing( AjaxRequestTarget target );
}
