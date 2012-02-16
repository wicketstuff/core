package br.digilabs.jqplot.chart.data;

/**
 *
 * @author bernardo.moura
 */
public interface ChartData<T> {

    T getData();
    
    String toJsonString();
}
