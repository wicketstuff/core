package org.wicketstuff.jwicket.demo;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.tooltip.BeautyTips;
import org.wicketstuff.jwicket.ui.dragdrop.DraggablesAcceptedByDroppable;
import org.wicketstuff.jwicket.ui.dragdrop.DroppableBehavior;
import org.wicketstuff.jwicket.ui.dragdrop.IDroppable;
import org.wicketstuff.jwicket.ui.effect.Pulsate;


public class DroppableElement extends GenericPanel<String>  {

	private static final long serialVersionUID = 1L;

	protected final Label l;

	private final Pulsate effects;
	private final DroppableBox droppable;
	private final DroppableBehavior dropper;

	public DroppableElement(final String id) {
		this(id, new Model<String>("Drop it!"), null);
	}

	public DroppableElement(final String id, final DraggablesAcceptedByDroppable accepted) {
		this(id, new Model<String>("Drop it!"), accepted);
	}

	public DroppableElement(final String id, final IModel<String> model,
							final DraggablesAcceptedByDroppable accepted) {
		super(id, model);

		dropper = new DroppableBehavior();
		dropper.setHoverClass("hoverClass");
		dropper.setActiveClass("activeClass");
		dropper.setTolerance(DroppableBehavior.DropTolerance.TOUCH);
		if (accepted != null)
			dropper.setDraggablesAcceptedByDroppable(accepted);

		effects = new Pulsate();
		effects.setTimes(2);
		effects.setSpeed(250);
		add(effects);

		

		BeautyTips toolTip = new BeautyTips("You may drag one of the droppable elements on this page into this rectangle.");
		toolTip	.setCssClass("toolTip")
				.setActiveClass("toolTippingComponent")
				.setRawOptions("fill:'rgb(80, 80, 80)'")
				.setCornerRadius(3)
				.setSpikeGirth(4);

		
		
		droppable = new DroppableBox("droppable", model);
		droppable.add(dropper);
		droppable.add(toolTip);

		droppable.add(l = new Label("theModel", model));
		droppable.setOutputMarkupId(true);

		l.setOutputMarkupId(true);
		add(droppable);

		setRenderBodyOnly(true);
	}

	
	
	
	private class DroppableBox extends WebMarkupContainer implements IDroppable {

		private boolean dropped = false;

		public DroppableBox(String id, IModel<?> model) {
			super(id, model);
		}

		private static final long serialVersionUID = 1L;

		@Override
        public void onDrop(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
			setModelObject("You dropped '" + draggedComponent.getId() + "' into me!");
			target.add(l);
			dropped = true;
			effects.fire(target, droppable);
		}

		@Override
        public void onActivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
			setModelObject("Drop '" + ((draggedComponent==null)?"<null>":draggedComponent.getId()) + "' into me!");
			target.add(l);
			dropped = false;
		}

		@Override
        public void onDeactivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
			if (!dropped) {
				setModelObject("Drop it!");
				target.add(l);
			}
		}
	}


	public void disable(final AjaxRequestTarget target) {
		dropper.disable(target);
	}


	public void enable(final AjaxRequestTarget target) {
		dropper.enable(target);
	}

}

