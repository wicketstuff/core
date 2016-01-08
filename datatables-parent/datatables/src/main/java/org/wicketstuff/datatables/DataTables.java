package org.wicketstuff.datatables;

import static de.agilecoders.wicket.jquery.JQuery.$;

import java.util.List;

import de.agilecoders.wicket.jquery.util.Strings2;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.wicketstuff.datatables.options.Options;

/**
 *
 */
public class DataTables<T, S> extends DataTable<T, S> {

    private final Options options;

    public DataTables(String id, List<? extends IColumn<T, S>> iColumns, IDataProvider<T> dataProvider, long rowsPerPage) {
        super(id, iColumns, dataProvider, rowsPerPage);
        
        setOutputMarkupId(true);
        this.options = new Options();
        
        add(new DataTablesBehavior(options));
    }

    public Options getOptions() {
        return options;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        String dataTableFn = $(this).chain("dataTable", options).get();
        String setup = String.format("window['%s'] = %s", getJsHandle(), dataTableFn);
        response.render(OnDomReadyHeaderItem.forScript(setup));
    }

    /**
     * @return An identifier that could be used to lookup the DataTable after initialization
     */
    public String getJsHandle() {

        // WicketStuff_DataTables_markupId
        return "WS_DT_" + Strings2.escapeMarkupId(getMarkupId());
    }
}
