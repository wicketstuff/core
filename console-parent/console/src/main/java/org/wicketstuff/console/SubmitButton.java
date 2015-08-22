package org.wicketstuff.console;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

final class SubmitButton extends AjaxButton implements IAjaxIndicatorAware
{
	private static final long serialVersionUID = 1L;

	private final ScriptEnginePanel enginePanel;


	SubmitButton(final String id, final ScriptEnginePanel scriptEnginePanel)
	{
		super(id, scriptEnginePanel.getForm());
		enginePanel = scriptEnginePanel;
	}

	@Override
	protected void onSubmit(final AjaxRequestTarget target, final Form<?> form)
	{
		enginePanel.process(target);
	}

	@Override
	protected void onError(final AjaxRequestTarget target, final Form<?> form)
	{
	}

	public String getAjaxIndicatorMarkupId()
	{
		return enginePanel.getIndicator().getMarkupId();
	}
}
