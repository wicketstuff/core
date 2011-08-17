package org.wicketstuff.browserid;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class LoggedInPanel extends Panel {

    public LoggedInPanel(String id) {
        super(id);

        BrowserId browserId = SessionHelper.getBrowserId(getSession());
        if (browserId == null) {
            throw new IllegalStateException("The user must be authenticated!");
        }

        add(new Label("emailLabel", new PropertyModel<String>(browserId, "email")));
        add(new AjaxLink<Void>("logoutLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                SessionHelper.logOut(getSession());
                onLoggedOut(target);
            }
        });
    }

    protected void onLoggedOut(AjaxRequestTarget target) {

    }

}
