package com.googlecode.wicket.jquery.ui.samples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.resource.ResourceUtil;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.lang.Generics;
import org.apache.wicket.util.reference.ClassReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SamplePage extends TemplatePage // NOSONAR
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SamplePage.class);

	protected enum Source
	{
		HTML, JAVA, TEXT
	}

	public SamplePage()
	{
		this.add(new Label("title", this.getResourceString("title")));
		this.add(new Label("source-desc", this.getSource(Source.TEXT)).setEscapeModelStrings(false));
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(new ListView<DemoLink>("demo-list", Model.ofList(this.getDemoLinks())) { // NOSONAR

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
				return !this.getModelObject().isEmpty(); // model object cannot be null
			}
		});
	}

	private String getResourceString(String string)
	{
		return this.getString(String.format("%s.%s", this.getClass().getSimpleName(), string));
	}

	protected IModel<String> getSource(Source source)
	{
		return Model.of(SamplePage.getSource(source, this.getClass()));
	}

	protected List<DemoLink> getDemoLinks()
	{
		return Collections.emptyList();
	}

	private static String getSource(Source source, Class<? extends SamplePage> scope)
	{
		final String filename = String.format("%s.%s", scope.getSimpleName(), source.toString().toLowerCase());

		try (PackageTextTemplate stream = new PackageTextTemplate(scope, filename))
		{
			return ResourceUtil.readString(stream);
		}
		catch (IOException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug(e.getMessage(), e);
			}
		}

		return "";
	}

	protected final List<ITab> newSourceTabList()
	{
		List<ITab> tabs = Generics.newArrayList(2);

		tabs.add(this.newJavaAjaxTab());
		tabs.add(this.newHtmlAjaxTab());

		return tabs;
	}

	private ITab newJavaAjaxTab()
	{
		return new AbstractTab(Model.of("Java")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new JavaFragment(panelId, SamplePage.this, getSource(Source.JAVA));
			}
		};
	}

	private ITab newHtmlAjaxTab()
	{
		return new AbstractTab(Model.of("HTML")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new HtmlFragment(panelId, SamplePage.this, getSource(Source.HTML));
			}
		};
	}

	// Classes //

	protected static class JavaFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public JavaFragment(String id, MarkupContainer provider, IModel<String> model)
		{
			super(id, "fragment-java", provider);

			this.add(new Label("code", model));
		}
	}

	protected static class HtmlFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public HtmlFragment(String id, MarkupContainer provider, IModel<String> model)
		{
			super(id, "fragment-html", provider);

			this.add(new Label("code", model));
		}
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
