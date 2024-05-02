package com.googlecode.wicket.jquery.ui.samples.kendoui.datatable;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;
import com.googlecode.wicket.jquery.ui.samples.data.provider.ProductDataProvider;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.column.CheckboxColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.CurrencyPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IdPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class CheckboxDataTablePage extends AbstractDataTablePage 
{
    private static final long serialVersionUID = 1L;
    
    public CheckboxDataTablePage() 
    {
        // FeedbackPanel //
        final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
        this.add(feedback);

        // Form //
        final Form<?> form = new Form<Void>("form");
        this.add(form);

        // DataTable //
        Options options = new Options();
        options.set("height", 430);
        options.set("pageable", "{ pageSizes: [ 25, 50, 100 ] }");
        options.set("columnMenu", true);
        options.set("persistSelection", true); // keep the selection when changing pages
        options.set("selectable", false); // not recommended to have both 'selectable' option and a CheckboxColumn

        final DataTable<Product> table = new DataTable<Product>("datatable", newColumnList(), newDataProvider(), 25, options) {

			private static final long serialVersionUID = 1L;

			@Override
            public void onChecked(AjaxRequestTarget target, List<String> selectedKeys)
            {
                final String message = "Selected keys: " + selectedKeys.toString();

                feedback.info(message);
                feedback.refresh(target);
            }
        };
        
        form.add(table);

        // Buttons //
        form.add(new AjaxButton("reload") 
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target)
            {
                table.reload(target, true);
            }
        });

        form.add(new AjaxButton("refresh") 
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target)
            {
                table.refresh(target);
            }
        });
    }

    private static IDataProvider<Product> newDataProvider()
    {
        return new ProductDataProvider();
    }

    private static List<IColumn> newColumnList()
    {
        List<IColumn> columns = Generics.newArrayList();

        /* 
         * An IdPropertyColumn is mandatory for the checkbox selection to work properly.
         * If you don't want to display it just override the isVisible() method to always return false .
         */
        columns.add(new IdPropertyColumn("ID", "id", 50));
        columns.add(new PropertyColumn("Name", "name"));
        columns.add(new PropertyColumn("Description", "description"));
        columns.add(new CurrencyPropertyColumn("Price", "price", 70));
        columns.add(new PropertyColumn("Vendor", "vendor.name"));
        columns.add(new CheckboxColumn());

        return columns;
    }
}
