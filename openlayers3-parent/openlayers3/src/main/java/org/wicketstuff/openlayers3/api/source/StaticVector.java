package org.wicketstuff.openlayers3.api.source;

import org.wicketstuff.openlayers3.api.proj.Projection;

/**
 * Provides an object that models a static vector data source.
 */
public abstract class StaticVector extends Source {

    /**
     * Projection used to transform the data.
     */
    private Projection projection;

    /**
     * Creates a new instance.
     *
     * @param projection
     *         Projection used to tranform the data
     */
    public StaticVector(Projection projection) {
        super();

        this.projection = projection;
    }

    /**
     * Returns the projection used to transform the data.
     *
     * @return The projection used to transform the data
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * Sets the projection used to transform the data.
     *
     * @param Projection
     *         New value
     */
    public void setProjection(Projection Projection) {
        this.projection = projection;
    }

    /**
     * Sets the projection used to transform the data.
     *
     * @param projection
     *         New value
     * @return This instance
     */
    public StaticVector projection(Projection projection) {
        setProjection(projection);
        return this;
    }
}
