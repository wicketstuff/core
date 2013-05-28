package org.wicketstuff.stateless;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author jfk
 * 
 */
public abstract class StatelessAjaxFormComponentUpdatingBehavior 
    extends AjaxFormComponentUpdatingBehavior
{

    private static final long serialVersionUID = -286307141298283926L;

    /**
     * @param event
     */
    public StatelessAjaxFormComponentUpdatingBehavior(final String event)
    {
        super(event);
    }

    @Override
    public CharSequence getCallbackUrl()
    {
        final Url url = Url.parse(super.getCallbackUrl().toString());
        final PageParameters params = getPageParameters();

        return StatelessEncoder.mergeParameters(url, params).toString();
    }

    protected abstract PageParameters getPageParameters();

    /**
     * @return always {@literal true}
     */
    @Override
    public boolean getStatelessHint(final Component component)
    {
        return true;
    }
}
