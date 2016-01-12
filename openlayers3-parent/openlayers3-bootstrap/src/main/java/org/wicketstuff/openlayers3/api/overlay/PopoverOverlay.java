package org.wicketstuff.openlayers3.api.overlay;

import org.wicketstuff.openlayers3.component.MarkerPopover;

/**
 * Provides an object that models a map overlay containing a popover.
 */
public class PopoverOverlay extends Overlay {

    /**
     * Creates a new instance.
     *
     * @param markerPopover
     *         The marker and popover linked to this overlay
     */
    public PopoverOverlay(MarkerPopover markerPopover) {
        super(markerPopover, markerPopover.getPositionModel().getObject(), DEFAULT_POSITIONING, DEFAULT_STOP_EVENT);
    }

    @Override
    public String getJsId() {
        return "popover_overlay_" + element.getMarkupId();
    }
}
