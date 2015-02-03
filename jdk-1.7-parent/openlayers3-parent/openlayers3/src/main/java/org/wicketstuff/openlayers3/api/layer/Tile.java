package org.wicketstuff.openlayers3.api.layer;

import org.wicketstuff.openlayers3.api.source.Source;

/**
 * Provides an object that models a tile layer.
 */
public class Tile extends Layer {

    /**
     * The title for this layer.
     */
    private String title;

    /**
     * Creates a new instance.
     *
     * @param source
     *         The source of data for this layer
     */
    public Tile(Source source) {
        this(null, source);
    }

    /**
     * Creates a new instance.
     *
     * @param title
     *         The title for the layer
     * @param source
     *         The source of data for this layer
     */
    public Tile(String title, Source source) {
        super();

        this.title = title;
        setSource(source);
    }

    /**
     * Returns the title for this layer.
     *
     * @return Title for this layer
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title for this layer.
     *
     * @param title
     *         New value
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the title for this layer.
     *
     * @param title
     *         New value
     * @return This instance
     */
    public Tile title(String title) {
        setTitle(title);
        return this;
    }

    /**
     * Sets the source for this layer.
     *
     * @param source
     *         New value
     * @return this instance
     */
    @Override
    public Tile source(Source source) {
        setSource(source);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.layer.Tile";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();

        builder.append("{");

        if (title != null) {
            builder.append("'title': '" + getTitle() + "',");
        }

        builder.append("'source': new " + getSource().getJsType() + "(");
        builder.append(getSource().renderJs());
        builder.append(")");
        builder.append("}");

        return builder.toString();
    }
}
