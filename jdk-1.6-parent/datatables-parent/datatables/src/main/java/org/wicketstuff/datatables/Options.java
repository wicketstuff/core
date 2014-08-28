package org.wicketstuff.datatables;

import de.agilecoders.wicket.jquery.AbstractConfig;
import de.agilecoders.wicket.jquery.IKey;
import de.agilecoders.wicket.jquery.Key;

/**
 *
 */
public class Options extends AbstractConfig {

    private static final IKey<Sort[]> Order = new Key<Sort[]>("order", null);

    public Options order(Sort... sortParams) {
        put(Order, sortParams);
        return this;
    }
}
