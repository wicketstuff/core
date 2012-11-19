package org.wicketstuff.jwicket.demo;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.tooltip.WTooltip;
import org.wicketstuff.jwicket.ui.dragdrop.*;
import org.wicketstuff.jwicket.ui.effect.Pulsate;


public class DraggableAndDroppableElement extends GenericPanel<String> {

    private static final long serialVersionUID = 1L;

    protected final Label l;
    private DraggableAndDroppableBox draggable;
    private final DraggableBehavior dragger;
    private final DroppableBehavior dropper;

    private final Pulsate effect;

    public DraggableAndDroppableElement(final String id) {
        this(id, new Model<String>("Drag me!"), null);
    }

    public DraggableAndDroppableElement(final String id, final IModel<String> model,
                                        final DraggablesAcceptedByDroppable accepted) {
        super(id, model);

        dragger = new DraggableBehavior();
        dragger.setRevert(DraggableBehavior.DragRevertMode.ALWAYS);
        dragger.setDistance(20);
        dragger.setWantOnDragStartNotification(true);
        dragger.setOpacity(0.99);
        dragger.setWantOnDragStopNotification(true);
        dragger.setName("one");


        dropper = new DroppableBehavior();
        dropper.setHoverClass("hoverClass");
        dropper.setActiveClass("activeClass");
        dropper.setTolerance(DroppableBehavior.DropTolerance.TOUCH);
        if (accepted != null)
            dropper.setDraggablesAcceptedByDroppable(accepted);

        effect = new Pulsate();
        effect.setTimes(2);
        effect.setSpeed(250);
        add(effect);


        WTooltip toolTip = new WTooltip("You may drag one of the droppable elements on this page into this rectangle. Otherwise you can drag this rectangle itself and drop it somewhere.").setCssClass("toolTip");


        draggable = new DraggableAndDroppableBox("draggableAndDroppable", model);
        draggable.add(toolTip);
        draggable.add(dragger);
        draggable.add(dropper);

        draggable.add(l = new Label("theModel", model));
        l.setOutputMarkupId(true);
        add(draggable);


        setRenderBodyOnly(true);
    }


    private class DraggableAndDroppableBox extends WebMarkupContainer implements IDraggable, IDroppable {

        private boolean dropped = false;

        public DraggableAndDroppableBox(String id, IModel<?> model) {
            super(id, model);
        }

        private static final long serialVersionUID = 1L;

        @Override
        public void onDragStop(final AjaxRequestTarget target, final SpecialKeys specialKeys) {
            setModelObject("Drag me!");
            target.add(l);
        }

        @Override
        public void onDragStart(final AjaxRequestTarget target, final SpecialKeys specialKeys) {
            setModelObject("dragging...");
            target.add(l);
        }

        @Override
        public void onDrag(final AjaxRequestTarget target, final SpecialKeys specialKeys) {
        }

        @Override
        public void onDrop(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
            setModelObject("You dropped '" + draggedComponent.getId() + "' into me!");
            target.add(l);
            dropped = true;
            effect.fire(target, draggable);
        }

        @Override
        public void onActivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
            setModelObject("Drop '" + ((draggedComponent == null) ? "<null>" : draggedComponent.getId()) + "' into me!");
            target.add(l);
            dropped = false;
        }

        @Override
        public void onDeactivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
            if (!dropped) {
                setModelObject("Drag me!");
                target.add(l);
            }
        }
    }

    public void disableDraggable(final AjaxRequestTarget target) {
        dragger.disable(target);
    }

    public void enableDraggable(final AjaxRequestTarget target) {
        dragger.enable(target);
    }

    public void disableDroppable(final AjaxRequestTarget target) {
        dropper.disable(target);
    }

    public void enableDroppable(final AjaxRequestTarget target) {
        dropper.enable(target);
    }
}

