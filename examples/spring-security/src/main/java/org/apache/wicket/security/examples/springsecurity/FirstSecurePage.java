package org.apache.wicket.security.examples.springsecurity;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.security.components.SecureWebPage;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;
import org.apache.wicket.security.components.markup.html.links.SecureAjaxLink;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.swarm.strategies.SwarmStrategyFactory;
import org.apache.wicket.markup.html.basic.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * First Secure Page
 *
 * @author Olger Warnier
 */
public class FirstSecurePage extends SecureWebPage {

	private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(FirstSecurePage.class);


    /**
	 * Constructor that is invoked when page is invoked without a session.
	 *
	 * @param parameters
	 *            Page parameters
	 */
    public FirstSecurePage(final PageParameters parameters) {
        // how to get to the subjects in order to get the user details ????
        //((SwarmStrategyFactory)((SpringSecureWicketApplication)getApplication()).getStrategyFactory()).

        // session seems the best way to get to the subject. 
        //WaspSession waspSession = ((WaspSession)getSession());


        add(new Label("message", "This is the first secure page"));

        SecureAjaxLink ajaxLink = new SecureAjaxLink("ajaxLink") {

            public void onClick(AjaxRequestTarget target) {
                logger.debug("Click !");
            }
        };

        add(ajaxLink);
        add(new SecurePageLink("to_secondsecurepage", SecondSecurePage.class));
        add(new SecurePageLink("to_homepage", HomePage.class));
    }
}