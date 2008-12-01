package wicket.contrib.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMapHeaderContributor;

/**
 * SimplePage for the wicket-contrib-gmap2 project
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
					target.addComponent(container);
				}
			}
		};
		add(create);

		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		// To avoid XMLHttpRequest cross-site requests
		// the GMapHeaderContributor needs to be present in a page if it
		// potentially might initialize a GMap2 component.
		// 
		container
				.add(new GMapHeaderContributor(GMapExampleApplication.get().getGoogleMapsAPIkey()));
		add(container);

		repeating = new RepeatingView("repeating");
		container.add(repeating);

		// addPanel();
	}

	protected void addPanel()
	{
		ManyPanel newPanel = new ManyPanel(repeating.newChildId(), GMapExampleApplication.get()
				.getGoogleMapsAPIkey())
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void closing(AjaxRequestTarget target)
			{
				repeating.remove(this);

				if (target != null)
				{
					target.addComponent(container);
				}
			}
		};
		repeating.add(newPanel);
	}
}