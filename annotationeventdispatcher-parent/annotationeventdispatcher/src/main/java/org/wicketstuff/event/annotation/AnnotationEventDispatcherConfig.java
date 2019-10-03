package org.wicketstuff.event.annotation;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;

public class AnnotationEventDispatcherConfig {
	
	private Class<?> eventFilter = null;
	
	private boolean dispatchToNonVisibleComponents = false;

	public AnnotationEventDispatcherConfig() {
		super();
	}

	public boolean isDispatchToNonVisibleComponents() {
		return dispatchToNonVisibleComponents;
	}

	public void setDispatchToNonVisibleComponents(final boolean dispatchToNonVisibleComponents) {
		this.dispatchToNonVisibleComponents = dispatchToNonVisibleComponents;
	}
	
	public AnnotationEventDispatcherConfig dispatchToNonVisibleComponents(final boolean dispatchToNonVisibleComponents) {
		setDispatchToNonVisibleComponents(dispatchToNonVisibleComponents);
		return this;
	}

	public Class<?> getEventFilter() {
		return eventFilter;
	}
	
	public AnnotationEventDispatcherConfig eventFilter(final Class<?> eventFilter) {
		this.eventFilter = eventFilter;
		return this;
	}

	/**
	 * By default dispatching is not allowed when:
	 * <ul>
	 * <li>the event's payload's type is not assignable to {@link #getEventFilter()}</li>
	 * <li>the sink is an invisible or disabled component (i.e. {@link Component#canCallListener()} returns false)</li>
	 * </ul>
	 *  
	 * @param sink the sink to dispatch to
	 * @param event the event to dispatch
	 * @return <code>true</code> if dispatch is allowed
	 */
	public boolean allowDispatch(final Object sink, final IEvent<?> event)
	{
		return (eventFilter == null || eventFilter.isAssignableFrom(event.getPayload().getClass()))
			&& (dispatchToNonVisibleComponents || (sink instanceof Component && ((Component)sink).canCallListener()));
	}
	
	/**
	 * Get the configuration.
	 * 
	 * @param application
	 * @return configuration
	 */
	public static AnnotationEventDispatcherConfig get(Application application) {
		return application.getMetaData(Initializer.ANNOTATION_EVENT_DISPATCHER_CONFIG_CONTEXT_KEY);
	}
}
