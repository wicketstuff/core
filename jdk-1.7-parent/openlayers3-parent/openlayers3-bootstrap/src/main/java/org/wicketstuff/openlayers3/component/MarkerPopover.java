package org.wicketstuff.openlayers3.component;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.overlay.Overlay;
import org.wicketstuff.openlayers3.api.util.Color;

/**
 * Provides a marker linked to a popover. When the marker is clicked, the popover will have it's content updated and
 * then be displayed.
 */
public class MarkerPopover extends Marker {

    /**
     * Default placement for the popover.
     */
    public final static String DEFAULT_PLACEMENT = "right";

    /**
     * Default positioning of the marker.
     */
    public final static Overlay.Positioning DEFAULT_POSITIONING = Overlay.Positioning.TopCenter;

    /**
     * Popover to use for this marker.
     */
    private PopoverPanel popoverPanel;

    /**
     * Title for the marker's popover.
     */
    private IModel<String> titleModel;

    /**
     * Content for the marker's popover.
     */
    private IModel<String> contentModel;

    /**
     * Location of the popover on the map.
     */
    private IModel<LongLat> positionModel;

    /**
     * Placement of the popover relative to the marker.
     */
    private IModel<String> placementModel;

    /**
     * Creates a new marker that is linked to the provided popover panel.
     *
     * @param id
     *         Wicket element ID
     * @param colorModel
     *         Color model for the marker
     * @param popoverPanel
     *         Popover panel for display detail when marker is clicked
     * @param titleModel
     *         Title model for the popover panel
     * @param contentModel
     *         Content model for the popover panel
     * @param positionModel
     *         Location of the marker on the map
     */
    public MarkerPopover(final String id, IModel<Color> colorModel, final PopoverPanel popoverPanel,
			 final IModel<String> titleModel, final IModel<String> contentModel,
			 final IModel<LongLat> positionModel) {
        this(id, colorModel, popoverPanel, titleModel, contentModel, positionModel, Model.of(DEFAULT_PLACEMENT));
    }

    /**
     * Creates a new marker that is linked to the provided popover panel.
     *
     * @param id
     *         Wicket element ID
     * @param colorModel
     *         Color model for the marker
     * @param popoverPanel
     *         Popover panel for display detail when marker is clicked
     * @param titleModel
     *         Title model for the popover panel
     * @param contentModel
     *         Content model for the popover panel
     * @param positionModel
     *         Location of the marker on the map
     * @param placementModel
     *         Position of the popover relative to the marker
     */
    public MarkerPopover(final String id, IModel<Color> colorModel, final PopoverPanel popoverPanel,
			 final IModel<String> titleModel,  final IModel<String> contentModel,
			 final IModel<LongLat> positionModel, final IModel<String> placementModel) {
        super(id, colorModel);
        this.popoverPanel = popoverPanel;
        this.titleModel = titleModel;
        this.contentModel = contentModel;
        this.positionModel = positionModel;
        this.placementModel = placementModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new AjaxEventBehavior("click") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                popoverPanel.getContentModel().setObject(getContentModel().getObject());
                popoverPanel.getTitleModel().setObject(getTitleModel().getObject());
                popoverPanel.setPosition(positionModel.getObject());
                popoverPanel.setPlacement(getPlacementModel().getObject());
                popoverPanel.setPositioning(DEFAULT_POSITIONING);
                popoverPanel.show(target);
            }
        });
    }

    /**
     * Returns the popover panel for this marker.
     *
     * @return Popover panel instance
     */
    public PopoverPanel getPopoverPanel() {
        return popoverPanel;
    }

    /**
     * Returns the title model used to populate the popover panel.
     *
     * @return Model with title
     */
    public IModel<String> getTitleModel() {
        return titleModel;
    }

    /**
     * Returns the content model used to populate the popover panel.
     *
     * @return Model with content
     */
    public IModel<String> getContentModel() {
        return contentModel;
    }

    /**
     * Returns the model used to position the marker.
     *
     * @return Model with position for the marker
     */
    public IModel<LongLat> getPositionModel() {
        return positionModel;
    }

    /**
     * Returns the model used to position the popover panel relative to the marker.
     *
     * @return Model with the placement of the popover panel relative to the marker
     */
    public IModel<String> getPlacementModel() {
        return placementModel;
    }
}
