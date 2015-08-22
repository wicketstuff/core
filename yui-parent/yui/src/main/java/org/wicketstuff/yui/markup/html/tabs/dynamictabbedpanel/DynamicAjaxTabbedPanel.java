package org.wicketstuff.yui.markup.html.tabs.dynamictabbedpanel;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.Loop.LoopItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.behavior.dragdrop.YuiDDList;
import org.wicketstuff.yui.helper.CollectionsHelper;

/**
 * Dynamnic Ajax Tabbed Panel...
 * 
 * double click on tab label to change the title drag to reorder the tabs
 * 
 * 
 * @author josh
 * 
 */
@SuppressWarnings("serial")
public abstract class DynamicAjaxTabbedPanel extends AjaxTabbedPanel
{

	private String groupId;

	public DynamicAjaxTabbedPanel(String id, final List<ITab> tabs)
	{
		super(id, tabs);
		add(CSSPackageResource.getHeaderContribution(DynamicAjaxTabbedPanel.class,
				"res/DynamicAjaxTabbedPanel.css"));

		MarkupContainer tabContainer = (MarkupContainer)get("tabs-container");
		tabContainer.setOutputMarkupId(true);
		groupId = tabContainer.getMarkupId();

		tabContainer.add(new AjaxFallbackLink<String>("add", new Model<String>("add"))
		{

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				String label = getNewTabLabel();
				tabs.add(newTab(label));
				onAdd(target, label);
				target.addComponent(DynamicAjaxTabbedPanel.this);
			}
		});

		tabContainer.add(new AjaxFallbackLink<String>("remove", new Model<String>("remove"))
		{

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				DynamicAjaxTabbedPanel.this.onRemove(target, getSelectedTab());
				target.addComponent(DynamicAjaxTabbedPanel.this);
			}
		});
	}

	protected String getNewTabLabel()
	{
		return "tab " + (getTabs().size() + 1);
	}

	@Override
	protected void onBeforeRender()
	{
		if (getTabs().isEmpty())
		{
			getTabs().add(newTab(getNewTabLabel()));
		}

		if (getSelectedTab() == getTabs().size())
		{
			setSelectedTab(getTabs().size() - 1);
		}
		super.onBeforeRender();
	}

	/**
	 * Override this for a more useful name
	 * 
	 * @param label
	 * 
	 * @return
	 */
	protected abstract ITab newTab(String label);


	@SuppressWarnings("unchecked")
	@Override
	protected WebMarkupContainer newLink(String linkId, final int index)
	{
		return new AjaxFallbackLink(linkId)
		{

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (getSelectedTab() != index)
				{
					setSelectedTab(index);
					if (target != null)
					{
						target.addComponent(DynamicAjaxTabbedPanel.this);
					}
					onAjaxUpdate(target);
				}
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Component newTitle(String titleId, final IModel<?> titleModel, final int index)
	{

		return new AjaxEditableLabel<String>(titleId, (IModel<String>)titleModel)
		{

			@Override
			protected String getLabelAjaxEvent()
			{
				return "ondblclick";
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				super.onSubmit(target);
				onLabelChanged(target, index, titleModel.getObject().toString());
			}
		};
	}

	@Override
	protected String getTabContainerCssClass()
	{
		return "dyn-tab-row";
	}

	@Override
	protected LoopItem newTabContainer(final int tabIndex)
	{
		return new LoopItem(tabIndex)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				String cssClass = (String)tag.getString("class");
				if (cssClass == null)
				{
					cssClass = " ";
				}
				cssClass += " tab" + getIteration();

				if (getIteration() == getSelectedTab())
				{
					cssClass += " selected";
				}
				if (getIteration() == getTabs().size() - 1)
				{
					cssClass += " last";
				}
				tag.put("class", cssClass.trim());
			}

			@Override
			public boolean isVisible()
			{
				return getTabs().get(tabIndex).isVisible();
			}

			@Override
			protected void onBeforeRender()
			{
				super.onBeforeRender();

				add(new YuiDDList(groupId)
				{
					@Override
					protected void onDrop(AjaxRequestTarget target, int index, Component destItem)
					{
						DynamicAjaxTabbedPanel.this.onDrop(target, getIteration(), index);
					}
				});
			}
		};
	}

	protected void onAdd(AjaxRequestTarget target, String label)
	{
	}

	protected void onRemove(AjaxRequestTarget target, int selectedTab)
	{
		getTabs().remove(getSelectedTab());
	}

	protected void onDrop(AjaxRequestTarget target, int fromInd, int toInd)
	{
		CollectionsHelper.rotateInto(getTabs(), fromInd, toInd);
		target.addComponent(DynamicAjaxTabbedPanel.this);
	}

	protected void onLabelChanged(AjaxRequestTarget target, int index, String label)
	{
	}

}
