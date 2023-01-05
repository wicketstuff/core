package org.wicketstuff;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.mock.MockApplication;

import java.util.Map;

/**
 * @author Vineet Semwal
 */
public class QuickMockApplication extends MockApplication {
    private AjaxRequestTarget lastAjaxRequestTarget;

    public AjaxRequestTarget getLastAjaxRequestTarget() {
        return lastAjaxRequestTarget;
    }

    @Override
    protected void init() {
        super.init();
        new RepeaterUtil(this);
        AjaxRequestTarget.IListener listener = new AjaxRequestTarget.IListener() {
            @Override
            public void updateAjaxAttributes(AbstractDefaultAjaxBehavior behavior, AjaxRequestAttributes attributes) {

            }

            @Override
            public void onBeforeRespond(Map<String, Component> map, AjaxRequestTarget target) {
                lastAjaxRequestTarget = target;
            }

            @Override
            public void onAfterRespond(Map<String, Component> map, AjaxRequestTarget response) {
            }
        };
        getAjaxRequestTargetListeners().add(listener);

    }

}
