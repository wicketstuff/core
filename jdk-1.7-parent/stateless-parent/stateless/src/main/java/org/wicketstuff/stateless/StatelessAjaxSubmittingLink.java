package org.wicketstuff.stateless;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.AbstractLink;

public class StatelessAjaxSubmittingLink extends AbstractLink
{

	public StatelessAjaxSubmittingLink(String id)
	{
		super(id);
		setOutputMarkupId(true);
		
		add(new StatelessAjaxFormSubmitBehavior("click"){
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				onClick(target);
				super.onEvent(target);
			}
		});
	}

	protected void onClick(AjaxRequestTarget target)
	{		
	}
	
	@Override
	protected boolean getStatelessHint()
	{
		return true;
	}
}
