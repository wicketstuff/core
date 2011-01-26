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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs;

/**
 * @author Stefan Fussenegger
 */
public abstract class Animation implements IClusterable
{
	/**
	 * dojo.fx.wipeIn with default duration
	 */
	public static final Animation WIPE_IN = new WipeIn(null, null, null);
	
	/**
	 * dojo.fx.wipeOut with default duration
	 */
	public static final Animation WIPE_OUT = new WipeOut(null, null, null);
	
	/**
	 * dojo.fadeIn with default duration
	 */
	public static final Animation FADE_IN = new FadeIn(null, null, null);
	
	/**
	 * dojo.fadeOut with default duration
	 */
	public static final Animation FADE_OUT = new FadeOut(null, null, null);
	
	/**
	 * fade to 100% opacity in 500 ms
	 */
	public static final Animation FADE_OPACITY_IN = new FadeOpacity(0.5, 1.0, 500);
	
	/**
	 * fade to 50% opacity in 500 ms
	 */
	public static final Animation FADE_OPACITY_OUT = new FadeOpacity(1.0, 0.5, 500);
	
	/**
	 * @param componentMarkupId
	 * @return JavaScript for animation
	 */
	public abstract String getAnimationScript(String componentMarkupId);
	
	/**
	 * @return initial CSS for component
	 */
	public String getInitStyle()
	{
		return null;
	}

	/**
	 * @param componentMarkupId 
	 * @return null or a javascript string
	 */
	public String getInitJavaScript(String componentMarkupId)
	{
		return null;
	}
	
	/**
	 * @param libs
	 */
	public void setRequire(RequireDojoLibs libs)
	{
	}
	
	/**
	 * @author stf
	 */
	public final static class WipeIn extends DefaultAnimation
	{
		/**
		 * Construct.
		 * @param easing 
		 * @param duration 
		 * @param args 
		 */
		public WipeIn(Easing easing, Integer duration, Map<String, Object> args)
		{
			super("dojo.fx.wipeIn", easing, duration, args);
		}
		
		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#getInitStyle()
		 */
		@Override
		public String getInitStyle()
		{
			return "display:none";
		}

		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation.DefaultAnimation#setRequire(org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs)
		 */
		@Override
		public void setRequire(RequireDojoLibs libs)
		{
			super.setRequire(libs);
			libs.add("dojo.fx");
		}
	}

	/**
	 * dojo.fx.wipeOut
	 */
	public static final class WipeOut extends 	DefaultAnimation
	{
		/**
		 * Construct.
		 * @param easing
		 * @param duration
		 * @param args
		 */
		public WipeOut(Easing easing, Integer duration, Map<String,Object> args)
		{
			super("dojo.fx.wipeOut", easing, duration, args);
		}

		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#setRequire(org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs)
		 */
		@Override
		public void setRequire(RequireDojoLibs libs)
		{
			super.setRequire(libs);
			libs.add("dojo.fx");
		}		
	}

	/**
	 * 
	 * @author stf
	 */
	public static abstract class DefaultAnimation extends Animation {
		private String _name;
		private Easing _easing;
		private Integer _duration;
		private Map<String, Object> _args;
		
		/**
		 * Construct.
		 * @param name 
		 * @param easing
		 * @param duration 
		 * @param args 
		 */
		public DefaultAnimation(String name, Easing easing, Integer duration, Map<String, Object> args) {
			_name = name;
			_easing = easing;
			_duration = duration;
			_args = args;
		}
		
		
		/**
		 * @return animation name
		 */
		public String getName()
		{
			return _name;
		}


		/**
		 * @param name
		 */
		public void setName(String name)
		{
			_name = name;
		}


		/**
		 * @return animation duration
		 */
		public Integer getDuration()
		{
			return _duration;
		}


		/**
		 * @param duration
		 */
		public void setDuration(Integer duration)
		{
			_duration = duration;
		}


		/**
		 * @return animation args
		 */
		public Map<String, Object> getArgs()
		{
			return _args;
		}


		/**
		 * @param args
		 */
		public void getArgs(Map<String, Object> args)
		{
			_args = args;
		}


		/**
		 * @return current easing function
		 */
		public Easing getEasing()
		{
			return _easing;
		}

		/**
		 * @param easing
		 */
		public void setEasing(Easing easing)
		{
			_easing = easing;
		}

		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#setRequire(org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs)
		 */
		@Override
		public void setRequire(RequireDojoLibs libs)
		{
			super.setRequire(libs);
			if (getEasing() != null) {
				getEasing().setRequire(libs);
			}
		}
		
		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#getAnimationScript(String)
		 */
		@Override
		public String getAnimationScript(String componentMarkupId)
		{
			StringBuilder buf = new StringBuilder(getName()).append("({node:'").append(componentMarkupId).append("'");
			if (getEasing() != null) {
				buf.append(", easing:").append(getEasing().getName());
			}
			if (getDuration() != null) {
				buf.append(", duration:").append(getDuration());
			}
			return buf.append("})").toString();
		}	
	}
	
	/**
	 * 
	 */
	public static final class FadeOut extends DefaultAnimation
	{
		/**
		 * Construct.
		 * @param easing
		 * @param duration
		 * @param args
		 */
		public FadeOut(Easing easing, Integer duration, Map<String, Object> args)
		{
			super("dojo.fadeOut", easing, duration, args);
		}	
	}

	/**
	 *  
	 */
	public static final class FadeIn extends DefaultAnimation
	{
		/**
		 * Construct.
		 * @param easing 
		 * @param duration 
		 * @param args 
		 */
		public FadeIn(Easing easing, Integer duration, Map<String, Object> args)
		{
			super("dojo.fadeIn", easing, duration, args);
		}

		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#getInitJavaScript(java.lang.String)
		 */
		@Override
		public String getInitJavaScript(String componentMarkupId)
		{
			return "dojo.style('" + componentMarkupId + "', {'opacity':0.0,'visibility':'visible'})";
		}

		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#getInitStyle()
		 */
		@Override
		public String getInitStyle()
		{
			return "visibility:hidden";
		}
		
		
	}

	/**
	 * @author Stefan Fussenegger
	 */
	public static final class FadeOpacity extends Animation
	{
		private double _startOpacity;
		private double _targetOpacity;
		private int _duration;

		/**
		 * Construct.
		 * @param startOpacity
		 * @param targetOpacity
		 * @param duration
		 */
		public FadeOpacity(double startOpacity, double targetOpacity, int duration) {
			_startOpacity = startOpacity;
			_targetOpacity = targetOpacity;
			_duration = duration;
		}
		
		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#getAnimationScript(String)
		 */
		@Override
		public String getAnimationScript(String componentMarkupId)
		{
			return "dojo.animateProperty({node: '"+componentMarkupId+"', duration: "+_duration+", properties: { opacity: "+_targetOpacity+" }})";
		}

		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#getInitStyle()
		 */
		@Override
		public String getInitStyle ()
		{
			return (_startOpacity < 1.0) ? "visibility:hidden" : null;
		}

		/**
		 * @see org.wicketstuff.dojo11.dojofx.Animation#getInitJavaScript(java.lang.String)
		 */
		@Override
		public String getInitJavaScript(String componentMarkupId)
		{
			return "dojo.style('" + componentMarkupId + "', {'opacity': "+_startOpacity+", 'visibility': 'visible'})";
		}
		
	}

	/**
	 * @param target
	 * @param component 
	 * @param animation
	 */
	public static void appendAjaxLoadAnimation(AjaxRequestTarget target, Component component, Animation animation)
	{
		target.appendJavascript(animation.getAnimationScript(component.getMarkupId())+".play()");
	}

	/**
	 * @param target
	 * @param component 
	 * @param animation
	 */
	public static void prependAjaxLoadAnimation(AjaxRequestTarget target, Component component, Animation animation)
	{
		target.prependJavascript(animation.getAnimationScript(component.getMarkupId())+".play()");
	}
}
