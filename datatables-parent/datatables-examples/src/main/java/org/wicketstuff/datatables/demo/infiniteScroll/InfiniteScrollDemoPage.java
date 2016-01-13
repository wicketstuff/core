package org.wicketstuff.datatables.demo.infiniteScroll;

import de.agilecoders.wicket.jquery.function.JavaScriptInlineFunction;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.datatables.DataTables;
import org.wicketstuff.datatables.demo.Person;
import org.wicketstuff.datatables.demo.SpanColumn;
import org.wicketstuff.datatables.demo.SpanHeadersToolbar;
import org.wicketstuff.datatables.options.Column;
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
        columns.add(new SpanColumn<Person, String>(Model.of("First"), "firstName"));
        columns.add(new SpanColumn<Person, String>(Model.of("Last"), "lastName"));
        columns.add(new SpanColumn<Person, String>(Model.of("Age"), "age"));

        final DataTables<Person, String> table = new DataTables<Person, String>("table", columns) {

            @Override
            public void renderHead(IHeaderResponse response) {
                super.renderHead(response);

                // see rowSelector below
                response.render(OnDomReadyHeaderItem.forScript($(this).on("click", "tr", new JavaScriptInlineFunction("$(this).toggleClass('selected');")).get()));
            }
        };
        add(table);

        table.addTopToolbar(new SpanHeadersToolbar<>(table));
        table.add(new BootstrapTheme());

        CharSequence ajaxUrl = urlFor(new InfiniteScrollResourceReference(), null);

        ScrollerOptions scrollerOptions = new ScrollerOptions();
        scrollerOptions
            .loadingIndicator(true)
            .displayBuffer(100)
            .serverWait(500)
        ;

        // the column definitions needed by DataTables.js to map the Ajax response data to the
        // table columns
        List<Column> jsColumns = new ArrayList<>();
        jsColumns.add(new Column("firstName"));
        jsColumns.add(new Column("lastName"));
        jsColumns.add(new Column("age"));


        Options options = table.getOptions();
        options
            .columns(jsColumns)
            .serverSide(true)
            .ordering(false)
            .searching(true)
            .scrollY("300")
            .deferRender(true)
            .scroller(scrollerOptions)
            .ajax(ajaxUrl)
            .dom("frti") // "p" is removed because we use Scroller (virtual scrolling)
//            .scrollCollapse(true)
            .stateSave(true)
            .info(true)
            .processing(false)
        ;
    }
}
