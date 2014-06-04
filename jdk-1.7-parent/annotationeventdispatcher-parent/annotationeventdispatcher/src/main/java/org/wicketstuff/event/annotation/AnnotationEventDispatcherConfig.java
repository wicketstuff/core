package org.wicketstuff.event.annotation;

public class AnnotationEventDispatcherConfig {

	private boolean dispatchToNonVisibleComponents = false;
	
	private Class<?> eventFilter = null;
	
	public AnnotationEventDispatcherConfig() {
		super();
	}

	public boolean isDispatchToNonVisibleComponents() {
		return dispatchToNonVisibleComponents;
	}

	public void setDispatchToNonVisibleComponents(
			final boolean dispatchToNonVisibleComponents) {
		this.dispatchToNonVisibleComponents = dispatchToNonVisibleComponents;
	}
	
	public AnnotationEventDispatcherConfig dispatchToNonVisibleComponents(
			final boolean dispatchToNonVisibleComponents) {
		setDispatchToNonVisibleComponents(dispatchToNonVisibleComponents);
		return this;
	}

	public Class<?> getEventFilter() {
		return eventFilter;
	}

	public void setEventFilter(final Class<?> eventFilter) {
		this.eventFilter = eventFilter;
	}
	
	public AnnotationEventDispatcherConfig eventFilter(final Class<?> eventFilter) {
		setEventFilter(eventFilter);
		return this;
	}
	
}
