package org.wicketstuff.jwicket.demo;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jwicket.SpecialKey;
import org.wicketstuff.jwicket.tooltip.BeautyTips;
import org.wicketstuff.jwicket.ui.dragdrop.DraggablesAcceptedByDroppable;
import org.wicketstuff.jwicket.ui.dragdrop.DroppableBehavior;
import org.wicketstuff.jwicket.ui.dragdrop.IDroppable;
import org.wicketstuff.jwicket.ui.effect.EffectBehavior;



public class DroppableElement extends GenericPanel<String>  {

	private static final long serialVersionUID = 1L;

	protected final Label l;

	private final EffectBehavior effects;
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

		effects = new EffectBehavior();

		

		BeautyTips toolTip = new BeautyTips("You may drag one of the droppable elements on this page into this rectangle.");
		toolTip	.setCssClass("toolTip")
				.setActiveClass("toolTippingComponent")
				.setRawOptions("fill:'rgb(80, 80, 80)'")
				.setCornerRadius(3)
				.setSpikeGirth(4);

		
		
		DroppableBox draggable = new DroppableBox("droppable", model);
		draggable.add(dropper);
		draggable.add(toolTip);

		draggable.add(l = new Label("theModel", model));
		draggable.add(effects);

		l.setOutputMarkupId(true);
		add(draggable);


		setRenderBodyOnly(true);
	}

	
	
	
	private class DroppableBox extends WebMarkupContainer implements IDroppable {

		private boolean dropped = false;

		public DroppableBox(String id, IModel<?> model) {
			super(id, model);
		}

		private static final long serialVersionUID = 1L;

		public void onDrop(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKey... specialKeys) {
			setModelObject("You dropped '" + draggedComponent.getId() + "' into me!");
			target.addComponent(l);
			dropped = true;
			effects.pulsate(target, 2, 250);
		}

		public void onActivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKey... specialKeys) {
			setModelObject("Drop '" + ((draggedComponent==null)?"<null>":draggedComponent.getId()) + "' into me!");
			target.addComponent(l);
			dropped = false;
		}

		public void onDeactivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKey... specialKeys) {
			if (!dropped) {
				setModelObject("Drop it!");
				target.addComponent(l);
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

