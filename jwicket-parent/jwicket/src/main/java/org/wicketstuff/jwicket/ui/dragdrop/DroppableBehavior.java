package org.wicketstuff.jwicket.ui.dragdrop;


import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Request;
import org.apache.wicket.Component.IVisitor;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;


/**
 * You can add an instance of this class to a Wicket {@link Component} to make it
 * a droppable {@link Component} i.e. the target of a drag operation.
 * An instance of this class can be added to one and only one
 * {@link Component}. Another {@link Component} that should have exactly the
 * same behavior needs it's own instance.
 */
public class DroppableBehavior extends AbstractDragDropBehavior {

	private static final long serialVersionUID = 1L;

	private static final String DROPPED_COMPONENTID_IDENTIFIER = "wsjqDroppedComponent";

	private JsMap options = new JsMap();

	private DraggablesAcceptedByDroppable draggablesAcceptedByDroppable = null;

	
	public DroppableBehavior() {
		super(new JavascriptResourceReference(DraggableBehavior.class, "ui.droppable.js"));
	}


	private boolean onActivatedNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onActivate(AjaxRequestTarget, Component)} 
	 * is called when the drag operation ends.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DroppableBehavior setWantOnActivatedNotification(final boolean value) {
		onActivatedNotificationWanted = value;
		return this;
	}


	private boolean onDeactivateNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onDeactivate(AjaxRequestTarget, Component)} 
	 * is called when the drag operation ends.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DroppableBehavior setWantOnDeactivateNotification(final boolean value) {
		onDeactivateNotificationWanted = value;
		return this;
	}

	/** You can restrict the {@link Component}s that can be dragged and dropped to
	 * a {@link Component} marked with this behavior. See {@link DraggablesAcceptedByDroppable}
	 * for more information. If the {@link DraggablesAcceptedByDroppable} is empty (i.e. it
	 * contains no names) then the droppable does not accept any draggables.
	 *
	 * @param accepted the accepted 
	 * @return this object
	 */
	public DroppableBehavior setDraggablesAcceptedByDroppable(final DraggablesAcceptedByDroppable accepted) {
		draggablesAcceptedByDroppable = accepted;
		if (accepted != null)
			options.put("accept", new JsFunction(accepted.getJsAcceptCheckerFunctionName()));
		else
			options.remove("accept");
		return this;
	}

	
	/**
	 * Sets the 'activeClass' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param activeClass the CSS class' name
	 * @return this object
	 */
	public DroppableBehavior setActiveClass(final String activeClass) {
		if (activeClass == null)
			options.remove("activeClass");
		else
			options.put("activeClass", activeClass);
		return this;
	}
	public DroppableBehavior setActiveClass(final AjaxRequestTarget target, final String activeClass) {
		setActiveClass(activeClass);
		if (activeClass != null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','activeClass','" + activeClass + "');");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','activeClass',false);");
		return this;
	}


	/**
	 * Sets the 'addClasses' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DroppableBehavior setAddClasses(final boolean value) {
		if (value)
			options.remove("addClasses");
		else
			options.put("addClasses", value);
		return this;
	}
	public DroppableBehavior setAddClasses(final AjaxRequestTarget target, final boolean value) {
		setAddClasses(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','addClasses'," + value + ");");
		return this;
	}


	/**
	 * Sets the 'greedy' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DroppableBehavior setGreedy(final boolean value) {
		if (value)
			options.remove("greedy");
		else
			options.put("greedy", value);
		return this;
	}
	public DroppableBehavior setGreedy(final AjaxRequestTarget target, final boolean value) {
		setGreedy(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','greedy'," + value + ");");
		return this;
	}

	
	/**
	 * Sets the 'hoverClass' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param hoverClass the CSS class' name
	 * @return this object
	 */
	public DroppableBehavior setHoverClass(final String hoverClass) {
		if (hoverClass == null)
			options.remove("hoverClass");
		else
			options.put("hoverClass", hoverClass);
		return this;
	}
	public DroppableBehavior setHoverClass(final AjaxRequestTarget target, final String hoverClass) {
		setHoverClass(hoverClass);
		if (hoverClass != null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','hoverClass','" + hoverClass + "');");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','hoverClass',false);");
		return this;
	}

	
	/**
	 * Sets the 'scope' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param scope the scode
	 * @return this object
	 */
	public DroppableBehavior setScope(final String scope) {
		if (scope == null)
			options.remove("scope");
		else
			options.put("scope", scope);
		return this;
	}
	public DroppableBehavior setScope(final AjaxRequestTarget target, final String scope) {
		setScope(scope);
		if (scope != null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','scope','" + scope + "');");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','scope','default');");
		return this;
	}


	public static enum DropTolerance {

		FTI("fit"),
		INTERSECT("intersect"),
		POINTER("pointer"),
		TOUCH("touch")
		;

		private final String value;
		private DropTolerance(final String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String toString() {
			return this.value;
		}
	}


	/**
	 * Sets the 'tolerance' property for this droppable. Please consult the
	 * jquery documentation for a detailled description of this peroperty.
	 * @param tolerance the tolerance
	 * @return this object
	 */
	public DroppableBehavior setTolerance(final DropTolerance tolerance) {
		if (tolerance == null)
			options.remove("tolerance");
		else
			options.put("tolerance", tolerance.getValue());
		return this;
	}
	public DroppableBehavior setScope(final AjaxRequestTarget target, final DropTolerance tolerance) {
		setTolerance(tolerance);
		if (tolerance != null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','tolerance','" + tolerance.getValue() + "');");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','tolerance','" + DropTolerance.INTERSECT + "');");
		return this;
	}


	/**
	 * handles the event processing during dragging.
	 */
	@Override
	protected void respond(final AjaxRequestTarget target) {
System.out.println("---------- respond");
		Component component = getComponent();
		Request request;

		if (component != null && (request = component.getRequest()) != null) {
			EventType dragEventType = EventType.stringToType(request.getParameter(EventType.IDENTIFIER));
System.out.println("EventType = " + dragEventType);

			String droppedId = request.getParameter(DROPPED_COMPONENTID_IDENTIFIER);
			
			ComponentFinder visitor = new ComponentFinder(droppedId);
			component.getPage().visitChildren(visitor);


			if (component instanceof IDroppable) {
				IDroppable draggableComponent = (IDroppable)component;
				if (dragEventType == EventType.DROP)
					draggableComponent.onDrop(target, visitor.getFoundComponent());
				else if (dragEventType == EventType.DROP_ACTIVATE)
					draggableComponent.onActivate(target, visitor.getFoundComponent());
				else if (dragEventType == EventType.DROP_DEACTIVATE)
					draggableComponent.onDeactivate(target, visitor.getFoundComponent());
			}


			if (dragEventType == EventType.DROP)
				onDrop(target, visitor.getFoundComponent());
			else if (dragEventType == EventType.DROP_ACTIVATE)
				onActivate(target, visitor.getFoundComponent());
			else if (dragEventType == EventType.DROP_DEACTIVATE)
				onDeactivate(target, visitor.getFoundComponent());
		}
	}


	/**
	 * This method is called when a draggable {@link Component} is dropped onto
	 * a {@link Component} marked with this behavior.
	 *
	 * @param target the AjaxRequestTarget of the drop operation.
	 */
	protected void onDrop(AjaxRequestTarget target, final Component draggedComponent) {}

	
	/**
	 * This method is called when a draggable {@link Component} is starting to
	 * drag and the dragging {@link Component}'s name is accepted to be
	 * dropped onto this.
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 * drop operation.
	 */
	protected void onActivate(final AjaxRequestTarget target, final Component draggedComponent) {}

	/**
	 * This method is called when a draggable {@link Component} has stopped
	 * dragging and the dragging {@link Component}'s name was accepted to be
	 * dropped onto this.
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 * drop operation.
	 */
	protected void onDeactivate(final AjaxRequestTarget target, final Component draggedComponent) {}


	/**
	 * Find a page's child component by it's markup id
	 */
	private static class ComponentFinder implements IVisitor<Component>, Serializable {
		private static final long serialVersionUID = 1L;
		private final String id;
		private Component found;

		public ComponentFinder(String id) {
			this.id = id;
		}

		public Object component(Component component) {
			if (component.getMarkupId().equals(id)) {
				this.found = component;
				return IVisitor.STOP_TRAVERSAL;
			}
			if (component instanceof MarkupContainer) {
				return ((MarkupContainer)component).visitChildren(this);
			}
			return IVisitor.CONTINUE_TRAVERSAL;
		}

		public Component getFoundComponent() {
			return found;
		}
	}


	/**
	 * Disable the dropping
	 *
	 * @param target An AjaxRequestTarget
	 */
	public void disable(final AjaxRequestTarget target) {
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('disable');");
	}


	/**
	 * Enable the dropping
	 *
	 * @param target An AjaxRequestTarget
	 */
	public void enable(final AjaxRequestTarget target) {
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').droppable('enable');");
	}



	/**
	 * @see org.apache.wicket.behavior.AbstractAjaxBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		if (draggablesAcceptedByDroppable != null)
			draggablesAcceptedByDroppable.renderJsDropAcceptFunction(response);
	}



	@Override
	/**
	 * For internal use only.
	 */
	protected JsBuilder getJsBuilder() {
		if (onDeactivateNotificationWanted)
			options.put("deactivate",
				new JsFunction("function(ev,ui) { \n"+
								"wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&" + EventType.IDENTIFIER + "=" + EventType.DROP_DEACTIVATE +
								"&" + DROPPED_COMPONENTID_IDENTIFIER + "='+jQuery(ui.draggable).attr('id')" + 
								"); }"));

		options.put("drop",
				new JsFunction("function(ev,ui) { \n"+
								"wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&" + EventType.IDENTIFIER + "=" + EventType.DROP +
								"&" + DROPPED_COMPONENTID_IDENTIFIER + "='+jQuery(ui.draggable).attr('id')" + 
								"); }"));

		if (onActivatedNotificationWanted)
			options.put("activate",
				new JsFunction("function(ev,ui) { \n"+
								"wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&" + EventType.IDENTIFIER + "=" + EventType.DROP_ACTIVATE +
								"&" + DROPPED_COMPONENTID_IDENTIFIER + "='+jQuery(ui.draggable).attr('id')" + 
								"); }"));

		JsBuilder builder = new JsBuilder();


		builder.append("jQuery(function(){");

		builder.append("jQuery('#" + getComponent().getMarkupId() + "').droppable(");
		builder.append("{");
		builder.append(options.toString(rawOptions));
		builder.append("}");
		builder.append(");");

		builder.append("});");


		return builder;
	}
	
	
}
