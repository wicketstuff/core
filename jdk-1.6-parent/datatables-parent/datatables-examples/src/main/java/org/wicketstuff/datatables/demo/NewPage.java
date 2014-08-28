package org.wicketstuff.datatables.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.datatables.DataTables;
import org.wicketstuff.datatables.themes.BootstrapTheme;

/**
 *
 */
public class NewPage extends WebPage {

    public NewPage(PageParameters parameters) {
        super(parameters);

        List<IColumn<Person, String>> columns = new ArrayList<IColumn<Person, String>>();
        columns.add(new PropertyColumn<Person, String>(Model.of("First name"), "firstName", "firstName"));
        columns.add(new PropertyColumn<Person, String>(Model.of("Last name"), "lastName", "lastName"));
        columns.add(new PropertyColumn<Person, String>(Model.of("Age"), "age", "age"));

        List<Person> people = new ArrayList<Person>();
        people.add(new Person("John", "Doe", 32));
        people.add(new Person("Jane", "Doe", 29));
        people.add(new Person("Johnny", "Doe", 3));

        PeopleDataProvider dataProvider = new PeopleDataProvider(people);

        DataTables<Person, String> table = new DataTables<Person, String>("table", columns, dataProvider, 4);
        add(table);

        table.addTopToolbar(new HeadersToolbar<String>(table, dataProvider));

        table.add(new BootstrapTheme());
    }

    private static class Person implements Serializable {
        private String firstName;
        private String lastName;
        private int age;

        private Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }
    }
    
    private static class PeopleDataProvider extends SortableDataProvider<Person, String> {

        private final List<Person> people;
        
        private PeopleDataProvider(List<Person> people) {
            this.people = people;
        }
        
        @Override
        public Iterator<? extends Person> iterator(long first, long count) {
            return people.subList((int)first, (int)count).iterator();
        }

        @Override
        public long size() {
            return people.size();
        }

        @Override
        public IModel<Person> model(Person person) {
            return Model.of(person);
        }
    }
}
