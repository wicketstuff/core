package org.wicketstuff.select2;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;

/**
 * Behavior that adds a select2 to a dropdown choice.
 * 
 * @author Ernesto Reinaldo Barreiro (reiern70@gmail.com)
 */
public class Select2Behavior extends Behavior 
{
	// TODO: add settings!
	private static final long serialVersionUID = 1L;

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		new Select2ResourcesBehavior().renderHead(component, response);
		StringBuilder builder = new StringBuilder("$('#").append(component.getMarkupId()).append("').select2({});");
		response.render(OnDomReadyHeaderItem.forScript(builder));
	}

	@Override
	public void bind(Component component)
	{
		component.setOutputMarkupId(true);
		if(!(component instanceof DropDownChoice))
		{
			throw new IllegalArgumentException("This behavior should only be used on DropDownChoice components");
		}
	}
}
