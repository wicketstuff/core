package org.wicketstuff.yui.behavior.animation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.form.FormComponent;
import org.wicketstuff.yui.helper.JSArray;
import org.wicketstuff.yui.helper.OnEvent;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * YUI Animation container. This Animation acts as a container of a sequence of
 * effects. which will animate in sequence upon an event. Animation needs to be
 * attached to a Component. The list of triggers allow this animation on the
 * component to be triggered by multiple points. (multiple triggers)
 * 
 * If more Animation objects are added to a component, they are kept in an array
 * in the Javascript Animator and are cycled through. (chaining) e.g.
 * Component1.add(new Animation1(OnClick)); Component1.add(new
 * Animation2(OnClick));
 * 
 * where Animation1 has a "Blind Down" effect and Animation2 is a "Blind Up"
 * effect. upon the first click Component1 will "Blind Down" and the second
 * click will "Blind Up" etc...
 * 
 * YAHOO.util.Anim / Motion / Scroll. Also include Effects from <a
 * href="http://blog.davglass.com/files/yui/effects/"
 * >http://blog.davglass.com/files/yui/effects/</a>
 * 
 * TODO : change to YuiLoaderContributor
 * 
 * @author josh
 */
public class YuiAnimation extends AbstractBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * a sequence of effects.
	 */
	private List<YuiAnimEffect> effects = new ArrayList<YuiAnimEffect>();

	/**
	 * a list of triggers for this Animation. Each An
	 */
	private List<AnimValueGroup> triggers = new ArrayList<AnimValueGroup>();

	/**
	 * defines if the attached component should trigger the Animation
	 */
	private boolean isTriggeredByAttachedComponent = true;

	/**
	 * the component that this behaviour is bound to
	 */
	private Component component;

	/**
	 * the Event to trigger the animation
	 */
	private OnEvent onEvent;

	/**
	 * Constructor for an Animation that will be triggered when the 'event'
	 * occurs on the attached Component.
	 * 
	 * @param onEvent
	 */
	public YuiAnimation(OnEvent onEvent)
	{
		this.onEvent = onEvent;
	}

	/**
	 * Constructor for an Animation that will be triggered by the triggerId.
	 * 
	 * @param onEvent
	 * @param triggerId
	 */
	public YuiAnimation(OnEvent onEvent, String triggerId)
	{
		this.onEvent = onEvent;
		addTrigger(triggerId);
		isTriggeredByAttachedComponent = false;
	}

	/**
	 * Constructor for an Anmation that will be triggered by the component
	 * 
	 * @param onEvent
	 * @param component
	 */
	public YuiAnimation(OnEvent onEvent, Component component)
	{
		this.onEvent = onEvent;
		addTrigger(component);
		isTriggeredByAttachedComponent = false;
	}

	/**
	 * 
	 * @param onEvent
	 * @param component
	 * @param element
	 * @param unselectValue
	 */
	public YuiAnimation(OnEvent onEvent, Component component, FormComponent<?> element, String value)
	{
		this.onEvent = onEvent;
		addTrigger(component, element, value);
		isTriggeredByAttachedComponent = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.behavior.AbstractBehavior#bind(org.apache.wicket.Component
	 * )
	 */
	@Override
	public void bind(Component component)
	{
		this.component = component;
		this.component.setOutputMarkupId(true);
		component.add(YuiHeaderContributor.forModule("animation"));
		component.add(JavascriptPackageResource.getHeaderContribution(YuiAnimEffect.class,
				"effects/effects.js"));
		component.add(JavascriptPackageResource.getHeaderContribution(YuiAnimEffect.class,
				"effects/tools.js"));
		component.add(JavascriptPackageResource.getHeaderContribution(YuiAnimEffect.class,
				"effects/animator.js"));

		if (isTriggeredByAttachedComponent)
		{
			addTrigger(this.component);
		}
	}

	/*
	 * 
	 * Renders the javascript for this animation. basically 2 lines of
	 * javascript. 1/ var a_anim_object = new new YAHOO.util.Anim('yim-6-pic',
	 * hide_attributes, 1, YAHOO.util.Easing.easeIn); 2/
	 * Wicketstuff.yui.Animator.add(animValueGroups, trigger_event,
	 * animation_id);
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket
	 * .markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		StringBuffer buffer = new StringBuffer().append("var ").append(getAnimVar()).append(" = ")
				.append(buildEffectsJS()).append("Wicket.yui.Animator.add(").append(
						getAnimValueGroups()).append(",").append("'").append(getOnEvent()).append(
						"'").append(",").append("'").append(getComponentId()).append("'").append(
						")");
		response.renderOnDomReadyJavascript(buffer.toString());
	}

	/**
	 * return a list of AnimValueGroup as an Array
	 * 
	 * @return
	 */
	private String getAnimValueGroups()
	{
		JSArray triggers = new JSArray();

		for (AnimValueGroup aAvg : getTriggers())
		{
			triggers.add(aAvg.newJS());
		}

		return triggers.toString();
	}

	/**
	 * 
	 * @return
	 */
	private OnEvent getOnEvent()
	{
		return this.onEvent;
	}

	/**
	 * need this to 1/ strip out the initial "eff =" 2/ end "animate()"
	 * 
	 * var anim_singapore0 = anim_singapore01=new
	 * YAHOO.widget.Effects.BlindDown(
	 * singapore0,{delay:true},{delay:true});.animate();;
	 * 
	 * @return
	 */
	private String buildEffectsJS()
	{
		String js = buildEffectsJS(getAnimVar(), effects);
		return js.substring(js.indexOf("=") + 1, js.lastIndexOf(getAnimVar() + ".animate();"));
	}

	/**
	 * builds a Effects from the list
	 * 
	 * eff = new YAHOO.widget.Effects.BlindUp('demo13', { delay: true });
	 * eff.onEffectComplete.subscribe(function() { eff2 = new
	 * YAHOO.widget.Effects.BlindRight('demo13', { delay: true });
	 * eff2.onEffectComplete.subscribe(function() { eff3 = new
	 * YAHOO.widget.Effects.BlindDown('demo13', { delay: true });
	 * eff3.animate(); }); eff2.animate(); });
	 * 
	 * @return
	 */
	private String buildEffectsJS(String jsVar, List<YuiAnimEffect> effectslist)
	{
		int listsize = effectslist.size();
		if (listsize == 0)
		{
			return "";
		}
		else
		{
			YuiAnimEffect effect = effectslist.get(0);
			StringBuffer buffer = new StringBuffer();
			buffer.append(jsVar).append("=").append("new ").append(effect.newEffectJS()).append(
					"('").append(getComponentId()).append("',").append(effect.getAttributes())
					.append(",").append(effect.getOpts()).append(");");

			if (listsize > 1) // means at least one more child to go
			{
				buffer.append(jsVar).append(".").append(effect.onCompleteJS()).append(
						".subscribe(function() {");
				buffer.append(buildEffectsJS(jsVar + listsize, effectslist.subList(1, listsize)));
				buffer.append("});");
			}
			buffer.append(jsVar).append(".").append("animate();");
			return buffer.toString();
		}
	}

	/**
	 * the Javascript variable
	 * 
	 * @return
	 */
	private String getAnimVar()
	{
		return getOnEvent() + "_" + getComponentId();
	}

	/**
	 * the componet's markup id
	 * 
	 * @return
	 */
	public String getComponentId()
	{
		return this.component.getMarkupId();
	}

	/**
	 * the list of Effects
	 * 
	 * @return
	 */
	public List<YuiAnimEffect> getEffects()
	{
		return effects;
	}

	public void setEffects(List<YuiAnimEffect> effects)
	{
		this.effects = effects;
	}

	public List<AnimValueGroup> getTriggers()
	{
		return triggers;
	}

	public void setTriggers(List<AnimValueGroup> triggers)
	{
		this.triggers = triggers;
	}

	public boolean isTriggeredByAttachedComponent()
	{
		return isTriggeredByAttachedComponent;
	}

	public void setTriggeredByAttachedComponent(boolean isTriggeredByAttachedComponent)
	{
		this.isTriggeredByAttachedComponent = isTriggeredByAttachedComponent;
	}

	public Component getComponent()
	{
		return component;
	}

	public void setComponent(Component component)
	{
		this.component = component;
	}

	public void setOnEvent(OnEvent onEvent)
	{
		this.onEvent = onEvent;
	}

	/**
	 * adds an Anim Effect to the list of Effects that will be batched up and
	 * run one after another.
	 * 
	 * @param effect
	 * @return
	 */
	public YuiAnimation addEffect(YuiAnimEffect effect)
	{
		getEffects().add(effect);
		return this;
	}

	/**
	 * adds a trigger for this Animation
	 * 
	 * @param triggerId
	 */
	public YuiAnimation addTrigger(String triggerId)
	{
		getTriggers().add(new AnimValueGroup(triggerId, null, null, false));
		return this;
	}

	/**
	 * adds a component which will be a trigger
	 * 
	 * @param component
	 * @return
	 */
	public YuiAnimation addTrigger(Component component)
	{
		getTriggers().add(new AnimValueGroup(component, null, null, false));
		return this;
	}

	/**
	 * 
	 * @param component
	 * @param element
	 * @param value
	 * @return
	 */
	private YuiAnimation addTrigger(Component component, FormComponent<?> element, String value)
	{
		getTriggers().add(new AnimValueGroup(component, element, value, false));
		return this;
	}

	/**
	 * adds a component which will be a trigger
	 * 
	 * @param component
	 * @param value
	 * @param element
	 * @return
	 */
	public YuiAnimation addTriggerOnValue(Component component, FormComponent<?> element,
			String value)
	{
		getTriggers().add(new AnimValueGroup(component, element, value, true));
		return this;
	}

	/**
	 * an AnimValueGroup
	 * 
	 * @author josh
	 */
	private class AnimValueGroup implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		FormComponent<?> element;

		String value;

		Component triggerComponent;

		String triggerId;

		boolean isAnimateOnValue;

		String anim;

		/**
		 * 
		 * @param triggerId
		 * @param element
		 * @param value
		 * @param animateOnValue
		 */
		public AnimValueGroup(String triggerId, FormComponent<?> element, String value,
				boolean animateOnValue)
		{
			this.triggerId = triggerId;
			this.isAnimateOnValue = animateOnValue;
			this.element = element;
			this.value = value;
		}

		/**
		 * 
		 * @param triggerComponent
		 * @param element
		 * @param value
		 * @param animateOnValue
		 */
		public AnimValueGroup(Component triggerComponent, FormComponent<?> element, String value,
				boolean animateOnValue)
		{
			this.triggerComponent = triggerComponent;
			this.isAnimateOnValue = animateOnValue;
			this.element = element;
			this.value = value;
			if (this.element != null)
				this.element.setOutputMarkupId(true);
			if (this.triggerComponent != null)
				this.triggerComponent.setOutputMarkupId(true);
		}

		/**
		 * generates the new AnimValueGroup javascript
		 * Wicket.yui.AnimValueGroup(trigger_id, anim, element_id,
		 * element_value, animate_on_value)
		 * 
		 * @return
		 */
		public String newJS()
		{
			StringBuffer script = new StringBuffer();

			script.append("new Wicket.yui.AnimValueGroup(").append("'").append(getTriggerId())
					.append("'").append(",").append(getAnimVar()).append(",")
					.append(getElementId()).append(",").append(getElementValue()).append(",")
					.append(this.isAnimateOnValue).append(")");

			return script.toString();
		}

		/**
		 * the value to be returned when this animation occurs
		 * 
		 * @return
		 */
		private String getElementValue()
		{
			String value = null;
			if (this.value != null)
			{
				value = "\"" + this.value + "\"";
			}
			return value;
		}

		/**
		 * 
		 * @return
		 */
		private String getElementId()
		{
			String markupId = null;
			if (this.element != null)
			{
				markupId = "\"" + this.element.getMarkupId() + "\"";
			}
			return markupId;
		}

		/**
		 * 
		 * @return
		 */
		private String getTriggerId()
		{
			if (this.triggerComponent != null)
				return this.triggerComponent.getMarkupId();
			else
				return this.triggerId;
		}
	}
}
