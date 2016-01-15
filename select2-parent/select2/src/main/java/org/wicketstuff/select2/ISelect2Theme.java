package org.wicketstuff.select2;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;

/**
 * Defines a select2 theme.
 *  
 */
public interface ISelect2Theme {

    void renderHead(final Component varComponent, final IHeaderResponse varResponse);
    
    String name();
}
