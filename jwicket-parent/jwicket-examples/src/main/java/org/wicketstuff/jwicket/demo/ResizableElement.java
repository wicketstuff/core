package org.wicketstuff.jwicket.demo;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.ui.resizable.IResizable;
import org.wicketstuff.jwicket.ui.resizable.ResizableBehavior;
import org.wicketstuff.jwicket.ui.resizable.ResizableBehavior.ResizableDirections;


public class ResizableElement extends GenericPanel<String>  {

	private static final long serialVersionUID = 1L;

	protected final Label l;
	private final ResizableBehavior resizer;

	public ResizableElement(String id) {
		this(id, new Model<String>("Size me!"));
	}

	public ResizableElement(String id, IModel<String> model) {
		super(id, model);

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
		add(resizable);


		setRenderBodyOnly(true);
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
        public void onResized(AjaxRequestTarget target,
				int top, int left, int width, int height,
				int originalTop, int originalLeft, int originalWidth, int originalHeight, final SpecialKeys specialKeys) {}
	}
	
	


	public void disable(final AjaxRequestTarget target) {
		resizer.disable(target);
	}


	public void enable(final AjaxRequestTarget target) {
		resizer.enable(target);
	}

}

