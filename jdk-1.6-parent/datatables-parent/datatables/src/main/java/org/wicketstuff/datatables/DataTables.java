package org.wicketstuff.datatables;

import static de.agilecoders.wicket.jquery.JQuery.$;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.repeater.data.IDataProvider;

/**
 *
 */
public class DataTables<T, S> extends DataTable<T, S> {

    private final Options options;

    public DataTables(String id, List<? extends IColumn<T, S>> iColumns, IDataProvider<T> dataProvider, long rowsPerPage) {
        super(id, iColumns, dataProvider, rowsPerPage);
        
        setOutputMarkupId(true);
        this.options = new Options();
        
        add(new DataTablesBehavior());
    }

    public Options getOptions() {
        return options;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(OnDomReadyHeaderItem.forScript($(this).chain("dataTable", options).get()));
    }

}
