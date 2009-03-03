package org.wicketstuff.yui.markup.html.tabs.dynamictabbedpanel;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.Loop.LoopItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.behavior.dragdrop.YuiListDragDropBehavior;

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

	public DynamicAjaxTabbedPanel(String id, final List<ITab> tabs)
	{
		super(id, tabs);
		add(CSSPackageResource.getHeaderContribution(DynamicAjaxTabbedPanel.class,
				"res/DynamicAjaxTabbedPanel.css"));

		MarkupContainer tabContainer = (MarkupContainer)get("tabs-container");
		tabContainer.setOutputMarkupId(true);

		tabContainer.add(new AjaxFallbackLink<String>("add", new Model<String>("add"))
		{

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				tabs.add(newTab(getNewTabLabel()));
				target.addComponent(DynamicAjaxTabbedPanel.this);
			}
		});

		tabContainer.add(new AjaxFallbackLink<String>("remove", new Model<String>("remove"))
		{

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				getTabs().remove(getSelectedTab());
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
	protected Component newTitle(String titleId, final IModel<?> titleModel, int index)
	{

		return new AjaxEditableLabel<String>(titleId, (IModel<String>)titleModel)
		{

			@Override
			protected String getLabelAjaxEvent()
			{
				return "ondblclick";
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
		LoopItem li = super.newTabContainer(tabIndex);
		li.add(new YuiListDragDropBehavior(tabIndex)
		{

			@Override
			public void onDrop(AjaxRequestTarget target, String swapId)
			{
				swapTabs(getDestinationIndex(swapId), tabIndex);
				target.addComponent(DynamicAjaxTabbedPanel.this);
			}

		});
		return li;
	}

	private int getDestinationIndex(final String swapId)
	{
		final StringBuffer sb = new StringBuffer();

		DynamicAjaxTabbedPanel.this.visitChildren(new IVisitor<Component>()
		{

			public Object component(Component component)
			{
				if (component instanceof LoopItem)
				{
					LoopItem li = (LoopItem)component;
					if (swapId.equals(component.getMarkupId()))
					{
						sb.append(li.getIteration());
						return STOP_TRAVERSAL;
					}
				}
				return CONTINUE_TRAVERSAL;
			}
		});

		return Integer.parseInt(sb.toString());
	}

	private void swapTabs(int index, int withIndex)
	{
		Collections.swap(getTabs(), index, withIndex);
		setSelectedTab(index);
	}

}
