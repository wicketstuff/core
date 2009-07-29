package org.wicketstuff.jwicket.demo;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jwicket.CssCursor;
import org.wicketstuff.jwicket.CssPosition;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.tooltip.WalterZornTips;
import org.wicketstuff.jwicket.ui.dragdrop.DraggableBehavior;
import org.wicketstuff.jwicket.ui.dragdrop.IDraggable;
import org.wicketstuff.jwicket.ui.effect.EffectBehavior;


public class DraggableElement extends GenericPanel<String>  {

	private static final long serialVersionUID = 1L;

	private final DraggableLink draggable;
	private final Label l;
	
	private final EffectBehavior effects;
	private final DraggableBehavior dragger;

	public DraggableElement(String id) {
		this(id, new Model<String>("Drag me!"));
	}

	public DraggableElement(final String id, final IModel<String> model) {
		super(id, model);

System.out.println("DraggableElement: model = " + model + "/" + model.getObject());

		dragger = new DraggableBehavior();
		dragger.setRevert(DraggableBehavior.DragRevertMode.ALWAYS);
		dragger.setDistance(20);
		dragger.setWantOnDragStartNotification(true);
		dragger.setOpacity(0.99);
		dragger.setWantOnDragStopNotification(true);
		dragger.setName("one");

dragger
.setCursor(CssCursor.MOVE)
.setCursorAt(CssPosition.BOTTOM, 125, CssPosition.RIGHT, 200)
.setGrid(20, 20)
.setOpacity(10.50)
.setSnap(true)
.setScrollSpeed(200)

;

		effects = new EffectBehavior();


		WalterZornTips tooltip = new WalterZornTips("This rectangle is draggable!")
			.setShadow(true)
		;


		draggable = new DraggableLink("draggableLink", model);
		draggable.add(tooltip);
		draggable.add(dragger);
		draggable.add(effects);

		draggable.add(l = new Label("theModel", model));
		l.setOutputMarkupId(true);


		addOrReplace(draggable);


		//setRenderBodyOnly(true);
	}

	
	
	
	private class DraggableLink extends AjaxFallbackLink<String> implements IDraggable {
		private static final long serialVersionUID = 1L;

		private boolean ignoreClick = false;

		public DraggableLink(String id, IModel<String> model) {
			super(id, model);
			System.out.println("DraggableLink: model = " + model);
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			if (!ignoreClick) {
				effects	//.pulsate(target, 5, 250)
						.puff(target, 70, 1200)
						//.explode(target, 25, 1500)
				;
			}
		}

		public void onDragStop(final AjaxRequestTarget target, final SpecialKeys specialKeys) {
			setModelObject("Drag me!");
			target.addComponent(l);
			ignoreClick = false;
			effects.explode(target, 25, 1500);
		}

		public void onDragStart(final AjaxRequestTarget target, final SpecialKeys specialKeys) {
			setModelObject("dragging...");
			target.addComponent(l);
			ignoreClick = true;
		}

		public void onDrag(final AjaxRequestTarget target, final SpecialKeys specialKeys) {}
	}


	public void disable(final AjaxRequestTarget target) {
		dragger.disable(target);
	}


	public void enable(final AjaxRequestTarget target) {
		dragger.enable(target);
	}
	
	
	
	
	
	
	
	
	
	public void test(final AjaxRequestTarget target) {
		target.addComponent(draggable);
		dragger.setCursor(target, CssCursor.MOVE);
		dragger.setCursorAt(target, CssPosition.BOTTOM, 125, CssPosition.RIGHT, 200);
		dragger.setGrid(target, 150, 150);
		dragger.setOpacity(target, 0.3);
		dragger.setRevert(target, DraggableBehavior.DragRevertMode.INVALID);
		dragger.setSnap(target, true);
				

		;
		dragger.updateBehavior(target);
	}
}

