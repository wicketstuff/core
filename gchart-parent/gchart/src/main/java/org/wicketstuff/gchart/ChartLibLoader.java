/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart;

import org.apache.wicket.markup.head.HeaderItem;

/**
 * Interface for implementation of central lib and package loading mechanism.
 * The implementation should load the Google chart lib only once and load every
 * package needed by all charts added. If a Maps API key is set in one of the charts,
 * it is also added.
 *
 * @author Dieter Tremel
 */
public interface ChartLibLoader {

    /**
     * Add a chart to the loader, so it's package is loaded to.
     *
     * @param chart Chart to add.
     * @return True if Chart is added.
     */
    public boolean addChart(Chart chart);

    /**
     * Remove a previous added chart.
     *
     * @param chart Chart to remove.
     * @return True if the Chart was managed and is removed now, false otherwise.
     */
    public boolean removeChart(Chart chart);

    /**
     * Get the HeaderItem the loader adds, only to make dependencies to.
     *
     * @return HeaderItem that the loader contributes.
     */
    public HeaderItem getHeaderItem();
}
