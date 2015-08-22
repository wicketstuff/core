package org.wicketstuff.jquery.lavalamp;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

@SuppressWarnings("serial")
public abstract class LavaLampMenuPanel extends Panel
{
	private final static String UL_CSS_CLASS = "lavaLamp";

	public LavaLampMenuPanel(String id, final List<MenuItem> linksList)
	{
		this(id, new AbstractReadOnlyModel<List<MenuItem>>()
		{
			@Override
			public List<MenuItem> getObject()
			{
				return linksList;
			}
		});
	}

	public LavaLampMenuPanel(String id, IModel<List<MenuItem>> linksModel)
	{
		super(id);
		// Add JQuery JS libraries
		add(new JQueryLavaLampBehavior());

		final WebMarkupContainer listContainer = new WebMarkupContainer("list");
		listContainer.add(new AttributeAppender("class", Model.of(UL_CSS_CLASS), " "));
		final ListView<MenuItem> lv = new ListView<MenuItem>("lavaLampMenu", linksModel)
		{
			@Override
			protected void populateItem(ListItem<MenuItem> item)
			{
				item.add(item.getModelObject());
			}
		};
		listContainer.add(lv);
		add(listContainer);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(LavaLampMenuPanel.class,
			"headlamp.js")));
		response.render(CssHeaderItem.forReference(getCssResourceReference()));
	}

	/**
	 * This method should return the reference for the CSS that will be used.
	 * 
	 * @return ResourceReference
	 */
	protected abstract ResourceReference getCssResourceReference();

}
