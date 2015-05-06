package org.wicketstuff.foundation.inlinelist;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.foundation.component.FoundationBasePanel;

/**
 * This simple construct creates a horizontal list of links, like in a footer.
 * http://foundation.zurb.com/docs/components/inline_lists.html
 * @author ilkka
 *
 */
public abstract class FoundationInlineList extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationInlineList.
	 * @param id - Wicket id.
	 * @param titles - List of titles.
	 */
	public FoundationInlineList(String id, List<String> titles) {
		this(id, new ListModel<>(titles));
	}
	
	/**
	 * Create FoundationInlineList.
	 * @param id - Wicket id.
	 * @param titles - Model for the titles.
	 */
	public FoundationInlineList(String id, IModel<List<String>> titlesModel) {
		super(id);
		add(new ListView<String>("item", titlesModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item) {
				AbstractLink link = createLink("link", item.getIndex());
				item.add(link);
				link.add(new Label("text", item.getModelObject()));
			}
			
		});
	}
	
	public abstract AbstractLink createLink(String id, int idx);
}
