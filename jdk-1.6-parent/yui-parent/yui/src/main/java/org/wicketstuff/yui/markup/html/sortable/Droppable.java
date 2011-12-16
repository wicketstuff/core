package org.wicketstuff.yui.markup.html.sortable;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.Component.IVisitor;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.util.string.JavascriptUtils;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

public abstract class Droppable extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final static ResourceReference SORTABLELIST_JS = new JavascriptResourceReference(
			Droppable.class, "sortablelist.js");

	@Override
	protected void onBind()
	{
		getComponent().setOutputMarkupId(true);
		getComponent().add(YuiHeaderContributor.forModule("dragdrop"));
		getComponent().add(YuiHeaderContributor.forModule("utilities"));
		getComponent().add(JavascriptPackageResource.getHeaderContribution(SORTABLELIST_JS));
	}

	@Override
	protected void onComponentRendered()
	{
		final Response response = getComponent().getResponse();
		final String groupId = getComponent().getMarkupId();
		final StringBuilder builder = new StringBuilder();
		getComponent().getPage().visitChildren(new DraggableVisitor()
		{
			@Override
			void visit(Draggable draggable)
			{
				if (builder.length() > 0)
				{
					builder.append(',');
				}
				builder.append('\'');
				builder.append(draggable.getComponent().getMarkupId());
				builder.append('\'');
			}
		});

		response.write(JavascriptUtils.SCRIPT_OPEN_TAG);
		response.write("YAHOO.util.Event.onDOMReady(Wicket.yui.SortableListApp.init,{groupId:'"
				+ groupId + "',items:[" + builder + "], callbackUrl:'" + getCallbackUrl() + "'});");
		response.write(JavascriptUtils.SCRIPT_CLOSE_TAG);
	}

	protected boolean accept(Draggable draggable)
	{
		return true;
	}

	@Override
	protected void respond(final AjaxRequestTarget target)
	{
		Request request = getComponent().getRequest();
		final int index = Integer.parseInt(request.getParameter("dindex"));
		final String did = request.getParameter("did");


		getComponent().getPage().visitChildren(new DraggableVisitor()
		{
			@Override
			void visit(Draggable draggable)
			{
				if (draggable.getComponent().getMarkupId().equals(did))
				{
					onDrop(target, draggable.getComponent(), index);
				}
			}
		});
	}

	public abstract void onDrop(AjaxRequestTarget target, Component component, int index);

	private abstract class DraggableVisitor implements IVisitor<Component>
	{
		public Object component(Component component)
		{
			List<IBehavior> behaviors = component.getBehaviors();
			for (int a = 0; a < behaviors.size(); a++)
			{
				IBehavior behavior = behaviors.get(a);
				if (behavior instanceof Draggable)
				{
					Draggable draggable = (Draggable)behavior;
					if (accept(draggable))
					{
						visit(draggable);
						return IVisitor.CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
					}
				}
			}
			return IVisitor.CONTINUE_TRAVERSAL;
		}

		abstract void visit(Draggable draggable);
	}
}