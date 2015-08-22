package org.wicketstuff.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMapHeaderContributor;

/**
 * Demonstrates how to use several maps on one page.
 */
public class ManyPage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;
    private final WebMarkupContainer container;
    private final RepeatingView repeating;

    public ManyPage()
    {
        AjaxFallbackLink<Void> create = new AjaxFallbackLink<Void>("create")
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                ManyPage.this.addPanel();

                if (target != null)
                {
                    target.add(container);
                }
            }
        };
        add(create);

        container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        // To avoid XMLHttpRequest cross-site requests
        // the GMapHeaderContributor needs to be present in a page if it
        // potentially might initialize a GMap component.
        // 
        container.add(new GMapHeaderContributor());
        add(container);

        repeating = new RepeatingView("repeating");
        container.add(repeating);

        addPanel();
    }

    protected void addPanel()
    {
        ManyPanel newPanel = new ManyPanel(repeating.newChildId())
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void closing(AjaxRequestTarget target)
            {
                repeating.remove(this);

                if (target != null)
                {
                    target.add(container);
                }
            }
        };
        repeating.add(newPanel);
    }
}