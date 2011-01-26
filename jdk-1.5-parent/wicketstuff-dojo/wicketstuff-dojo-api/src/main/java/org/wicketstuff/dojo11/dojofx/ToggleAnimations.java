/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.dojo11.dojofx;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.WicketRuntimeException;

/**
 * @author Stefan Fussenegger
 */
public class ToggleAnimations implements IClusterable
{
	/**
	 * wipe in and wipe out
	 */
	public static final ToggleAnimations WIPE = new ToggleAnimations(Animation.WIPE_IN, Animation.WIPE_OUT);
	
	/**
	 * fade in and fade out
	 */
	public static final ToggleAnimations FADE = new ToggleAnimations(Animation.FADE_IN, Animation.FADE_OUT);

	/**
	 * fade to 50% opacity
	 */
	public static final ToggleAnimations FADE_TO_OPACITY = new ToggleAnimations(Animation.FADE_OPACITY_IN, Animation.FADE_OPACITY_OUT);
	
	/**
	 * wipe with elastic easing
	 */
	public static final ToggleAnimations ELASTIC_WIPE = new ToggleAnimations(new Animation.WipeIn(Easing.ELASTIC_IN_OUT, null, null), new Animation.WipeOut(Easing.ELASTIC_IN_OUT, null, null));
	
	/**
	 * wipe with bounce easing
	 */
	public static final ToggleAnimations BOUNCE_WIPE = new ToggleAnimations(new Animation.WipeIn(Easing.BOUNCE_OUT, null, null), new Animation.WipeOut(Easing.BOUNCE_IN, null, null));
	
	/**
	 * wipe with back easing
	 */
	public static final ToggleAnimations BACK_WIPE = new ToggleAnimations(new Animation.WipeIn(Easing.BACK_IN_OUT, null, null), new Animation.WipeOut(Easing.BACK_IN_OUT, null, null));

	/**
	 * wipe with quart easing
	 */
	public static final ToggleAnimations QUART_WIPE = new ToggleAnimations(new Animation.WipeIn(Easing.QUART_OUT, null, null), new Animation.WipeOut(Easing.QUART_IN, null, null));

	
	private Animation _showAnim;

	private Animation _hideAnim;
	
	/**
	 * @return show animation
	 */
	public Animation getShowAnim()
	{
		return _showAnim;
	}

	/**
	 * @param anim new show animation
	 */
	public void setShowAnim(Animation anim)
	{
		_showAnim = anim;
	}

	/**
	 * @return hide animation
	 */
	public Animation getHideAnim()
	{
		return _hideAnim;
	}

	/**
	 * @param anim new hide animation
	 */
	public void setHideAnim(Animation anim)
	{
		_hideAnim = anim;
	}

	/**
	 * @param start 
	 * @param end 
	 * @return toggle functions that fade between opacity values
	 */
	public static ToggleAnimations getFadeToOpacity(final double start, final double end) {
		return getFadeToOpacity(end, start, 500);
	}
	
	/**
	 * @param start 
	 * @param end 
	 * @param duration
	 * @return toggle functions that fade between opacity values
	 */
	public static ToggleAnimations getFadeToOpacity(final double start, final double end, int duration) {
		return new FadeToOpacityAnimations(end, start, duration);
	}
	
	/**
	 * 
	 * Construct.
	 */
	public ToggleAnimations() {
		
	}
	
	/**
	 * Construct.
	 * @param showAnim
	 * @param hideAnim
	 */
	public ToggleAnimations(Animation showAnim, Animation hideAnim)
	{
		setShowAnim(showAnim);
		setHideAnim(hideAnim);
	}

	/**
	 * 
	 * @param component 
	 * @return JavaScript hide function
	 */
	public final String getShowFunction(Component component) {
		return getShowAnim().getAnimationScript(component.getMarkupId());
	}
	
	/**
	 * 
	 * @param component 
	 * @return JavaScript hide function
	 */
	public final String getHideFunction(Component component) {
		return getHideAnim().getAnimationScript(component.getMarkupId());
	}
	
	private static final class FadeToOpacityAnimations extends ToggleAnimations
	{
		/**
		 * Construct.
		 * @param hidden
		 * @param shown
		 * @param duration
		 */
		public FadeToOpacityAnimations(double hidden, double shown, int duration)
		{
			if ((shown < 0.0) || (shown >= hidden) || (hidden > 1.0))
			{
				throw new WicketRuntimeException("0 <= hidden < shown <= 1.0");
			}
			setShowAnim(new Animation.FadeOpacity(hidden, shown, duration));
			setHideAnim(new Animation.FadeOpacity(shown, hidden, duration));
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param relative
	 * @param duration 
	 * @return toggle animations for slide
	 */
	public static ToggleAnimations getSlide(int x, int y, boolean relative, int duration)
	{
		throw new WicketRuntimeException("animation not implemented");
	}

	/**
	 * @param easing 
	 * @param duration
	 * @param args 
	 * @return toggle animations for wipe
	 */
	public static ToggleAnimations getWipe(Easing easing, int duration, Map<String, Object> args)
	{
		return new ToggleAnimations(new Animation.WipeIn(easing, duration, args), new Animation.WipeOut(easing, duration, args));
	}
}
