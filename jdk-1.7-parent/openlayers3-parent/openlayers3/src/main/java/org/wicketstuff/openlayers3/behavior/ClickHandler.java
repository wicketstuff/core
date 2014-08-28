package org.wicketstuff.openlayers3.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a behavior that handles clicks on the map.
 */
public abstract class ClickHandler extends AbstractDefaultAjaxBehavior {

    /**
     * Default projection.
     */
    public final static String DEFAULT_PROJECTION = "EPSG:4326";
    /**
     * Counter for generating instance identifiers.
     */
    private static Long counter = 0L;
    /**
     * The projection for this behavior, used to translate the 'clicked' coordinates.
     */
    private final String projection;

    /**
     * Creates a new instance.
     */
    public ClickHandler() {
        this(DEFAULT_PROJECTION);
    }

    /**
     * Creates a new instance.
     *
     * @param projection
     *         The clicked coordinate will be transformed into this projection
     */
    public ClickHandler(String projection) {
        this.projection = projection;
    }

    /**
     * Dispatches the clicked coordinates.
     *
     * @param target
     *         Ajax request target
     * @param longLat
     *         Longitude and latitude of the click
     */
    public abstract void handleClick(AjaxRequestTarget target, LongLat longLat);

    @Override
    protected void respond(AjaxRequestTarget target) {

        String[] coordinates = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("coordinate")
                .toString().split(",");
        Double longitude = Double.parseDouble(coordinates[0]);
        Double latitude = Double.parseDouble(coordinates[1]);
        handleClick(target, new LongLat(longitude, latitude, projection));
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        final Map<String, CharSequence> params = new HashMap<String, CharSequence>();
        params.put("callbackUrl", getCallbackUrl());
        params.put("componentId", component.getMarkupId());
        params.put("clickHandlerId", (counter++).toString());
        params.put("projection", projection != null ? projection : "NULL");

        PackageTextTemplate template = new PackageTextTemplate(ClickHandler.class, "ClickHandler.js");
        response.render(OnDomReadyHeaderItem.forScript(template.asString(params)));
    }
}