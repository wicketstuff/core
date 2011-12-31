package br.digilabs.jqplot.renderer.plugin;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.chart.elements.Renderer;

/**
 *
 * @author bernardo.moura
 */
public class DonutRenderer implements Renderer {

	private static final long serialVersionUID = -3572013159602499333L;

	public JqPlotResources resource() {
        return JqPlotResources.PieRenderer;
    }

}
