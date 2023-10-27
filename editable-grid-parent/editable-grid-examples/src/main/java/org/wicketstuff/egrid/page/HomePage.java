package org.wicketstuff.egrid.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.egrid.EditableGrid;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.CheckBoxColumn;
import org.wicketstuff.egrid.column.RequiredDropDownColumn;
import org.wicketstuff.egrid.column.RequiredTextFieldColumn;
import org.wicketstuff.egrid.model.Person;
import org.wicketstuff.egrid.provider.EditableListDataProvider;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends WebPage {
    @Serial
    private static final long serialVersionUID = 1L;

    private final FeedbackPanel feedbackPanel;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        feedbackPanel = new FeedbackPanel("feedBack");
        feedbackPanel.setOutputMarkupPlaceholderTag(true);

        add(feedbackPanel);

        add(new EditableGrid<Person, String>("grid", getColumns(), new EditableListDataProvider<Person>(getPersons(), "name"), 5, Person.class) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onError(final AjaxRequestTarget target, final IModel<Person> rowModel) {
                super.onError(target, rowModel);
                target.add(feedbackPanel);
            }

            @Override
            protected void onCancel(final AjaxRequestTarget target, final IModel<Person> rowModel) {
                super.onCancel(target, rowModel);
                target.add(feedbackPanel);
            }

            @Override
            protected void onDelete(final AjaxRequestTarget target, final IModel<Person> rowModel) {
                super.onDelete(target, rowModel);
                target.add(feedbackPanel);
            }

            @Override
            protected void onSave(final AjaxRequestTarget target, final IModel<Person> rowModel) {
                super.onSave(target, rowModel);
                target.add(feedbackPanel);
            }

            @Override
            protected void onAdd(final AjaxRequestTarget target, final IModel<Person> rowModel) {
                super.onAdd(target, rowModel);
                target.add(feedbackPanel);
            }

        });
    }

    private List<AbstractEditablePropertyColumn<Person, String>> getColumns() {
        List<AbstractEditablePropertyColumn<Person, String>> columns = new ArrayList<>();
        columns.add(new RequiredTextFieldColumn<Person, String>(new Model<String>("Name"), "name", "name"));
        columns.add(new RequiredTextFieldColumn<Person, String>(new Model<String>("Address"), "address"));
        columns.add(new RequiredDropDownColumn<Person, Integer, String>(new Model<String>("Age"), "age", List.of(10, 11, 12, 13, 14, 15), "age"));
        columns.add(new CheckBoxColumn<Person, String>(new Model<String>("Married?"), "married"));
        return columns;
    }

    private List<Person> getPersons() {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("Person1", 12, "Address1", true));
        persons.add(new Person("Person2", 13, "Address2", false));
        persons.add(new Person("Person3", 13, "Address3", false));
        persons.add(new Person("Person4", 13, "Address4", false));
        persons.add(new Person("Person5", 13, "Address5", true));
        persons.add(new Person("Person6", 13, "Address6", true));
        return persons;
    }
}
