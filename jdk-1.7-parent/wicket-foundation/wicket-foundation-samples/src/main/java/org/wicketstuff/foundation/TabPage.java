package org.wicketstuff.foundation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.wicketstuff.foundation.tab.FoundationTab;

public class TabPage extends BasePage
{
	public TabPage(PageParameters params)
	{
		super(params);

		List<ITab> tabs = new ArrayList<>();

		tabs.add(new AbstractTab(Model.of("title 1"))
		{

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new TextualPanel(panelId, Model.of("Content 1"));
			}
		});

		tabs.add(new AbstractTab(Model.of("title 2"))
		{

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new TextualPanel(panelId, Model.of("Content 2"));
			}
		});
		
		tabs.add(new AbstractTab(Model.of("title 3"))
		{

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new TextualPanel(panelId, Model.of("Content 3"));
			}
		});

		add(new FoundationTab<>("ajaxTab", tabs).setVerticalTab(true));
	}
	
	class ContainerString extends WebMarkupContainer implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider
	{

		public ContainerString(String id, IModel<String> model)
		{
			super(id, model);
			setOutputMarkupId(true);
		}

		@Override
		public IResourceStream getMarkupResourceStream(MarkupContainer container,
			Class<?> containerClass)
		{
			return new StringResourceStream("<div>" + getDefaultModelObjectAsString() + "</div>");
		}

		@Override
		public String getCacheKey(MarkupContainer container, Class<?> containerClass)
		{
			return null;
		}
	}
}
