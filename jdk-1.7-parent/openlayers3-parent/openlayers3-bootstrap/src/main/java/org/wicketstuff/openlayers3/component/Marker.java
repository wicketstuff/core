package org.wicketstuff.openlayers3.component;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.wicketstuff.openlayers3.api.util.Color;

/**
 * Provides a marker that may be placed on a amp.
 */
public class Marker extends GenericPanel<Color> {

    /**
     * Creates a new instance.
     *
     * @param id
     *         Wicket element ID for the marker
     * @param colorModel
     *         Model with the color of the marker
     */
    public Marker(final String id, final IModel<Color> color) {
        super(id, color);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new WebMarkupContainer("pin")
	    .add(new AttributeAppender("style", getBackgroundColorModel())));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(new CssResourceReference(Marker.class,
                "Marker.css")));
    }

    /**
     * Returns a model with the CSS for specifying the marker color.
     *
     * @return Model with the CSS marker color
     */
    private IModel<String> getBackgroundColorModel() {
	return new AbstractReadOnlyModel<String>() {

	    public String getObject() {
		return "background-color: " + getModelObject() + ";";
	    }
	};
    }
}
