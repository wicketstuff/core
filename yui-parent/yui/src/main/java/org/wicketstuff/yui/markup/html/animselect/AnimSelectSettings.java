package org.wicketstuff.yui.markup.html.animselect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.wicketstuff.yui.helper.CSSInlineStyle;
import org.wicketstuff.yui.helper.ImageResourceInfo;
import org.wicketstuff.yui.helper.YuiImage;

/**
 * Allows the user to define the anim select settings
 * 
 * @author cptan
 * 
 */
public class AnimSelectSettings implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Get the default settings
	 * 
	 * @param easing
	 * @param duration
	 * @param maxSelection
	 * @param animSelectOptionList
	 * @return
	 */
	public static AnimSelectSettings getDefault(String easing, double duration, int maxSelection, List<AnimSelectOption> animSelectOptionList)
	{
		AnimSelectSettings settings = new AnimSelectSettings();
		settings.setResources(easing, duration, maxSelection, animSelectOptionList);
		return settings;
	}

	/**
	 * Get the default settings
	 * 
	 * @param easing
	 * @param duration
	 * @param maxSelection
	 * @param message
	 * @param animSelectOptionList
	 * @return
	 */
	public static AnimSelectSettings getDefault(String easing, double duration, int maxSelection, String message, List<AnimSelectOption> animSelectOptionList)
	{
		AnimSelectSettings settings = new AnimSelectSettings();
		settings.setResources(easing, duration, maxSelection, message, animSelectOptionList);
		return settings;
	}

	private List<AnimSelectOption> animSelectOptionList;

	private List<CSSInlineStyle> defaultImgOverStyleList = new ArrayList<CSSInlineStyle>();

	private List<CSSInlineStyle> defaultImgStyleList = new ArrayList<CSSInlineStyle>();

	private double duration;

	private String easing;

	private int height;

	private int maxSelection;

	private String message;

	private List<CSSInlineStyle> selectedImgOverStyleList = new ArrayList<CSSInlineStyle>();

	private List<CSSInlineStyle> selectedImgStyleList = new ArrayList<CSSInlineStyle>();

	private int width;

	/**
	 * Constructor
	 * 
	 */
	public AnimSelectSettings()
	{
	}

	/**
	 * Set the AnimSelectOptionList
	 * 
	 * @return
	 */
	public List<AnimSelectOption> getAnimSelectOptionList()
	{
		return animSelectOptionList;
	}

	/**
	 * Get the default mouseover image style
	 * 
	 * @return
	 */
	public List<CSSInlineStyle> getDefaultImgOverStyleList()
	{
		return defaultImgOverStyleList;
	}

	/**
	 * Get the default image style
	 * 
	 * @return
	 */
	public List<CSSInlineStyle> getDefaultImgStyleList()
	{
		return defaultImgStyleList;
	}

	/**
	 * Get the duration
	 * 
	 * @return
	 */
	public double getDuration()
	{
		return duration;
	}

	/**
	 * Get the easing effect
	 * 
	 * @return
	 */
	public String getEasing()
	{
		return easing;
	}

	/**
	 * Get the height
	 * 
	 * @return
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Get the maximum selection allowed
	 * 
	 * @return
	 */
	public int getMaxSelection()
	{
		return maxSelection;
	}

	/**
	 * Get the message
	 * 
	 * @return
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Get the selected mouseover image styles
	 * 
	 * @return
	 */
	public List<CSSInlineStyle> getSelectedImgOverStyleList()
	{
		return selectedImgOverStyleList;
	}

	/**
	 * Get the selected image styles
	 * 
	 * @return
	 */
	public List<CSSInlineStyle> getSelectedImgStyleList()
	{
		return selectedImgStyleList;
	}

	/**
	 * Get the width
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Set the AnimSelectOptionList
	 * 
	 * @param animSelectOptionList
	 */
	public void setAnimSelectOptionList(List<AnimSelectOption> animSelectOptionList)
	{
		this.animSelectOptionList = animSelectOptionList;
	}

	/**
	 * Set the default mouseover image style
	 * 
	 * @param defaultImgOverStyleList
	 */
	public void setDefaultImgOverStyleList(List<CSSInlineStyle> defaultImgOverStyleList)
	{
		this.defaultImgOverStyleList = defaultImgOverStyleList;
	}

	/**
	 * Set the default image styles
	 * 
	 * @param defaultImgStyleList
	 */
	public void setDefaultImgStyleList(List<CSSInlineStyle> defaultImgStyleList)
	{
		this.defaultImgStyleList = defaultImgStyleList;
	}

	/**
	 * Set the duration
	 * 
	 * @param duration
	 */
	public void setDuration(double duration)
	{
		this.duration = duration;
	}

	/**
	 * Set the easing effects
	 * 
	 * @param easing
	 */
	public void setEasing(String easing)
	{
		this.easing = easing;
	}

	/**
	 * Set the height
	 * 
	 * @param height
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * Set the image resources
	 * 
	 * @param animSelectGroupOption
	 */
	public void setImageResources(List<AnimSelectOption> animSelectOptionList)
	{

		for (int i = animSelectOptionList.size() - 1; i >= 0; i--) {
			YuiImage defaultImg = (animSelectOptionList.get(i)).getDefaultImg();
			YuiImage defaultImgOver = (animSelectOptionList.get(i)).getDefaultImgOver();
			YuiImage selectedImg = (animSelectOptionList.get(i)).getSelectedImg();
			YuiImage selectedImgOver = (animSelectOptionList.get(i)).getSelectedImgOver();

			ResourceReference defaultImgRR = new ResourceReference(AnimSelectSettings.class, defaultImg.getFileName());
			ResourceReference defaultImgOverRR = new ResourceReference(AnimSelectSettings.class, defaultImgOver.getFileName());
			ResourceReference selectedImgRR = new ResourceReference(AnimSelectSettings.class, selectedImg.getFileName());
			ResourceReference selectedImgOverRR = new ResourceReference(AnimSelectSettings.class, selectedImgOver.getFileName());

			ImageResourceInfo defaultImgInfo = new ImageResourceInfo(defaultImgRR);
			int defaultImgWidth = defaultImgInfo.getWidth();
			int defaultImgHeight = defaultImgInfo.getHeight();
			ImageResourceInfo defaultImgOverInfo = new ImageResourceInfo(defaultImgOverRR);
			int defaultImgOverWidth = defaultImgOverInfo.getWidth();
			int defaultImgOverHeight = defaultImgOverInfo.getHeight();
			ImageResourceInfo selectedImgInfo = new ImageResourceInfo(selectedImgRR);
			int selectedImgWidth = selectedImgInfo.getWidth();
			int selectedImgHeight = selectedImgInfo.getHeight();
			ImageResourceInfo selectedImgOverInfo = new ImageResourceInfo(selectedImgOverRR);
			int selectedImgOverWidth = selectedImgOverInfo.getWidth();
			int selectedImgOverHeight = selectedImgOverInfo.getHeight();

			CSSInlineStyle defaultImgStyle = new CSSInlineStyle();
			defaultImgStyle.add("background", "url(" + RequestCycle.get().urlFor(defaultImgRR) + ")");
			defaultImgStyle.add("width", defaultImgWidth + "px");
			defaultImgStyle.add("height", defaultImgHeight + "px");

			CSSInlineStyle defaultImgOverStyle = new CSSInlineStyle();
			defaultImgOverStyle.add("background", "url(" + RequestCycle.get().urlFor(defaultImgOverRR) + ")");
			defaultImgOverStyle.add("width", defaultImgOverWidth + "px");
			defaultImgOverStyle.add("height", defaultImgOverHeight + "px");

			CSSInlineStyle selectedImgStyle = new CSSInlineStyle();
			selectedImgStyle.add("background", "url(" + RequestCycle.get().urlFor(selectedImgRR) + ")");
			selectedImgStyle.add("width", selectedImgWidth + "px");
			selectedImgStyle.add("height", selectedImgHeight + "px");

			CSSInlineStyle selectedImgOverStyle = new CSSInlineStyle();
			selectedImgOverStyle.add("background", "url(" + RequestCycle.get().urlFor(selectedImgOverRR) + ")");
			selectedImgOverStyle.add("width", selectedImgOverWidth + "px");
			selectedImgOverStyle.add("height", selectedImgOverHeight + "px");

			defaultImgStyleList.add(defaultImgStyle);
			defaultImgOverStyleList.add(defaultImgOverStyle);
			selectedImgStyleList.add(selectedImgStyle);
			selectedImgOverStyleList.add(selectedImgOverStyle);

			this.width = defaultImgWidth;
			this.height = defaultImgHeight;
		}
	}

	/**
	 * Set the max selection allowed
	 * 
	 * @param maxSelection
	 */
	public void setMaxSelection(int maxSelection)
	{
		this.maxSelection = maxSelection;
	}

	/**
	 * Set the message
	 * 
	 * @param message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Set the resources
	 * 
	 * @param easing
	 * @param duration
	 * @param maxSelection
	 * @param animSelectGroupOption
	 */
	public void setResources(String easing, double duration, int maxSelection, List<AnimSelectOption> animSelectOptionList)
	{
		setEasing(easing);
		setDuration(duration);
		setMaxSelection(maxSelection);
		setAnimSelectOptionList(animSelectOptionList);
		setImageResources(animSelectOptionList);
	}

	/**
	 * Set the resources
	 * 
	 * @param easing
	 * @param duration
	 * @param maxSelection
	 * @param message
	 * @param animSelectOptionList
	 */
	public void setResources(String easing, double duration, int maxSelection, String message, List<AnimSelectOption> animSelectOptionList)
	{
		setEasing(easing);
		setDuration(duration);
		setMaxSelection(maxSelection);
		setMessage(message);
		setAnimSelectOptionList(animSelectOptionList);
		setImageResources(animSelectOptionList);
	}

	/**
	 * Set the selected mouseover image styles
	 * 
	 * @param selectedImgOverStyleList
	 */
	public void setSelectedImgOverStyleList(List<CSSInlineStyle> selectedImgOverStyleList)
	{
		this.selectedImgOverStyleList = selectedImgOverStyleList;
	}

	/**
	 * Set the selected image styles
	 * 
	 * @param selectedImgStyleList
	 */
	public void setSelectedImgStyleList(List<CSSInlineStyle> selectedImgStyleList)
	{
		this.selectedImgStyleList = selectedImgStyleList;
	}

	/**
	 * Set the width
	 * 
	 * @param width
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
}
