package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.yui.behavior.animation.YuiAnimation;
import org.wicketstuff.yui.behavior.animation.YuiEasing;
import org.wicketstuff.yui.behavior.animation.YuiEffect;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.helper.Attributes;
import org.wicketstuff.yui.helper.OnEvent;

/**
 * Example on using Animation...
 * 
 * @author josh
 * 
 */
public class AnimationPage extends WicketExamplePage
{

	public AnimationPage()
	{
		// Shrink Grow Fold UnFold Pulse
		newAnimatedComponent("shrink", OnEvent.click, YuiEffect.Type.Shrink, true);
		newAnimatedComponent("grow", OnEvent.click, YuiEffect.Type.Grow, true);
		newAnimatedComponent("fold", OnEvent.click, YuiEffect.Type.Fold, true);
		newAnimatedComponent("unfold", OnEvent.click, YuiEffect.Type.UnFold, true);
		newAnimatedComponent("pulse", OnEvent.click, YuiEffect.Type.Pulse, true);

		// Shadow Fade Appear ShakeLR ShakeTB
		newAnimatedComponent("shadow", OnEvent.click, YuiEffect.Type.Shadow, true); // needs
		// position:relative

		// Attributes
		Attributes delay = new Attributes("delay", "true");

		// Appear
		newAnimatedComponent("appear", OnEvent.click, YuiEffect.Type.Appear, false);
		newAnimatedComponent("shakelr", OnEvent.click, YuiEffect.Type.ShakeLR, true); // needs
		// position:relative
		newAnimatedComponent("shaketb", OnEvent.click, YuiEffect.Type.ShakeTB, true); // needs
		// position:relative

		// fade followed by appear
		newAnimatedComponent("fade", OnEvent.click, YuiEffect.Type.Fade, true);
		newAnimatedComponent("drop", OnEvent.click, YuiEffect.Type.Drop, true);

		// TV
		WebMarkupContainer tv;
		add(tv = new WebMarkupContainer("tv"));
		tv.add(new YuiAnimation(OnEvent.click).addEffect(new YuiEffect(YuiEffect.Type.TV, delay))
				.addEffect(new YuiEffect(YuiEffect.Type.Appear)));

		// mouse over
		newAnimatedComponent("fade_mouseover", OnEvent.mouseover, YuiEffect.Type.Fade, true);

		// Blind Up / Down + attributes...
		Attributes ghost_attr = delay.add(new Attributes("ghost", "true"));
		Attributes easing = new Attributes("ease", YuiEasing.elasticBoth.constant());

		WebMarkupContainer ghost;
		add(ghost = new WebMarkupContainer("ghost"));
		ghost.add(new YuiAnimation(OnEvent.click).addEffect(
				new YuiEffect(YuiEffect.Type.BlindUp, ghost_attr)).addEffect(
				new YuiEffect(YuiEffect.Type.BlindDown, easing)));


		// Batch Blind Up / Down / Right / Drop / Appear
		WebMarkupContainer batch;
		add(batch = new WebMarkupContainer("batch"));
		batch.add(new YuiAnimation(OnEvent.click).addEffect(
				new YuiEffect(YuiEffect.Type.BlindUp, delay)).addEffect(
				new YuiEffect(YuiEffect.Type.BlindDown)).addEffect(
				new YuiEffect(YuiEffect.Type.BlindRight, delay)).addEffect(
				new YuiEffect(YuiEffect.Type.Drop)).addEffect(
				new YuiEffect(YuiEffect.Type.Appear, delay)));

		// Blind
		newAnimatedComponent("blindup", OnEvent.click, YuiEffect.Type.BlindUp, true);
		newAnimatedComponent("blinddown", OnEvent.click, YuiEffect.Type.BlindDown, true);
		newAnimatedComponent("blindleft", OnEvent.click, YuiEffect.Type.BlindLeft, true);
		newAnimatedComponent("blindright", OnEvent.click, YuiEffect.Type.BlindRight, true);

		// Blind Right + with attributes
		WebMarkupContainer blindright;
		add(blindright = new WebMarkupContainer("blindright_binded"));

		// set up attributes
		Attributes blindrightAttributes = new Attributes("bind", "right");
		blindrightAttributes.add(delay);
		blindright.add(new YuiAnimation(OnEvent.click).addEffect(
				new YuiEffect(YuiEffect.Type.BlindRight, blindrightAttributes)).addEffect(
				new YuiEffect(YuiEffect.Type.Appear, delay)));

		// Click for Info ...
		WebMarkupContainer info;
		add(info = new WebMarkupContainer("info"));

		info.add(new YuiAnimation(OnEvent.click, "clickformore").addEffect(new YuiEffect(
				YuiEffect.Type.BlindDown, delay)));
		info.add(new YuiAnimation(OnEvent.click, "clickformore").addEffect(new YuiEffect(
				YuiEffect.Type.BlindUp, delay)));
	}

	/**
	 * create an Animated Component
	 * 
	 * @param id
	 * @param onEvent
	 * @param effectType
	 * @param delay
	 * @return
	 */
	private Component newAnimatedComponent(String id, OnEvent onEvent, YuiEffect.Type effectType,
			boolean delay)
	{
		WebMarkupContainer comp;
		comp = new WebMarkupContainer(id);
		Attributes attributes = new Attributes();

		if (delay)
		{
			attributes.add("delay", "true");
		}

		add(comp);
		comp.add(new YuiAnimation(onEvent).addEffect(new YuiEffect(effectType, attributes))
				.addEffect(new YuiEffect(YuiEffect.Type.Appear)));
		return comp;
	}
}
