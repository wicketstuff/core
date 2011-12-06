/*
 * $Id: Slider.java 5132 2006-03-26 02:13:41 -0800 (Sun, 26 Mar 2006)
 * jdonnerstag $ $Revision: 5189 $ $Date: 2006-03-26 02:13:41 -0800 (Sun, 26 Mar
 * 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.markup.html.slider;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;


/**
 * Slider component based on the Slider of Yahoo UI Library.
 * 
 * @deprecated see {@link YuiHorizSlider} for alternative. 
 * 
 * @author Eelco Hillenius
 * @author Joshua Lim
 */
public class Slider extends Panel implements IHeaderContributor
{
	private static final long serialVersionUID = 1L;

	/** Ref to CSS file */
	private static final ResourceReference CSS = new ResourceReference(Slider.class,
			"css/slider.css");

	/**
	 * The id of the background element.
	 */
	private String backgroundElementId;

	/**
	 * The id of the image element.
	 */
	private String imageElementId;

	/**
	 * The JavaScript variable name of the slider component.
	 */
	private String javaScriptId;

	/**
	 * the form Element that holds the value for the slider, ususally a
	 * Textfield or a Hidden Field.
	 */
	private FormComponent element;

	/**
	 * default divisor to divide the slider value by so that you can get a nicer
	 * range of numbers and not limited by the pixel size
	 */
	private float divisor;

	/**
	 * the slider settings
	 */
	SliderSettings sliderSettings;

	/**
	 * logger
	 */
	private static final Logger log = LoggerFactory.getLogger(Slider.class);

	/**
	 * Construct
	 * 
	 * @param id
	 * @param model
	 * @param element
	 * @param settings
	 */
	public Slider(String id, IModel model, final FormComponent element,
			final SliderSettings settings)
	{
		this(id, model, element, settings, 1);
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @param element
	 * @param settings
	 * @param divisor
	 *            Use this float to divide out the pixel value to give a nicer
	 *            value e.g. the total size in pixel specified in the
	 *            SliderSettings is 300 pixels (from -150 to 150) and you want
	 *            the values of the slider to be -15 to 15 then the divisor will
	 *            be 10. the default divisor is 1 so that the orignal value is
	 *            retained
	 */
	@SuppressWarnings("serial")
	public Slider(String id, IModel model, final FormComponent element,
			final SliderSettings settings, final float divisor)
	{
		super(id, model);

		this.sliderSettings = settings;
		add(YuiHeaderContributor.forModule("animation"));
		add(YuiHeaderContributor.forModule("slider"));
		add(CSSPackageResource.getHeaderContribution(getCSS()));

		this.divisor = divisor;

		/*
		 * default settings if null
		 */
		if (settings == null)
		{
			// error
		}

		/* handle form element */
		if (element != null)
		{
			element.setOutputMarkupId(true);
			this.element = element;
		}

		IModel<Map<String, Object>> variablesModel = new AbstractReadOnlyModel<Map<String, Object>>()
		{
			private static final long serialVersionUID = 1L;

			/** cached variables; we only need to fill this once. */
			private Map<String, Object> variables;

			/**
			 * @see wicket.model.AbstractReadOnlyModel#getObject(wicket.Component)
			 */
			@Override
			public Map<String, Object> getObject()
			{
				if (variables == null)
				{
					this.variables = new HashMap<String, Object>(8);
					variables.put("javaScriptId", javaScriptId);
					variables.put("backGroundElementId", backgroundElementId);
					variables.put("imageElementId", imageElementId);
					variables.put("leftUp", settings.getLeftUp());
					variables.put("rightDown", settings.getRightDown());
					variables.put("tick", settings.getTick());
					variables.put("formElementId", element.getMarkupId());
					variables.put("startValue", element.getModelObject());
					variables.put("divisor", getDivisor());
				}
				return variables;
			}
		};

		add(TextTemplateHeaderContributor.forJavaScript(Slider.class, "init.js", variablesModel));

		/*
		 * LEFT Corner Images & Tick Marks
		 */

		Image leftTickImg = new Image("leftTickImg", settings.getLeftTickResource());
		leftTickImg.add(new AttributeModifier("onclick", true, new AbstractReadOnlyModel<String>()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				int i = Math.round(Integer.parseInt(settings.getTick()) * divisor);
				return javaScriptId + ".setValue(" + javaScriptId + ".getXValue() - " + i + ");";
			}
		}));
		add(leftTickImg.setVisible(settings.isShowTicks()));

		Image leftCornerImg = new Image("leftCornerImg", settings.getLeftCornerResource());
		leftCornerImg.add(new AttributeModifier("onclick", true,
				new AbstractReadOnlyModel<String>()
				{
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject()
					{
						return javaScriptId + ".setValue(-" + settings.getLeftUp() + ")";
					}
				}));
		add(leftCornerImg);

		/*
		 * RIGHT Corner Images & Tick Marks
		 */

		Image rightCornerImg = new Image("rightCornerImg", settings.getRightCornerResource());
		rightCornerImg.add(new AttributeModifier("onclick", true,
				new AbstractReadOnlyModel<String>()
				{
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject()
					{
						return javaScriptId + ".setValue(" + settings.getRightDown() + ")";
					}
				}));
		add(rightCornerImg);

		Image rightTickImg = new Image("rightTickImg", settings.getRightTickResource());
		rightTickImg.add(new AttributeModifier("onclick", true, new AbstractReadOnlyModel<String>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				int i = Math.round(Integer.parseInt(settings.getTick()) * divisor);
				return javaScriptId + ".setValue(" + javaScriptId + ".getXValue() + " + i + ");";
			}
		}));
		add(rightTickImg.setVisible(settings.isShowTicks()));

		/*
		 * Background Div
		 */

		WebMarkupContainer backgroundElement = new WebMarkupContainer("backgroundDiv");
		backgroundElement.add(new AttributeModifier("id", true, new PropertyModel<String>(this,
				"backgroundElementId")));
		backgroundElement.add(new AttributeModifier("style", true,
				new AbstractReadOnlyModel<String>()
				{
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject()
					{

						settings.getBackground().add(
								"background",
								"url("
										+ RequestCycle.get().urlFor(
												settings.getBackgroundResource()) + ") repeat-x");

						return settings.getBackground().getStyle();
					}

				}));
		add(backgroundElement);

		/*
		 * Element Div and Thumb Div
		 */

		WebMarkupContainer imageElement = new WebMarkupContainer("handleDiv");
		imageElement.add(new AttributeModifier("id", true, new PropertyModel<String>(this,
				"imageElementId")));
		imageElement.add(new AttributeModifier("style", true, new Model<String>(settings
				.getHandle().getStyle())));

		WebMarkupContainer thumbElement = new WebMarkupContainer("thumbDiv");
		thumbElement.add(new AttributeModifier("style", true, new AbstractReadOnlyModel<String>()
		{

			@Override
			public String getObject()
			{
				settings.getThumb().add(
						"background",
						"url(" + RequestCycle.get().urlFor(settings.getThumbResource())
								+ ") no-repeat");
				return settings.getThumb().getStyle();
			}

		}));

		imageElement.add(thumbElement);
		backgroundElement.add(imageElement);

	}

	/**
	 * Contruct. creates a default Slider.
	 * 
	 * @param id
	 * @param model
	 * @param leftUp
	 * @param rightDown
	 * @param tick
	 * @param element
	 */

	public Slider(String id, IModel model, final int leftUp, final int rightDown, final int tick,
			final FormComponent element)
	{
		this(id, model, element, SliderSettings.getDefault(leftUp, rightDown, tick));
	}

	/**
	 * Gets backgroundElementId.
	 * 
	 * @return backgroundElementId
	 */
	public final String getBackgroundElementId()
	{
		return backgroundElementId;
	}

	/**
	 * Gets imageElementId.
	 * 
	 * @return imageElementId
	 */
	public final String getImageElementId()
	{
		return imageElementId;
	}

	/**
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		Integer value = (Integer)this.element.getModelObject();

		if (value == null)
			value = (Integer)this.element.getConvertedInput();

		if (value == null)
			value = getSliderSettings().getStartValue();

		response.renderOnLoadJavascript("init" + javaScriptId + "(" + value + ");");
	}

	/**
	 * TODO implement
	 */
	public void updateModel()
	{
		log.info("updateModel");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#onAttach()
	 */
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		// initialize lazily
		if (backgroundElementId == null)
		{
			// assign the markup id
			String id = getMarkupId();
			backgroundElementId = id + "Bg";
			imageElementId = id + "Img";
			javaScriptId = backgroundElementId + "JS";
		}
	}


	public float getDivisor()
	{
		return divisor;
	}

	public void setDivisor(float divisor)
	{
		this.divisor = divisor;
	}

	protected ResourceReference getCSS()
	{
		return CSS;
	}

	public SliderSettings getSliderSettings()
	{
		return sliderSettings;
	}

	public void setSliderSettings(SliderSettings sliderSettings)
	{
		this.sliderSettings = sliderSettings;
	}

}
