package org.wicketstuff.datatables;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import java.util.List;

/**
 *
 */
public class DataTables<T, S> extends DataTable<T, S> {

    public DataTables(String id, List<? extends IColumn<T, S>> iColumns, IDataProvider<T> dataProvider, long rowsPerPage) {
        super(id, iColumns, dataProvider, rowsPerPage);

        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(CssHeaderItem.forReference(new DataTablesCssReference()));
        response.render(JavaScriptHeaderItem.forReference(new DataTablesJsReference()));

        response.render(OnDomReadyHeaderItem.forScript(String.format("$('#%s').dataTable();", getMarkupId())));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        tag.append("class", "display", " ");
    }
}
