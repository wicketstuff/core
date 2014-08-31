package com.googlecode.wicket.jquery.ui.samples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.resource.ResourceUtil;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.reference.ClassReference;
import org.apache.wicket.util.template.PackageTextTemplate;

import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

public abstract class SamplePage extends TemplatePage
{
	private static final long serialVersionUID = 1L;
	private enum Source { HTML, JAVA, TEXT }

	private static String getSource(Source source, Class<? extends SamplePage> scope)
	{
		PackageTextTemplate stream = new PackageTextTemplate(scope, String.format("%s.%s", scope.getSimpleName(), source.toString().toLowerCase()));
		String string = ResourceUtil.readString(stream);

		try
		{
			stream.close();
		}
		catch (IOException e) { /* not handled */ }

		return string;
	}


	public SamplePage()
	{
		super();

		this.add(new Label("title", this.getResourceString("title")));
		this.add(new Label("source-desc", this.getSource(Source.TEXT)).setEscapeModelStrings(false));
		this.add(new Label("source-java", this.getSource(Source.JAVA)));
		this.add(new Label("source-html", this.getSource(Source.HTML)));
		this.add(new JQueryUIBehavior("#wrapper-panel-source", "tabs"));
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(new ListView<DemoLink>("demo-list", Model.ofList(this.getDemoLinks())) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<DemoLink> item)
			{
				DemoLink object = item.getModelObject();
				Link<SamplePage> link = new BookmarkablePageLink<SamplePage>("link", object.getPage());
				link.add(new Label("label", object.getLabel()).setEscapeModelStrings(false));

				item.add(link);
			}

			@Override
			public boolean isVisible()
			{
				return !this.getModelObject().isEmpty(); //model object cannot be null
			}

		});
	}

	private String getResourceString(String string)
	{
		return this.getString(String.format("%s.%s", this.getClass().getSimpleName(), string));
	}

	private String getSource(Source source)
	{
		return SamplePage.getSource(source, this.getClass());
	}

	protected List<? extends DemoLink> getDemoLinks()
	{
		return Collections.emptyList();
	}

	protected static class DemoLink implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		private final ClassReference<? extends SamplePage> reference;
		private final String label;

		public DemoLink(Class<? extends SamplePage> page, String label)
		{
			this.reference = ClassReference.of(page);
			this.label = label;
		}

		public Class<? extends SamplePage> getPage()
		{
			return this.reference.get();
		}

		public String getLabel()
		{
			return this.label;
		}
	}
}
