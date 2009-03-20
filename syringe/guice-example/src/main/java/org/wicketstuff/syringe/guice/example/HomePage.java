package org.wicketstuff.syringe.guice.example;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.syringe.Inject;

/**
 * @since 1.4
 */
public class HomePage extends WebPage
{
    private static final long serialVersionUID = 1L;

    @Inject
    private IMessageService messageService;

    public HomePage(final PageParameters parameters)
    {
        add(new Label("message", messageService.getMessage()));
    }
}
