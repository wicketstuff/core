package com.googlecode.wicket.jquery.ui.samples;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.resource.TextTemplateResourceReference;

public abstract class TemplatePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	public TemplatePage()
	{
		super();

		this.add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		CharSequence url = this.urlFor(this.getClass(), null);
		this.add(new GoogleAnalyticsBehavior(url));
	}
}

class GoogleAnalyticsBehavior extends Behavior
{
	private static final long serialVersionUID = 1L;

	private final String url;

	public GoogleAnalyticsBehavior(final CharSequence url)
	{
		this.url = url.toString();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.renderJavaScriptReference(new TextTemplateResourceReference(GoogleAnalyticsBehavior.class, "gaq.js", this.getResourceModel()), "gaq");
	}

	private IModel<Map<String, Object>> getResourceModel()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(this.url)));

		return Model.ofMap(map);
	}
}
