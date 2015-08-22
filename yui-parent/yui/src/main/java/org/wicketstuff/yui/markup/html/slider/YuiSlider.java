package org.wicketstuff.yui.markup.html.slider;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderModule;


/**
 * Yui Slider is a based on {@link http://developer.yahoo.com/yui/slider/}
 * 
 * @author josh
 * 
 */
public abstract class YuiSlider extends Panel
{

	private static final long serialVersionUID = 1L;

	private static final String MODULE_NAME = "wicket_yui_slider";

	private static final ResourceReference MODULE_JS_REF = new ResourceReference(YuiSlider.class,
			"YuiSlider.js");

	private static final String[] MODULE_REQUIRES = { "slider", "animation" };

	YuiSliderSettings sliderSettings;

	WebMarkupContainer sliderLeftUp;

	WebMarkupContainer sliderRightDown;

	WebMarkupContainer sliderBg;

	WebMarkupContainer sliderThumb;

	public YuiSlider(String id, final YuiSliderSettings settings)
	{
		super(id);
		add(CSSPackageResource.getHeaderContribution(YuiSlider.class, "css/yuislider.css"));

		setOutputMarkupId(true);

		this.sliderSettings = settings;

		// add this new Yui Loader Module
		add(YuiLoaderContributor.addModule(new YuiLoaderModule(MODULE_NAME,
				YuiLoaderModule.ModuleType.js, MODULE_JS_REF, MODULE_REQUIRES)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public String onSuccessJS()
			{
				StringBuffer buffer = new StringBuffer();

				buffer.append("var " + getSliderVar() + " = " + getSliderTypJS() + "(\""
						+ getSliderBgId() + "\",\"" + getSliderThumbId() + "\","
						+ YuiSliderSettings.LEFT_DOWN + "," + settings.getLength() + ","
						+ settings.getTickValue() + ");");

				buffer.append(getSliderVar() + ".animate = true;");
				buffer.append(getSliderVar() + ".setValue(" + settings.getStartValue() + ");");
				buffer.append(getSliderVar() + ".subscribe(\"change\"," + getOnChangeJSFunc()
						+ ");");

				buffer.append(getOnClickJSFunc(sliderLeftUp.getMarkupId(), getSliderVar()
						+ ".setValue(0);"));
				buffer.append(getOnClickJSFunc(sliderRightDown.getMarkupId(), getSliderVar()
						+ ".setValue(" + settings.getLength() + ");"));

				return buffer.toString();
			}

			private String getOnClickJSFunc(String id, String jsFunc)
			{
				StringBuffer buffer = new StringBuffer();

				buffer.append("YAHOO.util.Event.addListener(\"" + id + "\", \"click\", ");
				buffer.append("function(e) { " + jsFunc + "}");
				buffer.append(");");
				return buffer.toString();
			}

		}));

		if (settings == null)
		{
			// error
		}

		WebMarkupContainer yuislider;
		add(yuislider = newSliderComponent("yuislider", "class", getCssClass()));

		yuislider.add(sliderBg = newSliderComponent("yuislider-bg", getBackgroundStyle()));
		sliderBg.add(sliderThumb = newSliderComponent("yuislider-thumb", getThumbStyle()));
		yuislider.add(sliderLeftUp = newSliderComponent("yuislider-bg-lu", getLeftUpStyle()));
		yuislider.add(sliderRightDown = newSliderComponent("yuislider-bg-rd", getRightDownStyle()));


		// thumb
		Image thumbImg = new Image("thumbImg", settings.getThumbResource());
		sliderThumb.add(thumbImg);
	}

	private WebMarkupContainer newSliderComponent(String id, String style)
	{
		return newSliderComponent(id, "style", style);
	}

	/**
	 * create a slider Component
	 * 
	 * @param id
	 * @param style
	 * @param string
	 * @return
	 */
	private WebMarkupContainer newSliderComponent(String id, String attr, final String style)
	{
		WebMarkupContainer comp = new WebMarkupContainer(id);
		comp.add(new AttributeModifier(attr, true, new AbstractReadOnlyModel<String>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return style;
			}

		}));
		comp.setOutputMarkupId(true);
		return comp;
	}

	private String getSliderId()
	{
		return YuiSlider.this.getMarkupId();
	}

	protected String getSliderVar()
	{
		return "var_" + getSliderId();
	}

	protected String getSliderThumbId()
	{
		return sliderThumb.getMarkupId();
	}

	protected String getSliderBgId()
	{
		return sliderBg.getMarkupId();
	}

	public YuiSliderSettings getSliderSettings()
	{
		return sliderSettings;
	}

	public void setSliderSettings(YuiSliderSettings sliderSettings)
	{
		this.sliderSettings = sliderSettings;
	}

	protected abstract String getSliderTypJS();

	protected abstract String getBackgroundStyle();

	protected abstract String getThumbStyle();

	protected abstract String getLeftUpStyle();

	protected abstract String getRightDownStyle();

	protected String getCssClass()
	{
		return "yuislider";
	}

	/**
	 * override this method to provide any onChange event that you want.
	 * 
	 * @return
	 */
	protected String getOnChangeJSFunc()
	{
		return "function(offsetFromStart) { }";
	}
}
