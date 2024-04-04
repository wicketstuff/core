/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wicketstuff.jqplot.lib.test;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.JqPlotUtils;
import org.wicketstuff.jqplot.lib.axis.Axis;
import org.wicketstuff.jqplot.lib.axis.AxisString;
import org.wicketstuff.jqplot.lib.axis.XAxis;
import org.wicketstuff.jqplot.lib.chart.LineSeriesChart;
import org.wicketstuff.jqplot.lib.data.item.LineSeriesItem;
import org.wicketstuff.jqplot.lib.elements.Axes;
import org.wicketstuff.jqplot.lib.elements.Cursor;
import org.wicketstuff.jqplot.lib.elements.Grid;
import org.wicketstuff.jqplot.lib.elements.Highlighter;
import org.wicketstuff.jqplot.lib.elements.Legend;
import org.wicketstuff.jqplot.lib.elements.Location;
import org.wicketstuff.jqplot.lib.elements.MarkerOptions;
import org.wicketstuff.jqplot.lib.elements.Serie;
import org.wicketstuff.jqplot.lib.elements.TickOptions;

/**
 *
 * @author vinicius
 */
public class LineSerieTest {
	@Test
    public void testLineSeriesChart() {
        LineSeriesChart<Number, Number> chart = new LineSeriesChart<>("Curves","X", "Y");

        Collection<LineSeriesItem<Number, Number>> linhaSin = new ArrayList<>();

        for(double i = 0; i<= 6; i+= 0.01 ){
        	linhaSin.add(new LineSeriesItem<Number, Number>(i, Math.sin(i)+4));
        }

        chart.addValue(linhaSin);

        Collection<LineSeriesItem<Number, Number>> linhaCos = new ArrayList<>();

        for(double i = 0; i<= 6; i+= 0.01 ){
        	linhaCos.add(new LineSeriesItem<Number, Number>(i, Math.cos(i)));
        }

        chart.addValue(linhaCos);

        Collection<LineSeriesItem<Number, Number>> linhaExp = new ArrayList<>();

        for(double i = 0; i<= 2; i+= 0.01 ){
        	linhaExp.add(new LineSeriesItem<Number, Number>(i, Math.exp(i)+1));
        }

        chart.addValue(linhaExp);

        Collection<LineSeriesItem<Number, Number>> linhaReta = new ArrayList<>();

        for(double i = 0; i<= 6; i+= 0.01 ){
        	linhaReta.add(new LineSeriesItem<Number, Number>(i, i));
        }

        chart.addValue(linhaReta);


		Highlighter highlighter = criarHighlighter();

		chart.getChartConfiguration().setHighlighter(highlighter);

		chart.addSerie(criarSerie("Seno", "#489104"));
		chart.addSerie(criarSerie("Coseno", "#c91212"));
		chart.addSerie(criarSerie("Exponensial", "#7D02B2"));
		chart.addSerie(criarSerie("Reta", "#066FA7"));

		Legend legend = new Legend(true, Location.ne);
		legend.setPlacement("outsideGrid");


		chart.setLegend(legend);

		Axis<String> axis = new AxisString();
		axis.setLabelRenderer(JqPlotResources.CanvasAxisLabelRenderer);

		chart.setAxesDefaults(axis);

		Axes<String> axes = chart.getAxes();
		XAxis<String> xaxis = axes.getXaxis();
		TickOptions tickOptions = new TickOptions();
		tickOptions.setAngle(270);
		tickOptions.setLabelPosition("end");


		xaxis.setTickOptions(tickOptions);
		xaxis.setTickRenderer(JqPlotResources.CanvasAxisTickRenderer);
		xaxis.setTickInterval(1);

		axes.getYaxis().setTickInterval(0.50);

		axes.getYaxis().setMax(8.50);
		axes.getYaxis().setMin(-1.50);



		TickOptions tickOptionsY = new TickOptions();
		tickOptionsY.setFormatString("%.2f");
		axes.getYaxis().setTickOptions(tickOptionsY);

		Grid<String> grid = new Grid<>();
		grid.setBackground("#ffffff");
		grid.setGridLineColer("#a0a0a0");
		chart.getChartConfiguration().setGrid(grid);
		Cursor cursor = new Cursor();
		cursor.setZoom(true);
		cursor.setClickReset(true);
		chart.getChartConfiguration().setCursor(cursor);

        String json = JqPlotUtils.createJquery(chart, "div3");
        json = json.replaceAll("\\$", "jQuery");
        System.out.println(json);

    }

    /**
     * Cria um Highlighter
     * @return
     */
	public Highlighter criarHighlighter() {
		Highlighter highlighter = new Highlighter();
		highlighter.setFormatString("<table style='background:#fafafa;font-size:12px;color:#000000'>" +
					"<tr><td COLSPAN=2 style='font-size:12px;color:#0012FF;font-weight:bold'>#serieLabel#</td></tr>"+
      				"<tr><td>DU:</td><td>%s</td></tr>" +
     		 		"<tr><td>Taxa:</td><td>%s%%</td></tr>"+
      				"</table>");



		highlighter.setShow(true);
		highlighter.setSizeAdjust(2.0);
		return highlighter;
	}

    /**
     * Cria uma serie para o gráfico ela é responsável em determinar a cor dos pontos e o nome da linha.
     * @param titulo
     * @param cor
     * @return a Serie criada
     */
	public Serie criarSerie(String titulo, String cor) {
		Serie s = new Serie(titulo);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setStyle("filledSquare");
        markerOptions.size(1.f);

		s.setMarkerOptions(markerOptions );
		s.showLine(true);
		s.setLineWidth(1);
		s.setColor(cor);

		return s;
	}
}
