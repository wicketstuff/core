package wicket.contrib.examples.gmap.listen.overlay.advanced;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GControl;
import wicket.contrib.gmap3.api.GEvent;
import wicket.contrib.gmap3.api.GEventHandler;
import wicket.contrib.gmap3.api.GMarker;
import wicket.contrib.gmap3.api.GMarkerOptions;
import wicket.contrib.gmap3.api.GOverlay;
import wicket.contrib.gmap3.api.LatLng;
import wicket.contrib.gmap3.event.ClickListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project.
 */
public class HomePage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public HomePage() {
        final GMap map = new GMap( "map" );
        map.addControl( GControl.GLargeMapControl );
        add( map );
        final WebMarkupContainer repeaterParent = new WebMarkupContainer( "repeaterParent" );
        repeaterParent.setOutputMarkupId( true );
        add( repeaterParent );
        final RepeatingView rv = new RepeatingView( "label" );
        rv.setOutputMarkupId( true );
        repeaterParent.add( rv );
        map.add( new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onClick( AjaxRequestTarget target, LatLng latLng, GOverlay overlay ) {
                if ( latLng != null ) {
                    if ( map.getOverlays().size() >= 3 ) {
                        map.removeOverlay( map.getOverlays().get( 0 ) );
                    }
                    final MyMarker marker = new MyMarker( new GMarkerOptions( map, latLng ).draggable( true ) ) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        GEventHandler getDragendHandler() {
                            return new GEventHandler() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public void onEvent( AjaxRequestTarget eventTarget ) {
                                    eventTarget.addComponent( repeaterParent );
                                }
                            };
                        }

                        @Override
                        GEventHandler getDblclickHandler() {
                            return new GEventHandler() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public void onEvent( AjaxRequestTarget eventTarget ) {
                                    eventTarget.addComponent( repeaterParent );
                                }
                            };
                        }

                    };

                    map.addOverlay( marker );
                    marker.addListener( GEvent.dragend, marker.getDragendHandler() );
                    rv.removeAll();
                    for ( GOverlay myMarker : map.getOverlays() ) {
                        final GOverlayPanel label =
                                new GOverlayPanel( myMarker.getId(), new CompoundPropertyModel<MyMarker>( myMarker ) );
                        label.setOutputMarkupId( true );
                        rv.add( label );
                    }

                    target.addComponent( repeaterParent );
                }
            }
        } );
    }

    /**
     * Panel for displaying and controlling the state of a GOverlay.
     */
    private static class GOverlayPanel extends Panel {
        private static final long serialVersionUID = 1L;

        public GOverlayPanel( String id, final IModel<MyMarker> model ) {
            super( id, model );
            add( new Label( "latLng" ) );
            final Label dragendLabel = new Label( "dragend", new Model<Boolean>() {
                private static final long serialVersionUID = 1L;

                /*
                 * (non-Javadoc)
                 * 
                 * @see org.apache.wicket.model.Model#getObject()
                 */
                @Override
                public Boolean getObject() {

                    return model.getObject().getListeners().containsKey( GEvent.dragend );
                }
            } );
            dragendLabel.add( new AjaxEventBehavior( "onclick" ) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onEvent( AjaxRequestTarget target ) {
                    MyMarker overlay = model.getObject();
                    if ( (Boolean) dragendLabel.getDefaultModelObject() ) {
                        overlay.clearListeners( GEvent.dragend );
                    } else {
                        overlay.addListener( GEvent.dragend, overlay.getDragendHandler() );
                    }
                    target.addComponent( GOverlayPanel.this );
                }

            } );
            add( dragendLabel );
            final Label dblclickLabel = new Label( "dblclick", new Model<Boolean>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean getObject() {

                    return ( (GOverlay) getDefaultModelObject() ).getListeners().containsKey( GEvent.dblclick );
                }
            } );
            dblclickLabel.add( new AjaxEventBehavior( "onclick" ) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onEvent( AjaxRequestTarget target ) {
                    MyMarker overlay = ( (MyMarker) GOverlayPanel.this.getDefaultModelObject() );
                    if ( (Boolean) dragendLabel.getDefaultModelObject() ) {
                        overlay.clearListeners( GEvent.dblclick );
                    } else {
                        overlay.addListener( GEvent.dblclick, overlay.getDblclickHandler() );
                    }
                    target.addComponent( GOverlayPanel.this );
                }

            } );
            add( dblclickLabel );
        }
    }

    /**
     * Extend a GMarker with factory methods for needed handler.
     * 
     */
    private static abstract class MyMarker extends GMarker {

        private static final long serialVersionUID = -5720177222106241564L;

        public MyMarker( GMarkerOptions options ) {
            super( options );
        }

        abstract GEventHandler getDblclickHandler();

        abstract GEventHandler getDragendHandler();
    }

}
