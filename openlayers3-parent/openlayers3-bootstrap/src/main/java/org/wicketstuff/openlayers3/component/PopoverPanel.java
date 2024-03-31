package org.wicketstuff.openlayers3.component;

import de.agilecoders.wicket.core.markup.html.bootstrap.components.PopoverBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.overlay.Overlay;
import org.wicketstuff.openlayers3.api.overlay.Popover;

/**
 * Provides Wicket panel attached to a map popover overlay.
 */
public class PopoverPanel extends Panel {

    /**
     * Title model used to populate the popover.
     */
    private final IModel<String> titleModel;

    /**
     * Content model used to populate the popover.
     */
    private final IModel<String> contentModel;

    /**
     * Behavior that implements the Boostrap popover functionality.
     */
    private PopoverBehavior behavior;

    /**
     * Map overlay for the popover.
     */
    private Popover popover;

    /**
     * Creates a new instance.
     *
     * @param id
     *         Wicket element ID for the panel
     */
    public PopoverPanel(final String id) {
        this(id, Model.of(""), Model.of(""));
    }

    /**
     * Creates a new instance.
     *
     * @param id
     *         Wicket element ID for the panel
     * @param titleModel
     *         Model used to populate the title of the popover
     */
    public PopoverPanel(final String id, final IModel<String> titleModel) {
        this(id, titleModel, Model.of(""));
    }

    /**
     * Creates a new instance.
     *
     * @param id
     *         Wicket element ID for the panel
     * @param titleModel
     *         Model used to populate the title of the popover
     * @param contentModel
     *         Model used to populate the content of the popover
     */
    public PopoverPanel(final String id, final IModel<String> titleModel, final IModel<String> contentModel) {
        super(id);

        this.titleModel = titleModel;
        this.contentModel = contentModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(behavior = new PopoverBehavior(titleModel, contentModel));
        popover = new Popover(this, getTitleModel(), getContentModel());
    }

    /**
     * Returns the model used to populate the title of the popover.
     *
     * @return Model with the title
     */
    public IModel<String> getTitleModel() {
        return titleModel;
    }

    /**
     * Returns the model used to populate the content of the popover.
     *
     * @return Model with popover content
     */
    public IModel<String> getContentModel() {
        return contentModel;
    }

    /**
     * Returns the behavior that implements the Bootstrap popover.
     *
     * @return Behavior for the Bootstrap popover
     */
    public PopoverBehavior getBehavior() {
        return behavior;
    }

    /**
     * Returns the map overlay for the popover.
     *
     * @return Map overlay for the popover
     */
    public Popover getPopover() {
        return popover;
    }

    /**
     * Hides the popover from view.
     *
     * @param target
     *         Ajax request target
     */
    public void hide(AjaxRequestTarget target) {
        popover.hide(target);
    }

    /**
     * Shows the popover.
     *
     * @param target
     *         Ajax request target
     */
    public void show(AjaxRequestTarget target) {
        popover.show(target);
    }

    /**
     * Sets the position of the popover.
     *
     * @param position
     *         LongLat with the position
     */
    public void setPosition(LongLat position) {
        popover.setPosition(position);
    }

    /**
     * Sets the placement of the panel relative to the position.
     *
     * @param positioning
     *         Position placement of the panel relative to the position.
     */
    public void setPositioning(Overlay.Positioning positioning) {
        popover.setPositioning(positioning);
    }

    /**
     * Sets the placement of the popover relative to the position.
     *
     * @param placement
     *         Placement of the popover relative to the position
     */
    public void setPlacement(String placement) {
        popover.setPlacement(placement);
    }
}
