package br.digilabs.jqplot.renderer.plugin;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.chart.elements.Renderer;
import br.digilabs.jqplot.metadata.JqPlotPlugin;

/**
 *
 * @author bernardo.moura
 */
@JqPlotPlugin(values= {JqPlotResources.PieRenderer})
public class PieRenderer implements Renderer {

	private static final long serialVersionUID = 1L;

	public JqPlotResources resource() {
        return JqPlotResources.PieRenderer;
    }
}
