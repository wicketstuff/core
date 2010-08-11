package org.wicketstuff.yui.behavior.dragdrop;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Component.IVisitor;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.IBehavior;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderModule;


/**
 * DDList from <a href="http
 * ://developer.yahoo.com/yui/examples/dragdrop/dd-reorder.html">YUI Library
 * Examples: Drag & Drop: Reordering a List </a>.
 * 
 * onDragOver : for interacting with list items &lt;li&gt;
 * 
 * onDragDrop : for handling empty list using DDTarget
 * 
 * @author josh
 */
public abstract class YuiDDList extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private static final String MODULE_NAME = "wicket_yui_ddlist";

	private static final ResourceReference MODULE_REF_JS = new ResourceReference(YuiDDList.class,
			"YuiDDList.js");

	private static final String[] MODULE_REQUIRES = { "dragdrop", "animation" };

	/** index of this new DDList item after being dragged */
	private static final String IDX = "idx";

	private static final String DOI = "doi";

	protected static final String PREFIX = "YDDL_";

	private String listId;

	private String groupId = "default";

	public YuiDDList()
	{
		super();
	}

	public YuiDDList(String groupId)
	{
		super();
		this.groupId = groupId;
	}

	@SuppressWarnings("serial")
	@Override
	protected void onBind()
	{
		super.onBind();
		getComponent().setOutputMarkupId(true);
		listId = getComponent().getMarkupId();

		final String initScript = getInitJS();
		getComponent().add(
				YuiLoaderContributor.addModule(new YuiLoaderModule(MODULE_NAME,
						YuiLoaderModule.ModuleType.js, MODULE_REF_JS, MODULE_REQUIRES)
				{

					@Override
					public String onSuccessJS()
					{
						return initScript;
					}
				}));

	}

	private String getInitJS()
	{

		final StringBuffer js = new StringBuffer();
		String varId = PREFIX + listId;
		js.append("var " + varId + " = new YAHOO.Wicket.DDList(\"" + listId + "\",\""
				+ getGroupId() + "\"," + getConfig() + "," + getCallbackWicket() + ");\n");
		js.append(varId).append(".setHandleElId(\"" + listId + "\");\n");
		return js.toString();
	}

	private String getCallbackWicket()
	{
		return "function () { " + getCallbackScript() + " }";
	}

	@Override
	protected CharSequence getCallbackScript(boolean onlyTargetActivePage)
	{
		StringBuffer params = new StringBuffer();
		params.append(getCallbackUrl(onlyTargetActivePage)).append("'");
		params.append(" + '&").append(IDX).append("=' + ").append("this.idx");
		params.append(" + '&").append(DOI).append("=' + ").append("this.doi");
		CharSequence s = generateCallbackScript("wicketAjaxGet('" + params.toString());
		return s;
	}

	private String getGroupId()
	{
		return groupId;
	}

	protected String getConfig()
	{
		return "{}";
	}

	@Override
	protected void respond(final AjaxRequestTarget target)
	{
		final String idx = RequestCycle.get().getRequest().getParameter(IDX);
		final String doi = RequestCycle.get().getRequest().getParameter(DOI);

		final int index = Integer.parseInt(idx);

		getComponent().getPage().visitChildren(new IVisitor<Component>()
		{

			public Object component(Component component)
			{
				if (component.getMarkupId().equals(doi))
				{
					Component validTargetItem = component;

					// check if the dragOverId is a Target or
					// a DDList
					List<IBehavior> behaviors = component.getBehaviors();

					for (IBehavior behavior : behaviors)
					{
						if (behavior instanceof YuiDDTarget)
						{
							YuiDDTarget ddTarget = (YuiDDTarget)behavior;
							ddTarget.onDrop(target, YuiDDList.this.getComponent());
							validTargetItem = null;
						}
					}

					onDrop(target, index, validTargetItem);
				}
				return CONTINUE_TRAVERSAL;
			}
		});
	}

	/**
	 * 
	 * @param ajaxRequestTarget
	 * @param index
	 * @param lis2
	 */
	protected abstract void onDrop(AjaxRequestTarget target, int index, Component dragOverItem);

}
