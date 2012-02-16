/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.renderer.plugin;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.chart.elements.Renderer;

/**
 *
 * @author inaiat
 */
public class CategoryAxisRenderer implements Renderer {

	private static final long serialVersionUID = -6281926919874791228L;

	private Boolean sortMergedLabels;
    private Renderer tickRenderer;
    
    /**
     * @return the sortMergedLabels
     */
    public Boolean getSortMergedLabels() {
        return sortMergedLabels;
    }

    /**
     * @param sortMergedLabels the sortMergedLabels to set
     */
    public void setSortMergedLabels(Boolean sortMergedLabels) {
        this.sortMergedLabels = sortMergedLabels;
    }

    /**
     * @return the tickRenderer
     */
    public Renderer getTickRenderer() {
        return tickRenderer;
    }

    /**
     * @param tickRenderer the tickRenderer to set
     */
    public void setTickRenderer(Renderer tickRenderer) {
        this.tickRenderer = tickRenderer;
    }

    public JqPlotResources resource() {
        return JqPlotResources.CategoryAxisRenderer;
    }
    
}
