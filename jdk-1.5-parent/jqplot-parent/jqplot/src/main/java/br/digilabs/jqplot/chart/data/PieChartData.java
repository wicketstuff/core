package br.digilabs.jqplot.chart.data;

import java.util.ArrayList;
import java.util.Collection;

import br.digilabs.jqplot.json.JSONArray;


/**
 *
 * @author bernardo.moura
 */
public class PieChartData<T extends Object> extends  AbstractCollectionData<T>  {

    private Collection<T> data = new ArrayList<T>();

    public Collection<T> getData() {
        return data;
    }

    public String toJsonString() {
        String sReturn = null;
        JSONArray jsonArray = new JSONArray();
        try {

            jsonArray.put(data);
            sReturn = jsonArray.getJSONArray(0).toString();

        } catch (Exception e) {
            sReturn = jsonArray.toString();
        }
        return sReturn;
    }
}
