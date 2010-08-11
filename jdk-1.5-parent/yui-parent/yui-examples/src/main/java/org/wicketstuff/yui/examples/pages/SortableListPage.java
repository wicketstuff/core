package org.wicketstuff.yui.examples.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.sortable.SortableItem;
import org.wicketstuff.yui.markup.html.sortable.SortableList;

public class SortableListPage extends WicketExamplePage
{
	ArrayList<String> choices = new ArrayList<String>();

	public SortableListPage()
	{
		Locale[] locales = Locale.getAvailableLocales();

		for (int i = 0; i < 30; i++) {
			final Locale locale = locales[i];
			final String country = locale.getDisplayCountry();
			if (!Strings.isEmpty(country) && !choices.contains(country)) {
				choices.add(country);
			}
		}

		final Label serverState = new Label("serverState", new PropertyModel(this, "choices"));
		serverState.setOutputMarkupId(true);
		add(serverState);

		SortableList container = new SortableList("sortableList")
		{
			@Override
			protected void onDrop(AjaxRequestTarget target, Component component, int index)
			{
				Component label = ((MarkupContainer) component).get("label");
				String value = label.getDefaultModelObjectAsString();
				choices.remove(value);
				choices.add(index, value);
				target.addComponent(serverState);
			}
		};
		add(container);

		RepeatingView view = new RepeatingView("sortableItem");
		List<String> list = choices;
		for (int a = 0; a < list.size(); a++) {
			SortableItem item = new SortableItem(view.newChildId());
			view.add(item);
			item.add(new Label("label", list.get(a)));
		}
		container.add(view);
	}
}
