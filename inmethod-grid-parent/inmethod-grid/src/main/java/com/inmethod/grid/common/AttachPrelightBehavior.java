/**
 * 
 */
package com.inmethod.grid.common;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.util.value.AttributeMap;

/**
 * When a single item is (rendered using Ajax this behavior makes sure that it has the prelight
 * events attached.
 * 
 * @author Matej Knopp
 */
public final class AttachPrelightBehavior extends Behavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterRender(Component component)
	{
		WebRequest request = (WebRequest)RequestCycle.get().getRequest();
		Response response = RequestCycle.get().getResponse();
		AbstractGrid<?, ?, ?> grid = component.findParent(AbstractGrid.class);
		if (request.isAjax() && !grid.isRendering())
		{
			JavaScriptUtils.writeOpenTag(response, new AttributeMap());
			response.write("var e = Wicket.$('" + component.getMarkupId() + "');");
			response.write("var id = '" + grid.getMarkupId() + "';");
			response.write("var m = InMethod.XTableManager.instance;");
			response.write("m.updateRow(id, e);");
			JavaScriptUtils.writeCloseTag(response);
		}
	}
}
