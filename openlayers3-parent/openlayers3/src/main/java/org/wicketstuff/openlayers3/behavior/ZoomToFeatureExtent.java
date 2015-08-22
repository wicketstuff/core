package org.wicketstuff.openlayers3.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers3.api.layer.Layer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a behavior that zooms to the map to include all features, excluding features loaded from a remote data
 * source. If you need to zoom in order to view all remotely loaded features, please take a look at the
 * VectorLayerFeaturesLoaded listener.
 */
public class ZoomToFeatureExtent extends Behavior {

    private final static Logger logger = LoggerFactory.getLogger(ZoomToFeatureExtent.class);

    /**
     * Buffer to place around the zoomed extent.
     */
    private Number buffer;

    /**
     * List of layers to inspect when calculating the extent.
     */
    private List<Layer> layers;

    /**
     * Creates a new instance.
     */
    public ZoomToFeatureExtent() {
        this.buffer = buffer;
        this.layers = null;
    }

    /**
     * Creates a new instance.
     *
     * @param buffer
     *         Buffer to add when calculating extent
     */
    public ZoomToFeatureExtent(Number buffer) {
        this.buffer = buffer;
        this.layers = null;
    }

    /**
     * Creates a new instance.
     *
     * @param layers
     *         Layers to use when calculating extent
     */
    public ZoomToFeatureExtent(Layer... layers) {
        this(null, Arrays.asList(layers));
    }

    /**
     * Creates a new instance.
     *
     * @param layers
     *         List of layers to use when calculating extent
     */
    public ZoomToFeatureExtent(List<Layer> layers) {
        this(null, layers);
    }

    /**
     * Creates a new instance.
     *
     * @param buffer
     *         Buffer to add when calculating extent
     * @param layers
     *         Layers to use when calculating extent
     */
    public ZoomToFeatureExtent(Number buffer, Layer... layers) {
        this(buffer, Arrays.asList(layers));
    }

    /**
     * Creates a new instance.
     *
     * @param buffer
     *         Buffer to add when calculating extent
     * @param layers
     *         List of layers to use when calculating extent
     */
    public ZoomToFeatureExtent(Number buffer, List layers) {
        this.buffer = buffer;
        this.layers = layers;
    }

    /**
     * Returns the buffer added to the calculated extent.
     *
     * @return Number with the buffer
     */
    public Number getBuffer() {
        return buffer;
    }

    /**
     * Sets the buffer added to the calculated extent.
     *
     * @param buffer
     *         New value
     */
    public void setBuffer(Number buffer) {
        this.buffer = buffer;
    }

    /**
     * Sets the buffer added to the calculated extent.
     *
     * @param buffer
     *         New value
     * @return This instance
     */
    public ZoomToFeatureExtent buffer(Number buffer) {
        this.setBuffer(buffer);
        return this;
    }

    /**
     * Returns the layers used when calculating the extent.
     *
     * @return List of layers
     */
    public List<Layer> getLayers() {
        return layers;
    }

    /**
     * Sets the layers used when calculating the extent.
     *
     * @param layers
     *         List of layers
     */
    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    /**
     * Sets the layers used when calculating the extent.
     *
     * @param layers
     *         List of layers
     * @return This instance
     */
    public ZoomToFeatureExtent layers(List<Layer> layers) {
        setLayers(layers);
        return this;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        final Map<String, CharSequence> params = new HashMap<String, CharSequence>();
        params.put("componentId", component.getMarkupId());
        params.put("buffer", buffer != null ? buffer.toString() : "NULL");

        if (getLayers() != null) {
            StringBuilder layersOut = new StringBuilder();

            for (Layer layer : getLayers()) {

                layersOut.append(layer.getJsId());

                if (layersOut.length() > 0) {
                    layersOut.append(",");
                }
            }

            params.put("layers", layersOut);
        } else {
            params.put("layers", "NULL");
        }

        PackageTextTemplate template = new PackageTextTemplate(ZoomToOverlayExtent.class, "ZoomToFeatureExtent.js");
        response.render(OnDomReadyHeaderItem.forScript(template.asString(params)));
    }
}
