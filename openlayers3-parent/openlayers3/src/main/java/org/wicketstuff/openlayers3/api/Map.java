package org.wicketstuff.openlayers3.api;

import org.wicketstuff.openlayers3.api.interaction.Interaction;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.overlay.Overlay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides an object that models an OpenLayer Map instance.
 */
public class Map extends JavascriptObject implements Serializable {

    /**
     * The renderer for this map.
     */
    private RenderType renderer;
    /**
     * The layers for this map.
     */
    private List<Layer> layers = new ArrayList<Layer>();
    /**
     * Overlays for this map.
     */
    private List<Overlay> overlays = new ArrayList<Overlay>();
    /**
     * The view for this map.
     */
    private View view;
    /**
     * The target Wicket element for this map.
     */
    private String target;
    /**
     * List of interactions for this map
     */
    private List<Interaction> interactions = new ArrayList<Interaction>();

    /**
     * Creates a new instance.
     *
     * @param layers
     *         Layers for this map
     */
    public Map(List<Layer> layers) {
        this(null, layers, null);
    }

    /**
     * Creates a new instance.
     *
     * @param renderer
     *         The renderer for this map
     * @param layers
     *         Layers for this map
     */
    public Map(RenderType renderer, List<Layer> layers) {
        this(renderer, layers, null);
    }

    /**
     * Creates a new instance.
     *
     * @param layers
     *         The layers for this map
     * @param view
     *         The view for this map
     */
    public Map(List<Layer> layers, View view) {
        this(null, layers, null, view);
    }

    /**
     * Creates a new instance.
     *
     * @param renderer
     *         The renderer for this map
     * @param layers
     *         The layers for this map
     * @param view
     *         The view for this map
     */
    public Map(RenderType renderer, List<Layer> layers, View view) {
        this(renderer, layers, null, view);
    }

    /**
     * Creates a new instance.
     *
     * @param layers
     *         The layers for this map
     * @param overlays
     *         The overlays for this map
     * @param view
     *         The view for this map
     */
    public Map(List<Layer> layers, List<Overlay> overlays, View view) {
        this(null, layers, overlays, view);
    }

    /**
     * Creates a new instance.
     *
     * @param layers
     *         The layers for this map
     * @param overlays
     *         The overlays for this map
     */
    public Map(List<Layer> layers, List<Overlay> overlays) {
        this(null, layers, overlays, null);
    }

    /**
     * Creates a new instance.
     *
     * @param renderer
     *         The renderer for this map
     * @param layers
     *         The layers for this map
     * @param overlays
     *         The overlays for this map
     * @param view
     *         The view for this map
     */
    public Map(RenderType renderer, List<Layer> layers, List<Overlay> overlays, View view) {
        this(renderer, layers, overlays, view, null);
    }

    /**
     * Creates a new instance.
     *
     * @param renderer
     *         The renderer for this map
     * @param layers
     *         The layers for this map
     * @param overlays
     *         The overlays for this map
     * @param view
     *         The view for this map
     * @param interactions
     *         Interactions for the map
     */
    public Map(RenderType renderer, List<Layer> layers, List<Overlay> overlays, View view,
               List<Interaction> interactions) {
		super();

        this.renderer = renderer;
        this.view = view;

        if (layers != null) {
            this.layers.addAll(layers);
        }

        if (overlays != null) {
            this.overlays.addAll(overlays);
        }

        if (interactions != null) {
            this.interactions.addAll(interactions);
        }
    }

    /**
     * Returns the target element ID to which this map is tied.
     *
     * @return Wicket element ID
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the target Wicket element for this map. Normally this is called by Wicket during configuration time.
     *
     * @param target
     *         Target Wicket element ID
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Returns the renderer for this map.
     *
     * @return Renderer
     */
    public RenderType getRenderer() {
        return renderer;
    }

    /**
     * Sets the renderer for this map.
     *
     * @param renderer
     *         Renderer
     */
    public void setRenderer(RenderType renderer) {
        this.renderer = renderer;
    }

    /**
     * Returns the layers for this map.
     *
     * @return List of layers
     */
    public List<Layer> getLayers() {
        return layers;
    }

    /**
     * Sets the layers for this map.
     *
     * @param layers
     *         List of layers
     */
    public void setLayers(List<Layer> layers) {

        this.layers = new ArrayList<Layer>();

        if (layers != null) {
            this.layers.addAll(layers);
        }
    }

    /**
     * Returns the overlays for this map.
     *
     * @return List of overlays
     */
    public List<Overlay> getOverlays() {
        return overlays;
    }

    /**
     * Sets the overlays for this map.
     *
     * @param overlays
     *         List of overlays
     */
    public void setOverlays(List<Overlay> overlays) {

        this.overlays = new ArrayList<Overlay>();

        if (overlays != null) {
            this.overlays.addAll(overlays);
        }
    }

    /**
     * Returns the view for this map.
     *
     * @return View
     */
    public View getView() {
        return view;
    }

    /**
     * Sets the view for this map.
     *
     * @param view
     *         View for this map
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Sets the view for this map.
     *
     * @param view
     *         View for this map
     * @return This instance
     */
    public Map view(View view) {
        setView(view);
        return this;
    }

    /**
     * Sets the overlays for this map.
     *
     * @param overlays
     *         List of overlays
     * @return This instance
     */
    public Map overlays(List<Overlay> overlays) {
        setOverlays(overlays);
        return this;
    }

    /**
     * Sets the renderer for this map.
     *
     * @param renderer
     *         Render for this map
     * @return This instance
     */
    public Map renderer(RenderType renderer) {
        setRenderer(renderer);
        return this;
    }

    /**
     * Sets the layers for this map.
     *
     * @param layers
     *         List of layers
     * @return This instance
     */
    public Map Layers(List<Layer> layers) {
        setLayers(layers);
        return this;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {

        this.interactions = new ArrayList<Interaction>();

        if (interactions != null) {
            this.interactions.addAll(interactions);
        }
    }

    public Map interactions(List<Interaction> interactions) {
        setInteractions(interactions);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.Map";
    }

    @Override
    public String getJsId() {
        return  JS_GLOBAL + "['map_" + getTarget() + "']";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("'target': \'" + getTarget() + "\',");

        if (getRenderer() != null) {
            builder.append("'renderer': \'" + getRenderer().toString() + "',");
        }

        if (getInteractions() != null && getInteractions().size() > 0) {
            builder.append("'interactions': ol.interaction.defaults().extend([");

            for (Interaction interaction : getInteractions()) {
                builder.append(interaction.getJsId() + " = new " + interaction.getJsType() + "(");
                builder.append(interaction.renderJs());
                builder.append("),");
            }

            builder.append("]),");
        }

        if (getLayers() != null && getLayers().size() > 0) {
            builder.append("'layers': [");

            for (Layer layer : getLayers()) {
                builder.append(layer.getJsId() + ",");
            }

            builder.append("],");
        }

        if (getOverlays() != null && getOverlays().size() > 0) {
            builder.append("'overlays': [");

            for (Overlay overlay : getOverlays()) {
                builder.append(overlay.getJsId() + " = new " + overlay.getJsType() + "(");
                builder.append(overlay.renderJs());
                builder.append("),");
            }

            builder.append("],");
        }

        if (getView() != null) {
            builder.append("'view': ");
            builder.append("new " + getView().getJsType() + "(");
            builder.append(getView().renderJs());
            builder.append("),");
        }

        builder.append("}");

        return builder.toString();
    }

    /**
     * Provides an enumeration of the valid render types.
     */
    public enum RenderType {

        Canvas("canvas"), Dom("dom"), WebGl("webgl");

        private String type;

        RenderType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }
}
