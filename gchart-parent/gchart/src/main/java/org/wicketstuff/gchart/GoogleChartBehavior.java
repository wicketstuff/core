/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import static org.wicketstuff.gchart.Chart.LOADER_URL;

/**
 *
 * @author Dieter Tremel
 */
public class GoogleChartBehavior extends Behavior {

    private static final long serialVersionUID = 6022115409129348109L;

    private final Chart chart;
    private final String divId;

    public GoogleChartBehavior(Chart chart, String divId) {
        this.chart = chart;
        this.divId = divId;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        // TODO ist this rendering sufficient for refreshing chart by AJAX?
        response.render(JavaScriptHeaderItem.forReference(
                component.getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptHeaderItem.forUrl(LOADER_URL));
        response.render(JavaScriptHeaderItem.forScript(chart.toJavaScript(), chart.getScriptId()));
        if (chart.isResponsive()) {
            response.render(JavaScriptHeaderItem.forScript(chart.createRedrawJavaScript(), chart.getRedrawScriptId()));
        }
    }

}
