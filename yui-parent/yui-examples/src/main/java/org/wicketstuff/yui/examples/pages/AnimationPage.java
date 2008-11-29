package org.wicketstuff.yui.examples.pages;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.yui.behavior.Animation;
import org.wicketstuff.yui.behavior.Attributes;
import org.wicketstuff.yui.behavior.Easing;
import org.wicketstuff.yui.behavior.Effect;
import org.wicketstuff.yui.behavior.OnEvent;
import org.wicketstuff.yui.examples.WicketExamplePage;

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
		newAnimatedComponent("shrink", 	OnEvent.click, Effect.Type.Shrink, true);
		newAnimatedComponent("grow", 	OnEvent.click, Effect.Type.Grow,   true);
		newAnimatedComponent("fold", 	OnEvent.click, Effect.Type.Fold,   true);
		newAnimatedComponent("unfold", 	OnEvent.click, Effect.Type.UnFold, true);
		newAnimatedComponent("pulse", 	OnEvent.click, Effect.Type.Pulse,  true);

		// Shadow Fade Appear ShakeLR ShakeTB
		newAnimatedComponent("shadow", 	OnEvent.click, Effect.Type.Shadow, true);  // needs position:relative 
		
		// Attributes
		Attributes delay = new Attributes("delay", "true");
		
		// Appear
		newAnimatedComponent("appear", OnEvent.click, Effect.Type.Appear, false);
		newAnimatedComponent("shakelr", OnEvent.click, Effect.Type.ShakeLR, true); // needs position:relative 
		newAnimatedComponent("shaketb", OnEvent.click, Effect.Type.ShakeTB, true); // needs position:relative 
		
		// fade followed by appear
		newAnimatedComponent("fade", OnEvent.click, Effect.Type.Fade, true);
		newAnimatedComponent("drop", OnEvent.click, Effect.Type.Drop, true);
		
		// TV 
		WebMarkupContainer tv;
		add(tv = new WebMarkupContainer("tv"));
		tv.add(new Animation(OnEvent.click)
						.addEffect(new Effect(Effect.Type.TV, delay))
						.addEffect(new Effect(Effect.Type.Appear)));
		
		// mouse over 
		newAnimatedComponent("fade_mouseover", OnEvent.mouseover, Effect.Type.Fade, true);
		
		// Blind Up / Down + attributes...
		Attributes ghost_attr = delay.add(new Attributes("ghost", "true"));
		Attributes easing = new Attributes("ease", Easing.elasticBoth.function());
		
		WebMarkupContainer ghost;
		add(ghost = new WebMarkupContainer("ghost"));
		ghost.add(new Animation(OnEvent.click)
						.addEffect(new Effect(Effect.Type.BlindUp,  ghost_attr))
						.addEffect(new Effect(Effect.Type.BlindDown, easing)));
		
		
		// Batch Blind Up / Down / Right / Drop / Appear 
		WebMarkupContainer batch;
		add(batch = new WebMarkupContainer("batch"));
		batch.add(new Animation(OnEvent.click)
						.addEffect(new Effect(Effect.Type.BlindUp, delay))
						.addEffect(new Effect(Effect.Type.BlindDown))
						.addEffect(new Effect(Effect.Type.BlindRight, delay))
						.addEffect(new Effect(Effect.Type.Drop))
						.addEffect(new Effect(Effect.Type.Appear, delay)));
		
		// Blind
		newAnimatedComponent("blindup", OnEvent.click, Effect.Type.BlindUp, true);
		newAnimatedComponent("blinddown", OnEvent.click, Effect.Type.BlindDown, true);
		newAnimatedComponent("blindleft", OnEvent.click, Effect.Type.BlindLeft, true);
		newAnimatedComponent("blindright", OnEvent.click, Effect.Type.BlindRight, true);
		
		// Blind Right + with attributes
		WebMarkupContainer blindright;
		add(blindright = new WebMarkupContainer("blindright_binded"));
		
		// set up attributes
		Attributes blindrightAttributes = new Attributes("bind", "right");
		blindrightAttributes.add(delay);
		blindright.add(new Animation(OnEvent.click)
						.addEffect(new Effect(Effect.Type.BlindRight, blindrightAttributes))
						.addEffect(new Effect(Effect.Type.Appear, delay)));
		
		// Click for Info ... 
		WebMarkupContainer info;
		add(info = new WebMarkupContainer("info"));

		info.add(new Animation(OnEvent.click, "clickformore").addEffect(new Effect(Effect.Type.BlindDown, delay)));
		info.add(new Animation(OnEvent.click, "clickformore").addEffect(new Effect(Effect.Type.BlindUp, 	delay)));
	}
	
	/**
	 * create an Animated Component
	 * @param id
	 * @param onEvent
	 * @param effectType
	 * @param delay
	 * @return
	 */
	private Component newAnimatedComponent(String id, OnEvent onEvent, Effect.Type effectType, boolean delay)
	{
		WebMarkupContainer comp;
		comp = new WebMarkupContainer(id);
		Attributes attributes = new Attributes();
		
		if (delay)
		{
			attributes.add("delay", "true");
		}
		
		add(comp);
		comp.add(new Animation(onEvent)
						.addEffect(new Effect(effectType, attributes))
						.addEffect(new Effect(Effect.Type.Appear)));
		return comp;
	}
}
