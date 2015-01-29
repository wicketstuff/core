package org.wicketstuff.stateless;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.AbstractLink;

public class StatelessAjaxSubmittingLink extends AbstractLink
{

	public StatelessAjaxSubmittingLink(String id)
	{
		super(id);
		setOutputMarkupId(true);
		
		add(new StatelessAjaxSubmittingLinkBehavior("click"));
	}
	
	@Override
	protected boolean getStatelessHint()
	{
		return true;
	}
	
	class StatelessAjaxSubmittingLinkBehavior extends StatelessAjaxFormSubmitBehavior
	{

		public StatelessAjaxSubmittingLinkBehavior(String event)
		{
			super(event);
		}

		@Override
		protected void onAfterSubmit(AjaxRequestTarget target)
		{
			StatelessAjaxSubmittingLink.this.onAfterSubmit(target);
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target)
		{
			StatelessAjaxSubmittingLink.this.onSubmit(target);
		}

		@Override
		protected void onError(AjaxRequestTarget target)
		{
			StatelessAjaxSubmittingLink.this.onError(target);
		}		
	}
	
	protected void onAfterSubmit(AjaxRequestTarget target)
	{
		
	}

	protected void onSubmit(AjaxRequestTarget target)
	{
		System.out.println("AJAX submit!");
	}

	protected void onError(AjaxRequestTarget target)
	{
		
	}
}
