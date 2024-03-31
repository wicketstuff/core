package org.wicketstuff.urlfragment.example.asyncpanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.wicketstuff.urlfragment.AsyncUrlFragmentAwarePanel;
import org.wicketstuff.urlfragment.BookmarkableAjaxLink;

public class AsyncContentPanel extends AsyncUrlFragmentAwarePanel
{
	private static final long serialVersionUID = 1L;
	private final IModel<String> sortingModel;
	private final Label sortingLabel;

	public AsyncContentPanel(String id)
	{
		super(id);

		sortingModel = Model.of(""); // initial state

		sortingLabel = new Label("sorting", sortingModel);
		sortingLabel.setOutputMarkupId(true);
		add(sortingLabel); // render with initial content

		add(new BookmarkableAjaxLink<Void>("zins_asc", "sorting", "zins_asc")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onBookmarkableClick(AjaxRequestTarget target)
			{
				sortingModel.setObject("zins_asc");
				target.add(sortingLabel);
			}

		});

		add(new BookmarkableAjaxLink<Void>("zins_desc", "sorting", "zins_desc")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onBookmarkableClick(AjaxRequestTarget target)
			{
				sortingModel.setObject("zins_desc");
				target.add(sortingLabel);
			}

		});
	}

	@Override
	protected void onParameterIncome(IRequestParameters requestParameters, AjaxRequestTarget target)
	{
		String sorting = requestParameters.getParameterValue("sorting").toString();
		sortingModel.setObject(sorting);
		target.add(sortingLabel); // render with content based on URL fragment parameter(s)
	}
}
