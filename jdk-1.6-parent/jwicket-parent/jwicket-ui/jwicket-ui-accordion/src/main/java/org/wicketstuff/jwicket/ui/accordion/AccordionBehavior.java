package org.wicketstuff.jwicket.ui.accordion;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.wicketstuff.jwicket.*;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;

import java.io.Serializable;


public class AccordionBehavior extends AbstractJqueryUiEmbeddedBehavior implements IStyleResolver {

    private static final long serialVersionUID = 1L;

    public static final JQueryResourceReference uiAccordionJs
            = JQuery.isDebug()
            ? new JQueryResourceReference(AccordionBehavior.class, "jquery.ui.accordion.js")
            : new JQueryResourceReference(AccordionBehavior.class, "jquery.ui.accordion.min.js");

    protected JsMap options = new JsMap();

    public AccordionBehavior() {
        super(
                AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
                uiAccordionJs
        );
        addCssResources(getCssResources());
    }


    /**
     * Sets the 'autoHeight' property for this accordion. Please consult the
     * jQuery documentation for a detailled description of this property.
     *
     * @param value the autoHeight value
     * @return this object
     */
    public AccordionBehavior setAutoHeight(final boolean value) {
        this.options.put("autoHeight", value);
        return this;
    }

    public AccordionBehavior setAutoHeight(final AjaxRequestTarget target, final boolean value) {
        setAutoHeight(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').accordion('option','autoHeight'," + value + ");");
        return this;
    }


    /**
     * Sets the 'collapsible' property for this accordion. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the collapsible value
     * @return this object
     */
    public AccordionBehavior setCollapsible(final boolean value) {
        this.options.put("collapsible", value);
        return this;
    }

    public AccordionBehavior setCollapsible(final AjaxRequestTarget target, final boolean value) {
        setCollapsible(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').accordion('option','collapsible'," + value + ");");
        return this;
    }


    /**
     * Sets the 'active' property for this accordion. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the active (=expanded) item
     * @return this object
     */
    public AccordionBehavior setActive(final int value) {
        this.options.put("active", value);
        return this;
    }

    public AccordionBehavior setActive(final AjaxRequestTarget target, final int value) {
        setActive(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').accordion('option','active'," + value + ");");
        return this;
    }


    private int currentExpandedIndex = -1;

    public int getCurrentExpandedIndex() {
        return this.currentExpandedIndex;
    }


    /**
     * Handles the event processing during resizing.
     */
    @Override
    protected void respond(final AjaxRequestTarget target) {
        Component component = getComponent();
        Request request;
        if (component != null && (request = component.getRequest()) != null) {
            EventType eventType = EventType.stringToType(request.getRequestParameters().getParameterValue(EventType.IDENTIFIER).toString());

            String newHeader = "";
            String oldHeader = "";
            String newContent = "";
            String oldContent = "";
            String active = "";

            newHeader = request.getRequestParameters().getParameterValue("newHeader").toString();
            oldHeader = request.getRequestParameters().getParameterValue("oldHeader").toString();
            newContent = request.getRequestParameters().getParameterValue("newContent").toString();
            oldContent = request.getRequestParameters().getParameterValue("oldContent").toString();
            active = request.getRequestParameters().getParameterValue("active").toString();

            int activeIndex = -1;
            try {
                activeIndex = Integer.parseInt(active);
            } catch (Exception e) {
                activeIndex = -1;
            }


            if (eventType == EventType.CHANGE) {
                if (oldContent != null && oldHeader != null) {
                    ComponentFinder finder = new ComponentFinder(oldHeader);

                    Component oldHeaderComponent = component.getPage().visitChildren(finder);
                    finder = new ComponentFinder(oldContent);
                    Component oldContentComponent = component.getPage().visitChildren(finder);
                    onCollapse(target, oldHeaderComponent, oldContentComponent, this.currentExpandedIndex);
                }

                if (newContent != null && newHeader != null) {
                    ComponentFinder finder = new ComponentFinder(newHeader);
                    Component newHeaderComponent = component.getPage().visitChildren(finder);
                    finder = new ComponentFinder(newContent);
                    Component newContentComponent = component.getPage().visitChildren(finder);

                    if (newHeaderComponent != null && newContentComponent != null) {
                        onExpand(target, newHeaderComponent, newContentComponent, activeIndex);
                    }
                    this.currentExpandedIndex = activeIndex;
                } else {
                    this.currentExpandedIndex = -1;
                }

            }
        }
    }


    @Override
    protected JsBuilder getJsBuilder() {
        this.options.put(EventType.CHANGE.eventName,
                new JsFunction("function(ev,ui) {" +
                        "var active=jQuery('#" + getComponent().getMarkupId() + "').accordion('option', 'active');" +
                        "wicketAjaxGet('" +
                        this.getCallbackUrl() +
                        "&newHeader='+jQuery(ui.newHeader).attr('id')" +
                        "+'&oldHeader='+jQuery(ui.oldHeader).attr('id')" +
                        "+'&newContent='+jQuery(ui.newContent).attr('id')" +
                        "+'&oldContent='+jQuery(ui.oldContent).attr('id')" +
                        "+'&active='+active" +
                        "+'&" + EventType.IDENTIFIER + "=" + EventType.CHANGE +
                        "'" +
                        "); }"));


        JsBuilder builder = new JsBuilder();

        builder.append("jQuery('#" + getComponent().getMarkupId() + "').accordion(");
        builder.append("{");
        builder.append(this.options.toString(this.rawOptions));
        builder.append("}");
        builder.append(")");

        builder.append(";");

        return builder;
    }


    private enum EventType implements Serializable {

        UNKNOWN("*"),
        CHANGE("change"),
        CHANGE_START("changestart");

        public static final String IDENTIFIER = "wicketAccordionEvent";

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


    @Override
    public JQueryCssResourceReference[] getCssResources() {
        return new JQueryCssResourceReference[]{
                AbstractJqueryUiEmbeddedBehavior.jQueryUiCss,
                AbstractJqueryUiEmbeddedBehavior.jQueryUiThemeCss,
                AbstractJqueryUiEmbeddedBehavior.jQueryUiAccordionCss
        };
    }


    protected void onExpand(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {
    }


    protected void onCollapse(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index) {
    }

}
