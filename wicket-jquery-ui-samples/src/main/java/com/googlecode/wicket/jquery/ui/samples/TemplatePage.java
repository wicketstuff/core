package com.googlecode.wicket.jquery.ui.samples;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.TextTemplateResourceReference;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;

public abstract class TemplatePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	protected static final String CSS_NONE = "";
	protected static final String CSS_KENDO = "kendo";
	protected static final String CSS_JQUERY = "jquery";

	private static MetaDataKey<String> template = new MetaDataKey<String>() {
		private static final long serialVersionUID = 1L;
	};

	public TemplatePage()
	{
		super();

		TemplatePage.initTemplate();

		// buttons //
		this.add(this.newKendoButton("btnKendoUI"));
		this.add(this.newJQueryButton("btnJQueryUI"));
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.newHtmlClassBehavior());
		this.add(new GoogleAnalyticsBehavior(this));
	}

	// Methods //

	private static void initTemplate()
	{
		if (Session.get().getMetaData(template) == null)
		{
			TemplatePage.resetTemplate();
		}
	}

	protected static void resetTemplate()
	{
		TemplatePage.applyTemplate(CSS_NONE);
	}

	protected static void applyTemplate(String cssClass)
	{
		Session.get().setMetaData(template, cssClass);
	}

	// Factories //

	private Link<Void> newKendoButton(String id)
	{
		return new Link<Void>(id) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				String current = Session.get().getMetaData(template);
				Session.get().setMetaData(template, CSS_NONE.equals(current) ? CSS_KENDO : CSS_NONE);
			}
		};
	}

	private Link<Void> newJQueryButton(String id)
	{
		return new Link<Void>(id) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				String current = Session.get().getMetaData(template);
				Session.get().setMetaData(template, CSS_NONE.equals(current) ? CSS_JQUERY : CSS_NONE);
			}
		};
	}

	private JQueryAbstractBehavior newHtmlClassBehavior()
	{
		return new JQueryAbstractBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $()
			{
				return String.format("$('html').addClass('%s');", Session.get().getMetaData(template));
			}
		};
	}

	// Classes //

	static class GoogleAnalyticsBehavior extends Behavior
	{
		private static final long serialVersionUID = 1L;

		private final String url;

		public GoogleAnalyticsBehavior(final WebPage page)
		{
			this.url = GoogleAnalyticsBehavior.getUrl(page);
		}

		private IModel<Map<String, Object>> newResourceModel()
		{
			Map<String, Object> map = Generics.newHashMap();
			map.put("url", this.url);

			return Model.ofMap(map);
		}

		private ResourceReference newResourceReference()
		{
			return new TextTemplateResourceReference(GoogleAnalyticsBehavior.class, "gaq.js", this.newResourceModel());
		}

		@Override
		public void renderHead(Component component, IHeaderResponse response)
		{
			super.renderHead(component, response);

			response.render(JavaScriptHeaderItem.forReference(this.newResourceReference(), "gaq"));
		}

		private static String getUrl(WebPage page)
		{
			Url pageUrl = Url.parse(page.urlFor(page.getClass(), null).toString());
			Url baseUrl = new Url(page.getRequestCycle().getUrlRenderer().getBaseUrl());

			baseUrl.resolveRelative(pageUrl);

			return String.format("%s/%s", page.getRequest().getContextPath(), baseUrl);
		}
	}
}
