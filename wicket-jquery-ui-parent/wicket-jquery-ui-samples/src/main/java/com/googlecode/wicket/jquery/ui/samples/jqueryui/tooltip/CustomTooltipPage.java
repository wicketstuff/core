package com.googlecode.wicket.jquery.ui.samples.jqueryui.tooltip;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;
import com.googlecode.wicket.jquery.ui.widget.tooltip.CustomTooltipBehavior;

public class CustomTooltipPage extends AbstractTooltipPage
{
	private static final long serialVersionUID = 1L;

	private static Options newOptions()
	{
		Options options = new Options();
		options.set("track", true);
		options.set("hide", "{ effect: 'drop', delay: 100 }");

		return options;
	}

	public CustomTooltipPage()
	{
		this.add(new ListView<Genre>("genres", GenresDAO.all()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Genre> item)
			{
				final Genre genre = item.getModelObject();

				Label label = new Label("genre", genre.getName());
				label.add(new CoverTooltipBehavior(genre.getName(), genre.getCover()));

				item.add(label);
			}
		});
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(CustomTooltipPage.class));
	}

	// Classes //

	class CoverTooltipBehavior extends CustomTooltipBehavior
	{
		private static final long serialVersionUID = 1L;

		private final String name;
		private final String url;

		public CoverTooltipBehavior(String name, String url)
		{
			super(newOptions());

			this.name = name;
			this.url = url;
		}

		@Override
		protected WebMarkupContainer newContent(String markupId)
		{
			Fragment fragment = new Fragment(markupId, "tooltip-fragment", CustomTooltipPage.this);
			fragment.add(new Label("name", Model.of(this.name)));
			fragment.add(new ContextImage("cover", Model.of(this.url)));

			return fragment;
		}
	}
}
