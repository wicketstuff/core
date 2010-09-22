package org.wicketstuff.minis.mootipbehavior;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Response;


/**
 * Internal class used by MooTip integration, used for ajxax calls
 * @author nino.martinez @ jayway.dk
 *
 */
public class MootipAjaxListener extends AbstractDefaultAjaxBehavior
{

	MootipPanel panel = null;


	@Override
	protected void onBind()
	{
		// TODO Auto-generated method stub
		super.onBind();
			

	}

	public MootipAjaxListener(MootipPanel panel)
	{
		this.panel = panel;
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		target.getPage().addOrReplace(panel);
		target.addComponent(panel);
	}

	@Override
	public void beforeRender(Component component)
	{
		super.beforeRender(component);
		Response response=component.getResponse();
		String mootipPanelPlaceHolderId=MootipPanel.getMooTipContentId();
		response.write("<script> function mootipAjax"+getEscapedComponentMarkupId()+"(callback) { \n var content='';wicketAjaxGet('"+getCallbackUrl()+"',\n function(){\n var tip=document.getElementById('"+mootipPanelPlaceHolderId+"').innerHTML;\n var callbackInside=callback;\n var runCallback=  callbackInside.pass(tip);\n runCallback();\n  }, null,null);\n}</script>");
	}
	/**
	 * Gets the escaped DOM id that the input will get attached to. All non word
	 * characters (\W) will be removed from the string.
	 * 
	 * @return The DOM id of the input - same as the component's markup id}
	 */
	protected final String getEscapedComponentMarkupId()
	{
		return this.getComponent().getMarkupId().replaceAll("\\W", "");

	}



}
