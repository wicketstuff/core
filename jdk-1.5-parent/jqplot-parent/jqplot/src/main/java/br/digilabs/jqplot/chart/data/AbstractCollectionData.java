package br.digilabs.jqplot.chart.data;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author inaiat
 */
public abstract class AbstractCollectionData<T> implements ChartData<Collection<T>>  {        
    
    public void addValues(Collection<T> value) {
        getData().addAll(value);
    }

    public void addValues(T... values) {
        getData().addAll(Arrays.asList(values));
    }

    public void addValue(T value) {
        getData().add(value);
    }    
    
}
