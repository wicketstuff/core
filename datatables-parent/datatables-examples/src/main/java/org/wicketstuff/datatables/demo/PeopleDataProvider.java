package org.wicketstuff.datatables.demo;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;
import java.util.List;

/**
*
*/
class PeopleDataProvider extends SortableDataProvider<Person, String> {

    private final List<Person> people;

    PeopleDataProvider(List<Person> people) {
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
