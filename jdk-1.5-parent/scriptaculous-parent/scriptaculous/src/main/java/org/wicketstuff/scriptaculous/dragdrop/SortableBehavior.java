package org.wicketstuff.scriptaculous.dragdrop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.scriptaculous.JavascriptBuilder;

/**
 * add sortable behavior to a given component.
 * inherits all configuration options from DraggableBehavior.
 * 
 * @see DraggableBehavior
 * @see http://github.com/madrobby/scriptaculous/wikis/sortable
 */
public class SortableBehavior extends DraggableBehavior {
	private static final Logger LOG = LoggerFactory.getLogger(SortableBehavior.class);
	
	private static final long serialVersionUID = 1L;
	private Map<String, Object> options = new HashMap<String, Object>();

  @Override
	protected final void onComponentRendered() {
		super.onComponentRendered();

		options.put("onUpdate", new JavascriptBuilder.JavascriptFunction(
				"function(element) { wicketAjaxGet('" + this.getCallbackUrl()
						+ "&' + Sortable.serialize(element)); }"));

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new Sortable('" + getComponent().getMarkupId() + "', ");
		builder.addOptions(options);
		builder.addLine(");");

		Response response = RequestCycle.get().getResponse();
		response.write(builder.buildScriptTagString());
	}

	@Override
	protected final void respond(AjaxRequestTarget target) {
		String[] parameters = getComponent().getRequest().getParameters(getComponent().getMarkupId() + "[]");

		if (parameters == null) {
			LOG.warn("Invalid parameters passed in Ajax request.");
			return;
		}

		@SuppressWarnings("unchecked")
		List<Object> items = (List<Object>) getComponent().getDefaultModelObject();
		List<Object> originalItems = new ArrayList<Object>(items);
		for (int index = 0; index < items.size(); index++) {
			int newIndex = Integer.parseInt(parameters[index]);
			if (!items.get(index).equals(items.get(newIndex))) {
				LOG.info("Moving sortable object from location " + newIndex + " to " + index);
			}
			items.set(index, originalItems.get(newIndex));
		}
		getComponent().modelChanged();
		target.addComponent(getComponent());
	}

	public final void setScrollSensitivity(int value) {
		options.put("scrollSensitivity", value);
	}
	
	public final void setScrollSpeed(int value) {
		options.put("scrollSpeed", value);
	}
	
	public final void setDropOnEmpty(boolean value) {
		options.put("dropOnEmpty", value);
	}
	
	public final void setHoverClass(String className) {
		options.put("hoverclass", className);
	}
}
