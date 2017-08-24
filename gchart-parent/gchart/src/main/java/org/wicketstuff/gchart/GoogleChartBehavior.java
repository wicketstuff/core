/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
