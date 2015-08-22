package org.wicketstuff.yui.markup.html.anim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.wicketstuff.yui.helper.CSSInlineStyle;
import org.wicketstuff.yui.helper.ImageResourceInfo;
import org.wicketstuff.yui.helper.YuiImage;

/**
 * An AnimSettings allows the user to define the anim select settings
 * 
 * @author cptan
 * 
 */
public class AnimSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	private String easing;

	private double duration;

	private int maxSelection;

	private String message;

	private List<AnimOption> animOptionList;

	private List<CSSInlineStyle> defaultImgStyleList = new ArrayList<CSSInlineStyle>();

	private List<CSSInlineStyle> defaultImgOverStyleList = new ArrayList<CSSInlineStyle>();

	private List<CSSInlineStyle> selectedImgStyleList = new ArrayList<CSSInlineStyle>();

	private List<CSSInlineStyle> selectedImgOverStyleList = new ArrayList<CSSInlineStyle>();

	private int width;

	private int height;

	/**
	 * Creates an AnimSettings
	 * 
	 */
	public AnimSettings() {
	}

	/**
	 * Get the default settings
	 * 
	 * @param easing -
	 *            the easing effect of the animation
	 * @param duration -
	 *            the duration of the animation
	 * @param maxSelection -
	 *            the maximum selection allowed
	 * @param animSelectOptionList -
	 *            a list of the animOptions
	 * @return the default settings
	 */
	public static AnimSettings getDefault(String easing, double duration,
			int maxSelection, List<AnimOption> animOptionList) {
		AnimSettings settings = new AnimSettings();
		settings.setResources(easing, duration, maxSelection, animOptionList);
		return settings;
	}

	/**
	 * Set the resources
	 * 
	 * @param easing -
	 *            the easing effect of the animation
	 * @param duration -
	 *            the duration of the animation
	 * @param maxSelection -
	 *            the maximum selection allowed
	 * @param animSelectGroupOption -
	 *            a list of the animOptions
	 */
	public void setResources(String easing, double duration, int maxSelection,
			List<AnimOption> animOptionList) {
		setEasing(easing);
		setDuration(duration);
		setMaxSelection(maxSelection);
		setAnimOptionList(animOptionList);
		setImageResources(animOptionList);
	}

	/**
	 * Get the default settings
	 * 
	 * @param easing -
	 *            the easing effect of the animation
	 * @param duration -
	 *            the duration of the animation
	 * @param maxSelection -
	 *            the maximum selection allowed
	 * @param message -
	 *            the message to display if reach the maximum selection
	 * @param animSelectOptionList -
	 *            a list of the animOptions
	 * @return the default settings
	 */
	public static AnimSettings getDefault(String easing, double duration,
			int maxSelection, String message, List<AnimOption> animOptionList) {
		AnimSettings settings = new AnimSettings();
		settings.setResources(easing, duration, maxSelection, message,
				animOptionList);
		return settings;
	}

	/**
	 * Set the resources
	 * 
	 * @param easing -
	 *            the easing effect of the animation
	 * @param duration -
	 *            the duration of the animation
	 * @param maxSelection -
	 *            the maximum selection allowed
	 * @param message -
	 *            the message to display if reach the maximum selection
	 * @param animSelectOptionList -
	 *            a list of the animOptions
	 */
	public void setResources(String easing, double duration, int maxSelection,
			String message, List<AnimOption> animOptionList) {
		setEasing(easing);
		setDuration(duration);
		setMaxSelection(maxSelection);
		setMessage(message);
		setAnimOptionList(animOptionList);
		setImageResources(animOptionList);
	}

	/**
	 * Set the image resources
	 * 
	 * @param animOptionList -
	 *            a list of the animOptions
	 */
	public void setImageResources(List<AnimOption> animOptionList) {
		for (int i = animOptionList.size() - 1; i >= 0; i--) {
			YuiImage defaultImg = ((AnimOption) animOptionList.get(i))
					.getDefaultImg();
			YuiImage defaultImgOver = ((AnimOption) animOptionList.get(i))
					.getDefaultImgOver();
			YuiImage selectedImg = ((AnimOption) animOptionList.get(i))
					.getSelectedImg();
			YuiImage selectedImgOver = ((AnimOption) animOptionList.get(i))
					.getSelectedImgOver();

			ResourceReference defaultImgRR = new ResourceReference(
					AnimSettings.class, defaultImg.getFileName());
			ResourceReference defaultImgOverRR = new ResourceReference(
					AnimSettings.class, defaultImgOver.getFileName());
			ResourceReference selectedImgRR = new ResourceReference(
					AnimSettings.class, selectedImg.getFileName());
			ResourceReference selectedImgOverRR = new ResourceReference(
					AnimSettings.class, selectedImgOver.getFileName());

			ImageResourceInfo defaultImgInfo = new ImageResourceInfo(
					defaultImgRR);
			int defaultImgWidth = defaultImgInfo.getWidth();
			int defaultImgHeight = defaultImgInfo.getHeight();
			ImageResourceInfo defaultImgOverInfo = new ImageResourceInfo(
					defaultImgOverRR);
			int defaultImgOverWidth = defaultImgOverInfo.getWidth();
			int defaultImgOverHeight = defaultImgOverInfo.getHeight();
			ImageResourceInfo selectedImgInfo = new ImageResourceInfo(
					selectedImgRR);
			int selectedImgWidth = selectedImgInfo.getWidth();
			int selectedImgHeight = selectedImgInfo.getHeight();
			ImageResourceInfo selectedImgOverInfo = new ImageResourceInfo(
					selectedImgOverRR);
			int selectedImgOverWidth = selectedImgOverInfo.getWidth();
			int selectedImgOverHeight = selectedImgOverInfo.getHeight();

			CSSInlineStyle defaultImgStyle = new CSSInlineStyle();
			defaultImgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(defaultImgRR) + ")");
			defaultImgStyle.add("width", defaultImgWidth + "px");
			defaultImgStyle.add("height", defaultImgHeight + "px");

			CSSInlineStyle defaultImgOverStyle = new CSSInlineStyle();
			defaultImgOverStyle.add("background", "url("
					+ RequestCycle.get().urlFor(defaultImgOverRR) + ")");
			defaultImgOverStyle.add("width", defaultImgOverWidth + "px");
			defaultImgOverStyle.add("height", defaultImgOverHeight + "px");

			CSSInlineStyle selectedImgStyle = new CSSInlineStyle();
			selectedImgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(selectedImgRR) + ")");
			selectedImgStyle.add("width", selectedImgWidth + "px");
			selectedImgStyle.add("height", selectedImgHeight + "px");

			CSSInlineStyle selectedImgOverStyle = new CSSInlineStyle();
			selectedImgOverStyle.add("background", "url("
					+ RequestCycle.get().urlFor(selectedImgOverRR) + ")");
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
	 * Set the AnimSelectOptionList
	 * 
	 * @return a list of the animOptions
	 */
	public List<AnimOption> getAnimOptionList() {
		return (List<AnimOption>) animOptionList;
	}

	/**
	 * Set the AnimOptionList
	 * 
	 * @param animOptionList -
	 *            a new list of the animOptions
	 */
	public void setAnimOptionList(List<AnimOption> animOptionList) {
		this.animOptionList = animOptionList;
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
	 * Set the duration
	 * 
	 * @param duration -
	 *            the new duration
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * Get the easing effect
	 * 
	 * @return the easing effect
	 */
	public String getEasing() {
		return easing;
	}

	/**
	 * Set the easing effects
	 * 
	 * @param easing -
	 *            the new easing effect
	 */
	public void setEasing(String easing) {
		this.easing = easing;
	}

	/**
	 * Get the maximum selection allowed
	 * 
	 * @return the maximum selection allowed
	 */
	public int getMaxSelection() {
		return maxSelection;
	}

	/**
	 * Set the maximum selection allowed
	 * 
	 * @param maxSelection -
	 *            the new maximum selection allowed
	 */
	public void setMaxSelection(int maxSelection) {
		this.maxSelection = maxSelection;
	}

	/**
	 * Get the message
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message
	 * 
	 * @param message -
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the default mouseover image style list
	 * 
	 * @return the default mouseover image style list
	 */
	public List<CSSInlineStyle> getDefaultImgOverStyleList() {
		return (List<CSSInlineStyle>) defaultImgOverStyleList;
	}

	/**
	 * Set the default mouseover image style list
	 * 
	 * @param defaultImgOverStyleList -
	 *            the new default mouseover image style list
	 */
	public void setDefaultImgOverStyleList(
			List<CSSInlineStyle> defaultImgOverStyleList) {
		this.defaultImgOverStyleList = defaultImgOverStyleList;
	}

	/**
	 * Get the default image style list
	 * 
	 * @return the default image style list
	 */
	public List<CSSInlineStyle> getDefaultImgStyleList() {
		return (List<CSSInlineStyle>) defaultImgStyleList;
	}

	/**
	 * Set the default image style list
	 * 
	 * @param defaultImgStyleList -
	 *            set the new default image style list
	 */
	public void setDefaultImgStyleList(List<CSSInlineStyle> defaultImgStyleList) {
		this.defaultImgStyleList = defaultImgStyleList;
	}

	/**
	 * Get the selected mouseover image style list
	 * 
	 * @return the selected mouseover image style list
	 */
	public List<CSSInlineStyle> getSelectedImgOverStyleList() {
		return (List<CSSInlineStyle>) selectedImgOverStyleList;
	}

	/**
	 * Set the selected mouseover image style list
	 * 
	 * @param selectedImgOverStyleList -
	 *            the new selected mouseover image style list
	 */
	public void setSelectedImgOverStyleList(
			List<CSSInlineStyle> selectedImgOverStyleList) {
		this.selectedImgOverStyleList = selectedImgOverStyleList;
	}

	/**
	 * Get the selected image style list
	 * 
	 * @return the selected image style list
	 */
	public List<CSSInlineStyle> getSelectedImgStyleList() {
		return (List<CSSInlineStyle>) selectedImgStyleList;
	}

	/**
	 * Set the selected image style list
	 * 
	 * @param selectedImgStyleList -
	 *            the new selected image style list
	 */
	public void setSelectedImgStyleList(List<CSSInlineStyle> selectedImgStyleList) {
		this.selectedImgStyleList = selectedImgStyleList;
	}

	/**
	 * Get the height
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set the height
	 * 
	 * @param height -
	 *            the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Get the width
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the width
	 * 
	 * @param width -
	 *            the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
