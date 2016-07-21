package org.wicketstuff.yui.markup.html.animselect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * Grouping of the options
 * 
 * @author cptan
 */
public class AnimSelectOptionGroup extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	private List<AnimSelectOption> animSelectOptionList;

	private double duration;

	private String easing;

	private String javaScriptId;

	private int maxSelection;

	private String selectedValues = "";

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param settings
	 */
	public AnimSelectOptionGroup(String id, AnimSelectSettings settings)
	{
		super(id);
		add(YuiHeaderContributor.forModule("animation"));
		this.animSelectOptionList = settings.getAnimSelectOptionList();
		this.easing = settings.getEasing();
		this.duration = settings.getDuration();
		this.maxSelection = settings.getMaxSelection();

		for (int i = 0; i < animSelectOptionList.size(); i++) {
			AnimSelectOption animSelectOption = animSelectOptionList.get(i);
			String value = animSelectOption.getSelectedValue();
			if (selectedValues.equals("")) {
				selectedValues = "'" + value + "'";
			} else {
				selectedValues = selectedValues + ",'" + value + "'";
			}
		}

		Label initialization = new Label("init", new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject()
			{
				return getJavaScriptComponentInitializationScript();
			}
		});
		initialization.setEscapeModelStrings(false);
		add(initialization);
	}

	/**
	 * Initialize the init.js which is shared among a group of options
	 * 
	 * @return
	 */
	protected String getJavaScriptComponentInitializationScript()
	{
		PackagedTextTemplate template = new PackagedTextTemplate(AnimSelectOptionGroup.class, "init.js");
		Map<String, Object> variables = new HashMap<String, Object>(6);
		variables.put("javaScriptId", javaScriptId);
		variables.put("easing", "YAHOO.util.Easing." + easing);
		variables.put("duration", new Double(duration));
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("noOfBoxes", new Integer(animSelectOptionList.size()));
		variables.put("selectedValues", selectedValues);
		template.interpolate(variables);
		return template.getString();
	}

	/**
	 * Get the markup Id on attach
	 */
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		javaScriptId = getMarkupId();
	}
}
