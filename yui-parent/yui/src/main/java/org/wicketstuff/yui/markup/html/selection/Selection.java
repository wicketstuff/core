package org.wicketstuff.yui.markup.html.selection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.yui.helper.CSSInlineStyle;
import org.wicketstuff.yui.helper.YuiAttribute;
import org.wicketstuff.yui.helper.YuiImage;
import org.wicketstuff.yui.helper.YuiTextBox;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

public class Selection extends Panel {
    
	private final class SelectionBox extends FormComponent implements
			Serializable {
		private static final long serialVersionUID = 1L;

		public SelectionBox(final String id, final int imageId) {
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object getObject() {
					boxCount++;
					return elementId + "" + boxCount;
				}
			}));
			add(new AttributeModifier("style", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						@Override
						public Object getObject() {
							List<CSSInlineStyle> aCSSInlineStyleList = settings
									.getCSSInlineStyleList();
							CSSInlineStyle aCSSInlineStyle = aCSSInlineStyleList
									.get(imageId);
							return aCSSInlineStyle.getStyle();
						}
					}));
		}
	}

	private static final long serialVersionUID = 1L;

	private int boxCount = -1;

	private String boxes;

	private final List boxList = new Vector();

	private final ListView boxListView;

	private int count = 0;

	private String counts;

	private double duration;

	private String easing;

	private String elementId;

	private String event;

	private String javaScriptId;

	private List list;

	private int maxSelection;

	private String message;

	private SelectionSettings settings;

	private YuiAttribute yuiAttribute;

	public Selection(String id, final SelectionSettings settings) {
		super(id);
		add(YuiHeaderContributor.forModule("animation"));
		add(HeaderContributor.forCss(Selection.class, "css/style.css"));

		this.elementId = id;
		this.yuiAttribute = settings.getYuiAttribute();
		this.easing = settings.getEasing();
		this.duration = settings.getDuration();
		this.event = settings.getEvent();
		this.maxSelection = settings.getMaxSelection();
		this.message = settings.getMessage();
		this.settings = settings;
		this.list = settings.getList();

		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				boxes = "'" + elementId + "" + i + "'";
				counts = "0";
			} else {
				boxes = boxes + ", '" + elementId + "" + i + "'";
				counts = counts + ", 0 ";
			}
			boxList.add(0, list.get(i));
		}

		Label initialization = new Label("initialization",
				new AbstractReadOnlyModel() {
					private static final long serialVersionUID = 1L;

					@Override
					public Object getObject() {
						return getJavaScriptComponentInitializationScript();
					}
				});

		initialization.setEscapeModelStrings(false);
		add(initialization);

		Label selection = new Label("selection", new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject() {
				return getSelectionInitializationScript();
			}
		});

		selection.setEscapeModelStrings(false);
		add(selection);

		add(boxListView = new ListView("boxContainer", boxList) {
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(final ListItem listItem) {
				if (listItem.getModelObject().getClass().getSimpleName()
						.equals("YuiImage")) {
					final YuiImage aImage = (YuiImage) listItem
							.getModelObject();
					SelectionBox sBox = new SelectionBox("box", count++);
					listItem.add(sBox);
					MultiLineLabel aLabel = new MultiLineLabel("caption",
							aImage.getDesc());
					sBox.add(aLabel);
				} else {
					final YuiTextBox aTextBox = (YuiTextBox) listItem
							.getModelObject();
					SelectionBox sBox = new SelectionBox("box", count++);
					listItem.add(sBox);
					MultiLineLabel aLabel = new MultiLineLabel("caption",
							aTextBox.getDesc());
					sBox.add(aLabel);
				}
			}
		});
	}

	public void updateModel() {
	}

	protected String getJavaScriptComponentInitializationScript() {
		PackagedTextTemplate template = new PackagedTextTemplate(
				Selection.class, "init.js");
		Map<String, Object> variables = new HashMap<String, Object>(9);
		variables.put("javaScriptId", javaScriptId);
		variables.put("attributeOn", yuiAttribute.getJsScript());
		variables.put("attributeOff", yuiAttribute.getReverseJsScript());
		variables.put("easing", easing);
		variables.put("duration", new Double(duration));
		variables.put("event", "'" + event + "'");
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("boxes", boxes);
		variables.put("counts", counts);
		template.interpolate(variables);
		return template.getString();
	}

	protected String getSelectionInitializationScript() {
		PackagedTextTemplate template = null;

		if (maxSelection == 1) {
			template = new PackagedTextTemplate(Selection.class, "single.js");
		} else if (maxSelection > 1) {
			template = new PackagedTextTemplate(Selection.class, "multiple.js");
		}

		Map<String, Object> variables = new HashMap<String, Object>(10);
		variables.put("javaScriptId", javaScriptId);
		variables.put("attributeOn", yuiAttribute.getJsScript());
		variables.put("attributeOff", yuiAttribute.getReverseJsScript());
		variables.put("easing", easing);
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("duration", new Double(duration));
		variables.put("event", "'" + event + "'");
		variables.put("boxes", boxes);
		variables.put("counts", counts);
		variables.put("message", message);
		template.interpolate(variables);
		return template.getString();

	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		javaScriptId = getMarkupId();
	}

}
