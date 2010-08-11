package org.wicketstuff.objectautocomplete;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.WebResponse;

import java.util.Iterator;

/**
 * A renderer, which has complete access to the list of choices and which is free to create a (more structured)
 * drop down list. It is the responsibility of this renderer to add &lt;li&gt; elements with proper <code>idvalue</code>
 * and <code>textvalues</code> attributes reflecting
 * @author roland
 * @since Sep 26, 2008
 */
public abstract class ObjectAutoCompleteResponseRenderer<O> extends AbstractObjectAutoCompleteRenderer<O> {

    /**
     * Render the provided components on the given response
     *
     * @param pComps the components to render
     * @param pResponse response to write to
     * @param pInput input search string
     */
    abstract public void onRequest(Iterator<O> pComps, WebResponse pResponse, String pInput);
}
