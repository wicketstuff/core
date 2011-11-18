package br.digilabs.jqplot.chart.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.digilabs.jqplot.json.JSONArray;


/**
 *
 * @author inaiat
 */
public class AreaFillData<T extends Number> extends AbstractCollectionData<List<T>> {

    private List<List<T>> data = new ArrayList<List<T>>();

    public AreaFillData() {
    }

    public AreaFillData(List<T>... values) {
        for (int i = 0; i < values.length; i++) {
        	List<T> list = values[i];
            addValue(list);
        }
    }

    public Collection<List<T>> getData() {
        return data;
    }

    public String toJsonString() {
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray.toString();
    }
}
