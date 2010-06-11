package org.wicketstuff.jwicket.ui.dragdrop;


import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.CssCursor;
import org.wicketstuff.jwicket.CssPosition;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;


/**
 * You can add an instance of this class to a Wicket {@link Component} to make it
 * draggable. An instance of this class can be added to one and only one
 * {@link Component}. Another {@link Component} that should have exactly the
 * same draggable behavior needs it's own instance.
 */
public class DraggableBehavior extends AbstractDragDropBehavior {

	private static final long serialVersionUID = 1L;
	public  static final JQueryJavascriptResourceReference jQueryUiDraggableJs = new JQueryJavascriptResourceReference(DraggableBehavior.class, "jquery.ui.draggable.min.js");

	private JsMap options = new JsMap();

	
	public DraggableBehavior() {
		super(	AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
				AbstractJqueryUiEmbeddedBehavior.jQueryUiMouseJs,
				SpecialKeys.specialKeysJs,
				jQueryUiDraggableJs);
	}



	/**
	 * handles the event processing during dragging.
	 */
	@Override
	protected void respond(final AjaxRequestTarget target) {
		Component component = getComponent();
		Request request;

		if (component != null && (request = component.getRequest()) != null) {
			EventType dragEventType = EventType.stringToType(request.getParameter(EventType.IDENTIFIER));

			if (component instanceof IDraggable) {
				IDraggable draggableComponent = (IDraggable)component;
				if (dragEventType == EventType.DRAG_START)
					draggableComponent.onDragStart(target, new SpecialKeys(request));
				else if (dragEventType == EventType.DRAG_END)
					draggableComponent.onDragStop(target, new SpecialKeys(request));
				else if (dragEventType == EventType.DRAG)
					draggableComponent.onDrag(target, new SpecialKeys(request));
			}


			if (dragEventType == EventType.DRAG_START)
				onDragStart(target, new SpecialKeys(request));
			else if (dragEventType == EventType.DRAG_END)
				onDragStop(target, new SpecialKeys(request));
			else if (dragEventType == EventType.DRAG)
				onDrag(target, new SpecialKeys(request));
		}
	}
	
	
	private String name = null;

	/**
	 * Sets the name of this draggable. The name can be used by a droppable
	 * componen to distinguish between allowed and forbidden droppables.
	 * 
	 * @param value the name
	 * @return this object
	 */
	public DraggableBehavior setName(final String value) {
		this.name = value;
		return this;
	}
	/**
	 * Get the name of this draggable
	 * @return The draggable's name
	 */
	public String getName() {
		return this.name;
	}

	
	/**
	 * Sets the 'addClasses' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DraggableBehavior setAddClasses(final boolean value) {
		if (value)
			options.remove("addClasses");
		else
			options.put("addClasses", value);
		return this;
	}
	public DraggableBehavior setAddClasses(final AjaxRequestTarget target, final boolean value) {
		setAddClasses(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','addClasses'," + value + ");");
		return this;
	}

	
	/**
	 * Sets the 'axis' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value {@code 'x'} or {@code 'y'}.
	 * @return this object
	 */
	public DraggableBehavior setAxis(final char value) {
		if (value != 'x' && value != 'y')
			options.remove("axis");
		else
			options.put("axis", String.valueOf(value));
		return this;
	}
	public DraggableBehavior setAxis(final AjaxRequestTarget target, final char value) {
		setAxis(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','axis','" + value + "');");
		return this;
	}

	
	/**
	 * Sets the 'cursor' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value a {@link CssCursor}
	 * @return this object
	 */
	public DraggableBehavior setCursor(final CssCursor value) {
		if (value == null)
			options.remove("cursor");
		else
			options.put("cursor", value.getName());
		return this;
	}
	public DraggableBehavior setCursor(final AjaxRequestTarget target, final CssCursor value) {
		setCursor(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','cursor','" + value.getName() + "');");
		return this;
	}

	
	/**
	 * Sets the 'cursorAt' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param position a {@link CssPosition}
	 * @return this object
	 */
	public DraggableBehavior setCursorAt(final CssPosition position, final int offset) {
		if (position == null)
			options.remove("cursorAt");
		else
			options.put("cursorAt", new JsOption(position.getName(), offset));
		return this;
	}
	public DraggableBehavior setCursorAt(final AjaxRequestTarget target, final CssPosition position, final int offset) {
		setCursorAt(position, offset);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','cursorAt',{" + position.getName() + ":" + offset + "});");
		return this;
	}

	
	/**
	 * Sets the 'cursorAt' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param position1 a {@link CssPosition}
	 * @param offset1 the offset from {@code position1}
	 * @param position2 a {@link CssPosition}
	 * @param offset2 the offset from {@code position2}
	 * @return this object
	 */
	public DraggableBehavior setCursorAt(final CssPosition position1, final int offset1, final CssPosition position2, final int offset2) {
		if (position1 == null || position2 == null)
			options.remove("cursorAt");
		else
			options.put("cursorAt", new JsOption(position1.getName(), offset1), new JsOption(position2.getName(), offset2));
		return this;
	}
	public DraggableBehavior setCursorAt(final AjaxRequestTarget target, final CssPosition position1, final int offset1, final CssPosition position2, final int offset2) {
		setCursorAt(position1, offset1, position2, offset2);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','cursorAt',{" + position1.getName() + ":" + offset1 + "," + position2.getName() + ":" + offset2 + "});");
		return this;
	}

	
	/**
	 * Sets the 'delay' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value the delay in ms
	 * @return this object
	 */
	public DraggableBehavior setDelay(final int value) {
		if (value <= 0)
			options.remove("delay");
		else
			options.put("delay", value);
		return this;
	}
	public DraggableBehavior setDelay(final AjaxRequestTarget target, final int value) {
		setDelay(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','delay'," + value + ");");
		return this;
	}


	/**
	 * Sets the 'distance' property for this draggable. Please consult the
	 * jquery documentation for a detailled description of this peroperty.
	 * @param value the value of this property.
	 * @return this object
	 */
	public DraggableBehavior setDistance(final int value) {
		if (value <= 1)
			options.remove("distance");
		else
			options.put("distance", value);
		return this;
	}
	public DraggableBehavior setDistance(final AjaxRequestTarget target, final int value) {
		setDistance(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','distance'," + value + ");");
		return this;
	}


	/**
	 * Sets the 'grid' property for this draggable. Please consult the
	 * jquery documentation for a detailled description of this peroperty.
	 * @param x the stepping along the x axis
	 * @param y the stepping along the y axis
	 * @return this object
	 */
	public DraggableBehavior setGrid(final int x, final int y) {
		if (x <= 1 && y <= 1)
			options.remove("grid");
		else
			options.put("grid", x, y);
		return this;
	}
	public DraggableBehavior setGrid(final AjaxRequestTarget target, final int x, final int y) {
		setGrid(x, y);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','grid',[" + x + "," + y + "]);");
		return this;
	}


	/**
	 * Sets the 'helper' property for this draggable. Please consult the
	 * jquery documentation for a detailled description of this peroperty.
	 * @param value {@code original} or {@code clone}
	 * @return this object
	 */
	public DraggableBehavior setDistance(final String value) {
		if (value == null)
			options.remove("helper");
		else
			options.put("helper", value);
		return this;
	}
	public DraggableBehavior setDistance(final AjaxRequestTarget target, final String value) {
		setDistance(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','helper','" + value + "');");
		return this;
	}


	/**
	 * Sets the 'iframeFix' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DraggableBehavior setIframeFix(final boolean value) {
		if (!value)
			options.remove("iframeFix");
		else
			options.put("iframeFix", value);
		return this;
	}
	public DraggableBehavior setIframeFix(final AjaxRequestTarget target, final boolean value) {
		setIframeFix(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','iframeFix'," + value + ");");
		return this;
	}


	/**
	 * Sets the 'opacity' property for this draggable. Please consult the
	 * jquery documentation for a detailled description of this peroperty.
	 * @param value the opacity between 0 (transparent) and 1 (not transparent).
	 * @return this object
	 */
	public DraggableBehavior setOpacity(final double value) {
		if (value < 0)
			options.remove("opacity");
		else
			options.put("opacity", value);
		return this;
	}
	public DraggableBehavior setOpacity(final AjaxRequestTarget target, final double value) {
		setOpacity(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','opacity'," + value + ");");
		return this;
	}


	/**
	 * Sets the 'refreshPositions' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DraggableBehavior setRefreshPositions(final boolean value) {
		if (!value)
			options.remove("refreshPositions");
		else
			options.put("refreshPositions", value);
		return this;
	}
	public DraggableBehavior setRefreshPositions(final AjaxRequestTarget target, final boolean value) {
		setRefreshPositions(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','refreshPositions'," + value + ");");
		return this;
	}


	public static enum DragRevertMode {

		NEVER("false", false),
		ALWAYS("true", true),
		VALID("valid"),
		INVALID("invalid")
		;

		private final String value;
		private final Boolean booleanValue;
		private DragRevertMode(final String value, final boolean booleanValue) {
			this.value = value;
			this.booleanValue = booleanValue;
		}
		private DragRevertMode(final String value) {
			this.value = value;
			this.booleanValue = null;
		}

		public String getValue() {
			return this.value;
		}

		public Boolean getBooleanValue() {
			return this.booleanValue;
		}

		public String toString() {
			return this.value;
		}
	}


	/**
	 * Sets the 'revert' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value the revert behavior
	 * @return this object
	 */
	public DraggableBehavior setRevert(final DragRevertMode value) {
		if (value == null)
			options.remove("revert");
		else {
			if (value.getBooleanValue() != null)
				options.put("revert", value.getBooleanValue());
			else
				options.put("revert", value.getValue());
		}
		return this;
	}
	public DraggableBehavior setRevert(final AjaxRequestTarget target, final DragRevertMode value) {
		setRevert(value);
		if (value.getBooleanValue() != null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','revert'," + value + ");");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','revert','" + value + "');");
		return this;
	}


	/**
	 * Sets the 'revertDuration' property for this draggable. Please consult the
	 * jquery documentation for a detailled description of this peroperty.
	 * @param value the duration of the revert animation
	 * @return this object
	 */
	public DraggableBehavior setRevertDuration(final int value) {
		if (value < 0)
			options.remove("revertDuration");
		else
			options.put("revertDuration", value);
		return this;
	}
	public DraggableBehavior setRevertDuration(final AjaxRequestTarget target, final int value) {
		setRevertDuration(value);
		if (value >= 0)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','revertDuration'," + value + ");");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','revertDuration',0);");
		return this;
	}


	/**
	 * Sets the 'scroll' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DraggableBehavior setScroll(final boolean value) {
		if (value)
			options.remove("scroll");
		else
			options.put("scroll", value);
		return this;
	}
	public DraggableBehavior setScroll(final AjaxRequestTarget target, final boolean value) {
		setScroll(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','scroll'," + value + ");");
		return this;
	}


	/**
	 * Sets the 'scrollSensitivity' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value the scroll sensitivity in px
	 * @return this object
	 */
	public DraggableBehavior setScrollSensitivity(final int value) {
		if (value == 20)
			options.remove("scrollSensitivity");
		else
			options.put("scrollSensitivity", value);
		return this;
	}
	public DraggableBehavior setScrollSensitivity(final AjaxRequestTarget target, final int value) {
		setScrollSensitivity(value);
		if (value > 0)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','scrollSensitivity'," + value + ");");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','scrollSensitivity',0);");
		return this;
	}


	/**
	 * Sets the 'scrollSpeed' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value the scroll speed in px
	 * @return this object
	 */
	public DraggableBehavior setScrollSpeed(final int value) {
		if (value == 20)
			options.remove("scrollSpeed");
		else
			options.put("scrollSpeed", value);
		return this;
	}
	public DraggableBehavior setScrollSpeed(final AjaxRequestTarget target, final int value) {
		setScrollSpeed(value);
		if (value > 0)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','scrollSpeed'," + value + ");");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','scrollSpeed',0);");
		return this;
	}


	/**
	 * Sets the 'snap' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DraggableBehavior setSnap(final boolean value) {
		if (!value)
			options.remove("snap");
		else
			options.put("snap", value);
		return this;
	}
	public DraggableBehavior setSnap(final AjaxRequestTarget target, final boolean value) {
		setSnap(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','snap'," + value + ");");
		return this;
	}

	public static enum DragHelperMode {

		ORIGINAL("original"),
		CLONE("clone")
		;

		private final String value;
		private DragHelperMode(final String value) {
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
	 * Sets the 'helper' property for this draggable with a function. Please 
	 * consult the jQuery documentation for a detailed description of this property.
	 * DraggableHelper can be used for values of "original" and "clone", or
	 * a custom function can be used.
	 * @param function
	 * @return this object
	 */
	public DraggableBehavior setHelper(final String function) {
		if (function == null)
			options.remove("helper");
		else 
			options.put("helper", new JsFunction(function));
		return this;
	}
	public DraggableBehavior setHelper(final AjaxRequestTarget target, final String function) {
		setHelper(function);
		if (function != null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','helper'," + function + ");");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','helper','original');");
		return this;
	}

	/**
	 * Sets the 'helper' property for this draggable to either "original"
	 * or "clone". Please consult the jQuery documentation for a detailed 
	 * description of this property.
	 * @param value
	 * @return this object
	 */
	public DraggableBehavior setHelper(final DragHelperMode value) {
		if (value == null)
			options.remove("helper");
		else {
			options.put("helper", value.getValue());
		}
		return this;
	}
	public DraggableBehavior setHelper(final AjaxRequestTarget target, final DragHelperMode value) {
		setHelper(value);
		if (value != null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','helper','" + value + "');");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','helper','original');");
		return this;
	}

	public static enum DragSnapMode {

		INNER("inner"),
		OUTER("outer"),
		BOTH("both")
		;

		private final String value;
		private DragSnapMode(final String value) {
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
	 * Sets the 'snapMode' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value the revert behavior
	 * @return this object
	 */
	public DraggableBehavior setSnapMode(final DragSnapMode value) {
		if (value == null)
			options.remove("snapMode");
		else {
			options.put("snapMode", value.getValue());
		}
		return this;
	}
	public DraggableBehavior setSnapMode(final AjaxRequestTarget target, final DragSnapMode value) {
		setSnapMode(value);
		if (value != null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','snapMode','" + value + "');");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','snapMode','both');");
		return this;
	}


	/**
	 * Sets the 'snapTolerance' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value the scroll speed in px
	 * @return this object
	 */
	public DraggableBehavior setSnapTolerance(final int value) {
		if (value == 20)
			options.remove("snapTolerance");
		else
			options.put("snapTolerance", value);
		return this;
	}
	public DraggableBehavior setSnapTolerance(final AjaxRequestTarget target, final int value) {
		setSnapTolerance(value);
		if (value != 20)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','snapTolerance'," + value + ");");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','snapTolerance',0);");
		return this;
	}


	/**
	 * Sets the 'zIndex' property for this draggable. Please consult the
	 * jQuery documentation for a detailled description of this peroperty.
	 * @param value the scroll speed in px
	 * @return this object
	 */
	public DraggableBehavior setZIndex(final long value) {
		if (value <= 0)
			options.remove("zIndex");
		else
			options.put("zIndex", value);
		return this;
	}
	public DraggableBehavior setZIndex(final AjaxRequestTarget target, final int value) {
		setZIndex(value);
		if (value > 0)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','zIndex'," + value + ");");
		else
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('option','zIndex',false);");
		return this;
	}



	private boolean onDragStopNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onDragStop(AjaxRequestTarget,SpecialKeys)} 
	 * is called when the drag operation ends.
	 *
	 * Be careful when using this in conjunction with {@link DroppableBehavior#onDrop(AjaxRequestTarget, Component, SpecialKeys)}.
	 * The Ajax calls issued by jQuery are processed asynchronous. So it my happen that the
	 * onDrop event reaches Wicket <em>before</em> the onDragStop event. This will cause
	 * an Exception in Wicket's internal processing if you replaced the dragged component with a new one
	 * in your onDrop handler becaus the dragged component no longer exists in wickets store when
	 * the onDragStop event is processed by Wicket.
	 *
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DraggableBehavior setWantOnDragStopNotification(final boolean value) {
		onDragStopNotificationWanted = value;
		return this;
	}

	private boolean onDragNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onDrag(AjaxRequestTarget,SpecialKeys)} 
	 * is called every time the mouse moves during the drag operation.
	 * Be careful using this callback because it can generate a lot of Ajax calls.
	 *
	 * See {@link #setWantOnDragStartNotification(boolean) for a serious warning!
	 *
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DraggableBehavior setWantOnDragNotification(final boolean value) {
		onDragNotificationWanted = value;
		return this;
	}

	private boolean onDragStartNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onDragStart(AjaxRequestTarget,SpecialKeys)} 
	 * is called when the drag operation starts.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DraggableBehavior setWantOnDragStartNotification(final boolean value) {
		onDragStartNotificationWanted = value;
		return this;
	}



	/**
	 * If you have set {@link #setWantOnDragStartNotification(boolean)} to {@code true}
	 * this method is called when the drag operation starts. You can override this
	 * method to perform some action when dragging starts.
	 *
	 * @param target the AjaxRequestTarget of the drag operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	protected void onDragStart(final AjaxRequestTarget target, final SpecialKeys specialKeys) {}


	/**
	 * If you have set {@link #setWantOnDragNotification(boolean)} to {@code true}
	 * this method is called whenever ths mouse moves during the drag operation.
	 * You can override this method to perform some action during the drag operation.
	 *
	 * @param target the AjaxRequestTarget of the drag operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	protected void onDrag(final AjaxRequestTarget target, final SpecialKeys specialKeys) {}


	/**
	 * If you have set {@link #setWantOnDragStopNotification(boolean)} to {@code true}
	 * this method is called when the drag operation stops. You can override this
	 * method to perform some action when dragging stops.
	 *
	 * @param target the AjaxRequestTarget of the drag operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	protected void onDragStop(final AjaxRequestTarget target, final SpecialKeys specialKeys) {}


	/**
	 * Disable the dragging
	 *
	 * @param target An AjaxRequestTarget
	 */
	public void disable(final AjaxRequestTarget target) {
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('disable');");
	}


	/**
	 * Enable the dragging
	 *
	 * @param target An AjaxRequestTarget
	 */
	public void enable(final AjaxRequestTarget target) {
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('enable');");
	}


	@Override
	/**
	 * For internal use only.
	 */
	protected JsBuilder getJsBuilder() {
		if (onDragStopNotificationWanted)
			options.put(EventType.DRAG_END.getEventName(),
						new JsFunction("function() { wicketAjaxGet('" +
										this.getCallbackUrl(false) +
										"&" + EventType.IDENTIFIER + "=" + EventType.DRAG_END +
										"&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
										"); }"));
		else
			options.remove(EventType.DRAG_END.getEventName());


		if (onDragNotificationWanted)
			options.put(EventType.DRAG.getEventName(),
						new JsFunction("function() { wicketAjaxGet('" +
										this.getCallbackUrl() +
										"&" + EventType.IDENTIFIER + "=" + EventType.DRAG +
										"&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
										"); }"));
		else
			options.remove(EventType.DRAG.getEventName());


		if (onDragStartNotificationWanted) 
			options.put(EventType.DRAG_START.getEventName(),
						new JsFunction("function() { wicketAjaxGet('" +
										this.getCallbackUrl() +
										"&" + EventType.IDENTIFIER + "=" + EventType.DRAG_START +
										"&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
										"); }"));
		else
			options.remove(EventType.DRAG_START.getEventName());


		JsBuilder builder = new JsBuilder();

		builder.append("jQuery('#" + getComponent().getMarkupId() + "').draggable(");
		builder.append("{");
		builder.append(options.toString(rawOptions));
		builder.append("}");
		builder.append(");");


		if (name != null)
			builder.append(new JsFunction("\njQuery(function(){jQuery('#" +
					getComponent().getMarkupId() + "').attr('" +
					DraggablesAcceptedByDroppable.DRAG_NAME_IDENTIFIER + "', '" + name +
					"');});"));

		return builder;
	}
}
