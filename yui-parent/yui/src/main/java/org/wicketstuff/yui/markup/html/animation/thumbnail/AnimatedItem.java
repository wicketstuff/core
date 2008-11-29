package org.wicketstuff.yui.markup.html.animation.thumbnail;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.yui.behavior.Anim;
import org.wicketstuff.yui.behavior.AnimEffect;
import org.wicketstuff.yui.behavior.Animation;
import org.wicketstuff.yui.behavior.Attributes;
import org.wicketstuff.yui.behavior.Easing;
import org.wicketstuff.yui.behavior.OnEvent;

/**
 * AnimatedItem contains 3 component which have in built animations that will
 * overlay on the mouseover/out/click events. This serves as the base class for 
 * other Animated interested in this behaviour.
 * 
 *  nb: if the child component is a complex panel with lots of nested divs it may not
 *  work. I only use a Panel of IMG and Labels
 * 
 * @author josh
 *
 */
@SuppressWarnings("serial")
public abstract class AnimatedItem extends Panel 
{
	private static final long serialVersionUID = 1L;
		
	private Component onloadItem ;
	
	private Component mouseoverItem;

	private Component onclickItem;

	private FormComponent element;

	private String selectValue;

	private String unselectValue;

	private Animation onunselectAnimation;

	public static Attributes SHOW_ATTRIBUTE = new Attributes("zIndex", 0, 1);
	
	public static Attributes HIDE_ATTRIBUTE = new Attributes("zIndex", 1, 0);
	
	/**
	 * 
	 * @param id
	 * @param settings
	 */
	public AnimatedItem(String id)
	{
		super(id);
		add(HeaderContributor.forCss(AnimatedItem.class, "AnimatedItem.css"));
	}
	
	/**
	 * 
	 * @param id
	 * @param element
	 * @param selectValue
	 * @param unselectValue
	 */
	public AnimatedItem(String id, FormComponent element, String selectValue, String unselectValue)
	{
		this(id);
		this.element = element;
		this.selectValue = selectValue;
		this.unselectValue = unselectValue;
	}

	/**
	 * CHILD CLASS MUST CALL THIS !! 
	 * TODO : but how to ensure this???
	 */
	public void init()
	{
		add(onloadItem = newOnloadItem("onload_item"));
		add(mouseoverItem = newMouseoverItem("mouseover_item"));
		add(onclickItem = newOnclickItem("onclick_item"));
		
		// animation
		mouseoverItem.add(new Animation(OnEvent.mouseover, onloadItem).addEffect(mouseoverEffect()));
		mouseoverItem.add(new Animation(OnEvent.mouseout, mouseoverItem).addEffect(mouseoutEffect()));
		
		// this is the "select"
		onclickItem.add(new Animation(OnEvent.click, mouseoverItem, getElement(), getSelectValue())
							.addEffect(onselectEffect()));
		
		// this is the "unselect"
		onclickItem.add(onunselectAnimation = new Animation(OnEvent.click, onclickItem, getElement(), getUnselectValue())
							.addEffect(onunselectEffect()));
	}
	
	public AnimEffect onunselectEffect()
	{
		return new Anim(Anim.Type.Anim, HIDE_ATTRIBUTE, 1, Easing.easeNone);
	}

	public AnimEffect onselectEffect()
	{
		return new Anim(Anim.Type.Anim, SHOW_ATTRIBUTE, 1, Easing.easeNone);
	}

	public AnimEffect mouseoutEffect()
	{
		return new Anim(Anim.Type.Anim, HIDE_ATTRIBUTE, 1, Easing.easeNone);
	}

	public AnimEffect mouseoverEffect()
	{
		return new Anim(Anim.Type.Anim, SHOW_ATTRIBUTE, 1, Easing.easeNone);
	}

	public abstract Component newOnloadItem(String id);
	
	public abstract Component newMouseoverItem(String id);
	
	public abstract Component newOnclickItem(String id);

	public FormComponent getElement()
	{
		return element;
	}

	public void setElement(FormComponent element)
	{
		this.element = element;
	}

	public String getSelectValue()
	{
		return selectValue;
	}

	public void setSelectValue(String selectValue)
	{
		this.selectValue = selectValue;
	}

	public String getUnselectValue()
	{
		return unselectValue;
	}

	public void setUnselectValue(String unselectValue)
	{
		this.unselectValue = unselectValue;
	}

	public Animation getOnunselectAnimation()
	{
		return onunselectAnimation;
	}

	public void setOnunselectAnimation(Animation onunselectAnimation)
	{
		this.onunselectAnimation = onunselectAnimation;
	}

	public Component getMouseoverItem()
	{
		return mouseoverItem;
	}

	public void setMouseoverItem(Component mouseoverItem)
	{
		this.mouseoverItem = mouseoverItem;
	}
}