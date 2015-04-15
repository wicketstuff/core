package org.wicketstuff.select2;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

/**
 * Adds drag & drop behavior to Select2MultiChoice components, i.e. the list of selected values can
 * be sorted.
 *
 * @author Tom Götz (tom@decoded.de)
 */
public class DragAndDropBehavior extends Behavior
{
	private static final long serialVersionUID = 1L;

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{

		// Include jquery-ui reference, if configured
		final ApplicationSettings settings = ApplicationSettings.get();
		if (settings.isIncludeJqueryUI())
		{
			response.render(JavaScriptHeaderItem.forReference(settings.getJqueryUIReference()));
		}

		// Render script to enable sortable/drag-and-drop behavior
		String script = "$('#%1$s').select2('container').find('ul.select2-choices').sortable({"
			+ "containment: 'parent',"
			+ "start: function() { $('#%1$s').select2('onSortStart'); },"
			+ "update: function() { $('#%1$s').select2('onSortEnd'); }" + "});";
		response.render(OnDomReadyHeaderItem.forScript(JQuery.execute(script,
			component.getMarkupId())));
	}

}
