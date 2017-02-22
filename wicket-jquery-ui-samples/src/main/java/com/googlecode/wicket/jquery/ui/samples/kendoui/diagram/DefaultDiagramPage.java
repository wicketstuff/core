package com.googlecode.wicket.jquery.ui.samples.kendoui.diagram;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.json.JSONObject;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.UrlUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.converter.IJsonConverter;
import com.googlecode.wicket.jquery.core.converter.JsonConverter;
import com.googlecode.wicket.jquery.core.resource.JavaScriptPackageHeaderItem;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.jquery.ui.samples.data.bean.User.Avatar;
import com.googlecode.wicket.kendo.ui.dataviz.diagram.Diagram;
import com.googlecode.wicket.kendo.ui.dataviz.diagram.IDiagramNode;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class DefaultDiagramPage extends AbstractDiagramPage
{
	private static final long serialVersionUID = 1L;

	// List of Avatar(s) //
	static final List<Avatar> avatars = Arrays.asList(new Avatar(1, "avatar01.jpg"), new Avatar(2, "avatar02.jpg"), new Avatar(3, "avatar03.jpg"), new Avatar(4, "avatar04.jpg"), new Avatar(5, "avatar05.jpg"), new Avatar(6, "avatar06.jpg"),
			new Avatar(7, "avatar07.jpg"), new Avatar(8, "avatar08.jpg"), new Avatar(9, "avatar09.jpg"), new Avatar(10, "avatar10.jpg"), new Avatar(11, "avatar11.jpg"), new Avatar(12, "avatar12.jpg"), new Avatar(13, "avatar13.jpg"),
			new Avatar(14, "avatar14.jpg"), new Avatar(15, "avatar15.jpg"));

	public DefaultDiagramPage()
	{
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Diagram //
		this.add(new AvatarDiagram("diagram") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target, AvatarNode node)
			{
				if (node != null)
				{
					this.info("Clicked on node: " + node);
					this.refresh(target); // just because it's fun
				}

				target.add(feedback);
			}
		});
	}

	// classes //

	private static class AvatarDiagram extends Diagram<AvatarNode>
	{
		private static final long serialVersionUID = 1L;

		private AvatarDiagram(String id)
		{
			super(id, newModel(), newConverter());
		}

		@Override
		public void onConfigure(JQueryBehavior behavior)
		{
			super.onConfigure(behavior);

			behavior.setOption("editable", false);
			behavior.setOption("layout", "{ type: 'layered' }");
			behavior.setOption("shapeDefaults", "{ visual: visualTemplate }"); // AvatarDiagram.js
		}

		// Methods //

		@Override
		public void renderHead(IHeaderResponse response)
		{
			super.renderHead(response);

			response.render(new JavaScriptPackageHeaderItem(AvatarDiagram.class)); // AvatarDiagram.js
		}

		// Properties //

		@Override
		public boolean isClickEventEnabled()
		{
			return true;
		}

		// Factories //

		private static IModel<List<AvatarNode>> newModel()
		{
			return new LoadableDetachableModel<List<AvatarNode>>() {

				private static final long serialVersionUID = 1L;

				@Override
				protected List<AvatarNode> load()
				{
					return new AvatarTree().getNodes(); // will generate a random tree
				}
			};
		}

		private static IJsonConverter<AvatarNode> newConverter()
		{
			return new JsonConverter<AvatarNode>() {

				private static final long serialVersionUID = 1L;

				@Override
				public AvatarNode toObject(JSONObject object)
				{
					if (object != null)
					{
						return new AvatarNode(object.optInt("parentId"), object.optInt("id"), object.optString("image"));
					}

					return null;
				}
			};
		}
	}

	public static class AvatarNode extends Avatar implements IDiagramNode<AvatarNode>
	{
		private static final long serialVersionUID = 1L;
		private static String[] colors = { "#1696d3", "#ef6944", "#ee587b", "#75be16" };

		private final Integer parentId;
		private final List<AvatarNode> nodes = Generics.newArrayList(); // children

		public AvatarNode(Integer parentId, Avatar avatar)
		{
			this(parentId, avatar.getId(), avatar.getImage());
		}

		public AvatarNode(Integer parentId, Integer id, String image)
		{
			super(id, image);

			this.parentId = parentId;
		}

		// Properties //

		public String getColor()
		{
			return ListUtils.random(colors);
		}

		public String getTitle()
		{
			return this.toString();
		}

		public Integer getParentId()
		{
			return this.parentId;
		}

		public String getImageRelativePath()
		{
			return UrlUtils.rewriteToContextRelative(this.getImagePath(), RequestCycle.get());
		}

		@Override
		public List<AvatarNode> getNodes()
		{
			return Collections.unmodifiableList(this.nodes);
		}

		// Methods //

		public void addNode(AvatarNode avatar)
		{
			this.nodes.add(avatar);
		}
	}

	static class AvatarTree
	{
		private final List<AvatarNode> nodes = Generics.newArrayList();

		public AvatarTree()
		{
			for (int i = 0; i < avatars.size(); i++)
			{
				AvatarNode parent = ListUtils.random(this.nodes);
				Integer parentId = parent != null ? parent.getId() : null;

				this.nodes.add(new AvatarNode(parentId, avatars.get(i)));
			}

			this.build();
		}

		/**
		 * Builds the nodes hierarchy
		 */
		private void build()
		{
			for (AvatarNode node : this.nodes)
			{
				Integer parentId = node.getParentId();

				if (parentId != null)
				{
					AvatarNode parent = this.getNode(parentId);
					parent.addNode(node);
				}
			}
		}

		public AvatarNode getNode(int id)
		{
			for (AvatarNode node : this.nodes)
			{
				if (node.getId() == id)
				{
					return node;
				}
			}

			return null;
		}

		/**
		 * Gets the nodes hierarchy (the root)
		 */
		public List<AvatarNode> getNodes()
		{
			return Collections.singletonList(this.nodes.get(0));
		}
	}
}
