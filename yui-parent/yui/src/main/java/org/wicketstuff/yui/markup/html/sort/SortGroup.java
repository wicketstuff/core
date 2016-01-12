package org.wicketstuff.yui.markup.html.sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.StringHeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.template.PackagedTextTemplate;

import org.wicketstuff.yui.helper.YuiImage;

/**
 * A SortGroup groups the images
 * 
 * @author cptan
 * 
 */
public class SortGroup extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	private String javaScriptId;

	private String mode;

	private List<YuiImage> sortList;

	private String id;

	private String valueId;

	/**
	 * Creates a SortGroup
	 * 
	 * @param id -
	 *            wicket id
	 * @param settings -
	 *            defines the animation settings
	 * @param element -
	 *            component to contains the sorting order
	 */
	public SortGroup(String id, SortSettings settings,
			final FormComponent element) {
		super(id);
		this.mode = settings.getMode();
		this.sortList = settings.getSortList();
		this.id = id;
		this.valueId = element.getId() + "_" + id;

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
	 * Initialize the init.js which is shared among a a group of images
	 * 
	 * @return a String representation of the init.js
	 */
	protected String getJavaScriptComponentInitializationScript() {
		String sortValues = "";
		String sortIds = "";

		for (int i = 0; i < sortList.size(); i++) {
			YuiImage yuiImage = (YuiImage) sortList.get(i);
			if (sortValues.equals("") || sortValues == "") {
				sortValues = "'" + yuiImage.getDesc() + "'";
				sortIds = "'dd" + i + "_" + id + "'";
			} else {
				sortValues = sortValues + ", '" + yuiImage.getDesc() + "'";
				sortIds = sortIds + ", 'dd" + i + "_" + id + "'";
			}
		}
		PackagedTextTemplate template = new PackagedTextTemplate(
				SortGroup.class, "init.js");
		Map<String, Object> variables = new HashMap<String, Object>(4);
		variables.put("javaScriptId", javaScriptId);
		variables.put("mode", "YAHOO.util.DDM." + mode);
		variables.put("sortValues", sortValues);
		variables.put("sortIds", sortIds);
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
}
