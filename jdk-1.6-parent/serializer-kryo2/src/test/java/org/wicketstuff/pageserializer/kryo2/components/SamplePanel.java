package org.wicketstuff.pageserializer.kryo2.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;

public class SamplePanel extends Panel
{
	public SamplePanel(String id)
	{
		super(id);
		
		WebMarkupContainer container = new WebMarkupContainer("container");
		container.add(new EmptyPanel("empty"));
		
		List<Integer> list=new ArrayList<Integer>();
		list.addAll(Arrays.asList(1,2,3,4,5,6));
		
		container.add(new ListView<Integer>("list",list) {
			@Override
			protected void populateItem(ListItem<Integer> item)
			{
				item.add(new Label("label",item.getModel()));
			}
		});
		
		add(container);
	}

}
