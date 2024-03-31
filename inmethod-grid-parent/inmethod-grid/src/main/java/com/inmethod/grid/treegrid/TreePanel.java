package com.inmethod.grid.treegrid;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.inmethod.icon.Icon;
import com.inmethod.icon.IconImage;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.io.IClusterable;

/**
 * Represents the content of a tree column cell.
 * 
 * @param <T>
 *            tree model object type
 * @param <I>
 *            node model object type
 * 
 * @author Matej Knopp
 */
public abstract class TreePanel<T extends TreeModel, I extends TreeNode> extends Panel
{

	private static final long serialVersionUID = 1L;
	private static final String JUNCTION_LINK_ID = "junctionLink";
	private static final String NODE_COMPONENT_ID = "nodeComponent";

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model to access the {@link TreeNode}
	 * @param level
	 *            node depth level
	 */
	public TreePanel(String id, final IModel<I> model, int level)
	{
		super(id, model);
		this.level = level;
	}

	private final int level;

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// add junction link
		Object node = getDefaultModelObject();
		Component junctionLink = newJunctionLink(this, JUNCTION_LINK_ID, node);
		junctionLink.add(new JunctionBorder(node, level));
		add(junctionLink);

		// add node component
		Component nodeComponent = newNodeComponent(NODE_COMPONENT_ID, getDefaultNodeModel());
		add(nodeComponent);

		IconImage icon = new IconImage("icon", new IconModel())
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return getIcon() != null;
			}
		};
		icon.add(IconBorder.INSTANCE);
		add(icon);
	}

	/**
	 * Returns the icon component instance.
	 * 
	 * @return icon component
	 */
	public IconImage getIconComponent()
	{
		return (IconImage)get("icon");
	}

	/**
	 * Simple adapter that returns icon for this panel
	 * 
	 * @author Matej Knopp
	 */
	private class IconModel implements IModel<Icon>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		public Icon getObject()
		{
			return getIcon(getDefaultNodeModel());
		}

		/**
		 * {@inheritDoc}
		 */
		public void setObject(Icon object)
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		public void detach()
		{
		}
	};

	/**
	 * Return the icon for this node or null if no icon should be used.
	 * 
	 * @param model
	 *            model for the TreeNode
	 * @return icon instance or null
	 */
	protected abstract Icon getIcon(IModel<I> model);

	/**
	 * Creates a new component for the given TreeNode.
	 * 
	 * @param id
	 *            component ID
	 * @param model
	 *            model that returns the node
	 * @return component for node
	 */
	protected abstract Component newNodeComponent(String id, IModel<I> model);

	protected IModel<I> getDefaultNodeModel()
	{
		return (IModel<I>)getDefaultModel();
	}

	/**
	 * Very simple border that adds a proper <td></td> around an icon
	 * 
	 * @author Matej Knopp
	 */
	private static class IconBorder extends Behavior
	{

		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void beforeRender(Component component)
		{
			RequestCycle.get().getResponse().write("<td>");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void afterRender(Component component)
		{
			RequestCycle.get().getResponse().write("</td>");
		}

		private static final IconBorder INSTANCE = new IconBorder();
	};

	/**
	 * Class that wraps a link (or span) with a junction table cells.
	 * 
	 * @author Matej Knopp
	 */
	private static class JunctionBorder extends Behavior
	{
		private static final long serialVersionUID = 1L;

		private final int level;

		/**
		 * Construct.
		 * 
		 * @param node
		 * @param level
		 */
		public JunctionBorder(Object node, int level)
		{
			this.level = level;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void afterRender(Component component)
		{
			RequestCycle.get().getResponse().write("</td>");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void beforeRender(Component component)
		{
			Response response = RequestCycle.get().getResponse();

			for (int i = level - 1; i >= 0; --i)
			{
				response.write("<td class=\"imxt-spacer\"><span></span></td>");
			}

			response.write("<td class=\"imxt-spacer\">");
		}
	};

	private TreeGridBody<T, I, ?> getTreeGridBody()
	{
		return findParent(TreeGridBody.class);
	};

	/**
	 * Creates the junction link for given node. Also (optionally) creates the junction image. If
	 * the node is a leaf (it has no children), the created junction link is non-functional.
	 * 
	 * @param parent
	 *            parent component of the link
	 * @param id
	 *            wicket:id of the component
	 * @param node
	 *            tree node for which the link should be created.
	 * @return The link component
	 */
	protected Component newJunctionLink(MarkupContainer parent, final String id, final Object node)
	{
		final MarkupContainer junctionLink;

		TreeModel model = (TreeModel)getTreeGridBody().getDefaultModelObject();
		if (model.isLeaf(node) == false)
		{
			junctionLink = newLink(id, new ILinkCallback()
			{
				private static final long serialVersionUID = 1L;

				public void onClick(AjaxRequestTarget target)
				{
					if (getTreeGridBody().isNodeExpanded2(node))
					{
						getTreeGridBody().getTreeState().collapseNode(node);
					}
					else
					{
						getTreeGridBody().getTreeState().expandNode(node);
					}
					onJunctionLinkClicked(target, node);
					getTreeGridBody().updateTree(target);
				}
			});
			junctionLink.add(new Behavior()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onComponentTag(Component component, ComponentTag tag)
				{
					if (getTreeGridBody().isNodeExpanded2(node))
					{
						tag.put("class", "imxt-junction-open");
					}
					else
					{
						tag.put("class", "imxt-junction-closed");
					}
				}
			});
		}
		else
		{
			junctionLink = new WebMarkupContainer(id)
			{
				private static final long serialVersionUID = 1L;

				/**
				 * @see org.apache.wicket.Component#onComponentTag(org.apache.wicket.markup.ComponentTag)
				 */
				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);
					tag.setName("span");
				}
			};

		}

		return junctionLink;
	}

	/**
	 * Helper class for calling an action from a link.
	 * 
	 * @author Matej Knopp
	 */
	public interface ILinkCallback extends IClusterable
	{
		/**
		 * Called when the click is executed.
		 * 
		 * @param target
		 *            The ajax request target
		 */
		void onClick(AjaxRequestTarget target);
	}

	/**
	 * Creates a link of type specified by current linkType. When the links is clicked it calls the
	 * specified callback.
	 * 
	 * @param id
	 *            The component id
	 * @param callback
	 *            The link call back
	 * @return The link component
	 */
	public MarkupContainer newLink(String id, final ILinkCallback callback)
	{
		return new AjaxSubmitLink(id)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				callback.onClick(target);
			}
		}.setDefaultFormProcessing(false);
	}

	/**
	 * Callback function called after user clicked on an junction link. The node has already been
	 * expanded/collapsed (depending on previous status).
	 * 
	 * @param target
	 *            Request target - may be null on non-ajax call
	 * 
	 * @param node
	 *            Node for which this callback is relevant
	 */
	protected abstract void onJunctionLinkClicked(AjaxRequestTarget target, Object node);
}
