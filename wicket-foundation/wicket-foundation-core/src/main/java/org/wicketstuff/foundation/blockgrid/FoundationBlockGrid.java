package org.wicketstuff.foundation.blockgrid;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.foundation.component.FoundationBasePanel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * Block grids give you a way to evenly split contents of a list within the grid.
 * http://foundation.zurb.com/docs/components/block_grid.html
 * @author ilkka
 * @param <T> - The type of model object.
 */
public abstract class FoundationBlockGrid<T> extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationBlockGrid component.
	 * @param id - Wicket id.
	 * @param options - Options for the component.
	 * @param modelObjects - The model objects passed to client through createContent models parameter.
	 */
	public FoundationBlockGrid(String id, BlockGridOptions options, List<T> modelObjects) {
		this(id, new ListModel<BlockGridOptions>(Arrays.asList(options)), new ListModel<T>(modelObjects));
	}

	/**
	 * Create FoundationBlockGrid component.
	 * @param id - Wicket id.
	 * @param optionsList - List of component options.
	 * @param modelObjects - The model objects passed to client through createContent models parameter.
	 */
	public FoundationBlockGrid(String id, List<BlockGridOptions> optionsList, List<T> modelObjects) {
		this(id, new ListModel<BlockGridOptions>(optionsList), new ListModel<T>(modelObjects));
	}

	/**
	 * Create FoundationBlockGrid component.
	 * @param id - Wicket id.
	 * @param optionsListModel - Model providing list of options for the component.
	 * @param models - The models passed to client through createContent models parameter.
	 */
	public FoundationBlockGrid(String id, IModel<List<BlockGridOptions>> optionsListModel, IModel<List<T>> models) {
		super(id);
		BlockGridContainer container = new BlockGridContainer("container", optionsListModel);
		add(container);
		container.add(new ListView<T>("item", models) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<T> item) {
				WebMarkupContainer content = FoundationBlockGrid.this.createContent(item.getIndex(), "content", item.getModel());
				item.add(content);
			}
		});
	}
	
	/**
	 * 
	 * @param idx - Index number of the content item.
	 * @param id - Wicket id for the content item.
	 * @param model - Model for the content item.
	 * @return Content item to be placed into the FoundationBlockGrid.
	 */
	public abstract WebMarkupContainer createContent(int idx, String id, IModel<T> model);
	
	private class BlockGridContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		private IModel<List<BlockGridOptions>> optionsListModel;

		public BlockGridContainer(String id, IModel<List<BlockGridOptions>> optionsListModel) {
			super(id);
			this.optionsListModel = optionsListModel;
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			for (BlockGridOptions options: optionsListModel.getObject()) {
				String cssClass = resolveCssClass(options);
				Attribute.addClass(tag, cssClass);
			}
			super.onComponentTag(tag);
		}
		
		private String resolveCssClass(BlockGridOptions options) {
			String typeName = StringUtil.EnumNameToCssClassName(options.getType().name());
			return String.format("%s-%d", typeName, options.getColumnCount());
		}

		@Override
		protected void onDetach() {
			optionsListModel.detach();
			super.onDetach();
		}
	}
}
