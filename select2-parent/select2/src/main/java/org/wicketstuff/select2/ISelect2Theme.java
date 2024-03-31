package org.wicketstuff.select2;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;

import java.io.Serializable;

/**
 * Defines a select2 theme.
 *  
 */
public interface ISelect2Theme extends Serializable {

    /**
     * Allows theme to contribute headers (e.g. extra CSS resources) 
     *  
     * @param component The component
     * @param response The header response
     */
    void renderHead(final Component component, final IHeaderResponse response);

    /**
     *
     * @return The name of the theme.
     */
    String name();
}
