package org.wicketstuff.jwicket.ui.sortable;

import java.io.Serializable;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.ChildrenFinder;
import org.wicketstuff.jwicket.ComponentFinder;
import org.wicketstuff.jwicket.IStyleResolver;
import org.wicketstuff.jwicket.JQueryCssResourceReference;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;

public class SortableBehavior extends AbstractJqueryUiEmbeddedBehavior implements IStyleResolver {

	private static final long serialVersionUID = 1L;

	private static final String CONNECT_WITH_OPTION = "connectWith";

	public static final JQueryJavascriptResourceReference uiSortableJs = new JQueryJavascriptResourceReference(SortableBehavior.class, "jquery.ui.sortable.min.js");
	public static final JQueryJavascriptResourceReference jWicketSortJs = new JQueryJavascriptResourceReference(SortableBehavior.class, "jWicketSort.js");

	protected JsMap options = new JsMap();

	
	public SortableBehavior() {
		super(
			AbstractJqueryUiEmbeddedBehavior.jQueryUiMouseJs,
			AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
			uiSortableJs,
			jWicketSortJs
		);
		addCssResources(getCssResources());
	}
	
	/**
	 * Handles the event processing during resizing.
	 */
	@Override
	protected void respond(final AjaxRequestTarget target) {
		Component component = getComponent();
		Request request;
		if (component != null && (request = component.getRequest()) != null) {
			EventType eventType = EventType.stringToType(request.getParameter(EventType.IDENTIFIER));

			String draggedItemId = request.getParameter("draggedItemId");

System.out.println("----- respond: eventType = " + eventType);

			// We need the body of the <li> tag, the component inside it
			ChildrenFinder childrenFinder = new ChildrenFinder(draggedItemId);
			component.getPage().visitChildren(childrenFinder);
			if (childrenFinder.getFoundComponents().size() != 1)
				throw new WicketRuntimeException("this should not happen");
			Component sortedComponent = childrenFinder.getFoundComponents().get(0);
System.out.println("\tsortedComponent = " + sortedComponent);
			if (eventType == EventType.STOP) {
				int newPosition = 0;

				try {
					String s = request.getParameter("newPosition");
					newPosition = Integer.parseInt(s);

					if (sortedComponent instanceof ISortable) {
						((ISortable)sortedComponent).onSorted(target, newPosition);
					}

					onSorted(target, sortedComponent, newPosition);
				} catch (Exception e) {
					// don't process 
				}
			}
			else if (eventType == EventType.RECEIVE) {
				String otherSortableId = "";
				int newPosition = 0;

				try {
					String s = request.getParameter("newPosition");
System.out.println("\tnewPositon = " + s);
					newPosition = Integer.parseInt(s);
					otherSortableId = request.getParameter("otherSortableId");

					if (sortedComponent instanceof ISortable) {
						((ISortable)sortedComponent).onReceived(target, newPosition);
					}

					ComponentFinder visitor = new ComponentFinder(otherSortableId);
					component.getPage().visitChildren(visitor);
					Component otherSortable = visitor.getFoundComponent();
					onReceived(target, sortedComponent, newPosition, (Sortable<?>)otherSortable);
				} catch (Exception e) {
					// don't process 
				}
			}
			else if (eventType == EventType.REMOVE) {
				int newPosition = 0;

				try {
					String s = request.getParameter("newPosition");
System.out.println("\tnewPositon = " + s);
					newPosition = Integer.parseInt(s);

					if (sortedComponent instanceof ISortable) {
						((ISortable)sortedComponent).onRemoved(target);
					}

					onRemoved(target, sortedComponent);
				} catch (Exception e) {
					// don't process 
				}
			}

			
			
			
//			otherSortableId
			
			
		}
	}

	
	public void connectWith(final Sortable<?> other) {
		options.put(CONNECT_WITH_OPTION, "#" + other.getMarkupId());
	}

	
	protected void onSorted(final AjaxRequestTarget target, final Component movedComponent, final int newPosition) {}
	
	protected void onReceived(final AjaxRequestTarget target, final Component movedComponent, final int newPosition, final Sortable<?> from) {}

	protected void onRemoved(final AjaxRequestTarget target, final Component movedComponent) {}
	

	@Override
	protected JsBuilder getJsBuilder() {
System.out.println("++++++++++ Sortable.getJsBuilder");
		options.put(EventType.STOP.eventName,
			new JsFunction(
					"function(ev,ui) {" +
					"jQuery.handleSortStop('" + this.getCallbackUrl() + "',ui,'" + getComponent().getMarkupId() + "');" +
					"}"
			)
		);

		options.put(EventType.RECEIVE.eventName,
			new JsFunction(
					"function(ev,ui) {" +
					"jQuery.handleReceive('" + this.getCallbackUrl() + "',ui,'" + getComponent().getMarkupId() + "');" +
					"}"
			)
		);

		options.put(EventType.REMOVE.eventName,
			new JsFunction(
					"function(ev,ui) {" +
					"jQuery.handleRemove('" + this.getCallbackUrl() + "',ui,'" + getComponent().getMarkupId() + "');" +
					"}"
			)
		);


		JsBuilder builder = new JsBuilder();

		builder.append("jQuery('#" + getComponent().getMarkupId() + "').sortable(");
		builder.append("{");
		builder.append(options.toString(rawOptions));
		builder.append("}");
		builder.append(");");

Set<String> keySet = options.keySet();
for (String s : keySet) {
System.out.println("Key: " + s);
}

		
		return builder;
	}
	
	
	private enum EventType implements Serializable {

		UNKNOWN("*"),
		STOP("stop"),
		RECEIVE("receive"),
		REMOVE("remove");

		public static final String IDENTIFIER="jWicketEvent";

		private final String eventName;
		
		private EventType(final String eventName) {
			this.eventName = eventName;
		}
		
		public String getEventName() {
			return this.eventName;
		}
		
		public static EventType stringToType(final String s) {
			for (EventType t : EventType.values())
				if (t.getEventName().equals(s))
					return t;
			return UNKNOWN;
		}
		
		public String toString() {
			return this.eventName;
		}
	}

	
	



	@Override
	public JQueryCssResourceReference[] getCssResources() {
		return new JQueryCssResourceReference[] {
			AbstractJqueryUiEmbeddedBehavior.jQueryUiCss,
			AbstractJqueryUiEmbeddedBehavior.jQueryUiThemeCss
		};
	}


}
