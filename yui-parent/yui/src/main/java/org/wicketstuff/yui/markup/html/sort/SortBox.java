package org.wicketstuff.yui.markup.html.sort;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.yui.helper.CSSInlineStyle;
import org.wicketstuff.yui.helper.YuiImage;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * 
 * @author cptan
 * 
 */
public class SortBox extends Panel {

	/**
	 * Get the box's style
	 * 
	 * @author cptan
	 * 
	 */
	private final class Box extends FormComponent implements Serializable {
		private static final long serialVersionUID = 1L;

		public Box(final String id, final int count, final String name,
				YuiImage yuiImage) {
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object getObject() {
					return "dd" + count + "_" + javaScriptId;
				}
			}));
			add(new AttributeModifier("style", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						@Override
						public Object getObject() {
							if (name.equals("dd")) {
								List<CSSInlineStyle> aInlineStyleList = settings
										.getImgStyleList();
								CSSInlineStyle aInlineStyle = aInlineStyleList
										.get(count);
								return aInlineStyle.getStyle();
							} else {
								return new String("");
							}
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

						@Override
						public Object getObject() {
							return "width:" + settings.getWidth()
									+ "px; height:" + settings.getHeight()
									+ "px";
						}
					}));
		}
	}

	private static final ResourceReference DDSWAP = new CompressedResourceReference(SortBox.class, "DDSwap.js");
	
	private static final long serialVersionUID = 1L;

	private String javaScriptId;

	private String mode;

	private SortSettings settings;

	/**
	 * Defines a SortSettings object
	 * 
	 * @param id -
	 *            wicket id
	 * @param index -
	 *            auto-generated through the listview
	 * @param image -
	 *            defines the image
	 * @param settings -
	 *            defines the sort settings
	 */
	public SortBox(String id, final int index, YuiImage image,
			SortSettings settings) {
		super(id);
		add(YuiHeaderContributor.forModule("dragdrop"));
		add(HeaderContributor.forJavaScript(getDDSWAP()));
		
		this.settings = settings;
		mode = settings.getMode();

		ImgStyle style = new ImgStyle("imgStyle");
		add(style);
		style.add(new Box("dd", index, "dd", image));

		Label sortLabel = new Label("sortScript", new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject() {
				return getAnimSelectInitializationScript(index);
			}
		});
		sortLabel.setEscapeModelStrings(false);
		add(sortLabel);
	}

	/**
	 * Initialize the sort.js for each option
	 * 
	 * @param boxId -
	 *            auto-generated through the listview
	 * @return a String representation of the sort.js
	 */
	protected String getAnimSelectInitializationScript(int boxId) {
		PackagedTextTemplate template = new PackagedTextTemplate(SortBox.class,
				"sort.js");
		Map<String, Object> variables = new HashMap<String, Object>(5);
		variables.put("javaScriptId", javaScriptId);
		variables.put("id", new Integer(boxId));
		variables.put("classId", "'dd" + boxId + "_" + javaScriptId + "'");
		variables.put("groupId", "'" + javaScriptId + "'");

		if (mode.equals("INTERSECT")) {
			variables.put("isIntersect", "_i");
		} else if (mode.equals("POINT")) {
			variables.put("isIntersect", "");
		}

		template.interpolate(variables);
		return template.getString();
	}

	/**
	 * Get the markup Id of the super parent class on attach
	 */
	protected void onBeforeRender() {
		super.onBeforeRender();
		javaScriptId = findParent(SortGroup.class).getMarkupId();
	}

    protected ResourceReference getDDSWAP()
    {
        return DDSWAP;
    }
}
