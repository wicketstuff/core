package com.googlecode.wicket.jquery.ui.samples;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.resource.ResourceUtil;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.template.PackageTextTemplate;

import com.googlecode.wicket.jquery.core.JQueryBehavior;

public abstract class SamplePage extends TemplatePage
{
	private static final long serialVersionUID = 1L;
	private enum Source { HTML, JAVA, TEXT }

	public SamplePage()
	{
		super();

		this.add(new Label("title", this.getResourceString("title")));
		this.add(new Label("source-desc", this.getSource(Source.TEXT)).setEscapeModelStrings(false));
		this.add(new Label("source-java", this.getSource(Source.JAVA)));
		this.add(new Label("source-html", this.getSource(Source.HTML)));
		this.add(new JQueryBehavior("#wrapper-panel-source", "tabs"));

		this.add(new ListView<DemoLink>("demo-list", this.getDemoLinks()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<DemoLink> item)
			{
				DemoLink object = item.getModelObject();
				Link<SamplePage> link = new BookmarkablePageLink<SamplePage>("link", object.getPage());
				link.add(new Label("label", object.getLabel()).setEscapeModelStrings(false)); //new StringResourceModel("title", ), getString("title", type)

				item.add(link);
			}

			@Override
			public boolean isVisible()
			{
				return this.getModelObject().size() > 0; //model object cannot be null
			}

		});
	}

	private String getResourceString(String string)
	{
		return this.getString(String.format("%s.%s", this.getClass().getSimpleName(), string));
	}

	private String getSource(Source source)
	{
		return this.getSource(source, this.getClass());
	}

	private String getSource(Source source, Class<? extends SamplePage> scope)
	{
		PackageTextTemplate stream = new PackageTextTemplate(scope, String.format("%s.%s", scope.getSimpleName(), source.toString().toLowerCase()));

		return ResourceUtil.readString(stream);

	}

	protected List<DemoLink> getDemoLinks()
	{
		return Collections.emptyList();
	}

	protected class DemoLink implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		private final Class<? extends SamplePage> page;
		private final String label;

		public DemoLink(Class<? extends SamplePage> page, String label)
		{
			this.page = page;
			this.label = label;
		}

		public Class<? extends SamplePage> getPage()
		{
			return this.page;
		}

		public String getLabel()
		{
			return this.label;
		}
	}
}
