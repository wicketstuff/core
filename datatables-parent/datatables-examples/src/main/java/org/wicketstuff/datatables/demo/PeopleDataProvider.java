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

    public PeopleDataProvider() {
    }

    @Override
    public Iterator<? extends Person> iterator(long first, long count) {
        List<Person> people = new ArrayList<>();
        Random random = new Random(123);

        for (int i = 0; i < count; i++) {
            int randomFirst = random.nextInt(FIRST_NAMES.length);
            int randomLast = random.nextInt(LAST_NAMES.length);
            int randomAge = random.nextInt(99);
            people.add(new Person(FIRST_NAMES[randomFirst], LAST_NAMES[randomLast], randomAge, first + i));
        }
        return people.iterator();
    }

    @Override
    public long size() {
        return FIRST_NAMES.length;
    }

    @Override
    public IModel<Person> model(Person person) {
        return Model.of(person);
    }
}
