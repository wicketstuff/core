package wicket.contrib.scriptaculous.examples.dragdrop;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.wicketstuff.scriptaculous.Indicator;
import org.wicketstuff.scriptaculous.dragdrop.DraggableBehavior;
import org.wicketstuff.scriptaculous.dragdrop.DraggableTarget;
import org.wicketstuff.scriptaculous.dragdrop.SortableListView;

public class DragDropExamplePage extends WebPage
{
	public DragDropExamplePage()
	{
		WebMarkupContainer product1 = new WebMarkupContainer("product1");
		product1.add(new DraggableBehavior());

		WebMarkupContainer product2 = new WebMarkupContainer("product2");
		product2.add(new DraggableBehavior());

		List<String> objects = new ArrayList<String>();
		objects.add("item1");
		objects.add("item3");
		objects.add("item2");
		add(new SortableListView<String>("itemList", "item", objects) {
			@Override
			protected void populateItemInternal(ListItem<String> item)
			{
				item.add(new Label("label", item.getModel()));
			}});
		
		final DraggableTarget cart = new DraggableTarget("cart") {
			@Override
			protected void onDrop(Component component, AjaxRequestTarget target)
			{
				System.out.println("Input: " + component + " was dropped on the DraggableTarget");
			}
		};

		add(cart);
		add(product1);
		add(product2);
		add(new Indicator());
	}
}
