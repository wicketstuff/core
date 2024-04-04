package org.wicketstuff.datatables.demo;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
*
*/
public class PeopleDataProvider extends SortableDataProvider<Person, String> {

    private static final String[] FIRST_NAMES = {"John", "Jane", "Johnny", "Peter", "Nicolas", "Mary"};
    private static final String[] LAST_NAMES = {"Doe", "Smith", "Brown", "Anderson", "O'Tool", "Popins"};

    private final String searchFilter;

    public PeopleDataProvider(String searchFilter) {
        this.searchFilter = searchFilter != null ? searchFilter.toLowerCase() : null;
    }

    @Override
    public Iterator<? extends Person> iterator(long first, long count) {
        List<Person> people = new ArrayList<>();
        Random random = new Random(123);

        for (int i = 0; i < FIRST_NAMES.length * LAST_NAMES.length; i++) {
            int randomFirst = random.nextInt(FIRST_NAMES.length);
            int randomLast = random.nextInt(LAST_NAMES.length);
            int randomAge = random.nextInt(99);
            final String firstName = FIRST_NAMES[randomFirst];
            final String lastName = LAST_NAMES[randomLast];
            if (searchFilter == null || (firstName.toLowerCase().contains(searchFilter) || lastName.toLowerCase().contains(searchFilter) )) {
                people.add(new Person(firstName, lastName, randomAge, first + i));
            }
            if (people.size() >= count) {
            	break;
            }
        }
        return people.iterator();
    }

    @Override
    public long size() {
        return FIRST_NAMES.length * LAST_NAMES.length;
    }

    @Override
    public IModel<Person> model(Person person) {
        return Model.of(person);
    }
}
