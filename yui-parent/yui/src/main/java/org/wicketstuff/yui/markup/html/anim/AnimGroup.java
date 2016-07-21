package org.wicketstuff.yui.markup.html.anim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.StringHeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * An AnimGroup groups the options
 * 
 * @author cptan
 * 
 */
public class AnimGroup extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	private String javaScriptId;

	private List<AnimOption> animOptionList;

	private String easing;

	private double duration;

	private int maxSelection;

	private String selectedValues = "";

	private String valueId;

	/**
	 * Creates an AnimGroup
	 * 
	 * @param id -
	 *            wicket id
	 * @param settings -
	 *            defines the animation settings
	 * @param element -
	 *            component to contains the selected options
	 */
	public AnimGroup(String id, AnimSettings settings,
			final FormComponent element) {
		super(id);
		add(YuiHeaderContributor.forModule("animation"));
		this.animOptionList = settings.getAnimOptionList();
		this.easing = settings.getEasing();
		this.duration = settings.getDuration();
		this.maxSelection = settings.getMaxSelection();
		this.valueId = element.getId() + "_" + id;

		for (int i = 0; i < animOptionList.size(); i++) {
			AnimOption animSelectOption = (AnimOption) animOptionList.get(i);
			String value = animSelectOption.getSelectedValue();
			if (selectedValues.equals("")) {
				selectedValues = "'" + value + "'";
			} else {
				selectedValues = selectedValues + ",'" + value + "'";
			}
		}

		if (element != null) {
			element.add(new AttributeModifier("id", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject() {
							return element.getId() + "_" + javaScriptId;
						}
					}));
		}
		add(element);
	}

	/**
	 * Get the animOptionList
	 * 
	 * @return the animOptionList
	 */
	public List<AnimOption> getAnimOptionList() {
		return animOptionList;
	}

	/**
	 * Get the duration
	 * 
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Get the easing
	 * 
	 * @return the easing
	 */
	public String getEasing() {
		return easing;
	}

	/**
	 * Get the JavaScriptId
	 * 
	 * @return the javaScriptId
	 */
	public String getJavaScriptId() {
		return javaScriptId;
	}

	/**
	 * Get the maximum selection allowed
	 * 
	 * @return the maxSelection
	 */
	public int getMaxSelection() {
		return maxSelection;
	}

	/**
	 * Get the selected values
	 * 
	 * @return the selectedValues
	 */
	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 * Set the animOptionList
	 * 
	 * @param animOptionList
	 *            the animOptionList to set
	 */
	public void setAnimOptionList(List<AnimOption> animOptionList) {
		this.animOptionList = animOptionList;
	}

	/**
	 * Set the duration
	 * 
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * Set the easing
	 * 
	 * @param easing
	 *            the easing to set
	 */
	public void setEasing(String easing) {
		this.easing = easing;
	}

	/**
	 * Set the javaScriptId
	 * 
	 * @param javaScriptId
	 *            the javaScriptId to set
	 */
	public void setJavaScriptId(String javaScriptId) {
		this.javaScriptId = javaScriptId;
	}

	/**
	 * Set the maximum selections allowed
	 * 
	 * @param maxSelection
	 *            the maxSelection to set
	 */
	public void setMaxSelection(int maxSelection) {
		this.maxSelection = maxSelection;
	}

	/**
	 * Set the selected values
	 * 
	 * @param selectedValues
	 *            the selectedValues to set
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * Initialize the init.js which is shared among a group of options
	 * 
	 * @return a String representation of the init.js
	 */
	protected String getJavaScriptComponentInitializationScript() {
		PackagedTextTemplate template = new PackagedTextTemplate(
				AnimGroup.class, "init.js");
		Map<String, Object> variables = new HashMap<String, Object>(7);
		variables.put("javaScriptId", javaScriptId);
		variables.put("easing", "'" + easing + "'");
		variables.put("duration", new Double(duration));
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("noOfBoxes", new Integer(animOptionList.size()));
		variables.put("selectedValues", selectedValues);
		variables.put("valueId", "'" + valueId + "'");
		template.interpolate(variables);
		return template.getString();
	}

	/**
	 * Get the markup Id on attach
	 */
	protected void onBeforeRender() {
		super.onBeforeRender();
		javaScriptId = getMarkupId();

		String js = "\n<script type=\"text/javascript\">"
				+ getJavaScriptComponentInitializationScript()
				+ "\n</script>\n";
		add(new StringHeaderContributor(js));
	}

	/**
	 * 
	 */
	protected void onComponentTagBody(MarkupStream markupStream,
			ComponentTag openTag) {
		super.onComponentTagBody(markupStream, openTag);
	}

}
