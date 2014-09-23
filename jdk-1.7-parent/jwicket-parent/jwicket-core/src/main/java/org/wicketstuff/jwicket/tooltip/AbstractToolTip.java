package org.wicketstuff.jwicket.tooltip;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.IComponentAwareHeaderContributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractToolTip extends Behavior {

    private static final long serialVersionUID = 1L;

    List<Component> components = new ArrayList<Component>();
    Map<String, String> options = new HashMap<String, String>();

    String tooltipText;

    protected AbstractToolTip(final String tooltipText) {
        this.tooltipText = tooltipText.replace("</", "<\\/");
    }

    protected AbstractToolTip setTooltipText(final String htmlCode) {
        this.tooltipText = htmlCode.replace("</", "<\\/");
        return this;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        getHeaderContributor().renderHead(component, response);
    }

    protected abstract IComponentAwareHeaderContributor getHeaderContributor();

    public abstract String getJavaScript();

    @Override
    public void bind(final Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Argument component must be not null");
        }

        this.components.add(component);
        component.setOutputMarkupId(true);
    }

    public void update(final AjaxRequestTarget target) {
        target.appendJavaScript(getJavaScript());
    }

    protected final String getTooltipText() {
        return this.tooltipText;
    }
}
