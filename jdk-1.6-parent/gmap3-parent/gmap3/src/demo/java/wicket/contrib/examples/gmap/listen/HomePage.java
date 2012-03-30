package wicket.contrib.examples.gmap.listen;

import java.util.Locale;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GControl;
import wicket.contrib.gmap3.api.GLatLngBounds;
import wicket.contrib.gmap3.event.LoadListener;
import wicket.contrib.gmap3.event.MoveEndListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    private final Label zoomLabel;

    private final MultiLineLabel boundsLabel;

    private MoveEndListener moveEndBehavior;

    public HomePage() {
        final GMap map = new GMap( "map" );
        map.addControl( GControl.GLargeMapControl );
        add( map );
        moveEndBehavior = new MyMoveEndListener();
        map.add( moveEndBehavior );
        map.add( new LoadListener() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onLoad( AjaxRequestTarget target ) {
                target.addComponent( boundsLabel );
            }
        } );

        zoomLabel = new Label( "zoom", new PropertyModel<Integer>( map, "zoom" ) );
        zoomLabel.setOutputMarkupId( true );
        add( zoomLabel );

        boundsLabel = new MultiLineLabel( "bounds", new PropertyModel<GLatLngBounds>( map, "bounds" ) ) {
            private static final long serialVersionUID = 1L;

            @Override
            public IConverter getConverter( @SuppressWarnings( "rawtypes" ) Class type ) {
                if ( GLatLngBounds.class.isAssignableFrom( type ) ) {
                    return new IConverter() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public GLatLngBounds convertToObject( String value, Locale locale ) {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public String convertToString( Object value, Locale locale ) {
                            GLatLngBounds bounds = (GLatLngBounds) value;

                            StringBuffer buffer = new StringBuffer();
                            buffer.append( "NE (" );
                            buffer.append( bounds.getNE().getLat() );
                            buffer.append( "," );
                            buffer.append( bounds.getNE().getLng() );
                            buffer.append( ")\nSW (" );
                            buffer.append( bounds.getSW().getLat() );
                            buffer.append( "," );
                            buffer.append( bounds.getSW().getLng() );
                            buffer.append( ")" );
                            return buffer.toString();
                        }
                    };
                }
                return super.getConverter( type );
            }

        };
        boundsLabel.setOutputMarkupId( true );
        add( boundsLabel );
        final Label enabledLabel = new Label( "enabled", new Model<Boolean>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean getObject() {
                return map.getBehaviors().contains( moveEndBehavior );
            }
        } );
        enabledLabel.add( new AjaxEventBehavior( "onclick" ) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onEvent( AjaxRequestTarget target ) {
                if ( map.getBehaviors().contains( moveEndBehavior ) ) {
                    map.remove( moveEndBehavior );
                } else {
                    // TODO AbstractAjaxBehaviors are not reusable, so
                    // we have
                    // to recreate:
                    // https://issues.apache.org/jira/browse/WICKET-713
                    moveEndBehavior = new MyMoveEndListener();
                    map.add( moveEndBehavior );
                }
                target.addComponent( map );
                target.addComponent( enabledLabel );
            }
        } );
        add( enabledLabel );
    }

    private class MyMoveEndListener extends MoveEndListener {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onMoveEnd( AjaxRequestTarget target ) {
            target.addComponent( zoomLabel );
            target.addComponent( boundsLabel );
        }
    }
}
