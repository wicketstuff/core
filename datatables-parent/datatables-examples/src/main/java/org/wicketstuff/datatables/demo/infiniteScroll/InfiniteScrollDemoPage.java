package org.wicketstuff.datatables.demo.infiniteScroll;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.resource.CoreLibrariesContributor;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.datatables.DataTables;
import org.wicketstuff.datatables.columns.SpanColumn;
import org.wicketstuff.datatables.columns.SpanHeadersToolbar;
import org.wicketstuff.datatables.demo.Person;
import org.wicketstuff.datatables.options.Column;
import org.wicketstuff.datatables.options.Options;
import org.wicketstuff.datatables.options.ScrollerOptions;
import org.wicketstuff.datatables.options.SelectOptions;
import org.wicketstuff.datatables.themes.BootstrapTheme;

import java.util.ArrayList;
import java.util.List;

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

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);
        feedback.setOutputMarkupId(true);

        final AjaxEventBehavior selectBehavior = new AjaxEventBehavior("select.dt") {
            @Override
            protected void onEvent(final AjaxRequestTarget target) {
                Request request = RequestCycle.get().getRequest();
                List<StringValue> values = request.getRequestParameters().getParameterValues("id");
                info("Selected: " + values);
                target.add(feedback);
            }

            @Override
            protected void updateAjaxAttributes(final AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);

                attributes.getDynamicExtraParameters().add("var arr=[]; dt.rows(indexes).every(function() {arr=arr.concat({\"name\":\"id\","
                                                           + "\"value\": $(this.node()).attr('id')})}); return arr;");
            }

            @Override
            public void renderHead(final Component component, final IHeaderResponse response) {
                // do not contribute the default
            }
        };

        final DataTables<Person, String> table = new DataTables<Person, String>("table", columns) {
            @Override
            public void renderHead(IHeaderResponse response) {
                super.renderHead(response);

                CoreLibrariesContributor.contributeAjax(getApplication(), response);

                CallbackParameter evt = CallbackParameter.explicit("evt");
                CallbackParameter dt = CallbackParameter.explicit("dt");
                CallbackParameter type = CallbackParameter.explicit("type");
                CallbackParameter indexes = CallbackParameter.explicit("indexes");
                String callbackFunction = selectBehavior.getCallbackFunction(evt, dt, type, indexes).toString();
                response.render(OnDomReadyHeaderItem.forScript(String.format("$('#%s').on('select.dt', %s)", getMarkupId(), callbackFunction)));
            }
        };
        add(table);
        table.add(selectBehavior);

        table.addTopToolbar(new SpanHeadersToolbar<>(table));

        CharSequence ajaxUrl = urlFor(new VirtualScrollDemoResourceReference(), null);

        ScrollerOptions scrollerOptions = new ScrollerOptions();
        scrollerOptions
            .loadingIndicator(true)
            .displayBuffer(100)
            .serverWait(500)
        ;

        SelectOptions selectOptions = new SelectOptions()
                .style(SelectOptions.Style.OS);

        // the column definitions needed by DataTables.js to map the Ajax response data to the
        // table columns
        List<Column> jsColumns = new ArrayList<>();
        jsColumns.add(new Column("firstName"));
        jsColumns.add(new Column("lastName"));
        jsColumns.add(new Column("age"));


        Options options = table.getOptions();
        table.add(new BootstrapTheme(options));
        options
            .columns(jsColumns)
            .select(selectOptions)
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
            .retrieve(true)
        ;
    }
}
