/*
 * YuiLoaderHeaderContributor.java
 *
 * Created on 9. September 2007, 20:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wicketstuff.yui.markup.html.contributor;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author korbinianbachl
 */
public class YuiLoaderHeaderContributor {

    static final String DEFAULT_YUI_BUILD = "2.8.1";
    static final String YUI_BUILD_ROOT = "../../../inc";


    /**
     * Creates a new instance of YuiLoaderHeaderContributor
     * <p/>
     * is currently in Alpha-State!
     */
    public YuiLoaderHeaderContributor() {

    }

    /**
     * YuiLoaderHeaderContributor.forModule is used to tell the YuiLoader what to load
     * You specifiy the module as well as the required executionJavaScript for the module,
     * the list of the modules is:
     * <p/>
     * <br><b>!!! Currently they are loaded inline, no sandbox-support yet !!!</b>
     * <p/>
     * <table><thead><tr><th>YUI Component</th><th>Module Name</th></tr></thead><tbody><tr><td><a href='http://developer.yahoo.com/yui/animation/'>Animation Utility</a></td><td>animation</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/autocomplete/'>AutoComplete Control</a></td><td>autocomplete</td></tr><tr><td><a href='http://developer.yahoo.com/yui/base/'>Base CSS Style Foundation</a></td><td>base</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/button/'>Button Control</a></td><td>button</td></tr><tr><td><a href='http://developer.yahoo.com/yui/calendar/'>Calendar Control</a></td><td>calendar</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/charts/'>Charts Control</a></td><td>charts</td></tr><tr><td><a href='http://developer.yahoo.com/yui/colorpicker/'>Color Picker Control</a></td><td>colorpicker</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/connection/'>Connection Manager</a></td><td>connection</td></tr><tr><td><a href='http://developer.yahoo.com/yui/container/'>Container Family Core (Module, Overlay)</a></td><td>containercore</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/container/'>Container Family (Module, Overlay Panel, Tooltip, Dialog, SimpleDialog)</a></td><td>container</td></tr><tr><td><a href='http://developer.yahoo.com/yui/datasource/'>DataSource Utility</a></td><td>datasource</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/datatable/'>DataTable Control</a></td><td>datatable</td></tr><tr><td><a href='http://developer.yahoo.com/yui/dom/'>Dom Collection</a></td><td>dom</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/dragdrop/'>Drag &amp; Drop Utility</a></td><td>dragdrop</td></tr><tr><td><a href='http://developer.yahoo.com/yui/editor/'>Rich Text Editor</a></td><td>editor</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/element/'>Element Utility</a></td><td>element</td></tr><tr><td><a href='http://developer.yahoo.com/yui/event/'>Event Utility</a></td><td>event</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/fonts/'>Fonts CSS</a></td><td>fonts</td></tr><tr><td><a href='http://developer.yahoo.com/yui/get/'>Get Utility</a></td><td>get</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/grids/'>Grids CSS Kit</a></td><td>grids</td></tr><tr><td><a href='http://developer.yahoo.com/yui/history/'>Browser History Manager</a></td><td>history</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/imageloader/'>ImageLoader Utility</a></td><td>imageloader</td></tr><tr><td><a href='http://developer.yahoo.com/yui/json/'>JSON Utility</a></td><td>json</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/logger/'>Logger Control</a></td><td>logger</td></tr><tr><td><a href='http://developer.yahoo.com/yui/menu/'>Menu Control</a></td><td>menu</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/profiler/'>Profiler Tool</a></td><td>profiler</td></tr><tr><td><a href='http://developer.yahoo.com/yui/reset/'>Reset CSS</a></td><td>reset</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/selector/'>Selector Utility</a></td><td>selector</td></tr><tr><td><a href='http://developer.yahoo.com/yui/editor/'>Simple Editor</a></td><td>simpleeditor</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/slider/'>Slider Control</a></td><td>slider</td></tr><tr><td><a href='http://developer.yahoo.com/yui/tabview/'>TabView Control</a></td><td>tabview</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/treeview/'>TreeView Control</a></td><td>treeview</td></tr><tr><td><a href='http://developer.yahoo.com/yui/yahoo/'>YAHOO Global Object</a></td><td>yahoo</td></tr><tr class='odd'><td><a href='http://developer.yahoo.com/yui/yuiloader/'>YUI Loader Utility</a></td><td>yuiloader</td></tr><tr><td><a href='http://developer.yahoo.com/yui/yuitest/'>YUI Test Tool</a></td><td>yuitest</td></tr></tbody></table>
     *
     * @param module
     * @param executeJS
     * @return
     */
    public static final IHeaderContributor forModule(final String module, final String executeJS) {
        return new IHeaderContributor() {
            private static final long serialVersionUID = 1L;

            public void renderHead(IHeaderResponse response) {

                //Render the YuiLoader min JS
                String resource = YUI_BUILD_ROOT + "/" + DEFAULT_YUI_BUILD + "/yuiloader/yuiloader-beta-min.js";
                //String yahooResource = "http://yui.yahooapis.com/" + DEFAULT_YUI_BUILD + "/build/yuiloader/yuiloader-beta-min.js";
                ResourceReference yuiLoaderRef = new JavaScriptResourceReference(YuiLoaderHeaderContributor.class, resource);
                response.render(JavaScriptHeaderItem.forReference(yuiLoaderRef));


                // new YUILoader since 2.4
                response.render(OnLoadHeaderItem.forScript("" +
                        "var loader = new YAHOO.util.YUILoader({" +
                        "require: ['" + module + "']," +
                        "loadOptional: true," +
                        "onSuccess: function() {" +
                        "" + executeJS +
                        "}" +
                        "}); " +
                        "" +
                        "loader.insert();"));


            }
        };
    }


}
