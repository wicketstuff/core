package org.wicketstuff.urlfragment.example.asyncpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.urlfragment.BookmarkableAjaxButton;
import org.wicketstuff.urlfragment.BookmarkableAjaxLink;

public class ContentPanel extends Panel
{

	private final StringValue sorting;
	private final IModel<String> amount = Model.of("10000");

	public ContentPanel(String id, IRequestParameters requestParameters)
	{
		super(id);
		sorting = requestParameters.getParameterValue("sorting");
	}

	public ContentPanel(String id, PageParameters requestParameters)
	{
		super(id);
		sorting = requestParameters.get("sorting");
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		final IModel<String> sortingModel = Model.of(sorting == null ? "" : sorting.toString());

		final Label sortingLabel = new Label("sorting", sortingModel);
		sortingLabel.setOutputMarkupId(true);
		add(sortingLabel);

		add(new BookmarkableAjaxLink<Void>("zins_asc")
		{

			@Override
			public void onBookmarkableClick(AjaxRequestTarget target)
			{
				sortingModel.setObject("zins_asc");
				addFragmentParameter("sorting", "zins_asc", "|");
				target.add(sortingLabel);
			}

		});

		add(new BookmarkableAjaxLink<Void>("zins_desc", "sorting", "zins_desc")
		{

			@Override
			public void onBookmarkableClick(AjaxRequestTarget target)
			{
				sortingModel.setObject("zins_desc");
				target.add(sortingLabel);
			}

		});

		final Form<String> form = new StatelessForm<String>("form");
		form.setOutputMarkupId(true);
		form.add(new TextField<String>("text", amount));
		form.add(new BookmarkableAjaxButton("submit")
		{

			@Override
			protected void onBookmarkableSubmit(AjaxRequestTarget target, Form<?> arg1)
			{
				setFragmentParameter("betrag", amount.getObject());
				target.add(form);
			}

			@Override
			protected void onBookmarkableError(AjaxRequestTarget target, Form<?> arg1)
			{
			}
		});
		add(form);
	}
}
