package org.wicketstuff.openlayers3.api.style;

/**
 * Provides a class that models a circle style.
 */
public class Circle extends Style {

    /**
     * The radius of the circle.
     */
    private Number radius;

    /**
     * If true integral numbers of pixels are used as the X and Y pixel coordinate when drawing the circle in the
     * output canvas. If false fractional numbers may be used. Using true allows for "sharp" rendering (no blur),
     * while using false allows for "accurate" rendering. Note that accuracy is important if the circle's position
     * is animated. Without it, the circle may jitter noticeably. Default value is true.
     */
    private boolean snapToPixel;

    /**
     * The stroke used when drawing the circle.
     */
    private Stroke stroke;

    /**
     * Creates a new instance.
     *
     * @param fill The fill for the circle
     * @param radius The radius of the circle
     * @param stroke The stroke for the circle
     */
    public Circle(Fill fill, Number radius, Stroke stroke) {
        this(fill, radius, true, stroke);
    }

    /**
     * Creates a new instance.
     *
     * @param fill The fill for the circle
     * @param radius The radius of the circle
     * @param snapToPixel Indicates if pixels or fractional values are used
     * @param stroke The stroke for the circle
     */
    public Circle(Fill fill, Number radius, boolean snapToPixel, Stroke stroke) {
        super(fill);
        this.radius = radius;
        this.snapToPixel = snapToPixel;
        this.stroke = stroke;
    }

    @Override
    public Circle fill(Fill fill) {
        super.fill(fill);
        return this;
    }

    @Override
    public Circle image(Image image) {
        super.image(image);
        return this;
    }

    @Override
    public Circle stroke(Stroke stroke) {
        super.stroke(stroke);
        return this;
    }

    @Override
    public Circle text(Text text) {
        super.text(text);
        return this;
    }

    @Override
    public Style zIndex(Number zIndex) {
        super.zIndex(zIndex);
        return this;
    }

    /**
     * Returns the radius of the circle.
     *
     * @return Current value
     */
    public Number getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the circle.
     *
     * @param radius New value
     */
    public void setRadius(Number radius) {
        this.radius = radius;
    }

    /**
     * Sets the radius of the circle.
     *
     * @param radius New value
     * @return This instance
     */
    public Circle radius(Number radius) {
        setRadius(radius);
        return this;
    }

    /**
     * Returns the snap to pixel value.
     *
     * @return Current value
     */
    public boolean isSnapToPixel() {
        return snapToPixel;
    }

    /**
     * Sets the snap to pixel value.
     *
     * @param snapToPixel New value
     */
    public void setSnapToPixel(boolean snapToPixel) {
        this.snapToPixel = snapToPixel;
    }

    /**
     * Sets the snap to pixel value.
     *
     * @param snapToPixel New value
     * @return This instace
     */
    public Circle snapToPixel(boolean snapToPixel) {
        setSnapToPixel(snapToPixel);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.style.Circle";
    }

    @Override
    public String renderAttributesJs() {

        StringBuilder builder = new StringBuilder();

        builder.append(super.renderAttributesJs());

        if (getRadius() != null) {
            builder.append("'radius': " + getRadius() + ",");
        }

        if (isSnapToPixel()) {
            builder.append("'snapToPixel': " + isSnapToPixel() + ",");
        }

        return builder.toString();
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(renderAttributesJs());
        builder.append("}");
        return builder.toString();
    }
}
