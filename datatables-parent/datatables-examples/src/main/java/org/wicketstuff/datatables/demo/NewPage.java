package org.wicketstuff.datatables.demo;

import de.agilecoders.wicket.jquery.function.JavaScriptInlineFunction;
import de.agilecoders.wicket.jquery.util.Json;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.datatables.DataTables;
import org.wicketstuff.datatables.columns.SpanColumn;
import org.wicketstuff.datatables.columns.SpanHeadersToolbar;
import org.wicketstuff.datatables.columns.SpanPropertyColumn;
import org.wicketstuff.datatables.options.Options;
import org.wicketstuff.datatables.Sort;
import org.wicketstuff.datatables.themes.BootstrapTheme;

import java.util.ArrayList;
import java.util.List;

import static de.agilecoders.wicket.jquery.JQuery.$;

/**
 *
 */
public class NewPage extends WebPage {

    public NewPage(PageParameters parameters) {
        super(parameters);

        List<IColumn<Person, String>> columns = new ArrayList<>();
        columns.add(new PropertyColumn<Person, String>(Model.of("First"), "firstName", "firstName"));
        columns.add(new PropertyColumn<Person, String>(Model.of("Last"), "lastName", "lastName"));
        columns.add(new SpanPropertyColumn<Person, String>(Model.of("Age"), "age", "age") {
            @Override
            public int getRowspan() {
                return 2;
            }
        });

        PeopleDataProvider dataProvider = new PeopleDataProvider();

        final DataTables<Person, String> table = new DataTables<Person, String>("table", columns, dataProvider, 10) {
            @Override
            public void renderHead(IHeaderResponse response) {
                super.renderHead(response);

                // see rowSelector below
                response.render(OnDomReadyHeaderItem.forScript($(this).on("click", "tr", new JavaScriptInlineFunction("$(this).toggleClass('selected');")).get()));
            }
        };
        add(table);

        SpanColumn<Person, String> namesColumn = new SpanColumn<Person, String>(Model.of("Names"), null) {
            @Override
            public int getColspan() {
                return 2;
            }
        };
        table.addTopToolbar(new SpanHeadersToolbar<>(table, namesColumn));
        table.addTopToolbar(new SpanHeadersToolbar<>(table));
//        table.addTopToolbar(new HeadersToolbar<String>(table, dataProvider));


        Options options = table.getOptions();
        table.add(new BootstrapTheme(options));
        options.order(new Sort(2, Sort.Direction.ASC)); // single column ordering
//        table.getOptions().order(new Sort(2, Sort.Direction.DESC), new Sort(0, Sort.Direction.ASC)); // multi column ordering
        options
            .stateDuration(3600)
            .stateSave(true)
            .pagingType(Options.PagingType.simple)

//            .paging(false)
//            .scrollY("350px")
//            .scrollCollapse(true)

//            .scrollX(true)

            // highlights the second row
            // recommendation: load the String with PackageTextTemplate
            .rowCallback(new Json.RawValue("function(row, data, displayIndex) {if(displayIndex == 1) {$(row).addClass('selected')}}"))

             // makes the age in bold and underlined
             // recommendation: load the String with PackageTextTemplate
            .createdRow(new Json.RawValue("function(row, data, displayIndex) { $('td', row).eq(2).wrapInner('<b><u></u></b>');}"))

//            .lengthMenu(new Integer[]{1,2,5,10}, new String[]{"One", "Two", "Five", "Ten"})
        ;
    }

}
