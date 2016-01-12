package org.wicketstuff.foundation.topbar;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.foundation.component.FoundationJsPanel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * The Foundation Top Bar gives you a great way to display a complex navigation bar on small, medium or large screens.
 * http://foundation.zurb.com/docs/components/topbar.html
 * @author ilkka
 *
 */
public abstract class FoundationTopBar extends FoundationJsPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationTopBar.
	 * @param id - Wicket id.
	 * @param rightItems - Items on the right.
	 * @param leftItems - Items on the left.
	 */
	public FoundationTopBar(String id, List<TopBarItem> rightItems, List<TopBarItem> leftItems) {
		this(id, new TopBarOptions(), rightItems, leftItems);
	}
	
	/**
	 * Create FoundationTopBar.
	 * @param id - Wicket id.
	 * @param options - Options for the top bar.
	 * @param rightItems - Items on the right.
	 * @param leftItems - Items on the left.
	 */
	public FoundationTopBar(String id, TopBarOptions options, List<TopBarItem> rightItems, List<TopBarItem> leftItems) {
		this(id, Model.of(options), new ListModel<>(rightItems), new ListModel<>(leftItems));
	}
	
	/**
	 * Create FoundationTopBar.
	 * @param id - Wicket id.
	 * @param optionsModel - Model for the top bar options.
	 * @param rightItemsModel - Model for the items on the right.
	 * @param leftItemsModel - Model for the items on the left.
	 */
	public FoundationTopBar(String id, IModel<TopBarOptions> optionsModel, IModel<List<TopBarItem>> rightItemsModel, 
			IModel<List<TopBarItem>> leftItemsModel) {
		super(id);
		TopBarContainer topBarContainer = new TopBarContainer("topBarContainer", optionsModel);
		add(topBarContainer);
		TopBar topBar = new TopBar("topBar", optionsModel);
		topBarContainer.add(topBar);
		WebMarkupContainer titleContainer = createTitleContainer("titleContainer");
		topBar.add(titleContainer);
		topBar.add(new TopBarItemContainer("rightContainer", rightItemsModel));
		topBar.add(new TopBarItemContainer("leftContainer", leftItemsModel));
	}
	
	public abstract WebMarkupContainer createTitleContainer(String id);
	
	public abstract AbstractLink createLink(String id, String itemId);
	
	private class TopBarItemContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		public TopBarItemContainer(String id, final IModel<List<TopBarItem>> itemsModel) {
			super(id);
			add(new RepeatingView("item") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onPopulate() {
					this.removeAll();
					for (TopBarItem item: itemsModel.getObject()) {
						
						TopBarRecursiveLinkPanel linkPanel = new TopBarRecursiveLinkPanel(newChildId(), item) {

							private static final long serialVersionUID = 1L;

							@Override
							public AbstractLink createLink(String id, String itemId) {
								return FoundationTopBar.this.createLink(id, itemId);
							}
						};
						add(linkPanel);
					}
				}
			});
		}
	}
	
	private static class TopBarContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		private IModel<TopBarOptions> optionsModel;

		public TopBarContainer(String id, IModel<TopBarOptions> optionsModel) {
			super(id);
			this.optionsModel = optionsModel;
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			TopBarOptions options = optionsModel.getObject();
			if (options.isFixed()) {
				Attribute.addClass(tag, "fixed");
			}
			if (options.isContainToGrid()) {
				Attribute.addClass(tag, "contain-to-grid");
			}
			if (options.isSticky()) {
				Attribute.addClass(tag, "sticky");
			}
			super.onComponentTag(tag);
		}
		
		@Override
		protected void onDetach() {
			optionsModel.detach();
			super.onDetach();
		}
	}
	
	private static class TopBar extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		private IModel<TopBarOptions> optionsModel;

		public TopBar(String id, IModel<TopBarOptions> optionsModel) {
			super(id);
			this.optionsModel = optionsModel;
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addClass(tag, "top-bar");
			Attribute.addAttribute(tag, "data-topbar");
			Attribute.addAttribute(tag, "role", "navigation");
			TopBarOptions options = optionsModel.getObject();
			if (options.isClickable()) {
				Attribute.addDataOptions(tag, "is_hover:false");
			}
			if (!options.getStickySizes().isEmpty()) {
				for (TopBarStickySize size : options.getStickySizes()) {
					Attribute.addDataOptions(tag, "sticky_on:" + StringUtil.EnumNameToCssClassName(size.name()));
				}
			}
			super.onComponentTag(tag);
		}
		
		@Override
		protected void onDetach() {
			optionsModel.detach();
			super.onDetach();
		}
	}
}
