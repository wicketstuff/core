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
import java.util.List;
import java.util.Locale;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.gchart.gchart.options.ChartOptions;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

/**
 * Abstraction of Google charts for wicket. {@code OutputMarkupId} is set to
 * true, since id is referenced in generated JavaScript.
 *
 * @author Dieter Tremel
 */
public class Chart extends WebComponent implements JavaScriptable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(Chart.class);
    /** URL for Google lib loader */
    public static final String LOADER_URL = "https://www.gstatic.com/charts/loader.js";

    private ChartLibLoader loader = null;
    private boolean responsive = true;
    private Locale locale = null;
    private String mapsApiKey = null;
    private IModel<ChartType> typeModel;
    private IModel<DataTable> dataModel;

    /**
     * Basic Constructor.
     *
     * @param id Wicket id. Id is used in javascript, but at the moment not
     * escaped to JavaScript identifier rules. So use only characters allowed
     * for JavaScript declarations to avoid problems.
     */
    public Chart(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    /**
     * Complete Constructor.
     *
     * @param id Wicket id. Id is used in javascript, but at the moment not
     * escaped to JavaScript identifier rules. So use only characters allowed
     * for JavaScript declarations to avoid problems.
     *
     * This constructor is for use without {@link ChartLibLoader} (one chart per page).
     *
     * @param typeModel Model of Type of chart.
     * @param optionModel Model of optionModel.
     * @param dataModel Model of data dataModel.
     */
    public Chart(String id, IModel<ChartType> typeModel, IModel<ChartOptions> optionModel, IModel<DataTable> dataModel) {
        this(id, typeModel, optionModel, dataModel, null);
    }

    /**
     * Complete Constructor.
     *
     * @param id Wicket id. Id is used in javascript, but at the moment not
     * escaped to JavaScript identifier rules. So use only characters allowed
     * for JavaScript declarations to avoid problems.
     * 
     * This constructor is for use with {@link ChartLibLoader} (multiple charts per page).
     *
     * @param typeModel Model of Type of chart.
     * @param optionModel Model of optionModel.
     * @param dataModel Model of data dataModel.
     * @param loader Loader to add the chart to.
     */
    public Chart(String id, IModel<ChartType> typeModel, IModel<ChartOptions> optionModel, IModel<DataTable> dataModel, ChartLibLoader loader) {
        this(id);
        this.typeModel = typeModel;
        this.setDefaultModel(optionModel);
        setDataModel(dataModel);
//        add(new GoogleChartBehavior(this, getMarkupId()));
        this.loader = loader;
        if (loader != null) {
            loader.addChart(this);
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        typeModel.detach();
        dataModel.detach();
    }

    /**
     * Get Google Loader URL. Can be overwritten, if Google changes URL in
     * future.
     *
     * @return Returns {@link #LOADER_URL}.
     */
    public String getLoaderUrl() {
        return LOADER_URL;
    }

    /**
     * Create the JavaScript for the Google loader.
     *
     * @return HeaderItem for Google loader url.
     */
    public JavaScriptHeaderItem createLoaderItem() {
        return JavaScriptHeaderItem.forUrl(getLoaderUrl());
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        // TODO ist this rendering sufficient for refreshing chart by AJAX?

        final List<HeaderItem> depItemList = new ArrayList<>();
        final JavaScriptContentHeaderItem chartScriptItem = new JavaScriptContentHeaderItem(toJavaScript(), getScriptId(), null) {
            private static final long serialVersionUID = 1L;

            @Override
            public List<HeaderItem> getDependencies() {
                return depItemList;
            }
        };
        if (loader == null) {
            response.render(createLoaderItem());
        } else {
            depItemList.add(loader.getHeaderItem());
        }
        response.render(chartScriptItem);
        if (responsive) {
            final JavaScriptReferenceHeaderItem jQueryHeaderItem = JavaScriptHeaderItem.forReference(
                    getApplication().getJavaScriptLibrarySettings().getJQueryReference());
//            response.render(jQueryHeaderItem);
            response.render(new JavaScriptContentHeaderItem(createRedrawJavaScript(), getRedrawScriptId(), null) {
                private static final long serialVersionUID = 1L;

                @Override
                public List<HeaderItem> getDependencies() {
                    final List<HeaderItem> dependencies = super.getDependencies();
                    dependencies.add(jQueryHeaderItem);
                    return dependencies;
                }
            });
        }
    }

    /**
     * Should some JavaScript be added to make the Chart responsive. By default
     * Google charts are not responsive, this is an extension by wicket-gchart.
     *
     * <p>
     * This is set to true by default.
     *
     * @return True if the chart will redraw on window resize, false if not.
     */
    public boolean isResponsive() {
        return responsive;
    }

    /**
     * Set this to true if some JavaScript should be added to make the Chart
     * responsive. By default Google charts are not responsive, this is an
     * extension by wicket-gchart. If true a script like
     * <pre>
     * {@code $(window).resize(function(){
     *   drawChart1();
     * });}
     * </pre> will be rendered in head to add this functionality.
     *
     * @param responsive Set to true to dynamically redraw the chart on resize
     * events. Set to false not to redraw. Default is true.
     */
    public void setResponsive(boolean responsive) {
        this.responsive = responsive;
    }

    /**
     * The Locale is used to render a {@code language = "de"} in the
     * <a href="https://developers.google.com/chart/interactive/docs/basic_load_libs">load
     * statement</a> like in the example:
     * <pre>
     * {@code
     * // Load Google Charts for the Japanese locale.
     * google.charts.load('current', {'packages':['corechart'], 'language': 'ja'});
     * }</pre> If Locale is not explicitly set and null, the wicket {@link Component#getLocale()
     * }
     * is returned, which is identical to {@link Session#getLocale() }. To
     * override use {@link #setLocale(java.util.Locale) }.
     *
     * @return Locale used for the package language option.
     */
    @Override
    public Locale getLocale() {
        if (locale == null) {
            return super.getLocale();
        } else {
            return locale;
        }
    }

    /**
     * Override default Locale for the chart. See {@link #getLocale() }.
     *
     * @param locale Locale to render chart with.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Getter for Google Maps API key for geo- and map charts. In all other
     * charts this value will be null. Getter should never find any usage except
     * tests and logging.
     *
     * @return Google Maps API key. Null except for Geo Charts.
     */
    public String getMapsApiKey() {
        return mapsApiKey;
    }

    /**
     * Setter for Google Maps API key for geo- and map charts. In all other
     * charts this has no use. For geo- and mapcharts add this key to be
     * rendered in package deklaration:
     * <pre> {@code
     * google.charts.load('current', {
     *  'packages':['geochart'],
     *  // Note: you will need to get a mapsApiKey for your project.
     *  // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
     *  'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'
     * });
     * }</pre>
     *
     * @param mapsApiKey Google Maps API key.
     */
    public void setMapsApiKey(String mapsApiKey) {
        this.mapsApiKey = mapsApiKey;
    }

    public IModel<ChartType> getTypeModel() {
        return typeModel;
    }

    public void setTypeModel(IModel<ChartType> typeModel) {
        this.typeModel = typeModel;
    }

    public IModel<ChartOptions> getOptionModel() {
        return (IModel<ChartOptions>) getDefaultModelObject();
    }

    public void setOptionModel(IModel<ChartOptions> optionModel) {
        this.setDefaultModel(optionModel);
    }

    public IModel<DataTable> getDataModel() {
        return dataModel;
    }

    public final void setDataModel(IModel<DataTable> dataModel) {
        this.dataModel = dataModel instanceof IComponentAssignedModel
                ? ((IComponentAssignedModel<DataTable>) dataModel).wrapOnAssignment(this)
                : dataModel;
    }

    /**
     * Create the load statement of the chart lib with package, language and
     * Maps API key declaration as defined by the chart and its
     * {@link ChartType}.
     *
     * @return Onle line load statement to be included in a JavaScript.
     */
    private String createLoaderStatement() {
        StringBuilder sb = new StringBuilder();
        // Load the Visualization API and the package.
        JSONObject packageDecl = new JSONObject();
        JSONArray packages = new JSONArray();
        packages.put(typeModel.getObject().getLoadPackage());
        packageDecl.put("packages", packages);
        packageDecl.put("language", getLocale().getLanguage());
        if (mapsApiKey != null) {
            packageDecl.put("mapsApiKey", mapsApiKey);
        }
        sb.append("google.charts.load('current', ").append(packageDecl.toString()).append(");").append("\n");
        return sb.toString();
    }

    @Override
    public String toJavaScript() {
        StringBuilder sb = new StringBuilder();

        if (loader == null) {
            sb.append(createLoaderStatement());
        }

        // Register a callback to run when the Google Visualization API is loaded.
        sb.append("google.charts.setOnLoadCallback(").append(getCallbackId()).append(");").append("\n");

        // define callback function
        sb.append("function ").append(getCallbackId()).append("() {").append("\n");

        // data table
        final DataTable datatable = dataModel.getObject();
        sb.append(datatable.toJavaScript()).append("\n");

        // options
        final ChartOptions options = (ChartOptions) getDefaultModelObject();
        sb.append(options.toJavaScript()).append("\n");

        // Instantiate and draw our chart, passing in the optionModel.
        // var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        sb.append("var ").append(getChartId()).append(" = new ").append(typeModel.getObject().toJavaScript()).append("(");
        sb.append("document.getElementById('").append(getMarkupId()).append("')");
        sb.append(")").append("\n");

        // chart.draw(data, optionModel);
        sb.append(getChartId()).append(".draw(").append(datatable.getName()).append(", ").append(options.getName()).append(");").append("\n");

        sb.append("}").append("\n"); // close callback function

        return sb.toString();
    }

    /**
     * Make a callback identifier from chart id.
     *
     * @return Identifier for callback.
     */
    public String getCallbackId() {
        // TODO escape CSS ids in some way to avoid javascript id problems
        return "draw" + getId();
    }

    /**
     * Make a chart identifier from chart id.
     *
     * @return Identifier(js) for chart.
     */
    public String getChartId() {
        // TODO escape CSS ids in some way to avoid javascript id problems
        return getId() + "Chart";
    }

    /**
     * Make a script id from chart id.
     *
     * @return Identifier(js) for chart creation script..
     */
    public String getScriptId() {
        // TODO escape CSS ids in some way to avoid javascript id problems
        return getId() + "Script";
    }

    /**
     * Make a script id for redraw script from chart id. This is used if chart
     * should be responsive.
     *
     * @return Identifier(js) for chart redraw script..
     */
    public String getRedrawScriptId() {
        // TODO escape CSS ids in some way to avoid javascript id problems
        return getId() + "RedrawScript";
    }

//$(window).resize(function(){
//  drawChart1();
//  drawChart2();
//});
    /**
     * Create a a redraw script for responsive charts. The chart is redrawn on
     * {@code (window).resize} events.
     *
     * @return Complete redraw script.
     */
    public String createRedrawJavaScript() {
        StringBuilder sb = new StringBuilder("$(window).resize(function(){");
        sb.append(getCallbackId()).append("();");
        sb.append("});");
        return sb.toString();
    }

}
