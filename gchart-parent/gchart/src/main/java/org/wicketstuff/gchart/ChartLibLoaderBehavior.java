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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.wicketstuff.gchart.Chart.LOADER_URL;

/**
 * Behavior to bundle the Google chart lib loading and package declaration for
 * multiple charts on one page. All needed packages are merged in a set, so the
 * loading is done only once as the
 * <a href="https://developers.google.com/chart/interactive/docs/basic_multiple_charts">docs
 * request</a>.
 *
 * To use add this behavior to the page (<em>not the chart</em>) and give the
 * instance as param to every constructor of a chart component of the page. The
 * Charts will register in the loader by {@link ChartLibLoader#addChart(org.wicketstuff.gchart.Chart)
 * }
 * and lib loading is done centralized.
 *
 * Use this behavior at page when you display multiple charts on the page (Since
 * Google Charts version 45 this is mandatory, but still recommended.)
 *
 * @author Dieter Tremel
 */
public class ChartLibLoaderBehavior extends Behavior implements JavaScriptable, ChartLibLoader {

    private static final Logger log = LoggerFactory.getLogger(ChartLibLoaderBehavior.class);
    public static final String LOADER_SCRIPT_ID = ChartLibLoaderBehavior.class.getSimpleName();
    private static final long serialVersionUID = 1L;

    private Locale locale = null;
    private boolean renderLocale = true;
    private final List<Chart> charts = new ArrayList<>();
    private HeaderItem headerItem = null;

    @Override
    public void bind(Component component) {
        if (!(component instanceof Page)) {
            throw new IllegalArgumentException("This behavior should be used at the page, not the chart!");
        }
        super.bind(component);
        locale = component.getLocale();
    }

    @Override
    public boolean addChart(Chart chart) {
        return charts.add(chart);
    }

    @Override
    public boolean removeChart(Chart chart) {
        return charts.remove(chart);
    }

    /**
     * The Locale is used to render a {@code language = "de"} in the
     * <a href="https://developers.google.com/chart/interactive/docs/basic_load_libs">load
     * statement</a> like in the example:
     * <pre>
     * {@code
     * // Load Google Charts for the Japanese locale.
     * google.charts.load('current', {'packages':['corechart'], 'language': 'ja'});
     * }</pre> If this behavior is attached to the Page, Locale is set from the
     * Page which normally is identical to {@link Session#getLocale() }. To
     * override use {@link #setLocale(java.util.Locale) }.
     *
     * @return Locale used for the package language option.
     */
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public List<Chart> getCharts() {
        return charts;
    }

    public boolean isRenderLocale() {
        return renderLocale;
    }

    public void setRenderLocale(boolean renderLocale) {
        this.renderLocale = renderLocale;
    }

    /**
     * Get Google loader URL. Can be overwritten, if Google changes URL in
     * future.
     *
     * @return Returns {@link Chart#LOADER_URL}.
     */
    public String getLoaderUrl() {
        return LOADER_URL;
    }

    @Override
    public String toJavaScript() {
        // collect information from charts
        Set<String> packageSet = new HashSet<>();
        String mapsApiKey = null;
        for (Chart chart : charts) {
            packageSet.add(chart.getTypeModel().getObject().getLoadPackage());
            if (chart.getMapsApiKey() != null) {
                mapsApiKey = chart.getMapsApiKey();
            }
        }

        StringBuilder sb = new StringBuilder();
        // Load the Visualization API and the package.
        // google.charts.load('current', {'packages':['corechart']});
        JSONObject packageDecl = new JSONObject();
        JSONArray packages = new JSONArray(packageSet);
//        packages.put(packageSet);
        packageDecl.put("packages", packages);
        if (renderLocale) {
            packageDecl.put("language", getLocale().getLanguage());
        }
        if (mapsApiKey != null) {
            packageDecl.put("mapsApiKey", mapsApiKey);
        }
        sb.append("google.charts.load('current', ").append(packageDecl.toString()).append(");").append("\n");
        return sb.toString();
    }

    @Override
    public HeaderItem getHeaderItem() {
        if (headerItem == null) {
            headerItem = new JavaScriptContentHeaderItem(toJavaScript(), LOADER_SCRIPT_ID, null) {
                @Override
                public List<HeaderItem> getDependencies() {
                    final List<HeaderItem> dependencies = super.getDependencies();
                    dependencies.add(JavaScriptHeaderItem.forUrl(getLoaderUrl()));
                    return dependencies;
                }
            };
        }
        return headerItem;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        response.render(getHeaderItem());
    }

}
