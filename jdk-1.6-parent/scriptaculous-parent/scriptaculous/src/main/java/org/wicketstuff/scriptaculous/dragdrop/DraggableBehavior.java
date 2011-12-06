package org.wicketstuff.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;

/**
 * adds draggable behavior to any component.
 *
 * Can use a {@link DraggableTarget} to perform work when a Draggable object
 * is dropped on a component.
 *
 * @see http://wiki.script.aculo.us/scriptaculous/show/Draggable
 * @see DraggableTarget
 */
public class DraggableBehavior extends ScriptaculousAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private Map<String, Object> options = new HashMap<String, Object>();

  @Override
	protected final void onBind() {
		super.onBind();

		getComponent().setOutputMarkupId(true);
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		//no callback...yet
	}
	
	public void setRevert(boolean shouldRevert) {
		options.put("revert", shouldRevert);
	}

	public void setSnap(int x, int y) {
		options.put("snap", new JavascriptBuilder.JavascriptFunction("[" + x + "," + y + "]"));
	}
	
	public void setZIndex(int index) {
		options.put("zindex", index);
	}
	
	public void setConstraintHorizontal() {
		options.put("constraint", "horizontal");
	}
	public void setConstraintVertical() {
		options.put("constraint", "vertical");
	}

	public void setGhosting(boolean ghosting) {
		options.put("ghosting", ghosting);
	}

	protected void onComponentRendered() {
		super.onComponentRendered();

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new Draggable('" + getComponent().getMarkupId() + "', ");
		builder.addOptions(options);
		builder.addLine(");");

		Response response = RequestCycle.get().getResponse();
		response.write(builder.buildScriptTagString());
	}
}
