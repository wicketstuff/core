package org.wicketstuff.jwicket.demo;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.ui.dragdrop.DraggableBehavior;
import org.wicketstuff.jwicket.ui.dragdrop.IDraggable;
import org.wicketstuff.jwicket.ui.resizable.IResizable;
import org.wicketstuff.jwicket.ui.resizable.ResizableBehavior;
import org.wicketstuff.jwicket.ui.resizable.ResizableBehavior.ResizableDirections;


public class DraggableAndResizableElement extends GenericPanel<String>  {

	private static final long serialVersionUID = 1L;

	protected final Label l;
	private final DraggableBehavior dragger;
	private final ResizableBehavior resizer;

	public DraggableAndResizableElement(String id) {
		this(id, new Model<String>("Size me!"));
	}

	public DraggableAndResizableElement(String id, IModel<String> model) {
		super(id, model);

		
		dragger = new DraggableBehavior();
		dragger.setRevert(DraggableBehavior.DragRevertMode.ALWAYS);
		dragger.setDistance(20);
		dragger.setWantOnDragStartNotification(true);
		dragger.setOpacity(0.99);
		dragger.setWantOnDragStopNotification(true);
		dragger.setName("one");


		DraggableBox draggable = new DraggableBox("draggable", model);
		draggable.add(dragger);




		resizer = new ResizableBehavior();
		resizer.setHandles(ResizableDirections.NORTH, ResizableDirections.WEST,
						   ResizableDirections.NORTH_WEST,
						   ResizableDirections.SOUTH_EAST
						);
		resizer.setWantOnResizeNotification(true);


		ResizableBox resizable = new ResizableBox("resizable", model);
		resizable.add(resizer);

		resizable.add(l = new Label("theModel", model));
		l.setOutputMarkupId(true);
		draggable.add(resizable);


		add(draggable);


		setRenderBodyOnly(true);
	}

	
	private class DraggableBox extends WebMarkupContainer implements IDraggable {

		public DraggableBox(String id, IModel<?> model) {
			super(id, model);
		}

		private static final long serialVersionUID = 1L;

		@Override
        public void onDragStop(AjaxRequestTarget target, final SpecialKeys specialKeys) {
			setModelObject("Drag me!");
			target.add(l);
		}

		@Override
        public void onDragStart(AjaxRequestTarget target, final SpecialKeys specialKeys) {
			setModelObject("dragging...");
			target.add(l);
		}

		@Override
        public void onDrag(AjaxRequestTarget target, final SpecialKeys specialKeys) {}
	}
	

	
	private class ResizableBox extends WebMarkupContainer implements IResizable {

		public ResizableBox(String id, IModel<?> model) {
			super(id, model);
		}

		private static final long serialVersionUID = 1L;

		@Override
        public void onResize(AjaxRequestTarget target, int top, int left, int width, int height, final SpecialKeys specialKeys) {
			setModelObject("(" + left + ", " + top + ", " + height + ", " + width + ")");
			target.add(l);
		}

		@Override
        public void onResizeStart(AjaxRequestTarget target, int top, int left, int width, int height, final SpecialKeys specialKeys) {}

		@Override
        public void onResized(AjaxRequestTarget target, int top, int left, int width, int height,
				int originalTop, int originalLeft, int originalWidth, int originalHeight, final SpecialKeys specialKeys) {}
	}
	
	
	
	


	public void disableDraggable(final AjaxRequestTarget target) {
		dragger.disable(target);
	}


	public void enableDraggable(final AjaxRequestTarget target) {
		dragger.enable(target);
	}

	
	


	public void disableResizable(final AjaxRequestTarget target) {
		resizer.disable(target);
	}


	public void enableResizable(final AjaxRequestTarget target) {
		resizer.enable(target);
	}

}

