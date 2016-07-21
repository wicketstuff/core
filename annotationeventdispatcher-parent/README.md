Annotation based event dispatcher
=================================

Overview
--------
The standard way of handling events in Wicket is to override the [Component#onEvent(IEvent<?> event)][1] method and use the Java `instanceof` operator to determine if the payload of the event is of interest.

	public void onEvent(IEvent<?> event) {
		Object payload = event.getPayload();
		if (payload instanceof MyObject) {
			...	
		} else if (payload instanceof YourObject) {
			...
		}
	}

This library improves upon this situation by providing an `@OnEvent` annotation to specify any number of single-parameter event handler methods in Wicket components that the event dispatcher in the library will take care of calling.  The event handler will only be invoked if the payload of the event is appropriate for the parameter of the method.

The `@OnEvent` annotation takes an optional `stop` property.  If the `stop` property is true, the event will not propagate to any other event handlers.  Note that it probably only makes sense to use this with events where the broadcast type is `Broadcast.BUBBLE`.  By default, this property is false.

Note that the `stop` property is specified at compile-time.  In order to more flexibly control the propagation of an event at run-time, an event handler method can return a `Visit` object to specify whether an event should continue propagating or whether the event should not continue propagating deeper into the component hierarchy. 

The `@OnEvent` annotation also takes an optional `types` property.  The `types` property is an array of classes that is used in conjunction with the `ITypedEvent` interface to provide a means of discriminating the event handler methods to invoke.  The basic idea is that in order for an event handler to be invoked when the event payload is of type `ITypedEvent` the `types` classes on the event handler must be assignable from the result of the getTypes() method on the event.  The `AbstractPayloadTypedEvent` and `AbstractClassTypedPayloadEvent` classes are starting points for these types of events.       

Installation
------------
The library uses a Wicket `IInitializer` to install itself into an application so the library will be automatically installed if it is on the classpath.  See the [Wicket Guide][2] and [IInitializer Javadocs][3] for more information.

Configuration
-------------
The configuration object for the library, `AnnotationEventDispatcherConfig`, can be accessed via an application's metadata: `Application.get().getMetaData(Initializer.ANNOTATION_EVENT_DISPATCHER_CONFIG_CONTEXT_KEY)`.

Currently, there is just one configuration parameter:

* class filter that events must implement in order to be handled by the annotation dispatcher; by default, there is no filter

Whether an event is dispatched to a given event handler method is controlled by the Component#canCallListenerInterface(Method) method.  By default, this method returns false if the component is either disabled or not visible.

Usage
-----
With events, it is easy to create components that only deal with UI or data-entry logic and not the logic of what to do with the data.  For example, the job of a form component is to get the data from the user in the right format, with the proper maximum lengths, etc.  When these requirements have been satisfied, rather than the saving the data itself, the form component simply `send`s an event that some other component (most likely a parent component) listens for and handles (i.e., using a single-argument method with an @OnEvent annotation).

In most cases, after receiving and processing an event, an event handler will want to refresh part of the screen using AJAX.  The `AbsractTargetedEvent` is a base class for events that carry an `AjaxRequestTarget` object.  An example of an event that might extend this class is a cancel event; for example, an event handler might use the `AjaxRequestTarget` to close a `ModalWindow`.

In addition to an `AjaxRequestTarget` object, the actual data being worked with is another useful piece of information for an event to carry.  The `AbstractPayloadEvent` provides a generic base class for working with these types of events.  For example, if there was a `Widget` class, the form panel that handled the editing of a `Widget` might send a `WidgetSaveEvent` that extends the `AbstractPayloadEvent<Widget>` class.  Similarly, a button that is clicked to delete a `Widget`, might send a `WidgetDeleteEvent` when it is clicked; this class also would extend the `AbstractPayloadEvent<Widget>` class.    

In most systems, there are a common set of actions that can be performed on most of the objects in the system.  For example, a system might deal with widgets, people, places, jobs and relationships between those various objects.  It is probably the case that similar actions can be performed on the objects: create, edit, delete, detail, select, etc.  

It would be nice to have, for example, a generic `SaveEvent` to send when there was a save action to perform.  For example
	
	send(this, Broadcast.BUBBLE, new SaveEvent<>(target, person));
	send(this, Broadcast.BUBBLE, new SaveEvent<>(target, widget));

These events could then be listened for:

	@OnEvent public void handleSavePersonEvent(SaveEvent<Person> event) { ... }
	@OnEvent public void handleSaveWidgetEvent(SaveEvent<Widget> event) { ... }

The problem is that the generic information is not retained at run-time and the `handleSavePersonEvent` method might be called with a `Widget` object and the `handleSaveWidgetEvent` might be called with a `Person` object.

To get around this problem, use the `types` property of the `@OnEvent` annotation in conjunction with an event that implements `ITypedEvent`.  The idea is that an event will only be dispatched to an event handler if the `types` classes match the classes returned by the `ITypedEvent#getTypes()` method.  

The `AbstractPayloadTypedEvent` is the best starting place for implementing events of this type.  It simply returns the type of its payload -- the generic parameter.  Here is an example of this:

	public class SaveEvent<T> extends AbstractPayloadTypedEvent<T> { } 
	...
	send(this, Broadcast.BUBBLE, new SaveEvent<>(target, new Widget()));
	...
	@OnEvent(types = Widget.class) 
	public void handleSaveWidgetEvent(SaveEvent<Widget> event) { ... }

For the event handler, the `types` property (Widget.class) and the generic parameter on the event (Widget) should agree.

Suggestions
-----------
* Determine the set of common actions that can be used with the various objects in the system.  These are most likely verbs such as save, delete, detail, edit, etc.
* Create an event for each these actions that extends `AbstractPayloadTypedEvent`.  For example, `SaveEvent<T>`, `DeleteEvent<T>`, etc.
* Create event-specific implementations of each event-producing component, most likely buttons and links, that simply sends the specific event in the `onClick()` or `onSubmit()` handler methods.  For example, `SaveButton`, `DeleteButton`, etc.  The object the component will send can either be explicitly set as the model for the component or the component can get the model from, for example, the enclosing form.
* Separate form functionality into "pure" UI components that deal with user input and send events, and "mediator" components that contain the pure UI components and handle the events they send.  This has the effect of centralizing the code that persists the data.

[1]: http://ci.apache.org/projects/wicket/apidocs/6.x/org/apache/wicket/Component.html#onEvent(org.apache.wicket.event.IEvent)
[2]: http://wicket.apache.org/guide/guide/advanced.html#advanced_3
[3]: http://ci.apache.org/projects/wicket/apidocs/6.x/org/apache/wicket/IInitializer.html