package org.wicketstuff.minis.mootipbehavior;

import org.apache.wicket.Component;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;


/**
 * Internal class used by MooTip integration, used for AJAX calls
 * 
 * @author nino.martinez @ jayway.dk
 * 
 */
public class MootipAjaxListener extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private MootipPanel panel = null;

	public MootipAjaxListener(final MootipPanel panel)
	{
		this.panel = panel;
	}

	@Override
	public void beforeRender(final Component component)
	{
		super.beforeRender(component);
		final Response response = component.getResponse();
		final String mootipPanelPlaceHolderId = MootipPanel.getMooTipContentId();
		response.write("<script> function mootipAjax" +
			getEscapedComponentMarkupId() +
			"(callback) { \n var content='';wicketAjaxGet('" +
			getCallbackUrl(false) +
			"',\n function(){\n var tip=document.getElementById('" +
			mootipPanelPlaceHolderId +
			"').innerHTML;\n var callbackInside=callback;\n var runCallback=  callbackInside.pass(tip);\n runCallback();\n  }, null,null);\n}</script>");
	}

	/**
	 * Gets the escaped DOM id that the input will get attached to. All non word characters (\W)
	 * will be removed from the string.
	 * 
	 * @return The DOM id of the input - same as the component's markup id}
	 */
	protected final String getEscapedComponentMarkupId()
	{
		return getComponent().getMarkupId().replaceAll("\\W", "");
	}

	@Override
	protected void respond(final AjaxRequestTarget target)
	{
		target.getPage().addOrReplace(panel);
		target.addComponent(panel);
	}
}
