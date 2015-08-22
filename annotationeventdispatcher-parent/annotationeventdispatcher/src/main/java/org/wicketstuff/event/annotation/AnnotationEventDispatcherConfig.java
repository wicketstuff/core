package org.wicketstuff.event.annotation;

public class AnnotationEventDispatcherConfig {
	
	private Class<?> eventFilter = null;
	
	public AnnotationEventDispatcherConfig() {
		super();
	}

	public Class<?> getEventFilter() {
		return eventFilter;
	}
	
	public AnnotationEventDispatcherConfig eventFilter(final Class<?> eventFilter) {
		this.eventFilter = eventFilter;
		return this;
	}
	
}
