package org.wicketstuff.jwicket.ui.resizable;


import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;
import org.wicketstuff.jwicket.JsMap;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;

import java.io.Serializable;


/**
 * You can add an instance of this class to a Wicket {@link Component} to make it
 * a resizable {@link Component}.
 * An instance of this class can be added to one and only one
 * {@link Component}. Another {@link Component} that should have exactly the
 * same behavior needs it's own instance.
 */
public class ResizableBehavior extends AbstractJqueryUiEmbeddedBehavior {

    private static final long serialVersionUID = 1L;

    public static final JQueryResourceReference uiResizableJs
            = JQuery.isDebug()
            ? new JQueryResourceReference(ResizableBehavior.class, "jquery.ui.resizable.js")
            : new JQueryResourceReference(ResizableBehavior.class, "jquery.ui.resizable.min.js");

    protected JsMap options = new JsMap();
    private boolean onResizeStartNotificationWanted = false;

    public ResizableBehavior() {
        super(AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
                AbstractJqueryUiEmbeddedBehavior.jQueryUiMouseJs,
                SpecialKeys.specialKeysJs,
                uiResizableJs);
    }

    /**
     * Handles the event processing during resizing.
     */
    @Override
    protected void respond(final AjaxRequestTarget target) {
//System.out.println("***** ResizableBehavior.respond");

        Component component = getComponent();
        Request request;
        if (component != null && (request = component.getRequest()) != null) {
//System.out.println("\rrequest != null");
//Map<String, String[]> parameterMap = request.getParameterMap();
//for (String par : parameterMap.keySet()) {
//	String parameter = request.getRequestParameters().getParameterValue(par);
//	System.out.println("\t" + par + "='" + parameter + "'");
//}
            EventType eventType = EventType.stringToType(request.getRequestParameters().getParameterValue(EventType.IDENTIFIER).toString());

            int top = 0;
            int left = 0;
            int width = 0;
            int height = 0;
            int originalTop = 0;
            int originalLeft = 0;
            int originalWidth = 0;
            int originalHeight = 0;
            try {
                top = Integer.parseInt(request.getRequestParameters().getParameterValue("top").toString());
                left = Integer.parseInt(request.getRequestParameters().getParameterValue("left").toString());
                width = (int) (Double.parseDouble(request.getRequestParameters().getParameterValue("width").toString()) + 0.5);
                height = (int) (Double.parseDouble(request.getRequestParameters().getParameterValue("height").toString()) + 0.5);

                if (eventType == EventType.RESIZE_END) {
                    originalTop = Integer.parseInt(request.getRequestParameters().getParameterValue("originalTop").toString());
                    originalLeft = Integer.parseInt(request.getRequestParameters().getParameterValue("originalLeft").toString());
                    originalWidth = (int) (Double.parseDouble(request.getRequestParameters().getParameterValue("originalWidth").toString()) + 0.5);
                    originalHeight = (int) (Double.parseDouble(request.getRequestParameters().getParameterValue("originalHeight").toString()) + 0.5);
                }
            } catch (Exception e) {
                // should not happen!
                throw new WicketRuntimeException(e);
            }

            if (component instanceof IResizable) {
                IResizable resizableComponent = (IResizable) component;
                if (eventType == EventType.RESIZE_END) {
                    resizableComponent.onResized(target, top, left, width, height, originalTop, originalLeft, originalWidth, originalHeight, new SpecialKeys(request));
                } else if (eventType == EventType.RESIZE_START) {
                    resizableComponent.onResizeStart(target, top, left, width, height, new SpecialKeys(request));
                } else if (eventType == EventType.RESIZE) {
                    resizableComponent.onResize(target, top, left, width, height, new SpecialKeys(request));
                }
            }

            if (eventType == EventType.RESIZE_END) {
                onResized(target, top, left, width, height, originalTop, originalLeft, originalWidth, originalHeight, new SpecialKeys(request));
            } else if (eventType == EventType.RESIZE_START) {
                onResizeStart(target, top, left, width, height, new SpecialKeys(request));
            } else if (eventType == EventType.RESIZE) {
                onResize(target, top, left, width, height, new SpecialKeys(request));
            }
        }
    }


    /**
     * Sets the 'animate' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public ResizableBehavior setAnimate(final boolean value) {
        if (!value) {
            this.options.remove("animate");
        } else {
            this.options.put("animate", value);
        }
        return this;
    }

    public ResizableBehavior setAnimate(final AjaxRequestTarget target, final boolean value) {
        setAnimate(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','animate'," + value + ");");
        return this;
    }


    /**
     * Sets the 'animateDuration' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public ResizableBehavior setAnimateDuration(final int value) {
        if (value <= 0) {
            this.options.remove("animateDuration");
        } else {
            this.options.put("animateDuration", value);
        }
        return this;
    }

    public ResizableBehavior setAnimateDuration(final AjaxRequestTarget target, final int value) {
        setAnimateDuration(value);
        if (value >= 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','animateDuration'," + value + ");");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','animateDuration','slow');");
        }
        return this;
    }


    /**
     * Sets the 'animateEasing' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the behavior
     * @return this object
     */
    public ResizableBehavior setAnimateEasing(final String value) {
        if (value == null) {
            this.options.remove("animateEasing");
        } else {
            this.options.put("animateEasing", value);
        }
        return this;
    }

    public ResizableBehavior setAnimateEasing(final AjaxRequestTarget target, final String value) {
        setAnimateEasing(value);
        if (value != null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','animateEasing','" + value + "');");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','animateEasing','swing');");
        }
        return this;
    }


    /**
     * Sets the 'aspectRatio' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the aspect ratio
     * @return this object
     */
    public ResizableBehavior setAspectRatio(final double value) {
        if (value <= 0) {
            this.options.remove("aspectRatio");
        } else {
            this.options.put("aspectRatio", value);
        }
        return this;
    }

    public ResizableBehavior setAspectRatio(final boolean value) {
        if (value) {
            setAspectRatio(1);
        } else {
            setAspectRatio(0);
        }
        return this;
    }

    public ResizableBehavior setAspectRatio(final AjaxRequestTarget target, final double value) {
        setAspectRatio(value);
        if (value > 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','aspectRatio'," + value + ");");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','aspectRatio',false);");
        }
        return this;
    }

    public ResizableBehavior setAspectRatio(final AjaxRequestTarget target, final boolean value) {
        if (value) {
            setAspectRatio(target, 1);
        } else {
            setAspectRatio(target, 0);
        }
        return this;
    }


    /**
     * Sets the 'autoHide' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public ResizableBehavior setAutoHide(final boolean value) {
        if (!value) {
            this.options.remove("autoHide");
        } else {
            this.options.put("autoHide", value);
        }
        return this;
    }

    public ResizableBehavior setAutoHide(final AjaxRequestTarget target, final boolean value) {
        setAutoHide(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','autoHide'," + value + ");");
        return this;
    }


    /**
     * Sets the 'delay' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the delay in ms
     * @return this object
     */
    public ResizableBehavior setDelay(final int value) {
        if (value <= 0) {
            this.options.remove("delay");
        } else {
            this.options.put("delay", value);
        }
        return this;
    }

    public ResizableBehavior setDelay(final AjaxRequestTarget target, final int value) {
        setDelay(value);
        if (value >= 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','delay'," + value + ");");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','delay',0);");
        }
        return this;
    }


    /**
     * Sets the 'distance' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the distance in px
     * @return this object
     */
    public ResizableBehavior setDistance(final int value) {
        if (value <= 0) {
            this.options.remove("distance");
        } else {
            this.options.put("distance", value);
        }
        return this;
    }

    public ResizableBehavior setDistance(final AjaxRequestTarget target, final int value) {
        setDistance(value);
        if (value >= 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','distance'," + value + ");");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','distance',1);");
        }
        return this;
    }


    /**
     * Sets the 'ghost' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public ResizableBehavior setGhost(final boolean value) {
        if (!value) {
            this.options.remove("ghost");
        } else {
            this.options.put("ghost", value);
        }
        return this;
    }

    public ResizableBehavior setGhost(final AjaxRequestTarget target, final boolean value) {
        setGhost(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','ghost'," + value + ");");
        return this;
    }


    /**
     * Sets the 'grid' property for this draggable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param x the stepping along the x axis
     * @param y the stepping along the y axis
     * @return this object
     */
    public ResizableBehavior setGrid(final int x, final int y) {
        if (x <= 1 && y <= 1) {
            this.options.remove("grid");
        } else {
            this.options.put("grid", x, y);
        }
        return this;
    }

    public ResizableBehavior setGrid(final AjaxRequestTarget target, final int x, final int y) {
        setGrid(x, y);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','grid',[" + x + "," + y + "]);");
        return this;
    }


    public static enum ResizableDirections {

        NORTH("n"),
        EAST("e"),
        SOUTH("s"),
        WEST("w"),
        NORTH_EAST("ne"),
        SOUTH_EAST("se"),
        SOUTH_WEST("sw"),
        NORTH_WEST("nw");


        private String direction;

        private ResizableDirections(final String direction) {
            this.direction = direction;
        }

        public String getDirection() {
            return this.direction;
        }

        public String toString() {
            return getDirection();
        }
    }

    public ResizableBehavior setHandles(final ResizableDirections... directions) {
        String handles = "";
        if (directions == null || directions.length == 0) {
            this.options.remove("handles");
        } else {
            for (ResizableDirections direction : directions) {
                if (handles.length() > 0) {
                    handles += ",";
                }
                handles += direction.getDirection();
            }
            this.options.put("handles", handles);
        }
        return this;
    }

    public ResizableBehavior setHandles(final AjaxRequestTarget target, final ResizableDirections... directions) {
        setHandles(directions);
        if (directions == null || directions.length == 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','handles','e, s, se');");
        } else {
            String handles = "";
            for (ResizableDirections direction : directions) {
                if (handles.length() > 0) {
                    handles += ",";
                }
                handles += direction.getDirection();
            }
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','handles','" + handles + "');");
        }
        return this;
    }


    /**
     * Sets the 'helper' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the helper's CSS class
     * @return this object
     */
    public ResizableBehavior setHelper(final String value) {
        if (value == null) {
            this.options.remove("helper");
        } else {
            this.options.put("helper", value);
        }
        return this;
    }

    public ResizableBehavior setHelper(final AjaxRequestTarget target, final String value) {
        setHelper(value);
        if (value != null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','helper','" + value + "');");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','helper',false);");
        }
        return this;
    }


    /**
     * Sets the 'maxHeight' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the value of the property
     * @return this object
     */
    public ResizableBehavior setMaxHeight(final int value) {
        if (value <= 0) {
            this.options.remove("maxHeight");
        } else {
            this.options.put("maxHeight", value);
        }
        return this;
    }

    public ResizableBehavior setMaxHeight(final AjaxRequestTarget target, final int value) {
        setMaxHeight(value);
        if (value > 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','maxHeight'," + value + ");");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','maxHeight',null);");
        }
        return this;
    }


    /**
     * Sets the 'maxWidth' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the value of the property
     * @return this object
     */
    public ResizableBehavior setMaxWidth(final int value) {
        if (value <= 0) {
            this.options.remove("maxWidth");
        } else {
            this.options.put("maxWidth", value);
        }
        return this;
    }

    public ResizableBehavior setMaxWidth(final AjaxRequestTarget target, final int value) {
        setMaxWidth(value);
        if (value > 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','maxWidth'," + value + ");");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','maxWidth',null);");
        }
        return this;
    }


    /**
     * Sets the 'minHeight' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the value of the property
     * @return this object
     */
    public ResizableBehavior setminHeight(final int value) {
        if (value <= 0) {
            this.options.remove("minHeight");
        } else {
            this.options.put("minHeight", value);
        }
        return this;
    }

    public ResizableBehavior setminHeight(final AjaxRequestTarget target, final int value) {
        setminHeight(value);
        if (value > 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','minHeight'," + value + ");");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','minHeight',10);");
        }
        return this;
    }


    /**
     * Sets the 'minWidth' property for this resizable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the value of the property
     * @return this object
     */
    public ResizableBehavior setminWidth(final int value) {
        if (value <= 0) {
            this.options.remove("minWidth");
        } else {
            this.options.put("minWidth", value);
        }
        return this;
    }

    public ResizableBehavior setminWidth(final AjaxRequestTarget target, final int value) {
        setminWidth(value);
        if (value > 0) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','minWidth'," + value + ");");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable('option','minWidth',10);");
        }
        return this;
    }

    /**
     * If set to {@code true}, the callback-Method {@link #onResizeStart(AjaxRequestTarget, int, int, int, int, SpecialKeys)}
     * is called when the resize operation starts.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public ResizableBehavior setWantOnresizeStartNotification(final boolean value) {
        this.onResizeStartNotificationWanted = value;
        return this;
    }


    private boolean onResizeNotificationWanted = false;

    /**
     * If set to {@code true}, the callback-Method {@link #onResize(AjaxRequestTarget, int, int, int, int, SpecialKeys)}
     * is called every time the mouse moves during the resize operation.
     * Be careful using this callback because it can generate a lot of Ajax calls.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public ResizableBehavior setWantOnResizeNotification(final boolean value) {
        this.onResizeNotificationWanted = value;
        return this;
    }


    @Override
    protected JsBuilder getJsBuilder() {
        if (this.onResizeStartNotificationWanted) {
            this.options.put(EventType.RESIZE_START.eventName,
                    new JsFunction("function(ev,ui) { wicketAjaxGet('" +
                            this.getCallbackUrl() +
                            "&height='+jQuery.data(ui.size, 'height')" +
                            "+'&width='+jQuery.data(ui.size, 'width')" +
                            "+'&top='+jQuery.data(ui.position, 'top')" +
                            "+'&left='+jQuery.data(ui.position, 'left')" +
                            "+'&" + EventType.IDENTIFIER + "=" + EventType.RESIZE_START +
                            "&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
                            "); }"));
        } else {
            this.options.remove(EventType.RESIZE_START.getEventName());
        }


        this.options.put(EventType.RESIZE_END.eventName,
                new JsFunction("function(ev,ui) {" +
                        " wicketAjaxGet('" +
                        this.getCallbackUrl() +
                        "&height='+jQuery.data(ui.size, 'height')" +
                        "+'&width='+jQuery.data(ui.size, 'width')" +
                        "+'&top='+jQuery.data(ui.position, 'top')" +
                        "+'&left='+jQuery.data(ui.position, 'left')" +
                        "+'&originalHeight='+jQuery.data(ui.originalSize, 'height')" +
                        "+'&originalWidth='+jQuery.data(ui.originalSize, 'width')" +
                        "+'&originalTop='+jQuery.data(ui.originalPosition, 'top')" +
                        "+'&originalLeft='+jQuery.data(ui.originalPosition, 'left')" +
                        "+'&" + EventType.IDENTIFIER + "=" + EventType.RESIZE_END +
                        "&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
                        "); }"));


        if (this.onResizeNotificationWanted) {
            this.options.put(EventType.RESIZE.eventName,
                    new JsFunction("function(ev,ui) { wicketAjaxGet('" +
                            this.getCallbackUrl() +
                            "&height='+jQuery.data(ui.size, 'height')" +
                            "+'&width='+jQuery.data(ui.size, 'width')" +
                            "+'&top='+jQuery.data(ui.position, 'top')" +
                            "+'&left='+jQuery.data(ui.position, 'left')" +
                            "+'&" + EventType.IDENTIFIER + "=" + EventType.RESIZE +
                            "&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
                            "); }"));
        } else {
            this.options.remove(EventType.RESIZE.getEventName());
        }


        JsBuilder builder = new JsBuilder();

        builder.append("jQuery('#" + getComponent().getMarkupId() + "').resizable(");
        builder.append("{");
        builder.append(this.options.toString(this.rawOptions));
        builder.append("}");
        builder.append(");");

        return builder;
    }


    /**
     * If you have set {@link #setWantOnresizeStartNotification(boolean)} to {@code true}
     * this method is called when the resize operation starts. You can override this
     * method to perform some action when resizing starts.
     *
     * @param target      the AjaxRequestTarget of the resize operation.
     * @param specialKeys the special keys that were pressed when the event occurs
     */
    protected void onResizeStart(final AjaxRequestTarget target, final int top, final int left, final int width, final int height, final SpecialKeys specialKeys) {
    }

    /**
     * If you have set {@link #setWantOnResizeNotification(boolean)} to {@code true}
     * this method is called when the mouse moves during the resize operation. You
     * can override this
     * method to perform some action during the resize operation.
     *
     * @param target      the AjaxRequestTarget of the resize operation.
     * @param specialKeys the special keys that were pressed when the event occurs
     */
    protected void onResize(final AjaxRequestTarget target, final int top, final int left, final int width, final int height, final SpecialKeys specialKeys) {
    }

    /**
     * After the resize operation has ended this method is called.
     * You can override this method to perform some action after the
     * resize operation has finished.
     *
     * @param target      the AjaxRequestTarget of the resize operation.
     * @param specialKeys the special keys that were pressed when the event occurs
     */
    protected void onResized(final AjaxRequestTarget target,
                             final int top, final int left, final int width, final int height,
                             final int originalTop, final int originalLeft, final int originalWidth, final int originalHeight,
                             final SpecialKeys specialKeys) {
    }

    /**
     * Disable the resizing
     *
     * @param target An AjaxRequestTarget
     */
    public void disable(final AjaxRequestTarget target) {
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable( 'disable' );");
        target.add(getComponent());
    }

    /**
     * Enable the resizing
     *
     * @param target An AjaxRequestTarget
     */
    public void enable(final AjaxRequestTarget target) {
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').resizable( 'enable' );");
        target.appendJavaScript(getJsBuilder().toString());
    }

    private enum EventType implements Serializable {

        UNKNOWN("*"),
        RESIZE_START("start"),
        RESIZE("resize"),
        RESIZE_END("stop");

        public static final String IDENTIFIER = "wicketResizeEvent";

        private final String eventName;

        private EventType(final String eventName) {
            this.eventName = eventName;
        }

        public String getEventName() {
            return this.eventName;
        }

        public static EventType stringToType(final String s) {
            for (EventType t : EventType.values()) {
                if (t.getEventName().equals(s)) {
                    return t;
                }
            }
            return UNKNOWN;
        }

        public String toString() {
            return this.eventName;
        }
    }

}
