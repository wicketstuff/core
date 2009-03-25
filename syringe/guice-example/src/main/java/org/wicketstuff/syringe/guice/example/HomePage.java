package org.wicketstuff.syringe.guice.example;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.syringe.Inject;

/**
 * @since 1.4
 */
public class HomePage extends WebPage
{
    private static final long serialVersionUID = 1L;

    @Inject(name="messageService")
    private IMessageService messageService;

    public HomePage(final PageParameters parameters)
    {
        final Label label = new Label("message", new LoadableDetachableModel<String>()
        {
            protected String load()
            {
                return messageService.getMessage();
            }
        } );
        label.setOutputMarkupId(true);
        add(label);
        final AjaxLink refreshLink = new AjaxLink("refresh")
        {
            public void onClick(AjaxRequestTarget target)
            {
                target.addComponent(label);
            }
        };
        add(refreshLink);
    }
}
