package org.wicketstuff.stateless;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.AbstractLink;

public class StatelessAjaxSubmitLink extends AbstractLink
{

	public StatelessAjaxSubmitLink(String id)
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
			StatelessAjaxSubmitLink.this.onAfterSubmit(target);
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target)
		{
			StatelessAjaxSubmitLink.this.onSubmit(target);
		}

		@Override
		protected void onError(AjaxRequestTarget target)
		{
			StatelessAjaxSubmitLink.this.onError(target);
		}		
	}
	
	protected void onAfterSubmit(AjaxRequestTarget target)
	{
		
	}

	protected void onSubmit(AjaxRequestTarget target)
	{
		
	}

	protected void onError(AjaxRequestTarget target)
	{
		
	}
}
