package org.wicketstuff.datatables;

import de.agilecoders.wicket.jquery.util.Strings2;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.repeater.data.EmptyDataProvider;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.wicketstuff.datatables.options.Options;

import java.util.List;

import static de.agilecoders.wicket.jquery.JQuery.$;

/**
 *
 */
public class DataTables<T, S> extends DataTable<T, S> {

    public static final int ZERO_ROWS_PER_PAGE = Integer.MIN_VALUE;

    private final Options options;
    private final long rowsPerPage;

   /**
     * Constructor that should be used for virtual scrolling
     *
     * @param id The component id
     * @param columns The table columns
     */
    public DataTables(final String id, final List<? extends IColumn<T, S>> columns) {
        this(id, columns, new EmptyDataProvider<T>(), ZERO_ROWS_PER_PAGE);
    }

    public DataTables(final String id, final List<? extends IColumn<T, S>> iColumns, final IDataProvider<T> dataProvider, final long rowsPerPage) {
        super(id, iColumns, dataProvider, rowsPerPage == ZERO_ROWS_PER_PAGE ? 1 : rowsPerPage);
        this.rowsPerPage = rowsPerPage;

        setOutputMarkupId(true);
        this.options = new Options();
        
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(newDataTablesBehavior());
    }

    protected Behavior newDataTablesBehavior() {
        return new DataTablesBehavior(options);
    }

    public Options getOptions() {
        return options;
    }

    public void repaint(AjaxRequestTarget target) {
        target.appendJavaScript(String.format("window['%s'].api().ajax.reload();", getJsHandle()));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        if (isEnabledInHierarchy()) {
            String dataTableFn = $(this).chain("dataTable", getOptions()).get();
            String setup = String.format("window['%s'] = %s", getJsHandle(), dataTableFn);
            response.render(OnDomReadyHeaderItem.forScript(setup));
        }
    }

    /**
     * @return An identifier that could be used to lookup the DataTable after initialization
     */
    public String getJsHandle() {

        // WicketStuff_DataTables_markupId
        return "WS_DT_" + Strings2.escapeMarkupId(getMarkupId());
    }

    @Override
    protected DataGridView<T> newDataGridView(final String id, final List<? extends IColumn<T, S>> iColumns, final IDataProvider<T> dataProvider) {
        return new DataGridView<T>(id, iColumns, dataProvider) {
            @Override
            public long getItemsPerPage() {
                return rowsPerPage;
            }
        };
    }
}
