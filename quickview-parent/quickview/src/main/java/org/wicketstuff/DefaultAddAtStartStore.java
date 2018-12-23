package org.wicketstuff;

import org.apache.wicket.Component;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * default store for elements added at start in quickview
 */
public class DefaultAddAtStartStore implements IAddAtStartStore {

    private ConcurrentLinkedDeque<String> orderedIds = new ConcurrentLinkedDeque<>();

    public ConcurrentLinkedDeque<String> getOrderedIds() {
        return orderedIds;
    }

    private Set<String>ids=new HashSet<>();

    public Set<String>getIds(){
        return ids;
    }

    @Override
    public boolean contains(final Component component) {
       return contains(component.getId());
    }

    @Override
    public boolean contains(final String componentId) {
        boolean contains = getIds().contains(componentId);
        return contains;
    }


    @Override
    public Iterator<String> iterator() {
        return getOrderedIds().iterator();
    }


    @Override
    public int size() {
        return getIds().size();
    }

    /**
     * adds components id in store along with their indexes
     *
     * @param components component that are added at start
     */
    @Override
    public void add(final Component... components) {
        for (int i = components.length-1; i >=0; i--) {
            Component c = components[i];
            getOrderedIds().addFirst(c.getId());
            getIds().add(c.getId());
        }
    }


    /**
     * remove component id from store
     *
     * @param component
     */
    @Override
    public void remove(final Component component) {
        remove(component.getId());
    }

    /**
     * remove component id from store
     *
     * @param id
     */
    @Override
    public void remove(final String id) {
        getIds().remove(id);
        getOrderedIds().remove(id);
    }



}
