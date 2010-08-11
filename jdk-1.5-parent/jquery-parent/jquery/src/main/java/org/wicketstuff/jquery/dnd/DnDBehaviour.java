package org.wicketstuff.jquery.dnd;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.IBehaviorListener;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.jquery.FunctionString;
import org.wicketstuff.jquery.JQueryBehavior;
import org.wicketstuff.jquery.Options;

/**
 * This implementation is really too simple, only supporting one drop-container etc,
 * but it is an ok startingpoint to extend the functionality.
 */
public class DnDBehaviour extends JQueryBehavior implements IBehaviorListener {
	private Options droppableOptions;
	private Options draggableOptions;
	private String dropSelector;
	private String dragSelector;

	public DnDBehaviour() {
		super();
	}

	/**
	 *
	 * @param draggableOptions 	(helper=clone to "leave a copy" of the object you are dragging. Doesn't work without helper argument.)
	 * @param droppableOptions 	(hoverClass=cssClass to set on hover over a droppable)
	 * @param dropSelector		jquery selector setting what elements are droppable (example: #myListId li)
	 * @param dragSelector		jquery selector setting what elements are draggable (example: .draggable)
	 *
	 */
	public DnDBehaviour(Options draggableOptions, Options droppableOptions, String dragSelector, String dropSelector) {
		super();
		this.dropSelector = dropSelector;
		this.dragSelector = dragSelector;
		this.draggableOptions = draggableOptions;
		this.droppableOptions = droppableOptions;

	}

	/**
	 * @param dropSelector		jquery selector setting what elements are droppable (example: #myListId li)
	 * @param dragSelector		jquery selector setting what elements are draggable (example: .draggable)
	 */
	public DnDBehaviour(String dragSelector, String dropSelector) {
		this(new Options(), new Options(), dragSelector, dropSelector);
	}

	@Override
    public void renderHead(IHeaderResponse response) {
		droppableOptions.set("accept", dragSelector, false);
		droppableOptions.set("drop", new FunctionString(getDropScript()));

		response.renderJavascriptReference(JQueryBehavior.JQUERY_UI_JS);
		super.renderHead(response);
    }

	public String getDropScript() {
		return "function(ev, ui) {\n " + getCallbackScript() + "\n}\n";
	}

	@Override
	public final void respond(AjaxRequestTarget target) {
		Request req = RequestCycle.get().getRequest();

		onDrop(req.getParameter("src"), req.getParameter("dest"), target);
	}

	@Override
	protected CharSequence getCallbackScript() {
		return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl()
				+ "&src=' + $(ui.draggable).attr('id') + '"
				+ "&dest=' + $(this).attr('id')");

	}

	public void onDrop(String srcId, String destId, AjaxRequestTarget target) {

	}
	
	@Override protected CharSequence getOnReadyScript() {
		return getRebindScript();
	}

	public String getRebindScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("\t$('" + dragSelector + "').draggable(" + draggableOptions.toString(false) + ");\n");
		sb.append("\t$('" + dropSelector + "').addClass('droppable-active').droppable(" + droppableOptions.toString(false) + ");\n");
		return sb.toString();
	}
}
