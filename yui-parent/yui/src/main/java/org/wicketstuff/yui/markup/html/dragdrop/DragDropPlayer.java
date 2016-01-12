package org.wicketstuff.yui.markup.html.dragdrop;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.yui.helper.YuiImage;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

public class DragDropPlayer extends Panel {

	private static final long serialVersionUID = 1L;

	private int index;

	private YuiImage slot;

	private DragDropSettings settings;

	private String javaScriptId;

	public DragDropPlayer(final String id, final int index, YuiImage slot,
			DragDropSettings settings) {
		super(id);
		add(YuiHeaderContributor.forModule("dragdrop"));
		add(HeaderContributor.forJavaScript(DragDropPlayer.class, "DDPlayer.js"));
		this.index = index;
		this.slot = slot;
		this.settings = settings;

		Label slotLabel = new Label("dragDropPlayerScript",
				new AbstractReadOnlyModel() {
					private static final long serialVersionUID = 1L;

					public Object getObject() {
						return getDragDropPlayerInitializationScript(id + ""
								+ index);
					}
				});
		slotLabel.setEscapeModelStrings(false);
		add(slotLabel);
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the settings
	 */
	public DragDropSettings getSettings() {
		return settings;
	}

	/**
	 * @return the slot
	 */
	public YuiImage getSlot() {
		return slot;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	public void setSettings(DragDropSettings settings) {
		this.settings = settings;
	}

	/**
	 * @param slot
	 *            the slot to set
	 */
	public void setSlot(YuiImage slot) {
		this.slot = slot;
	}

	protected String getDragDropPlayerInitializationScript(String playerId) {
		PackagedTextTemplate template = new PackagedTextTemplate(
				DragDropPlayer.class, "dragdropPlayer.js");
		Map<String, Object> variables = new HashMap<String, Object>(4);
		variables.put("javaScriptId", javaScriptId);
		variables.put("id", playerId);
		variables.put("dragableSlot", settings.getDragableSlotList().getId());
		variables.put("targetSlot", settings.getTargetSlotList().getId());
		template.interpolate(variables);
		return template.getString();
	}

	protected void onBeforeRender() {
		super.onBeforeRender();
		javaScriptId = findParent(DragDropGroup.class).getMarkupId();
	}
}
