package org.wicketstuff.yui.markup.html.anim;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.yui.helper.CSSInlineStyle;
import org.wicketstuff.yui.helper.YuiImage;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * An AnimBox is a component which consists of four images:
 * <p> - the unselected image
 * <p> - the unselected mouseover image
 * <p> - the selected image
 * <p> - the selected mouseover image
 * 
 * @author cptan
 * 
 */
public class AnimBox extends Panel {

	/**
	 * Represent one of the images for each option
	 * 
	 * @author cptan
	 * 
	 */
	private final class AnimSelectBox extends FormComponent implements
			Serializable {
		private static final long serialVersionUID = 1L;

		public AnimSelectBox(final String id, final int count,
				final String name, YuiImage yuiImage) {
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel() {
				private static final long serialVersionUID = 1L;

				public Object getObject() {
					return name + count + "_" + javaScriptId;
				}
			}));
			add(new AttributeModifier("style", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject() {
							if (name.equals("DefaultImg")) {
								List aCSSInlineStyleList = settings
										.getDefaultImgStyleList();
								CSSInlineStyle aCSSInlineStyle = (CSSInlineStyle) aCSSInlineStyleList
										.get(0);
								return aCSSInlineStyle.getStyle();
							} else if (name.equals("DefaultImgOver")) {
								List aCSSInlineStyleList = settings
										.getDefaultImgOverStyleList();
								CSSInlineStyle aCSSInlineStyle = (CSSInlineStyle) aCSSInlineStyleList
										.get(0);
								return aCSSInlineStyle.getStyle();
							} else if (name.equals("SelectedImg")) {
								List aCSSInlineStyleList = settings
										.getSelectedImgStyleList();
								CSSInlineStyle aCSSInlineStyle = (CSSInlineStyle) aCSSInlineStyleList
										.get(0);
								return aCSSInlineStyle.getStyle();
							} else if (name.equals("SelectedImgOver")) {
								List aCSSInlineStyleList = settings
										.getSelectedImgOverStyleList();
								CSSInlineStyle aCSSInlineStyle = (CSSInlineStyle) aCSSInlineStyleList
										.get(0);
								return aCSSInlineStyle.getStyle();
							} else
								return new String("");
						}
					}));
		}
	}

	/**
	 * Get the image's width and height
	 * 
	 * @author cptan
	 * 
	 */
	private final class ImgStyle extends FormComponent implements Serializable {
		private static final long serialVersionUID = 1L;

		public ImgStyle(final String id) {
			super(id);
			add(new AttributeModifier("style", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject() {
							return "width:" + settings.getWidth()
									+ "px; height:" + settings.getHeight()
									+ "px";
						}
					}));
		}
	}

	private static final long serialVersionUID = 1L;

	private AnimSettings settings;

	private String javaScriptId;

	private String easing;

	private double duration;

	private int maxSelection;

	private String message;

	/**
	 * Creates an AnimBox
	 * 
	 * @param id -
	 *            wicket id
	 * @param index -
	 *            auto-generated through the listview
	 * @param animSelectOption -
	 *            defines the four images
	 * @param settings -
	 *            defines the animation settings
	 */
	public AnimBox(String id, final int index, AnimOption animSelectOption,
			AnimSettings settings) {
		super(id);
		add(YuiHeaderContributor.forModule("animation"));
		this.settings = settings;
		this.easing = settings.getEasing();
		this.duration = settings.getDuration();
		this.maxSelection = settings.getMaxSelection();
		this.message = settings.getMessage();

		ImgStyle style = new ImgStyle("imgStyle");
		add(style);
		style.add(new AnimSelectBox("defaultImg", index, "DefaultImg",
				animSelectOption.getDefaultImg()));
		style.add(new AnimSelectBox("defaultImgOver", index, "DefaultImgOver",
				animSelectOption.getDefaultImgOver()));
		style.add(new AnimSelectBox("selectedImg", index, "SelectedImg",
				animSelectOption.getSelectedImg()));
		style.add(new AnimSelectBox("selectedImgOver", index,
				"SelectedImgOver", animSelectOption.getSelectedImgOver()));

		Label animSelect = new Label("animSelectScript",
				new AbstractReadOnlyModel() {
					private static final long serialVersionUID = 1L;

					public Object getObject() {
						return getAnimSelectInitializationScript(index);
					}
				});
		animSelect.setEscapeModelStrings(false);
		add(animSelect);
	}

	/**
	 * Initialize the anim.js for each option
	 * 
	 * @param index -
	 *            auto-generated through the listview
	 * @return a String representation of the anim.js
	 */
	protected String getAnimSelectInitializationScript(int index) {
		PackagedTextTemplate template = new PackagedTextTemplate(AnimBox.class,
				"anim.js");
		Map<String, Object> variables = new HashMap<String, Object>(7);
		variables.put("javaScriptId", javaScriptId);
		variables.put("boxId", new Integer(index));
		variables.put("easing", "YAHOO.util.Easing." + easing);
		variables.put("duration", new Double(duration));
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("noOfBoxes", new Integer(settings.getAnimOptionList()
				.size()));
		if (message == null || message.equals("")) {
			message = "Up to " + maxSelection + " selections allowed!";
		}
		variables.put("message", message);
		template.interpolate(variables);
		return template.getString();
	}

	/**
	 * Get the markup Id of the super parent class on attach
	 */
	protected void onBeforeRender() {
		super.onBeforeRender();
		javaScriptId = findParent(AnimGroup.class).getMarkupId();
	}
}
