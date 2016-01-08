package org.wicketstuff.datatables.demo.infiniteScroll;

import de.agilecoders.wicket.jquery.function.JavaScriptInlineFunction;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.datatables.DataTables;
import org.wicketstuff.datatables.demo.PeopleDataProvider;
import org.wicketstuff.datatables.demo.Person;
import org.wicketstuff.datatables.demo.SpanHeadersToolbar;
import org.wicketstuff.datatables.options.Options;
import org.wicketstuff.datatables.options.ScrollerOptions;
import org.wicketstuff.datatables.themes.BootstrapTheme;

import java.util.ArrayList;
import java.util.List;

import static de.agilecoders.wicket.jquery.JQuery.$;

/**
 *
 */
public class InfiniteScrollDemoPage extends WebPage {

    public InfiniteScrollDemoPage(PageParameters parameters) {
        super(parameters);

        List<IColumn<Person, String>> columns = new ArrayList<>();
        columns.add(new PropertyColumn<Person, String>(Model.of("Nr."), "number"));
        columns.add(new PropertyColumn<Person, String>(Model.of("First"), "firstName", "firstName"));
        columns.add(new PropertyColumn<Person, String>(Model.of("Last"), "lastName", "lastName"));
        columns.add(new PropertyColumn<Person, String>(Model.of("Age"), "age", "age"));

        PeopleDataProvider dataProvider = new PeopleDataProvider();

        final DataTables<Person, String> table = new DataTables<Person, String>("table", columns, dataProvider, 1) {

            protected DataGridView<Person> newDataGridView(String id, List<? extends IColumn<Person, String>> columns, IDataProvider<Person> dataProvider)
            {
                return new DataGridView<Person>(id, columns, dataProvider) {
//                    @Override
//                    protected Item<Person> newRowItem(final String id, final int index, final IModel<Person> model)
//                    {
//                        return DataTables.this.newRowItem(id, index, model);
//                    }

                    @Override
                    public long getItemsPerPage() {
                        return 0;
                    }
                };
            }

            @Override
            public void renderHead(IHeaderResponse response) {
                super.renderHead(response);

                // see rowSelector below
                response.render(OnDomReadyHeaderItem.forScript($(this).on("click", "tr", new JavaScriptInlineFunction("$(this).toggleClass('selected');")).get()));
            }
        };
        add(table);

        table.addTopToolbar(new SpanHeadersToolbar<>(table));
//        table.addTopToolbar(new HeadersToolbar<String>(table, dataProvider));

        table.add(new BootstrapTheme());

        CharSequence ajaxUrl = urlFor(new InfiniteScrollResourceReference(), null);

        ScrollerOptions scrollerOptions = new ScrollerOptions();
        scrollerOptions
            .loadingIndicator(true)
            .displayBuffer(100)
            .serverWait(500)
        ;

        Options options = table.getOptions();
        options
            .serverSide(true)
            .ordering(false)
            .searching(false)
            .scrollY("200")
            .deferRender(false)
            .scroller(scrollerOptions)
            .ajax(ajaxUrl)
            .dom("frti")
//            .scrollCollapse(true)
//            .stateSave(true)
            .info(true)
            .processing(true)
        ;
    }
}
