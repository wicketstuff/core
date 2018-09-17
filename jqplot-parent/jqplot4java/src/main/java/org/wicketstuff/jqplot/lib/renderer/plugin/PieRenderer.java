/*
 *  Copyright 2011 Inaiat H. Moraes.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.lib.renderer.plugin;

import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;
import org.wicketstuff.jqplot.lib.renderer.Renderer;

/**
 * Plugin renderer to draw a pie chart. x values, if present, will be used as slice labels. y values give slice size.
 * 
 * @see <a href="http://www.jqplot.com/docs/files/plugins/jqplot-pieRenderer-js.html">http://www.jqplot.com/docs/files/plugins/jqplot-pieRenderer-js.html</a>
 *
 * @author inaiat
 */
@Deprecated
@JqPlotPlugin(values = {JqPlotResources.PieRenderer})
public class PieRenderer implements Renderer {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;


    /* (non-Javadoc)
     * @see br.com.digilabs.jqplot.renderer.Renderer#resource()
     */
    public JqPlotResources resource() {
        return JqPlotResources.PieRenderer;
    }
}
