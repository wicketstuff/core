package org.wicketstuff.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;
import org.wicketstuff.scriptaculous.JavascriptBuilder.JavascriptFunction;
import org.wicketstuff.scriptaculous.effect.Effect;


/**
 * Target for drag/drop operations.
 * user can drop a Draggable item onto this component to perform ajax operation.
 * 
 * <p>
 * NOTE: only one DraggableTarget component is allowed for a Page.  This is a limitation
 * of the scriptaculous framework.  Scriptaculous freaks out in IE6/7 when updating 
 * the HTML DOM for a particular Droppable object.  We're able to refresh the current 
 * Droppable, but all other Droppables on the page will be broken.
 * </p>
 *
 * @see http://wiki.script.aculo.us/scriptaculous/show/Droppables.add
 */
public abstract class DraggableTarget extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;
	final ScriptaculousAjaxBehavior onDropBehavior = new DraggableTargetBehavior();
	private final Map<String, Object> dropOptions = new HashMap<String, Object>();

	public DraggableTarget(String id)
	{
		super(id);

		setOutputMarkupId(true);
		add(onDropBehavior);
	}

	/**
	 * extension point for defining functionality when a component is dropped.
	 * @param component the component dropped on the target
	 * @param target response to stream back to the user
	 */
	protected abstract void onDrop(Component component, AjaxRequestTarget target);

	/**
	 * configure the draggable target to accept a component.
	 * The component must have a {@link DraggableBehavior} attached to it.
	 * @param component
	 * @param className css class name to add to the component
	 * 
	 */
	public void accepts(Component component, String className) {
		assertHasDraggableBehavior(component);
		component.add(new AttributeAppender("class", new Model<String>(className), " "));
		addAcceptClass(className);
	}

	private void assertHasDraggableBehavior(Component component) {
		for (Iterator iter = component.getBehaviors().iterator(); iter.hasNext();) {
			IBehavior behavior = (IBehavior) iter.next();
			if (behavior instanceof DraggableBehavior) {
				return;
			}
		}
		throw new IllegalArgumentException("Component must have DraggableBehavior attached: " + component);
	}

	/**
	 * configure the draggable target to accept any draggable item from the {@link SortableListView}
	 * The sortable container needs to override {@link SortableListView#getDraggableClassName()}
	 * in order for the draggable target to know what to accept.
	 * @param container
	 */
	public void acceptAll(SortableListView container) {
		addAcceptClass(container.getDraggableClassName());
	}

	/**
	 * TODO: this should build a string array of classes so that one target
	 * can accept multiple classes.
	 * @param className
	 */
	private void addAcceptClass(String className) {
		dropOptions.put("accept", className);
	}

	/**
	 * set an additional CSS class for when an accepted Draggable is hovered over it.
	 * default is none
	 * @param className
	 */
	public void setHoverClass(String className) {
		dropOptions.put("hoverclass", className);
	}

	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);


		dropOptions.put("onDrop", new JavascriptFunction("function(draggable, droppable, event) { wicketAjaxGet('" + onDropBehavior.getCallbackUrl()
				+ "&id=' + draggable.id); }"));

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("Droppables.add('" + getMarkupId() + "', ");
		builder.addOptions(dropOptions);
		builder.addLine(");");

		getResponse().write(builder.buildScriptTagString());
	}

	private class DraggableTargetBehavior extends ScriptaculousAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(final AjaxRequestTarget target) {
			final String input = getRequest().getParameter("id");
			DraggableTarget draggableTarget = DraggableTarget.this;
			target.prependJavascript("Droppables.remove($(" + draggableTarget.getMarkupId() + "));");
			target.addComponent(draggableTarget);
			target.appendJavascript(new Effect.Highlight(draggableTarget).toJavascript());

			MarkupIdVisitor visitor = new MarkupIdVisitor(input);
			getPage().visitChildren(visitor);
			onDrop(visitor.getFoundComponent(), target);
		}
	}
	
	/**
	 * find a child component by it's markup id
	 * NOTE: the markup id may be different from the wicket id used in the markup.
	 */
	private static class MarkupIdVisitor implements IVisitor<Component> {
		private final String id;
		private Component found;

		public MarkupIdVisitor(String id) {
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
}
