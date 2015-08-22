package org.wicketstuff.openlayers3.api.style;

/**
 * Provides a style for clusters.
 */
public class ClusterStyle extends Style {

    /**
     * Default minimum radius.
     */
    public final static Integer DEFAULT_MINIMUM_RADIUS = 10;

    /**
     * Default maximum radius.
     */
    public final static Integer DEFAULT_MAXIMUM_RADIUS = 50;

    /**
     * Circle style used when drawing clusters.
     */
    private Circle circle;

    /**
     * Minimum radius for the clusters.
     */
    private Number minimumRadius;

    /**
     * Maximum radius for the clusters.
     */
    private Number maximumRadius;

    /**
     * Creates a new instance.
     *
     * @param circle Circle style used when drawing clusters
     * @param text Text style used when drawing clusters
     */
    public ClusterStyle(Circle circle, Text text)  {
        this(circle, text, DEFAULT_MINIMUM_RADIUS, DEFAULT_MAXIMUM_RADIUS);
    }

    /**
     * Creates a new instance.
     *
     * @param circle Circle style used when drawing clusters
     * @param text Text style used when drawing clusters
     * @param minimumRadius Minimum radius of clusters
     * @param maximumRadius Maximum radius of clusters
     */
    public ClusterStyle(Circle circle, Text text, Number minimumRadius, Number maximumRadius)  {
        super(null, null, null, text, null);

        this.circle = circle;
        this.minimumRadius = minimumRadius;
        this.maximumRadius = maximumRadius;

        circle.setRadius(null);
        text.setText(null);
    }

    @Override
    public ClusterStyle fill(Fill fill) {
        super.fill(fill);
        return this;
    }

    @Override
    public ClusterStyle image(Image image) {
        super.image(image);
        return this;
    }

    @Override
    public ClusterStyle stroke(Stroke stroke) {
        super.stroke(stroke);
        return this;
    }

    @Override
    public ClusterStyle text(Text text) {
        super.text(text);
        return this;
    }

    @Override
    public ClusterStyle zIndex(Number zIndex) {
        super.zIndex(zIndex);
        return this;
    }

    /**
     * Returns the circle style.
     *
     * @return Current value
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Sets the circle style.
     *
     * @param circle New value
     */
    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    /**
     * Sets the circle style.
     *
     * @param circle New value
     * @return This instance
     */
    public ClusterStyle circle(Circle circle) {
        setCircle(circle);
        return this;
    }

    /**
     * Returns the minimum radius.
     *
     * @return Current value
     */
    public Number getMinimumRadius() {
        return minimumRadius;
    }

    /**
     * Sets the minimum radius.
     *
     * @param minimumRadius New value
     */
    public void setMinimumRadius(Number minimumRadius) {
        this.minimumRadius = minimumRadius;
    }

    /**
     * Sets the minimum radius.
     *
     * @param minimumRadius New value
     * @return This instance
     */
    public ClusterStyle minimumRadius(Number minimumRadius) {
        setMinimumRadius(minimumRadius);
        return this;
    }

    /**
     * Returns the maximum radius.
     *
     * @return Current value
     */
    public Number getMaximumRadius() {
        return maximumRadius;
    }

    /**
     * Sets the maximum radius.
     *
     * @param maximumRadius New value
     */
    public void setMaximumRadius(Number maximumRadius) {
        this.maximumRadius = maximumRadius;
    }

    /**
     * Sets the maximum radius.
     *
     * @param maximumRadius New value
     * @return This instance
     */
    public ClusterStyle maximumRadius(Number maximumRadius) {
        setMaximumRadius(maximumRadius);
        return this;
    }

    @Override
    public String getJsType() {
        return "";
    }

    @Override
    public String renderAttributesJs() {

        StringBuilder builder = new StringBuilder();

        builder.append("image: new " + circle.getJsType() + "({");
        builder.append(circle.renderAttributesJs());
        builder.append("'radius': sizeOut,");
        builder.append("}),");

        builder.append("text: new " + getText().getJsType() + "({");
        builder.append(getText().renderAttributesJs());
        builder.append("'text': size.toString(),");
        builder.append("}),");

        return builder.toString();
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("function(features, resolution) {");
        builder.append("  var size = features.get('features').length;");
        builder.append("  var sizeOut = size;");
        builder.append("  if(sizeOut < " + getMinimumRadius() + ") {");
        builder.append("    sizeOut = " + getMinimumRadius() + ";");
        builder.append("  } else if(sizeOut > " + getMaximumRadius() + ") {");
        builder.append("    sizeOut = " + getMaximumRadius() + ";");
        builder.append("  };");

        builder.append("  var style = [");
        builder.append("    new ol.style.Style({");
        builder.append(renderAttributesJs());
        builder.append("  })];");

        builder.append("  return style;");
        builder.append("}");
        return builder.toString();
    }
}
