package org.wicketstuff.stateless.components;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

public class StatelessIndicatingAjaxButton extends StatelessAjaxButton implements IAjaxIndicatorAware
{
	private static final long serialVersionUID = 1L;
	private final AjaxIndicatorAppender indicatorAppender = new AjaxIndicatorAppender()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean getStatelessHint(Component component) 
		{
			return true;
		}
	};
	
	public StatelessIndicatingAjaxButton(String id)
	{
		super(id);
	}
	
	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		add(indicatorAppender);
	}
	
	public StatelessIndicatingAjaxButton(String id, Form<?> form)
	{
		super(id, form);
	}


	public StatelessIndicatingAjaxButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
	}

	public StatelessIndicatingAjaxButton(String id, IModel<String> model)
	{
		super(id, model);
	}

	@Override
	public String getAjaxIndicatorMarkupId()
	{
		return indicatorAppender.getMarkupId();
	}
}
