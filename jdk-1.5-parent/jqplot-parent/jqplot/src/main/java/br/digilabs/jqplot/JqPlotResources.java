package br.digilabs.jqplot;

/**
 *
 * @author inaiat
 */
public enum JqPlotResources {

    BarRenderer("$.jqplot.barRenderer", "plugins/br.digilabs.jqplot.barRenderer.min.js"),
    PieRenderer("$.jqplot.PieRenderer", "plugins/br.digilabs.jqplot.pieRenderer.min.js"),
    DonutRenderer("$.jqplot.DonutRenderer", "plugins/br.digilabs.jqplot.donutRenderer.min.js"),
    CanvasAxisLabelRenderer("$.jqplot.CanvasAxisLabelRenderer", "plugins/br.digilabs.jqplot.canvasAxisLabelRenderer.min.js"),
    CategoryAxisRenderer("$.jqplot.CategoryAxisRenderer", "plugins/br.digilabs.jqplot.categoryAxisRenderer.min.js"),
    CanvasAxisTickRenderer("$.jqplot.CanvasAxisTickRenderer", "plugins/br.digilabs.jqplot.canvasAxisTickRenderer.min.js"),
    CanvasTextRenderer("$.jqplot.CanvasTextRenderer","plugins/br.digilabs.jqplot.canvasTextRenderer.min.js"),
    ShadowRenderer("$.jqplot.ShadowRenderer", "jquery.jqplot.min.js");
    
    private String className;
    private String resource;

    private JqPlotResources(String className) {
        this(className, null);
    }

    private JqPlotResources(String className, String resource) {
        this.className = className;
        this.resource = resource;
    }

    @Override
    public String toString() {
        return className;
    }

    public String getClassName() {
        return className;
    }

    public String getResource() {
        return resource;
    }
}
